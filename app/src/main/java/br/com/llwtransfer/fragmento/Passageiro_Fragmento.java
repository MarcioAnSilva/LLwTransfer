package br.com.llwtransfer.fragmento;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import android.widget.Toast;

import br.com.llwtransfer.util.RecyclerItemClickListener;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.llwtransfer.R;
import br.com.llwtransfer.atividade.Cad_Passageiros_Atividade;
import br.com.llwtransfer.model.Passageiro;
import br.com.llwtransfer.adapter.Adapter_Passageiro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class Passageiro_Fragmento extends Fragment
{

    private Intent intent = null;
    private RecyclerView recyclerView;
    private ArrayList<Passageiro> listaPassageiros = new ArrayList<>();
    private Adapter_Passageiro adapter = null;
    private EditText editTextSearch;

    private Toolbar toolbar = null;

    //Defie no para o atributos
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragmento_passageiro, container, false);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatbutton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Cad_Passageiros_Atividade.class);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.rview_passageiros);
        editTextSearch = (EditText) view.findViewById(R.id.pesq_passageiro);

        //Preenche o Recyclerview
        this.buscaPassagieros();

        //adding a TextChangedListener
        //to call a method whenever there is some change on the EditText
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });

        //Configura o menu para aparecer no toolbar no fragmento
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        return view;
    }

    //Recursos para mostrar o menu no toolbar
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Make sure you have this line of code.
    }

    //Recursos para mostrar o menu no toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sair, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Recurso para trabalhar as opçoes do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.itemSair:
                Sair();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void buscaPassagieros()
    {

        //Define a tabela ser trabalhada
        DatabaseReference passageiros = reference.child("passageiros");

        passageiros.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String itemNome = null;
                String itemTelefone = null;
                String itemFoto = null;
                Passageiro passageiro = null;

                //Limpa lista
                listaPassageiros.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren())
                {
                    itemNome = snap.child("nome").getValue(String.class);
                    itemTelefone = snap.child("telefone").getValue(String.class);
                    itemFoto = snap.child("foto").getValue(String.class);

                    passageiro = new Passageiro(itemNome,"", itemTelefone,"",itemFoto);
                    listaPassageiros.add(passageiro);

                    //Log.i("Dados email usuario: ", itemNome);

                }

                //Configurar adapter
                adapter = new Adapter_Passageiro( listaPassageiros,getActivity());

                //Configurar Recyclerview
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.addItemDecoration( new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter( adapter );


                //evento de click
                recyclerView.addOnItemTouchListener
                (
                        new RecyclerItemClickListener(
                                getActivity().getApplicationContext(),
                                recyclerView,
                                new RecyclerItemClickListener.OnItemClickListener() {

                                    //Click curto
                                    @Override
                                    public void onItemClick(View view, int position)
                                    {
                                        Passageiro item = listaPassageiros.get( position );

                                        Toast.makeText(
                                                getActivity().getApplicationContext(),
                                                "Item pressionado: " + item.getNome(),
                                                Toast.LENGTH_SHORT
                                        ).show();

                                    }

                                    //Click longo
                                    @Override
                                    public void onLongItemClick(View view, int position) {

                                    }

                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    }
                                }
                        )
                );

                 itemNome = null;
                 itemTelefone = null;
                 passageiro = null;


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        passageiros = null;

    }


    private void filter(String text)
    {
        //new array list that will hold the filtered data
        ArrayList<Passageiro> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Passageiro s : listaPassageiros)
        {
            //if the existing elements contains the search input
            if (s.getNome().toLowerCase().contains(text.toLowerCase()))
            {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }

    private void Sair()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Deseja realmente sair?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        //Configurar icone
        builder.setIcon(android.R.drawable.ic_menu_save);

        AlertDialog alert = builder.create();
        alert.show();
    }

}
