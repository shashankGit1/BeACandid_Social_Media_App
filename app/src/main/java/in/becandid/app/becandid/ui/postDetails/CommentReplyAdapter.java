package in.becandid.app.becandid.ui.postDetails;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.becandid.app.becandid.di.rxbus.RxBus;
import in.becandid.app.becandid.dto.PostUserCommentModel;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.infrastructure.Account;
import in.becandid.app.becandid.ui.chat.MessageActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.base.VoicemeApplication;

import static in.becandid.app.becandid.ui.base.Constants.CONSTANT_PREF_FILE;

/**
 * Created by harishpc on 6/15/2017.
 */
public class CommentReplyAdapter extends RecyclerView.Adapter<CommentReplyAdapter.ReplyMessageViewHolder> {

    private final static int MAXIMUM_VISIBLE_ITEM_COUNT = 4;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    protected String postId;

    private SharedPreferences preferences;
    private final Context mContext;
    protected List<ReplyCommentPojo> mMessageList;
    private List<ReplyMessageViewHolder> mMessageHolderList = new ArrayList<>();
    private static SharedPreferences recyclerviewpreferences;
    protected RxBus bus;


    public CommentReplyAdapter(Context context, List<PostUserCommentModel> mMessageList, int mPosition, String postId, RxBus bus) {
        mContext = context;
        this.postId = postId;
        this.bus = bus;
        this.mMessageList = mMessageList.get(mPosition).getReplyComment();
        recyclerviewpreferences = ((VoicemeApplication) context.getApplicationContext()).getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);
    }

    @Override
    public ReplyMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_message_reply, parent, false);

        ReplyMessageViewHolder messageViewHolder = new ReplyMessageViewHolder(v, bus);
        mMessageHolderList.add(messageViewHolder);

        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(ReplyMessageViewHolder holder, int position) {
        holder.onBind(position, mMessageList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mMessageList == null){
            return 0;
        } else {
            return mMessageList.size();
        }

    }

    @Override
    public int getItemViewType(int position) {
        //   return (position == dataSet.size() - 1) ? VIEW_ITEM : VIEW_PROG;
        /// current   return dataSet.get(position) != null ? VIEW_ITEM : VIEW_PROG;

        return mMessageList.get((mMessageList.size() - 1)) != null ? VIEW_ITEM : VIEW_PROG;

        // return mMessageList.get((mMessageList.size() - 1)) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public void addMessage(ReplyCommentPojo messageItem) {
        mMessageList.add(messageItem);

        int position = mMessageList.size() - 1;
     //   mInsertMessageListener.onMessageInserted(position);
        notifyItemInserted(position);
    }

    private ReplyMessageViewHolder getViewHolderByPosition(int position) {
        for (ReplyMessageViewHolder viewHolder : mMessageHolderList) {
            if (viewHolder.getBoundPosition() == position) {
                return viewHolder;
            }
        }
        return null;
    }

    public interface InsertReplyMessageListener {
        void onMessageInserted(int position);
    }

    class ReplyMessageViewHolder extends RecyclerView.ViewHolder {

        private static final float ALPHA_INVISIBLE = 0f;
        private static final float ALPHA_VISIBLE = 1f;
        private static final int ANIMATION_DELAY = 5000;
        private final Handler mDelayHandler = new Handler();
        private View mHolderView;
        private ReplyCommentPojo user_comment;

        private Animation mFadeOutAnimation;
        private boolean isAnimating = false;
        private boolean isVisible = true;

        private TextView username;
        private TextView messageCard;
        private TextView comment_time;
        private ImageView commentMore;
        private SimpleDraweeView userImage;
        private SimpleDraweeView comment_reply_image;
        private PopupMenu popupMenu;
        private TextView comment_reply_textview;
        private TextView comment_counter_reply_number;
        private ImageView like_imageview_below_comment;
        protected int likeReplyCounter;
        protected View messageReplyProgress;
        protected boolean like_reply_true = false;
        protected RxBus bus;
        protected int mPosition;



        private Animation.AnimationListener mFadeOutAnimationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mHolderView.setAlpha(ALPHA_INVISIBLE);
                isAnimating = false;
                isVisible = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        public ReplyMessageViewHolder(final View itemView, RxBus bus) {
            super(itemView);
            mHolderView = itemView;
            this.bus = bus;

            messageReplyProgress = itemView.findViewById(R.id.messageReplyProgress_reply);
            like_imageview_below_comment = (ImageView) itemView.findViewById(R.id.like_imageview_below_comment);
            comment_reply_textview = (TextView) itemView.findViewById(R.id.comment_reply_textview);
            comment_time = (TextView) itemView.findViewById(R.id.comment_time_reply);
            comment_counter_reply_number = (TextView) itemView.findViewById(R.id.comment_counter_reply_number);
            username = (TextView) itemView.findViewById(R.id.tv_user_name_reply);
            messageCard = (TextView) itemView.findViewById(R.id.tv_message_card_reply);
            userImage = (SimpleDraweeView) itemView.findViewById(R.id.iv_user_image_reply);
            comment_reply_image = (SimpleDraweeView) itemView.findViewById(R.id.comment_reply_image);

            preferences = ((VoicemeApplication) itemView.getContext().getApplicationContext()).getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);

            commentMore = (ImageView) itemView.findViewById(R.id.comment_more_reply);
            mFadeOutAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_out_anim);
            mFadeOutAnimation.setAnimationListener(mFadeOutAnimationListener);
            messageReplyProgress.setVisibility(View.GONE);
        }

        protected void commentMoreMethod(View view, int position) {
            this.mPosition = position;

            popupMenu = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.comment_more, popupMenu.getMenu());
            //    this.menu = popupMenu.getMenu();

            if (MySharedPreferences.getUserId(preferences).equals(mMessageList.get(position).getId_user_name())){
                popupMenu.getMenu().setGroupVisible(R.id.menu_group_remove_comment_reply, true);
            } else {
                popupMenu.getMenu().setGroupVisible(R.id.menu_group_remove_comment_reply, false);
            }

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.commentDelete:

                            try {

                                bus.send(new Account.sendDeleteCommentReply(mMessageList.get(position).getId_post_comment_reply(), mMessageList.get(position).getId_posts()));

                             //   deleteChat(view, mMessageList.get(mPosition).getCommentId());
                                //      Toast.makeText(view.getContext(), "comment ID: " + mMessageList.get(position).getCommentId(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            remove(position);


                            return true;
                        case R.id.send_message_menu_comment:
                            final Random rand = new Random();
                            int randomUserId = rand.nextInt(54) + 1;
                            String randomUserIID = String.valueOf(randomUserId);

                            Intent intent = new Intent(itemView.getContext(), MessageActivity.class);
                            intent.putExtra(Constants.YES, mMessageList.get(position).getCommentUserId());
                            intent.putExtra(Constants.USERNAME, mMessageList.get(position).getUserName());

                         /*   intent.putExtra(Constants.RANDOM_SENDER_USERNAME, dialog.getLastMessage().getUser().getName()); // username inside message
                            intent.putExtra(Constants.RANDOM_SENDER_ID, dialog.getLastMessage().getUser().getId()); // username inside message
                            intent.putExtra(Constants.SENDER_USERID, dialog.getLastMessage().getUser().getSenderAnonymous()); // username inside message
                            intent.putExtra(Constants.RANDOM_RECEIVER_USERNAME, dialog.getUser().getName()); // direct username in chat
                            intent.putExtra(Constants.RANDOM_RECEIVER_ID, dialog.getUser().getId()); // direct username id in chat
                            intent.putExtra(Constants.RECEIVER_USERID, dialog.getLastMessage().getUser().getReceiverAnonymous()); // direct random user id in chat
                            */

                            intent.putExtra(Constants.RANDOM_SENDER_ID, randomUserIID); // username inside message
                            intent.putExtra(Constants.SENDER_USERID, mMessageList.get(position).getCommentUserId() ); // username inside message
                            intent.putExtra(Constants.RANDOM_RECEIVER_USERNAME, mMessageList.get(position).getUserName()); // direct username in chat
                            intent.putExtra(Constants.RANDOM_RECEIVER_ID, mMessageList.get(position).getId_user_name_random()); // direct username id in chat
                            intent.putExtra(Constants.RECEIVER_USERID, MySharedPreferences.getUserId(preferences)); // direct random user id in chat
                            intent.putExtra(Constants.RANDOM_RECEIVER_AVATAR, mMessageList.get(position).getAvatar()); // direct random user id in chat
                            intent.putExtra(Constants.POSTID, mMessageList.get(position).getId_posts());

                            itemView.getContext().startActivity(intent);

                            return true;
                        //case R.id.report_post_comment:

                           /* Intent reportIntent = new Intent(itemView.getContext(), ReportAbuseActivity.class);
                            reportIntent.putExtra(Constants.IDPOST, mMessageList.get(mPosition).get);
                            reportIntent.putExtra(Constants.IDUSERNAME, dataItem.getIdUserName());
                            reportIntent.putExtra(Constants.STATUS_POST, dataItem.getTextStatus());
                            itemView.getContext().startActivity(reportIntent);

                            return true; */

                        default:
                            return false;

                    }
                }
            });
            popupMenu.show();
        }

        private void remove(int mPosition) {
            mMessageList.remove(mPosition);
            notifyItemRemoved(mPosition);

            notifyItemRangeChanged(mPosition, getItemCount());
        }

        public void onBind(int position, ReplyCommentPojo messageItem) {


            user_comment = messageItem;
            mHolderView.setAlpha(ALPHA_VISIBLE);
            isVisible = true;

            if (messageItem.getComment_likes_true() != null){
                if (messageItem.getComment_likes_true().equals("true")){
                    like_reply_true = true;
                }
            } else {
                like_reply_true = false;
            }


            likeReplyCounter = messageItem.getComment_likes();

            comment_counter_reply_number.setText(String.valueOf(likeReplyCounter));

            comment_time.setText(DateUtils.getRelativeDateTimeString(itemView.getContext(), Long.parseLong(messageItem.getCommentTime()), DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));

          //  comment_time.setText(CurrentTime.getCurrentTime(messageItem.getCommentTime(), itemView.getContext()));

            String message = messageItem.getComment();
            String imageUri = messageItem.getAvatar();
            String userName = messageItem.getUserName();

         /*   username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userProfile(view, messageItem);
                }
            }); */

            comment_counter_reply_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommentlikeCounter(view, messageItem);
                }
            });
            comment_reply_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommentReply(view, messageItem);
                }
            });

            like_imageview_below_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommentlikeCounter(view, messageItem);
                }
            });

         /*   userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userProfile(view, messageItem);
                }
            }); */

        /*    messageCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userProfile(view, messageItem);
                }
            }); */

            commentMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commentMoreMethod(view, position);

                }
            });

            if (messageItem.getCommentUserId() != null){
                if (messageItem.getId_user_name().equals(MySharedPreferences.getUserId(preferences))||
                        messageItem.getIdPostUserName().equals(MySharedPreferences.getUserId(preferences))){
                    commentMore.setVisibility(View.VISIBLE);
                } else {
                    commentMore.setVisibility(View.INVISIBLE);

                }
            }

            messageCard.setText(String.valueOf(message));
            username.setText(userName);
            userImage.setImageURI(imageUri);

            if (messageItem.getMessage_image() != null && !messageItem.getMessage_image().toString().isEmpty()){
                if (messageItem.getType() == 1){
                    comment_reply_image.setImageURI(messageItem.getMessage_image());

                } else if (messageItem.getType() == 2){
                    Glide.with(itemView.getContext()).asGif()
                            .load(messageItem.getMessage_image())
                            .placeholder(R.drawable.image_accepted)
                            .into(comment_reply_image);
                } else {
                    comment_reply_image.setVisibility(View.GONE);

                }
            } else {
                comment_reply_image.setVisibility(View.GONE);

            }


        }

        // TOdo add code to open private chat  or code to enter username while using reply
   /*     private void userProfile(View view, ReplyCommentPojo messageItem) {
            if (messageItem.getId_user_name().equals(MySharedPreferences.getUserId(recyclerviewpreferences))){
                view.getContext().startActivity(new Intent(view.getContext(), ProfileActivity.class));
            } else {
                Intent intent = new Intent(view.getContext(), SecondProfile.class);
                intent.putExtra(Constants.SECOND_PROFILE_ID, messageItem.getId_user_name());
                view.getContext().startActivity(intent);
            }
        } */

        protected void CommentlikeCounter(View view, ReplyCommentPojo messageItem) {

            if (like_reply_true){
                like_reply_true = false;
                likeReplyCounter--;

                try {
                    // add like once

                  //  bus.send(new Events.sendCommentLike(messageItem.getCommentId(), "1"));

                    bus.send(new Account.sendCommentReplyLike(mMessageList.get(mPosition).getId_post_comment_reply()));

                } catch (Exception e) {
                    e.printStackTrace();
                }


                // unlike network call
            } else {
                like_reply_true = true;
                likeReplyCounter++;

                try {
                    // unlike when we send like
                    bus.send(new Account.sendCommentReplyLike(mMessageList.get(mPosition).getId_post_comment_reply()));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            comment_counter_reply_number.setText(String.valueOf(likeReplyCounter));

        }

        private void CommentReply(View view, ReplyCommentPojo messageItem) {

            try {

                //  commentReplyInterface.sendCommentReplyReply(messageItem.getCommentId(), messageItem.getIdPostUserName(), messageItem.getId_posts(), edt.getText().toString(),"", "");

                bus.send(new Account.sendCommentReplyReply(messageItem.getCommentId(), messageItem.getId_post_comment_reply(), messageItem.getIdPostUserName(), messageItem.getUserName()));


                // postComment(edt.getText().toString(),messageItem);
                //   postReplyCommentFinal(view,edt.getText().toString(),  )
                // postReplyCommentFinal(view, edt.getText().toString(), messageItem.getCommentId(), messageItem.getIdPostUserName(), messageItem.getCommentUserId());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        public int getBoundPosition() {
            return mPosition;
        }

        public boolean isAnimating() {
            return isAnimating;
        }

        public boolean isVisible() {
            return isVisible;
        }
    }

    /*
    private void postCommentReplyLike(String id_post_comment_reply, String post_comment_reply_id) throws Exception {
        getBus.post(new Account.sendCommentLike(id_post_comment_reply, post_comment_reply_id, "1"));

    }

    private void postCommentReplyUnLike(String id_post_comment_reply, String post_comment_reply_id) throws Exception {
        getBus.post(new Account.sendCommentLike(id_post_comment_reply, post_comment_reply_id, null));

    }

    private void postCommentReplyLikeNotification(String  user_id_who_will_receive_notification, String random_user_id_of_comment_user) throws Exception {
        getBus.post(new Account.sendLikeUserId(user_id_who_will_receive_notification, random_user_id_of_comment_user));

    }
    */


}

/*

private void postReplyCommentFinal(View view, String message, String commentId, String idPostUsername, String post_user_id) throws Exception {

            ((VoicemeApplication)(itemView.getContext().getApplicationContext())).getWebService()
                    .sendCommentReply(commentId, MySharedPreferences.getUserId(preferences), idPostUsername, postId, message, MySharedPreferences.getUserToken(preferences))
                    .observeOn(AndroidSchedulers.mainThread())
                    .retryWhen(new RetryWithDelay(3,2000))
                    .subscribe(new BaseSubscriber<PostCommentReplyPojo>() {
                        @Override
                        public void onNext(PostCommentReplyPojo userResponse) {

                            addMessage(new ReplyCommentPojo(userResponse.getMessage(),postId,
                                    userResponse.getAvatarUrl(),
                                    userResponse.getUsername(), userResponse.getUsername(),userResponse.getIdUserNameRandom() , String.valueOf(System.currentTimeMillis()/1000), 0, "1"));



                            //   Timber.e(userResponse.getMsg());

                        /*    sendCommentReply(userResponse);

                            if (sendReply.id_post_user_name.equals(MySharedPreferences.getUserId(preferences))){
                                //     Timber.e("Same User");
                                Timber.e("same user who posted the post");
                                String sendLike01 = "senderid@" + MySharedPreferences.getUserId(preferences)  + "_contactId@" +
                                        sendReply.commentUserId + "_postId@" + postId  + "_click@" + "7"; // 6 for like the comment. 7 for replied to comment
                                postLikeNotification(sendLike01);

                            } else if(sendReply.commentUserId.equals(MySharedPreferences.getUserId(preferences))) {

                                String sendLike01 = "senderid@" + sendReply.commentUserId  + "_contactId@" +
                                        MySharedPreferences.getUserId(preferences) + "_postId@" + postId  + "_click@" + "7"; // 6 for like the comment. 7 for replied to comment
                                postLikeNotification(sendLike01);

                            }
                            messageReplyProgress.setVisibility(View.GONE);

                                    }
@Override
public void onError(Throwable e) {
        Timber.d(e);
        Crashlytics.logException(e);

        }
        });
        }
        */
/*

private void deleteChat(View view, String messageId) throws Exception {
        SharedPreferences preferences = ((VoicemeApplication) view.getContext().getApplicationContext()).getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);

        ((VoicemeApplication) view.getContext().getApplicationContext()).getWebService()
                .deleteCommentReply(messageId, MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserToken(preferences))
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<UserResponse>() {
                    @Override
                    public void onNext(UserResponse response) {

                           Toast.makeText(view.getContext(), "Comment was successfully deleted", Toast.LENGTH_LONG).show();
                        //          Toast.makeText(MessageActivity.this, response.get(0).getId(), Toast.LENGTH_SHORT).show();
                        //       String text = response.get(0).getText();
                        //    MessagePojo pojo = response.get(0).getMessage();
                        //messages = response;
                        //   Toast.makeText(MessageActivity.this, "deleted message", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
    }
 */
