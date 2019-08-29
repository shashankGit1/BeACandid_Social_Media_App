package in.becandid.app.becandid.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import in.becandid.app.becandid.ui.login.locationPOJO;

@SuppressWarnings("unused")
public class PostsModel {

    public static final int TEXT_TYPE=0;
    public static final int IMAGE_TYPE=1;

    @SerializedName("id_posts") @Expose private String idPosts;
    @SerializedName("category") @Expose private String category;
    @SerializedName("id_user_name") @Expose private String idUserName;
    @SerializedName("post_time") @Expose private String postTime;
    @SerializedName("text_status") @Expose private String textStatus;
    @SerializedName("audio_duration") @Expose private String audioDuration;
    @SerializedName("audio_file_link") @Expose private String audioFileLink;
    @SerializedName("report_abuse_count") @Expose private int reportAbuseCount;
    @SerializedName("group_id") @Expose private String group_id;


    // todo enter group owner ID.


    @SerializedName("id_user_name_random") @Expose private String id_user_name_random;
    @SerializedName("user_name_random") @Expose private String user_name_random;
    @SerializedName("avatar_url_random") @Expose private String avatar_url_random;
    @SerializedName("likes") @Expose private Integer likes;
  //  @SerializedName("same") @Expose private Integer same;
    @SerializedName("hug") @Expose private Integer hug;
  //  @SerializedName("listen") @Expose private Integer listen;
    @SerializedName("comments") @Expose private Integer comments;
    @SerializedName("comments_reply") @Expose private Integer comments_reply;
    @SerializedName("user_like") @Expose private Boolean userLike;
    @SerializedName("location") @Expose private locationPOJO locationPOJO;
 //   @SerializedName("user_Same") @Expose private Boolean userSame;
    @SerializedName("user_Huge") @Expose private Boolean userHuge;
//    @SerializedName("user_Listen") @Expose private Boolean userListen;
    @SerializedName("post_comment") @Expose private String post_comment;
    @SerializedName("light_color") @Expose private String light_color;
    @SerializedName("dark_color") @Expose private String dark_color;
    @SerializedName("name") @Expose private String name;
    @SerializedName("comment_reply") @Expose private String comment_reply;
    @SerializedName("image_url") @Expose private String image_url;
    @SerializedName("comment_avatar") @Expose private String comment_avatar;
    @SerializedName("isImage") @Expose private Boolean isImage;
    @SerializedName("adult_filter") @Expose private boolean adult_filter;
    @SerializedName("type") @Expose private int type;
    @SerializedName("admin_id") @Expose private String admin_id;

    @SerializedName("feeling_like") @Expose private String feelingLike;
    @SerializedName("comment_random_id") @Expose private String commentRandomId;


    public String getAdmin_id() {
        return admin_id;
    }

    public String getFeelingLike() {
        return feelingLike;
    }

    public String getCommentRandomId() {
        return commentRandomId;
    }

    public String getImage_url() {
        return image_url;
    }

    public int getType() {
        return type;
    }

    public Boolean getImage() {
        return isImage;
    }

    public boolean isAdult_filter() {
        return adult_filter;
    }

    public String getCategory() {
        return category;
    }

    public String getLight_color() {
        return light_color;
    }

    public String getDark_color() {
        return dark_color;
    }

    public locationPOJO getLocationPOJO() {
        return locationPOJO;
    }

    public String getName() {
        return name;
    }

    public String getComment_reply() {
        return comment_reply;
    }

    public String getComment_avatar() {
        return comment_avatar;
    }

    public String getAvatar_url_random() {
        return avatar_url_random;
    }

    public String getUser_name_random() {
        return user_name_random;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getId_user_name_random() {
        return id_user_name_random;
    }

    public String getPost_comment() {
        return post_comment;
    }

    public int getReportAbuseCount() {
        return reportAbuseCount;
    }

    public Boolean getUserLike() {
        return userLike;
    }

    public Integer getComments_reply() {
        return comments_reply;
    }

    public void setUserLike(Boolean userLike) {
        this.userLike = userLike;
    }

   /* public Boolean getUserSame() {
        return userSame;
    }

    public void setUserSame(Boolean userSame) {
        this.userSame = userSame;
    } */

    public Boolean getUserHuge() {
        return userHuge;
    }

    public void setUserHuge(Boolean userHuge) {
        this.userHuge = userHuge;
    }

  /*  public Boolean getUserListen() {
        return userListen;
    }

    public void setUserListen(Boolean userListen) {
        this.userListen = userListen;
    } */

    public String getIdPosts() {
        return idPosts;
    }

    public void setIdPosts(String idPosts) {
        this.idPosts = idPosts;
    }

    public String getIdUserName() {
        return idUserName;
    }

    public void setIdUserName(String idUserName) {
        this.idUserName = idUserName;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getTextStatus() {
        return textStatus;
    }

    public void setTextStatus(String textStatus) {
        this.textStatus = textStatus;
    }

    public String getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(String audioDuration) {
        this.audioDuration = audioDuration;
    }

    public String getAudioFileLink() {
        return audioFileLink;
    }

    public void setAudioFileLink(String audioFileLink) {
        this.audioFileLink = audioFileLink;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

  /*  public Integer getSame() {
        return same;
    }

    public void setSame(Integer same) {
        this.same = same;
    } */

    public Integer getHug() {
        return hug;
    }

    public void setHug(Integer hug) {
        this.hug = hug;
    }

   /* public Integer getListen() {
        return listen;
    }

    public void setListen(Integer listen) {
        this.listen = listen;
    } */

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}