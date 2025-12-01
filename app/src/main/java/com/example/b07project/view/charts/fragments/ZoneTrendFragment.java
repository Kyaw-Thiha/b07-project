package com.example.b07project.view.charts.fragments;

import com.example.b07project.view.charts.strategy.TrendStrategy;
import com.example.b07project.view.charts.strategy.ZoneTrendStrategy;
import java.util.Collections;
import java.util.List;

public class ZoneTrendFragment extends BaseTrendFragment {
  @Override
  protected List<TrendStrategy> provideStrategies() {
    return Collections.singletonList(new ZoneTrendStrategy());
  }
}
