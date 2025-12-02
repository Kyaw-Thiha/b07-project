package com.example.b07project.view.parent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.b07project.R;
import com.example.b07project.model.User.ChildUser;
import com.example.b07project.view.common.BackButtonActivity;
import com.example.b07project.viewModel.ChildProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;

public class SelectChildActivity extends BackButtonActivity
    implements SelectChildAdapter.ChildSelectionListener {

  public static final String RESULT_CHILD_ID = "result_child_id";

  private RecyclerView childList;
  private TextView emptyView;
  private ProgressBar loading;
  private SelectChildAdapter adapter;
  private ChildProfileViewModel childProfileViewModel;
  private final List<ChildUser> children = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_select_child);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    childList = findViewById(R.id.childList);
    emptyView = findViewById(R.id.childEmptyView);
    loading = findViewById(R.id.childLoading);

    adapter = new SelectChildAdapter(children, this);
    childList.setLayoutManager(new LinearLayoutManager(this));
    childList.setAdapter(adapter);

    setupViewModel();
  }

  private void setupViewModel() {
    childProfileViewModel = new ViewModelProvider(this).get(ChildProfileViewModel.class);
    childProfileViewModel.getChildren().observe(this, list -> {
      loading.setVisibility(View.GONE);
      children.clear();
      if (list != null) {
        children.addAll(list);
      }
      adapter.notifyDataSetChanged();
      emptyView.setVisibility(children.isEmpty() ? View.VISIBLE : View.GONE);
      childList.setVisibility(children.isEmpty() ? View.GONE : View.VISIBLE);
    });
    loading.setVisibility(View.VISIBLE);
    String parentId = FirebaseAuth.getInstance().getCurrentUser() != null
        ? FirebaseAuth.getInstance().getCurrentUser().getUid()
        : null;
    if (!TextUtils.isEmpty(parentId)) {
      childProfileViewModel.observeChildrenForParent(parentId);
    } else {
      loading.setVisibility(View.GONE);
      emptyView.setVisibility(View.VISIBLE);
      emptyView.setText(R.string.no_child_selected);
    }
  }

  @Override
  public void onChildSelected(ChildUser child) {
    if (child == null || child.getUid() == null) {
      setResult(Activity.RESULT_CANCELED);
      finish();
      return;
    }
    Intent data = new Intent();
    data.putExtra(RESULT_CHILD_ID, child.getUid());
    setResult(Activity.RESULT_OK, data);
    finish();
  }
}
