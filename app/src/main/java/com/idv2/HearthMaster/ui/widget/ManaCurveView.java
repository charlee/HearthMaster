package com.idv2.HearthMaster.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.idv2.HearthMaster.HearthMasterApp;

/**
 * Created by charlee on 2014-10-04.
 */
public class ManaCurveView extends View {

    private static float screenDensity;

    public final static int WIDTH = 160;
    public final static int HEIGHT = 120;

    // drawing constants
    private final static int MARGIN = 4;            // margin around the whole graph
    private final static int SPACING = 2;           // spacing between bars / bar & text

    private int[] mManaCurve;

    public ManaCurveView(Context context) {
        super(context);
        init(null, 0);
    }

    public ManaCurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ManaCurveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        mManaCurve = new int[8];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics metrics = HearthMasterApp.getInstance().getApplicationContext().getResources().getDisplayMetrics();
        screenDensity = metrics.density;
        setMeasuredDimension((int)(WIDTH * screenDensity), (int)(HEIGHT * screenDensity));
    }

    /**
     * Setup mana curve display
     * @param manaCurve card counts array. only first 8 elements are used as 0-6, 7+.
     */
    public void setCurve(int[] manaCurve) {
        System.arraycopy(manaCurve, 0, this.mManaCurve, 0, this.mManaCurve.length);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(screenDensity, screenDensity);

        // draw the mana curve

        // structure:
        // ----------------
        //        4dp margin
        //        16dp  card count
        // 10dp   80dp chart          10dp
        //        16dp  mana cost
        //        4dp margin




        int max = 0;
        for (int i = 0; i < mManaCurve.length; i++) {
            if (mManaCurve[i] > max) max = mManaCurve[i];
        }

        // draw chart

        // draw background
        Paint fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(0xffeeeeee);

        Paint strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(0xffcccccc);

        Paint chartPaint = new Paint();
        chartPaint.setStyle(Paint.Style.FILL);
        chartPaint.setColor(0xff428bca);

        Paint textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // constants
        float graphWidth = WIDTH - MARGIN * 2;
        float graphHeight = HEIGHT - MARGIN * 2;
        float barWidthWithSpacing = graphWidth / mManaCurve.length;
        float barWidth = barWidthWithSpacing - SPACING;
        float textHeight = 16;
        float barMaxHeight = graphHeight - textHeight * 2;
        float scale = (max == 0) ? 0 : barMaxHeight / max;            // 1 card = `scale` dp


        for (int i = 0; i < mManaCurve.length; i++) {
            // draw background
            float x0 = MARGIN + i * barWidthWithSpacing;
            float y0 = MARGIN;
            float x1 = x0 + barWidth;
            float y1 = graphHeight - textHeight;

            RectF rect = new RectF(x0, y0, x1, y1);

            canvas.drawRoundRect(rect, 2, 2, fillPaint);
            canvas.drawRoundRect(rect, 2, 2, strokePaint);

            float x2 = x0 + 2;
            float y2 = y1 - scale * mManaCurve[i];
            float x3 = x1 - 2;
            float y3 = y1;

            RectF chartRect = new RectF(x2, y2, x3, y3);
            canvas.drawRoundRect(chartRect, 2, 2, chartPaint);

            // draw numbers
            canvas.drawText(String.format("%d", mManaCurve[i]), (x2 + x3) / 2, y2 - SPACING, textPaint);
            canvas.drawText(String.format((i == mManaCurve.length - 1) ? "%d+" : "%d", i), (x2 + x3) / 2, y3 + textHeight, textPaint);
        }

        canvas.restore();
    }
}
