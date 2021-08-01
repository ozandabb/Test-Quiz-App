package com.builditmasters.testquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.builditmasters.testquizapp.Adapters.SetAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetsActivity extends AppCompatActivity {

    private GridView set_grid;
    private TextView set_title;
    private FirebaseFirestore firebaseFirestore;
    public static int CATEGORY_ID;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        firebaseFirestore = FirebaseFirestore.getInstance();

        set_title = findViewById(R.id.set_title);
        String title = getIntent().getStringExtra("CATEGORY");
        set_title.setText(title);

        set_grid = findViewById(R.id.setsGridView);

        loadingDialog = new Dialog(SetsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progressbar_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        CATEGORY_ID = getIntent().getIntExtra("CATEGORY_ID", 1);
        loadSets();
    }

    private void loadSets() {

        firebaseFirestore.collection("QUIZ").document("CAT0" + String.valueOf(CATEGORY_ID))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()){
                        long sets = (long) doc.get("SETS");

                        SetAdapter adapter = new SetAdapter((int)sets);
                        set_grid.setAdapter(adapter);

                    }else {
                        Toast.makeText(SetsActivity.this,"No sets exists ",Toast.LENGTH_LONG).show();
                        finish();
                    }


                }else {
                    Toast.makeText(SetsActivity.this,"Data not loaded. please check your connection",Toast.LENGTH_LONG).show();
                }

                loadingDialog.cancel();
            }
        });
    }
}