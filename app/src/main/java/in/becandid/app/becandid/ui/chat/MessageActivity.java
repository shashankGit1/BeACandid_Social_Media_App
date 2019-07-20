package in.becandid.app.becandid.ui.chat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.di.rxbus.RxBus;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.SuccessResponseChat;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.notifications_page.ChatNotificationService;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.chat_design.commons.ImageLoader;
import in.becandid.app.becandid.chat_design.messages.MessageHolders;
import in.becandid.app.becandid.chat_design.messages.MessagesList;
import in.becandid.app.becandid.chat_design.messages.MessagesListAdapter;
import in.becandid.app.becandid.infrastructure.Account;
import in.becandid.app.becandid.payload.Payload;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.chat02.model.Dialog;
import in.becandid.app.becandid.ui.chat02.model.Message;
import in.becandid.app.becandid.ui.chat02.model.User;
import in.becandid.app.becandid.ui.postDetails.PostsDetailsActivity;
import in.becandid.app.becandid.ui.profile.ChatTextPojo;
import in.becandid.app.becandid.utils.ActivityUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static in.becandid.app.becandid.ui.base.Constants.POSTID;
import static in.becandid.app.becandid.ui.base.Constants.RANDOM_RECEIVER_ID;
import static in.becandid.app.becandid.ui.base.Constants.RANDOM_RECEIVER_USERNAME;

public class  MessageActivity extends BaseActivity implements
        MessagesListAdapter.SelectionListener,
        MessagesListAdapter.OnMessageClickListener<Message>,
        View.OnClickListener,
        ChatNotificationService.Callbacks, ChatMvpView, MessagesListAdapter.OnLoadMoreListener {

    private static final int PAGE_START = 1;
    private static final int PERMISSION_REQUEST_CODE = 1121;

    private static final String[] TAKE_PICTURE_PERM = {CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE};
    private static final int PERMISSION_REQUEST_UPLOAD = 1023;


    private int currentPage = PAGE_START;
    private ProgressBar activity_messages_progress;


    private List<Message> currentResponse;
    protected CompositeDisposable disposables;
    private MessagesList messagesList = null;
    private ViewGroup rootView;
    public MessagesListAdapter<Message> adapter;
    // private MessageInput input;
    private View progressFrame;
    private ChatTextPojo chatTextPojo;
    //   private List<MessageActivity.Message> chatMessages;
    private int selectionCount;
   // private ImageButton emojiButton;
    private ImageButton chat_image;
    private EditText editText;
    private ImageButton sendButton;
    private String messageCopied;

    static final String USER_ID_SECOND = "USER_ID_SECOND";
    static final String USERNAME = "USERNAME";

    private String onlineString = " ";

    private static final int REQUEST_SELECT_IMAGE = 100;

    public static MessageActivity mThis = null;
    private Menu menu;
    private String base64String;
    private String receiver_user_name;
    private String SENDER_USERID;
    private String RANDOM_RECEIVER_AVATAR;
    private String RECEIVER_USERID;
    private String postId;
    private String randomId;
  //  private String receiver_random_user_id;
  //  private int sender_random_user_id_int;
  //  private String sender_random_user_id;
    private String image_Url = null;
    private File tempOutputFile;
    private RxBus bus;
    private int addTotalItems = 0;
    // List<MessagePojo> messages;

    @Inject
    ChatMvpPresenter<ChatMvpView> mPresenter;

    public static Intent createIntent(@NonNull Context context, @NonNull RemoteMessage message) {


        JSONObject json = null;
        try {
            json = new JSONObject(message.getData().get("chat"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String randomUserIID = json.optString("RANDOM_SENDER_ID");
        final String postId = json.optString("POSTID");
        final String SENDER_USERID = json.optString("SENDER_USERID");
        final String RANDOM_RECEIVER_USERNAME = json.optString("RANDOM_RECEIVER_USERNAME");
        final String RANDOM_RECEIVER_AVATAR = json.optString("RANDOM_RECEIVER_AVATAR");
        final String RANDOM_RECEIVER_ID = json.optString("RANDOM_RECEIVER_ID");
        final String RECEIVER_USERID = json.optString("RECEIVER_USERID");
        final String chatImage = json.optString("chatImage");
        final String chatText = json.optString("chatText");

        Intent i = new Intent(context, MessageActivity.class);


        i.putExtra(Constants.RANDOM_SENDER_ID, randomUserIID); // username inside message
        i.putExtra(Constants.POSTID, postId); // username inside message
        i.putExtra(Constants.SENDER_USERID, SENDER_USERID); // username inside message
        i.putExtra(Constants.RANDOM_RECEIVER_USERNAME, RANDOM_RECEIVER_USERNAME); // direct username in chat
        i.putExtra(Constants.RANDOM_RECEIVER_AVATAR, RANDOM_RECEIVER_AVATAR); // direct username in chat
        i.putExtra(Constants.RANDOM_RECEIVER_ID, RANDOM_RECEIVER_ID); // direct username id in chat
        i.putExtra(Constants.RECEIVER_USERID, RECEIVER_USERID); // direct random user id in chat
        i.putExtra("chatImage", chatImage); // direct random user id in chat
        i.putExtra("chatText", chatText); // direct random user id in chat

      //  i.putExtra(Constants.postId, postId);

        return i;
    }

    public static PendingIntent createPendingIntent(@NonNull Context context, @NonNull RemoteMessage message) {
        String messageId = message.getMessageId();
        return PendingIntent.getActivity(context, messageId != null ? messageId.hashCode() : (int) System.currentTimeMillis(), MessageActivity.createIntent(context, message), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(MessageActivity.this);

        disposables = new CompositeDisposable();

        progressFrame = findViewById(R.id.chat_details);
        rootView = (ViewGroup) findViewById(R.id.message_rootview);
        activity_messages_progress = (ProgressBar) findViewById(R.id.activity_messages_progress);

        if(toolbar!= null){
            toolbar.setBackground(getResources().getDrawable(R.drawable.dark_blue_top_gradient));
        }

       /* NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.deleteNotificationChannel(IOS_CHANNEL_ID); */

        Intent intent = getIntent();
        receiver_user_name = intent.getStringExtra(RANDOM_RECEIVER_USERNAME); // seconnd person username random
        randomId = intent.getStringExtra(Constants.RANDOM_RECEIVER_ID); // actual user ID of other person
        postId = intent.getStringExtra(POSTID); // actual user ID of other person
      //  sender_random_user_id = intent.getStringExtra(RANDOM_SENDER_ID); // actual user ID of us
        SENDER_USERID = intent.getStringExtra(Constants.RECEIVER_USERID); // actual user ID of us
        RANDOM_RECEIVER_AVATAR = intent.getStringExtra("RANDOM_RECEIVER_AVATAR"); // actual user ID of us
        RECEIVER_USERID = intent.getStringExtra(Constants.SENDER_USERID); // second user


        if (receiver_user_name == null){
            if (getIntent().getExtras() != null) {
                for (String key : getIntent().getExtras().keySet()) {
                    String text = getIntent().getExtras().getString("chat");

                    JSONObject arr = null;
                    try {
                        arr = new JSONObject(text);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    receiver_user_name = arr.optString(RANDOM_RECEIVER_USERNAME);
                    randomId = arr.optString(RANDOM_RECEIVER_ID);
                    postId = arr.optString(POSTID);
                    RECEIVER_USERID = arr.optString(Constants.SENDER_USERID);
                    RANDOM_RECEIVER_AVATAR = arr.optString("RANDOM_RECEIVER_AVATAR");

                    break;

                }
            }
        }


        tempOutputFile = new File(getExternalCacheDir(), "temp-profile_image.jpg");

        //   Toast.makeText(this, "User ID: " + messageActivityuserId, Toast.LENGTH_SHORT).show();

        toolbar.setNavigationIcon(R.drawable.ic_clear_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText = (EditText) findViewById(R.id.messageEmojiEdittext);
   //     emojiButton = (ImageButton) findViewById(R.id.main_activity_emoji);
        chat_image = (ImageButton) findViewById(R.id.chat_image);
        sendButton = (ImageButton) findViewById(R.id.emoji_send_message);

        messagesList = (MessagesList) findViewById(R.id.messagesList);

        // Repeating code


        messagesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });


        chat_image.setOnClickListener(this);

  //      emojiButton.setOnClickListener(this);

        sendButton.setOnClickListener(this);


            if (isNetworkConnected()){
                progressFrame.setVisibility(View.VISIBLE);

                try {
                    currentPage = PAGE_START;

                    // todo add code to make all chat go away
                    mPresenter.getChatMessages(MySharedPreferences.getUserId(preferences), RECEIVER_USERID,
                            postId, MySharedPreferences.getFireBaseToken(preferences), String.valueOf(currentPage));
                  //  chatMessages();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //        startActivity(new Intent(this, OfflineActivity.class));
                Toast.makeText(this, "You are not connected to internet", Toast.LENGTH_SHORT).show();
            }


        disposables.add(
                getBus().asFlowable().observeOn(AndroidSchedulers.mainThread()).subscribe(
                event -> {
                    if (event instanceof Account.sendPrivateMessage) {

                        if (((Account.sendPrivateMessage) event).SENDER_USERID.equalsIgnoreCase(RECEIVER_USERID)){
                            // todo need testing
                            if (!(((Account.sendPrivateMessage) event).chatImage.equalsIgnoreCase(""))){

                                Message message = new Message(((Account.sendPrivateMessage) event).Id, new User(((Account.sendPrivateMessage) event).SENDER_USERID,
                                        ((Account.sendPrivateMessage) event).RANDOM_RECEIVER_USERNAME,((Account.sendPrivateMessage) event).RANDOM_RECEIVER_AVATAR, false),
                                        ((Account.sendPrivateMessage) event).chatText, String.valueOf(System.currentTimeMillis()));

                                message.setImage(new Message.Image(((Account.sendPrivateMessage) event).chatImage));
                                adapter.addToStart(message, true);

                               /* MessagePojo messagePojo = new
                                        MessagePojo(((Account.sendPrivateMessage) event).Id, ((Account.sendPrivateMessage) event).chatText,
                                        new UserPojoReceiver(((Account.sendPrivateMessage) event).senderId,
                                                ((Account.sendPrivateMessage) event).senderName,((Account.sendPrivateMessage) event).avatar, true,((Account.sendPrivateMessage) event).postId),((Account.sendPrivateMessage) event).postId);
                                messagePojo.setImage(new MessagePojo.Image(((Account.sendPrivateMessage) event).chatImage));
                                adapter.addToStart(messagePojo, true); */

                            } else {

                                Message message = new Message(((Account.sendPrivateMessage) event).Id, new User(((Account.sendPrivateMessage) event).SENDER_USERID,
                                        ((Account.sendPrivateMessage) event).RANDOM_RECEIVER_USERNAME,((Account.sendPrivateMessage) event).RANDOM_RECEIVER_AVATAR, false),
                                        ((Account.sendPrivateMessage) event).chatText, String.valueOf(System.currentTimeMillis()));

                                message.setImage(new Message.Image(null));
                                adapter.addToStart(message, true);

                                       /* new
                                        MessagePojo(((Account.sendPrivateMessage) event).senderId, ((Account.sendPrivateMessage) event).chatText,
                                        new UserPojoReceiver(((Account.sendPrivateMessage) event).senderId,
                                                ((Account.sendPrivateMessage) event).senderName,((Account.sendPrivateMessage) event).avatar,
                                                true,((Account.sendPrivateMessage) event).postId),((Account.sendPrivateMessage) event).postId), true); */
                            }
                        } else {
                            // todo create notification

                            Payload payload02 = Payload.with(((Account.sendPrivateMessage) event).remoteMessage).saveToSharedPreferences(this);

                            if (payload02.shouldShowNotification()) {

                                payload02.showNotification(this);

                            }

                        }





                        }
                }, error-> Log.e("OnError: ", error.getMessage())));


    }

    public RxBus getBus(){
        if (bus == null) {
            bus = new RxBus();
        }

        return bus;
    }


    private void sendTextMessage() {

        progressFrame.setVisibility(View.VISIBLE);


        byte[] data = new byte[0];
        try {
            String editData = editText.getText().toString().trim();
            data = editData.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        base64String = Base64.encodeToString(data, Base64.DEFAULT);
        if (base64String.length() > 800){
            Toast.makeText(MessageActivity.this, "Please enter short messages", Toast.LENGTH_SHORT).show();
        } else {
            if (isNetworkConnected()){

                // todo need testing
                Message message = new Message(MySharedPreferences.getUserId(preferences), new User(MySharedPreferences.getUserId(preferences),
                        "harish","", false),
                        base64String, String.valueOf(System.currentTimeMillis()));

                message.setImage(new Message.Image(null));
                adapter.addToStart(message, true);


                progressFrame.setVisibility(View.GONE);


             /*   adapter.addToStart(
                        new MessagePojo(MySharedPreferences.getUserId(preferences), base64String,
                        new UserPojoReceiver(MySharedPreferences.getUserId(preferences),
                        "harish","",true, postId), postId),true); */

                mPresenter.sendTextNotification(MySharedPreferences.getUserId(preferences),RECEIVER_USERID,
                        base64String.replaceAll("\n",""), "", randomId,
                        postId, MySharedPreferences.getFireBaseToken(preferences));

           //  mPresenter.check_blommck_userOnline(messageActivityuserId, MySharedPreferences.getUserId(preferences), MySharedPreferences.getFireBaseToken(preferences), false);

            //    checkUserBlockText();
            } else {
                Toast.makeText(MessageActivity.this, "You are not connected to internet", Toast.LENGTH_SHORT).show();
            }
            editText.setText("");
        }
    }



    @Override protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onResume() {
        mThis = this;
        super.onResume();
    }
    @Override
    protected void onPause() {

        mThis = null;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        if (bus.hasObservers()){
            disposables.dispose();
        }


        super.onDestroy();
    }


    private void changeProfileRequest() {
        //  ActivityUtils.openGallery(this);
     //   ActivityUtils.cameraPermissionGranted(this);
       // changeAvatar();

        if (checkPermission()) {

            changeAvatar();
            //main logic or main code

            // . write your main code to execute, It will execute if the permission is already given.

        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private boolean checkReadPermission() {
        if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                TAKE_PICTURE_PERM,
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   // Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    changeAvatar();
                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions to send chat image",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
            case PERMISSION_REQUEST_UPLOAD:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    uploadFile(tempOutputFile);
                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions to send chat image",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;


        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MessageActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



    private void changeAvatar() {
        List<Intent> otherImageCaptureIntent = new ArrayList<>();
        List<ResolveInfo> otherImageCaptureActivities =
                getPackageManager().queryIntentActivities(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                        0); // finding all intents in apps which can handle capture image
        // loop through all these intents and for each of these activities we need to store an intent
        for (ResolveInfo info : otherImageCaptureActivities) { // Resolve info represents an activity on the system that does our work
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.setClassName(info.activityInfo.packageName,
                    info.activityInfo.name); // declaring explicitly the class where we will go
            // where the picture activity dump the image
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempOutputFile));
            otherImageCaptureIntent.add(captureIntent);
        }

        // above code is only for taking picture and letting it go through another app for cropping before setting to imageview
        // now below is for choosing the image from device

        Intent selectImageIntent = new Intent(Intent.ACTION_PICK);
        selectImageIntent.setType("image/*");

        Intent chooser = Intent.createChooser(selectImageIntent, "Choose Avatar");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, otherImageCaptureIntent.toArray(
                new Parcelable[otherImageCaptureActivities.size()]));  // add 2nd para as intent of parcelables.

        startActivityForResult(chooser, REQUEST_SELECT_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            tempOutputFile.delete();
            return;
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_IMAGE) {
                // user selected an image off their device. other condition they took the image and that image is in our tempoutput file
                Uri outputFile;
                Uri tempFileUri = Uri.fromFile(tempOutputFile);
                // if statement will detect if the user selected an image from the device or took an image
                if (data != null && (data.getAction() == null || !data.getAction()
                        .equals(MediaStore.ACTION_IMAGE_CAPTURE))) {
                    //then it means user selected an image off the device
                    // so we can get the Uri of that image using data.getData
                    outputFile = data.getData();
                    // Now we need to do the crop
                } else {
                    // image was out temp file. user took an image using camera
                    outputFile = tempFileUri;
                    // Now we need to do the crop
                }
                startCropActivity(outputFile);
            } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

                final Uri resultUri = UCrop.getOutput(data);

                tempOutputFile = new File(resultUri.getPath());

              //  Timber.e(String.valueOf(Uri.fromFile(tempOutputFile)));

                if (checkReadPermission()) {

                    uploadFile(tempOutputFile);

                    //main logic or main code

                    // . write your main code to execute, It will execute if the permission is already given.

                } else {
                    requestPermission();
                }

                // avatarProgressFrame.setVisibility(View.VISIBLE);
                // getBus.post(new Account.ChangeAvatarRequest(Uri.fromFile(tempOutputFile)));
            }
        }
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();

        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        options.withMaxResultSize(960,1028);
        //    options.setCompressionQuality(DEFAULT_COMPRESS_QUALITY);
        options.setFreeStyleCropEnabled(true);
        uCrop.useSourceImageAspectRatio();

        options.setShowCropGrid(false);
        options.setMaxScaleMultiplier(10.0f);
        return uCrop.withOptions(options);
    }

    private void startCropActivity(@NonNull Uri uri) {

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(tempOutputFile));

        uCrop = advancedConfig(uCrop);
        uCrop.start(MessageActivity.this);
    }

    private void initMessagesAdapter(List<Message> response) {


if (getSupportActionBar()!=null){
    getSupportActionBar().setTitle(receiver_user_name);

}
  //   getSupportActionBar().setSubtitle(getResources().getString(R.string.mySubTitle));


        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                if (url != null){
                    if (!url.isEmpty()){

                          Picasso.get().load(url).fit().centerCrop().into(imageView);

                    }
                } else {
                 //   Glide.with(getContext()).load(R.drawable.user).into(imageView);

                       Picasso.get().load(R.drawable.user).centerCrop().fit().into(imageView);

                    //  Picasso.with(MessageActivity.this).load(url).fit().centerInside().into(imageView);
                }
            }
        };

        MessageHolders holdersConfig = new MessageHolders();
        holdersConfig.setIncomingTextLayout(R.layout.item_custom_incoming_text_message);
        holdersConfig.setOutcomingTextLayout(R.layout.item_custom_outcoming_text_message);
        holdersConfig.setIncomingImageLayout(R.layout.item_custom_incoming_image_message);
        holdersConfig.setOutcomingImageLayout(R.layout.item_custom_outcoming_image_message);




        adapter = new MessagesListAdapter<>(MySharedPreferences.getUserId(preferences), holdersConfig, imageLoader);
        adapter.setOnMessageClickListener(this);

        adapter.setLoadMoreListener(this);


        adapter.enableSelectionMode(this);

       // adapter.onLoadMore(1,10);


        // Todo place where we will add initial chatting messages
        if (response==null || response.isEmpty()){
            Timber.d("message is null");
        } else if (response.get(0)==null){
            Timber.e("MessagePojo null");
        } else {
            adapter.addToStart(response.get(response.size() - 1), true);
            //  response = new ArrayList<>();
            response.remove(response.size() - 1);
            if (response.size() == 0){
                Timber.e("Single Message inside chat");
            } else {
                adapter.addToEnd(response, true);
            }

        }


        messagesList.setAdapter(adapter);

    }





    private void uploadFile(File fileUri) {

    //    File file = new File(fileUri.getPath());

        progressFrame.setVisibility(View.VISIBLE);


        mPresenter.postImageUpload(fileUri);

    }



    private void setImageFileUrl(String imageUrl) {
        this.image_Url = imageUrl;
    }

    private void setOnlineString(String onlineString){
        this.onlineString = onlineString;
    }

    private void chatMessage(String url){

        Message message = new Message(MySharedPreferences.getUserId(preferences), new User(MySharedPreferences.getUserId(preferences),
                "harish","", false),
                null, String.valueOf(System.currentTimeMillis()));

        message.setImage(new Message.Image(this.image_Url));
        adapter.addToStart(message, true);

        mPresenter.sendTextNotification(MySharedPreferences.getUserId(preferences),RECEIVER_USERID,""
                , this.image_Url, randomId,
                postId, MySharedPreferences.getFireBaseToken(preferences));

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.chat_actions_menu, menu);
        onSelectionChanged(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                progressFrame.setVisibility(View.VISIBLE);


                // Todo: make delete message arrays in one go
                currentResponse = adapter.getSelectedMessages();
                for (int i = 0; i < this.selectionCount; i++){
                    try {
                        mPresenter.delete_chat(currentResponse.get(i).getId());
                       // deleteChat();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Crashlytics.logException(e);
                    }
                    //    Toast.makeText(this, "selected IDs: " + messageArray.get(i).getId(), Toast.LENGTH_SHORT).show();
                }
                adapter.deleteSelectedMessages();
                adapter.notifyDataSetChanged();
                //
                break;
            case R.id.action_copy:
                adapter.copySelectedMessagesText(this, getMessageStringFormatter(), true);
                break;
            case R.id.go_to_post:
                if (postId != null){
                    Intent intent = new Intent(MessageActivity.this, PostsDetailsActivity.class);
                    intent.putExtra(Constants.POST_BACKGROUND, postId);
                    intent.putExtra(Constants.IDUSERNAME, MySharedPreferences.getUserId(preferences));
                    startActivity(intent);
                }


                break;
        }
        return true;
    }

    private MessagesListAdapter.Formatter<Message> getMessageStringFormatter() {
        return new MessagesListAdapter.Formatter<Message>() {
            @Override
            public String format(Message message) {
          //      String createdAt = new SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault()).format(message.getCreatedAt());

                byte[] data = Base64.decode(message.getText(), Base64.DEFAULT);
                try {
                    messageCopied = new String(data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //Set Last message text

                String text = messageCopied;
                if (message.getText().equals("") || message.getText() == null){
                    text = message.getImageUrl();
                }

                return text;
            }
        };
    }

    @Override
    public void onSelectionChanged(int count) {
        this.selectionCount = count;
        menu.findItem(R.id.action_delete).setVisible(count > 0);
        menu.findItem(R.id.action_copy).setVisible(count > 0);
    }

    @Override
    public void onBackPressed() {
        if (adapter!= null){
            if (selectionCount == 0) {
                super.onBackPressed();
            } {
                adapter.unselectAllItems();
            }

        } else {
            super.onBackPressed();
        }

    }


    @Override
    protected void setUp() {

    }

    @Override
    public void onMessageClick(Message message) {
        if (message.getImageUrl()!=null){
            Intent imageURL = new Intent(this, ImageZoomActivity.class);
            imageURL.putExtra(Constants.IMAGE_URL, message.getImageUrl());
            startActivity(imageURL);
        } else {
            Timber.e("Its Text");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chat_image:

                changeProfileRequest();
                break;
        /*    case R.id.main_activity_emoji:
                emojiPopup.toggle();
                break;*/
            case R.id.emoji_send_message:
                if (editText.getText().toString().trim().isEmpty()){
                    Toast.makeText(MessageActivity.this, "Please enter some message", Toast.LENGTH_SHORT).show();
                } else {

                    sendTextMessage();
                }
                break;

        }
    }

    @Override
    public void updateClient(long data) {

    }


    @Override
    public void insertCustomName(SuccessResponse successResponse) {

    }

    @Override
    public void deleteEntireChat(SuccessResponse successResponse) {

    }

    @Override
    public void blockUserInsert(UserResponse userResponse) {

    }

    @Override
    public void getAllChatMessages(List<Dialog> chatDialogPojos) {

    }

    @Override
    public void sendTextNotification(SuccessResponseChat textResponse) {
        progressFrame.setVisibility(View.GONE);

        if (textResponse.getBlocked()){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MessageActivity.this);
            alertDialogBuilder.setTitle("This User has Blocked You");
            alertDialogBuilder.setMessage("You cannot send private message to this user because you are blocked.");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
// continue with discard
                }
            });
            alertDialogBuilder.show();
        } else {
            progressFrame.setVisibility(View.GONE);

        }
    }


    @Override
    public void delete_chat(SuccessResponse userResponse) {
        progressFrame.setVisibility(View.GONE);


    }

    @Override
    public void getImageUrl(String response) {
        setImageFileUrl(response);
        chatMessage(response);
    }

    @Override
    public void getChatMessages(List<Message> response) {

        this.currentResponse = response;

        initMessagesAdapter(response);

        progressFrame.setVisibility(View.GONE);
        }


    /* @Override
    public void getChatMessages02(List<Message> response) {
        this.currentResponse = response;

        adapter.addToEnd(response, true);


        activity_messages_progress.setVisibility(View.GONE);


    } */

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
     /*   int totalItems = totalItemsCount;

        if (currentResponse.size() >= 39){
            currentPage++;

            activity_messages_progress.setVisibility(View.VISIBLE);

            mPresenter.getChatMessages02(MySharedPreferences.getUserId(preferences), RECEIVER_USERID,
                    postId, MySharedPreferences.getFireBaseToken(preferences), String.valueOf(currentPage));
            //  chatMessages();

        }
        */


    }

}

/*


private void sendImageMessage(String message){

        String sendChat = "senderid@" + MySharedPreferences.getUserId(preferences) + "_contactId@" +
                messageActivityuserId  + "_chat@yes";
        Timber.e(sendChat);

        application.getWebService()
                .getImageResponse(sendChat, message, sender_random_user_id, receiver_random_user_id, postId)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onNext(String response) {
                        Timber.d("Got user details " + response);
                        //        Toast.makeText(MessageActivity.this, "Response from message: " + response, Toast.LENGTH_SHORT).show();
                        //     followers.setText(String.valueOf(response.size()));
                        // Toast.makeText(ChangeProfileActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
                        Timber.d("Message from server" + response);
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        Crashlytics.logException(e);
                        progressFrame.setVisibility(View.GONE);
                    }

                });
    }

    private void checkUserBlock(String url){

        application.getWebService()
                .block_user_check(messageActivityuserId, MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserToken(preferences))
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<SuccessResponse>() {
                    @Override
                    public void onNext(SuccessResponse response) {

                        if (response.getSuccess()){

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MessageActivity.this);
                            alertDialogBuilder.setTitle("This User has Blocked You");
                            alertDialogBuilder.setMessage("You cannot send private message to this user because you are blocked.");
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
// continue with discard
                                }
                            });
                            alertDialogBuilder.show();

                            Toast.makeText(MessageActivity.this, "This user has blocked You", Toast.LENGTH_LONG).show();
                        } else {
                            sendImageOnline(url);
                        }
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        Crashlytics.logException(e);
                        progressFrame.setVisibility(View.GONE);
                    }

                });
    }



    private void checkUserBlockText(){

        application.getWebService()
                .block_user_check(messageActivityuserId, MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserToken(preferences))
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<SuccessResponse>() {
                    @Override
                    public void onNext(SuccessResponse response) {

                        if (response.getSuccess()){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MessageActivity.this);
                            alertDialogBuilder.setTitle("This User has Blocked You");
                            alertDialogBuilder.setMessage("You cannot send private message to this user because you are blocked.");
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
// continue with discard
                                }
                            });
                            alertDialogBuilder.show();
                        } else {
                            sendTextOnline();
                        }
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        Crashlytics.logException(e);
                        progressFrame.setVisibility(View.GONE);
                    }

                });
    }

    private void sendTextOnline(){
        sendMessage(base64String);
    }


    private void chatMessages() throws Exception {
        application.getWebService()
                .getChatMessages(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences),
                        MySharedPreferences.getUserToken(preferences), messageActivityuserId)
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<List<MessagePojo>>() {
                    @Override
                    public void onNext(List<MessagePojo> response) {
                        //          Toast.makeText(MessageActivity.this, response.get(0).getId(), Toast.LENGTH_SHORT).show();
                        //       String text = response.get(0).getText();
                        //    MessagePojo pojo = response.get(0).getMessage();
                   //     this.messages = response;
                        initMessagesAdapter(response);
                        progressFrame.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        Crashlytics.logException(e);
                        progressFrame.setVisibility(View.GONE);
                    }
                });
    }

    private void deleteChat(String messageId) throws Exception {
        application.getWebService()
                .deleteChat(messageId, MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserToken(preferences))
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<UserResponse>() {
                    @Override
                    public void onNext(UserResponse response) {
                        //          Toast.makeText(MessageActivity.this, response.get(0).getId(), Toast.LENGTH_SHORT).show();
                        //       String text = response.get(0).getText();
                        //    MessagePojo pojo = response.get(0).getMessage();
                        //messages = response;
                        //   Toast.makeText(MessageActivity.this, "deleted message", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        progressFrame.setVisibility(View.GONE);
                    }
                });
    }
 */
/*
private void sendMessage(String message){

        String sendChat = "senderid@" + MySharedPreferences.getUserId(preferences) + "_contactId@" +
                messageActivityuserId + "_chat@yes";
        Timber.e(sendChat);


        application.getWebService()
        .getResponse(sendChat, message, sender_random_user_id, receiver_random_user_id, postId)
        .retryWhen(new RetryWithDelay(3,2000))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new BaseSubscriber<String>() {
@Override
public void onNext(String response) {
        Timber.d("Got user details");
        //        Toast.makeText(MessageActivity.this, "Response from message: " + response, Toast.LENGTH_SHORT).show();
        //     followers.setText(String.valueOf(response.size()));
        // Toast.makeText(ChangeProfileActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
        Timber.d("Message from server" + response);
        }
@Override
public void onError(Throwable e){
        e.printStackTrace();
        Crashlytics.logException(e);
        progressFrame.setVisibility(View.GONE);
        }

        });
        }

        @Override
    public void check_block_user_image(SuccessResponse response) {
        progressFrame.setVisibility(View.GONE);

        if (response.getSuccess()){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MessageActivity.this);
            alertDialogBuilder.setTitle("This User has Blocked You");
            alertDialogBuilder.setMessage("You cannot send private message to this user because you are blocked.");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
// continue with discard
                }
            });
            alertDialogBuilder.show();

            Toast.makeText(MessageActivity.this, "This user has blocked You", Toast.LENGTH_LONG).show();
        } else {

          /*  String sendChat = "senderid@" + MySharedPreferences.getUserId(preferences) + "_contactId@" +
                    messageActivityuserId  + "_chat@yes"; */



        /*    mPresenter.sendImageNotification(MySharedPreferences.getUserId(preferences),messageActivityuserId, "",
                    image_Url, randomId, postId, MySharedPreferences.getFireBaseToken(preferences));

                    //  mPresenter.sendImageNotification(sendChat, image_Url.getUrl(), sender_random_user_id, receiver_random_user_id, postId);

                    }
                    }

                    */
