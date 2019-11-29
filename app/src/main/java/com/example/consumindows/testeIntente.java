package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import modelo.Cliente;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class testeIntente extends AppCompatActivity {

    ListView lvLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // esconde barra de titulo
        setContentView(R.layout.activity_teste_intente);

        lvLista = findViewById(R.id.lvLista);

        Call<List<Cliente>> call = new RetrofitConfig().getWigService().listarCliente();
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if (response.code() == 200 || response.isSuccessful()) {
                    List<Cliente> lista = response.body();

                    String[] testeFor = new String[lista.size()];
                    for (int i = 0; i < lista.size(); i++) {
                        testeFor[i] = lista.get(i).getLogin();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(testeIntente.this,
                            android.R.layout.simple_list_item_1,testeFor);
                    lvLista.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                Log.e("wig", "erro ao se comunicar com o WS: " + t.getMessage());
            }
        });


        // itemArray[0] = testeLogin;



        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(testeIntente.this, "click no item " + position, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
