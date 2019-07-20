package in.becandid.app.becandid.notifications_page;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.di.rxbus.Events;
import in.becandid.app.becandid.di.rxbus.RxBus;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.payload.Payload;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.ui.base.VoicemeApplication;
import in.becandid.app.becandid.ui.chat.MessageActivity;
import in.becandid.app.becandid.ui.profile.ChatTextPojo;
import timber.log.Timber;

import static in.becandid.app.becandid.ui.base.Constants.CONSTANT_PREF_FILE;

public class FCMReceiverOld extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private String chatMesssage;
    private ChatTextPojo chatTextPojo;
   // private MessagePojo.Image image;
    private static SharedPreferences recyclerviewpreferences3;
    private static RxBus bus;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //  Log.d("NEW_TOKEN",s);

        // String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        final Intent intent = new Intent("tokenReceiver");
        // You can also include some extra data.
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        intent.putExtra("token",s);
        broadcastManager.sendBroadcast(intent);

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
        if (payload.shouldShowNotification()) {
            payload.showNotification(this);
        }
        payload.execute(this);



        String channelId  = getString(R.string.default_notification_channel_id);



        recyclerviewpreferences3 = ((VoicemeApplication)getApplication()).getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);
      ///  bus = ((VoicemeApplication)getApplication()).getBus();

        if (MessageActivity.mThis != null){
            bus = MessageActivity.mThis.getBus();
            bus.send(new Events.sendCommentLike( "", "0"));
        }






        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        //  Log.d(TAG, "From: " + remoteMessage.getFrom());


        String checkdata = remoteMessage.getData().get(4);


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
      /*      Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Timber.d("message id : %s", remoteMessage.getMessageId());
            Timber.d("message notification body : %s", remoteMessage.getNotification().getBody());
            Timber.d("message data : %s", remoteMessage.getData());
            Timber.d("message from : %s", remoteMessage.getFrom());
            Timber.d("message to : %s", remoteMessage.getTo());
            Timber.d("message type : %s", remoteMessage.getMessageType());
            Timber.d("message sent time : %s", remoteMessage.getSentTime());
            Timber.d("message notification title : %s", remoteMessage.getNotification().getTitle());
            Timber.d("message click action : %s", remoteMessage.getNotification().getClickAction()); */

            List<String> notificationData =
                    Arrays.asList(remoteMessage.getData().toString().replace("{", "").replace("}", "").replace(" ", "").replace(" ", "").split(","));


            //   Set<Map.Entry<String, String>> values = remoteMessage.getData().entrySet();

            if (remoteMessage.getData().containsKey("chat")){


                if (bus.hasObservers()){
                    //  Timber.e("got message logged", new Object[0]);
                    String chatMessage = getJsonFromList(notificationData, remoteMessage);
                    //  Timber.e(chatMessage, new Object[0]);
                    Gson gson = new Gson();
                    Intent intent;

                   /* if (remoteMessage.getData().get(7).contains("https")){
                        Timber.d("this is image. convert it to Image POJO");
                        showChatNotification(remoteMessage.getNotification().getTitle(), remoteMessage);
                    } */
                    try {
                        synchronized (this) {
                            this.chatTextPojo = (ChatTextPojo) gson.fromJson(chatMessage, ChatTextPojo.class);
                        }

                         if (MessageActivity.mThis.equals(this.chatTextPojo.getSenderId())) {
                            bus.send(chatTextPojo);

                         //   intent = new Intent(getBaseContext(), ChatNotificationService.class);
                        //    intent.putExtra(Constants.CHAT_MESSAGE, this.chatTextPojo);
                        //    startService(intent);
                        }
                    } catch (Exception ex) {
                        try {
                            ex.printStackTrace();

                            showChatNotification(remoteMessage.getNotification().getTitle(), remoteMessage,channelId);

                        } catch (Throwable th) {

                            showChatNotification(remoteMessage.getNotification().getTitle(), remoteMessage,channelId);

                        }
                    }
                }


                else {
                    Intent intent = new Intent();
                    intent.setAction("in.voiceme.app.voiceme.CHAT");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("fromNotification", true);
                    PendingIntent pendingIntent =
                            PendingIntent.getActivity(
                                    this, 1 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    // builder.setGroup(remoteNotification.getUserNotificationGroup());

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channelId)

                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText("View the message inside Voiceme")
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL) // must requires VIBRATE permission
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setGroup(remoteMessage.getNotification().getTag())
                            .setSound(defaultSoundUri)
                            .setSmallIcon(R.drawable.ic_stat_name)
                            .setContentIntent(pendingIntent);

                    NotificationManagerCompat  notificationManager = NotificationManagerCompat.from(this);
                    //    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    //    long time = System.currentTimeMillis();
                    //  String tmpStr = String.valueOf(time);
                    // String last4Str = tmpStr.substring(tmpStr.length() - 5);
                    //    int notificationId = Integer.valueOf(last4Str);

                    notificationManager.notify(0 /* ID of notification */,
                            notificationBuilder.build());

                }
            } else if(remoteMessage.getData().containsKey("announce")) {

                Intent intent = new Intent();
                intent.setAction("in.voiceme.app.voiceme.REACTIONS");
                intent.putExtra(Constants.POST_BACKGROUND, remoteMessage.getData().get("postId"));
                intent.putExtra(Constants.IDUSERNAME, remoteMessage.getData().get("userId"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("fromNotification", true);
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channelId)

                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL) // must requires VIBRATE permission
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setGroup(remoteMessage.getNotification().getTag())
                        .setSound(defaultSoundUri)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentIntent(pendingIntent);

                NotificationManagerCompat  notificationManager = NotificationManagerCompat.from(this);
                //    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                long time = System.currentTimeMillis();
                String tmpStr = String.valueOf(time);
                String last4Str = tmpStr.substring(tmpStr.length() - 5);
                int notificationId = Integer.valueOf(last4Str);
                notificationManager.notify(notificationId /* ID of notification */,
                        notificationBuilder.build());

                // announcement(, , remoteMessage);
                //     saveNotificationObject(getJsonFromList(notificationData, remoteMessage));
            } else {
                //     saveNotificationObject(getJsonFromList(notificationData, remoteMessage));
                if (MySharedPreferences.getUserId(recyclerviewpreferences3).equals(remoteMessage.getData().get("receiverId"))) {
                    Timber.d("same user sending notification");
                } else {

                    Intent intent = new Intent();
                    intent.setAction("in.voiceme.app.voiceme.REACTIONS");
                    intent.putExtra(Constants.POST_BACKGROUND, remoteMessage.getData().get("postId"));
                    intent.putExtra(Constants.IDUSERNAME, remoteMessage.getData().get("receiverId"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("fromNotification", true);
                    PendingIntent pendingIntent =
                            PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)

                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(remoteMessage.getNotification().getBody())
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL) // must requires VIBRATE permission
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setGroup(remoteMessage.getNotification().getTag())
                            .setSound(defaultSoundUri)
                            .setSmallIcon(R.drawable.ic_stat_name)
                            .setContentIntent(pendingIntent);

                    NotificationManagerCompat  notificationManager = NotificationManagerCompat.from(this);
                    //    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                //    long time = System.currentTimeMillis();
                  //  String tmpStr = String.valueOf(time);
                   // String last4Str = tmpStr.substring(tmpStr.length() - 5);
                //    int notificationId = Integer.valueOf(last4Str);
                    notificationManager.notify(0 /* ID of notification */,
                            notificationBuilder.build());
               //     manager.notify(0, notificationBuilder.build());
                }


            }

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See showNotification method below.
    }
    // [END receive_message]


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    private void showChatNotification(String messageBody, RemoteMessage remoteMessage, String channelId) {
    /*
    Creates pending intent
     */

        Intent intent = new Intent();
        intent.setAction("in.voiceme.app.voiceme.CHAT");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fromNotification", true);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        this, 1 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // builder.setGroup(remoteNotification.getUserNotificationGroup());

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channelId)

                .setContentTitle(messageBody)
                .setContentText("View the message inside Voiceme")
                .setAutoCancel(true)
                //.setGroup(remoteMessage.getNotification().getTag())
                .setSound(defaultSoundUri)
                .setDefaults(Notification.DEFAULT_ALL) // must requires VIBRATE permission
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pendingIntent);


        NotificationManagerCompat  notificationManager = NotificationManagerCompat.from(this);
        //    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //    long time = System.currentTimeMillis();
        //  String tmpStr = String.valueOf(time);
        // String last4Str = tmpStr.substring(tmpStr.length() - 5);
        //    int notificationId = Integer.valueOf(last4Str);

        notificationManager.notify(0 /* ID of notification */,
                notificationBuilder.build());
    }

    private String getJsonFromList(List<String> notificationData, RemoteMessage remoteMessage) {
        String json = "{";

        json += "\"sentTime\":\"" + remoteMessage.getSentTime() + "\"";
        json += ",";

        for (int i = 0; i < notificationData.size(); i++) {
            Timber.d("Split string %s : %s", i, notificationData.get(i));
            String[] keyValuePair = notificationData.get(i).split("=");
            json += "\"";
            json += keyValuePair[0];
            json += "\"";
            json += ":";
            json += "\"";
            json += keyValuePair[1];
            json += "\"";
            if (i != notificationData.size() - 1) json += ",";
        }

        json += "}";
        Timber.d("json data : %s", json);

        return json;
    }



}

