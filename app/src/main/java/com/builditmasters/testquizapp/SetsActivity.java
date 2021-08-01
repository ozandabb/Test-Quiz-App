package com.builditmasters.testquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.builditmasters.testquizapp.Adapters.SetAdapter;

public class SetsActivity extends AppCompatActivity {

    private GridView set_grid;
    private TextView set_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
//
//        Toolbar toolbar = findViewById(R.id.setTooldbar);
//        setSupportActionBar(toolbar);

        set_title = findViewById(R.id.set_title);

        String title = getIntent().getStringExtra("CATEGORY");

        set_title.setText(title);

        set_grid = findViewById(R.id.setsGridView);

        SetAdapter adapter = new SetAdapter(6);
        set_grid.setAdapter(adapter);
    }
}