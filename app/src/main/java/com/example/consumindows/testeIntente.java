package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import modelo.Cliente;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class testeIntente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_intente);

        Intent i = getIntent();
        Bundle b = new Bundle();
        b = i.getExtras();
        final TextView tvParam = findViewById(R.id.tvParam);
        final TextView tvLista = findViewById(R.id.tvLista);

        tvParam.setText("Ola " + b.getString("login"));

        Call<List<Cliente>> call = new RetrofitConfig().getWigService().listarCliente();
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                List<Cliente> lista = response.body();
                tvLista.setText(lista.toString());
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {

            }
        });

    }
}
