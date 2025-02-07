package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FruitAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Fruits> FruitList;

    public FruitAdapter(Context context, int layout, List<Fruits> fruitList) {
        this.context = context;
        this.layout = layout;
        FruitList = fruitList;
    }

    @Override
    public int getCount(){
        return FruitList.size();
    }
    @Override
    public Object getItem(int i) { return  null; }
    @Override
    public long getItemId(int i) {return 0; }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView txtName = (TextView) view.findViewById(R.id.textvienten);
        TextView txtDes = (TextView) view.findViewById(R.id.textviewmota);
        ImageView imageView = (ImageView) view.findViewById(R.id.imagevienhinh);
        Fruits fruits = FruitList.get(i);
        txtName.setText(fruits.getName());
        txtDes.setText(fruits.getDescription());
        imageView.setImageResource(fruits.getImage());
        return view;
    }
}
