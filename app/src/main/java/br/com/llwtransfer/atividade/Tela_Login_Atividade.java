package br.com.llwtransfer.atividade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.com.llwtransfer.R;

//import com.example.marcioansilva.llwtransfer.R;

public class Tela_Login_Atividade extends AppCompatActivity {

    private Button buttonEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividade_tela_login);

      buttonEntrar = findViewById(R.id.buttonEntrar);

      buttonEntrar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              //Navegar entre telas
              Intent intent = new Intent(getApplicationContext(), Principal_Atividade.class);
              startActivity(intent);

          }
      });


    }
}
