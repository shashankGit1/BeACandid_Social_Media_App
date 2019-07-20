package in.becandid.app.becandid.infrastructure;

import com.google.firebase.messaging.RemoteMessage;

import in.becandid.app.becandid.ui.profile.ChatTextPojo;

/**
 * Created by Harish on 7/25/2016.
 */
public final class Account {

    private Account() {

    }

    public static class UserResponse extends ServiceResponse {
        public int Id;
        public String AvatarUrl;
        public String UserNickName;
        public String Email;
        public String AuthToken;
        public String gender;
    }

    /* Login with Register with username and Paswword */

    public static class ChatMessageBusEvent {
        public ChatTextPojo messagePojo;

        public ChatMessageBusEvent(ChatTextPojo messagePojo) {
            this.messagePojo = messagePojo;
        }
    }

    /* Server returns the response for register login with username and passsword */
    public static class LoginWithUsernameResponse extends ServiceResponse {
    }


    /* login with local auth token that came from above register */
    public static class LoginWithLocalTokenRequest {
        public String AuthToken;

        public LoginWithLocalTokenRequest(String authToken) {
            AuthToken = authToken;
        }
    }

    /* login response with local auth token from register */
    public static class LoginWithLocalTokenResponse extends UserResponse {
    }


    /* login with Facebook and Google */
    public static class LoginWithExternalTokenRequest {
        public String Provider;
        public String Token;
        public String ClientId;

        public LoginWithExternalTokenRequest(String provider, String token) {
            Provider = provider;
            Token = token;
            ClientId = "android";
        }
    }

    public static class sendLikeNotify{
        public String postId;
        public String likeUnlike;

        public sendLikeNotify(String postId, String likeUnlike) {
            this.postId = postId;
            this.likeUnlike = likeUnlike;
        }
    }

    public static class saveFirebaseToken{
        public saveFirebaseToken() {
        }
    }

    public static class sendPrivateMessage{
        public String Id;
        public String chatText;
        public String chatImage;
        public String RANDOM_RECEIVER_USERNAME;
        public String RANDOM_RECEIVER_AVATAR;
        public String RANDOM_RECEIVER_ID;
        public String post_id;
        public String SENDER_USERID;
        public String RECEIVER_USERID;
        public RemoteMessage remoteMessage;

        public sendPrivateMessage(String Id, String chatText, String chatImage, String RANDOM_RECEIVER_USERNAME, String RANDOM_RECEIVER_AVATAR, String RANDOM_RECEIVER_ID, String post_id, String SENDER_USERID, String RECEIVER_USERID, RemoteMessage remoteMessage) {
            this.Id = Id;
            this.chatText = chatText;
            this.chatImage = chatImage;
            this.RANDOM_RECEIVER_USERNAME = RANDOM_RECEIVER_USERNAME;
            this.RANDOM_RECEIVER_AVATAR = RANDOM_RECEIVER_AVATAR;
            this.RANDOM_RECEIVER_ID = RANDOM_RECEIVER_ID;
            this.post_id = post_id;
            this.SENDER_USERID = SENDER_USERID;
            this.RECEIVER_USERID = RECEIVER_USERID;
            this.remoteMessage = remoteMessage;
        }
    }

    public static class comment_reply_response{
        public String otherUserId;
        public String postId;
        public String likeUnlike;

        public comment_reply_response(String otherUserId, String postId, String likeUnlike) {
            this.otherUserId = otherUserId;
            this.postId = postId;
            this.likeUnlike = likeUnlike;
        }
    }

    public static class sendDeleteCommentReply{
        public String id_post_comment_reply;
        public String id_posts;

        public sendDeleteCommentReply(String id_post_comment_reply, String id_posts) {
            this.id_post_comment_reply = id_post_comment_reply;
            this.id_posts = id_posts;
        }
    }

    public static class sendSadEntry{
        public String postId;
        public String sadUnSad;


        public sendSadEntry(String postId, String sadUnSad) {
            this.postId = postId;
            this.sadUnSad = sadUnSad;

        }
    }

    public static class makePostAdult{
        public String id_posts;
        public String adult_filter;


        public makePostAdult(String id_posts, String adult_filter) {
            this.id_posts = id_posts;
            this.adult_filter = adult_filter;

        }
    }

    /* UserResponse from Facebook and Google login */
    public static class LoginWithExternalTokenResponse extends UserResponse {
    }

    /* when we are register for account, we are registering a  new user and loggin in at the same time */
    public static class sendCommentReply {
        public String id_post_comments;
        public String id_post_user_name;
        public String user_name;

        public sendCommentReply(String id_post_comments, String id_post_user_name, String user_name) {
            this.id_post_comments = id_post_comments;
            this.id_post_user_name = id_post_user_name;
            this.user_name = user_name;
        }
    }

    public static class sendCommentReplyReply {
        public String id_post_comments;
        public String id_post_comments_reply;
        public String id_post_user_name;
        public String user_name;

        public sendCommentReplyReply(String id_post_comments, String id_post_comments_reply, String id_post_user_name, String user_name) {
            this.id_post_comments = id_post_comments;
            this.id_post_comments_reply = id_post_comments_reply;
            this.id_post_user_name = id_post_user_name;
            this.user_name = user_name;
        }
    }
    public static class sendCommentReplyResponse {
        public String randomUsername;
        public String randomAvatarUrl;
        public String message;

        public sendCommentReplyResponse(String randomUsername, String randomAvatarUrl, String message) {
            this.randomUsername = randomUsername;
            this.randomAvatarUrl = randomAvatarUrl;
            this.message = message;
        }
    }


    public static class sendLikeUserId {
        public String user_id_who_will_receive_notification;
        public String randomUserId;

        public sendLikeUserId(String user_id_who_will_receive_notification, String randomUserId) {
            this.user_id_who_will_receive_notification = user_id_who_will_receive_notification;
            this.randomUserId = randomUserId;
        }
    }

    public static class sendCommentLike {
        public String id_post_comment;
        public String id_user_name;
        public String like;

        public sendCommentLike(String id_post_comment, String id_user_name, String like) {
            this.id_post_comment = id_post_comment;
            this.id_user_name = id_user_name;
            this.like = like;
        }
    }


    public static class sendCommentReplyLike {
        public String id_post_comment_reply;

        public sendCommentReplyLike(String id_post_comment_reply) {
            this.id_post_comment_reply = id_post_comment_reply;
        }
    }

    public static class UpdateProfileResponse extends ServiceResponse {
        public String displayName;
        public String Email;
    }

    public static class ChangePasswordRequest {
        public String CurrentPassword;
        public String NewPassword;
        public String ConfirmNewPassword;

        public ChangePasswordRequest(String currentPassword, String newPassword, String confirmNewPassword) {
            CurrentPassword = currentPassword;
            NewPassword = newPassword;
            this.ConfirmNewPassword = confirmNewPassword;
        }
    }

    public static class ChangePasswordResponse extends ServiceResponse {
    }

    public static class UserDetailsUpdatedEvent {
        public in.becandid.app.becandid.dto.User User;

        public UserDetailsUpdatedEvent(in.becandid.app.becandid.dto.User user) {
            User = user;
        }
    }

    public static class UpdateGcmRegistrationRequest {
        public String registrationId;

        public UpdateGcmRegistrationRequest(String registrationId) {
            this.registrationId = registrationId;
        }
    }

    public static class UpdateGcmRegistrationResponse extends ServiceResponse {

    }

    public static class GoogleAccessTokenCognito {
        public String accessToken;

        public GoogleAccessTokenCognito(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getToken() {
            return accessToken;
        }
    }

    public static class FacebookAccessTokenCognito {
        public String fbToken;

        public FacebookAccessTokenCognito(String fbToken) {
            this.fbToken = fbToken;
        }
    }


    public static class AmazonIdentity {
        public String amazonIdentity;

        public AmazonIdentity(String amazonIdentity) {
            this.amazonIdentity = amazonIdentity;
        }

        public String getAmazonIdentity() {
            return amazonIdentity;
        }
    }

    public static class chatData {
        public ChatTextPojo chatTextPojo;

        public chatData(ChatTextPojo chatTextPojo) {
            this.chatTextPojo = chatTextPojo;
        }
    }


}
