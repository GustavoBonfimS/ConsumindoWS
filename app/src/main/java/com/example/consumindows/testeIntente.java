package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        Bundle b;
        b = i.getExtras();
        final TextView tvParam = findViewById(R.id.tvParam);
        final TextView tvLista = findViewById(R.id.tvLista);

        // tvParam.setText("Ola " + b.getString("login")); // se vier da tela de login
        // tvParam.setText("Ola " + b.getInt("id")); // se vier da tela de cadastro

        Call<List<Cliente>> call = new RetrofitConfig().getWigService().listarCliente();
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                List<Cliente> lista = response.body();
                assert lista != null;
                tvLista.setText(lista.get(1).getLogin());
                String login = lista.get(1).getLogin();
                Log.e("wig", "login: " + login);

            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                Log.e("wig", "erro ao se comunicar com o WS: " + t.getMessage());
            }
        });

    }
}
