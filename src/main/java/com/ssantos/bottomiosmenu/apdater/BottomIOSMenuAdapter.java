package com.ssantos.bottomiosmenu.apdater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ssantos.bottomiosmenu.R;
import com.ssantos.bottomiosmenu.model.OptionMenu;

import java.util.List;

/**
 * Created by ssantos on 10/10/2017.
 */

public class BottomIOSMenuAdapter extends BaseAdapter {

    List<OptionMenu> menu;
    Context context;

    public BottomIOSMenuAdapter(Context context, List<OptionMenu> menu) {
        this.context = context;
        this.menu = menu;
    }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int i) {
        return menu.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder vh;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_bottomiosmenu, null);
            vh = new ViewHolder();
            vh.optionMenu = view.findViewById(R.id.option_menu_name);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        OptionMenu d = (OptionMenu) getItem(i);
        vh.optionMenu.setText(d.getName());

        return view;
    }

    static class ViewHolder {
        TextView optionMenu;
    }
}
