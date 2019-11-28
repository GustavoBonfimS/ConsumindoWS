package com.example.consumindows;

import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import modelo.Empresa;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaPesquisa extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pesquisa);


        final SearchView pesquisar = (SearchView) findViewById(R.id.search);
        pesquisar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
                Call<List<Empresa>> call new RetrofitConfig().getWigService().pesquisa();
                        call.enqueue(new Callback<List<Empresa>>() {
                            @Override
                            public void onResponse(Call<List<Empresa>> call, Response<List<Empresa>> response) {

                            }

                            @Override
                            public void onFailure(Call<List<Empresa>> call, Throwable t) {

                            }
                        });

            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

}
