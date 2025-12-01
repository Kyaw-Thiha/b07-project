package com.example.b07project.view.charts.strategy;

import com.example.b07project.R;
import com.example.b07project.model.PEF;
import com.example.b07project.view.charts.ChartBindingUtils;
import com.example.b07project.view.charts.TrendInput;
import com.example.b07project.view.charts.TrendPoint;
import com.example.b07project.view.charts.render.ChartRenderContext;
import com.example.b07project.view.charts.util.DateRange;
import com.example.b07project.view.charts.util.ZoneUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ZoneDistributionStrategy implements TrendStrategy {
  @Override
  public String id() {
    return "zone_distribution";
  }

  @Override
  public int title() {
    return R.string.chart_zone_distribution;
  }

  @Override
  public List<TrendPoint> compute(TrendInput input, DateRange range) {
    if (input == null) {
      return Collections.emptyList();
    }
    Map<String, Integer> counts = new TreeMap<>();
    for (PEF pef : input.getPefLogs()) {
      if (!range.contains(pef.getTime())) {
        continue;
      }
      String zone = ZoneUtils.normalize(pef.getZone());
      if (zone == null) {
        continue;
      }
      counts.put(zone, counts.getOrDefault(zone, 0) + 1);
    }
    List<TrendPoint> points = new ArrayList<>();
    counts.forEach((zone, count) -> points.add(new TrendPoint(range.getStartMillis(), count, null, zone)));
    return points;
  }

  @Override
  public void render(List<TrendPoint> points, ChartRenderContext context) {
    context.showPieChart(ChartBindingUtils.toPieData(points));
  }
}
