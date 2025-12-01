package com.example.b07project.view.charts;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.b07project.databinding.ItemTrendChartBinding;
import com.example.b07project.view.charts.strategy.TrendStrategy;
import com.example.b07project.view.charts.util.DateRange;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrendChartAdapter extends RecyclerView.Adapter<TrendChartViewHolder> {

  public interface RangeListener {
    void onRangeSelected(String strategyId, DateRange range);
  }

  private final List<TrendStrategy> strategies = new ArrayList<>();
  private final Map<String, List<TrendPoint>> series = new HashMap<>();
  private final RangeListener rangeListener;

  public TrendChartAdapter(RangeListener rangeListener) {
    this.rangeListener = rangeListener;
  }

  public void submitStrategies(List<TrendStrategy> items) {
    strategies.clear();
    if (items != null) {
      strategies.addAll(items);
    }
    notifyDataSetChanged();
  }

  public void submitSeries(Map<String, List<TrendPoint>> data) {
    series.clear();
    if (data != null) {
      series.putAll(data);
    }
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public TrendChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemTrendChartBinding binding =
        ItemTrendChartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    return new TrendChartViewHolder(binding, rangeListener);
  }

  @Override
  public void onBindViewHolder(@NonNull TrendChartViewHolder holder, int position) {
    TrendStrategy strategy = strategies.get(position);
    List<TrendPoint> points = series.get(strategy.id());
    holder.bind(strategy, points);
  }

  @Override
  public int getItemCount() {
    return strategies.size();
  }
}
