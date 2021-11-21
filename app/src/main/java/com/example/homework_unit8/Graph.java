package com.example.homework_unit8;


import static com.example.homework_unit8.MainActivity.AMOUNT1;
import static com.example.homework_unit8.MainActivity.AMOUNT10;
import static com.example.homework_unit8.MainActivity.AMOUNT11;
import static com.example.homework_unit8.MainActivity.AMOUNT2;
import static com.example.homework_unit8.MainActivity.AMOUNT3;
import static com.example.homework_unit8.MainActivity.AMOUNT4;
import static com.example.homework_unit8.MainActivity.AMOUNT5;
import static com.example.homework_unit8.MainActivity.AMOUNT6;
import static com.example.homework_unit8.MainActivity.AMOUNT7;
import static com.example.homework_unit8.MainActivity.AMOUNT8;
import static com.example.homework_unit8.MainActivity.AMOUNT9;
import static com.example.homework_unit8.MainActivity.GRADE1;
import static com.example.homework_unit8.MainActivity.GRADE10;
import static com.example.homework_unit8.MainActivity.GRADE11;
import static com.example.homework_unit8.MainActivity.GRADE2;
import static com.example.homework_unit8.MainActivity.GRADE3;
import static com.example.homework_unit8.MainActivity.GRADE4;
import static com.example.homework_unit8.MainActivity.GRADE5;
import static com.example.homework_unit8.MainActivity.GRADE6;
import static com.example.homework_unit8.MainActivity.GRADE7;
import static com.example.homework_unit8.MainActivity.GRADE8;
import static com.example.homework_unit8.MainActivity.GRADE9;
import static com.example.homework_unit8.MainActivity.MYPREF;
import static com.example.homework_unit8.MainActivity.NUM_OF_STUDENTS;
import static com.example.homework_unit8.MainActivity.lettersAmount;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class Graph extends View {

    private Paint mPaint;
    private Paint mPaint2;
    private Paint axisPaint;
    float barWidth;
    float barHeight;
    float graphMargin = 110f;
    float origin; //(0,0) on graph
    float graphWidth;
    float screenWidth;
    float screenHeight;
    float barSpace = 20f;
    SharedPreferences sharedPreferences;
    public String[] letterGrades;
    public int[] letterAmounts;
    int totalLetters;
    int totalStudents;
    int sharedPreferencesInt;
    int getSharedPreferencesNumOfStudents;
    public static String[] keys = {GRADE1, GRADE2, GRADE3, GRADE4, GRADE5, GRADE6, GRADE7, GRADE8, GRADE9, GRADE10, GRADE11};
    public static String[] amounts = {AMOUNT1, AMOUNT2, AMOUNT3, AMOUNT4, AMOUNT5, AMOUNT6, AMOUNT7, AMOUNT8, AMOUNT9, AMOUNT10, AMOUNT11};
    Paint[] colors = new Paint[3];
    int[] colorResources = {getResources().getColor(R.color.bar1), getResources().getColor(R.color.bar2), getResources().getColor(R.color.bar3)};
    TextView text;
    float[] percents;


    public Graph(Context context, AttributeSet attrs) {
        super(context, attrs);

        text = new TextView(context);

        sharedPreferences = context.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        sharedPreferencesInt = sharedPreferences.getInt(lettersAmount, totalLetters);
        getSharedPreferencesNumOfStudents = sharedPreferences.getInt(NUM_OF_STUDENTS, totalStudents);
        Log.d("total amount of letters: ", ""+sharedPreferencesInt);

        percents = new float[sharedPreferencesInt];


        getSharedPreferences();
        checkSharedPreferences();
        getPercentages();




        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //set array of paints for bars in graph
        for(int i =0; i < colors.length; i++){
            colors[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
            colors[i].setColor(colorResources[i]);
        }



    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);


        screenWidth = canvas.getWidth();
        screenHeight = canvas.getHeight();

        origin = screenHeight- graphMargin;

        //width of entire graph space
        graphWidth = graphMargin + (screenWidth - graphMargin);

        //try to determine bar width dynamically depending on number of different grades to graph
        barWidth = (graphWidth / 11) -21;


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

        //canvas.drawRect(graphMargin+10, graphMargin,  graphMargin+10 + barWidth, screenHeight-graphMargin, mPaint);
        drawRects(canvas);

        axisPaint.setTextSize(30f);
        canvas.drawText("Grade Percentages", graphWidth/2-100, screenHeight-graphMargin/2+20, axisPaint);
    }

    public void getSharedPreferences(){
        String letter = "";
        int amount = 0;
        letterGrades = new String[sharedPreferencesInt];
        letterAmounts = new int[sharedPreferencesInt];

        for(int i = 0; i < sharedPreferencesInt; i++){
            letterGrades[i] = sharedPreferences.getString(keys[i], letter);
            letterAmounts[i] = sharedPreferences.getInt(amounts[i], amount);
        }
        Log.d("arrays:", ""+letterGrades);
    }

    public void checkSharedPreferences(){
        for(int i = 0; i < sharedPreferencesInt; i++){
            Log.d("grades:", ""+letterGrades[i]);
            Log.d("amounts:", ""+letterAmounts[i]);
        }
    }

    //dynamically draw bars depending on amount of letter grades inputted
    public void drawRects(Canvas canvas){
        Paint fontColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontColor.setTextSize(30f);
        fontColor.setColor(Color.BLACK);
        fontColor.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int c = 0;
        float originalPlacement = graphMargin+10;
        for(int i =0; i< sharedPreferencesInt; i++, originalPlacement += barWidth, c++){
            if(i % 3 == 0){
                c= 0;
            }
            //dynamically change top of bar placement depending on value
            canvas.drawRect(originalPlacement, (screenHeight-graphMargin)-percents[i]*15,  originalPlacement + barWidth, screenHeight-graphMargin-10, colors[c]);

            //label percentages above each bar
            canvas.drawText(""+String.format("%.1f", percents[i]) + "%",originalPlacement +barWidth/2 - 40,(screenHeight-graphMargin)-percents[i]*15-10, fontColor);

            //label letter grade under each bar
            canvas.drawText(""+letterGrades[i].toUpperCase(),originalPlacement +barWidth/2 - 15,(screenHeight-graphMargin)+40, fontColor);
        }

    }

    public void getPercentages(){
        for(int i=0; i < letterAmounts.length; i++){
            if(letterAmounts[i] == 0){
                percents[i] = 0.0f;
            }
            else {
                percents[i] = (float)letterAmounts[i] / (float)getSharedPreferencesNumOfStudents * 100;
                Log.d("total students" , ""+ getSharedPreferencesNumOfStudents);
                Log.d("Percent of " + letterAmounts[i] + ": ", ""+ percents[i]);
            }
        }
    }

}
