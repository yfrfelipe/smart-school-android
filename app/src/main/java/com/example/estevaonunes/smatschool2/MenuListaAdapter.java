package com.example.estevaonunes.smatschool2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by estevaonunes on 20/10/14.
 */
public class MenuListaAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<MenuLista> menus;

    public MenuListaAdapter(Context c, ArrayList<MenuLista> m){
        this.context = c;
        this.menus = m;
    }

    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public Object getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MenuLista menu = menus.get(position);

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = li.inflate(R.layout.list_menu, null);

        TextView textoMenu = (TextView) layout.findViewById(R.id.textoMenu);
        textoMenu.setText(menu.getMenu());

        return layout;
    }
}
