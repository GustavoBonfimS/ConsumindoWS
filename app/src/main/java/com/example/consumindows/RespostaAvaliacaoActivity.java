package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Time;

import modelo.Avaliacao;
import modelo.Cliente;
import modelo.Empresa;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RespostaAvaliacaoActivity extends AppCompatActivity {
    Empresa empresa;
    Avaliacao avaliacao;
    EditText conteudo;
    Button responder;
    Button voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resposta_avaliacao);
        getSupportActionBar().hide();

        empresa = (Empresa) getIntent().getSerializableExtra("empresa");
        avaliacao = (Avaliacao) getIntent().getSerializableExtra("avaliacao");
        conteudo = findViewById(R.id.etConteudo);
        responder = findViewById(R.id.btnResponder);
        voltar = findViewById(R.id.btnVoltar);

        // a resosta é feita com o mesmo objeto Avaliacao das avaliações dos usuarios,
        // porem armazenadas em uma tabela diferente com ligação com as de usurios

        // busca avaliacao pelo conteudo para obter id
        Call<Avaliacao> call = new RetrofitConfig().getWigService().getAvaliacao(avaliacao.getConteudo());
        call.enqueue(new Callback<Avaliacao>() {
            @Override
            public void onResponse(Call<Avaliacao> call, Response<Avaliacao> response) {
                if (response.code() == 200 || response.isSuccessful()) {
                    avaliacao = response.body();
                }
            }

            @Override
            public void onFailure(Call<Avaliacao> call, Throwable t) {
                Log.e("wig", "erro ao se comunicar com o WS: " + t.getMessage());
            }
        });

        responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // seta conteudo da resposta da resposta da empresa
                avaliacao.setConteudo(conteudo.getText().toString());

                // busca o nome da emrpesa assim pois passando no objeto.getAutor gera null ex
                String nomeDaEmpresa = getIntent().getStringExtra("autor");
                avaliacao.setAutor(nomeDaEmpresa);

                // pega data atual do sistema
                java.util.Date dataUtil = new java.util.Date();
                Date data = new Date(dataUtil.getTime());
                avaliacao.setData(data);

                // pegar hora atual do sistema;
                Time hora = new Time(dataUtil.getTime());
                avaliacao.setHora(hora);

                // metodo para responder avliacoes, retorna true ou false
                Call<String> call = new RetrofitConfig().getWigService().responderAvaliacao(avaliacao);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("true")) {
                            Toast.makeText(RespostaAvaliacaoActivity.this
                                    , "respondido com sucesso", Toast.LENGTH_SHORT).show();
                            responder.setVisibility(View.GONE);
                            voltar.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("wig", "erro ao se comunicar com o WS: " + t.getMessage());
                    }
                });

            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
