package com.example.consumindows.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.consumindows.Index;
import com.example.consumindows.R;
import com.example.consumindows.TelaPesquisa;
import com.example.consumindows.telaEmpresa;

import java.util.List;

import modelo.Empresa;
import modelo.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    Empresa empresa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final ListView textView = root.findViewById(R.id.lvListaEmpresas);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
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

                                    Intent telaEmrpes = new Intent(getContext(), telaEmpresa.class);
                                    telaEmrpes.putExtra("empresa", empresa);
                                    // telaEmrpes.putExtra("cliente", cliente);
                                    startActivity(telaEmrpes);
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Empresa>> call, Throwable t) {

                    }
                });
            }
        });

        return root;
    }
}