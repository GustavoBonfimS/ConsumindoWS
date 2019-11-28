package com.example.consumindows;

import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

public class TelaPesquisa extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pesquisa);

        SearchView pesquisar = (SearchView) findViewById(R.id.search);
        pesquisar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

}
