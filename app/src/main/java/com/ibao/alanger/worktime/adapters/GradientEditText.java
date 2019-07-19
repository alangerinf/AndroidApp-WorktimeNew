package com.ibao.alanger.worktime.adapters;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;


import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.ibao.alanger.worktime.R;


public class GradientEditText extends AppCompatEditText {

  public GradientEditText(Context context) {
    super(context);
  }

  public GradientEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public GradientEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);

    //Setting the gradient if layout is changed
    if (changed) {
      getPaint().setShader(new LinearGradient(0, 0, getWidth(), getHeight(),
              ContextCompat.getColor(getContext(), R.color.colorPrimaryDark),
              ContextCompat.getColor(getContext(), R.color.colorPrimary),
              Shader.TileMode.CLAMP));
      getPaint().setAlpha(2);
    }
  }
}
