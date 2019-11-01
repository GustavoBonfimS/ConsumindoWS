package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import modelo.Avaliacao;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TesteAvaliacao extends AppCompatActivity {

    public void TesteAvaliaco () {
        this.TesteAvaliaco();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_avaliacao);
        EditText autor = findViewById(R.id.etAutor);
        Button btAvalia = findViewById(R.id.btAvaliacao);
        EditText conteudo = findViewById(R.id.etConteudo);

        final Avaliacao a = new Avaliacao();
        a.setAutor(autor.getText().toString());
        a.setConteudo(conteudo.getText().toString());
        a.setIdcliente(1);
        a.setIdempresa(1);

        final String[] resposta = new String[1];

        btAvalia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Avaliacao> call = new RetrofitConfig().getWigService().inserirAvaliacao(a);
                call.enqueue(new Callback<Avaliacao>() {
                    @Override
                    public void onResponse(Call<Avaliacao> call, Response<Avaliacao> response) {
                        resposta[0] = response.body().toString();
                    }

                    @Override
                    public void onFailure(Call<Avaliacao> call, Throwable t) {
                        Log.e("Wig", "Erro ao fazer request");
                    }
                });
            }
        });

        if (resposta[0] != null) {
            Toast.makeText(getBaseContext(), resposta[0], Toast.LENGTH_SHORT).show();
        }
    }

}

