package com.example.consumindows.ui.send;

import android.os.Bundle;
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

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        sendViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("");
            }
        });
        return root;

        /*
        Quem somos nós?
O Where i Go conta com um time de desenvolvedores jovens. Felipe Ribeiro, 19,
Gustavo Bonfim, 18, João Vitor Cassiano, 17 e Luís Carlos Rodrigues, 20; Os mesmos Cursam o 2 semestre
 do ensino superior em Análise e Desenvolvimento de Sistemas.
         */
    }
}