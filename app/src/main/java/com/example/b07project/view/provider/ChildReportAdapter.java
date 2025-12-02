package com.example.b07project.view.provider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.model.Report;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChildReportAdapter extends RecyclerView.Adapter<ChildReportAdapter.ViewHolder> {

    public interface OnReportClickListener {
        void onReportClick(Report report);
    }

    private final List<Report> reports = new ArrayList<>();
    private final DateFormat dateFormat =
            DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
    private OnReportClickListener listener;

    public void setOnReportClickListener(OnReportClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Report> data) {
        reports.clear();
        if (data != null) {
            reports.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_child_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report report = reports.get(position);
        holder.bind(report);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onReportClick(report);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateRange;
        private final TextView parentView;
        private final TextView summaryView;
        private final DateFormat dateFormat =
                DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateRange = itemView.findViewById(R.id.item_report_date_range);
            parentView = itemView.findViewById(R.id.item_report_parent);
            summaryView = itemView.findViewById(R.id.item_report_summary);
        }

        void bind(Report report) {
            if (report == null) {
                dateRange.setText("-");
                parentView.setText("-");
                summaryView.setText("-");
                return;
            }
            dateRange.setText(formatDateRange(report.getStartDate(), report.getEndDate()));
            parentView.setText(itemView.getContext().getString(
                    R.string.child_report_parent_format,
                    report.getParentName() != null ? report.getParentName() : "Unknown"));
            summaryView.setText(buildSummaryLine(report.getSummary()));
        }

        private String formatDateRange(long start, long end) {
            return dateFormat.format(new Date(start)) + " – " + dateFormat.format(new Date(end));
        }

        private String buildSummaryLine(Report.Summary summary) {
            if (summary == null) {
                return itemView.getContext().getString(R.string.child_report_summary_placeholder);
            }
            int rescue = summary.getRescueCount();
            int adherence = (int) Math.round(summary.getControllerAdherencePercent());
            int symptoms = 0;
            if (summary.getSymptomBurden() != null) {
                for (Integer value : summary.getSymptomBurden().values()) {
                    symptoms += value != null ? value : 0;
                }
            }
            return itemView.getContext().getString(
                    R.string.child_report_summary_format,
                    rescue,
                    adherence,
                    symptoms
            );
        }
    }
}
