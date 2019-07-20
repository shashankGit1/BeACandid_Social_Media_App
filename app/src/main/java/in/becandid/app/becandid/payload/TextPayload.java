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
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import android.text.TextUtils;
import android.text.style.StyleSpan;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.notifications_page.Truss;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.ui.postDetails.PostsDetailsActivity;

public class TextPayload extends Payload {

    static final String KEY = "text";
    public final String body;
    private final String title;
    private final String postId;
    private final String idusername;
   // private final boolean clipboard;

    private TextPayload(RemoteMessage message, String title, String body, String postId, String idusername) { //, boolean clipboard
        super(message);
        this.title = title;
        this.body = body;
        this.postId = postId;
        this.idusername = idusername;
       // this.clipboard = clipboard;
    }

    static TextPayload create(RemoteMessage message) throws JSONException {
        final String data = extractPayloadData(message, KEY);
        JSONObject json = new JSONObject(data);
        final String title = json.optString("title");
        final String body = json.optString("body");
        final String postId = json.optString("postId");
        final String idusername = json.optString("idusername");
     //   final boolean clipboard = json.optBoolean("clipboard");
        return new TextPayload(message, title, body, postId, idusername); //clipboard
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


        final Intent intent = new Intent(context, PostsDetailsActivity.class);

        intent.putExtra(Constants.postId, postId);
        final Notification notification = getNotificationBuilder(context, message)
                .setSmallIcon(R.drawable.ic_chat_24dp)
                .setContentTitle(TextUtils.isEmpty(title) ? "Text" : title)
                .setContentText(body)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .addAction(0, context.getString(R.string.payload_go_to_post_details), PendingIntent.getActivity(context, 0, intent, 0))
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
