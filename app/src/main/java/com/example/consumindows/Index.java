package com.example.consumindows;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
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
import java.sql.Date;

import modelo.Cliente;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Index extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    Cliente clienteOBJ;
    private String clienteLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        clienteLogin = b.getString("login");

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

        /*
        Call<List<Avaliacao>> call = new RetrofitConfig().getWigService().atualizarIndex(clienteOBJ.getIdcliente());
        call.enqueue(new Callback<List<Avaliacao>>() {
            @Override
            public void onResponse(Call<List<Avaliacao>> call, Response<List<Avaliacao>> response) {
                // setar texto dos textView
            }

            @Override
            public void onFailure(Call<List<Avaliacao>> call, Throwable t) {
                Log.e("wig", "erro ao fazer request" + t.getMessage());
            }
        });

         */


    }

    // não deixar voltar para tela de login
    @Override
    public void onBackPressed() {

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

    public boolean isLastCheckToday(Date lastCheck) {
        // pega data atual do sistema
        java.util.Date dataUtil = new java.util.Date();
        Date dataAtual = new Date(dataUtil.getTime());

        if (lastCheck.equals(dataAtual)) {
            return true;
        } else {
            // HomeDAO dao = new HomeDAO(this);
            // dao.inserirLastCheck(lastCheck);
            return false;
        }
    }
}
