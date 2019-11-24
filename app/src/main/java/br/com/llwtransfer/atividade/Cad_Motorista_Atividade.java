package br.com.llwtransfer.atividade;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.com.llwtransfer.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class Cad_Motorista_Atividade extends AppCompatActivity
{

    private ImageButton imageButtonGaleria;
    private static final int SELEACO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;
    private CircleImageView circleImageViewPerfil;
    private Toolbar toolbar = null;
    private View view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividade_cad_motorista);

        view = new View(this);

        //Retorna os botos de tratamento de foto criados no XML
        //imageButtonCamera = findViewById(R.id.imageButtonCamera);
        imageButtonGaleria = findViewById(R.id.imageButtonGaleria);

        //Retorna objeto imagem do XML onde vai ficar a foto.
        circleImageViewPerfil = findViewById(R.id.imagem_cad_motorista);

        //Cria os evento de click no botao de tratamento de foto camera
        imageButtonGaleria.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });

        //Configura o menu para aparecer no toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.inflateMenu(R.menu.menu_salvar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //Configura permissao para acessar informaçoes da camera de foto
    private String[] permissoesNecessarias = new String[]
            {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            Bitmap imagem = null;

            try{
                switch ( requestCode)
                {
                    case SELECAO_GALERIA:
                        Uri localImaegmSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(),localImaegmSelecionada);
                        break;
                }

                if(imagem != null)
                {
                    circleImageViewPerfil.setImageBitmap(imagem);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //Recuros para mostrar o menu no toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_salvar,menu);
        return true;
    }

    //Configura a ação da opção do menu Salvar
    @Override
    public boolean onOptionsItemSelected(MenuItem item ) {
        switch (item.getItemId()) {
            case R.id.itemSalvar:
                Salvar(view);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void Salvar(View view)
    {
        //Ciar o Dialog de Alerta
        AlertDialog.Builder alertaSalvar = new AlertDialog.Builder(this);

        //configurar titulo e mensagem
        alertaSalvar.setTitle("Confirmar Cadastro");
        alertaSalvar.setMessage("Para salvar o motorista clique em confirmar.");

        //Configura cancelamento - para forçar o usuário escolher uma das duas opçao do dialog
        alertaSalvar.setCancelable(false);

        //Configurar icone
        alertaSalvar.setIcon(android.R.drawable.ic_menu_save);

        //Configurar ações para o botão Sim
        alertaSalvar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Sim", Toast.LENGTH_LONG).show();
            }
        });

        //Configurar ações para o botão não
        alertaSalvar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Não", Toast.LENGTH_LONG).show();
            }
        });

        //Criar e exibir o AlertDialog
        alertaSalvar.create();
        alertaSalvar.show();


    }

}
