package com.example.ecreyes.ccompra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MarketListActivity extends AppCompatActivity {
    ListView mylist;
    String text[] = new String[]{"Tienda 1",
            "Tienda 2",
            "Tienda 3",
            "Tienda 4",
            "Tienda 5",
            "Tienda 6",
            "Tienda 7"};
    int image[] = new int[]{R.drawable.pretienda,
            R.drawable.pretienda,
            R.drawable.pretienda,
            R.drawable.pretienda,
            R.drawable.pretienda,
            R.drawable.pretienda,
            R.drawable.pretienda};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketlist);
        mylist = (ListView) findViewById(R.id.mylist);
        MyCustomListAdapter myadapter = new MyCustomListAdapter(getApplicationContext(),image, text);
        mylist.setAdapter((ListAdapter) myadapter);
    }
}
