package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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

        int response_count=get_intent.getIntExtra("count",0);
        if(response_count==0) {
            ls = new ArrayList<>();
        }
        String response=get_intent.getStringExtra("response");
        Toast.makeText(RecyclerViewResponseView.this,response,Toast.LENGTH_LONG).show();
        rv = findViewById(R.id.rv_response);

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        ls.add(new ResponseClass(response, currentTime,getResources().getDrawable(R.drawable.ic_baseline_person_add_24) ));

        if(response.contains("Bluetooth") && response.contains("On") ){
            Toast.makeText(this, "Bluetooth Turned On", Toast.LENGTH_SHORT).show();
        }


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rv.setLayoutManager(mLayoutManager);
        adapter = new RecyclerViewListActivityAdapter(ls, this);
        rv.setAdapter(adapter);
    }
}