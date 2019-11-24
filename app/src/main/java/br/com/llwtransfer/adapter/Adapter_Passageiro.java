package br.com.llwtransfer.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.content.Context;
import br.com.llwtransfer.R;
import br.com.llwtransfer.atividade.GlideApp;
import br.com.llwtransfer.model.Passageiro;
import de.hdodenhof.circleimageview.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by jamiltondamasceno
 */

public class Adapter_Passageiro extends RecyclerView.Adapter<Adapter_Passageiro.MyViewHolder>
{

    private ArrayList<Passageiro> listaPassageiro;
    private Context context;
    CircleImageView fotoImageView;


    //Define ns para o storage
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();


    public Adapter_Passageiro(ArrayList<Passageiro> lista, Context c) {

        this.listaPassageiro = lista;
        context = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_passageiro, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Passageiro passageiro = listaPassageiro.get( position );
        holder.nome.setText( passageiro.getNome() );
        holder.telefone.setText( passageiro.getTelefone() );

        StorageReference fotosRef_IMG    = storageReference.child("imagens");
        StorageReference fotosRef_Perfil = fotosRef_IMG.child("perfil");
        StorageReference fotosRef        = fotosRef_Perfil.child(passageiro.getFoto());
        fotosRef.getDownloadUrl().toString();

        //Recuperar a imagem do storage
        fotosRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                //Log.i("uri ", uri.toString());
                Picasso.with(context).load(uri.toString()).into(fotoImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPassageiro.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView telefone;
        //CircleImageView fotoImageView;


        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nome_adapter_passageiro);
            telefone = itemView.findViewById(R.id.telefone_adapter_passageiro);
            fotoImageView =  itemView.findViewById(R.id.foto_adapter_passageiro);

        }
    }

    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<Passageiro> filterdNames) {
        this.listaPassageiro = filterdNames;
        notifyDataSetChanged();
    }

}
