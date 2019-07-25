package com.example.appketquaxoso;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.ChatAdapter;
import com.example.callback.TaiKhoanOnClickListener;
import com.example.firebase.ChatMessage;
import com.example.model.User;
import com.example.sharedpreferences.SharedPreferencesManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpHeaders;
import cz.msebera.android.httpclient.entity.StringEntity;


public class HoiNhomFragment extends Fragment {
    View view;
    FloatingActionButton fabSend;
    EditText edtInput;
    RecyclerView rcyMessege;
    ImageView imgBack;
    ChatAdapter adapter;
    ArrayList<ChatMessage> dsMessages;
    ArrayList<String> dsKEY = new ArrayList<>();
    User user=null;
    DialogTaiKhoan dialogTaiKhoan;
    SharedPreferences sharedPreferences;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    String urlImage = "https://github.com/quoccuong151197/AppAndroid/blob/master/app/src/main/res/drawable/ic_fist_sub.png";
    private static final String TAG = "ChatActivity";
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_hoi_nhom, container, false);
        if (SharedPreferencesManager.isPrefUser()==true){
            dialogTaiKhoan= new DialogTaiKhoan(view.getContext(), new TaiKhoanOnClickListener() {
                @Override
                public void onButtonClick(String username) {
                    user= new User(username);
                    String s=user.getUserName();
                    PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit().putString("INFO",user.getUserName()).apply();
                    SharedPreferencesManager.setPrefUser(false);
                    addControls();
                    addEvents();
                }
            });
            dialogTaiKhoan.show();
            String sss=SharedPreferencesManager.getInfoUser();
            String sssd=SharedPreferencesManager.getInfoUser();
        }
        else {
            String sa= PreferenceManager.getDefaultSharedPreferences(view.getContext()).getString("INFO","abc");
            user= new User(sa);
            addControls();
            addEvents();
        }


        return view;
    }

    private void addEvents() {
        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aaaaa = urlImage;
                mData.child("chats")
                        .push()
                        .setValue(new ChatMessage(edtInput.getText().toString(), user.getUserName(), urlImage));
                sendNotification();
                edtInput.setText("");
            }
        });
    }

    private void addControls() {

        dsMessages = new ArrayList<>();
        fabSend = view.findViewById(R.id.fab);
        edtInput = view.findViewById(R.id.edtInput);
        imgBack = view.findViewById(R.id.iv_back);
        rcyMessege = view.findViewById(R.id.lvMessages);
        rcyMessege.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new ChatAdapter(view.getContext(), dsMessages, user);
        rcyMessege.setAdapter(adapter);
        displayChatMessages();
    }
    private void displayChatMessages() {
        mData.child("chats").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                dsMessages.add(chatMessage);
                dsKEY.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
                rcyMessege.smoothScrollToPosition(rcyMessege.getAdapter().getItemCount() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendNotification() {
        try {
            String url = "https://fcm.googleapis.com/fcm/send";
            AsyncHttpClient client = new AsyncHttpClient();

            client.addHeader(HttpHeaders.AUTHORIZATION, "key=AIzaSyBPEu5J5vqRLd_feuZzflDaJgWXBLchjMI");
            client.addHeader(HttpHeaders.CONTENT_TYPE, RequestParams.APPLICATION_JSON);
            JSONObject params = new JSONObject();

            params.put("to", "/topics/ThongBao");

            JSONObject notificationObject = new JSONObject();
            notificationObject.put("body", "Ban co tin nhan moi");
            notificationObject.put("title", "Gui tu " + removeAccent(user.getUserName()));

            params.put("notification", notificationObject);

            StringEntity entity = new StringEntity(params.toString());

            client.post(view.getContext().getApplicationContext(), url, entity, RequestParams.APPLICATION_JSON, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                    Log.i(TAG, responseString);
                }

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                    Log.i(TAG, responseString);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
