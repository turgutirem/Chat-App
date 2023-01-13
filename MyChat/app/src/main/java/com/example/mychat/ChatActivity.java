package com.example.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.mychat.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
ActivityChatBinding binding;

String receiverId;
DatabaseReference databaseReferenceSender;
DatabaseReference databaseReferenceReceiver;
String senderRoom,receiverRoom;
MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        receiverId=getIntent().getStringExtra("id");

        senderRoom= FirebaseAuth.getInstance().getUid()+receiverId;
        receiverRoom=receiverId+FirebaseAuth.getInstance().getUid();
        messageAdapter=new MessageAdapter(this);
        binding.recylerr.setAdapter(messageAdapter);
        binding.recylerr.setLayoutManager(new LinearLayoutManager(this));

        databaseReferenceSender= FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferenceReceiver= FirebaseDatabase.getInstance().getReference("chats").child(receiverRoom);


        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MessageModel messageModel=dataSnapshot.getValue(MessageModel.class);
                    messageAdapter.add(messageModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=binding.messageEd.getText().toString();

                if (message.trim().length()>0){
                    sendMessage(message);
                    binding.messageEd.getText().clear();
                }

            }
        });

    }

    private void sendMessage(String message) {
        String messageId= UUID.randomUUID().toString();
        String timeStamp=new SimpleDateFormat("dd-MM-yy_HH:mm a").format(Calendar.getInstance().getTime());
        MessageModel messageModel=new MessageModel(messageId,FirebaseAuth.getInstance().getUid(),message,timeStamp);

        messageAdapter.add(messageModel);
        databaseReferenceSender.child(messageId)
                .setValue(messageModel);
        databaseReferenceReceiver.child(messageId)
                .setValue(messageModel);
    }
}