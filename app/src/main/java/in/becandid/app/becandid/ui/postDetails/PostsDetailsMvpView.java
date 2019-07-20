package in.becandid.app.becandid.ui.postDetails;

import java.util.List;

import in.becandid.app.becandid.dto.PostLikesResponse;
import in.becandid.app.becandid.dto.PostUserCommentModel;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.ui.base.MvpView;

public interface PostsDetailsMvpView extends MvpView {


    void sendCommentNotification();
    void sendCommentReplyNotification(String id_post_comments);
    void sendCommentReplyReplyNotification(String id_post_comments_reply);
    void postCommentLike(SuccessResponse successResponse);
    void postCommentReplyLike(SuccessResponse successResponse);
    void getCommentRepy(List<PostUserCommentModel> userCommentModelList);
    void sendLikeToServer(PostLikesResponse postLikesResponse);
    void sendSadToServer(PostLikesResponse postLikesResponse);
    void deleteComment(UserResponse response);
    void deleteCommentReply(UserResponse response);

        void gotoDiscoverActivity();


    void notificationResult(SuccessResponse notificationResult);


    void getImageUrl(String imageUrl);

    void getSinglePost(List<PostsModel> postsModels);



}
