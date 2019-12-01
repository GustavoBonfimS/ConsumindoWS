package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class TesteAvaliacao extends AppCompatActivity {
    Empresa empresa;
    Cliente cliente;

    public void TesteAvaliaco() {
        this.TesteAvaliaco();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_avaliacao);
        final EditText autor = findViewById(R.id.etAutor);
        Button btAvalia = findViewById(R.id.btAvaliacao);
        final EditText conteudo = findViewById(R.id.etConteudo);
        final TextView resposta = findViewById(R.id.tvResposta);
        final Avaliacao a = new Avaliacao();

        empresa = (Empresa) getIntent().getSerializableExtra("empresa");
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");

        btAvalia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a.setAutor(autor.getText().toString());
                a.setConteudo(conteudo.getText().toString());
                a.setIdcliente(cliente.getIdcliente());
                a.setIdempresa(empresa.getIdempresa());

                // pega data atual do sistema
                java.util.Date dataUtil = new java.util.Date();
                Date data = new Date(dataUtil.getTime());
                a.setData(data);

                // pegar hora atual do sistema;
                Time hora = new Time(dataUtil.getTime());
                a.setHora(hora);

                Call<Avaliacao> call = new RetrofitConfig().getWigService().inserirAvaliacao(a);
                call.enqueue(new Callback<Avaliacao>() {
                    @Override
                    public void onResponse(Call<Avaliacao> call, Response<Avaliacao> response) {
                        if (response.code() == 200 || response.isSuccessful()) {
                            Toast.makeText(TesteAvaliacao.this, "Avaliado com sucesso!"
                                    , Toast.LENGTH_SHORT).show();
                            a.setIdavaliacao(response.body().getIdavaliacao());
                            Intent telaVolta = new Intent(TesteAvaliacao.this, telaEmpresa.class);
                            startActivity(telaVolta);
                        }
                    }

                    @Override
                    public void onFailure(Call<Avaliacao> call, Throwable t) {
                        Log.e("wig", "erro ao fazer request" + t.getMessage());
                    }
                });


                // Toast.makeText(getBaseContext(), "id= :" + a.getIdavaliacao(), Toast.LENGTH_SHORT).show();
            } // onClick ends

        });


    }

}

