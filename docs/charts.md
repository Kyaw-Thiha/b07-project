# Charts Usage

Asthma trends live under `view/charts`. Each chart is a Fragment that extends `BaseTrendFragment`, which internally wires a `TrendViewModel`, a `RecyclerView`, and a list of `TrendStrategy` objects. You only need to embed the fragment, feed it a `TrendInput`, and it will handle date-range toggles plus chart rendering.

## Available Fragments & Strategies
| Fragment | Strategy | What it shows |
|---|---|---|
| `RescueUsageTrendFragment` | `RescueUsageStrategy` | Line chart of rescue uses over time. |
| `ControllerAdherenceTrendFragment` | `ControllerAdherenceStrategy` | Line chart comparing scheduled vs taken controller doses. |
| `ZoneDistributionFragment` | `ZoneDistributionStrategy` | Pie chart of green/yellow/red zone counts. |
| `ZoneTrendFragment` | `ZoneTrendStrategy` | Temporal zone changes (line chart). |
| `PefTrendFragment` | `PefTrendStrategy` | Line chart of pre/post-med peak-flow values. |

Each `TrendStrategy` provides an `id()`, title (`@StringRes`), a `compute(TrendInput, DateRange)` function, and a `render(...)` implementation that uses `ChartRenderContext` to pick the right MPAndroidChart widget (`LineChart`, `BarChart`, `PieChart`).

## Embedding a Chart Fragment
Drop a `FragmentContainerView` into any layout and point it at one of the fragments above.

```xml
<androidx.fragment.app.FragmentContainerView
    android:id="@+id/chart_rescue_usage"
    android:layout_width="match_parent"
    android:layout_height="320dp"
    android:name="com.example.b07project.view.charts.fragments.RescueUsageTrendFragment" />
```

`BaseTrendFragment` inflates `res/layout/fragment_trend_chart.xml`, which hosts a `RecyclerView`. For most fragments the list contains a single strategy card, but you can return multiple strategies from `provideStrategies()` to stack charts vertically.

## Supplying Data (`TrendInput`)
`TrendInput` is a simple value object that bundles the raw datasets:

```java
TrendInput input = new TrendInput(medicineLogs, pefLogs, checkIns);
```

Each fragment exposes `setTrendInput(TrendInput input)`. Call it after the fragment is attached (e.g., in an Activity once ViewBinding is ready), and call it again any time the logs change.

```java
private void attachCharts(TrendInput input) {
    setTrendInput(binding.chartRescueUsage.getId(), input);
    setTrendInput(binding.chartControllerAdherence.getId(), input);
    setTrendInput(binding.chartZoneDistribution.getId(), input);
    setTrendInput(binding.chartPefTrend.getId(), input);
}

private void setTrendInput(@IdRes int containerId, TrendInput input) {
    Fragment fragment = getSupportFragmentManager().findFragmentById(containerId);
    if (fragment instanceof BaseTrendFragment) {
        ((BaseTrendFragment) fragment).setTrendInput(input);
    }
}
```

When chart fragments are hosted inside another fragment, use `getChildFragmentManager()` to locate the inner fragment before calling `setTrendInput`.

## Date Ranges & Re-rendering
`TrendChartAdapter` renders each strategy using `item_trend_chart.xml`. Every card exposes a 7-day / 30-day toggle; when the user flips it, the adapter calls `TrendViewModel.setRange(strategyId, DateRange)` and the LiveData (`getTrendSeries()`) emits a recomputed data set. You do not need to manage this manually—just make sure you're feeding fresh `TrendInput` objects when the underlying logs change.

## Creating a New Chart
1. Create a new `TrendStrategy` implementation (`id`, `title`, `compute`, `render`).
2. Create a fragment that extends `BaseTrendFragment` and returns your strategy (or a list of strategies) from `provideStrategies()`.
3. Add a `FragmentContainerView` to the desired layout and drop the fragment name in the `android:name` attribute.
4. Feed it data via `setTrendInput`.

Because the rendering context uses MPAndroidChart under the hood, you get the same styling helpers for free (see `ChartBindingUtils`).

## Direct MPAndroidChart Helpers
For ad-hoc charts outside the trend system (e.g., the sparkline on `ParentDashboardActivity`), use `ChartBindingUtils`:

```java
ChartBindingUtils.styleLineChart(rescueTrendChart);
rescueTrendChart.setData(ChartBindingUtils.toLineData(points));
rescueTrendChart.invalidate();
```

`ChartBindingUtils` also includes `styleBarChart`, `stylePieChart`, and helper methods to convert `List<TrendPoint>` into the appropriate MPAndroidChart `Data` object. That keeps the look-and-feel consistent whether you are inside a fragment or binding a one-off chart in an Activity.
