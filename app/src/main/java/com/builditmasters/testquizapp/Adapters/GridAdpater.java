package com.builditmasters.testquizapp.Adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.builditmasters.testquizapp.CategoryActivity;
import com.builditmasters.testquizapp.MainActivity;
import com.builditmasters.testquizapp.R;
import com.builditmasters.testquizapp.SetsActivity;

import java.util.List;
import java.util.Random;

public class GridAdpater extends BaseAdapter {

    private List<String> catList;

    public GridAdpater(List<String> catList) {
        this.catList = catList;
    }

    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if (convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_grid_layout, parent, false);

        }else {
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), SetsActivity.class);
                intent.putExtra("CATEGORY", catList.get(position));
                intent.putExtra("CATEGORY_ID", position + 1);
                parent.getContext().startActivity(intent);
            }
        });

        ((TextView) view.findViewById(R.id.catName)).setText(catList.get(position));

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
        view.setBackgroundColor(color);

        return view;
    }
}
