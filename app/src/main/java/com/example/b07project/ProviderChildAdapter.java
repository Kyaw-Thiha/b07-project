package com.example.b07project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.model.ProviderChild;

import java.util.List;

public class ProviderChildAdapter extends RecyclerView.Adapter<ProviderChildAdapter.ChildViewHolder> {

    private final Context context;
    private final List<ProviderChild> children;

    public ProviderChildAdapter(Context context, List<ProviderChild> children) {
        this.context = context;
        this.children = children;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // use child_item.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_item, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        ProviderChild child = children.get(position);

        int displayIndex = position + 1;
        holder.index.setText(String.valueOf(displayIndex));

        // add ProviderChild to TextView
        holder.name.setText(child.getName() + " (" + child.getAge() + ")");
        holder.parent.setText("Parent: " + child.getParentName());
        holder.todayZone.setText("Today zone: " + child.getTodayZone());
        holder.rescue7d.setText("Rescue (7d): " + child.getRescue7d());
        holder.adherence.setText("Controller adherence (30d): "
                + child.getControllerAdherence() + "%");
        holder.lastUpdated.setText("Last updated: " + child.getLastUpdated());

        // click name -> jump to report
        holder.name.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChildReportActivity.class);
            intent.putExtra("childId", child.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return children.size();
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView index;
        TextView name;
        TextView parent;
        TextView todayZone;
        TextView rescue7d;
        TextView adherence;
        TextView lastUpdated;

        ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.textChildIndex);
            name = itemView.findViewById(R.id.textChildName);
            parent = itemView.findViewById(R.id.textParent);        // ← 关键是这里
            todayZone = itemView.findViewById(R.id.textTodayZone);
            rescue7d = itemView.findViewById(R.id.textRescue7d);
            adherence = itemView.findViewById(R.id.textAdherence);
            lastUpdated = itemView.findViewById(R.id.textLastUpdated);
        }
    }
}

