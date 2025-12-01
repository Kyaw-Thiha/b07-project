package com.example.b07project.view.charts.fragments;

import com.example.b07project.view.charts.strategy.PefTrendStrategy;
import com.example.b07project.view.charts.strategy.TrendStrategy;
import java.util.Collections;
import java.util.List;

public class PefTrendFragment extends BaseTrendFragment {
  @Override
  protected List<TrendStrategy> provideStrategies() {
    return Collections.singletonList(new PefTrendStrategy());
  }
}
