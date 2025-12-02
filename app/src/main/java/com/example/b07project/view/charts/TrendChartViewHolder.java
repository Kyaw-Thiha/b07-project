package com.example.b07project.view.charts;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.b07project.databinding.ItemTrendChartBinding;
import com.example.b07project.view.charts.render.ChartRenderContext;
import com.example.b07project.view.charts.strategy.TrendStrategy;
import com.example.b07project.view.charts.util.DateRange;
import java.util.Collections;
import java.util.List;

class TrendChartViewHolder extends RecyclerView.ViewHolder {

  private final ItemTrendChartBinding binding;
  private final ChartRenderContext renderContext;
  private final TrendChartAdapter.RangeListener rangeListener;
  private TrendStrategy boundStrategy;
  private boolean suppressRangeCallback;

  TrendChartViewHolder(@NonNull ItemTrendChartBinding binding,
      TrendChartAdapter.RangeListener listener) {
    super(binding.getRoot());
    this.binding = binding;
    this.rangeListener = listener;
    this.renderContext = new ChartRenderContext(binding);

    binding.rangeToggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
      if (suppressRangeCallback || !isChecked || boundStrategy == null) {
        return;
      }
      DateRange range = checkedId == binding.range30.getId()
          ? DateRange.thirtyDays()
          : DateRange.sevenDays();
      rangeListener.onRangeSelected(boundStrategy.id(), range);
    });
  }

  void bind(TrendStrategy strategy, List<TrendPoint> points) {
    boundStrategy = strategy;
    binding.chartTitle.setText(strategy.title());
    suppressRangeCallback = true;
    binding.rangeToggle.check(binding.range7.getId());
    suppressRangeCallback = false;
    List<TrendPoint> safePoints = points != null ? points : Collections.emptyList();
    strategy.render(safePoints, renderContext);
  }
}
