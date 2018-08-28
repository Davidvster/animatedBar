package com.bar.david.animatedbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class AnimatedBar extends View {

    private int backgroundColor = 0;
    private int borderColor = 0;
    private int borderWidth = 0;

    private Paint backgroundPaint = new Paint();
    private Paint borderPaint = new Paint();
    private Paint barPaint = new Paint();

    private int viewWidth = 0;
    private int viewHeight = 0;

    private float oneDp = 0f;
    private int maxDivider = 0;
    private float position = 0f;

    private double[] percentages;
    private int[] colors;

    private ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);

    public AnimatedBar(Context context) {
        super(context);
    }

    public AnimatedBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray tA = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.AnimatedBarView, 0, 0);

        try {
            backgroundColor = tA.getInteger(R.styleable.AnimatedBarView_backgroundColor, 0);
            borderColor = tA.getInteger(R.styleable.AnimatedBarView_borderColor, 0);
            borderWidth = tA.getInteger(R.styleable.AnimatedBarView_borderWidthDp, 0);
        } finally {
            tA.recycle();
        }
        oneDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, getResources().getDisplayMetrics());

        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(backgroundColor);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeWidth(oneDp * borderWidth);
        borderPaint.setColor(borderColor);

        barPaint.setStyle(Paint.Style.FILL);
        barPaint.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        viewWidth = this.getMeasuredWidth() - (int)(oneDp * maxDivider);
        viewHeight = this.getMeasuredHeight();

        canvas.drawRect(0f, 0f, viewWidth + oneDp * maxDivider, viewHeight, backgroundPaint);
        canvas.drawRect(0f, 0f, viewWidth + oneDp * maxDivider, viewHeight, borderPaint);

        int startPosition = 0;
        int endPosition = 0;
        if (percentages != null) {
            for (int i = 0; i < percentages.length; i++) {
                endPosition += ((percentages[i] * viewWidth) + (oneDp * borderWidth)) * position;
                barPaint.setColor(colors[i]);
                canvas.drawRect(startPosition, 0, endPosition, viewHeight, barPaint);
                if (borderWidth > 0) {
                    canvas.drawRect(startPosition, 0, endPosition, viewHeight, borderPaint);
                }
                startPosition = endPosition;
            }
        }
    }

    public void initAnimatedBar(double[] percentages, int[] colors, double limit, int animationDuartion) {
        if (percentages.length == colors.length) {
            maxDivider = 0;
            double sum = 0;
            for (int i = 0; i < percentages.length; i++) {
                sum += percentages[i];
                if (percentages[i] > limit) {
                    maxDivider += borderWidth;
                }
            }
            if (sum > 1) {
                for (int i = 0; i < percentages.length; i++) {
                    percentages[i] = percentages[i] / sum;
                }
            }
            this.percentages = percentages;
            this.colors = colors;

            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.setDuration(animationDuartion);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    position = valueAnimator.getAnimatedFraction();
                    invalidate();
                }
            });
        }
    }

    public void initAnimatedBar(double[] percentages, int[] colors, double limit) {
        int animationDuration = 500;

        initAnimatedBar(percentages, colors, limit, animationDuration);
    }

    public void initAnimatedBar(double[] percentages, int[] colors) {
        int animationDuration = 500;
        double limit = 0;

        initAnimatedBar(percentages, colors, limit, animationDuration);
    }

    public void startAnimatedBar() {
        valueAnimator.start();
    }
}
