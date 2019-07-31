package in.becandid.app.becandid.ui.discover;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.net.Uri;
import android.preference.PreferenceManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.becandid.app.becandid.di.component.ActivityComponent;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseViewHolder;
import in.becandid.app.becandid.ui.base.MvpView;

public abstract class PostsImageCardViewHolder extends BaseViewHolder implements View.OnClickListener, MvpView {
    protected int likeCounter;
    protected int hugCounter;

    @BindView(R.id.list_item_posts_avatar_image)
    protected SimpleDraweeView  user_avatar;

    @BindView(R.id.list_item_post_userNickName_image)
    protected TextView user_name;

    @BindView(R.id.list_item_posts_category_image)
    protected TextView category;

    @BindView(R.id.list_item_posts_timeStamp_image)
    protected TextView timeStamp;

    @BindView(R.id.list_item_posts_message_image)
    protected TextView postMessage;

    @BindView(R.id.drop_down_menu_image)
    protected ImageView drop_down_menu;

    @BindView(R.id.new_counter_like_number_image)
    protected TextView new_counter_like_number;

    @BindView(R.id.new_counter_hug_number_image)
    protected TextView new_counter_hug_number;

    @BindView(R.id.emoji_above_comment_image)
    protected ImageView commentCounterImage;

    @BindView(R.id.new_counter_cmt_number_image)
    protected TextView new_counter_cmt_number;

    @BindView(R.id.list_item_like_button_image)
    protected SimpleDraweeView likeButtonMain;

    @BindView(R.id.list_item_hug_button_image)
    protected ImageView HugButtonMain;

    @BindView(R.id.switchbutton_image)
    protected LabeledSwitch switchbutton;

    @BindView(R.id.list_item_posts_message_image_image)
    protected SimpleDraweeView list_item_posts_message_image;

    @BindView(R.id.list_item_post_background_image)
    protected ConstraintLayout itemcard_back_light;

    @BindView(R.id.all_post_like_buttons_image)
    protected ConstraintLayout all_post_like_buttons;

    @BindView(R.id.parent_row_image)
    protected View parent_row;


    protected boolean like_button_true;
    protected boolean hug_button_true;


  //  protected Location mLastLocation;
   // protected MediaPlayer mediaPlayer = new MediaPlayer();
    // protected Geocoder geocoder;


    protected boolean key_adult_filter;
    protected ActivityComponent activityComponent;

    protected List<Address> addresses = null;

    protected PostsModel dataItem;
    SharedPreferences pref;

    private Context mContext;

    public PostsImageCardViewHolder(View itemView, Context context) {
        super(itemView);
        //Imageview for avatar and play pause button
        this.mContext = context;



        ButterKnife.bind(this, itemView);


        pref = PreferenceManager.getDefaultSharedPreferences(itemView.getContext());
        key_adult_filter = pref.getBoolean("key_adult_filter", true);
        //      geocoder = new Geocoder(itemView.getContext(), Locale.getDefault());
        // distance_country = (TextView) itemView.findViewById(R.id.distance_country);
        // distance_locality = (TextView) itemView.findViewById(R.id.distance_locality);

        //OnClickListeners

        postMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardBackground(v);
            }
        });
        likeButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeButtonMethod(view);
            }
        });

        HugButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hugButtonMethod(view);
            }
        });

        list_item_posts_message_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardBackground(v);
            }
        });


    /*    post_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenCounterClicked(v);
            }
        });

        */

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClicked(view);
            }
        });

        commentCounterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardBackground(v);
            }
        });

        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreClick(view);
            }
        });

        user_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreClick(view);
            }
        });

        parent_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardBackground(view);
            }
        });



        drop_down_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreClick(view);
            }
        });

        switchbutton.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                // Implement your switching logic here
                switchButton(labeledSwitch,isOn,itemView);
            }
        });


    }

    protected void likeButtonMethod(View view) {

    }

    protected void hugButtonMethod(View view) {

    }

   /* protected void sameButtonMethod(View view) {

    } */

    protected void moreClick(View view){

    }

    protected void cardBackground(View view) {

    }

    protected void switchButton(LabeledSwitch labeledSwitch, boolean isOn, View view){

    }

    protected void categoryClicked(View view) {

    }

    @Override
    public void onClick(View v) {
    }

    public void bind(PostsModel dataItem) {
        this.dataItem = dataItem;

        if (dataItem.getLikes() != null && dataItem.getHug() != null){
            setLikeCounter(dataItem.getLikes());
            setHugCounter(dataItem.getHug());
        } else {
            return;
        }

        if (key_adult_filter){
            if (dataItem.isAdult_filter()){
                switchbutton.setVisibility(View.VISIBLE);
                switchbutton.setOn(true);
                switchbutton.setColorOn(Color.parseColor(dataItem.getDark_color()));
                list_item_posts_message_image.setImageDrawable(itemView.getResources().getDrawable(R.drawable.picture));
                postMessage.setText("This post is filtered as Adult");

            } else {
                switchbutton.setVisibility(View.GONE);
                switchbutton.setOn(false);
                switchbutton.setColorOn(Color.parseColor(dataItem.getDark_color()));
                list_item_posts_message_image.setImageURI(Uri.parse(dataItem.getImage_url()));
                postMessage.setText(dataItem.getTextStatus());
            }

        } else {
            switchbutton.setVisibility(View.GONE);
            switchbutton.setOn(false);
            switchbutton.setColorOn(Color.parseColor(dataItem.getDark_color()));
            list_item_posts_message_image.setImageURI(Uri.parse(dataItem.getImage_url()));
            postMessage.setText(dataItem.getTextStatus());
        }



    /*    if (!checkPermissions()){
            distance_locality.setVisibility(View.GONE);
            distance_country.setVisibility(View.GONE);
        } else {
            if (dataItem.getLocationPOJO()==null){
                distance_locality.setVisibility(View.GONE);
                distance_country.setVisibility(View.GONE);
            } else {
                List<Address> getLocationvalue =  getLocation(dataItem.getLocationPOJO().getLatitude(), dataItem.getLocationPOJO().getLongitude());
                distance_locality.setText(getLocationvalue.get(0).getLocality());
                distance_country.setText(getLocationvalue.get(0).getCountryName());
            }
        }
*/


        category.setText(dataItem.getName());

        user_name.setText(dataItem.getUser_name_random());

        itemcard_back_light.setBackgroundColor(Color.parseColor(dataItem.getLight_color()));
        all_post_like_buttons.setBackgroundColor(Color.parseColor(dataItem.getDark_color()));
       // llCommentViews.setBackgroundColor(Color.parseColor(dataItem.getDark_color()));



        if (dataItem.getPostTime() == null){
            return;
        } else {
            timeStamp.setText(DateUtils.getRelativeDateTimeString(itemView.getContext(), Long.parseLong(dataItem.getPostTime()), DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));

            //  timeStamp.setText(CurrentTime.getCurrentTime(, ));
        }
        new_counter_like_number.setText(String.valueOf(dataItem.getLikes()));
        new_counter_hug_number.setText(String.valueOf(dataItem.getHug()));
        new_counter_cmt_number.setText(String.valueOf(dataItem.getComments() + dataItem.getComments_reply()));


        user_avatar.setImageURI(dataItem.getAvatar_url_random());

       /* if (!dataItem.getAvatarPics().equals("") || dataItem.getAvatarPics() != null) {
            Picasso.with(itemView.getContext())
                    .load(dataItem.getAvatarPics())
                    .resize(75, 75)
                    .centerInside()
                    .into(user_avatar);
        } */

        likeButtonMain.setImageResource(R.drawable.ic_heart);
        HugButtonMain.setImageResource(R.drawable.ic_heart_broken);

        if (dataItem.getUserLike() != null){
            if (dataItem.getUserLike()){

                likeButtonMain.setImageResource(R.drawable.ic_heart_red);

              //  likeButtonMain.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_heart_red));
                like_button_true = true;
                //   likeButtonMain.setFavoriteResource(like_after);
            } else {
                like_button_true = false;
                likeButtonMain.setImageResource(R.drawable.ic_heart);


                //   likeButtonMain.setFavoriteResource(like_before);
            }


            if (dataItem.getUserHuge()){
                HugButtonMain.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_heart_broken_red));

                hug_button_true = true;
            } else {
                hug_button_true = false;
                HugButtonMain.setImageResource(R.drawable.ic_heart_broken);

            }

        } else {
            return;
        }

        if (dataItem.getReportAbuseCount() >= 2){
            postMessage.setText("***** This post is blocked by General Public ******");
            list_item_posts_message_image.setImageDrawable(itemView.getResources().getDrawable(R.drawable.picture));
            switchbutton.setVisibility(View.GONE);


        } else {
            return;
        }

    }


    public void setLikeCounter(int likeCounter) {
        this.likeCounter = likeCounter;
    }

    public void setHugCounter(int hugCounter) {
        this.hugCounter = hugCounter;
    }

   /* private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(itemView.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
    */

}


/* private List<Address> getLocation(double latitude, double longitude){
        try {
            // Using getFromLocation() returns an array of Addresses for the area immediately
            // surrounding the given latitude and longitude. The results are a best guess and are
            // not guaranteed to be accurate.
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    // In this sample, we get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.

        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.

        }

        return addresses;
    }




    protected void sendLikeToServer(final VoicemeApplication application, int like, final String message) {

        SharedPreferences preferences = application.getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);
        String userId = MySharedPreferences.getUserId(preferences);
        String postId = dataItem.getIdPosts();

        Rx2AndroidNetworking.get(ApiEndPoint.GET_LIKES_SINGLE)   //likes_get_single.php
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("user_id", userId)
                .addQueryParameter("post_id", postId)
                .addQueryParameter("like", String.valueOf(like))
                .addQueryParameter("token", MySharedPreferences.getUserToken(preferences))
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(PostLikesResponse.class);
    }

    protected void sendHugToServer(final VoicemeApplication application, int hug, final String message) {

        SharedPreferences preferences = application.getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);
        String userId = MySharedPreferences.getUserId(preferences);
        String postId = dataItem.getIdPosts();

        Rx2AndroidNetworking.get(ApiEndPoint.GET_HUGS_SINGLE)   //hugs_get_single.php
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("user_id", userId)
                .addQueryParameter("post_id", postId)
                .addQueryParameter("hug", String.valueOf(hug))
                .addQueryParameter("token", MySharedPreferences.getUserToken(preferences))
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(PostLikesResponse.class);

    }

    protected void sendUnlikeToServer(final VoicemeApplication application, int like, int hug, final String message) {
        SharedPreferences preferences = application.getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);
        String userId = MySharedPreferences.getUserId(preferences);
        String postId = dataItem.getIdPosts();

        Rx2AndroidNetworking.get(ApiEndPoint.GET_UNLIKE)   //hugs_get_single.php
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("user_id", userId)
                .addQueryParameter("post_id", postId)
                .addQueryParameter("like", String.valueOf(like))
                .addQueryParameter("token", MySharedPreferences.getUserToken(preferences))
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(PostLikesResponse.class);
    }


    public void getRandomName(final VoicemeApplication application, String url){
        SharedPreferences preferences = application.getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);
        String userId = MySharedPreferences.getUserId(preferences);
        String postId = dataItem.getIdPosts();

        Rx2AndroidNetworking.get(ApiEndPoint.GET_RANDOM_NAME_COMMENT)   //hugs_get_single.php
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("id_user_name", MySharedPreferences.getUserId(preferences))
                .addQueryParameter("id_posts", postId)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(CheckRandomUserNamePojo.class);

        // AFTER ABOVE RESPONSE    sendLikeNotification(application, postId, url, userResponse.getIdUserNameRandom(), dataItem.getId_user_name_random());

    }

    protected void sendLikeNotification(final VoicemeApplication application, String postId, String likeUrl, String senderAnonymous, String receiverAnonymous) {

        //  TODO ADD NOTIFICATION

     /*   application.getWebService()
                .sendLikeNotification(likeUrl, senderAnonymous, receiverAnonymous)
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onNext(String response) {
                        Timber.d("Got user details");
                        //     followers.setText(String.valueOf(response.size()));
                        // Toast.makeText(ChangeProfileActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
                        Timber.d("Message from server" + response);
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();

                    }
                });
    }
 */