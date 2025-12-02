package com.example.b07project.view.charts.render;

import android.view.View;
import com.example.b07project.databinding.ItemTrendChartBinding;
import com.example.b07project.view.charts.ChartBindingUtils;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.PieData;

public final class ChartRenderContext {
  private final ItemTrendChartBinding binding;

  public ChartRenderContext(ItemTrendChartBinding binding) {
    this.binding = binding;
    ChartBindingUtils.styleLineChart(binding.lineChart);
    ChartBindingUtils.styleBarChart(binding.barChart);
    ChartBindingUtils.stylePieChart(binding.pieChart);
  }

  public void showLineChart(LineData data) {
    binding.lineChart.setVisibility(View.VISIBLE);
    binding.barChart.setVisibility(View.GONE);
    binding.pieChart.setVisibility(View.GONE);
    binding.lineChart.setData(data);
    binding.lineChart.invalidate();
  }

  public void showBarChart(BarData data) {
    binding.lineChart.setVisibility(View.GONE);
    binding.barChart.setVisibility(View.VISIBLE);
    binding.pieChart.setVisibility(View.GONE);
    binding.barChart.setData(data);
    binding.barChart.invalidate();
  }

  public void showPieChart(PieData data) {
    binding.lineChart.setVisibility(View.GONE);
    binding.barChart.setVisibility(View.GONE);
    binding.pieChart.setVisibility(View.VISIBLE);
    binding.pieChart.setData(data);
    binding.pieChart.invalidate();
  }
}
