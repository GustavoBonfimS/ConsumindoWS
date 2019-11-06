package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        final Button btncadastrar = findViewById(R.id.btnCadastrar);
        final EditText email = findViewById(R.id.etLogin);
        final EditText username = findViewById(R.id.etUsername);
        final EditText senha = findViewById(R.id.etSenha);
        final EditText cpf = findViewById(R.id.etCPF);
        final Cliente c = new Cliente();

        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.setEmail(email.getText().toString());
                c.setCPF(cpf.getText().toString());
                c.setLogin(username.getText().toString());
                c.setSenha(senha.getText().toString());


                Call<Cliente> call = new RetrofitConfig().getWigService().cadastrarCliente(c);
                call.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                        if (response.body() != null) {
                            c.setIdcliente(response.body().getIdcliente());
                            Toast.makeText(CadastrarUsuario.this, "Sucesso! id=" + response.body().getIdcliente(),
                                    Toast.LENGTH_SHORT).show();

                            btncadastrar.setVisibility(View.INVISIBLE); //tentativa de esconder o botão depois do click
                        }
                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {
                        Log.e("Wig", "Erro ao fazer request: " + t.getMessage());
                        Toast.makeText(CadastrarUsuario.this, "ERRO: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                // Toast.makeText(CadastrarUsuario.this, "id= " + c.getIdcliente(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
