package com.example.consumindows.ui.slideshow;

import android.app.Activity;
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
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.consumindows.Index;
import com.example.consumindows.R;
import com.example.consumindows.TelaPesquisa;
import com.example.consumindows.telaEmpresa;

import java.util.List;

import modelo.Cliente;
import modelo.Empresa;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlideshowFragment extends Fragment {


    private SlideshowViewModel slideshowViewModel;
    Empresa empresa;
    Cliente cliente;
    String clienteLogin;
    Bundle b;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final ListView textView = root.findViewById(R.id.lvListaEmpresas);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                b = getActivity().getIntent().getExtras();
                clienteLogin = b.getString("login");

                Call<Cliente> getCliente = new RetrofitConfig().getWigService().getCliente(clienteLogin);
                getCliente.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                        cliente = response.body();
                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {
                        Log.e("wig", "erro ao fazer request" + t.getMessage());
                    }
                });

                Call<List<Empresa>> call = new RetrofitConfig().getWigService().listarEmpresas();
                call.enqueue(new Callback<List<Empresa>>() {
                    @Override
                    public void onResponse(Call<List<Empresa>> call, Response<List<Empresa>> response) {
                        if (response.code() == 200 || response.isSuccessful()) {
                            final List<Empresa> lista = response.body();

                            final String[] array = new String[lista.size()];
                            for (int i = 0; i < lista.size(); i++) {
                                array[i] = lista.get(i).getLogin();
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_list_item_1, array);
                            textView.setAdapter(adapter);

                            textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    empresa = new Empresa();
                                    empresa = lista.get(position);
                                    String nome = lista.get(position).getLogin();
                                    // pegando nome da empresa separado poeque
                                    // o serialize do objeto buga ao passar pra proxima tela
                                    // e aparece como null

                                    Intent telaEmrpes = new Intent(getContext(), telaEmpresa.class);
                                    telaEmrpes.putExtra("empresa", empresa);
                                    telaEmrpes.putExtra("cliente", cliente);
                                    telaEmrpes.putExtra("empresaNome", nome);

                                    startActivity(telaEmrpes);
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Empresa>> call, Throwable t) {
                        Log.e("wig", "erro ao se conectar com o WS " + t.getMessage());
                    }
                });
            }
        });

        return root;
    }
}