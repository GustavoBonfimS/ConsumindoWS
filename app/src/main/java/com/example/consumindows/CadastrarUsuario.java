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

import modelo.Cliente;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // esconder barra de titulo
        setContentView(R.layout.activity_cadastrar_usuario);

        final Button btncadastrar = findViewById(R.id.btnCadastrar);
        final EditText email = findViewById(R.id.etEmail);
        final EditText login = findViewById(R.id.etLogin);
        final EditText senha = findViewById(R.id.etSenha);
        final EditText cpf = findViewById(R.id.etCPF);
        final TextView testeResposta = findViewById(R.id.tvCadastro);
        final Cliente c = new Cliente();

        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c.setEmail(email.getText().toString());
                c.setCPF(cpf.getInputType());
                c.setLogin(login.getText().toString());
                c.setSenha(senha.getText().toString());

                Call<Cliente> call = new RetrofitConfig().getWigService().cadastrarCliente(c);
                call.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                        if (response.body() != null) {
                            Cliente res = response.body();
                            Log.i("wig", "id= " + res.getIdcliente());

                            // após cadastro jogar pra index
                            Intent telaIndex = new Intent(CadastrarUsuario.this, testeIntente.class);
                            Bundle param = new Bundle();
                            param.putInt("id", res.getIdcliente());
                            telaIndex.putExtras(param);
                            startActivity(telaIndex);
                        } else {
                            // se der erro no cadastro
                            Toast.makeText(CadastrarUsuario.this, "Erro ao cadastrar",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {
                        Log.e("Wig", "Erro ao fazer request: " + t.getMessage());
                        Toast.makeText(CadastrarUsuario.this, "ERRO: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            } // onClick ends
        });

    }

}
