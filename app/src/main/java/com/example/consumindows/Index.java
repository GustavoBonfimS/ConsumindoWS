package com.example.consumindows;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.List;

import modelo.Avaliacao;
import modelo.Cliente;
import modelo.Empresa;
import modelo.RetrofitConfig;
import modelo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Index extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    Cliente clienteOBJ;
    private String clienteLogin;
    String status;
    TextView autor1;
    TextView conteudo1;
    Empresa empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        autor1 = findViewById(R.id.tvAutor1);
        conteudo1 = findViewById(R.id.tvConteudo1);

        Bundle b = getIntent().getExtras();
        if (b.getString("login").equals("convidado")) {
            Toast.makeText(this, "Você entrou como convidado", Toast.LENGTH_LONG).show();
            toolbar.setVisibility(View.GONE);
            status = "convidado";
        } else {
            clienteLogin = b.getString("login");
            status = "logado";
        }

        // buscar usuario pra ver se o usuario logado é uma empresa
        Call<Usuario> buscarUsuario = new RetrofitConfig().getWigService().buscarUsuario(clienteLogin);
        buscarUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.body().getPerfil().equals("empresa")) {
                    // caso for, buscar objeto completo de empresa
                    status = "empresa";
                    toolbar.setVisibility(View.GONE);

                    Call<Empresa> getEmpresa = new RetrofitConfig()
                            .getWigService().getEmpresaPeloNome(clienteLogin);
                    getEmpresa.enqueue(new Callback<Empresa>() {
                        @Override
                        public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                            empresa = response.body();
                        }

                        @Override
                        public void onFailure(Call<Empresa> call, Throwable t) {
                            Log.e("wig", "erro ao fazer request" + t.getMessage());
                        }
                    });
                } else {
                    // caso não for empresa sera buscado o objeto de usuario
                    Call<Cliente> getCliente = new RetrofitConfig().getWigService().getCliente(clienteLogin);
                    getCliente.enqueue(new Callback<Cliente>() {
                        @Override
                        public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                            if (response.code() == 200 || response.isSuccessful()) {
                                if (response.body() != null) {
                                    clienteOBJ = response.body();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Cliente> call, Throwable t) {
                            Log.e("wig", "erro ao fazer request" + t.getMessage());
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("wig", "erro ao fazer request" + t.getMessage());
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telaPesquisa = new Intent(Index.this, TelaPesquisa.class);
                telaPesquisa.putExtra("cliente", clienteOBJ);
                telaPesquisa.putExtra("status", status);
                if (empresa != null) {
                    // caso seja empresa sera passado o obj para a tela de pesquisa
                    telaPesquisa.putExtra("empresa", empresa);
                }
                startActivity(telaPesquisa);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(

                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    // não deixar voltar para tela de login ao pressionar botao voltar
    // (caso esteja logado)
    @Override
    public void onBackPressed() {
        if (status.equals("convidado")) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void Sair(MenuItem item) {
        Intent telaLogin = new Intent(this, loginActivity.class);
        startActivity(telaLogin);
    }
}
