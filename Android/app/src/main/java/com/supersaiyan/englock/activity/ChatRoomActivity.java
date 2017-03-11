package com.supersaiyan.englock.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.supersaiyan.englock.R;
import com.supersaiyan.englock.databinding.ActivityChatRoomBinding;
import com.supersaiyan.englock.model.Message;

/**
 * Created by thanh_000 on 3/11/2017.
 */

public class ChatRoomActivity extends AppCompatActivity {
    private static final String TAG = "ChatRoomActivity";
    private ActivityChatRoomBinding binding;
    private String roomName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_chat_room, null, false);
        binding.setActivity(this);
        setContentView(binding.getRoot());
        roomName = getIntent().getStringExtra("data");
        setUpListener();

    }

    public void setUpListener() {
        FirebaseDatabase.getInstance().getReference().child("room").child(roomName).addChildEventListener(listener);

    }


    @Override
    public void onBackPressed() {
        FirebaseDatabase.getInstance().getReference().child("room").child(roomName).removeEventListener(listener);
        super.onBackPressed();
    }

    public void sendMsg() {
        FirebaseDatabase.getInstance().getReference().child("room").child(roomName).push().setValue(new Message("he", "he"));
    }


    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.i(TAG, dataSnapshot.toString());
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
