package com.example.chatapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText messageBox;
    ImageButton sendBtn, emojiBtn;

    ArrayList<MessageModel> list;
    MessageAdapter adapter;

    String senderId = "user1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // ✅ MUST: findViewById (IMPORTANT)
        recyclerView = findViewById(R.id.chatRecycler);
        messageBox = findViewById(R.id.messageBox);
        sendBtn = findViewById(R.id.sendBtn);
        emojiBtn = findViewById(R.id.emojiBtn);

        list = new ArrayList<>();
        adapter = new MessageAdapter(this, list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // ✍️ typing
        messageBox.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        // 🔥 SEND MESSAGE
        sendBtn.setOnClickListener(v -> {
            String msg = messageBox.getText().toString().trim();

            if (!msg.isEmpty()) {

                MessageModel message = new MessageModel(senderId, msg);
                list.add(message);

                adapter.notifyDataSetChanged();

                if (list.size() > 0) {
                    recyclerView.scrollToPosition(list.size() - 1);
                }

                showNotification(msg);

                messageBox.setText("");
            }
        });

        // 😍 EMOJI BUTTON
        emojiBtn.setOnClickListener(v -> {
            messageBox.requestFocus();
        });
    }

    // 🔔 NOTIFICATION
    private void showNotification(String msg) {

        NotificationManager manager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String channelId = "chat_local";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(channelId, "Chat",
                            NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, channelId)
                        .setContentTitle("New Message")
                        .setContentText(msg)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setAutoCancel(true);

        manager.notify(1, builder.build());
    }
}