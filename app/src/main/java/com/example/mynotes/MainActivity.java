package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mynotes.ObjectSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    static ArrayList<String> titles;
    static ArrayList<String> content;
    static ArrayList<String> dates;
    static ArrayList<Integer> deleteElements;
    static ArrayAdapter<String> arrayAdapter1;
    static MyListAdapter arrayAdapter;
    static SharedPreferences sharedPreferences;

    Intent intent;

    int removeIndex;

    Button add;

    ImageView back, delete;

    LinearLayout linearLayout;

    Boolean isDelete = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addANewNote:
                titles.add("New Title");
                content.add("Add your content here");
                dates.add(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
                int index = titles.size() - 1;
                intent.putExtra("Title", titles.get(index));
                intent.putExtra("Content", content.get(index));
                intent.putExtra("Position", index);
                startActivity(intent);
                return true;
            default:
                return false;
        }

    }

    public void alertDialog(int index) {

        removeIndex = index;

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Are you sure")
                .setMessage("Do you want to delete this note")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        titles.remove(removeIndex);
                        content.remove(removeIndex);
                        dates.remove(removeIndex);
                        try {
                            sharedPreferences.edit().putString("titles", ObjectSerializer.serialize(titles)).apply();
                            sharedPreferences.edit().putString("content", ObjectSerializer.serialize(content)).apply();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addNote(View view) {

        titles.add("New Title");
        content.add("Add your content here");
        dates.add(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        int index = titles.size() - 1;
        intent.putExtra("Title", titles.get(index));
        intent.putExtra("Content", content.get(index));
        intent.putExtra("Position", index);
        startActivity(intent);

    }

    public void deleteNotes(View view) {

        int size = deleteElements.size();

        boolean canDelete = false;

        Collections.sort(deleteElements, Collections.<Integer>reverseOrder());

        Log.i("deleteElements", deleteElements.toString());

        if (deleteElements.size() == 0) {
            Toast.makeText(this, "Please Select a Note", Toast.LENGTH_SHORT).show();
        } else if (deleteElements.size() == 1) {
            Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, deleteElements.size() + " Notes Deleted", Toast.LENGTH_SHORT).show();
        }

        for (int i = 0; i < deleteElements.size(); i++) {

            titles.remove((int) deleteElements.get(i));
            content.remove((int) deleteElements.get(i));
            dates.remove((int) deleteElements.get(i));
            arrayAdapter.notifyDataSetChanged();

        }

        deleteElements.clear();
        add.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        for (int i = 0; i < listView.getCount(); i++) {

            listView.getChildAt(i).setScaleX(1.0f);
            listView.getChildAt(i).setScaleY(1.0f);

        }

        try {
            MainActivity.sharedPreferences.edit().putString("titles", ObjectSerializer.serialize(titles)).apply();
            MainActivity.sharedPreferences.edit().putString("content", ObjectSerializer.serialize(content)).apply();
            MainActivity.sharedPreferences.edit().putString("dates", ObjectSerializer.serialize(dates)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

        arrayAdapter.notifyDataSetChanged();

        isDelete = false;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                view.setScaleX(1.0f);
                view.setScaleY(1.0f);

                intent.putExtra("Title", titles.get(position));
                intent.putExtra("Content", content.get(position));
                intent.putExtra("Date", dates.get(position));
                intent.putExtra("Position", position);
                startActivity(intent);

            }
        });

    }

    public void back(View view) {

        add.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
        deleteElements.clear();

        for (int i = 0; i < listView.getCount(); i++) {

            listView.getChildAt(i).setScaleX(1.0f);
            listView.getChildAt(i).setScaleY(1.0f);

        }

        try {
            MainActivity.sharedPreferences.edit().putString("titles", ObjectSerializer.serialize(titles)).apply();
            MainActivity.sharedPreferences.edit().putString("content", ObjectSerializer.serialize(content)).apply();
            MainActivity.sharedPreferences.edit().putString("dates", ObjectSerializer.serialize(dates)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

        arrayAdapter.notifyDataSetChanged();

        isDelete = false;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                view.setScaleX(1.0f);
                view.setScaleY(1.0f);

                intent.putExtra("Title", titles.get(position));
                intent.putExtra("Content", content.get(position));
                intent.putExtra("Date", dates.get(position));
                intent.putExtra("Position", position);
                startActivity(intent);

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = (Button) findViewById(R.id.add);

        back = (ImageView) findViewById(R.id.back);
        delete = (ImageView) findViewById(R.id.delete);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.GONE);

        isDelete = false;

        /*
        Simple date format to get time can also be used as
        "HH:mm:ss"
         */
        //String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //Log.i("Date", date);

        intent = new Intent(getApplicationContext(), SecondActivity.class);

        sharedPreferences = this.getSharedPreferences("com.example.mynotes", Context.MODE_PRIVATE);

        sharedPreferences.edit().putBoolean("first run", sharedPreferences.getBoolean("first run", true)).apply();

        listView = findViewById(R.id.listView);

        titles = new ArrayList<String>();
        content = new ArrayList<String>();
        dates = new ArrayList<String>();
        deleteElements = new ArrayList<Integer>();

        titles.clear();
        content.clear();
        dates.clear();

        try {

            titles = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("titles", ObjectSerializer.serialize(new ArrayList<String>())));
            content = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("content", ObjectSerializer.serialize(new ArrayList<String>())));
            dates = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("dates", ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sharedPreferences.getBoolean("first run", false) == true) {

            titles.add("Example");
            content.add("Add your content here");
            dates.add(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
            Log.i("first run value", "" + sharedPreferences.getBoolean("first run", true));
            sharedPreferences.edit().putBoolean("first run", false).apply();
            Log.i("changed first run value", "" + sharedPreferences.getBoolean("first run", true));

        }

        //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        arrayAdapter = new MyListAdapter(this, R.layout.my_list_adapter, titles);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent.putExtra("Title", titles.get(position));
                intent.putExtra("Content", content.get(position));
                intent.putExtra("Date", dates.get(position));
                intent.putExtra("Position", position);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                isDelete = true;

                view.setScaleX(0.9f);
                view.setScaleY(0.9f);

                linearLayout.setVisibility(View.VISIBLE);

                add.setVisibility(View.GONE);

                deleteElements.add(position);

                if (isDelete = true) {

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            view.setScaleX(0.9f);
                            view.setScaleY(0.9f);

                            int f = 0;

                            for (int i = 0; i < deleteElements.size(); i++) {

                                if (deleteElements.get(i) == position) {
                                    view.setScaleX(1.0f);
                                    view.setScaleY(1.0f);
                                    deleteElements.remove(i);
                                    f = 1;
                                    break;
                                }

                            }

                            if (f == 0) {
                                deleteElements.add(position);
                                Log.i("Added to deleteElements", deleteElements.toString());
                            }

                        }
                    });
                }

                return true;

            }
        });

    }

}
