package com.builditmasters.testquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.builditmasters.testquizapp.Adapters.GridAdpater;

import java.util.ArrayList;
import java.util.List;

import static com.builditmasters.testquizapp.SplashActivity.catList;

public class CategoryActivity extends AppCompatActivity {

    private GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        gridView = findViewById(R.id.catGridView);

//        List<String> catList = new ArrayList<>();
//
//        catList.add("cat 01");
//        catList.add("cat 02");
//        catList.add("cat 03");
//        catList.add("cat 04");
//        catList.add("cat 05");
//        catList.add("cat 06");
//        catList.add("cat 07");
//        catList.add("cat 08");

        GridAdpater adpater = new GridAdpater(catList);
        gridView.setAdapter(adpater);



    }
}