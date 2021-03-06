package co.intentservice.chatui.models;

import android.text.format.DateFormat;

import java.util.concurrent.TimeUnit;


/**
 * Chat Message model used when ChatMessages are required, either to be sent or received,
 * all messages that are to be shown in the chat-ui must be contained in this model.
 */
public class ChatMessage {
    private String message;
    private long timestamp;
    private Type type;
    private ContentType contentType;
    private String sender;
    private Integer imageWidth = 0;
    private Integer imageHeight = 0;

    public ChatMessage(String message, long timestamp, Type type, ContentType contentType, Integer imageWidth, Integer imageHeight) {
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
        this.contentType = contentType;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public ChatMessage(String message, long timestamp, Type type, ContentType contentType) {
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
        this.contentType = contentType;
    }

    public ChatMessage(String message, long timestamp, Type type, ContentType contentType, String sender) {
        this(message, timestamp, type, contentType);
        this.sender = sender;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    //TODO changes the date format
    public String getFormattedTime() {

        long oneDayInMillis = TimeUnit.DAYS.toMillis(1); // 24 * 60 * 60 * 1000;

        long timeDifference = System.currentTimeMillis() - timestamp;

        return timeDifference < oneDayInMillis
                ? DateFormat.format("h:mm a", timestamp).toString()
                : DateFormat.format("dd MMM - h:mm a", timestamp).toString();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public enum Type {
        SENT, RECEIVED
    }

    public enum ContentType {
        TEXT, IMAGE
    }
}
