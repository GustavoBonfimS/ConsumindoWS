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
import modelo.RetrofitConfig;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Toolbar toolbar = findViewById(R.id.toolbar);
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

        Call<Cliente> call = new RetrofitConfig().getWigService().getCliente(clienteLogin);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                clienteOBJ = response.body();
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
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
