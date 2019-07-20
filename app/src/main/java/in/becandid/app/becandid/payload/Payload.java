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
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.text.format.DateUtils;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.postDetails.PostsDetailsActivity;


public abstract class Payload implements Comparable<Payload> {

    private static final String KEY = "payloads";

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    long timestamp;

    @Nullable
    transient RemoteMessage message;

    transient CharSequence display;

    Payload() {
        this.timestamp = System.currentTimeMillis();
    }

    Payload(@NonNull RemoteMessage message) {
        this.timestamp = System.currentTimeMillis();
        this.message = message;
    }

    @NonNull
    public static Payload with(RemoteMessage message) {
        Map<String, String> data = message.getData();

        for (Map.Entry<String, String> entry : data.entrySet())
        {
          //  System.out.println(entry.getKey() + "/" + entry.getValue());
            try {
                switch (entry.getKey()) {
                    case PingPayload.KEY:
                        return PingPayload.create(message);
                    case TextPayload.KEY:
                        return TextPayload.create(message);
                        case ChatPayload.KEY:
                        return ChatPayload.create(message);
                    case LinkPayload.KEY:
                        return LinkPayload.create(message);
                    case AppPayload.KEY:
                        return AppPayload.create(message);
                    default:
                        break;
                }
            } catch (Exception ignored) {
            }

        }


      //  Set<Map.Entry<String, String>> entries = data.entrySet();
        //for (Map.Entry<String, String> entry : entries) {

       // }
        return RawPayload.create(message);
    }

    @Nullable
    public static Payload with(@NonNull String key, @NonNull String value) {
        final String[] split = key.split("\\|");
        if (split.length != 2) {
            return null;
        }
        switch (split[1]) {
            case PingPayload.KEY:
                return GSON.fromJson(value, PingPayload.class);
            case TextPayload.KEY:
                return GSON.fromJson(value, TextPayload.class);
            case LinkPayload.KEY:
                return GSON.fromJson(value, LinkPayload.class);
                case ChatPayload.KEY:
                return GSON.fromJson(value, ChatPayload.class);
            case AppPayload.KEY:
                return GSON.fromJson(value, AppPayload.class);
            case RawPayload.KEY:
                return GSON.fromJson(value, RawPayload.class);
            default:
                return null;
        }
    }

    @NonNull
    public static List<Payload> fetchPayloads(@NonNull Context context) {
        final List<Payload> payloads = new ArrayList<>();
        final SharedPreferences sp = getSharedPreferences(context);
        for (Map.Entry<String, ?> entry : sp.getAll().entrySet()) {
            final String key = entry.getKey();
            final String value = (String) entry.getValue();
            Payload payload = with(key, value);
            if (payload != null) {
                payloads.add(payload);
            }
        }
        Collections.sort(payloads);
        return payloads;
    }

    @Nullable
    static String extractPayloadData(RemoteMessage message, String key) {
        return message.getData().get(key);
    }

    public static boolean remove(@NonNull Context context, @NonNull Payload payload) {
        payload.cancelNotification(context);
        final SharedPreferences sp = getSharedPreferences(context);
        for (Map.Entry<String, ?> entry : sp.getAll().entrySet()) {
            if (entry.getKey().equals(payload.timestamp + "|" + payload.key())) {
                sp.edit().remove(entry.getKey()).apply();
                return true;
            }
        }
        return false;
    }

    public static void removeAll(Context context) {
        getNotificationManager(context).cancelAll();
        getSharedPreferences(context).edit().clear().apply();
    }

    public static void registerOnSharedPreferenceChanges(@NonNull Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        getSharedPreferences(context).registerOnSharedPreferenceChangeListener(listener);
    }

    public static void unregisterOnSharedPreferenceChanges(@NonNull Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        getSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(listener);
    }

    private static SharedPreferences getSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    private static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    protected abstract String key();

    public final Payload saveToSharedPreferences(@NonNull Context context) {
        getSharedPreferences(context).edit().putString(timestamp + "|" + key(), GSON.toJson(this)).apply();
        return this;
    }

    public boolean shouldShowNotification() {
        return !(message != null && Boolean.valueOf(message.getData().get("hide")));
    }

    public abstract void execute(Context context);

    public abstract void showNotification(Context context);

    public abstract void cancelNotification(Context context);

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

    @NonNull
    final NotificationCompat.Builder getNotificationBuilder(@NonNull Context context, RemoteMessage message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context);
        }
        return new NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))

                .setContentIntent(PostsDetailsActivity.createPendingIntent(context, message))
                .setLocalOnly(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);
    }

    final void showNotification(@NonNull Context context, @NonNull Notification notification, @NonNull String tag, @IdRes int id) {
        getNotificationManager(context).notify(tag, id, notification);
    }

    final void showNotification(@NonNull Context context, @NonNull Notification notification, @IdRes int id) {
        getNotificationManager(context).notify(id, notification);
    }

    final void cancelNotification(@NonNull Context context, @NonNull String tag, @IdRes int id) {
        getNotificationManager(context).cancel(tag, id);
    }

    final void cancelNotification(@NonNull Context context, @IdRes int id) {
        getNotificationManager(context).cancel(id);
    }

    @Override
    public int compareTo(@NonNull Payload payload) {
        return (payload.timestamp < timestamp) ? -1 : ((payload.timestamp == timestamp) ? 0 : 1);
    }

    public abstract CharSequence getFormattedCharSequence(Context context);

    public final CharSequence getFormattedTimestamp() {
        return DateUtils.getRelativeTimeSpanString(timestamp, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }

}
