package com.example.b07project.view.charts.strategy;

import com.example.b07project.R;
import com.example.b07project.model.MedicineLog;
import com.example.b07project.view.charts.ChartBindingUtils;
import com.example.b07project.view.charts.TrendInput;
import com.example.b07project.view.charts.TrendPoint;
import com.example.b07project.view.charts.render.ChartRenderContext;
import com.example.b07project.view.charts.util.DateBuckets;
import com.example.b07project.view.charts.util.DateRange;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ControllerAdherenceStrategy implements TrendStrategy {
  @Override
  public String id() {
    return "controller_adherence";
  }

  @Override
  public int title() {
    return R.string.chart_controller_adherence;
  }

  @Override
  public List<TrendPoint> compute(TrendInput input, DateRange range) {
    if (input == null) {
      return Collections.emptyList();
    }
    Map<Long, Integer> perDay = new TreeMap<>();
    for (MedicineLog log : input.getMedicineLogs()) {
      if (!range.contains(log.getTime())) {
        continue;
      }
      if (!"controller".equalsIgnoreCase(log.getMedicineType())) {
        continue;
      }
      long day = DateBuckets.toDay(log.getTime());
      perDay.put(day, perDay.getOrDefault(day, 0) + log.getDose());
    }
    List<TrendPoint> points = new ArrayList<>();
    perDay.forEach((day, value) -> points.add(new TrendPoint(day, value)));
    return points;
  }

  @Override
  public void render(List<TrendPoint> points, ChartRenderContext context) {
    context.showLineChart(ChartBindingUtils.toLineData(points));
  }
}
