package in.becandid.app.becandid.ui.postDetails;


import java.io.File;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface PostsDetailsMvpPresenter <V extends PostsDetailsMvpView> extends MvpPresenter<V> {

    void deletePendingPost(String id_posts);
    void postStatus(String user_id, String post_text, String image_url, String group_id, String cat_id, String feeling_id, String type, String adult_filter);

    void postCommentReply(String id_post_comments, String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type);
    void post_commentsOnline(String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type);
    // void postCommentLikeOnline(String id_post_comment,
    // String id_user_name, String comment_likes, String user_id, String token, String post_comment_id);

    void sendSadToServerOnline(String senderId, String postId, String sad, String token);
    void sendLikeNotification(String user_id, String post_id, String like, String token);
    void postCommentReplyLike(String id_post_comment_reply, String id_user_name, String likes, String id_posts, String token);
    void sendLikeToServerOnline(String user_id, String post_id, String like, String token);
    void getCommentsReplyOnline(String id_posts, String user_id, String token);
    void getSinglePostOnline(String id_posts, String user_id);
   // void post_commentsOnline(String id_user_name, String id_post_user_name, String id_posts, String message, String token);
    void deleteCommentReply(String id_post_comment_reply, String user_id, String token);
    // void postCommentReply(String id_post_comments, String id_user_name, String id_post_user_name, String id_posts, String message, String token);
  //  void postCommentReplyReply(String id_post_comments, String id_post_comments_reply, String id_user_name, String id_post_user_name, String id_posts, String message, String token);
    void postCommentLikeOnline(String id_post_comment, String id_user_name, String id_posts, String comment_likes, String token);
    void deleteComment(String id_post_comments, String user_id, String token);

    void postCommentReplyReply(String id_post_comments, String id_post_comments_reply, String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type);
    void postOwnerNotification(String id_user_name, String id_posts, String token);
    void postCommentOwnerNotification(String id_user_name, String id_post_comments, String id_posts, String token);
    void postCommentReplyOwnerNotification(String id_user_name, String id_post_comments_reply, String id_posts, String token);

    void postImageUpload(File file);
}
