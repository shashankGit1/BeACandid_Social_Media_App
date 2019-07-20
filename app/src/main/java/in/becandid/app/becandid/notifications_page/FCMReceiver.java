package in.becandid.app.becandid.notifications_page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import in.becandid.app.becandid.di.rxbus.RxBus;
import in.becandid.app.becandid.infrastructure.Account;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.payload.Payload;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.ui.base.VoicemeApplication;
import in.becandid.app.becandid.ui.chat.MessageActivity;
import in.becandid.app.becandid.ui.profile.ChatTextPojo;

import static in.becandid.app.becandid.ui.base.Constants.CONSTANT_PREF_FILE;

public class FCMReceiver extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private String chatMesssage;
    private ChatTextPojo chatTextPojo;
   // private MessagePojo.Image image;
    private static SharedPreferences recyclerviewpreferences3;
    private static RxBus bus;

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        //  Log.d("NEW_TOKEN",s);

        // String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        MySharedPreferences.registerFirebaseToken((((VoicemeApplication)getApplication()).getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE)), token);

        // now subscribe to `global` topic to receive app wide notifications
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.GLOBAL_TOPIC)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // String msg = "Subscribed to topic <global>";
                    }
                });

        sendTokenBroadcast(token);

    }


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Payload payload = Payload.with(remoteMessage).saveToSharedPreferences(this);

        if (remoteMessage.getData().get("chat") == null){

            if (payload.shouldShowNotification()) {

                // payload.showNotification(this);

                final String data = extractPayloadData(remoteMessage, "text");
                if (data != null){
                    JSONObject json = null;
                    try {
                        json = new JSONObject(data);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final String idusername = json.optString("idusername");
                    if (!idusername.equalsIgnoreCase(MySharedPreferences.getUserId(((VoicemeApplication)getApplication()).getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE)))){
                        // same user who posted post and should not received broadcast of post
                        payload.showNotification(this);

                    }
                } else {
                     payload.showNotification(this);

                }


            }
            payload.execute(this);

        }


        if (remoteMessage.getData().get("chat") != null){
            final String data = extractPayloadData(remoteMessage, "chat");
            JSONObject json = null;
            try {
                json = new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String Id = json.optString("Id");
            final String postId = json.optString("POSTID");
            final String RANDOM_RECEIVER_ID = json.optString("RANDOM_RECEIVER_ID");
            final String RANDOM_RECEIVER_USERNAME = json.optString("RANDOM_RECEIVER_USERNAME");
            final String RANDOM_RECEIVER_AVATAR = json.optString("RANDOM_RECEIVER_AVATAR");
            final String senderId = json.optString("SENDER_USERID");
            final String receiverId = json.optString("RECEIVER_USERID");
            final String chatImage = json.optString("chatImage");
            final String chatText = json.optString("chatText");

            if (MessageActivity.mThis != null) {
                bus = MessageActivity.mThis.getBus();
                bus.send(new Account.sendPrivateMessage(Id, chatText, chatImage, RANDOM_RECEIVER_USERNAME,RANDOM_RECEIVER_AVATAR, RANDOM_RECEIVER_ID, postId, senderId, receiverId, remoteMessage));
            } else {
                Payload payload02 = Payload.with(remoteMessage).saveToSharedPreferences(this);

                if (payload02.shouldShowNotification()) {

                    payload02.showNotification(this);

                }
            }
        }


    }

    private void sendTokenBroadcast(String tokenVal) {
     //   Log.d(TAG, "Sending token broadcast: " + tokenVal);

        Intent tokenBroadcastEvent = new Intent(Constants.ACTION_TOKEN_BROADCAST);
        tokenBroadcastEvent.putExtra(Constants.tokenVal, tokenVal);
        LocalBroadcastManager.getInstance(this).sendBroadcast(tokenBroadcastEvent);
    }

    @Nullable
    static String extractPayloadData(RemoteMessage message, String key) {
        return message.getData().get(key);
    }


}

/*
payload json data

URL
hide = true
"link" -> "{"title":"ho","url":"https:\/\/google.com","open":false}"

Text
hide = true
"text" -> "{"title":"hi","message":"hello","clipboard":false}"

Ping
hide = true
"ping" -> "{}"

Play store link
hide = true
"app" -> "{"package":"com.android.google","title":"hi"}"

 */

