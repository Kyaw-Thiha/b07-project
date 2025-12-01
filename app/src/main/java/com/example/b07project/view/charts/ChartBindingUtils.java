package com.example.b07project.view.charts;

import android.graphics.Color;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import java.util.List;

public final class ChartBindingUtils {
  private ChartBindingUtils() {
  }

  public static LineData toLineData(List<TrendPoint> points) {
    List<Entry> entries = new ArrayList<>();
    if (points != null) {
      for (int i = 0; i < points.size(); i++) {
        TrendPoint point = points.get(i);
        entries.add(new Entry(i, point.getPrimary()));
      }
    }
    LineDataSet dataSet = new LineDataSet(entries, "");
    dataSet.setDrawValues(false);
    dataSet.setCircleRadius(3f);
    dataSet.setColor(Color.parseColor("#6200EE"));
    dataSet.setCircleColor(Color.parseColor("#6200EE"));
    dataSet.setLineWidth(2f);
    return new LineData(dataSet);
  }

  public static BarData toStackedBarData(List<TrendPoint> points) {
    List<BarEntry> entries = new ArrayList<>();
    if (points != null) {
      for (int i = 0; i < points.size(); i++) {
        TrendPoint point = points.get(i);
        float[] values;
        if (point.getSecondary() != null) {
          values = new float[]{point.getPrimary(), point.getSecondary()};
        } else {
          values = new float[]{point.getPrimary()};
        }
        entries.add(new BarEntry(i, values));
      }
    }
    BarDataSet dataSet = new BarDataSet(entries, "");
    dataSet.setDrawValues(false);
    dataSet.setColors(Color.parseColor("#03DAC5"), Color.parseColor("#FFB300"));
    dataSet.setStackLabels(new String[]{"Primary", "Secondary"});
    BarData data = new BarData(dataSet);
    data.setBarWidth(0.8f);
    return data;
  }

  public static PieData toPieData(List<TrendPoint> points) {
    List<PieEntry> entries = new ArrayList<>();
    if (points != null) {
      for (TrendPoint point : points) {
        entries.add(new PieEntry(point.getPrimary(), point.getLabel()));
      }
    }
    PieDataSet dataSet = new PieDataSet(entries, "");
    dataSet.setColors(Color.parseColor("#4CAF50"), Color.parseColor("#FFC107"),
        Color.parseColor("#F44336"));
    dataSet.setValueTextColor(Color.WHITE);
    dataSet.setValueTextSize(12f);
    PieData data = new PieData(dataSet);
    data.setDrawValues(true);
    return data;
  }

  public static void styleLineChart(LineChart chart) {
    chart.getDescription().setEnabled(false);
    chart.setTouchEnabled(false);
    chart.getAxisRight().setEnabled(false);
    chart.getAxisLeft().setDrawGridLines(false);
    XAxis xAxis = chart.getXAxis();
    xAxis.setDrawGridLines(false);
    xAxis.setDrawAxisLine(false);
    xAxis.setGranularity(1f);
    chart.getLegend().setEnabled(false);
  }

  public static void styleBarChart(BarChart chart) {
    chart.getDescription().setEnabled(false);
    chart.setTouchEnabled(false);
    chart.getAxisRight().setEnabled(false);
    chart.getAxisLeft().setDrawGridLines(false);
    chart.getXAxis().setDrawGridLines(false);
    chart.getXAxis().setDrawAxisLine(false);
    chart.getLegend().setEnabled(false);
  }

  public static void stylePieChart(PieChart chart) {
    chart.getDescription().setEnabled(false);
    chart.setUsePercentValues(true);
    chart.setDrawEntryLabels(false);
    Legend legend = chart.getLegend();
    legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
    legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
  }
}
