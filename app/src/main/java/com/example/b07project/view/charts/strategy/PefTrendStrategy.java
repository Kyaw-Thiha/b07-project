package com.example.b07project.view.charts.strategy;

import com.example.b07project.R;
import com.example.b07project.model.PEF;
import com.example.b07project.view.charts.ChartBindingUtils;
import com.example.b07project.view.charts.TrendInput;
import com.example.b07project.view.charts.TrendPoint;
import com.example.b07project.view.charts.render.ChartRenderContext;
import com.example.b07project.view.charts.util.DateRange;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PefTrendStrategy implements TrendStrategy {
  @Override
  public String id() {
    return "pef_trend";
  }

  @Override
  public int title() {
    return R.string.chart_pef_trend;
  }

  @Override
  public List<TrendPoint> compute(TrendInput input, DateRange range) {
    if (input == null) {
      return Collections.emptyList();
    }
    List<TrendPoint> points = new ArrayList<>();
    for (PEF pef : input.getPefLogs()) {
      if (!range.contains(pef.getTime())) {
        continue;
      }
      float value = pef.getPost_med() > 0 ? pef.getPost_med() : pef.getPre_med();
      points.add(new TrendPoint(pef.getTime(), value));
    }
    points.sort(Comparator.comparingLong(TrendPoint::getTimestamp));
    return points;
  }

  @Override
  public void render(List<TrendPoint> points, ChartRenderContext context) {
    context.showLineChart(ChartBindingUtils.toLineData(points));
  }
}
