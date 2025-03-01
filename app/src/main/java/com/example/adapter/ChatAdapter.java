package com.example.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appketquaxoso.R;
import com.example.firebase.ChatMessage;
import com.example.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ChatMessage> chatData;
    private User user;

    public static final  int TYPE_OWER=1;
    public static final  int TYPE_GUEST=2;

    public ChatAdapter(Context context, ArrayList<ChatMessage> chatData, User user) {
        mContext = context;
        this.chatData = chatData;
        this.user=user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view=null;
        if(i==TYPE_OWER)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_owner,viewGroup,false);
        }
        else
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_guest,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final ChatMessage chatMessage = chatData.get(i);
            viewHolder.messageText.setText(chatMessage.getMessageText());
            viewHolder.messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                    chatMessage.getMessageTime()));
            viewHolder.messageUser.setText(chatMessage.getMessageUser());
            Picasso.with(mContext).load("https://github.com/quoccuong151197/AppAndroid/blob/master/app/src/main/res/drawable/ic_fist_sub.png").into(viewHolder.imgAvarta);
        IImageLoader iImageLoader= new PicassoLoader();
        iImageLoader.loadImage(viewHolder.imgAvarta,"https://github.com/quoccuong151197/AppAndroid/blob/master/app/src/main/res/drawable/ic_fist_sub.png",chatMessage.getMessageUser());
    }

    @Override
    public int getItemCount() {
        return chatData.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage=chatData.get(position);
        if (chatMessage!=null) {
            if (chatMessage.getMessageUser().equals(user.getUserName())) {
                return TYPE_OWER;
            } else
                return TYPE_GUEST;
        } else
            return -1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView messageUser;
        TextView messageTime;
        AvatarView imgAvarta;
        LinearLayout llItemChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_text);
            messageUser = itemView.findViewById(R.id.message_user);
            messageTime = itemView.findViewById(R.id.message_time);
            imgAvarta = itemView.findViewById(R.id.iv_avatar);
            llItemChat=itemView.findViewById(R.id.llIemChat);
        }
    }
}
