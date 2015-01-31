package com.example.estevaonunes.smatschool2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class Menu extends Activity {

    private Button frequencia;
    private Button diario;
    private Button perfil;
    private Button config;
    private Button sair;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        frequencia = (Button) findViewById(R.id.frequenciaBt);
        diario = (Button) findViewById(R.id.diarioBt);
        perfil = (Button) findViewById(R.id.perfilBt);
        config = (Button) findViewById(R.id.configuracaoBt);
        sair = (Button) findViewById(R.id.sairBt);

        frequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Menu.this, Frequencia.class);
                startActivity(intent);
            }
        });

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
