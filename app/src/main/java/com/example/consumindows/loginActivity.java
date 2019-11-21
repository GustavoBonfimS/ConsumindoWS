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

import modelo.RetrofitConfig;
import modelo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText login = findViewById(R.id.etavaliacao);
        final EditText senha = findViewById(R.id.etSenha);

        final Button btnLogin = findViewById(R.id.btnLogin);
        final TextView cadastrar = findViewById(R.id.tvCadastrar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario u = new Usuario();
                u.setLogin(login.getText().toString());
                u.setSenha(senha.getText().toString());

                Call<String> call = new RetrofitConfig().getWigService()
                        .validarLogin(login.getText().toString()
                        , senha.getText().toString());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        // status.setText(response.body());

                        if (response.body().equals("true")) {
                            Intent telaMainTeste = new Intent(loginActivity.this, testeIntente.class);
                            Bundle param = new Bundle();
                            param.putString("login", login.getText().toString());

                            telaMainTeste.putExtras(param);
                            startActivity(telaMainTeste);
                        } else {
                            Toast.makeText(loginActivity.this, "Nome de usuairo ou senha icnorretos",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("wig", "erro ao se comunicar com o WS: " + t.getMessage());
                    }
                });
            }
        });
        cadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent telaCadastro = new Intent(loginActivity.this, Index.class);
                startActivity(telaCadastro);
            }
        });

    }


}
