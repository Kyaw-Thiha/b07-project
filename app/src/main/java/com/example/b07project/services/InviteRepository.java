package com.example.b07project.services;

import com.example.b07project.model.Invite;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class InviteRepository {
  private final Service service;

  public InviteRepository(Service service) {
    this.service = service;
  }

  public void saveInvite(String parentId, Invite invite, String previousCode,
      DatabaseReference.CompletionListener listener) {
    DatabaseReference parentRef = service.parentInviteDatabase(parentId);
    parentRef.setValue(invite, (error, ref) -> {
      if (error == null) {
        if (previousCode != null && !previousCode.isEmpty()) {
          service.inviteCodeIndex().child(previousCode).removeValue();
        }
        service.inviteCodeIndex().child(invite.getCode()).setValue(invite);
      }
      if (listener != null) {
        listener.onComplete(error, ref);
      }
    });
  }

  public void revokeInvite(String parentId, String currentCode,
      DatabaseReference.CompletionListener listener) {
    DatabaseReference parentRef = service.parentInviteDatabase(parentId);
    parentRef.removeValue((error, ref) -> {
      if (error == null && currentCode != null && !currentCode.isEmpty()) {
        service.inviteCodeIndex().child(currentCode).removeValue();
      }
      if (listener != null) {
        listener.onComplete(error, ref);
      }
    });
  }

  public void getInviteForParent(String parentId, ValueEventListener listener) {
    service.parentInviteDatabase(parentId).addListenerForSingleValueEvent(listener);
  }

  public void observeInviteForParent(String parentId, ValueEventListener listener) {
    service.parentInviteDatabase(parentId).addValueEventListener(listener);
  }

  public void getInviteByCode(String code, ValueEventListener listener) {
    service.inviteCodeIndex().child(code).addListenerForSingleValueEvent(listener);
  }
}
