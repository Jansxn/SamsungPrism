package com.jason.prism_app;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView messageListView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the ListView
        messageListView = findViewById(R.id.messages_view);

        // Initialize the message list
        messageList = new ArrayList<>();

        // Populate the message list with some data
        populateMessages();

        // Initialize the adapter with the context and message list
        messageAdapter = new MessageAdapter(this, messageList);

        // Set the adapter to the ListView
        messageListView.setAdapter(messageAdapter);// Initialize the ListView
        messageListView = findViewById(R.id.messages_view);

        // Initialize the message list
        messageList = new ArrayList<>();

        // Populate the message list with some data
        populateMessages();

        // Initialize the adapter with the context and message list
        messageAdapter = new MessageAdapter(this, messageList);

        // Set the adapter to the ListView
        messageListView.setAdapter(messageAdapter);
    }

    private void populateMessages() {
        // Add some sample messages to the list
        messageList.add(new Message("John Doe", "Hello, how are you?"));
        messageList.add(new Message("Jane Smith", "I'm good, thanks! How about you?"));
        messageList.add(new Message("John Doe", "I'm doing great, thanks for asking!"));
        // Add more messages as needed
    }
}