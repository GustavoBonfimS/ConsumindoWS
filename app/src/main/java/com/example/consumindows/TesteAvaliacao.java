package com.example.consumindows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import modelo.Avaliacao;
import modelo.RetrofitConfig;
import retrofit2.Call;

public class TesteAvaliacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_avaliacao);
        TextView tAvalia = (TextView) findViewById(R.id.etAutor);
        Button btAvalia = (Button) findViewById(R.id.btAvaliacao);
        TextView tAvalia2 = (TextView) findViewById(R.id.btAvaliacao);

        final Avaliacao a = new Avaliacao();
        a.setAutor(tAvalia.getText().toString());
        a.setConteudo(tAvalia2.getText().toString());
        a.setIdcliente(1);
        a.setIdempresa(1);
    }
    public void onClickbtAvalia{
        Call<Avaliacao> call = new RetrofitConfig().getWigService().inserirAvaliacao();
        call.enqueue();
    }
}
