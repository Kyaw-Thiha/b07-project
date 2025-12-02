package com.example.b07project.view.parent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.model.Medicine;

import java.util.ArrayList;
import java.util.List;

public class ParentInventoryAdapter extends RecyclerView.Adapter<ParentInventoryAdapter.InventoryViewHolder> {

    private final List<Medicine> items = new ArrayList<>();

    public void submitList(List<Medicine> medicines) {
        items.clear();
        if (medicines != null) {
            items.addAll(medicines);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_parent_inventory, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        Medicine medicine = items.get(position);
        holder.bind(medicine);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class InventoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView typeView;
        private final TextView purchaseView;
        private final TextView expiryView;
        private final TextView puffsView;

        InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.inventory_item_name);
            typeView = itemView.findViewById(R.id.inventory_item_type);
            purchaseView = itemView.findViewById(R.id.inventory_item_purchase);
            expiryView = itemView.findViewById(R.id.inventory_item_expiry);
            puffsView = itemView.findViewById(R.id.inventory_item_puffs);
        }

        void bind(Medicine medicine) {
            if (medicine == null) {
                nameView.setText("-");
                typeView.setText("-");
                purchaseView.setText("-");
                expiryView.setText("-");
                puffsView.setText("-");
                return;
            }
            nameView.setText(medicine.getName() != null ? medicine.getName() : "-");
            String type = medicine.getType() != null ? medicine.getType() : "unknown";
            typeView.setText(itemView.getContext().getString(R.string.inventory_item_type_format, type));
            purchaseView.setText(itemView.getContext().getString(
                    R.string.inventory_item_purchase_format,
                    medicine.getPurchase_date() != null ? medicine.getPurchase_date() : "-"));
            expiryView.setText(itemView.getContext().getString(
                    R.string.inventory_item_expiry_format,
                    medicine.getExpiry_date() != null ? medicine.getExpiry_date() : "-"));
            puffsView.setText(itemView.getContext().getString(
                    R.string.inventory_item_puffs_format,
                    medicine.getCanister_puffs()));
        }
    }
}
