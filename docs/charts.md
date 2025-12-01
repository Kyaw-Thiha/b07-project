# Charts Usage

## Embedding chart fragments
Each chart lives in its own fragment under `com.example.b07project.view.charts.fragments`. Drop the fragment into any layout via `FragmentContainerView`:

```xml
<androidx.fragment.app.FragmentContainerView
    android:id="@+id/rescue_chart"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    tools:name="com.example.b07project.view.charts.fragments.RescueUsageTrendFragment" />
```

Available fragments:
- `RescueUsageTrendFragment`
- `ControllerAdherenceTrendFragment`
- `ZoneDistributionFragment`
- `ZoneTrendFragment`
- `PefTrendFragment`

## Supplying data from an Activity
Fragments expose `setTrendInput(TrendInput input)`. Build a `TrendInput` with logs you already fetched (medicine logs, PEF entries, check-ins) and pass it once the fragment is attached:

```java
TrendInput trendInput = new TrendInput(medicineLogs, pefLogs, checkIns);
RescueUsageTrendFragment fragment = (RescueUsageTrendFragment) getSupportFragmentManager().findFragmentById(R.id.rescue_chart);
if (fragment != null) {
    fragment.setTrendInput(trendInput);
}
```

Call `setTrendInput` again whenever the underlying data changes; the fragment’s internal `TrendViewModel` recomputes and the chart updates automatically.

## Updating from a parent Fragment
When hosting charts inside another fragment, look up children via `getChildFragmentManager()`:

```java
TrendInput input = new TrendInput(medicineLogs, pefLogs, checkIns);
ZoneDistributionFragment zoneFragment = (ZoneDistributionFragment) getChildFragmentManager().findFragmentById(R.id.zone_chart);
if (zoneFragment != null) {
    zoneFragment.setTrendInput(input);
}
```

## Notes
- `TrendInput` accepts `List<MedicineLog>`, `List<PEF>`, and `List<CheckIn>`; pass empty lists if certain data is unavailable.
- Each chart fragment can host multiple charts if you override `provideStrategies()` to return more than one strategy.
- The 7-day / 30-day toggle emits range changes automatically; no extra wiring is required beyond supplying the input data.
