package com.builditmasters.testquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.builditmasters.testquizapp.Adapters.SetAdapter;
import com.builditmasters.testquizapp.Model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.builditmasters.testquizapp.SetsActivity.CATEGORY_ID;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView question_number, question, timer;
    private Button option01, option02, option03, option04;
    private List<Question> QuestionList;
    private int quesNum;
    private CountDownTimer countDownTimer;
    private int score;
    private int setNo;
    private FirebaseFirestore firebaseFirestore;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        firebaseFirestore = FirebaseFirestore.getInstance();

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

        setNo = getIntent().getIntExtra("SETNO", 1);

//        loadingDialog = new Dialog(GameActivity.this);
//        loadingDialog.setContentView(R.layout.loading_progressbar);
//        loadingDialog.setCancelable(false);
//        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progressbar_background);
//        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        loadingDialog.show();

        getQuestiosList();

        score = 0;


    }




    private void getQuestiosList() {

        QuestionList = new ArrayList<>();

        firebaseFirestore.collection("QUIZ").document("CAT0" + String.valueOf(CATEGORY_ID))
                .collection("SET0" + String.valueOf(setNo))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    QuerySnapshot questions = task.getResult();

                    Log.d("looooooooooooooooggggg", String.valueOf(questions.size()));

                    for (QueryDocumentSnapshot doc : questions ){
                        QuestionList.add(new Question(doc.getString("QUESTION"),
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                Integer.valueOf(doc.getString("ANSWER"))
                                ));

                        Log.d("vaaaaaalueeeee", doc.getString("QUESTION"));
                    }

                    setQuestion();


                }else {
                    Toast.makeText(GameActivity.this,"Data not loaded. please check your connection",Toast.LENGTH_LONG).show();
                }

//                loadingDialog.cancel();

            }
        });

//        QuestionList.add(new Question("Quest 01", "A", "B", "CR", "D", 2));
//        QuestionList.add(new Question("Quest 02", "G", "H", "C", "DG", 3));
//        QuestionList.add(new Question("Quest 03", "N", "B", "CD", "J", 4));
//        QuestionList.add(new Question("Quest 04", "O", "BS", "CG", "DY", 1));
//        QuestionList.add(new Question("Quest 05", "5", "3", "6", "D", 2));



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
            score++;

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
            goScore.putExtra("SCORE", String.valueOf(score) + "/" + String.valueOf(QuestionList.size() ));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        countDownTimer.cancel();
    }
}