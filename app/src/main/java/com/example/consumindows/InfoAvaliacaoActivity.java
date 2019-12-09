package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_avaliacao);
        getSupportActionBar().hide();

        tvEmpresa = findViewById(R.id.tvEmpresa);
        conteudo = findViewById(R.id.tvConteudo);
        data = findViewById(R.id.tvDAta);
        hora = findViewById(R.id.tvHora);

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

    }
}
