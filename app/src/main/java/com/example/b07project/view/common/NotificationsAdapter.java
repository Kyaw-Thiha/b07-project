package com.example.b07project.view.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.model.Notification;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationsAdapter extends ListAdapter<Notification, NotificationsAdapter.ViewHolder> {

    public interface NotificationActionListener {
        void onMarkRead(Notification notification);
        void onDelete(Notification notification);
    }

    private final NotificationActionListener listener;

    public NotificationsAdapter(NotificationActionListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView body;
        private final TextView timestamp;
        private final ImageButton markRead;
        private final ImageButton delete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textNotificationTitle);
            body = itemView.findViewById(R.id.textNotificationBody);
            timestamp = itemView.findViewById(R.id.textNotificationTime);
            markRead = itemView.findViewById(R.id.buttonMarkRead);
            delete = itemView.findViewById(R.id.buttonDelete);
        }

        void bind(Notification notification) {
            title.setText(notification.getTitle());
            body.setText(notification.getText());
            long created = notification.getCreatedAt();
            String formatted = DateFormat.getDateTimeInstance(
                    DateFormat.SHORT,
                    DateFormat.SHORT,
                    Locale.getDefault())
                    .format(new Date(created));
            timestamp.setText(formatted);

            markRead.setEnabled(notification.getStatus() == null || !"read".equalsIgnoreCase(notification.getStatus()));
            markRead.setOnClickListener(v -> listener.onMarkRead(notification));
            delete.setOnClickListener(v -> listener.onDelete(notification));
        }
    }

    private static final DiffUtil.ItemCallback<Notification> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Notification>() {
                @Override
                public boolean areItemsTheSame(@NonNull Notification oldItem, @NonNull Notification newItem) {
                    return safeEquals(oldItem.getNotificationId(), newItem.getNotificationId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Notification oldItem, @NonNull Notification newItem) {
                    return safeEquals(oldItem.getTitle(), newItem.getTitle())
                            && safeEquals(oldItem.getText(), newItem.getText())
                            && oldItem.getCreatedAt() == newItem.getCreatedAt()
                            && safeEquals(oldItem.getStatus(), newItem.getStatus());
                }

                private boolean safeEquals(Object a, Object b) {
                    return a == b || (a != null && a.equals(b));
                }
            };
}
