package br.com.llwtransfer.atividade;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.llwtransfer.R;
import br.com.llwtransfer.fragmento.Motorista_Fragmento;
import br.com.llwtransfer.fragmento.Passageiro_Fragmento;

//import com.example.marcioansilva.llwtransfer.R;

public class Principal_Atividade extends AppCompatActivity {

    private TextView mTextMessage;
    private Passageiro_Fragmento passageiro_fragmento;
    private Motorista_Fragmento motorista_fragmento;
    private FragmentTransaction mostra_fragmento;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.tab_passageiro:
                    passageiro_fragmento = new Passageiro_Fragmento();
                    mostra_fragmento = getSupportFragmentManager().beginTransaction();
                    mostra_fragmento.replace(R.id.frameConteudo, passageiro_fragmento);
                    mostra_fragmento.commit();
                    mostra_fragmento = null;
                    return true;
                case R.id.tab_motorista:
                    motorista_fragmento = new Motorista_Fragmento();
                    mostra_fragmento = getSupportFragmentManager().beginTransaction();
                    mostra_fragmento.replace(R.id.frameConteudo, motorista_fragmento);
                    mostra_fragmento.commit();
                    mostra_fragmento = null;
                    return true;
                case R.id.tab_corrida:
                    passageiro_fragmento = new Passageiro_Fragmento();
                    mostra_fragmento = getSupportFragmentManager().beginTransaction();
                    mostra_fragmento.replace(R.id.frameConteudo, passageiro_fragmento);
                    mostra_fragmento.commit();
                    mostra_fragmento = null;
                    return true;
                case R.id.tab_locais:
                    passageiro_fragmento = new Passageiro_Fragmento();
                    mostra_fragmento = getSupportFragmentManager().beginTransaction();
                    mostra_fragmento.replace(R.id.frameConteudo, passageiro_fragmento);
                    mostra_fragmento.commit();
                    mostra_fragmento = null;
                    return true;
                case R.id.tab_administrador:
                    passageiro_fragmento = new Passageiro_Fragmento();
                    mostra_fragmento = getSupportFragmentManager().beginTransaction();
                    mostra_fragmento.replace(R.id.frameConteudo, passageiro_fragmento);
                    mostra_fragmento.commit();
                    mostra_fragmento = null;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividade_principal);

        //Instancia os fragmentos
         passageiro_fragmento = new Passageiro_Fragmento();

         //instancia o objeto que exibe o fragmento
        mostra_fragmento = getSupportFragmentManager().beginTransaction();

         //Abre o primeiro fragmento o entrar no app
        mostra_fragmento.replace(R.id.frameConteudo, passageiro_fragmento);
        mostra_fragmento.commit();
        mostra_fragmento = null;


        //Aciona o objeto de navegacao
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Integer indexItem = 2; //Defini o indece do menu de icones para saber qual será selecionado ao abrir a activity
        navigation.getMenu().getItem(indexItem).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
