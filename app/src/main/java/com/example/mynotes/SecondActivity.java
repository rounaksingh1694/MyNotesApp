package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mynotes.ObjectSerializer;

import java.util.Date;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {

    EditText editTextTitle, editTextContent;
    Intent intent;
    int pos;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void save(View view) {

        Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();

        MainActivity.titles.set(pos, editTextTitle.getText().toString());
        MainActivity.content.set(pos, editTextContent.getText().toString());
        MainActivity.dates.set(pos, new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        MainActivity.arrayAdapter.notifyDataSetChanged();

        try {
            MainActivity.sharedPreferences.edit().putString("titles", ObjectSerializer.serialize(MainActivity.titles)).apply();
            MainActivity.sharedPreferences.edit().putString("content", ObjectSerializer.serialize(MainActivity.content)).apply();
            MainActivity.sharedPreferences.edit().putString("dates", ObjectSerializer.serialize(MainActivity.dates)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextContent = (EditText) findViewById(R.id.editTextContent);

        intent = getIntent();
        pos = intent.getIntExtra("Position", 0);
        editTextTitle.setText(intent.getStringExtra("Title"));
        editTextContent.setText(intent.getStringExtra("Content"));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();

        MainActivity.titles.set(pos, editTextTitle.getText().toString());
        MainActivity.content.set(pos, editTextContent.getText().toString());
        MainActivity.dates.set(pos, new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        MainActivity.arrayAdapter.notifyDataSetChanged();

        try {
            MainActivity.sharedPreferences.edit().putString("titles", ObjectSerializer.serialize(MainActivity.titles)).apply();
            MainActivity.sharedPreferences.edit().putString("content", ObjectSerializer.serialize(MainActivity.content)).apply();
            MainActivity.sharedPreferences.edit().putString("dates", ObjectSerializer.serialize(MainActivity.dates)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
