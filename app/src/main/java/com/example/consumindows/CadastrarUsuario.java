package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import modelo.Cliente;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        Button btcadastrar = findViewById(R.id.btnCadastrar);
        final EditText email = findViewById(R.id.etEmail);
        final EditText username = findViewById(R.id.etUsername);
        final EditText senha = findViewById(R.id.etSenha);
        final EditText cpf = findViewById(R.id.etCPF);
        final Cliente a = new Cliente();

        btcadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.setLogin(username.getText().toString());
                a.setEmail(email.getText().toString());
                a.setCPF(cpf.getText().toString());
                a.setSenha(senha.getText().toString());

                Call<Cliente> call = new RetrofitConfig().getWigService().cadastrarCliente(a);
                call.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {

                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {
                        Log.e("Wig", "Erro ao fazer request");
                    }
                });

            }
        });

    }

}
