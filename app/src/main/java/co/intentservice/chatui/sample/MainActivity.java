package co.intentservice.chatui.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String [] permissions = { Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, 1000);

        final ChatView chatView = (ChatView) findViewById(R.id.chat_view);
        chatView.setOnImageTapListener(new ChatView.OnImageTapListener() {
            @Override public void imageTap(String url) {
                Toast.makeText(MainActivity.this, ""+url, Toast.LENGTH_SHORT).show();
            }
        });

        chatView.addMessage(new ChatMessage("Message received https://search.maven.org/artifact/org.nibor.autolink/autolink/0.10.0/jar", System.currentTimeMillis(), ChatMessage.Type.RECEIVED, ChatMessage.ContentType.TEXT));

        chatView.addMessage(new ChatMessage("http://app.chasquihost.com/media/imagenes-de-chat/405ae0cb4a7d4d888d3277fb4c2d5b9d.png", System.currentTimeMillis(), ChatMessage.Type.RECEIVED, ChatMessage.ContentType.IMAGE, 739, 415));//739 - 415

        chatView.addMessage(new ChatMessage("http://app.chasquihost.com/media/imagenes-de-chat/dde63ef1405146e3b2c7056d8a831b3c.png", System.currentTimeMillis(), ChatMessage.Type.SENT, ChatMessage.ContentType.IMAGE, 1600, 1200));//1600 - 1200

        chatView.addMessage(new ChatMessage("http://app.chasquihost.com/media/imagenes-de-chat/3203b8b84bce4c7db5a5f7474aa5b1b3.png", System.currentTimeMillis(), ChatMessage.Type.RECEIVED, ChatMessage.ContentType.IMAGE, 678, 452));//678 - 452


        chatView.addMessage(new ChatMessage("http://app.chasquihost.com/media/imagenes-de-chat/a0ce04eee53845f78c84238975ff7d97.png", System.currentTimeMillis(), ChatMessage.Type.SENT, ChatMessage.ContentType.IMAGE, 875, 350));//875 - 350


        chatView.addMessage(new ChatMessage("A message with a sender name https://travis-ci.org/", System.currentTimeMillis(), ChatMessage.Type.RECEIVED, ChatMessage.ContentType.TEXT, "Ryan Java"));

        chatView.addMessage(new ChatMessage("google google www.google.com aaa", System.currentTimeMillis(), ChatMessage.Type.RECEIVED, ChatMessage.ContentType.TEXT, "Ryan Java"));


        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override public boolean sendMessage(ChatMessage chatMessage) {
                Log.d("MainActivity", "setOnSentMessageListener()");
                return true;
            }
        });

        chatView.setTypingListener(new ChatView.TypingListener() {
            @Override public void userStartedTyping() {
                Log.d("MainActivity", "userStartedTyping()");
            }

            @Override public void userStoppedTyping() {
                Log.d("MainActivity", "userStoppedTyping()");
            }
        });

        chatView.setOnAttachListener(new ChatView.OnAttachListener() {
            @Override public void attachFile() {
                Toast.makeText(MainActivity.this, "gonna attach something :)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
