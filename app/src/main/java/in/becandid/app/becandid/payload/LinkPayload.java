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

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import android.text.TextUtils;
import android.text.style.StyleSpan;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.notifications_page.Truss;

public class LinkPayload extends Payload {

    static final String KEY = "link";
    private final String url;
    private final String title;
    private final boolean open;

    private LinkPayload(RemoteMessage message, String title, String url, boolean open) {
        super(message);
        this.title = title;
        this.url = url;
        this.open = open;
    }

    static LinkPayload create(RemoteMessage message) throws JSONException {
        final String data = extractPayloadData(message, KEY);
        JSONObject json = new JSONObject(data);
        String title = json.optString("title");
        String url = json.optString("url");
        url = TextUtils.isEmpty(url) || url.contains("://") ? url : ("http://" + url);
        boolean force = json.optBoolean("open");
        return new LinkPayload(message, title, url, force);
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
        final Intent intent = getIntent();
        final NotificationCompat.Builder builder = getNotificationBuilder(context, message)
                .setSmallIcon(R.drawable.ic_link_24dp)
                .setContentTitle(TextUtils.isEmpty(title) ? "Link" : title)
                .setContentText(url);
        if (!TextUtils.isEmpty(url)) {
            builder.addAction(0, context.getString(R.string.payload_link_open), PendingIntent.getActivity(context, 0, intent, 0));
        }
        showNotification(context, builder.build(), String.valueOf(timestamp), R.id.link_notification_id);
    }

    public Intent getIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    public void cancelNotification(Context context) {
        cancelNotification(context, String.valueOf(timestamp), R.id.link_notification_id);
    }

    @Override
    public CharSequence getFormattedCharSequence(Context context) {
        if (display == null) {
            display = new Truss()
                    .pushSpan(new StyleSpan(android.graphics.Typeface.BOLD)).append("title: ").popSpan().append(title).append('\n')
                    .pushSpan(new StyleSpan(android.graphics.Typeface.BOLD)).append("url: ").popSpan().append(url).append('\n')
                    .pushSpan(new StyleSpan(android.graphics.Typeface.BOLD)).append("open: ").popSpan().append(String.valueOf(open))
                    .build();
        }
        return display;
    }

    @Override
    public void execute(Context context) {
        if (open) {
            try {
                context.getApplicationContext().startActivity(getIntent());
            } catch (Exception ignore) {

            }
        }
    }
}
