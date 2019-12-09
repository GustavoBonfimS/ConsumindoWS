package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import modelo.RetrofitConfig;
import modelo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        A PRIMEIRA TELA FEITA, o começo de tudo
    não foi apagada por motivo de nostalgia de começo de projeto,
    é notavel a linha de aprendizado gradativa que se cria com a pratica
         */

        final EditText username = findViewById(R.id.etUsername);
        final TextView resposta = findViewById(R.id.tvResposta);
        Button btnBuscar = findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Call<Usuario> call = new RetrofitConfig().getWigService().buscarUsuario(username.getText().toString());
                call.enqueue(new Callback<Usuario>() {

                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        // pegar a resposta
                        Usuario usuario = response.body();
                        resposta.setText("Email : " + usuario.getEmail() + "\n"
                                + "Senha: " + usuario.getSenha() + "\n"
                                + "Perfil: " + usuario.getPerfil());
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        // tratar algum erro
                        Log.e("WigService  ", "Erro ao buscar usuario");
                    }
                });
            }
        });
    }
}
