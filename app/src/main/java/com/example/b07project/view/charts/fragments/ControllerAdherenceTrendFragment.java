package com.example.b07project.view.charts.fragments;

import com.example.b07project.view.charts.strategy.ControllerAdherenceStrategy;
import com.example.b07project.view.charts.strategy.TrendStrategy;
import java.util.Collections;
import java.util.List;

public class ControllerAdherenceTrendFragment extends BaseTrendFragment {
  @Override
  protected List<TrendStrategy> provideStrategies() {
    return Collections.singletonList(new ControllerAdherenceStrategy());
  }
}
