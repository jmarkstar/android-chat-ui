package co.intentservice.chatui.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import java.util.EnumSet;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.R;
import co.intentservice.chatui.utils.ImageLoader;

/**
 * View to display messages that have been received throught the chat-ui.
 *
 * Created by James Lendrem
 */

public class ItemRecvView extends MessageView {

    private CardView bubble;
    private TextView messageTextView, timestampTextView;
    private SimpleDraweeView simpleDraweeView;

    @Override public void setImageMessage(final String url, final ChatView.OnImageTapListener onImageTapListener, int width, int heigth) {

        if (messageTextView == null) {
            messageTextView = (TextView) findViewById(R.id.message_text_view);
        }

        messageTextView.setVisibility(View.GONE);

        if(simpleDraweeView == null){
            simpleDraweeView = (SimpleDraweeView)findViewById(R.id.image_view);
        }

        simpleDraweeView.setVisibility(View.VISIBLE);

        simpleDraweeView.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                if(onImageTapListener != null)
                    onImageTapListener.imageTap(url);
            }
        });

        if(width == 0 || heigth == 0){

            ImageLoader.load(url, simpleDraweeView);
            return;
        }

        double density = simpleDraweeView.getContext().getResources().getDisplayMetrics().density;

        double maxWidth = density*200;
        double photoWidth = (double)width;
        double photoHeight = (double)heigth;

        Log.v("item","maxWidth: "+maxWidth+", width: "+photoWidth+", heigth: "+photoHeight);

        int ivHeight = 0;
        int ivWidth = (int)maxWidth;

        if(photoWidth >= maxWidth){

            double ratio = photoWidth/maxWidth;
            ivHeight = (int)(photoHeight/ratio);
        } else {

            double ratio = photoWidth/photoWidth;
            ivHeight= (int)(photoHeight*ratio);
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( ivWidth, ivHeight);
        simpleDraweeView.setLayoutParams(layoutParams);

        ImageLoader.load(url, simpleDraweeView);
    }

    /**
     * Method to set the messages text in the view so it can be displayed on the screen.
     * @param message   The message that you want to be displayed.
     */
    public void setMessage(final String message) {

        if(simpleDraweeView == null){
            simpleDraweeView = (SimpleDraweeView)findViewById(R.id.image_view);
        }

        simpleDraweeView.setVisibility(View.GONE);

        if (messageTextView == null) {

            messageTextView = (TextView) findViewById(R.id.message_text_view);
        }

        LinkExtractor linkExtractor = LinkExtractor.builder().linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW)).build();

        Iterable<LinkSpan> links = linkExtractor.extractLinks(message);

        SpannableStringBuilder builder = new SpannableStringBuilder(message);

        for(final LinkSpan link: links) {

            final int start = link.getBeginIndex();
            final int end = link.getEndIndex();

            ClickableSpan clickableSpan = new ClickableSpan() {

                @Override public void onClick(@NonNull View widget) {
                    String linkString = message.substring(start, end);
                    if (link.getType() == LinkType.WWW){
                        linkString = "http://" + linkString;
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkString));
                    widget.getContext().startActivity(browserIntent);
                }
            };

            builder.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        messageTextView.setVisibility(View.VISIBLE);

        messageTextView.setText(builder);
        messageTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Method to set the timestamp that the message was received or sent on the screen.
     * @param timestamp The timestamp that you want to be displayed.
     */
    public void setTimestamp(String timestamp) {

        if (timestampTextView == null) {

            timestampTextView = (TextView) findViewById(R.id.timestamp_text_view);

        }

        timestampTextView.setText(timestamp);

    }

    /**
     * Method to set the background color that you want to use in your message.
     * @param background The background that you want to be displayed.
     */
    public void setBackground(@ColorInt int background) {

        if (bubble == null) {

            this.bubble = (CardView) findViewById(R.id.bubble);

        }

        bubble.setCardBackgroundColor(background);

    }

    /**
     * Method to set the elevation of the view.
     * @param elevation The elevation that you want the view to be displayed at.
     */
    public void setElevation(float elevation) {

        if (bubble == null) {

            this.bubble = (CardView) findViewById(R.id.bubble);

        }

        bubble.setCardElevation(elevation);

    }

    /**
     * Constructs a new message view.
     * @param context
     */
    public ItemRecvView(Context context) {

        super(context);
        initializeView(context);

    }

    /**
     * Constructs a new message view with attributes, this constructor is used when we create a
     * message view using XML.
     * @param context
     * @param attrs
     */
    public ItemRecvView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initializeView(context);

    }

    /**
     * Inflates the view so it can be displayed and grabs any child views that we may require
     * later on.
     * @param context   The context that is used to inflate the view.
     */
    private void initializeView(Context context) {

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.chat_item_rcv, this);

        this.bubble = (CardView) findViewById(R.id.bubble);
        this.messageTextView = (TextView) findViewById(R.id.message_text_view);
        this.timestampTextView = (TextView) findViewById(R.id.timestamp_text_view);
        this.simpleDraweeView = (SimpleDraweeView) findViewById(R.id.image_view);
    }

}
