package com.example.andro.entertainmenttrip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Andro on 6/1/2019.
 */

public class myadapter extends ArrayAdapter<String> {
    int Resource;
    Context Con;
    TextView nameshow, discript;
    List<trip> list;

    public myadapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        Resource = resource;
        Con = context;
        list = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(Con);
        View view = layoutInflater.inflate(Resource, parent, false);
        nameshow = view.findViewById(R.id.nameshow);
        discript = view.findViewById(R.id.discrip);
        nameshow.setText(list.get(position).getName());
        discript.setText("Price : " + list.get(position).getPrice() + "$                       " + "palace : " + list.get(position).getPalace());
        view.findViewById(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.select = list.get(position).getName();
                Toast.makeText(Con, "Select : " + MainActivity.select, Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }
}
