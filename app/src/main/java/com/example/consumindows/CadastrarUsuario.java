package com.example.consumindows;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
    EditText etLogin;
    EditText etEmail;
    EditText etSenha;
    EditText etCpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // esconder barra de titulo
        setContentView(R.layout.activity_cadastrar_usuario);

        final Button btncadastrar = findViewById(R.id.btnCadastrar);
        etEmail = findViewById(R.id.etEmail);
        etLogin = findViewById(R.id.etLogin);
        etSenha = findViewById(R.id.etSenha);
        etCpf = findViewById(R.id.etCPF);
        final Cliente c = new Cliente();

        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validaCampos()) {

                    c.setEmail(etEmail.getText().toString());
                    c.setCPF(etCpf.getText().toString());
                    c.setLogin(etLogin.getText().toString());
                    c.setSenha(etSenha.getText().toString());

                    Call<Cliente> call = new RetrofitConfig().getWigService().cadastrarCliente(c);
                    call.enqueue(new Callback<Cliente>() {
                        @Override
                        public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                            if (response.body() != null) {
                                Cliente res = response.body();

                                // após cadastro jogar pra index
                                Intent telaIndex = new Intent(CadastrarUsuario.this, Index.class);
                                Bundle param = new Bundle();
                                param.putString("login", res.getLogin());
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
                }


            } // onClick ends
        });

    }

    private boolean validaCampos() {
        boolean res = false;
        String login = etLogin.getText().toString();
        String senha = etSenha.getText().toString();
        String email = etEmail.getText().toString();
        String CPF = etCpf.getText().toString();

        if (res = isCampoVazio(login)) {
            etLogin.requestFocus();
        } else if (res = isCampoVazio(senha)) {
            etSenha.requestFocus();
        } else if (res = isCampoVazio(email) && isEmailValido(email)) {
            etEmail.requestFocus();
        } else if (res = isCampoVazio(CPF)) {
            etCpf.requestFocus();
        }

        if (res) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage("Há campos vazios ou em branco");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        return res;
    }

    private boolean isCampoVazio(String valor) {
        boolean resultado = (TextUtils.isEmpty(valor)) || valor.trim().isEmpty();
        return resultado;
    }

    private boolean isEmailValido(String email) {
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }

}
