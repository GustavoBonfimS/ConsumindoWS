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

    public void TesteAvaliaco() {
        this.TesteAvaliaco();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_avaliacao);
        // final EditText autor = findViewById(R.id.etAutor);
        Button btAvalia = findViewById(R.id.btAvaliacao);
        // final EditText conteudo = findViewById(R.id.etConteudo);
        final TextView resposta = findViewById(R.id.tvResposta);
        final Avaliacao a = new Avaliacao();

        btAvalia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //a.setAutor(autor.getText().toString());
                //a.setConteudo(conteudo.getText().toString());
                a.setIdcliente(1);
                a.setIdempresa(1);

                Call<Avaliacao> call = new RetrofitConfig().getWigService().inserirAvaliacao(a);
                call.enqueue(new Callback<Avaliacao>() {
                    @Override
                    public void onResponse(Call<Avaliacao> call, Response<Avaliacao> response) {
                        a.setIdavaliacao(response.body().getIdavaliacao());
                        resposta.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Avaliacao> call, Throwable t) {
                        Log.e("wig", "erro ao fazer request" + t.getMessage());
                    }
                });


                Toast.makeText(getBaseContext(), "id= :" + a.getIdavaliacao(), Toast.LENGTH_SHORT).show();
            } // onClick ends

        });


    }

}

