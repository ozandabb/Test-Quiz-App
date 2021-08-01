package com.builditmasters.testquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    public static List<String> catList = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseFirestore = FirebaseFirestore.getInstance();


//        final Intent i = new Intent(this,MainActivity.class);
        Thread timer = new Thread(){
            public void run(){

                loadData();
//                try{
//                    sleep(1000);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }finally {
//                    startActivity(i);
//                    finish();

//                }
            }
        };
        timer.start();
    }

    private void loadData(){

        catList.clear();

        firebaseFirestore.collection("QUIZ").document("Categories")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()){
                        long count = (long) doc.get("COUNT");

                        for (int i = 1; i <= count; i++){
                            String catName = doc.getString("CAT0" + String.valueOf(i));

                            catList.add(catName);

                        }

                        Intent i = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(i);
                        SplashActivity.this.finish();

                    }else {
                        Toast.makeText(SplashActivity.this,"No category exists ",Toast.LENGTH_LONG).show();
                        finish();
                    }


                }else {
                    Toast.makeText(SplashActivity.this,"Data not loaded. please check your connection",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}