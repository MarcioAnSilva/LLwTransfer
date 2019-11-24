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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import br.com.llwtransfer.R;
import br.com.llwtransfer.model.Passageiro;
import br.com.llwtransfer.util.Codigicar_Decodificar;
import br.com.llwtransfer.util.MascaraEditUtil;
import de.hdodenhof.circleimageview.CircleImageView;


public class Cad_Passageiros_Atividade extends AppCompatActivity
{

    private ImageButton imageButtonGaleria;
    private static final int SELEACO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;
    private CircleImageView circleImageViewPerfil;
    private Toolbar toolbar = null;
    private View view = null;
    private Passageiro passageiro = null;

    private  boolean is_negado_cadastro = false;

    private ImageView imageFoto;

    private TextInputEditText campoNome;
    private TextInputEditText campoEndereco;
    private TextInputEditText campoEmail;
    private TextInputEditText campoTelefone;

    private String textoNome = null;
    private String textoEndereco = null;
    private String textoEmail = null;
    private String textoTelefone = null;

    private String nomeImagemFoto = null;

    //Defie no para o atributos
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    //Define ns para o storage
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private StorageReference fotos = storageReference.child("imagens/perfil");

    //Define a tabela ser trabalhada
    private  DatabaseReference passageiros = reference.child("passageiros");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividade_cad_passageiros);

        view = new View(this);

        //pega os campos da tela
        campoNome = findViewById(R.id.nome_passageiro);
        campoEndereco = findViewById(R.id.endereco_passageiro);
        campoEmail = findViewById(R.id.email_passageiro);
        campoTelefone = findViewById(R.id.telefone_passageiro);

        //Pega a imagem da foto da tela
        imageFoto = findViewById(R.id.imagem_cad_passageiro);


        //Coloca Mascara no campo telefone
        campoTelefone.addTextChangedListener(MascaraEditUtil.mask(campoTelefone, MascaraEditUtil.FORMAT_FONE));

        //Retorna os botos de tratamento de foto criados no XML
        //imageButtonCamera = findViewById(R.id.imageButtonCamera);
        imageButtonGaleria = findViewById(R.id.imageButtonGaleria);

        //Retorna objeto imagem do XML onde vai ficar a foto.
        circleImageViewPerfil = findViewById(R.id.imagem_cad_passageiro);

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

    } //Fim do onCreate


    //Metodo para gravar o passageiro
    protected void criaBancoPassageiros()
    {
        //Grava a foto do passageiro.
        gravarFoto();

        passageiro = new Passageiro();
        passageiro.setNome(textoNome);
        passageiro.setEndereco(textoEndereco);
        passageiro.setTelefone(textoTelefone);
        passageiro.setEmail(textoEmail);
        passageiro.setFoto(nomeImagemFoto);

        //Grava os dados do passageiro
        passageiros.push().setValue(passageiro);

        Toast.makeText(getApplicationContext(), "Cadastrado com sucesso ..!", Toast.LENGTH_SHORT).show();

        //Limpa os valore na tela
        campoNome.setText("");
        campoEndereco.setText("");
        campoEmail.setText("");
        campoTelefone.setText("");

    }

    //Metodo para gravar foto. Os comandos para gravar imagem no Storage não podem ficar dentro
    //do metodo onCreate em funçao das threads
    protected void gravarFoto()
    {
        //Configura para a imagem ser salva na memória
        imageFoto.setDrawingCacheEnabled(true);
        imageFoto.buildDrawingCache();

        //Recupera o bitmap da imagem a ser carregada que seria a foto
        Bitmap bitmap = imageFoto.getDrawingCache();

        //Comprimindo bitmap para um formato png/jpeg
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,75,baos);

        //Converte o baos (criadd acima) para pixel grutos em um matriz de bytes
        byte[] dadosImagem = baos.toByteArray();

        //Prepara o mome da imagem que esta codificada
        nomeImagemFoto = Codigicar_Decodificar.codificarDado(textoEmail)+".png";
        StorageReference fotosRef = fotos.child(nomeImagemFoto);

        //Grava imagem no storage
        UploadTask uploadTask = fotosRef.putBytes(dadosImagem);

    }

    protected void recuperaValidarDadosTela()
    {
        //Coloca os valore pegos das tela nas variavies
        textoNome = campoNome.getText().toString();
        textoEndereco = campoEndereco.getText().toString();
        textoEmail = campoEmail.getText().toString();
        textoTelefone = campoTelefone.getText().toString();

        Query dadoPesquisa = passageiros.orderByChild("email").equalTo(textoEmail);

        //validar se o campo nao esta vazio
        if(!textoNome.trim().isEmpty()) {
            if (!textoEndereco.trim().isEmpty())
            {
                if (!textoTelefone.trim().isEmpty())
                {
                    if (!textoEmail.trim().isEmpty())
                    {
                        if(validateEmailFormat(textoEmail))
                        {
                            dadoPesquisa.addValueEventListener(new ValueEventListener()
                            {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {
                                    is_negado_cadastro = false;
                                    for(DataSnapshot snap : dataSnapshot.getChildren())
                                    {
                                        String itemName = snap.child("email").getValue(String.class);

                                        if(itemName.toLowerCase().contains(textoEmail.toLowerCase()))
                                        {
                                            Toast.makeText(getApplicationContext(), "Passageiro já cadastrado", Toast.LENGTH_SHORT).show();
                                            is_negado_cadastro = true;
                                            break;
                                        }
                                    }

                                    if(!is_negado_cadastro) {
                                          Salvar(view);
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }else {
                            Toast.makeText(getApplicationContext(), "E-mail inválido", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(getApplicationContext(), "Preencha o campo email!", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Preencha o campo telefone!", Toast.LENGTH_SHORT).show();
                }


            }else {
                Toast.makeText(getApplicationContext(), "Preencha o campo endereco!", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getApplicationContext(), "Preencha o campo nome!", Toast.LENGTH_SHORT).show();
        }

        //return is_negado_cadastro;

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
    public boolean onOptionsItemSelected(MenuItem item )
    {
        switch (item.getItemId())
        {
            case R.id.itemSalvar:
                    recuperaValidarDadosTela();
                    //Salvar(view);
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
        alertaSalvar.setMessage("Para salvar o passgeiro clique em confirmar.");

        //Configura cancelamento - para forçar o usuário escolher uma das duas opçao do dialog
        alertaSalvar.setCancelable(false);

        //Configurar icone
        alertaSalvar.setIcon(android.R.drawable.ic_menu_save);

        //Configurar ações para o botão Sim
        alertaSalvar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                criaBancoPassageiros();
            }
        });

        //Configurar ações para o botão não
        alertaSalvar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Cadastramento cancelado ..!", Toast.LENGTH_LONG).show();
            }
        });

        //Criar e exibir o AlertDialog
        alertaSalvar.create();
        alertaSalvar.show();
    }

    //validar e-mail
    private boolean validateEmailFormat(final String email)
    {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }

}
