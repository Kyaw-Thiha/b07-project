package com.example.b07project.view.provider;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.b07project.databinding.ItemTriageIncidentBinding;
import com.example.b07project.model.Incident;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class TriageIncidentAdapter extends ListAdapter<Incident, TriageIncidentAdapter.ViewHolder> {

  public TriageIncidentAdapter() {
    super(DIFF_CALLBACK);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemTriageIncidentBinding binding = ItemTriageIncidentBinding.inflate(
        LayoutInflater.from(parent.getContext()), parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    private final ItemTriageIncidentBinding binding;
    private final DateFormat dateFormat = DateFormat.getDateTimeInstance(
        DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault());

    ViewHolder(ItemTriageIncidentBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void bind(Incident incident) {
      if (incident == null) {
        return;
      }
      binding.incidentTime.setText(dateFormat.format(new Date(incident.getTime())));
      binding.incidentDecision.setText(
          binding.getRoot().getContext().getString(
              com.example.b07project.R.string.report_triage_decision,
              safe(incident.getDecision())));

      binding.incidentGuidance.setText(
          binding.getRoot().getContext().getString(
              com.example.b07project.R.string.report_triage_guidance,
              safe(incident.getGuidance())));

      binding.incidentFlags.setText(
          binding.getRoot().getContext().getString(
              com.example.b07project.R.string.report_triage_flags,
              formatFlags(incident.getFlags())));
    }

    private String safe(String value) {
      return value == null || value.isEmpty() ? "-" : value;
    }

    private String formatFlags(Incident.Flags flags) {
      if (flags == null) {
        return "-";
      }
      StringBuilder sb = new StringBuilder();
      appendFlag(sb, flags.isCantSpeakFullSentences(), "can't speak");
      appendFlag(sb, flags.isChestPulling(), "chest pulling");
      appendFlag(sb, flags.isBlueLips(), "blue lips");
      appendFlag(sb, flags.isSevereWheeze(), "severe wheeze");
      appendFlag(sb, flags.isUnableToLieFlat(), "can't lie flat");
      return sb.length() == 0 ? "-" : sb.toString();
    }

    private void appendFlag(StringBuilder sb, boolean condition, String label) {
      if (condition) {
        if (sb.length() > 0) {
          sb.append(", ");
        }
        sb.append(label);
      }
    }
  }

  private static final DiffUtil.ItemCallback<Incident> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<Incident>() {
        @Override
        public boolean areItemsTheSame(@NonNull Incident oldItem, @NonNull Incident newItem) {
          return safeEquals(oldItem.getUid(), newItem.getUid());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Incident oldItem, @NonNull Incident newItem) {
          return safeEquals(oldItem.getDecision(), newItem.getDecision())
              && safeEquals(oldItem.getGuidance(), newItem.getGuidance())
              && oldItem.getTime() == newItem.getTime();
        }

        private boolean safeEquals(String a, String b) {
          if (a == null) {
            return b == null;
          }
          return a.equals(b);
        }
      };
}
