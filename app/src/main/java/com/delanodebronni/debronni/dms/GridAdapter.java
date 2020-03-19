package com.delanodebronni.debronni.dms;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Debronni on 03-May-17.
 */
public class GridAdapter extends BaseAdapter{
    private int contacts[];
    private String letters[];

    private Context context;


    public GridAdapter(Context context,int contacts[],String letters[]){
        this.context = context;
        this.letters = letters;
        this.contacts=contacts;
    }

    @Override
    public int getCount() {
        return letters.length;
    }

    @Override
    public Object getItem(int position) {
        return letters[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
