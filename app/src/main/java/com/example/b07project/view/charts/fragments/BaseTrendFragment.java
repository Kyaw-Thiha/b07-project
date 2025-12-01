package com.example.b07project.view.charts.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.b07project.databinding.FragmentTrendChartBinding;
import com.example.b07project.view.charts.TrendChartAdapter;
import com.example.b07project.view.charts.TrendInput;
import com.example.b07project.view.charts.strategy.TrendStrategy;
import com.example.b07project.viewModel.TrendViewModel;
import java.util.List;

public abstract class BaseTrendFragment extends Fragment {

  private FragmentTrendChartBinding binding;
  private TrendViewModel viewModel;
  private List<TrendStrategy> strategies;
  private TrendInput pendingInput = TrendInput.empty();

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentTrendChartBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(this).get(TrendViewModel.class);
    strategies = provideStrategies();
    TrendChartAdapter adapter = new TrendChartAdapter(
        (strategyId, range) -> viewModel.setRange(strategyId, range));
    adapter.submitStrategies(strategies);
    binding.chartRecycler.setAdapter(adapter);

    viewModel.setStrategies(strategies);
    viewModel.updateInput(pendingInput);
    viewModel.getTrendSeries().observe(getViewLifecycleOwner(), adapter::submitSeries);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  protected abstract List<TrendStrategy> provideStrategies();

  public void setTrendInput(TrendInput input) {
    pendingInput = input != null ? input : TrendInput.empty();
    if (viewModel != null) {
      viewModel.updateInput(pendingInput);
    }
  }
}
