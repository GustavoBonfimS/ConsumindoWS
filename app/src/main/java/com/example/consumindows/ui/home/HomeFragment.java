package com.example.consumindows.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView autor1 = root.findViewById(R.id.tvAutor1);
        final TextView conteudo1 = root.findViewById(R.id.tvConteudo1);
        Bundle b = getActivity().getIntent().getExtras();
        final String clienteLogin = b.getString("login");
        final TextView autor2 = root.findViewById(R.id.tvAutor3);
        final TextView conteudo2 = root.findViewById(R.id.tvConteudo2);
        final ConstraintLayout cardView2 = root.findViewById(R.id.cardView2);
        final TextView empresaCV1 = root.findViewById(R.id.tvEmpresaCV1);
        final TextView empresaCV2 = root.findViewById(R.id.tvEmpresaCV2);
        final TextView empresaCV3 = root.findViewById(R.id.tvEmpresaCV3);
        final ConstraintLayout cardView3 = root.findViewById(R.id.cardView3);
        final TextView autor3 = root.findViewById(R.id.tvAutor3);
        final TextView conteudo3 = root.findViewById(R.id.tvConteudo3);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // setar conteudo index aqui

                Call<List<Avaliacao>> call = new RetrofitConfig().getWigService().atualizarIndex(clienteLogin);
                call.enqueue(new Callback<List<Avaliacao>>() {
                    @Override
                    public void onResponse(Call<List<Avaliacao>> call, Response<List<Avaliacao>> response) {
                        if (response.code() == 200 || response.isSuccessful()) {
                            List<Avaliacao> lista = response.body();
                            switch (lista.size()) {
                                case 0:
                                    autor1.setText("nenhuma avaliação foi feita hoje...");
                                    conteudo1.setText("Avaliações recentes aparecerão aqui");

                                    cardView2.setVisibility(View.GONE);
                                    cardView3.setVisibility(View.GONE);
                                    break;

                                case 1: // caso a lista só tenha 1 elemento
                                    autor1.setText(lista.get(0).getAutor());
                                    conteudo1.setText(lista.get(0).getConteudo());
                                    empresaCV1.setText(lista.get(0).getEmpresa());
                                    // Toast.makeText(getContext(), "apenas 1 nova avaliação", Toast.LENGTH_SHORT).show();
                                    cardView2.setVisibility(View.GONE);
                                    cardView3.setVisibility(View.GONE);
                                    break;
                                case 2: // caso a lista só tenha 2 elemento
                                    autor1.setText(lista.get(0).getAutor());
                                    conteudo1.setText(lista.get(0).getConteudo());
                                    empresaCV1.setText(lista.get(0).getEmpresa());

                                    autor2.setText(lista.get(1).getAutor());
                                    conteudo2.setText(lista.get(1).getConteudo());
                                    empresaCV2.setText(lista.get(1).getEmpresa());
                                    cardView3.setVisibility(View.GONE);;
                                    break;
                                case 3: // caso a lista tenha 3 elementos
                                    autor1.setText(lista.get(0).getAutor());
                                    conteudo1.setText(lista.get(0).getConteudo());
                                    empresaCV1.setText(lista.get(0).getEmpresa());

                                    autor2.setText(lista.get(1).getAutor());
                                    conteudo2.setText(lista.get(1).getConteudo());
                                    empresaCV2.setText(lista.get(1).getEmpresa());

                                    autor3.setText(lista.get(2).getAutor());
                                    conteudo3.setText(lista.get(2).getConteudo());
                                    empresaCV3.setText(lista.get(2).getEmpresa());
                                    break;
                            }
                            if (lista.size() > 3) {
                                autor1.setText(lista.get(0).getAutor());
                                conteudo1.setText(lista.get(0).getConteudo());
                                empresaCV1.setText(lista.get(0).getEmpresa());

                                autor2.setText(lista.get(1).getAutor());
                                conteudo2.setText(lista.get(1).getConteudo());
                                empresaCV2.setText(lista.get(1).getEmpresa());

                                autor3.setText(lista.get(2).getAutor());
                                conteudo3.setText(lista.get(2).getConteudo());
                                empresaCV3.setText(lista.get(2).getEmpresa());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Avaliacao>> call, Throwable t) {
                        Log.e("wig", "erro ao fazer request" + t.getMessage());
                    }
                });
            }
        });
        return root;
    }
}