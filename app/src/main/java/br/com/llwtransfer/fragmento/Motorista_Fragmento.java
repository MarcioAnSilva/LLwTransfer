package br.com.llwtransfer.fragmento;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.llwtransfer.R;
import br.com.llwtransfer.atividade.Cad_Motorista_Atividade;

//import android.support.design.widget.FloatingActionButton;

//import com.example.marcioansilva.llwtransfer.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class Motorista_Fragmento extends Fragment {

    private Intent intent = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragmento_motorista, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatbutton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Cad_Motorista_Atividade.class);
                startActivity(intent);
            }
        });



        return view;
    }

}
