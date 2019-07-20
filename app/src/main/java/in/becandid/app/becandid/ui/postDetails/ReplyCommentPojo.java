package in.becandid.app.becandid.ui.postDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harishpc on 6/15/2017.
 */
public class ReplyCommentPojo {

        @SerializedName("user_name") @Expose private String userName;
        @SerializedName("id_posts") @Expose private String id_posts;
        @SerializedName("id_user_name_random") @Expose private String id_user_name_random;
        @SerializedName("id_post_comments") @Expose private String commentId;
        @SerializedName("id_post_comment_reply") @Expose private String id_post_comment_reply;  // post comment reply
        @SerializedName("comment_likes_true") @Expose private String comment_likes_true;
        @SerializedName("id_user_name") @Expose private String id_user_name;
        @SerializedName("avatar") @Expose private String avatar;
        @SerializedName("message") @Expose private String comment;
        @SerializedName("message_image") @Expose private String message_image;
        @SerializedName("id_post_user_name") @Expose private String idPostUserName;
        @SerializedName("commentUserId") @Expose private String commentUserId;
        @SerializedName("comment_time") @Expose private String commentTime;
        @SerializedName("comment_likes") @Expose private int comment_likes;
        @SerializedName("user_name_reply") @Expose private String user_name_reply;
    @SerializedName("type") @Expose private int type;
    @SerializedName("isGif") @Expose private boolean isGif;

    public boolean isGif() {
        return isGif;
    }

    public String getMessage_image() {
        return message_image;
    }

    public int getType() {
        return type;
    }

    public String getId_posts() {
        return id_posts;
    }

    public String getUserName() {
        return userName;
    }

    public String getId_user_name_random() {
        return id_user_name_random;
    }

    public String getComment_likes_true() {
        return comment_likes_true;
    }

    public int getComment_likes() {
        return comment_likes;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getId_post_comment_reply() {
        return id_post_comment_reply;
    }

    public String getId_user_name() {
        return id_user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getComment() {
        return comment;
    }

    public String getIdPostUserName() {
        return idPostUserName;
    }

    public String getUser_name_reply() {
        return user_name_reply;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public ReplyCommentPojo(String message, String id_posts, String imageUri, String userName, String id_user_name_random, String user_name_reply, String commentTime, int comment_likes, String comment_likes_true) {
        this.comment = message;
        this.id_posts = id_posts;
        this.avatar = imageUri;
        this.userName = userName;
        this.id_user_name_random = id_user_name_random;
        this.user_name_reply = user_name_reply;
        this.commentTime = commentTime;
        this.comment_likes = comment_likes;
        this.comment_likes_true = comment_likes_true;
    }


}
