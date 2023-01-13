package com.example.mychat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private Context context;
    private List<MessageModel> messageModelList;

    public MessageAdapter(Context context) {
        this.context = context;
        messageModelList =new ArrayList<>();
    }
    public void add(MessageModel messageModel){
        messageModelList.add(messageModel);
        notifyDataSetChanged();
    }
    public void clear(){
        messageModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent,false);
        //View view= CardView.inflate(context,viewType,parent);
        //CardView view1=CardView.inflate(context,viewType,parent).findViewById(viewType);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageModel messageModel= messageModelList.get(position);
        holder.message.setText(messageModel.getMessage());
        holder.dateTime.setText(messageModel.getDateTime());


        if (messageModel.getSenderId().equals(FirebaseAuth.getInstance().getUid())){
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.teal_700));
            holder.message.setTextColor(context.getResources().getColor(R.color.white));
            holder.dateTime.setTextColor(context.getResources().getColor(R.color.white));

        }else{
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.myColor));
            holder.message.setTextColor(context.getResources().getColor(R.color.white));
            holder.dateTime.setTextColor(context.getResources().getColor(R.color.white));


        }
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView message,dateTime;
        private CardView main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.user_messsage);
            dateTime=itemView.findViewById(R.id.user_messsage_date_time);
            main=itemView.findViewById(R.id.mainLayout);
        }
    }

}
