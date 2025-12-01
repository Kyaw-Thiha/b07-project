package com.example.b07project.view.charts.fragments;

import com.example.b07project.view.charts.strategy.RescueUsageStrategy;
import com.example.b07project.view.charts.strategy.TrendStrategy;
import java.util.Collections;
import java.util.List;

public class RescueUsageTrendFragment extends BaseTrendFragment {
  @Override
  protected List<TrendStrategy> provideStrategies() {
    return Collections.singletonList(new RescueUsageStrategy());
  }
}
