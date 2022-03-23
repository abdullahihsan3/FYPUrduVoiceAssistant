package com.code.fypurduvoiceassistant;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<ImageClass> ls;
    Context c;
    String TAG="XAXAXA";


    public RecyclerViewAdapter(List<ImageClass> ls, Context c) {
        this.ls = ls;
        this.c = c;

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(c).inflate(R.layout.row,parent,false);
        return new MyViewHolder(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.image.setImageDrawable(ls.get(position).getImage());
        holder.command_name.setText(ls.get(position).getCommand_name());
        holder.command_short_description.setText(ls.get(position).getCommand_short_description());
        holder.command_sub_command_count.setText(ls.get(position).getCommand_sub_command_count());

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public void filterList(ArrayList<ImageClass> filteredList) {
        ls = filteredList;
        notifyDataSetChanged();
    }

    public class MyViewHolder<V> extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView command_name;
        TextView command_short_description;
        TextView command_sub_command_count;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image=itemView.findViewById(R.id.image_to_show);
            command_name=itemView.findViewById(R.id.command_text_to_view);
            command_short_description=itemView.findViewById(R.id.command_description_to_view);
            command_sub_command_count=itemView.findViewById(R.id.command_subcommandcount_to_view);

        }

        public void db(String command) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("CommandCount")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String getTitle = (String) document.getData().get("Command_Name");
                            if (getTitle.equals(command)) {
                                String getcount = (String) document.getData().get("Count");
                                int value = Integer.valueOf(getcount) ;
                                value = value + 1;
                                db.collection("CommandCount").document(document.getId()).update("Count", String.valueOf(value));

                            }
                        }
                    } else {
                        Log.w("TAGXAXA", "Error getting documents.", task.getException());
                    }


                }
            });

        }

        @Override
        public void onClick(View v) {

            // Create a new user with a first and last name


            if(getLayoutPosition()==0){
                db("FlashLight");
                Intent send_intent=new Intent(v.getContext(),Flashlight.class);
                c.startActivity(send_intent);
            }
            else if(getLayoutPosition()==1){

                db("AirplaneMode");

                Intent send_intent=new Intent(v.getContext(),AirplaneMode.class);
                c.startActivity(send_intent);
            }

            else if(getLayoutPosition()==2){

                db("Screenshot");
                Intent send_intent=new Intent(v.getContext(),Screenshot.class);
                c.startActivity(send_intent);
            }

            else if(getLayoutPosition()==3){
                db("Sound");
                Intent send_intent=new Intent(v.getContext(),Sound.class);
                c.startActivity(send_intent);
            }

            else if(getLayoutPosition()==4){
                db("MobileData");
                Intent send_intent=new Intent(v.getContext(),MobileData.class);
                c.startActivity(send_intent);
            }

            else if(getLayoutPosition()==5){
                db("Alarm");
                Intent send_intent=new Intent(v.getContext(),Alarm.class);
                c.startActivity(send_intent);
            }

            else if(getLayoutPosition()==6){

                db("Reminder");
                Intent send_intent=new Intent(v.getContext(),Reminder.class);
                c.startActivity(send_intent);
            }


            else if(getLayoutPosition()==7){

                db("Map");
                Intent send_intent=new Intent(v.getContext(),Map.class);
                c.startActivity(send_intent);
            }

            else if(getLayoutPosition()==8){

                db("Time");
                Intent send_intent=new Intent(v.getContext(),Time.class);
                c.startActivity(send_intent);
            }

            else if(getLayoutPosition()==9){

                db("Bluetooth");
                Intent send_intent=new Intent(v.getContext(), Bluetooth.class);
                c.startActivity(send_intent);
            }

            else if(getLayoutPosition()==10){

                db("Wifi");
                Intent send_intent=new Intent(v.getContext(),WifiActivity.class);
                c.startActivity(send_intent);
            }

            else if(getLayoutPosition()==11){

                db("Youtube");
                Intent send_intent=new Intent(v.getContext(),Youtube.class);
                c.startActivity(send_intent);
            }

            else if(getLayoutPosition()==12){

                db("Facebook");
                Intent send_intent=new Intent(v.getContext(),Facebook.class);
                c.startActivity(send_intent);
            }
            else if(getLayoutPosition()==13){

                db("Browser");
                Intent send_intent=new Intent(v.getContext(),Browser.class);
                c.startActivity(send_intent);
            }
            else if(getLayoutPosition()==14){

                db("Email");
                Intent send_intent=new Intent(v.getContext(),Email.class);
                c.startActivity(send_intent);
            }
            else if(getLayoutPosition()==15){

                db("Date");
                Intent send_intent=new Intent(v.getContext(),Date.class);
                c.startActivity(send_intent);
            }
            else if(getLayoutPosition()==16){

                db("Music");
                Intent send_intent=new Intent(v.getContext(),Music.class);
                c.startActivity(send_intent);
            }
            else if(getLayoutPosition()==17){

                db("Brightness");
                Intent send_intent=new Intent(v.getContext(),Brightness.class);
                c.startActivity(send_intent);
            }



        }
    }




}
