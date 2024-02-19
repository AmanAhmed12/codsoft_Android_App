package com.example.my_quote_app;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textQuote;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuoteServ quoteServ = new QuoteServ();
        String quote = quoteServ.getQuotes();
        Log.d("QuoteApp", "Generated Quote: " + quote);
        textQuote = findViewById(R.id.textQuote);
        textQuote.setText(quote);

        Button saveBtn = findViewById(R.id.saveQuote);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SavedActivity.class);
                intent.putExtra("quote", quote);
                startActivity(intent);


            }
        });


        Button viewBtn = findViewById(R.id.viewButton);

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SavedActivity.class);
                startActivity(intent);
                finish();
            }
        });



        Button shareBtn = findViewById(R.id.btnShare);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareMessage ="Quote of the day:" + quote;
                intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(intent, "Share using"));
                finish();

            }
        });



    }
}