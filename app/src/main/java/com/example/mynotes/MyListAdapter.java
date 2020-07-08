package com.example.mynotes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<String> {

    private int resourceLayout;
    private Context mContext;

    public MyListAdapter(Context context, int resource, ArrayList<String> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        TextView textViewTitle = (TextView) v.findViewById(R.id.textViewTitle);
        TextView textViewDate = (TextView) v.findViewById(R.id.textViewDate);

        textViewTitle.setTextColor(Color.BLACK);
        textViewDate.setTextColor(Color.BLACK);

        textViewTitle.setText(MainActivity.titles.get(position));
        textViewDate.setText(MainActivity.dates.get(position));

        return v;

    }

}
