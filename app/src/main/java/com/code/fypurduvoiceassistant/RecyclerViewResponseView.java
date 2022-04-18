package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecyclerViewResponseView extends AppCompatActivity {
    RecyclerView rv;
    RecyclerViewListActivityAdapter adapter;
    ArrayList<ResponseClass> ls;
    Intent get_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_response_view);
        get_intent=getIntent();
        String response=get_intent.getStringExtra("response");
        rv = findViewById(R.id.rv_response);
        ls = new ArrayList<ResponseClass>();
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.ic_baseline_person_add_24) ));

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rv.setLayoutManager(mLayoutManager);
        adapter = new RecyclerViewListActivityAdapter(ls, this);
        rv.setAdapter(adapter);
    }
}