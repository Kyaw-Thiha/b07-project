package com.example.b07project.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.b07project.view.charts.TrendInput;
import com.example.b07project.view.charts.TrendPoint;
import com.example.b07project.view.charts.strategy.TrendStrategy;
import com.example.b07project.view.charts.util.DateRange;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrendViewModel extends ViewModel {

  private final MutableLiveData<Map<String, List<TrendPoint>>> series = new MutableLiveData<>();
  private final Map<String, DateRange> ranges = new HashMap<>();
  private List<TrendStrategy> strategies = Collections.emptyList();
  private TrendInput input = TrendInput.empty();

  public TrendViewModel() {
    series.setValue(Collections.emptyMap());
  }

  public LiveData<Map<String, List<TrendPoint>>> getTrendSeries() {
    return series;
  }

  public void setStrategies(List<TrendStrategy> definitions) {
    if (definitions == null) {
      this.strategies = Collections.emptyList();
    } else {
      this.strategies = definitions;
    }
    recompute();
  }

  public void setRange(String chartId, DateRange range) {
    ranges.put(chartId, range);
    recompute();
  }

  public void updateInput(TrendInput input) {
    this.input = input != null ? input : TrendInput.empty();
    recompute();
  }

  private void recompute() {
    if (strategies.isEmpty()) {
      series.setValue(Collections.emptyMap());
      return;
    }
    Map<String, List<TrendPoint>> computed = new HashMap<>();
    for (TrendStrategy strategy : strategies) {
      DateRange range = ranges.getOrDefault(strategy.id(), DateRange.sevenDays());
      computed.put(strategy.id(), strategy.compute(input, range));
    }
    series.setValue(computed);
  }
}
