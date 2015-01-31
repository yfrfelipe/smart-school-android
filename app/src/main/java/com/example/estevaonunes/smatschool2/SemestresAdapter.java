package com.example.estevaonunes.smatschool2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by estevaonunes on 21/10/14.
 */
public class SemestresAdapter extends BaseExpandableListAdapter {

    private List<String> semestres;
    private HashMap<String, List<String>> disciplinas;
     private LayoutInflater inflater;

    public SemestresAdapter (Context context, List<String> semestres, HashMap<String, List<String >> disciplinas){
        this.semestres = semestres;
        this.disciplinas = disciplinas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return semestres.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return disciplinas.get(semestres.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return semestres.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return disciplinas.get(semestres.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolerGroup holderSemestre;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.semestre_list, null);
            holderSemestre = new ViewHolerGroup();
            convertView.setTag(holderSemestre);
            holderSemestre.semestreNome = (TextView)  convertView.findViewById(R.id.semestreNome);
        }else{
            holderSemestre = (ViewHolerGroup) convertView.getTag();
        }

        holderSemestre.semestreNome.setText(semestres.get(groupPosition));



        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolerItem holderDisciplinas;

        String valor = (String) getChild(groupPosition, childPosition);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.disciplina_item, null);
            holderDisciplinas = new ViewHolerItem();
            convertView.setTag(holderDisciplinas);
            holderDisciplinas.disciplinaNome = (TextView)  convertView.findViewById(R.id.disciplinaNome);
        }else{
            holderDisciplinas = (ViewHolerItem) convertView.getTag();
        }

        holderDisciplinas.disciplinaNome.setText(valor);



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolerGroup{
        TextView semestreNome;
    }

    class ViewHolerItem{
        TextView disciplinaNome;
    }

}
