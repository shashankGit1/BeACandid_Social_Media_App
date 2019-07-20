/*
 * Copyright 2017 Simon Marquis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.becandid.app.becandid.payload;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.text.style.StyleSpan;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.notifications_page.Truss;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.ui.chat.MessageActivity;

public class ChatPayload extends Payload {

    static final String KEY = "chat";
    public final String body;
    private final String title;
    private final String postId;
    private final String randomUserIID;
    private final String SENDER_USERID;
    private final String RANDOM_RECEIVER_USERNAME;
    private final String RANDOM_RECEIVER_ID;
    private final String RECEIVER_USERID;
   // private final boolean clipboard;

    private ChatPayload(RemoteMessage message, String title, String body, String randomUserIID, String postId, String SENDER_USERID, String RANDOM_RECEIVER_USERNAME, String RANDOM_RECEIVER_ID, String RECEIVER_USERID) { //, boolean clipboard
        super(message);
        this.title = title;
        this.body = body;
        this.randomUserIID = randomUserIID;
        this.postId = postId;
        this.SENDER_USERID = SENDER_USERID;
        this.RANDOM_RECEIVER_USERNAME = RANDOM_RECEIVER_USERNAME;
        this.RANDOM_RECEIVER_ID = RANDOM_RECEIVER_ID;
        this.RECEIVER_USERID = RECEIVER_USERID;
       // this.clipboard = clipboard;
    }

    static ChatPayload create(RemoteMessage message) throws JSONException {
        final String data = extractPayloadData(message, KEY);
        JSONObject json = new JSONObject(data);
        final String title = json.optString("title");
        final String body = json.optString("body");

        final String randomUserIID = json.optString("RANDOM_SENDER_ID");
        final String postId = json.optString("POSTID");
        final String SENDER_USERID = json.optString("SENDER_USERID");
        final String RANDOM_RECEIVER_USERNAME = json.optString("RANDOM_RECEIVER_USERNAME");
        final String RANDOM_RECEIVER_ID = json.optString("RANDOM_RECEIVER_ID");
        final String RECEIVER_USERID = json.optString("RECEIVER_USERID");

     //   final boolean clipboard = json.optBoolean("clipboard");
        return new ChatPayload(message, title, body,randomUserIID, postId, SENDER_USERID,RANDOM_RECEIVER_USERNAME,RANDOM_RECEIVER_ID,RECEIVER_USERID); //clipboard
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void createNotificationChannel(Context context) {
        String id = context.getString(R.string.notification_channel_id);
        CharSequence name = context.getString(R.string.notification_channel_name);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setShowBadge(true);
        getNotificationManager(context).createNotificationChannel(channel);
    }

    private static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @NonNull
    NotificationCompat.Builder getNotificationBuilderChat(@NonNull Context context, RemoteMessage message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context);
        }
        return new NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))

                .setContentIntent(MessageActivity.createPendingIntent(context, message))
                .setLocalOnly(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);
    }

    @Override
    protected String key() {
        return KEY;
    }

    @Override
    public void showNotification(Context context) {
        if (message == null) {
            return;
        }


        final Intent intent = new Intent(context, MessageActivity.class);

        intent.putExtra(Constants.RANDOM_SENDER_ID, randomUserIID); // username inside message
        intent.putExtra(Constants.POSTID, postId); // username inside message
        intent.putExtra(Constants.SENDER_USERID, SENDER_USERID); // username inside message
        intent.putExtra(Constants.RANDOM_RECEIVER_USERNAME, RANDOM_RECEIVER_USERNAME); // direct username in chat
        intent.putExtra(Constants.RANDOM_RECEIVER_ID, RANDOM_RECEIVER_ID); // direct username id in chat
        intent.putExtra(Constants.RECEIVER_USERID, RECEIVER_USERID); // direct random user id in chat

       // intent.putExtra(Constants.postId, postId);

        final Notification notification = getNotificationBuilderChat(context, message)
                .setSmallIcon(R.drawable.ic_chat_24dp)
                .setContentTitle(TextUtils.isEmpty(title) ? "Text" : title)
                .setContentText(body)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .addAction(0, context.getString(R.string.payload_go_to_chat), PendingIntent.getActivity(context, 0, intent, 0))
                .build();
        showNotification(context, notification, String.valueOf(timestamp), R.id.text_notification_id);
    }

    @Override
    public void cancelNotification(Context context) {
        cancelNotification(context, String.valueOf(timestamp), R.id.text_notification_id);
    }

    @Override
    public CharSequence getFormattedCharSequence(Context context) {
        if (display == null) {
            display = new Truss()
                    .pushSpan(new StyleSpan(android.graphics.Typeface.BOLD)).append("title: ").popSpan().append(title).append('\n')
                    .pushSpan(new StyleSpan(android.graphics.Typeface.BOLD)).append("text: ").popSpan().append(body).append('\n')
                //    .pushSpan(new StyleSpan(android.graphics.Typeface.BOLD)).append("clipboard: ").popSpan().append(String.valueOf(clipboard))
                    .build();
        }
        return display;
    }

    @Override
    public void execute(Context context) {

     /*   if (clipboard) {
            final Context app = context.getApplicationContext();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    copyToClipboard(app.getApplicationContext(), text);
                }
            });
        } */
    }
}
