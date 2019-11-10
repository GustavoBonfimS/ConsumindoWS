package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class testeIntente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_intente);

        Intent i = getIntent();
        Bundle b = new Bundle();
        b = i.getExtras();
        final TextView tvParam = findViewById(R.id.tvParam);

        tvParam.setText("Ola " + b.getString("login"));

    }
}
