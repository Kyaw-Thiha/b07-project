package com.example.b07project.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.model.TriageSession;
import com.example.b07project.services.Service;
import com.example.b07project.services.TriageSessionRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TriageSessionViewModel extends ViewModel {
  private final Service service = new Service();
  private final TriageSessionRepository repository = new TriageSessionRepository(service);

  private final MutableLiveData<List<TriageSession>> sessions = new MutableLiveData<>();
  private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

  public LiveData<List<TriageSession>> getSessions() {
    return sessions;
  }

  public LiveData<String> getErrorMessage() {
    return errorMessage;
  }

  public void loadSessions(String childId) {
    repository.getAll(childId, buildListener());
  }

  public void observeSessions(String childId) {
    repository.observeAll(childId, buildListener());
  }

  public void addSession(String childId, TriageSession session) {
    repository.add(childId, session);
    loadSessions(childId);
  }

  public String addSessionAndReturnId(String childId, TriageSession session) {
    String id = repository.addAndReturnId(childId, session);
    loadSessions(childId);
    return id;
  }

  public void updateSession(String childId, String sessionId, Map<String, Object> updates) {
    repository.update(childId, sessionId, updates);
    loadSessions(childId);
  }

  public void deleteSession(String childId, String sessionId) {
    repository.delete(childId, sessionId);
    loadSessions(childId);
  }

  private ValueEventListener buildListener() {
    return new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        List<TriageSession> data = new ArrayList<>();
        for (DataSnapshot child : snapshot.getChildren()) {
          TriageSession session = child.getValue(TriageSession.class);
          if (session != null) {
            if (session.getSessionId() == null) {
              session.setSessionId(child.getKey());
            }
            data.add(session);
          }
        }
        sessions.postValue(data);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        errorMessage.postValue("Failed to load triage sessions");
      }
    };
  }
}
