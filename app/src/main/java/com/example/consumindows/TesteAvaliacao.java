package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import modelo.Avaliacao;
import modelo.RetrofitConfig;
import retrofit2.Call;

public class TesteAvaliacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_avaliacao);
        TextView tAvalia = (TextView) findViewById(R.id.editText);
        Button btAvalia = (Button) findViewById(R.id.btAvaliacao);
    }
    public void onClickbtAvalia{
        Call<Avaliacao> call = RetrofitConfig.

            }
}
