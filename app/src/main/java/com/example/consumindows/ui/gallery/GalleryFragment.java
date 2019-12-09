package com.example.consumindows.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.consumindows.InfoAvaliacaoActivity;
import com.example.consumindows.R;
import com.example.consumindows.telaEmpresa;

import java.util.List;

import modelo.Avaliacao;
import modelo.Cliente;
import modelo.Empresa;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {
    Cliente cliente;
    String clienteLogin;
    Bundle b;
    Avaliacao avaliacao;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final ListView textView = root.findViewById(R.id.lvMinhasAvaliacoes);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                b = getActivity().getIntent().getExtras();
                clienteLogin = b.getString("login");

                Call<Cliente> getCliente = new RetrofitConfig().getWigService().getCliente(clienteLogin);
                getCliente.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                        cliente = response.body();
                        Call<List<Avaliacao>> mAvaliacoes = new RetrofitConfig()
                                .getWigService().minhasAvaliacoes(cliente.getIdcliente());
                        mAvaliacoes.enqueue(new Callback<List<Avaliacao>>() {
                            @Override
                            public void onResponse(Call<List<Avaliacao>> call, Response<List<Avaliacao>> response) {
                                if (response.code() == 200 || response.isSuccessful()) {
                                    final List<Avaliacao> lista = response.body();

                                    final String[] array = new String[lista.size()];
                                    for (int i = 0; i < lista.size(); i++) {
                                        array[i] = lista.get(i).getConteudo();
                                    }

                                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_list_item_1, array);
                                    textView.setAdapter(adapter);

                                    textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            avaliacao = new Avaliacao();
                                            avaliacao = lista.get(position);

                                            Intent telaInfo = new Intent(getContext(), InfoAvaliacaoActivity.class);
                                            telaInfo.putExtra("avaliacao", avaliacao);
                                            startActivity(telaInfo);
                                        }
                                    });

                                }
                            }

                            @Override
                            public void onFailure(Call<List<Avaliacao>> call, Throwable t) {
                                Log.e("wig", "erro ao fazer request" + t.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {
                        Log.e("wig", "erro ao fazer request" + t.getMessage());
                    }
                });

                /*
                Call<List<Avaliacao>> call = new RetrofitConfig().getWigService().minhasAvaliacoes(1);
                call.enqueue(new Callback<List<Avaliacao>>() {
                    @Override
                    public void onResponse(Call<List<Avaliacao>> call, Response<List<Avaliacao>> response) {
                        if (response.code() == 200 || response.isSuccessful()) {
                            final List<Avaliacao> lista = response.body();

                            final String[] array = new String[lista.size()];
                            for (int i = 0; i < lista.size(); i++) {
                                array[i] = lista.get(i).getConteudo();
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_list_item_1, array);
                            textView.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Avaliacao>> call, Throwable t) {
                        Log.e("wig", "erro ao fazer request" + t.getMessage());
                    }
                });

                 */

            }
        });
        return root;
    }
}