package in.becandid.app.becandid.ui.postDetails;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import in.becandid.app.becandid.di.rxbus.Events;
import in.becandid.app.becandid.di.rxbus.RxBus;
import in.becandid.app.becandid.dto.PostUserCommentModel;
import in.becandid.app.becandid.infrastructure.Account;
import in.becandid.app.becandid.ui.profile.SimpleDividerItemDecoration;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.chat.MessageActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.base.VoicemeApplication;

import static in.becandid.app.becandid.ui.base.Constants.CONSTANT_PREF_FILE;

/**
 * Created by User on 07.12.2016.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private final static int MAXIMUM_VISIBLE_ITEM_COUNT = 4;

    private SharedPreferences preferences;
    private final Context mContext;
    private InsertMessageListener mInsertMessageListener;
    protected List<PostUserCommentModel> mMessageList;
    private List<MessageViewHolder> mMessageHolderList = new ArrayList<>();
    private static SharedPreferences recyclerviewpreferences;
    protected String commentReplyMessage;
    protected String postId;
    protected RxBus bus;

    public MessageAdapter(Context context, List<PostUserCommentModel> mMessageList, InsertMessageListener insertMessageListener, String postId, RxBus bus) {
        mContext = context;
        mInsertMessageListener = insertMessageListener;
        this.mMessageList = mMessageList;
        this.postId = postId;
        this.bus = bus;
        recyclerviewpreferences = ((VoicemeApplication) context.getApplicationContext()).getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_message, parent, false);

        MessageViewHolder messageViewHolder = new MessageViewHolder(v, bus);
        mMessageHolderList.add(messageViewHolder);

        return messageViewHolder;
    }

    public void clear() {
        mMessageList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.onBind(position, mMessageList.get(position));
    }



    public void addMessage(PostUserCommentModel messageItem) {
        mMessageList.add(messageItem);

        int position = mMessageList.size() - 1;
        mInsertMessageListener.onMessageInserted(position);
        notifyItemInserted(position);
    }

    private MessageViewHolder getViewHolderByPosition(int position) {
        for (MessageViewHolder viewHolder : mMessageHolderList) {
            if (viewHolder.getBoundPosition() == position) {
                return viewHolder;
            }
        }
        return null;
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
        // current   return dataSet.get(position) != null ? VIEW_ITEM : VIEW_PROG;

        return mMessageList.get((mMessageList.size() - 1)) != null ? VIEW_ITEM : VIEW_PROG;

        //  return mMessageList.get((mMessageList.size() - 1)) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public interface InsertMessageListener {
        void onMessageInserted(int position);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private static final float ALPHA_INVISIBLE = 0f;
        private static final float ALPHA_VISIBLE = 1f;
        private static final int ANIMATION_DELAY = 5000;
        private final Handler mDelayHandler = new Handler();
        private View mHolderView;
        private int mPosition;
        private PostUserCommentModel user_comment;

        private Animation mFadeOutAnimation;
        private boolean isAnimating = false;
        private boolean isVisible = true;

        private TextView username;
        private TextView tv_user_name_your_username;
        private TextView comment_time;
        private TextView like_below_comment_reply;
        private TextView like_below_comment_counter;
        private ImageView like_below_comment_direct;
        private TextView messageCard;
        private ImageView commentMore;
        private SimpleDraweeView userImage;
        private SimpleDraweeView comment_image;
        private PopupMenu popupMenu;
        protected View messageReplyProgress;
        protected RxBus bus;

        public CommentReplyAdapter commentReplyAdapter;
        List<ReplyCommentPojo> mReplyMessageList;
        private RecyclerView replyRecyclerview;
        private LinearLayoutManager mReplyLinearLayoutManager;
        protected int comment_likes;
        protected boolean comment_likes_true = false;


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



        public MessageViewHolder(final View itemView, RxBus bus) {
            super(itemView);
            mHolderView = itemView;

            this.bus = bus;
            username = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_user_name_your_username = (TextView) itemView.findViewById(R.id.tv_user_name_your_username);
            messageReplyProgress = itemView.findViewById(R.id.messageReplyProgress);
            comment_time = (TextView) itemView.findViewById(R.id.comment_time);
            like_below_comment_direct = (ImageView) itemView.findViewById(R.id.like_below_comment_direct);
            like_below_comment_counter = (TextView) itemView.findViewById(R.id.like_below_comment_counter);
            like_below_comment_reply = (TextView) itemView.findViewById(R.id.like_below_comment_reply);
            messageCard = (TextView) itemView.findViewById(R.id.tv_message_card);
            userImage = (SimpleDraweeView) itemView.findViewById(R.id.iv_user_image);
            comment_image = (SimpleDraweeView) itemView.findViewById(R.id.comment_image);
            replyRecyclerview = (RecyclerView) itemView.findViewById(R.id.reply_comment_recyclerview);

            preferences = ((VoicemeApplication) itemView.getContext().getApplicationContext()).getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);

            commentMore = (ImageView) itemView.findViewById(R.id.comment_more);
            mFadeOutAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_out_anim);
            mFadeOutAnimation.setAnimationListener(mFadeOutAnimationListener);

            messageReplyProgress.setVisibility(View.GONE);

        }

        protected void commentMoreMethod(View view, int position) {
            popupMenu = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.comment_more_single, popupMenu.getMenu());
            //    this.menu = popupMenu.getMenu();

            if (MySharedPreferences.getUserId(preferences).equals(mMessageList.get(mPosition).getCommentUserId())){
                popupMenu.getMenu().setGroupVisible(R.id.menu_group_remove_comment, true);
            } else {
                popupMenu.getMenu().setGroupVisible(R.id.menu_group_remove_comment, false);
            }

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.commentDeleteSingle:

                            try {
                                    // done
                                 bus.send(new Events.deleteComment(mMessageList.get(mPosition).getCommentId(), MySharedPreferences.getUserId(preferences), mMessageList.get(mPosition).getId_posts()));

                                //  postReplyCommentFinal(view, messageItem.getCommentId(), edt.getText().toString(), messageItem.getId_post_user_name(), messageItem.getCommentUserId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            remove(position);
                           // notifyItemRemoved(position);

                            return true;
                        case R.id.send_message_reply_menu:

                            Intent intent = new Intent(itemView.getContext(), MessageActivity.class);
                            final Random rand = new Random();
                            int randomUserId = rand.nextInt(54) + 1;
                            String randomUserIID = String.valueOf(randomUserId);

                            intent.putExtra(Constants.RANDOM_SENDER_ID, randomUserIID); // username inside message
                            intent.putExtra(Constants.SENDER_USERID, mMessageList.get(mPosition).getCommentUserId() ); // username inside message
                            intent.putExtra(Constants.RANDOM_RECEIVER_USERNAME, mMessageList.get(mPosition).getUserName()); // direct username in chat
                            intent.putExtra(Constants.RANDOM_RECEIVER_ID, mMessageList.get(mPosition).getId_user_name_random()); // direct username id in chat
                            intent.putExtra(Constants.RECEIVER_USERID, MySharedPreferences.getUserId(preferences)); // direct random user id in chat
                            intent.putExtra(Constants.RANDOM_RECEIVER_AVATAR, mMessageList.get(mPosition).getAvatar()); // direct random user id in chat
                            intent.putExtra(Constants.POSTID, mMessageList.get(mPosition).getId_posts());

                            intent.putExtra(Constants.YES, mMessageList.get(mPosition).getCommentUserId());
                            intent.putExtra(Constants.USERNAME, mMessageList.get(mPosition).getUserName());
                            itemView.getContext().startActivity(intent);

                            return true;

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

        public void onBind(int position, PostUserCommentModel messageItem) {

            mPosition = position;
            user_comment = messageItem;
            mHolderView.setAlpha(ALPHA_VISIBLE);
            isVisible = true;
            if (messageItem.getPost_comment_like_true()!= null){
                if (messageItem.getPost_comment_like_true().equals("true")){
                    comment_likes_true = true;
                }
            } else {
                comment_likes_true = false;
            }


            comment_likes = messageItem.getComment_likes();

            like_below_comment_counter.setText(String.valueOf(comment_likes));

            String message = messageItem.getComment();
            String imageUri = messageItem.getAvatar();
            String userName = messageItem.getUserName();

            comment_time.setText(DateUtils.getRelativeDateTimeString(itemView.getContext(), Long.parseLong(messageItem.getCommentTime()), DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));

           // comment_time.setText(CurrentTime.getCurrentTime(messageItem.getCommentTime(), itemView.getContext()));
            commentReplyAdapter = new CommentReplyAdapter(itemView.getContext(), mMessageList, mPosition, postId, bus);

            mReplyLinearLayoutManager = new LinearLayoutManager(itemView.getContext()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            mReplyLinearLayoutManager.setStackFromEnd(true);
//        mLinearLayoutManager.setReverseLayout(true);

            replyRecyclerview.setLayoutManager(mReplyLinearLayoutManager);
            replyRecyclerview.addItemDecoration(new SimpleDividerItemDecoration(itemView.getContext()));
            replyRecyclerview.setAdapter(commentReplyAdapter);

         /*   username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userProfile(view, messageItem);
                }
            }); */

            like_below_comment_counter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommentlikeCounter(view, messageItem);
                }
            });

            like_below_comment_direct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommentlikeCounter(view, messageItem);
                }
            });
            like_below_comment_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommentReply(view, messageItem);
                }
            });


         /*   userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userProfile(view, messageItem);
                }
            });

            messageCard.setOnClickListener(new View.OnClickListener() {
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

            messageCard.setText(message);
            username.setText(userName);
            userImage.setImageURI(imageUri);

            if (messageItem.getMessage_image() != null && !messageItem.getMessage_image().toString().isEmpty()){
                if (messageItem.getType() == 1){
                    comment_image.setImageURI(messageItem.getMessage_image());

                } else if (messageItem.getType() == 2){
                    Glide.with(itemView.getContext()).asGif()
                            .load(messageItem.getMessage_image())
                            .placeholder(R.drawable.image_accepted)
                            .into(comment_image);
                } else {
                    comment_image.setVisibility(View.GONE);
                }

            } else {
                comment_image.setVisibility(View.GONE);

            }








            if (messageItem.getCommentUserId() != null){
                if (!messageItem.getCommentUserId().equals(MySharedPreferences.getUserId(preferences))||
                        !messageItem.getId_post_user_name().equals(MySharedPreferences.getUserId(preferences))){
                    commentMore.setVisibility(View.VISIBLE);
                } else {
                    commentMore.setVisibility(View.INVISIBLE);

                }

                if (messageItem.getCommentUserId().equals(MySharedPreferences.getUserId(preferences))){
                    tv_user_name_your_username.setVisibility(View.VISIBLE);
                }
            }

        }



        public CommentReplyAdapter getCommentReplyAdapter() {
            return commentReplyAdapter;
        }

        // Todo open chat with random username
     /*   private void userProfile(View view, PostUserCommentModel messageItem) {
            if (messageItem.getCommentUserId().equals(MySharedPreferences.getUserId(recyclerviewpreferences))){
                view.getContext().startActivity(new Intent(view.getContext(), ProfileActivity.class));
            } else {
                Intent intent = new Intent(view.getContext(), SecondProfile.class);
                intent.putExtra(Constants.SECOND_PROFILE_ID, messageItem.getCommentUserId());
                view.getContext().startActivity(intent);
            }
        } */

        private void CommentlikeCounter(View view, PostUserCommentModel messageItem) {


            if (comment_likes_true){
                comment_likes_true = false;
                comment_likes--;

                try {

                    bus.send(new Events.sendCommentLike(messageItem.getCommentId(), "1"));


                    //  postReplyCommentFinal(view, messageItem.getCommentId(), edt.getText().toString(), messageItem.getId_post_user_name(), messageItem.getCommentUserId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                comment_likes_true = true;
                try {
                    bus.send(new Events.sendCommentLike(messageItem.getCommentId(), "1"));

                //    postLike(messageItem.getCommentId(), messageItem.getPost_comment_id());
                 //   postCommentReplyLikeNotification(messageItem.getCommentUserId(), messageItem.getId_user_name_random());  // commenting userid, commenting user randomname.

                } catch (Exception e) {
                    e.printStackTrace();
                }
                comment_likes++;
            }
            like_below_comment_counter.setText(String.valueOf(comment_likes));
            //   Toast.makeText(view.getContext(), "Counter clicked", Toast.LENGTH_SHORT).show();
        }

        private void CommentReply(View view, PostUserCommentModel messageItem) {

            bus.send(new Account.sendCommentReply(messageItem.getCommentId(), messageItem.getId_post_user_name(), messageItem.getUserName()));


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

    private void postLike(String id_post_comment, String id_user_name) throws Exception {
        getBus.post(new Account.sendCommentLike(id_post_comment, id_user_name, "1"));

    }

    private void postUnLike(String id_post_comment, String id_user_name) throws Exception {
        getBus.post(new Account.sendCommentLike(id_post_comment, id_user_name, null));

    }

    private void postCommentReplyLikeNotification(String like_sent_to_user_who_commented, String randomUserId) throws Exception {
        getBus.post(new Account.sendLikeUserId(like_sent_to_user_who_commented, randomUserId));
    }

*/


}

/*

deleteing comment


private void postReplyCommentFinal(View view, String commentId , String message, String idPostUsername, String post_user_id) throws Exception {

            ((VoicemeApplication)(itemView.getContext().getApplicationContext())).getWebService()
                    .sendCommentReply(commentId, MySharedPreferences.getUserId(preferences), idPostUsername, postId, message, MySharedPreferences.getUserToken(preferences))
                    .observeOn(AndroidSchedulers.mainThread())
                    .retryWhen(new RetryWithDelay(3,2000))
                    .subscribe(new BaseSubscriber<PostCommentReplyPojo>() {
                        @Override
                        public void onNext(PostCommentReplyPojo userResponse) {
                            commentReplyAdapter.addMessage(new ReplyCommentPojo(userResponse.getMessage(),postId,
                                    userResponse.getAvatarUrl(),
                                    userResponse.getUsername(),userResponse.getIdUserNameRandom() , userResponse.getUsername(), String.valueOf(System.currentTimeMillis()/1000), 0, "1"));

                            messageReplyProgress.setVisibility(View.GONE);

                        }
                        @Override
                        public void onError(Throwable e) {
                            Timber.d(e);
                            Crashlytics.logException(e);

                        }
                    });
        }



private void deleteChat(View view, String messageId) throws Exception {
        SharedPreferences preferences = ((VoicemeApplication) view.getContext().getApplicationContext()).getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);

        ((VoicemeApplication) view.getContext().getApplicationContext()).getWebService()
                .deleteComment(messageId, MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserToken(preferences))
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<UserResponse>() {
                    @Override
                    public void onNext(UserResponse response) {

                        //   Toast.makeText(view.getContext(), "Comment was successfully deleted", Toast.LENGTH_LONG).show();
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
