package com.example.homework_unit8;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.content.Intent;

import java.util.HashMap;

public class Graph extends View {

    private Paint mPaint;
    private Paint mPaint2;
    private Paint axisPaint;
    float barWidth;
    float barHeight = 20f;
    float graphMargin = 110f;
    float origin; //(0,0) on graph
    float graphWidth;




    public Graph(Context context, AttributeSet attrs) {
        super(context, attrs);


        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);


        float screenWidth = canvas.getWidth();
        float screenHeight = canvas.getHeight();
        origin = screenHeight- graphMargin;

        //width of entire graph space
        graphWidth = graphMargin + (screenWidth - graphMargin);

        //try to determine bar width dynamically depending on number of different grades to graph
        barWidth = graphWidth /6 -20;


        mPaint.setColor(Color.RED);
        mPaint2.setColor(Color.BLUE);
        axisPaint.setColor(Color.BLACK);

        //draw yAxis
        canvas.drawRect(graphMargin, graphMargin, graphMargin+10f, screenHeight-graphMargin, axisPaint);

        //draw xAxis
        canvas.drawRect(graphMargin+10f, screenHeight-graphMargin-10f, screenWidth-graphMargin, screenHeight-graphMargin, axisPaint);

        /*//possibly draw side value labels with this
        for(int i = (int)origin; i >=graphMargin; i-= 80){
            canvas.drawRect(graphMargin-30f, i-10, graphMargin, i, axisPaint);
        }*/

        canvas.drawRect(graphMargin+10, graphMargin,  graphMargin+10 + barWidth, screenHeight-graphMargin, mPaint);
    }

}
