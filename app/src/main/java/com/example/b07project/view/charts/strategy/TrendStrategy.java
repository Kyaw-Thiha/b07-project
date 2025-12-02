package com.example.b07project.view.charts.strategy;

import androidx.annotation.StringRes;
import com.example.b07project.view.charts.TrendInput;
import com.example.b07project.view.charts.TrendPoint;
import com.example.b07project.view.charts.render.ChartRenderContext;
import com.example.b07project.view.charts.util.DateRange;
import java.util.List;

public interface TrendStrategy {
  String id();
  @StringRes
  int title();
  List<TrendPoint> compute(TrendInput input, DateRange range);
  void render(List<TrendPoint> points, ChartRenderContext context);
}
