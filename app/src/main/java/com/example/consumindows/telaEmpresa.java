package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import modelo.Empresa;

public class telaEmpresa extends AppCompatActivity {

    TextView nome;
    TextView endereco;
    TextView tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tela_empresa);

        nome = findViewById(R.id.tvNomeEmpresa);
        endereco = findViewById(R.id.tvEnderecoEmpres);
        tipo = findViewById(R.id.tvTipo);

        Empresa empresa = (Empresa) getIntent().getSerializableExtra("empresa");

        nome.setText(empresa.getLogin());
        endereco.setText(empresa.getEndereco());
        tipo.setText(empresa.getTipo());

    }
}
