package com.example.b07project.view.charts.fragments;

import com.example.b07project.view.charts.strategy.TrendStrategy;
import com.example.b07project.view.charts.strategy.ZoneDistributionStrategy;
import java.util.Collections;
import java.util.List;

public class ZoneDistributionFragment extends BaseTrendFragment {
  @Override
  protected List<TrendStrategy> provideStrategies() {
    return Collections.singletonList(new ZoneDistributionStrategy());
  }
}
