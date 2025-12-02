package com.example.b07project.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.text.TextUtils;
import com.example.b07project.model.Report;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public final class ReportExportUtils {

  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private static final DateFormat dateFormat =
      DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());

  private ReportExportUtils() {}

  public static File writeJson(Context context, Report report) throws IOException {
    File dir = getExportDir(context);
    File file = new File(dir, buildFileName(report, "json"));
    try (FileWriter writer = new FileWriter(file, false)) {
      writer.write(gson.toJson(report));
    }
    return file;
  }

  public static File writePdf(Context context, Report report) throws IOException {
    File dir = getExportDir(context);
    File file = new File(dir, buildFileName(report, "pdf"));

    PdfDocument document = new PdfDocument();
    PdfDocument.PageInfo pageInfo =
        new PdfDocument.PageInfo.Builder(595, 842, 1).create();
    PdfDocument.Page page = document.startPage(pageInfo);
    Canvas canvas = page.getCanvas();

    Paint titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    titlePaint.setTextSize(18f);
    titlePaint.setFakeBoldText(true);

    Paint bodyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    bodyPaint.setTextSize(14f);

    int margin = 40;
    int y = 60;
    y = drawLine(canvas, titlePaint, "Child Report", margin, y);
    y += 10;
    y = drawLine(canvas, bodyPaint,
        "Child: " + safe(report.getChildName()) + "  Parent: " + safe(report.getParentName()), margin, y);
    y = drawLine(canvas, bodyPaint,
        "Date Range: " + formatDateRange(report.getStartDate(), report.getEndDate()), margin, y);

    Report.Summary summary = report.getSummary();
    if (summary != null) {
      y += 20;
      y = drawLine(canvas, titlePaint, "Summary", margin, y);
      y += 10;
      y = drawLine(canvas, bodyPaint,
          "Rescue Days: " + summary.getRescueCount(), margin, y);
      y = drawLine(canvas, bodyPaint,
          "Controller Adherence: " + Math.round(summary.getControllerAdherencePercent()) + "%", margin, y);
      y = drawLine(canvas, bodyPaint,
          "Symptom Days: " + summarizeMap(summary.getSymptomBurden()), margin, y);
      y = drawLine(canvas, bodyPaint,
          "Zones: " + summarizeMap(summary.getZoneDistribution()), margin, y);
    }

    document.finishPage(page);
    try (FileOutputStream out = new FileOutputStream(file)) {
      document.writeTo(out);
    } finally {
      document.close();
    }
    return file;
  }

  private static int drawLine(Canvas canvas, Paint paint, String text, int x, int y) {
    canvas.drawText(text, x, y, paint);
    return y + 24;
  }

  private static String summarizeMap(java.util.Map<String, Integer> map) {
    if (map == null || map.isEmpty()) {
      return "-";
    }
    StringBuilder builder = new StringBuilder();
    for (java.util.Map.Entry<String, Integer> entry : map.entrySet()) {
      if (builder.length() > 0) {
        builder.append(", ");
      }
      builder.append(entry.getKey()).append(": ").append(entry.getValue());
    }
    return builder.toString();
  }

  private static String formatDateRange(long start, long end) {
    if (start <= 0 && end <= 0) {
      return "-";
    }
    String startText = start > 0 ? dateFormat.format(new Date(start)) : "-";
    String endText = end > 0 ? dateFormat.format(new Date(end)) : "-";
    return startText + " – " + endText;
  }

  private static File getExportDir(Context context) {
    File dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
    if (dir == null) {
      dir = context.getFilesDir();
    }
    if (!dir.exists()) {
      dir.mkdirs();
    }
    return dir;
  }

  private static String buildFileName(Report report, String ext) {
    String id = !TextUtils.isEmpty(report.getUid())
        ? report.getUid()
        : String.valueOf(System.currentTimeMillis());
    return "report_" + id + "." + ext;
  }

  private static String safe(String value) {
    return TextUtils.isEmpty(value) ? "-" : value;
  }
}
