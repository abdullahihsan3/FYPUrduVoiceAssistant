package com.code.fypurduvoiceassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewListActivityAdapter extends RecyclerView.Adapter<RecyclerViewListActivityAdapter.MyViewHolder> {

    List<ResponseClass> ls;
    Context c;


    public RecyclerViewListActivityAdapter(List<ResponseClass> ls, Context c) {
        this.ls = ls;
        this.c = c;

    }

    @NonNull
    @Override
    public RecyclerViewListActivityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(c).inflate(R.layout.row1,parent,false);
        return new MyViewHolder(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.person.setImageDrawable(ls.get(position).getPerson());
        holder.time.setText(ls.get(position).getTime());
        holder.response.setText(ls.get(position).getResponse());

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public void filterList(ArrayList<ResponseClass> filteredList) {
        ls = filteredList;
        notifyDataSetChanged();
    }

    public class MyViewHolder<V> extends RecyclerView.ViewHolder {
        ImageView person;
        TextView time;
        TextView response;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            person=itemView.findViewById(R.id.image_person);
            time=itemView.findViewById(R.id.time_response);
            response=itemView.findViewById(R.id.response);

        }



    }




}
