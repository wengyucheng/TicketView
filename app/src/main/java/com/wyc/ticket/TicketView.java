package com.wyc.ticket;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * author: wyc
 * date: 2020-01-02
 * time: 11:00
 * description:
 */
public class TicketView extends View {

    private int backgroundColor;
    private int dashColor;

    public TicketView(Context context) {
        this(context, null);
    }

    public TicketView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TicketView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TicketView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.TicketView_backgroundColor:
                    backgroundColor = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.TicketView_dashColor:
                    dashColor = array.getColor(attr, context.getResources().getColor(R.color.colorE3));
                    break;
                default:
                    break;
            }
        }
        array.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        int r = canvasHeight / 2;

        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(backgroundColor);
        canvas.drawRect(0, 0, canvasWidth, canvasHeight, paint);
        //使用CLEAR作为PorterDuffXfermode绘制透明圆形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //正常绘制圆形
        canvas.drawCircle(0, r, r, paint);
        canvas.drawCircle(canvasWidth, r ,r, paint);
        //最后将画笔去除Xfermode
        paint.setXfermode(null);

        paint.setColor(dashColor);
        paint.setStrokeWidth(2);
        paint.setPathEffect(new DashPathEffect(new float[]{15, 15}, 0));
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        canvas.drawLine(r, r, canvasWidth - r, r, paint);

        Path mPath = new Path();

        mPath.reset();
        mPath.moveTo(r, r);
        mPath.lineTo(getWidth()-r, r);
        canvas.drawPath(mPath, paint);

        canvas.restoreToCount(layerId);
    }
}
