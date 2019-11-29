package com.example.consumindows;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import modelo.Empresa;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaPesquisa extends AppCompatActivity {
    ListView listView;

    public void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pesquisa);

        listView = findViewById(R.id.lvLista);

        final SearchView pesquisar = (SearchView) findViewById(R.id.search);
        pesquisar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s != null) {
                    Call<List<Empresa>> call = new RetrofitConfig().getWigService().pesquisa(s);
                    call.enqueue(new Callback<List<Empresa>>() {
                        @Override
                        public void onResponse(Call<List<Empresa>> call, Response<List<Empresa>> response) {
                            if (response.code() == 200 || response.isSuccessful()) {
                                List<Empresa> lista = response.body();

                                String[] array = new String[lista.size()];
                                for (int i = 0; i < lista.size(); i++) {
                                    array[i] = lista.get(i).getLogin();
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TelaPesquisa.this,
                                        android.R.layout.simple_list_item_1, array);
                                listView.setAdapter(adapter);

                            }

                        }

                        @Override
                        public void onFailure(Call<List<Empresa>> call, Throwable t) {
                            Log.e("wig", "erro ao se comunicar com o WS: " + t.getMessage());
                        }
                    });
                }


                Toast.makeText(TelaPesquisa.this, "pesquisou: " + s, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

}
