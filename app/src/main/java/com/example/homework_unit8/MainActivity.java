package com.example.homework_unit8;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    LinearLayout ll;
    EditText mainET;
    String mainEditTextInput;
    String inputArray[];
    Button getFieldsButton;
    Button computeButton;
    EditText editTextArray[] = new EditText[20];
    EditText numOfStudents;

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
                if(!isValidInput()) {
                    Toast.makeText(MainActivity.this, "Cannot leave any fields blank", Toast.LENGTH_LONG).show();
                }
                else{
                    populateGradeAmounts();
                }

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
        for(int i = 0; i < inputArray.length; i++){
            gradeAmounts.put(inputArray[i], Integer.parseInt(editTextArray[i].getText().toString()));
        }
        Log.d("GradeAmounts: ", ""+gradeAmounts);
    }


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



}