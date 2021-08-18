package com.apachat.loaderview.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.apachat.loaderview.core.interfaces.Loader;

public class ImageView extends AppCompatImageView implements Loader {
  private Controller controller;
  private int defaultColorResource;

  public ImageView(Context context) {
    super(context);
    init(null);
  }

  public ImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public ImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    controller = new Controller(this);
    TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.loader_view, 0, 0);
    controller.setUseGradient(typedArray.getBoolean(R.styleable.loader_view_use_gradient, Constants.USE_GRADIENT_DEFAULT));
    controller.setCorners(typedArray.getInt(R.styleable.loader_view_corners, Constants.CORNER_DEFAULT));
    defaultColorResource = typedArray.getColor(R.styleable.loader_view_custom_color, ContextCompat.getColor(getContext(), R.color.default_color));
    typedArray.recycle();
  }

  public void resetLoader() {
    if (getDrawable() != null) {
      super.setImageDrawable(null);
      controller.startLoading();
    }
  }

  @Override
  protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    super.onSizeChanged(width, height, oldWidth, oldHeight);
    controller.onSizeChanged();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    controller.onDraw(canvas);
  }

  @Override
  public void setRectColor(Paint rectPaint) {
    rectPaint.setColor(defaultColorResource);
  }

  @Override
  public boolean valueSet() {
    return (getDrawable() != null);
  }

  @Override
  public void setImageBitmap(Bitmap bm) {
    super.setImageBitmap(bm);
    if (controller != null)
      controller.stopLoading();
  }

  @Override
  public void setImageDrawable(Drawable drawable) {
    super.setImageDrawable(drawable);
    if (controller != null)
      controller.stopLoading();
  }

  @Override
  public void setImageIcon(Icon icon) {
    super.setImageIcon(icon);
    if (controller != null)
      controller.stopLoading();
  }

  @Override
  public void setImageResource(int resId) {
    super.setImageResource(resId);
    if (controller != null)
      controller.stopLoading();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    controller.removeAnimatorUpdateListener();
  }

}
