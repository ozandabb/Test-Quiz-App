package com.builditmasters.testquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.builditmasters.testquizapp.Model.Question;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView question_number, question, timer;
    private Button option01, option02, option03, option04;
    private List<Question> QuestionList;
    private int quesNum;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        question_number = findViewById(R.id.question_number);
        question = findViewById(R.id.question);
        timer = findViewById(R.id.countdown);

        option01 = findViewById(R.id.option01);
        option02 = findViewById(R.id.option02);
        option03 = findViewById(R.id.option03);
        option04 = findViewById(R.id.option04);

        option01.setOnClickListener(this);
        option02.setOnClickListener(this);
        option03.setOnClickListener(this);
        option04.setOnClickListener(this);

        getQuestiosList();




    }




    private void getQuestiosList() {

        QuestionList = new ArrayList<>();

        QuestionList.add(new Question("Quest 01", "A", "B", "CR", "D", 2));
        QuestionList.add(new Question("Quest 02", "G", "H", "C", "DG", 3));
        QuestionList.add(new Question("Quest 03", "N", "B", "CD", "J", 4));
        QuestionList.add(new Question("Quest 04", "O", "BS", "CG", "DY", 1));
        QuestionList.add(new Question("Quest 05", "5", "3", "6", "D", 2));

        setQuestion();

    }

    private void setQuestion() {

        timer.setText(String.valueOf(10));

        question.setText(QuestionList.get(0).getQuestion());
        option01.setText(QuestionList.get(0).getOptionA());
        option02.setText(QuestionList.get(0).getOptionB());
        option03.setText(QuestionList.get(0).getOptionC());
        option04.setText(QuestionList.get(0).getOptionD());

        question_number.setText(String.valueOf(1) + "/" + String.valueOf(QuestionList.size()));

        startTimer();

        quesNum = 0;
    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished/1000));

            }

            @Override
            public void onFinish() {
                changeQuestion();

            }
        };

        countDownTimer.start();
    }



    @Override
    public void onClick(View v) {

        int selectedOption = 0;

        switch (v.getId()){
            case R.id.option01:
                selectedOption = 1;
                break;

            case R.id.option02:
                selectedOption = 2;
                break;

            case R.id.option03:
                selectedOption = 3;
                break;

            case R.id.option04:
                selectedOption = 4;
                break;

            default:
        }

        countDownTimer.cancel();
        checkAnswer(selectedOption, v);

    }

    private void checkAnswer(int selectedOption, View view) {

        if (selectedOption == QuestionList.get(quesNum).getCorrectAns()){
            //Answer is right

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            }

        }else {
            //Answer is wrong

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            }

            switch (QuestionList.get(quesNum).getCorrectAns()){
                case 1:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        option01.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    };
                    break;

                case 2:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        option02.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    };
                    break;

                case 3:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        option03.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    };
                    break;

                case 4:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        option04.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    };
                    break;
            }

        }

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        }, 2000);

//        changeQuestion();


    }

    private void changeQuestion() {

        if (quesNum < QuestionList.size() - 1){

            quesNum++;

            playAnim(question, 0, 0);
            playAnim(option01, 0, 1);
            playAnim(option02, 0, 2);
            playAnim(option03, 0, 3);
            playAnim(option04, 0, 4);

            question_number.setText(String.valueOf(quesNum + 1 + "/" + String.valueOf(QuestionList.size() )));
//            timer.setText(String.valueOf(10));
            startTimer();


        }else {
            //Go to Score Activity
            Intent goScore = new Intent(GameActivity.this, ScoreActivity.class);
            startActivity(goScore);
            GameActivity.this.finish();
        }
    }

    private void playAnim(View view, final int value, int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (value == 0 ){

                            switch (viewNum){

                                case 0:
                                    ((TextView)view).setText(QuestionList.get(quesNum).getQuestion());
                                    break;
                                case 1:
                                    ((Button)view).setText(QuestionList.get(quesNum).getOptionA());
                                    break;
                                case 2:
                                    ((Button)view).setText(QuestionList.get(quesNum).getOptionB());
                                    break;
                                case 3:
                                    ((Button)view).setText(QuestionList.get(quesNum).getOptionC());
                                    break;
                                case 4:
                                    ((Button)view).setText(QuestionList.get(quesNum).getOptionD());
                                    break;

                            }

                            if (viewNum != 0){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8B8000")));
                                }
                            }

                            playAnim(view, 1, viewNum);

                        }

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }


}