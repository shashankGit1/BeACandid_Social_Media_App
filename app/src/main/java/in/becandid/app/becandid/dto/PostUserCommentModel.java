package in.becandid.app.becandid.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import in.becandid.app.becandid.ui.postDetails.ReplyCommentPojo;

/**
 * Created by harish on 1/17/2017.
 */

public class PostUserCommentModel implements Parcelable {

    public static final Creator<PostUserCommentModel> CREATOR = new Creator<PostUserCommentModel>() {
        @Override
        public PostUserCommentModel createFromParcel(Parcel in) {
            return new PostUserCommentModel(in);
        }

        @Override
        public PostUserCommentModel[] newArray(int size) {
            return new PostUserCommentModel[size];
        }
    };


    @SerializedName("user_name") @Expose private String userName;
    @SerializedName("id_posts") @Expose private String id_posts;
    @SerializedName("id_user_name_random") @Expose private String id_user_name_random;
    @SerializedName("id_post_user_name") @Expose private String id_post_user_name;
    @SerializedName("post_comment_id") @Expose private String post_comment_id;  // ID of the user whose comment got liked by someone else
    @SerializedName("post_comment_like_true") @Expose private String post_comment_like_true;
    @SerializedName("commentId") @Expose private String commentId;
    @SerializedName("avatar") @Expose private String avatar;
    @SerializedName("comment") @Expose private String comment;
    @SerializedName("message_image") @Expose private String message_image;
    @SerializedName("postUserId") @Expose private String postUserId;
    @SerializedName("commentUserId") @Expose private String commentUserId;
    @SerializedName("comment_time") @Expose private String commentTime;
    @SerializedName("comment_likes") @Expose private int comment_likes;
    @SerializedName("reply") @Expose private List<ReplyCommentPojo> replyComment;
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

    public List<ReplyCommentPojo> getReplyComment() {
        return replyComment;
    }

    public String getPost_comment_id() {
        return post_comment_id;
    }

    public int getComment_likes() {
        return comment_likes;
    }

    public String getPost_comment_like_true() {
        return post_comment_like_true;
    }

    public String getUserName() {
        return userName;
    }

    public String getId_post_user_name() {
        return id_post_user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public String getPostUserId() {
        return postUserId;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public String getId_user_name_random() {
        return id_user_name_random;
    }

    public PostUserCommentModel(String message, String id_posts, String imageUri, String userName, String commentTime, int comment_likes, String post_comment_like_true) {
        this.comment = message;
        this.id_posts = id_posts;
        this.commentTime = commentTime;
        this.avatar = imageUri;
        this.userName = userName;
        this.comment_likes = comment_likes;
        this.post_comment_like_true = post_comment_like_true;
    }



    protected PostUserCommentModel(Parcel in) {
        userName = in.readString();
        id_user_name_random = in.readString();
        avatar = in.readString();
        id_posts = in.readString();
        comment = in.readString();
        commentTime = in.readString();
        post_comment_like_true = in.readString();
        comment_likes = in.readInt();
        post_comment_id = in.readString();
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(avatar);
        parcel.writeString(id_posts);
        parcel.writeString(comment);
        parcel.writeString(commentTime);
        parcel.writeString(post_comment_like_true);
        parcel.writeString(post_comment_id);
        parcel.writeInt(comment_likes);
    }
}
