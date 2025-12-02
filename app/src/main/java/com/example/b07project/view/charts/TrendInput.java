package com.example.b07project.view.charts;

import com.example.b07project.model.CheckIn;
import com.example.b07project.model.MedicineLog;
import com.example.b07project.model.PEF;
import java.util.Collections;
import java.util.List;

public final class TrendInput {
  private final List<MedicineLog> medicineLogs;
  private final List<PEF> pefLogs;
  private final List<CheckIn> checkIns;

  public TrendInput(List<MedicineLog> medicineLogs,
      List<PEF> pefLogs,
      List<CheckIn> checkIns) {
    this.medicineLogs = medicineLogs != null ? medicineLogs : Collections.emptyList();
    this.pefLogs = pefLogs != null ? pefLogs : Collections.emptyList();
    this.checkIns = checkIns != null ? checkIns : Collections.emptyList();
  }

  public static TrendInput empty() {
    return new TrendInput(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
  }

  public List<MedicineLog> getMedicineLogs() {
    return medicineLogs;
  }

  public List<PEF> getPefLogs() {
    return pefLogs;
  }

  public List<CheckIn> getCheckIns() {
    return checkIns;
  }
}
