package com.apachat.loaderview.core;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.animation.LinearInterpolator;

import com.apachat.loaderview.core.interfaces.Loader;

public class Controller implements ValueAnimator.AnimatorUpdateListener {
  private Loader loader;
  private Paint rectPaint;
  private LinearGradient linearGradient;
  private float progress;
  private ValueAnimator valueAnimator;
  private float widthWeight = Constants.MAX_WEIGHT;
  private float heightWeight = Constants.MAX_WEIGHT;
  private boolean useGradient = Constants.USE_GRADIENT_DEFAULT;
  private int corners = Constants.CORNER_DEFAULT;

  private final static int MAX_COLOR_CONSTANT_VALUE = 255;
  private final static int ANIMATION_CYCLE_DURATION = 750;

  public Controller(Loader loader) {
    this.loader = loader;
    init();
  }

  private void init() {
    rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    loader.setRectColor(rectPaint);
    setValueAnimator(0.5f, 1, ObjectAnimator.INFINITE);
  }

  public void onDraw(Canvas canvas) {
    onDraw(canvas, 0, 0, 0, 0);
  }

  public void onDraw(Canvas canvas, float left_pad, float top_pad, float right_pad, float bottom_pad) {
    float margin_height = canvas.getHeight() * (1 - heightWeight) / 2;
    rectPaint.setAlpha((int) (progress * MAX_COLOR_CONSTANT_VALUE));
    if (useGradient) {
      prepareGradient(canvas.getWidth() * widthWeight);
    }
    canvas.drawRoundRect(new RectF(0 + left_pad,
        margin_height + top_pad,
        canvas.getWidth() * widthWeight - right_pad,
        canvas.getHeight() - margin_height - bottom_pad),
      corners, corners,
      rectPaint);
  }

  public void onSizeChanged() {
    linearGradient = null;
    startLoading();
  }

  private void prepareGradient(float width) {
    if (linearGradient == null) {
      linearGradient = new LinearGradient(0, 0, width, 0, rectPaint.getColor(),
        Constants.COLOR_DEFAULT_GRADIENT, Shader.TileMode.MIRROR);
    }
    rectPaint.setShader(linearGradient);
  }

  public void startLoading() {
    if (valueAnimator != null && !loader.valueSet()) {
      valueAnimator.cancel();
      init();
      valueAnimator.start();
    }
  }

  public void setHeightWeight(float heightWeight) {
    this.heightWeight = validateWeight(heightWeight);
  }

  public void setWidthWeight(float widthWeight) {
    this.widthWeight = validateWeight(widthWeight);
  }

  public void setUseGradient(boolean useGradient) {
    this.useGradient = useGradient;
  }

  public void setCorners(int corners) {
    this.corners = corners;
  }

  private float validateWeight(float weight) {
    if (weight > Constants.MAX_WEIGHT)
      return Constants.MAX_WEIGHT;
    if (weight < Constants.MIN_WEIGHT)
      return Constants.MIN_WEIGHT;
    return weight;
  }

  public void stopLoading() {
    if (valueAnimator != null) {
      valueAnimator.cancel();
      setValueAnimator(progress, 0, 0);
      valueAnimator.start();
    }
  }

  private void setValueAnimator(float begin, float end, int repeatCount) {
    valueAnimator = ValueAnimator.ofFloat(begin, end);
    valueAnimator.setRepeatCount(repeatCount);
    valueAnimator.setDuration(ANIMATION_CYCLE_DURATION);
    valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
    valueAnimator.setInterpolator(new LinearInterpolator());
    valueAnimator.addUpdateListener(this);
  }

  @Override
  public void onAnimationUpdate(@org.jetbrains.annotations.NotNull ValueAnimator valueAnimator) {
    progress = (float) valueAnimator.getAnimatedValue();
    loader.invalidate();
  }

  public void removeAnimatorUpdateListener() {
    if (valueAnimator != null) {
      valueAnimator.removeUpdateListener(this);
      valueAnimator.cancel();
    }
    progress = 0f;
  }

}
