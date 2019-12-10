package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import modelo.Avaliacao;
import modelo.Empresa;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoAvaliacaoActivity extends AppCompatActivity {
    Avaliacao avalicao;
    Empresa empresa;
    TextView tvEmpresa;
    TextView conteudo;
    TextView data;
    TextView hora;
    CardView respostaCV;
    TextView autorResposta;
    TextView conteudoResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_avaliacao);
        getSupportActionBar().hide();

        tvEmpresa = findViewById(R.id.tvEmpresa);
        conteudo = findViewById(R.id.tvConteudo);
        data = findViewById(R.id.tvDAta);
        hora = findViewById(R.id.tvHora);
        respostaCV = findViewById(R.id.respostaCV);
        autorResposta = findViewById(R.id.tvAutorResposta);
        conteudoResposta = findViewById(R.id.tvConteudoResposta);

        avalicao = (Avaliacao) getIntent().getSerializableExtra("avaliacao");

        Call<Empresa> call = new RetrofitConfig().getWigService()
                .getEmpresaPeloID(avalicao.getIdempresa());
        call.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                if (response.code() == 200 || response.isSuccessful()) {
                    empresa = response.body();
                    tvEmpresa.setText(empresa.getLogin());
                    conteudo.setText(avalicao.getConteudo());

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    data.setText(sdf.format(avalicao.getData()));

                    hora.setText(avalicao.getHora().toString());

                }
            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {
                Log.e("wig", "erro ao fazer request" + t.getMessage());
            }
        });

        Call<Avaliacao> resposta = new RetrofitConfig().getWigService().getResposta(avalicao.getIdavaliacao());
        resposta.enqueue(new Callback<Avaliacao>() {
            @Override
            public void onResponse(Call<Avaliacao> call, Response<Avaliacao> response) {
                if (response.code() == 200 || response.isSuccessful()) {
                    if (response.body() != null) {
                        respostaCV.setVisibility(View.VISIBLE);
                        autorResposta.setText(response.body().getAutor());
                        conteudoResposta.setText(response.body().getConteudo());
                    } else {
                        Toast.makeText(InfoAvaliacaoActivity.this
                                , "A empresa ainda não deu nenhuma resposta", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Avaliacao> call, Throwable t) {
                Log.e("wig", "erro ao fazer request" + t.getMessage());
            }
        });
    }
}
