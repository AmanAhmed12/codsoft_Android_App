package com.example.my_quote_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import java.util.ArrayList;

public class SavedActivity extends AppCompatActivity {
    private static ArrayList<String> dataArray = new ArrayList<>();
    private static ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataArray);
        String quote = getIntent().getStringExtra("quote");
        if (quote != null && !quote.isEmpty()) {

            saveQuote(quote);
        }
        ListView listView = findViewById(R.id.lstView);
        listView.setAdapter(adapter);

        Button backBtn = findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public static void saveQuote(String text) {
        dataArray.add(text);
        adapter.notifyDataSetChanged();
    }
}