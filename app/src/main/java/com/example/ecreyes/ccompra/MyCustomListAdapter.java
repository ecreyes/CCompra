package com.example.ecreyes.ccompra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class MyCustomListAdapter extends BaseAdapter {
    Context context;
    int image[];
    String text[];

    public MyCustomListAdapter(Context applicationContext, int[] image, String[] text){
        this.context=applicationContext;
        this.image=image;
        this.text=text;
    }
    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View listrow=inflater.inflate(R.layout.mylistrow,null);
        ImageView imageView=(ImageView)listrow.findViewById(R.id.listimage);
        imageView.setBackgroundResource(image[position]);
        TextView textView = (TextView) listrow.findViewById(R.id.listtext);
        textView.setText(text[position]);
        return listrow;
    }
}