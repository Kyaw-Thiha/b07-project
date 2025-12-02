package com.example.b07project.view.parent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.b07project.R;
import com.example.b07project.model.User.ChildUser;
import java.util.List;

public class SelectChildAdapter extends RecyclerView.Adapter<SelectChildAdapter.ChildViewHolder> {

  interface ChildSelectionListener {
    void onChildSelected(ChildUser child);
  }

  private final List<ChildUser> children;
  private final ChildSelectionListener listener;

  SelectChildAdapter(List<ChildUser> children, ChildSelectionListener listener) {
    this.children = children;
    this.listener = listener;
  }

  @NonNull
  @Override
  public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_select_child, parent, false);
    return new ChildViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
    ChildUser child = children.get(position);
    holder.bind(child, listener);
  }

  @Override
  public int getItemCount() {
    return children != null ? children.size() : 0;
  }

  static class ChildViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameView;

    ChildViewHolder(@NonNull View itemView) {
      super(itemView);
      nameView = itemView.findViewById(R.id.selectChildName);
    }

    void bind(ChildUser child, ChildSelectionListener listener) {
      nameView.setText(child != null ? child.getName() : "-");
      itemView.setOnClickListener(v -> {
        if (listener != null) {
          listener.onChildSelected(child);
        }
      });
    }
  }
}
