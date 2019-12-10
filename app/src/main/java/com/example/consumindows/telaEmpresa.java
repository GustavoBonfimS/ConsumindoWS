package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    CardView cardView1;
    CardView cardView2;
    CardView cardView3;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tela_empresa);

        refreshLayout = findViewById(R.id.swipeRefreshEmpresa);
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
        cardView1 = findViewById(R.id.empresaCV1);
        cardView2 = findViewById(R.id.empresaCV2);
        cardView3 = findViewById(R.id.empresaCV3);
        nomeEmpresa = getIntent().getStringExtra("empresaNome");
        final String nomeEmpresaLogada = getIntent().getStringExtra("loginEmpresaLogada");

        empresa = (Empresa) getIntent().getSerializableExtra("empresa");


        // verifica o status do usuario logado (empresa, usuario normal ou convidado)
        if (getIntent().getStringExtra("status").equals("logado")) {
            cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        } else if (getIntent().getStringExtra("status").equals("empresa")) {
            if (nomeEmpresaLogada.equalsIgnoreCase(nomeEmpresa)) {
                // é passado o nome da empresa com redundcancia pois passando pelo metodo gera null ex
                // apenas se o nome da empresa logada for igual o nome da empresa visitada
                // sera exibido os botoes de responder
                // garantindo que só a propria empresa responda suas avaliações
                responder1.setVisibility(View.VISIBLE);
                responder2.setVisibility(View.VISIBLE);
                responder3.setVisibility(View.VISIBLE);

                avaliar.setVisibility(View.GONE);
                empresaLogada = (Empresa) getIntent().getSerializableExtra("empresaLogada");
            }
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

        // setar conteudo das avaliacoes mostradas na tela
        Call<List<Avaliacao>> call = new RetrofitConfig().getWigService().listarAvaliacaoDaEmpresa(empresa.getIdempresa());
        call.enqueue(new Callback<List<Avaliacao>>() {
            @Override
            public void onResponse(Call<List<Avaliacao>> call, Response<List<Avaliacao>> response) {
                if (response.code() == 200 || response.isSuccessful()) {
                    List<Avaliacao> lista = response.body();
                    switch (lista.size()) {
                        case 0:
                            autor1.setText("nenhuma avaliação foi feita hoje...");
                            conteudo1.setText("Avaliações recentes aparecerão aqui");

                            cardView2.setVisibility(View.GONE);
                            cardView3.setVisibility(View.GONE);
                            break;

                        case 1: // caso a lista só tenha 1 elemento
                            autor1.setText(lista.get(0).getAutor());
                            conteudo1.setText(lista.get(0).getConteudo());

                            cardView2.setVisibility(View.GONE);
                            cardView3.setVisibility(View.GONE);
                            break;
                        case 2: // caso a lista só tenha 2 elemento
                            autor1.setText(lista.get(0).getAutor());
                            conteudo1.setText(lista.get(0).getConteudo());

                            autor2.setText(lista.get(1).getAutor());
                            conteudo2.setText(lista.get(1).getConteudo());

                            cardView3.setVisibility(View.GONE);
                            break;
                        case 3: // caso a lista tenha 3 elementos
                            autor1.setText(lista.get(0).getAutor());
                            conteudo1.setText(lista.get(0).getConteudo());

                            autor2.setText(lista.get(1).getAutor());
                            conteudo2.setText(lista.get(1).getConteudo());

                            autor3.setText(lista.get(2).getAutor());
                            conteudo3.setText(lista.get(2).getConteudo());
                            break;
                    }
                    if (lista.size() > 3) {
                        autor1.setText(lista.get(0).getAutor());
                        conteudo1.setText(lista.get(0).getConteudo());

                        autor2.setText(lista.get(1).getAutor());
                        conteudo2.setText(lista.get(1).getConteudo());

                        autor3.setText(lista.get(2).getAutor());
                        conteudo3.setText(lista.get(2).getConteudo());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Avaliacao>> call, Throwable t) {
                Log.e("wig", "erro ao se comunicar com o WS: " + t.getMessage());
            }
        });

        // responder a primeira avaliacao mostrada na tela
        responder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telaResposta = new Intent(telaEmpresa.this, RespostaAvaliacaoActivity.class);
                Avaliacao a = new Avaliacao();
                a.setAutor(autor1.getText().toString());
                a.setConteudo(conteudo1.getText().toString());

                telaResposta.putExtra("avaliacao", a);
                telaResposta.putExtra("autor", nomeEmpresa);
                telaResposta.putExtra("empresa", empresaLogada);
                startActivity(telaResposta);
            }
        });

        // responder a segunda avaliacao mostrada na tela
        responder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telaResposta = new Intent(telaEmpresa.this, RespostaAvaliacaoActivity.class);
                Avaliacao a = new Avaliacao();
                a.setAutor(autor2.getText().toString());
                a.setConteudo(conteudo2.getText().toString());

                telaResposta.putExtra("avaliacao", a);
                telaResposta.putExtra("autor", nomeEmpresa);
                telaResposta.putExtra("empresa", empresaLogada);
                startActivity(telaResposta);
            }
        });

        // responder a terceira avaliacao mostrada na tela
        responder3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telaResposta = new Intent(telaEmpresa.this, RespostaAvaliacaoActivity.class);
                Avaliacao a = new Avaliacao();
                a.setAutor(autor3.getText().toString());
                a.setConteudo(conteudo3.getText().toString());

                telaResposta.putExtra("avaliacao", a);
                telaResposta.putExtra("autor", nomeEmpresa);
                telaResposta.putExtra("empresa", empresaLogada);
                startActivity(telaResposta);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<Avaliacao>> call = new RetrofitConfig().getWigService().listarAvaliacaoDaEmpresa(empresa.getIdempresa());
                call.enqueue(new Callback<List<Avaliacao>>() {
                    @Override
                    public void onResponse(Call<List<Avaliacao>> call, Response<List<Avaliacao>> response) {
                        if (response.code() == 200 || response.isSuccessful()) {
                            refreshLayout.setRefreshing(false);
                            List<Avaliacao> lista = response.body();
                            switch (lista.size()) {
                                case 0:
                                    autor1.setText("nenhuma avaliação foi feita hoje...");
                                    conteudo1.setText("Avaliações recentes aparecerão aqui");

                                    cardView2.setVisibility(View.GONE);
                                    cardView3.setVisibility(View.GONE);
                                    break;

                                case 1: // caso a lista só tenha 1 elemento
                                    autor1.setText(lista.get(0).getAutor());
                                    conteudo1.setText(lista.get(0).getConteudo());

                                    cardView2.setVisibility(View.GONE);
                                    cardView3.setVisibility(View.GONE);
                                    break;
                                case 2: // caso a lista só tenha 2 elemento
                                    autor1.setText(lista.get(0).getAutor());
                                    conteudo1.setText(lista.get(0).getConteudo());

                                    autor2.setText(lista.get(1).getAutor());
                                    conteudo2.setText(lista.get(1).getConteudo());

                                    cardView3.setVisibility(View.GONE);
                                    break;
                                case 3: // caso a lista tenha 3 elementos
                                    autor1.setText(lista.get(0).getAutor());
                                    conteudo1.setText(lista.get(0).getConteudo());

                                    autor2.setText(lista.get(1).getAutor());
                                    conteudo2.setText(lista.get(1).getConteudo());

                                    autor3.setText(lista.get(2).getAutor());
                                    conteudo3.setText(lista.get(2).getConteudo());
                                    break;
                            }
                            if (lista.size() > 3) {
                                autor1.setText(lista.get(0).getAutor());
                                conteudo1.setText(lista.get(0).getConteudo());

                                autor2.setText(lista.get(1).getAutor());
                                conteudo2.setText(lista.get(1).getConteudo());

                                autor3.setText(lista.get(2).getAutor());
                                conteudo3.setText(lista.get(2).getConteudo());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Avaliacao>> call, Throwable t) {
                        Log.e("wig", "erro ao se comunicar com o WS: " + t.getMessage());
                    }
                });
            }
        });
    }
}
