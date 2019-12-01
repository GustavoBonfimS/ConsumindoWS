package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import modelo.Cliente;
import modelo.Empresa;

public class telaEmpresa extends AppCompatActivity {

    TextView nome;
    TextView endereco;
    TextView tipo;
    Button avaliar;
    Empresa empresa;
    Cliente cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tela_empresa);

        nome = findViewById(R.id.tvNomeEmpresa);
        endereco = findViewById(R.id.tvEnderecoEmpresa);
        tipo = findViewById(R.id.tvTipoEmpresa);
        avaliar = findViewById(R.id.btnAvaliar);

        empresa = (Empresa) getIntent().getSerializableExtra("empresa");
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");

        nome.setText(empresa.getLogin());
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

    }
}
