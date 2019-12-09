package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import modelo.Avaliacao;
import modelo.Cliente;
import modelo.Empresa;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class telaEmpresa extends AppCompatActivity {

    TextView nome;
    String nomeEmpresa;
    TextView endereco;
    TextView tipo;
    Button avaliar;
    Empresa empresa;
    Cliente cliente;
    TextView responder1;
    TextView responder2;
    TextView responder3;
    TextView autor1;
    TextView conteudo1;
    TextView autor2;
    TextView conteudo2;
    TextView autor3;
    TextView conteudo3;
    Empresa empresaLogada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tela_empresa);

        nome = findViewById(R.id.tvNomeEmpresa);
        endereco = findViewById(R.id.tvEnderecoEmpresa);
        tipo = findViewById(R.id.tvTipoEmpresa);
        avaliar = findViewById(R.id.btnAvaliar);
        autor1 = findViewById(R.id.tvAutor1);
        conteudo1 = findViewById(R.id.tvConteudo1);
        autor2 = findViewById(R.id.tvAutor2);
        conteudo2 = findViewById(R.id.tvConteudo2);
        autor3 = findViewById(R.id.tvAutor3);
        conteudo3 = findViewById(R.id.tvConteudo3);
        responder1 = findViewById(R.id.tvResponder1);
        responder2 = findViewById(R.id.tvResponder2);
        responder3 = findViewById(R.id.tvResponder3);
        nomeEmpresa = getIntent().getStringExtra("empresaNome");

        empresa = (Empresa) getIntent().getSerializableExtra("empresa");


        if (getIntent().getStringExtra("status").equals("logado")) {
            cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        } else if (getIntent().getStringExtra("status").equals("empresa")) {
            responder1.setVisibility(View.VISIBLE);
            responder2.setVisibility(View.VISIBLE);
            responder3.setVisibility(View.VISIBLE);
            responder1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(telaEmpresa.this, "funcionando...", Toast.LENGTH_SHORT).show();
                }
            });
            avaliar.setVisibility(View.GONE);
            empresaLogada = (Empresa) getIntent().getSerializableExtra("empresaLogada");
        } else avaliar.setVisibility(View.GONE);

        nome.setText(nomeEmpresa);
        endereco.setText(empresa.getEndereco());
        tipo.setText(empresa.getTipo());

        avaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telaAvaliacao = new Intent(telaEmpresa.this, TesteAvaliacao.class);
                telaAvaliacao.putExtra("empresa", empresa);
                telaAvaliacao.putExtra("cliente", cliente);
                startActivity(telaAvaliacao);
            }
        });

        Call<List<Avaliacao>> call = new RetrofitConfig().getWigService().listarAvaliacao();
        call.enqueue(new Callback<List<Avaliacao>>() {
            @Override
            public void onResponse(Call<List<Avaliacao>> call, Response<List<Avaliacao>> response) {
                if (response.code() == 200 || response.isSuccessful()) {
                    List<Avaliacao> lista = response.body();
                    autor1.setText(lista.get(0).getAutor());
                    conteudo1.setText(lista.get(0).getConteudo());

                    autor2.setText(lista.get(1).getAutor());
                    conteudo2.setText(lista.get(1).getConteudo());

                    autor3.setText(lista.get(3).getAutor());;
                    conteudo3.setText(lista.get(3).getConteudo());
                }
            }

            @Override
            public void onFailure(Call<List<Avaliacao>> call, Throwable t) {
                Log.e("wig", "erro ao se comunicar com o WS: " + t.getMessage());
            }
        });

    }
}
