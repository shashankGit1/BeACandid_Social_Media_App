package in.becandid.app.becandid.ui.postDetails;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.view.inputmethod.InputConnectionCompat;
import androidx.core.view.inputmethod.InputContentInfoCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.PermissionChecker;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.yalantis.ucrop.UCrop;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.becandid.app.becandid.BuildConfig;
import in.becandid.app.becandid.di.rxbus.Events;
import in.becandid.app.becandid.di.rxbus.RxBus;
import in.becandid.app.becandid.dto.PostLikesResponse;
import in.becandid.app.becandid.dto.PostUserCommentModel;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.infrastructure.Account;
import in.becandid.app.becandid.pixel_shot.PixelShot;
import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.ui.group.UserGroupDetails;
import in.becandid.app.becandid.ui.profile.SimpleDividerItemDecoration;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.chat.MessageActivity;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.userpost.EditPost;
import in.becandid.app.becandid.ui.userpost.PostActivity;
import in.becandid.app.becandid.ui.userpost.ReportAbuseActivity;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static in.becandid.app.becandid.ui.base.Constants.explicitWords;

public class PostsDetailsActivity extends BaseActivity implements View.OnClickListener, PostsDetailsMvpView, PixelShot.PixelShotListener {

    private static final String INPUT_CONTENT_INFO_KEY = "COMMIT_CONTENT_INPUT_CONTENT_INFO";
    private static final String COMMIT_CONTENT_FLAGS_KEY = "COMMIT_CONTENT_FLAGS";

    @BindView(R.id.detail_rv_messages) protected RecyclerView mMessageRecyclerView;
    @BindView(R.id.detail_list_item_posts_avatar) protected SimpleDraweeView user_avatar;
    @BindView(R.id.detail_list_item_post_userNickName) protected TextView user_name;
    @BindView(R.id.detail_list_item_posts_timeStamp) protected TextView timeStamp;
    @BindView(R.id.detail_list_item_posts_category) protected TextView category;
    @BindView(R.id.detail_list_item_posts_message) protected TextView postMessage;
    @BindView(R.id.drop_down_menu_detail) protected ImageView drop_down_menu_detail;
    @BindView(R.id.emoji_above_comment_detail) protected ImageView commentCounterImage;
    @BindView(R.id.like_back_layout) protected ConstraintLayout like_back_layout;
    @BindView(R.id.candid_back) protected ConstraintLayout candid_back;
    @BindView(R.id.detail_list_item_like_button) protected ImageView likeButtonMain;
    @BindView(R.id.detail_list_item_hug_button) protected ImageView HugButtonMain;
    @BindView(R.id.list_item_posts_message_image) protected SimpleDraweeView list_item_posts_message_image;
    @BindView(R.id.status_below_space) protected View status_below_space;
    @BindView(R.id.new_counter_like_number_detail) protected TextView new_counter_like_number;
    @BindView(R.id.new_counter_hug_number_detail) protected TextView new_counter_hug_number;
    @BindView(R.id.distance_country_detail) protected TextView distance_country_detail;
    @BindView(R.id.distance_locality_postdetail) protected TextView distance_locality_postdetail;

    protected ImageView mSendMessageImageButton;
    private InputContentInfoCompat mCurrentInputContentInfo;
    private int mCurrentFlags;
    protected String gifImage;
    protected String imageUrl;
    protected boolean gifGiven = false;
    private String adultWord;

    protected boolean imageGiven = false;

    LinearLayout commentLayout = null;
    ImageView post_detail_image_attach = null;


    private static final int REQUEST_SELECT_IMAGE = 100;

    @BindView(R.id.new_counter_cmt_number_detail) protected TextView new_counter_cmt_number;
    @BindView(R.id.detail_list_item_post_background) protected ConstraintLayout detail_list_item_post_background;
    protected RxBus bus;
    protected CompositeDisposable disposables;
    protected LabeledSwitch switchbutton_postDetail;
    List<PostUserCommentModel> myCommentList = null;

    @Inject
    PostsDetailsMvpPresenter<PostsDetailsMvpView> mPresenter;

    private MessageAdapter mMessageAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private String postId;
    private ImageView post_detail_image;
    private ImageView detail_btn_send_message;
    private PostsModel myList = null;

    private File tempOutputFile;


    //   protected Geocoder geocoder;
    private boolean doDislike;
    private CardView parent_row_text;
    private LinearLayout detail_ll_menu_tab;
    private int commentCount;
    private Bitmap bmp;
    private int likeCounter;
    private int hugCounter;
    private PopupMenu popupMenu;
    private RelativeLayout post_back_layout_layout;
    private View progressFrame;
    private String idusername;
    protected EditText mMessageEditText;

    private boolean shareStatus = false;
    //animated buttons
    private boolean like_button_true;
    private boolean hug_button_true;
    private MessageAdapter.InsertMessageListener mInsertMessageListener = new MessageAdapter.InsertMessageListener() {

        @Override
        public void onMessageInserted(int position) {
            mLinearLayoutManager.scrollToPosition(position);
        }

        };

    public static Intent createIntent(@NonNull Context context, @NonNull RemoteMessage message) {


        JSONObject json = null;
        try {
            json = new JSONObject(message.getData().get("text"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String postId = json.optString("postId");
        final String receiverId = json.optString("receiverId");

        Intent i = new Intent(context, PostsDetailsActivity.class);

        i.putExtra(Constants.postId, postId);

        return i;
    }

    public static PendingIntent createPendingIntent(@NonNull Context context, @NonNull RemoteMessage message) {
        String messageId = message.getMessageId();
        return PendingIntent.getActivity(context, messageId != null ? messageId.hashCode() : (int) System.currentTimeMillis(), PostsDetailsActivity.createIntent(context, message), 0);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_details);
        getSupportActionBar().setTitle("Comments");

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(PostsDetailsActivity.this);

        disposables = new CompositeDisposable();

        bus = new RxBus();

        progressFrame = findViewById(R.id.post_details_progressBar);
        post_detail_image = (ImageView) findViewById(R.id.post_detail_image);
        switchbutton_postDetail = findViewById(R.id.switchbutton_postDetail);
        post_back_layout_layout = (RelativeLayout) findViewById(R.id.post_back_layout_layout);
        parent_row_text = (CardView) findViewById(R.id.parent_row_text);

        detail_ll_menu_tab = (LinearLayout) findViewById(R.id.detail_ll_menu_tab);

        tempOutputFile = new File(getExternalCacheDir(), "temp-profile_image.jpg");

       // @BindView(R.id.detail_et_message) protected EditText mMessageEditText;

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                300,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2.0f
        );


        LinearLayout.LayoutParams param02 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );

        mMessageEditText = createEditTextWithContentMimeTypesMain(new String[]{"image/png", "image/gif", "image/jpeg", "image/webp"});


        detail_ll_menu_tab.addView(mMessageEditText);

        mSendMessageImageButton = new ImageView(PostsDetailsActivity.this);



        // Set an image for ImageView
        mSendMessageImageButton.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_send_now));

        detail_ll_menu_tab.addView(mSendMessageImageButton);

        mMessageEditText.setLayoutParams(param);

        mSendMessageImageButton.setLayoutParams(param02);


        candid_back.setVisibility(View.GONE);
        Intent intent = getIntent();
        postId = intent.getStringExtra(Constants.postId); // seconnd person username random
        idusername = intent.getStringExtra(Constants.receiverId); // seconnd person username random
       // receiver_random_user_id = intent.getStringExtra(Constants.receiverId); // actual user ID of other person

        if (postId == null){
            if (getIntent().getExtras() != null) {
                for (String key : getIntent().getExtras().keySet()) {
                    String text = getIntent().getExtras().getString("text");

                    JSONObject arr = null;
                    try {
                        arr = new JSONObject(text);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    postId = arr.optString("postId");
                    idusername = arr.optString("idusername");

                    break;

                }
            }
        }

        post_detail_image.setOnClickListener(this);


        toolbar.setNavigationIcon(R.drawable.ic_clear_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchbutton_postDetail.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                // Implement your switching logic here
                if (labeledSwitch.isOn()){

                    list_item_posts_message_image.setImageDrawable(getResources().getDrawable(R.drawable.picture));
                    postMessage.setText("This post is flagged as Abusive");
                    labeledSwitch.setOn(true);
                } else {
                    postMessage.setText(myList.getTextStatus());
                    list_item_posts_message_image.setImageURI(Uri.parse(myList.getImage_url()));

                    labeledSwitch.setOn(false);

                }
            }
        });





        mSendMessageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressFrame.setVisibility(View.VISIBLE);

                if (gifGiven) {
                    mPresenter.post_commentsOnline(MySharedPreferences.getUserId(preferences), idusername, postId, mMessageEditText.getText().toString(), MySharedPreferences.getFireBaseToken(preferences), gifImage, "2");

                } else if (imageGiven) {
                    mPresenter.post_commentsOnline(MySharedPreferences.getUserId(preferences), idusername, postId, mMessageEditText.getText().toString(), MySharedPreferences.getFireBaseToken(preferences), imageUrl, "1");

                } else {
                    if (!mMessageEditText.getText().toString().isEmpty()) {
                        try {
                            if (isAdultContent(mMessageEditText.getText().toString().toLowerCase())) {
                                new AlertDialog.Builder(PostsDetailsActivity.this)
                                        .setTitle("The post contains adult content!")
                                        .setMessage("As per Google Guidelines, No adult content can posted publicly. Adult word found in your status : " + adultWord)
                                        .setCancelable(true)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();
                            } else {
                                mPresenter.post_commentsOnline(MySharedPreferences.getUserId(preferences), idusername, postId, mMessageEditText.getText().toString(), MySharedPreferences.getFireBaseToken(preferences), "", "0");

                            }
                            //   postStatus(locationValue);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        Toast.makeText(PostsDetailsActivity.this, "Empty comment", Toast.LENGTH_SHORT).show();
                        progressFrame.setVisibility(View.INVISIBLE);
                    }


                }


                    if (!MySharedPreferences.getUserId(preferences).equalsIgnoreCase(idusername)){
                        // subscribe only users outside post
                        subscribeToTopic(String.valueOf("POST_" + postId));
                    }

                    //   mPresenter.check_block_userOnline(idusername, MySharedPreferences.getUserId(preferences), MySharedPreferences.getFireBaseToken(preferences));

                }

        });
        //OnClickListeners
        likeButtonMain.setOnClickListener(this);
        HugButtonMain.setOnClickListener(this);

        //   hug_counter.setOnClickListener(this);
        new_counter_like_number.setOnClickListener(this);
        drop_down_menu_detail.setOnClickListener(this);
        new_counter_hug_number.setOnClickListener(this);
        new_counter_cmt_number.setOnClickListener(this);
        //   post_listen.setOnClickListener(this);
        category.setOnClickListener(this);

        commentCounterImage.setOnClickListener(this);
        //   listenCounterImage.setOnClickListener(this);
        user_name.setOnClickListener(this);
        user_avatar.setOnClickListener(this);

        if (savedInstanceState != null) {
            final InputContentInfoCompat previousInputContentInfo = InputContentInfoCompat.wrap(
                    savedInstanceState.getParcelable(INPUT_CONTENT_INFO_KEY));
            final int previousFlags = savedInstanceState.getInt(COMMIT_CONTENT_FLAGS_KEY);
            if (previousInputContentInfo != null) {
                onCommitContentInternal(previousInputContentInfo, previousFlags);
            }
        }


        initRecyclerView();
        try {
            progressFrame.setVisibility(View.VISIBLE);
            mPresenter.getSinglePostOnline(postId, MySharedPreferences.getUserId(preferences));
            mPresenter.getCommentsReplyOnline(postId, MySharedPreferences.getUserId(preferences), MySharedPreferences.getFireBaseToken(preferences));
            } catch (Exception e) {
            e.printStackTrace();
        }


        disposables.add(bus.asFlowable()
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) {

                        if (object instanceof Events.deleteComment) { // done

                            mPresenter.deleteComment(((Events.deleteComment) object).id_post_comments, MySharedPreferences.getUserId(preferences), ((Events.deleteComment) object).id_posts);
                            // done delete
                            //  textView.setText("Tap Event Received");
                        } else if (object instanceof Events.sendCommentLike) {
                            //done
                            mPresenter.postCommentLikeOnline(((Events.sendCommentLike) object).id_post_comment, MySharedPreferences.getUserId(preferences), postId, ((Events.sendCommentLike) object).like,MySharedPreferences.getFireBaseToken(preferences));

                            //  textView.setText("Tap Event Received");
                        } else if (object instanceof Account.sendDeleteCommentReply) {

                            mPresenter.deleteCommentReply(((Account.sendDeleteCommentReply) object).id_post_comment_reply, MySharedPreferences.getUserId(preferences), ((Account.sendDeleteCommentReply) object).id_posts);
                            //  textView.setText("Auto Event Received");
                        } else if (object instanceof Account.sendCommentReplyLike) {
                            mPresenter.postCommentReplyLike(((Account.sendCommentReplyLike) object).id_post_comment_reply, MySharedPreferences.getUserId(preferences), "1", postId,  MySharedPreferences.getFireBaseToken(preferences));

                            //  textView.setText("Tap Event Received");
                        } else if (object instanceof Account.sendCommentReply) {


                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PostsDetailsActivity.this);
                            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View dialogView = inflater.inflate(R.layout.reply_comment_dialog, null);
                            commentLayout = (LinearLayout) dialogView.findViewById(R.id.reply_edit_text);
                            post_detail_image_attach = (ImageView) dialogView.findViewById(R.id.post_detail_image_attach);
                            post_detail_image_attach.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    askPermissionsComment();
                                }
                            });
                          //  dialogBuilder.setView(dialogView);

                         //   String[] content = new String[]{"image/png", "image/gif", "image/jpeg", "image/webp"};
                         //   final String[] mimeTypes = Arrays.copyOf(content, content.length);

                            EditText edt = createEditTextWithContentMimeTypes(new String[]{"image/png", "image/gif", "image/jpeg", "image/webp"});
                            commentLayout.addView(edt);
                            dialogBuilder.setView(commentLayout);

                          //  final EditText edt = (EditText) dialogView.findViewById(R.id.edit_reply_comment);

                            dialogBuilder.setTitle("Reply to " + ((Account.sendCommentReply) object).user_name);
                            dialogBuilder.setMessage("Enter text below");
                            dialogBuilder.setPositiveButton("Post Comment", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //do something with edt.getText().toString();
                                   // messageReplyProgress.setVisibility(View.VISIBLE);
                                    try {

                                        progressFrame.setVisibility(View.VISIBLE);

                                        //     String text = edt.getText().toString();

                                        if (gifGiven){
                                            mPresenter.postCommentReply(((Account.sendCommentReply) object).id_post_comments, MySharedPreferences.getUserId(preferences), ((Account.sendCommentReply) object).id_post_user_name, postId, String.valueOf("@" + ((Account.sendCommentReply) object).user_name + " " + edt.getText().toString()), MySharedPreferences.getFireBaseToken(preferences), gifImage, "2");

                                        } else if (imageGiven){
                                            mPresenter.postCommentReply(((Account.sendCommentReply) object).id_post_comments, MySharedPreferences.getUserId(preferences), ((Account.sendCommentReply) object).id_post_user_name, postId, String.valueOf("@" + ((Account.sendCommentReply) object).user_name + " " + edt.getText().toString()), MySharedPreferences.getFireBaseToken(preferences), imageUrl, "1");

                                        } else {

                                            try {
                                                if (isAdultContent(edt.getText().toString())) {
                                                    new AlertDialog.Builder(PostsDetailsActivity.this)
                                                            .setTitle("The post contains adult content!")
                                                            .setMessage("As per Google Guidelines, No adult content can posted publicly. Adult word found in your status : " + adultWord)
                                                            .setCancelable(true)
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                }
                                                            })
                                                            .show();
                                                } else {
                                                    mPresenter.postCommentReply(((Account.sendCommentReply) object).id_post_comments, MySharedPreferences.getUserId(preferences), ((Account.sendCommentReply) object).id_post_user_name, postId, String.valueOf("@" + ((Account.sendCommentReply) object).user_name + " " + edt.getText().toString()), MySharedPreferences.getFireBaseToken(preferences), "", "0");

                                                }
                                                //   postStatus(locationValue);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }



                                        }

                                        subscribeToTopic(String.valueOf("POST_" + postId));
                                        //bus.send(new Account.sendCommentReply(messageItem.getCommentId(), messageItem.getId_post_user_name(), String.valueOf("@"+messageItem.getUserName() + " " + edt.getText().toString())));

                                        //  postReplyCommentFinal(view, messageItem.getCommentId(), edt.getText().toString(), messageItem.getId_post_user_name(), messageItem.getCommentUserId());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //pass
                                }
                            });
                            AlertDialog b = dialogBuilder.create();
                            b.show();


                      /*      if (!MySharedPreferences.getUserId(preferences).equalsIgnoreCase(idusername)){
                                // subscribe only users outside post
                                subscribeToTopic(String.valueOf("POST_" + postId));
                            } */



                        } else if (object instanceof Account.sendCommentReplyReply) {

                            AlertDialog.Builder dialogBuilder02 = new AlertDialog.Builder(PostsDetailsActivity.this);
                            LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );

                            final View dialogView = inflater.inflate(R.layout.reply_comment_dialog, null);
                            commentLayout = (LinearLayout) dialogView.findViewById(R.id.reply_edit_text);
                            post_detail_image_attach = (ImageView) dialogView.findViewById(R.id.post_detail_image_attach);


                            post_detail_image_attach.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    askPermissionsComment();
                                }
                            });


                          //  String[] content = new String[]{"image/png", "image/gif", "image/jpeg", "image/webp"};
                           // final String[] mimeTypes = Arrays.copyOf(content, content.length);

                            EditText edt = createEditTextWithContentMimeTypes(new String[]{"image/png", "image/gif", "image/jpeg", "image/webp"});
                            commentLayout.addView(edt);
                            dialogBuilder02.setView(commentLayout);


                            dialogBuilder02.setTitle("Reply to " + ((Account.sendCommentReplyReply) object).user_name);
                            dialogBuilder02.setMessage("Enter text below");
                            dialogBuilder02.setPositiveButton("Post Comment", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                   // messageReplyProgress.setVisibility(View.VISIBLE);

                                    progressFrame.setVisibility(View.VISIBLE);

                                    // String text = edt.getText().toString();

                                    try {

                                        //  commentReplyInterface.sendCommentReplyReply(messageItem.getCommentId(), messageItem.getIdPostUserName(), messageItem.getId_posts(), edt.getText().toString(),"", "");

                                        if (gifGiven){
                                            mPresenter.postCommentReplyReply(((Account.sendCommentReplyReply) object).id_post_comments, ((Account.sendCommentReplyReply) object).id_post_comments_reply, MySharedPreferences.getUserId(preferences), ((Account.sendCommentReplyReply) object).id_post_user_name, postId, String.valueOf("@" + ((Account.sendCommentReplyReply) object).user_name + " " + edt.getText().toString()), MySharedPreferences.getFireBaseToken(preferences), gifImage, "2");

                                        } else if (imageGiven){
                                            mPresenter.postCommentReplyReply(((Account.sendCommentReplyReply) object).id_post_comments, ((Account.sendCommentReplyReply) object).id_post_comments_reply, MySharedPreferences.getUserId(preferences), ((Account.sendCommentReplyReply) object).id_post_user_name, postId, String.valueOf("@" + ((Account.sendCommentReplyReply) object).user_name + " " + edt.getText().toString()), MySharedPreferences.getFireBaseToken(preferences), imageUrl, "1");

                                        } else {

                                            try {
                                                if (isAdultContent(edt.getText().toString().toLowerCase())) {
                                                    new AlertDialog.Builder(PostsDetailsActivity.this)
                                                            .setTitle("The post contains adult content!")
                                                            .setMessage("As per Google Guidelines, No adult content can posted publicly. Adult word found in your status : " + adultWord)
                                                            .setCancelable(true)
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                }
                                                            })
                                                            .show();
                                                } else {
                                                    mPresenter.postCommentReplyReply(((Account.sendCommentReplyReply) object).id_post_comments, ((Account.sendCommentReplyReply) object).id_post_comments_reply, MySharedPreferences.getUserId(preferences), ((Account.sendCommentReplyReply) object).id_post_user_name, postId, String.valueOf("@" + ((Account.sendCommentReplyReply) object).user_name + " " + edt.getText().toString()), MySharedPreferences.getFireBaseToken(preferences), "", "0");

                                                }
                                                //   postStatus(locationValue);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                        }




                                        subscribeToTopic(String.valueOf("POST_" + postId));

                                        //  bus.send(new Account.sendCommentReplyReply(messageItem.getCommentId(), messageItem.getId_post_comment_reply(), messageItem.getIdPostUserName(), String.valueOf("@"+messageItem.getUserName() + " " + edt.getText().toString())));


                                        // postComment(edt.getText().toString(),messageItem);
                                        //   postReplyCommentFinal(view,edt.getText().toString(),  )
                                        // postReplyCommentFinal(view, edt.getText().toString(), messageItem.getCommentId(), messageItem.getIdPostUserName(), messageItem.getCommentUserId());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //do something with edt.getText().toString();
                                }
                            });
                            dialogBuilder02.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //pass
                                }
                            });
                            AlertDialog b = dialogBuilder02.create();
                            b.show();


                         /*   if (!MySharedPreferences.getUserId(preferences).equalsIgnoreCase(idusername)){
                                // subscribe only users outside post
                                subscribeToTopic(String.valueOf("POST_" + postId));
                            } */



                        }

                    }
                }));



    }

    public RxBus getBus() {
        if (bus == null) {
            bus = new RxBus();
        }

        return bus;
    }

    private void initRecyclerView() {
        /**code edited by nirmal
         * Replacing the linearlayout manager
         */

    //    geocoder = new Geocoder(PostsDetailsActivity.this, Locale.getDefault());


        //    mLinearLayoutManager = new LinearLayoutManager(PostsDetailsActivity.this,LinearLayoutManager.VERTICAL,true);

        mMessageAdapter = new MessageAdapter(PostsDetailsActivity.this, myCommentList, mInsertMessageListener, postId, getBus());

        mLinearLayoutManager = new LinearLayoutManager(PostsDetailsActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mLinearLayoutManager.setStackFromEnd(true);
//        mLinearLayoutManager.setReverseLayout(true);

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mMessageRecyclerView.setAdapter(mMessageAdapter);

    }


    @Override
    public void onClick(View view) {
        //     processLoggedState(view);

        switch (view.getId()) {
                case R.id.post_detail_image:

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PostsDetailsActivity.this);
                        alertDialogBuilder.setTitle("Google Keyboard built in GIF is fully supported");
                        alertDialogBuilder.setMessage("You can post GIFs using google keyboard built in GIF button inside the comments..");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alertDialogBuilder.show();


                   // askPermissionsComment();

                    break;
            case R.id.drop_down_menu_detail:
                    popupMenu = new PopupMenu(view.getContext(), view);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.pop_menu_detail, popupMenu.getMenu());
                    //    this.menu = popupMenu.getMenu();


                    if (MySharedPreferences.getUserId(preferences).equals(myList.getIdUserName())) {
                        if (popupMenu.getMenu() == null)
                            return;
                        popupMenu.getMenu().setGroupVisible(R.id.main_menu_group_details, true);
                    } else {
                        popupMenu.getMenu().setGroupVisible(R.id.main_menu_group_details, false);
                    }


              //  popupMenu.getMenu().setGroupVisible(R.id.main_menu_group_details, true);


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit_post:

                                    Intent editIntent = new Intent(PostsDetailsActivity.this, EditPost.class);
                                    editIntent.putExtra(Constants.IDPOST, myList.getIdPosts());
                                    editIntent.putExtra(Constants.IDUSERNAME, myList.getIdUserName());
                                    editIntent.putExtra(Constants.ADMIN_ID, myList.getAdmin_id());
                                    editIntent.putExtra(Constants.STATUS_POST, myList.getTextStatus());
                                    startActivity(editIntent);
                                    return true;

                                case R.id.report_post:

                                    Intent reportIntent = new Intent(PostsDetailsActivity.this, ReportAbuseActivity.class);
                                    reportIntent.putExtra(Constants.IDPOST, postId);
                                    reportIntent.putExtra(Constants.IDUSERNAME, myList.getIdUserName());
                                    reportIntent.putExtra(Constants.STATUS_POST, myList.getTextStatus());
                                    startActivity(reportIntent);
                                    return true;

                         /*       case R.id.make_popular:
                                    sendPopularPost(myList.getIdPosts(), myList.getTextStatus());
                                    return true;

                                case R.id.remove_make_popular:
                                    removePopularPost(myList.getIdPosts());
                                    return true; */

                                case R.id.send_message_menu:

                                    final Random rand = new Random();
                                    int randomUserId = rand.nextInt(54) + 1;
                                    String randomUserIID = String.valueOf(randomUserId);
                                    Intent intent = new Intent(PostsDetailsActivity.this, MessageActivity.class);

                                    // intent.putExtra(Constants.RANDOM_SENDER_USERNAME, mMessageList.get(mPosition).getUserName()); // username inside message
                                    intent.putExtra(Constants.RANDOM_SENDER_ID, randomUserIID); // username inside message
                                    intent.putExtra(Constants.POSTID, postId); // username inside message
                                    intent.putExtra(Constants.SENDER_USERID, myList.getIdUserName() ); // username inside message
                                    intent.putExtra(Constants.RANDOM_RECEIVER_USERNAME, myList.getUser_name_random()); // direct username in chat
                                    intent.putExtra(Constants.RANDOM_RECEIVER_ID, myList.getId_user_name_random()); // direct username id in chat
                                    intent.putExtra(Constants.RECEIVER_USERID, MySharedPreferences.getUserId(preferences)); // direct random user id in chat

                                    intent.putExtra(Constants.YES, myList.getIdUserName());
                                    intent.putExtra(Constants.USERNAME, myList.getUser_name_random());
                                    startActivity(intent);

                                    return true;

                                case R.id.menu_item_share:

                                        shareImage();

                                    return true;

                                default:
                                    return false;

                            }
                        }
                    });
                    popupMenu.show();


                break;
            case R.id.detail_list_item_like_button:

                    if (like_button_true) {
                        like_button_true = false;
                        // sendUnlikeToServer((VoicemeApplication) itemView.getContext().getApplicationContext());
                        unLikeMethod();
                    } else {
                        like_button_true = true;
                      /*  if (hug_button_true) {
                            hug_button_true = false;
                            unHugMethod();
                        } */
                        likeCounter++;



                    /*    String sendLike = "senderid@" + MySharedPreferences.getUserId(preferences)  + "_contactId@" +
                                myList.getIdUserName()  + "_postId@" + myList.getIdPosts()  + "_click@" + "1"; */


                        likeButtonMain.setImageDrawable(getResources().getDrawable(R.drawable.heart_shape_outlinered));
                      //  sendLikeToServer(application, 1, 0, 0, 0, "clicked like button");

                        mPresenter.sendLikeToServerOnline(MySharedPreferences.getUserId(preferences), postId, "1",  MySharedPreferences.getFireBaseToken(preferences));

                  //      sendLikeToServer();
                        new_counter_like_number.setText(NumberFormat.getIntegerInstance().format(likeCounter));
                    }

                break;
            case R.id.detail_list_item_hug_button:

                    if (hug_button_true) {
                        hug_button_true = false;
                        // sendUnlikeToServer((VoicemeApplication) itemView.getContext().getApplicationContext());
                        unHugMethod();
                    } else {
                        hug_button_true = true;
                      /*  if (like_button_true) {
                            like_button_true = false;
                            unLikeMethod();
                        } */
                        hugCounter++;

                        HugButtonMain.setImageDrawable(getResources().getDrawable(R.drawable.broken_candidred));

                        mPresenter.sendSadToServerOnline(MySharedPreferences.getUserId(preferences), postId, "1",  MySharedPreferences.getFireBaseToken(preferences));

                       // sendSadToServer();
                      //  sendHugToServer(application, 0, 1, 0, 0, "clicked hug button");
                        new_counter_hug_number.setText(NumberFormat.getIntegerInstance().format(hugCounter));
                    }

                break;
            case R.id.detail_list_item_post_userNickName:
                    popupMenu = new PopupMenu(view.getContext(), view);
                    MenuInflater inflater02 = popupMenu.getMenuInflater();
                    inflater02.inflate(R.menu.pop_menu_detail, popupMenu.getMenu());
                    //    this.menu = popupMenu.getMenu();


               if (MySharedPreferences.getUserId(preferences).equals(myList.getIdUserName())) {
                    if (popupMenu.getMenu() == null)
                        return;
                    popupMenu.getMenu().setGroupVisible(R.id.main_menu_group_details, true);
                } else {
                    popupMenu.getMenu().setGroupVisible(R.id.main_menu_group_details, false);
                }


            //    popupMenu.getMenu().setGroupVisible(R.id.main_menu_group_details, true);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit_post:

                                    Intent editIntent = new Intent(PostsDetailsActivity.this, EditPost.class);
                                    editIntent.putExtra(Constants.IDPOST, myList.getIdPosts());
                                    editIntent.putExtra(Constants.IDUSERNAME, myList.getIdUserName());
                                    editIntent.putExtra(Constants.ADMIN_ID, myList.getAdmin_id());
                                    editIntent.putExtra(Constants.STATUS_POST, myList.getTextStatus());
                                    startActivity(editIntent);
                                    return true;


                                case R.id.report_post:

                                    Intent reportIntent = new Intent(PostsDetailsActivity.this, ReportAbuseActivity.class);
                                    reportIntent.putExtra(Constants.IDPOST, postId);
                                    reportIntent.putExtra(Constants.IDUSERNAME, myList.getIdUserName());
                                    reportIntent.putExtra(Constants.STATUS_POST, myList.getTextStatus());
                                    startActivity(reportIntent);
                                    return true;

                         /*       case R.id.make_popular:
                                    sendPopularPost(myList.getIdPosts(), myList.getTextStatus());
                                    return true;

                                case R.id.remove_make_popular:
                                    removePopularPost(myList.getIdPosts());
                                    return true; */

                                case R.id.send_message_menu:

                                    final Random rand = new Random();
                                    int randomUserId = rand.nextInt(54) + 1;
                                    String randomUserIID = String.valueOf(randomUserId);



                                    Intent intent = new Intent(PostsDetailsActivity.this, MessageActivity.class);
                                    intent.putExtra(Constants.RANDOM_SENDER_ID, randomUserIID); // username inside message
                                    intent.putExtra(Constants.POSTID, postId); // username inside message
                                    intent.putExtra(Constants.SENDER_USERID, myList.getIdUserName() ); // username inside message
                                    intent.putExtra(Constants.RANDOM_RECEIVER_USERNAME, myList.getUser_name_random()); // direct username in chat
                                    intent.putExtra(Constants.RANDOM_RECEIVER_ID, myList.getId_user_name_random()); // direct username id in chat
                                    intent.putExtra(Constants.RECEIVER_USERID, MySharedPreferences.getUserId(preferences));
                                            intent.putExtra(Constants.YES, myList.getIdUserName());
                                    intent.putExtra(Constants.USERNAME, myList.getUser_name_random());
                                    startActivity(intent);

                                    return true;

                                case R.id.menu_item_share:

                                        shareImage();
                                    return true;

                                default:
                                    return false;

                            }
                        }
                    });
                    popupMenu.show();


                break;
        /*    case R.id.detail_list_item_post_userNickName:
                if (myList.getIdUserName() != null){
                    if (myList.getIdUserName().equals(MySharedPreferences.getUserId(preferences))) {
                        startActivity(new Intent(view.getContext(), ProfileActivity.class));
                    } else {
                        Intent intent = new Intent(this, SecondProfile.class);
                        intent.putExtra(Constants.SECOND_PROFILE_ID, myList.getIdUserName());
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(PostsDetailsActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                }

                break; */
            case R.id.detail_list_item_posts_avatar:

                    popupMenu = new PopupMenu(view.getContext(), view);
                    MenuInflater inflater03 = popupMenu.getMenuInflater();
                    inflater03.inflate(R.menu.pop_menu_detail, popupMenu.getMenu());
                    //    this.menu = popupMenu.getMenu();


                    if (MySharedPreferences.getUserId(preferences).equals(myList.getIdUserName())) {
                        if (popupMenu.getMenu() == null)
                            return;
                        popupMenu.getMenu().setGroupVisible(R.id.main_menu_group_details, true);
                    } else {
                        popupMenu.getMenu().setGroupVisible(R.id.main_menu_group_details, false);
                    }


             //   popupMenu.getMenu().setGroupVisible(R.id.main_menu_group_details, true);


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit_post:

                                    Intent editIntent = new Intent(PostsDetailsActivity.this, EditPost.class);
                                    editIntent.putExtra(Constants.IDPOST, myList.getIdPosts());
                                    editIntent.putExtra(Constants.IDUSERNAME, myList.getIdUserName());
                                    editIntent.putExtra(Constants.ADMIN_ID, myList.getAdmin_id());
                                    editIntent.putExtra(Constants.STATUS_POST, myList.getTextStatus());
                                    startActivity(editIntent);
                                    return true;

                                case R.id.report_post:

                                    Intent reportIntent = new Intent(PostsDetailsActivity.this, ReportAbuseActivity.class);
                                    reportIntent.putExtra(Constants.IDPOST, postId);
                                    reportIntent.putExtra(Constants.IDUSERNAME, myList.getIdUserName());
                                    reportIntent.putExtra(Constants.STATUS_POST, myList.getTextStatus());
                                    startActivity(reportIntent);
                                    return true;

                         /*       case R.id.make_popular:
                                    sendPopularPost(myList.getIdPosts(), myList.getTextStatus());
                                    return true;

                                case R.id.remove_make_popular:
                                    removePopularPost(myList.getIdPosts());
                                    return true; */

                                case R.id.send_message_menu:

                                    final Random rand = new Random();
                                    int randomUserId = rand.nextInt(54) + 1;
                                    String randomUserIID = String.valueOf(randomUserId);
                                    Intent intent = new Intent(PostsDetailsActivity.this, MessageActivity.class);

                                    // intent.putExtra(Constants.RANDOM_SENDER_USERNAME, mMessageList.get(mPosition).getUserName()); // username inside message
                                    intent.putExtra(Constants.RANDOM_SENDER_ID, randomUserIID); // username inside message
                                    intent.putExtra(Constants.POSTID, myList.getIdPosts()); // username inside message
                                    intent.putExtra(Constants.SENDER_USERID, myList.getIdUserName() ); // username inside message
                                    intent.putExtra(Constants.RANDOM_RECEIVER_USERNAME, myList.getUser_name_random()); // direct username in chat
                                    intent.putExtra(Constants.RANDOM_RECEIVER_ID, myList.getId_user_name_random()); // direct username id in chat
                                    intent.putExtra(Constants.RECEIVER_USERID, MySharedPreferences.getUserId(preferences)); // direct random user id in chat

                                    intent.putExtra(Constants.YES, myList.getIdUserName());
                                    intent.putExtra(Constants.USERNAME, myList.getUser_name_random());
                                    startActivity(intent);

                                    return true;

                                case R.id.menu_item_share:

                                        shareImage();

                                    return true;

                                default:
                                    return false;

                            }
                        }
                    });
                    popupMenu.show();


                break;
            case R.id.detail_list_item_posts_category:
                Intent in = new Intent(this, UserGroupDetails.class);
                in.putExtra(Constants.GROUPID, myList.getGroup_id());
                startActivity(in);
                break;


        }

    }

    private EditText createEditTextWithContentMimeTypes(String[] contentMimeTypes) {
        final CharSequence hintText = "Enter Message                   ";
        final String[] mimeTypes = Arrays.copyOf(contentMimeTypes, contentMimeTypes.length);  // our own copy of contentMimeTypes.


        EditText exitText = new AppCompatEditText(this) {
            @Override
            public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
                final InputConnection ic = super.onCreateInputConnection(editorInfo);
                EditorInfoCompat.setContentMimeTypes(editorInfo, mimeTypes);
                final InputConnectionCompat.OnCommitContentListener callback =
                        new InputConnectionCompat.OnCommitContentListener() {
                            @Override
                            public boolean onCommitContent(InputContentInfoCompat inputContentInfo,
                                                           int flags, Bundle opts) {
                                return PostsDetailsActivity.this.onCommitContent(
                                        inputContentInfo, flags, opts, mimeTypes);
                            }
                        };
                return InputConnectionCompat.createWrapper(ic, editorInfo, callback);
            }
        };
        exitText.setHint(hintText);
        exitText.setTextColor(Color.BLACK);
        exitText.setHintTextColor(Color.BLACK);
        exitText.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams
                .MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        return exitText;
    }

    private boolean onCommitContent(InputContentInfoCompat inputContentInfo, int flags,
                                    Bundle opts, String[] contentMimeTypes) {
        // Clear the temporary permission (if any).  See below about why we do this here.
        try {
            if (mCurrentInputContentInfo != null) {
                mCurrentInputContentInfo.releasePermission();
            }
        } catch (Exception e) {
            Timber.e("InputContentInfoCompat#releasePermission() failed." + e.getLocalizedMessage());
        } finally {
            mCurrentInputContentInfo = null;
        }

     /*   mWebView.loadUrl("about:blank");
        mMimeTypes.setText("");
        mContentUri.setText("");
        mLabel.setText("");
        mLinkUri.setText("");
        mFlags.setText("");*/

        boolean supported = false;
        for (final String mimeType : contentMimeTypes) {
            if (inputContentInfo.getDescription().hasMimeType(mimeType)) {
                supported = true;
                break;
            }
        }
        if (!supported) {
            return false;
        }

        return onCommitContentInternal(inputContentInfo, flags);
    }

    private boolean isAdultContent(String postDesc) {
        for (String str : explicitWords)
            if (postDesc.contentEquals(str)){
                adultWord = str;
                return true;
            }
        return false;
    }

    private boolean onCommitContentInternal(InputContentInfoCompat inputContentInfo, int flags) {
        if ((flags & InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION) != 0) {
            try {
                inputContentInfo.requestPermission();
            } catch (Exception e) {
                Timber.e( "InputContentInfoCompat#requestPermission() failed." + e.getMessage());
                return false;
            }
        }


        Glide.with(PostsDetailsActivity.this).asGif()
                .load(inputContentInfo.getLinkUri().toString())
                .placeholder(R.drawable.image_accepted)
                .into(post_detail_image_attach);

       // post_detail_image_attach.setImageDrawable(getResources().getDrawable(R.drawable.image_accepted));

      //  gifImage = inputContentInfo.getContentUri().toString();
         gifImage = inputContentInfo.getLinkUri().toString();// GIF URL
        gifGiven = true;

        mCurrentInputContentInfo = inputContentInfo;
        mCurrentFlags = flags;

        return true;
    }

    private EditText createEditTextWithContentMimeTypesMain(String[] contentMimeTypes) {
        final CharSequence hintText = "Enter Message            ";
        final String[] mimeTypes = Arrays.copyOf(contentMimeTypes, contentMimeTypes.length);  // our own copy of contentMimeTypes.


        EditText exitText = new AppCompatEditText(this) {
            @Override
            public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
                final InputConnection ic = super.onCreateInputConnection(editorInfo);
                EditorInfoCompat.setContentMimeTypes(editorInfo, mimeTypes);
                final InputConnectionCompat.OnCommitContentListener callback =
                        new InputConnectionCompat.OnCommitContentListener() {
                            @Override
                            public boolean onCommitContent(InputContentInfoCompat inputContentInfo,
                                                           int flags, Bundle opts) {
                                return PostsDetailsActivity.this.onCommitContent02(
                                        inputContentInfo, flags, opts, mimeTypes);
                            }
                        };
                return InputConnectionCompat.createWrapper(ic, editorInfo, callback);
            }
        };
        exitText.setHint(hintText);
        exitText.setTextColor(Color.BLACK);
        exitText.setHintTextColor(Color.BLACK);

        return exitText;
    }

    private boolean onCommitContent02(InputContentInfoCompat inputContentInfo, int flags,
                                    Bundle opts, String[] contentMimeTypes) {
        // Clear the temporary permission (if any).  See below about why we do this here.
        try {
            if (mCurrentInputContentInfo != null) {
                mCurrentInputContentInfo.releasePermission();
            }
        } catch (Exception e) {
            Timber.e("InputContentInfoCompat#releasePermission() failed." + e.getLocalizedMessage());
        } finally {
            mCurrentInputContentInfo = null;
        }

     /*   mWebView.loadUrl("about:blank");
        mMimeTypes.setText("");
        mContentUri.setText("");
        mLabel.setText("");
        mLinkUri.setText("");
        mFlags.setText("");*/

        boolean supported = false;
        for (final String mimeType : contentMimeTypes) {
            if (inputContentInfo.getDescription().hasMimeType(mimeType)) {
                supported = true;
                break;
            }
        }
        if (!supported) {
            return false;
        }

        return onCommitContentInternal02(inputContentInfo, flags);
    }

    private boolean onCommitContentInternal02(InputContentInfoCompat inputContentInfo, int flags) {
        if ((flags & InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION) != 0) {
            try {
                inputContentInfo.requestPermission();
            } catch (Exception e) {
                Timber.e( "InputContentInfoCompat#requestPermission() failed." + e.getMessage());
                return false;
            }
        }


        Glide.with(PostsDetailsActivity.this).asGif()
                .load(inputContentInfo.getLinkUri().toString())
                .placeholder(R.drawable.image_accepted)
                .into(post_detail_image);

       // post_detail_image_attach.setImageDrawable(getResources().getDrawable(R.drawable.image_accepted));

      //  gifImage = inputContentInfo.getContentUri().toString();
         gifImage = inputContentInfo.getLinkUri().toString();// GIF URL
        gifGiven = true;


        mCurrentInputContentInfo = inputContentInfo;
        mCurrentFlags = flags;

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (mCurrentInputContentInfo != null) {
            savedInstanceState.putParcelable(INPUT_CONTENT_INFO_KEY,
                    (Parcelable) mCurrentInputContentInfo.unwrap());
            savedInstanceState.putInt(COMMIT_CONTENT_FLAGS_KEY, mCurrentFlags);
        }
        mCurrentInputContentInfo = null;
        mCurrentFlags = 0;
        super.onSaveInstanceState(savedInstanceState);
    }



    private Intent sharedIntentMaker() {
        String sharebody = String.valueOf(myList.getUser_name_random() + " " + "said:"
                + " " + myList.getTextStatus() + " " + "inside Voiceme Android App. " +
                "You can download from www.beacandid.com/candid?Post=" + postId + "");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, sharebody);
        return shareIntent;
    }



    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }






    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();

        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        options.withMaxResultSize(960, 1028);
        //    options.setCompressionQuality(DEFAULT_COMPRESS_QUALITY);
        options.setFreeStyleCropEnabled(true);
        uCrop.useSourceImageAspectRatio();

        options.setShowCropGrid(false);
        options.setMaxScaleMultiplier(10.0f);
        return uCrop.withOptions(options);
    }



    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.subscribe_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.subscribe_menu:

                new AlertDialog.Builder(PostsDetailsActivity.this)
                        .setTitle("Subscribe to receive Notification from this post ?")
                        .setMessage("Note: You can unsubscribe to stop receiving notifications")
                        .setPositiveButton("unSubscribe", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //    Log.d("MainActivity", "Sending atomic bombs to Jupiter");

                                unsubscribeToTopic(String.valueOf("POST_" + postId));

                            }
                        })
                        .setNegativeButton("Subscribe", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //   Log.d("MainActivity", "Aborting mission...");
                                subscribeToTopic02(String.valueOf("POST_" + postId));


                            }
                        })
                        .show();


                return true;

            case R.id.post_pending: //myList

                try {
                    mPresenter.deletePendingPost(myList.getIdPosts());

                    mPresenter.postStatus(MySharedPreferences.getUserId(preferences), myList.getTextStatus(), myList.getImage_url(), myList.getGroup_id(), myList.getCategory(),
                            "", "1", String.valueOf(myList.isAdult_filter()));

                    //postStatus(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void unHugMethod() {

        mPresenter.sendSadToServerOnline(MySharedPreferences.getUserId(preferences), postId, "0",  MySharedPreferences.getFireBaseToken(preferences));

        hug_button_true = false;

        hugCounter--;
        HugButtonMain.setImageDrawable(getResources().getDrawable(R.drawable.broken_candid));
        new_counter_hug_number.setText(NumberFormat.getIntegerInstance().format(hugCounter));
    }

    private void unLikeMethod() {
        mPresenter.sendLikeToServerOnline(MySharedPreferences.getUserId(preferences), postId, "0",  MySharedPreferences.getFireBaseToken(preferences));

        likeCounter--;
        likeButtonMain.setImageDrawable(getResources().getDrawable(R.drawable.heart_shape_outline));
        new_counter_like_number.setText(NumberFormat.getIntegerInstance().format(likeCounter));
    }





    private void enterTextInPostDetails(String randomUsername, String randomAvatarUrl) {
        String message = mMessageEditText.getText().toString();
        // Todo post comment on server

        commentCount = mMessageEditText.getText().toString().length();
        if (commentCount > 500){
            Toast.makeText(this, "Please enter short messages", Toast.LENGTH_SHORT).show();
        } else {
            mMessageAdapter.addMessage(new PostUserCommentModel(message, postId,
                    randomAvatarUrl,
                    randomUsername, String.valueOf(System.currentTimeMillis()/1000), 0, "1"));
        }

        mMessageEditText.setText("");
    }



    private void showComments(final List<PostUserCommentModel> myList) {

        this.myCommentList = myList;
        mMessageAdapter = new MessageAdapter(this, myList, mInsertMessageListener, postId, getBus());
        mMessageRecyclerView.setAdapter(mMessageAdapter);
        mMessageEditText.setText("");


    }

  /*  @Subscribe
    public void postLikeNotification(Account.sendLikeUserId sendReply) throws Exception {

        //   Timber.e("same user who posted the post");

        String sendLike01 = "senderid@" + MySharedPreferences.getUserId(preferences) + "_contactId@" +
                sendReply.user_id_who_will_receive_notification + "_postId@" + postId  + "_click@" + "6"; // 6 for like the comment. 7 for replied to comment
        getRandomName(sendLike01, sendReply.randomUserId   );
    }
    */

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        disposables.clear(); // do not send event after activity has been destroyed


        super.onDestroy();
    }

    private void showScreenShotImage(Bitmap b) {
        list_item_posts_message_image.setImageBitmap(b);
    }

    private void shareImage() {
        progressFrame.setVisibility(View.VISIBLE);
        candid_back.setVisibility(View.VISIBLE);

        askPermissions();
    }

    private void askPermissions() {
        int requestCode = 232;
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PermissionChecker.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, requestCode);
            }
        }
    }

    private void askPermissionsComment() {
        int requestCode = 23;
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PermissionChecker.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, requestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try{
        switch (requestCode) {
            case 232: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    shareStatus = true;
                    PixelShot.of(parent_row_text).setResultListener(this).toPNG().save();

                    progressFrame.setVisibility(View.GONE);


                } else {

                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case 23: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    changeAvatar();



                } else {

                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }}
        catch (Exception e){
            Log.e("PDA", e.getMessage());
        }
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

                File file = new File(resultUri.getPath());

                if (shareStatus){
                    shareScreenshot(file);

                } else {
                    uploadFile(Uri.parse(tempOutputFile.getPath()));
                    post_detail_image.setImageResource(0);

                    post_detail_image.setImageURI(Uri.fromFile(file));
                }

               // Timber.e(String.valueOf(Uri.fromFile(tempOutputFile)));

                // avatarProgressFrame.setVisibility(View.VISIBLE);
                // getBus.post(new Account.ChangeAvatarRequest(Uri.fromFile(tempOutputFile)));
            }
        }
    }

    private void uploadFile(Uri fileUri) {
        File file = new File(String.valueOf(fileUri));

        progressFrame.setVisibility(View.VISIBLE);


        mPresenter.postImageUpload(file);

    }

    private void startCropActivity(@NonNull Uri uri) {

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(tempOutputFile));

        uCrop = advancedConfig(uCrop);
        uCrop.start(PostsDetailsActivity.this);
    }

    private void startCropActivity(@NonNull Uri uri, File file) {

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(file));

        uCrop = advancedConfig(uCrop);
        uCrop.start(PostsDetailsActivity.this);
    }


    private void shareScreenshot(File uri) {

        Uri photoURI = FileProvider.getUriForFile(PostsDetailsActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                uri);

        shareStatus = false;

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Candid App");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "You can download Candid app from play store https://play.google.com/store/apps/details?id=in.beacandid.app.beacandid");
        intent.putExtra(Intent.EXTRA_STREAM, photoURI);//pass uri here
        startActivity(Intent.createChooser(intent, "SHARE"));

    }



    private void showRecycleWithDataFilled(final PostsModel myList) {

        this.myList = myList;


        if (myList.isAdult_filter()){
            switchbutton_postDetail.setVisibility(View.VISIBLE);
            switchbutton_postDetail.setOn(true);
            switchbutton_postDetail.setColorOn(Color.parseColor(myList.getDark_color()));
            postMessage.setText("This post is flagged as Abusive");
            if (myList.getType()==1){
                list_item_posts_message_image.setImageDrawable(getResources().getDrawable(R.drawable.picture));
                status_below_space.setVisibility(View.GONE);
            } else {
                list_item_posts_message_image.setVisibility(View.GONE);
            }
          //  list_item_posts_message_image.setImageDrawable(getResources().getDrawable(R.drawable.picture));

        } else {
            switchbutton_postDetail.setVisibility(View.GONE);
            switchbutton_postDetail.setOn(false);
            switchbutton_postDetail.setColorOn(Color.parseColor(myList.getDark_color()));
            postMessage.setText(myList.getTextStatus());

            if (myList.getType()==1){
                list_item_posts_message_image.setImageURI(myList.getImage_url());
                status_below_space.setVisibility(View.GONE);
            } else {
                list_item_posts_message_image.setVisibility(View.GONE);
            }
        }



        detail_list_item_post_background.setBackgroundColor(Color.parseColor(myList.getLight_color()));
        like_back_layout.setBackgroundColor(Color.parseColor(myList.getDark_color()));
        candid_back.setBackgroundColor(Color.parseColor(myList.getDark_color()));

        if (myList.getLocationPOJO()==null){
            distance_country_detail.setVisibility(View.GONE);
            distance_locality_postdetail.setVisibility(View.GONE);
        } else {
       //    List<Address> getLocationvalue =  getLocation(myList.getLocationPOJO().getLatitude(), myList.getLocationPOJO().getLongitude());
        //    distance_locality_postdetail.setText(getLocationvalue.get(0).getLocality());
          //  distance_country_detail.setText(getLocationvalue.get(0).getCountryName());
        }


        setLikeCounter(myList.getLikes());
        setHugCounter(myList.getHug());

        user_name.setText(myList.getUser_name_random());

        // Todo make JodaTIme

        if (myList.getPostTime() == null){
            return;
        } else {
            timeStamp.setText(DateUtils.getRelativeDateTimeString(PostsDetailsActivity.this, Long.parseLong(myList.getPostTime()), DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));

            //    timeStamp.setText(CurrentTime.getCurrentTime(myList.getPostTime(), PostsDetailsActivity.this));
        }
        postMessage.setText(myList.getTextStatus());

        category.setText(myList.getName());

        new_counter_like_number.setText(String.valueOf(myList.getLikes()));
        new_counter_hug_number.setText(String.valueOf(myList.getHug()));
        new_counter_cmt_number.setText(String.valueOf(myList.getComments() + myList.getComments_reply()));

        user_avatar.setImageURI(myList.getAvatar_url_random());

        if (myList.getUserLike() != null){
            if (myList.getUserLike()){
                likeButtonMain.setImageDrawable(getResources().getDrawable(R.drawable.heart_shape_outlinered));
                like_button_true = true;
                //   likeButtonMain.setFavoriteResource(like_after);
            } else {
                likeButtonMain.setImageDrawable(getResources().getDrawable(R.drawable.heart_shape_outline));
                like_button_true = false;
            }


            if (myList.getUserHuge()){
                HugButtonMain.setImageDrawable(getResources().getDrawable(R.drawable.broken_candidred));
                hug_button_true = true;
            } else {
                HugButtonMain.setImageDrawable(getResources().getDrawable(R.drawable.broken_candid));
                hug_button_true = false;
            }
        }

        if (myList.getReportAbuseCount() >= 1){
            postMessage.setText("******** This post is flagged as Abusive for General Public *********");
            list_item_posts_message_image.setImageDrawable(getResources().getDrawable(R.drawable.picture));
        }
    }


    public void setLikeCounter(int likeCounter) {
        this.likeCounter = likeCounter;
    }

    public void setHugCounter(int hugCounter) {
        this.hugCounter = hugCounter;
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void sendCommentNotification() {

        mPresenter.postOwnerNotification(MySharedPreferences.getUserId(preferences), postId, MySharedPreferences.getFireBaseToken(preferences));
    }

    @Override
    public void sendCommentReplyNotification(String id_post_comments) {

        mPresenter.postCommentOwnerNotification(MySharedPreferences.getUserId(preferences), id_post_comments, postId, MySharedPreferences.getFireBaseToken(preferences));

    }

    @Override
    public void sendCommentReplyReplyNotification(String id_post_comments_reply) {
        mPresenter.postCommentReplyOwnerNotification(MySharedPreferences.getUserId(preferences), id_post_comments_reply, postId, MySharedPreferences.getFireBaseToken(preferences));


    }

    @Override
    public void postCommentLike(SuccessResponse successResponse) {
        // Send notification to user with id_user_name ID.
        // todo make call to this method using presenter
        Toast.makeText(PostsDetailsActivity.this, "success comment like", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postCommentReplyLike(SuccessResponse successResponse) {

    }

    @Override
    public void getCommentRepy(List<PostUserCommentModel> response) {
      //  Log.e("RESPONSE:::", "Size===" + response.size());
        showComments(response);
        progressFrame.setVisibility(View.GONE);

        gifGiven = false;
        imageGiven = false;


    }

    @Override
    public void getSinglePost(List<PostsModel> response) {
        try {
            showRecycleWithDataFilled(response.get(0));
        }
        catch (IndexOutOfBoundsException e){

        }
    }

    @Override
    public void gotoDiscoverActivity() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("No Post Found");
        alertDialog.setMessage("The page you are trying to open is not available or removed by the user. Go back to the app");
        alertDialog.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(PostsDetailsActivity.this, DiscoverActivity.class));
            }
        });
        alertDialog.show();

    }

    @Override
    public void notificationResult(SuccessResponse notificationResult) {
      //  Toast.makeText(this, String.valueOf(notificationResult.getSuccess()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        post_detail_image.setImageResource(0);

        imageGiven = true;


    }

    @Override
    public void sendLikeToServer(PostLikesResponse postLikesResponse) {

    }

    @Override
    public void sendSadToServer(PostLikesResponse postLikesResponse) {
    }

    @Override
    public void deleteComment(UserResponse response) {
        Toast.makeText(this, "deleted comment successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteCommentReply(UserResponse response) {

    }


    private void subscribeToTopic(String topic){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                FirebaseMessaging.getInstance().subscribeToTopic(topic)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg = String.valueOf("Subscribed to topic " + topic);
                                if (!task.isSuccessful()) {
                                    msg = String.valueOf("Failed to Subscribed to topic " + topic);
                                }
                             //   Toast.makeText(PostsDetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }, 10000);


    }

    private void subscribeToTopic02(String topic){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                FirebaseMessaging.getInstance().subscribeToTopic(topic)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg = String.valueOf("You have subscribed to receive notification from this post ");
                                if (!task.isSuccessful()) {
                                    msg = String.valueOf("Failed to Subscribed to topic " + topic);
                                }
                                Toast.makeText(PostsDetailsActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }, 100);


    }

    private void unsubscribeToTopic(String topic){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = String.valueOf("You have Unsubscribed to stop notifications from this post");
                        if (!task.isSuccessful()) {
                            msg = String.valueOf("Unsubscribed to topic " + topic);
                        }
                        Toast.makeText(PostsDetailsActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });
    }


    @Override
    public void onPixelShotSuccess(String path) {

        startCropActivity(Uri.fromFile(new File(path)), new File(path));
    }

    @Override
    public void onPixelShotFailed() {

    }


}