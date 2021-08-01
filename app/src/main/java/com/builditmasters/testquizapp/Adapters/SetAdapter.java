package com.builditmasters.testquizapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.builditmasters.testquizapp.R;

public class SetAdapter extends BaseAdapter {

    private int numOfSets;

    public SetAdapter(int numOfSets) {
        this.numOfSets = numOfSets;
    }

    @Override
    public int getCount() {
        return numOfSets;
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item_layout, parent, false);

        }else {
            view = convertView;
        }

        ((TextView) view.findViewById(R.id.setNum_textView)).setText(String.valueOf(position +1));

        return view;
    }
}
