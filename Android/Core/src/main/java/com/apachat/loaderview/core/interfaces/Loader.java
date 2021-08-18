package com.apachat.loaderview.core.interfaces;

import android.graphics.Paint;

public interface Loader {
  void setRectColor(Paint rectPaint);

  void invalidate();

  boolean valueSet();
}
