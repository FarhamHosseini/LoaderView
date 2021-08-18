package com.apachat.loaderview.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.apachat.loaderview.core.interfaces.Loader;

public class TextView extends AppCompatTextView implements Loader {
  private Controller controller;
  private int defaultColorResource;
  private int darkerColorResource;

  public TextView(Context context) {
    super(context);
    init(null);
  }

  public TextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    controller = new Controller(this);
    TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.loader_view, 0, 0);
    controller.setWidthWeight(typedArray.getFloat(R.styleable.loader_view_width_weight, Constants.MAX_WEIGHT));
    controller.setHeightWeight(typedArray.getFloat(R.styleable.loader_view_height_weight, Constants.MAX_WEIGHT));
    controller.setUseGradient(typedArray.getBoolean(R.styleable.loader_view_use_gradient, Constants.USE_GRADIENT_DEFAULT));
    controller.setCorners(typedArray.getInt(R.styleable.loader_view_corners, Constants.CORNER_DEFAULT));
    defaultColorResource = typedArray.getColor(R.styleable.loader_view_custom_color, ContextCompat.getColor(getContext(), R.color.default_color));
    darkerColorResource = typedArray.getColor(R.styleable.loader_view_custom_color, ContextCompat.getColor(getContext(), R.color.darker_color));
    typedArray.recycle();
  }

  @Override
  protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    super.onSizeChanged(width, height, oldWidth, oldHeight);
    controller.onSizeChanged();
  }

  public void resetLoader() {
    if (!TextUtils.isEmpty(getText())) {
      super.setText(null);
      controller.startLoading();
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    controller.onDraw(canvas, getCompoundPaddingLeft(),
      getCompoundPaddingTop(),
      getCompoundPaddingRight(),
      getCompoundPaddingBottom());
  }

  @Override
  public void setText(CharSequence text, BufferType type) {
    super.setText(text, type);
    if (controller != null) {
      controller.stopLoading();
    }
  }

  @Override
  public void setRectColor(Paint rectPaint) {
    final Typeface typeface = getTypeface();
    if (typeface != null && typeface.getStyle() == Typeface.BOLD) {
      rectPaint.setColor(darkerColorResource);
    } else {
      rectPaint.setColor(defaultColorResource);
    }
  }

  @Override
  public boolean valueSet() {
    return !TextUtils.isEmpty(getText());
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    controller.removeAnimatorUpdateListener();
  }
}
