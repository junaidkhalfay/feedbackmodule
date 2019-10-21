package com.example.feedbackmodule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.feedbackmodule.questions.AnswersActivity;
import com.example.feedbackmodule.questions.QuestionActivity;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity
{
    private static final int QUESTIONNAIRE_REQUEST = 2018;
    Button resultButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();

        SharedPreferences preferences=getSharedPreferences("userDetails",MODE_PRIVATE);
        String usertype=   preferences.getString("utype","na");

        Button questionnaireButton = findViewById(R.id.questionnaireButton);
        Button questionnaireButton2 = findViewById(R.id.questionnaireButton2);
        resultButton = findViewById(R.id.resultButton);
        if(usertype.equals("Student"))
        {
            questionnaireButton2.setVisibility(View.VISIBLE);
            questionnaireButton.setVisibility(View.INVISIBLE);
        }
        if(usertype.equals("Teacher"))
        {
            questionnaireButton2.setVisibility(View.INVISIBLE);
            questionnaireButton.setVisibility(View.VISIBLE);
        }


        questionnaireButton.setOnClickListener(v -> {
            resultButton.setVisibility(View.GONE);

            Intent questions = new Intent(MainActivity.this, QuestionActivity.class);
            //you have to pass as an extra the json string.
            questions.putExtra("json_questions", loadQuestionnaireJson("CurriTeacher"));
            startActivityForResult(questions, QUESTIONNAIRE_REQUEST);
        });


        questionnaireButton2.setOnClickListener(v -> {
            resultButton.setVisibility(View.GONE);

            Intent questions = new Intent(MainActivity.this, QuestionActivity.class);
            //you have to pass as an extra the json string.
            questions.putExtra("json_questions", loadQuestionnaireJson("CurriStudent"));
            startActivityForResult(questions, QUESTIONNAIRE_REQUEST);
        });

        resultButton.setOnClickListener(v -> {
            Intent questions = new Intent(MainActivity.this, AnswersActivity.class);
            startActivity(questions);
        });
    }

    void setUpToolbar()
    {
        Toolbar mainPageToolbar = findViewById(R.id.mainPageToolbar);
        setSupportActionBar(mainPageToolbar);
        getSupportActionBar().setTitle("FeedBack Module");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QUESTIONNAIRE_REQUEST) {
            if (resultCode == RESULT_OK) {
                resultButton.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Questionnaire Completed!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    //json stored in the assets folder. but you can get it from wherever you like.
    private String loadQuestionnaireJson(String filename)
    {
        try
        {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}