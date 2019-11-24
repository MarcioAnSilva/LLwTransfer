package br.com.llwtransfer.banco;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ValidarDados
{
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    public boolean is_Cadastrado = false;


    public void validarCadastroPassageiro__(final String  srtDadoPesuisado)
    {
        Query dadoPesquisa = null;
        DatabaseReference passageiros = null;
        passageiros = referencia.child("Passageiros");


        dadoPesquisa = passageiros.orderByChild("email").equalTo(srtDadoPesuisado);

        dadoPesquisa.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot snap : dataSnapshot.getChildren())
                {
                    String itemName = snap.child("email").getValue(String.class);

                    if(itemName.toLowerCase().contains(srtDadoPesuisado.toLowerCase()))
                    {
                        Log.i("Dados email usuario: ", itemName);
                        is_Cadastrado = true;
                        break;
                    }else is_Cadastrado = false;
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
