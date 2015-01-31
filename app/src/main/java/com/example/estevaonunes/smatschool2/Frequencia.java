package com.example.estevaonunes.smatschool2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Frequencia extends FragmentActivity
        implements AdapterView.OnItemClickListener, SlidingPaneLayout.PanelSlideListener {

    private SlidingPaneLayout mSlidingLayout;
    private ListView mList;
    private List<String> semestres;
    private HashMap<String, List<String>> disciplinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_frequencia);


        mSlidingLayout = (SlidingPaneLayout)
                findViewById(R.id.sliding_pane_layout);
        mSlidingLayout.setPanelSlideListener(this);

        ArrayList<MenuLista> opcoes = new ArrayList<MenuLista>();

        opcoes.add(new MenuLista("Página Inicial"));
        opcoes.add(new MenuLista("Frequência"));
        opcoes.add(new MenuLista("Diário"));
        opcoes.add(new MenuLista("Perfil"));
        opcoes.add(new MenuLista("Configurações"));

        mList = (ListView) findViewById(R.id.lista_menu);

        mList.setAdapter(new MenuListaAdapter(this, opcoes));
        mList.setOnItemClickListener(this);

        semestres = new ArrayList<String>();
        disciplinas = new HashMap<String, List<String>>();

        semestres.add("1º Semestre");
        semestres.add("2º Semestre");
        semestres.add("7º Semestre");
        semestres.add("8º Semestre");

        List<String> auxList = new ArrayList<String>();
        auxList.add("Programação I");
        disciplinas.put(semestres.get(0), auxList);

        auxList = new ArrayList<String>();
        auxList.add("Programação II");
        auxList.add("Tecnologias Web I");
        auxList.add("Lógica de Programação I");
        disciplinas.put(semestres.get(1), auxList);

        auxList = new ArrayList<String>();
        auxList.add("TCC I");
        disciplinas.put(semestres.get(2), auxList);

        auxList = new ArrayList<String>();
        auxList.add("TCC II");
        disciplinas.put(semestres.get(3), auxList);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.lista_expansiva_semestres);
        expandableListView.setAdapter(new SemestresAdapter(Frequencia.this, semestres, disciplinas));

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(Frequencia.this,"Semestre - "+semestres.get(groupPosition) + " e disciplina - "+disciplinas.get(semestres.get(groupPosition)).get(childPosition),Toast.LENGTH_SHORT ).show();
                return false;
            }
        });



    }

    // Evento de clique do botão
    public void abrirMenu(View v){
        // Se estive aberto, feche. Senão abra.
        if (mSlidingLayout.isOpen()){
            mSlidingLayout.closePane();
        } else {
            mSlidingLayout.openPane();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView,
                            View view, int position, long id) {

        switch (position){
            case 0:
                //página inicial
                finish();
                break;
            case 2:
                //Diário
                finish();
                startActivity(new Intent(Frequencia.this, Diario.class));
                break;
            case 3:
                //Perfil
                finish();
                startActivity(new Intent(Frequencia.this, Perfil.class));
                break;
            case 4:
                //configuraçoes
                finish();
                startActivity(new Intent(Frequencia.this, Configuracoes.class));
                break;
        }

    }

    @Override
    public void onPanelClosed(View arg0) {
        // TODO Ao fechar o painel
    }

    @Override
    public void onPanelOpened(View arg0) {
        // TODO Ao abrir o painel
    }

    @Override
    public void onPanelSlide(View arg0, float arg1) {
        // TODO Enquanto o painel desliza
    }

}
