package com.example.consumindows.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.consumindows.R;

import java.util.List;

import modelo.Avaliacao;
import modelo.Cliente;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView autor1 = root.findViewById(R.id.tvAutor1);
        final TextView conteudo1 = root.findViewById(R.id.tvConteudo1);
        Bundle b = getActivity().getIntent().getExtras();
        String clienteLogin = b.getString("login");
        final TextView autor2 = root.findViewById(R.id.tvAutor3);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // setar conteudo index aqui

                Call<List<Avaliacao>> call = new RetrofitConfig().getWigService().listarAvaliacao();
                call.enqueue(new Callback<List<Avaliacao>>() {
                    @Override
                    public void onResponse(Call<List<Avaliacao>> call, Response<List<Avaliacao>> response) {
                        List<Avaliacao> lista = response.body();
                        Log.e("wig", "erro: " + response.message());
                        autor1.setText(lista.get(0).getAutor());
                        conteudo1.setText(lista.get(0).getConteudo());
                    }

                    @Override
                    public void onFailure(Call<List<Avaliacao>> call, Throwable t) {
                        Log.e("wig", "erro:" + t.getMessage());
                    }
                });
            }
        });
        return root;
    }
}