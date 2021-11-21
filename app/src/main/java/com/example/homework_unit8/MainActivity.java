package com.example.homework_unit8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    LinearLayout ll;
    EditText mainET;
    String mainEditTextInput;
    String inputArray[];
    Button getFieldsButton;
    Button computeButton;
    EditText editTextArray[] = new EditText[20];
    EditText numOfStudents;
    public static final String GRADE1 = "FIRST_GRADE_KEY";
    public static final String GRADE2 = "SECOND_GRADE_KEY";
    public static final String GRADE3 = "THIRD_GRADE_KEY";
    public static final String GRADE4 = "FOURTH_GRADE_KEY";
    public static final String GRADE5 = "FIFTH_GRADE_KEY";
    public static final String GRADE6 = "SIXTH_GRADE_KEY";
    public static final String GRADE7 = "SEVENTH_GRADE_KEY";
    public static final String GRADE8 = "EIGHTH_GRADE_KEY";
    public static final String GRADE9 = "NINTH_GRADE_KEY";
    public static final String GRADE10 = "TENTH_GRADE_KEY";
    public static final String GRADE11 = "ELEVENTH_GRADE_KEY";
    public static final String AMOUNT1 = "LETTER_AMOUNT_ONE";
    public static final String AMOUNT2 = "LETTER_AMOUNT_TWO";
    public static final String AMOUNT3 = "LETTER_AMOUNT_THREE";
    public static final String AMOUNT4 = "LETTER_AMOUNT_FOUR";
    public static final String AMOUNT5 = "LETTER_AMOUNT_FIVE";
    public static final String AMOUNT6 = "LETTER_AMOUNT_SIX";
    public static final String AMOUNT7 = "LETTER_AMOUNT_SEVEN";
    public static final String AMOUNT8 = "LETTER_AMOUNT_EIGHT";
    public static final String AMOUNT9 = "LETTER_AMOUNT_NINE";
    public static final String AMOUNT10 = "LETTER_AMOUNT_TEN";
    public static final String AMOUNT11 = "LETTER_AMOUNT_ELEVEN";
    public static final String lettersAmount = "TOTAL_DIFFERENT_LETTER_GRADES";
    //array of sharedpreference keys for letter grade
    public static String[] keys = {GRADE1, GRADE2, GRADE3, GRADE4, GRADE5, GRADE6, GRADE7, GRADE8, GRADE9, GRADE10, GRADE11};
    //array of sharedpreference keys for grade amounts
    public static String[] amounts = {AMOUNT1, AMOUNT2, AMOUNT3, AMOUNT4, AMOUNT5, AMOUNT6, AMOUNT7, AMOUNT8, AMOUNT9, AMOUNT10, AMOUNT11};
    SharedPreferences sharedPreferences;
    public static final String MYPREF = "MY_PREF_FILE";
    public static final String[] acceptableGrades = {"a", "a+", "a-", "b", "b+", "b-", "c", "c+", "c-", "d", "f"};

    int totalStudents;

    HashMap<String, Integer> gradeAmounts = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainEditTextInput = "";

        ll = (LinearLayout) findViewById(R.id.mainLayout);
        mainET = new EditText(this);
        getFieldsButton = new Button(this);
        computeButton = new Button(this);

        sharedPreferences = getSharedPreferences(MYPREF, Context.MODE_PRIVATE);



        LinearLayout.LayoutParams eTParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.gravity = Gravity.CENTER;

        mainET.setLayoutParams(eTParams);
        ll.addView(mainET);

        numOfStudents = new EditText(this);
        numOfStudents.setLayoutParams(eTParams);
        numOfStudents.setHint("Enter total number of students");

        computeButton.setLayoutParams(buttonParams);
        computeButton.setText("Compute");

        getFieldsButton.setLayoutParams(buttonParams);
        getFieldsButton.setText("Done");

        //when done button is clicked
        getFieldsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainET.setFocusable(false);
                ll.addView(numOfStudents);
                getFieldsButton.setVisibility(View.GONE);
                numOfStudents.requestFocus();
                populateArray();
                checkArray();
                populateEditTexts();
                addEditTexts();
                ll.addView(computeButton);
            }
        });

        ll.addView(getFieldsButton);

        //when compute button is clicked
        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!areFieldsCompleted()) {
                    Toast.makeText(MainActivity.this, "Cannot leave any fields blank", Toast.LENGTH_LONG).show();
                }
                else{
                    populateGradeAmounts();
                    startGraphActivity(view);
                }
                //startGraphActivity(view);

            }
        });

        //watch for tab key being pressed...wish I could figure this out
        mainET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               /* if(editable.charAt(editable.length()-1) == '\n'){
                    mainET.setFocusable(false);
                    Toast.makeText(MainActivity.this, "Tab key pressed", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }

    //get input from main edittext
    public void populateArray(){
        String input = mainET.getText().toString();
        inputArray = input.split(",");
    }

    //logging array
    public void checkArray(){
        for(int i =0; i < inputArray.length; i++){
            Log.d("array[0]", inputArray[i]);
        }
    }

    //initalize editTexts
    public void populateEditTexts(){
        LinearLayout.LayoutParams eTParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        for(int i = 0; i < inputArray.length; i++){
            editTextArray[i] = new EditText(this);
            editTextArray[i].setHint("Enter total number of " + inputArray[i] + " students");
            editTextArray[i].setLayoutParams(eTParams);
        }
    }

    //add however many edit texts needed to view
    public void addEditTexts(){
        for (int i =0; i < editTextArray.length; i++){
            if(editTextArray[i] != null){
                ll.addView(editTextArray[i]);
            }
        }
    }

    public void populateGradeAmounts(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(int i = 0; i < inputArray.length; i++){
            gradeAmounts.put(inputArray[i], Integer.parseInt(editTextArray[i].getText().toString()));
            editor.putString(keys[i], inputArray[i]);
            editor.putInt(amounts[i], Integer.parseInt(editTextArray[i].getText().toString()));
        }
        //put the total number of different letters
        editor.putInt(lettersAmount, inputArray.length);
        //editor.commit();
        editor.apply();
        Log.d("GradeAmounts: ", ""+gradeAmounts);
    }


    public boolean areFieldsCompleted(){
        boolean result = true;
        for(int i = 0; i < inputArray.length; i++){
            if(editTextArray[i].getText().toString().matches("")){
                result = false;
                break;
            }
        }
        return result;
    }

    // finish this to make sure user enters a number
    public boolean isValidInput(){
        boolean result = true;
        for(int i = 0; i < inputArray.length; i++){
            if(editTextArray[i].getText().toString().matches("")){
                result = false;
                break;
            }
        }
        return result;
    }

    public void startGraphActivity(View view){
        Intent intent = new Intent(this, GraphActivity.class);
        //intent.putExtra("map", gradeAmounts);
        //intent.putExtra
        startActivity(intent);
    }

    public void pushValuesToView(View view){
        Intent intent = new Intent(this, Graph.class);
        intent.putExtra("map", gradeAmounts);
    }

    public void setSharedPreferences(HashMap<String, Integer> amounts){

        for (Map.Entry<String, Integer> entry : amounts.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();


        }
    }



}