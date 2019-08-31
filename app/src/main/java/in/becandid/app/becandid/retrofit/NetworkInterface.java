package in.becandid.app.becandid.retrofit;

import java.util.List;

import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.data.network.ApiEndPoint;
import in.becandid.app.becandid.dto.CategoryJoined;
import in.becandid.app.becandid.dto.ContactAddResponse;
import in.becandid.app.becandid.dto.LoginResponse;
import in.becandid.app.becandid.dto.MainResponse;
import in.becandid.app.becandid.dto.PostLikesResponse;
import in.becandid.app.becandid.dto.PostUserCommentModel;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.dto.ProfileMain;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.SuccessResponseChat;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.ui.chat02.model.Dialog;
import in.becandid.app.becandid.ui.chat02.model.Message;
import in.becandid.app.becandid.ui.group.CommunityGroupPojo;
import in.becandid.app.becandid.ui.group.CommunityGroupPojoNew;
import in.becandid.app.becandid.ui.group.GroupsCreatePOJO;
import in.becandid.app.becandid.ui.group.InsertGroupPOJO;
import in.becandid.app.becandid.ui.profile.NotificationPojo;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkInterface {

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_LOGIN_WITHOUT_BASE)
    Single<LoginResponse> getMovies(@Field("deviceId") String deviceId,
                                              @Field("socialNetwork") String socialNetwork);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_REPORT_ABUSE)
    Single<SuccessResponse> reportAbuse(@Field("id_user_name") String id_user_name,
                                        @Field("sender_user_id") String sender_user_id,
                                        @Field("id_posts") String id_posts,
                                        @Field("token") String token,
                                        @Field("message") String message);


    @FormUrlEncoded
    @POST(ApiEndPoint.UPDATE_GROUP)
    Single<SuccessResponse> editGroup(@Field("id_categories") String id_categories,
                                        @Field("name") String name,
                                        @Field("group_description") String group_description,
                                        @Field("group_id") String group_id);




    @FormUrlEncoded
    @POST(ApiEndPoint.GET_LIKES_SINGLE)
    Single<PostLikesResponse> post_likes(@Field("user_id") String user_id,
                                        @Field("post_id") String post_id,
                                        @Field("like") String like,
                                        @Field("token") String token);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_COMMENT_OWNER_NOTIFICATION)
    Single<SuccessResponse> post_comment_owner_notification(
                                        @Field("id_user_name") String id_user_name,
                                        @Field("id_post_comments") String id_post_comments,
                                        @Field("id_posts") String id_posts,
                                        @Field("token") String token);
    @FormUrlEncoded
    @POST(ApiEndPoint.POST_COMMENT_OWNER_REPLY_NOTIFICATION)
    Single<SuccessResponse> post_comment_owner_reply_notification(
                                        @Field("id_user_name") String id_user_name,
                                        @Field("id_post_comments_reply") String id_post_comments_reply,
                                        @Field("id_posts") String id_posts,
                                        @Field("token") String token);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_LOGIN)
    Single<LoginResponse> doLoginResponse(@Field("name") String name,
                                        @Field("email") String email,
                                        @Field("user_id") String user_id,
                                        @Field("deviceId") String deviceId,
                                        @Field("socialNetwork") String socialNetwork);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_COMMENT_LIKE)
    Single<SuccessResponse> postCommentLike(@Field("id_post_comment") String id_post_comment,
                                        @Field("id_user_name") String id_user_name,
                                        @Field("id_posts") String id_posts,
                                        @Field("comment_likes") String comment_likes,
                                        @Field("token") String token);


    @FormUrlEncoded
    @POST(ApiEndPoint.POST_COMMENT_REPLY_LIKE)
    Single<SuccessResponse> postCommentReplyLike(@Field("id_post_comment_reply") String id_post_comment_reply,
                                        @Field("id_user_name") String id_user_name,
                                        @Field("likes") String likes,
                                        @Field("id_posts") String id_posts,
                                        @Field("token") String token);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_INSERT_GROUP)
    Single<GroupsCreatePOJO> postInsertGroup(@Field("id_categories") String id_categories,
                                              @Field("user_id") String user_id,
                                              @Field("group_name") String group_name,
                                              @Field("group_image") String group_image,
                                              @Field("group_description") String group_description);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_NEW_SETTINGS)
    Single<SuccessResponse> postNewSettings(@Field("age_range") String age_range,
                                              @Field("gender") String gender,
                                              @Field("adult_filter") String adult_filter,
                                              @Field("block_premium_search") String block_premium_search,
                                              @Field("id_user_name") String id_user_name);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_LOGIN)
    Single<LoginResponse> doSkipLoginResponse(@Field("deviceId") String deviceId,
                                          @Field("socialNetwork") String socialNetwork);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_SEND_FRIENDS_LIST)
    Single<ContactAddResponse> sendFacebookFriends(@Field("id_user_name") String id_user_name,
                                                   @Field("facebook_id") String facebook_id);


    @FormUrlEncoded
    @POST(ApiEndPoint.POST_JOIN_GROUPS)
    Single<InsertGroupPOJO> sendJoinGroup(@Field("group_ids") String group_ids,
                                          @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_UNJOIN_GROUPS)
    Single<InsertGroupPOJO> sendUnJoinGroup(@Field("group_ids") String group_ids,
                                          @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_JOIN_GROUPS)
    Single<LoginResponse> sendJoinedGroupsOnlineApi(@Field("id_user_name") String id_user_name,
                                          @Field("facebook_id") String facebook_id);

    @FormUrlEncoded
    @POST(ApiEndPoint.JOIN_CATEGORIES)
    Single<InsertGroupPOJO> joinCategories(@Field("user_id") String user_id,
                                              @Field("group_ids") String group_ids);

    @FormUrlEncoded
    @POST(ApiEndPoint.UPDATE_USER_TIME)
    Single<SuccessResponse> updateTimeOnline(@Field("token") String token,
                                              @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ApiEndPoint.UPDATE_ADULT)
    Single<SuccessResponse> postMakeAdult(@Field("id_posts") String id_posts,
                                              @Field("adult_filter") String adult_filter);


    @FormUrlEncoded
    @POST(ApiEndPoint.POST_UPDATE_POST)
    Single<SuccessResponse> updatePost(@Field("id_posts") String id_posts,
                                          @Field("action") String action,
                                       @Field("text_status") String text_status);


    @FormUrlEncoded
    @POST(ApiEndPoint.POST_OWNER_NOTIFICATION)
    Single<SuccessResponse> post_owner_notification(@Field("id_user_name") String id_user_name,
                                          @Field("id_posts") String id_posts,
                                       @Field("token") String token);


    @FormUrlEncoded
    @POST(ApiEndPoint.DELETE_COMMENTS)
    Single<UserResponse> deleteComment(@Field("id_post_comments") String id_post_comments,
                                          @Field("user_id") String user_id,
                                       @Field("id_posts") String id_posts);


    @FormUrlEncoded
    @POST(ApiEndPoint.DELETE_COMMENTS_REPLY)
    Single<UserResponse> deleteCommentReply(@Field("id_post_comment_reply") String id_post_comment_reply,
                                          @Field("user_id") String user_id,
                                       @Field("id_posts") String id_posts);

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_COMMENTS_REPLY)
    Single<List<PostUserCommentModel>> getCommentsReply(@Field("id_posts") String id_posts,
                                                        @Field("user_id") String user_id,
                                                        @Field("token") String token);


    @FormUrlEncoded
    @POST(ApiEndPoint.POST_STATUS)
    Single<UserResponse> postStatus(@Field("user_id") String user_id,
                                    @Field("post_text") String post_text,
                                    @Field("image_url") String image_url,
                                    @Field("group_id") String group_id,
                                    @Field("cat_id") String cat_id,
                                    @Field("feeling_id") String feeling_id,
                                    @Field("type") String type,
                                    @Field("adult_filter") String adult_filter);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_STATUS_PENDING)
    Single<UserResponse> postStatusPending(@Field("user_id") String user_id,
                                    @Field("post_text") String post_text,
                                    @Field("image_url") String image_url,
                                    @Field("group_id") String group_id,
                                    @Field("cat_id") String cat_id,
                                    @Field("feeling_id") String feeling_id,
                                    @Field("type") String type,
                                    @Field("adult_filter") String adult_filter);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_COMMENTS)
    Single<List<PostUserCommentModel>> post_comments(@Field("id_user_name") String id_user_name,
                                    @Field("id_post_user_name") String id_post_user_name,
                                    @Field("id_posts") String id_posts,
                                    @Field("message") String message,
                                    @Field("token") String token,
                                    @Field("message_image") String message_image,
                                    @Field("type") String type);

    @FormUrlEncoded
    @POST(ApiEndPoint.INSERT_CHAT)
    Single<SuccessResponseChat> sendTextNotification(@Field("senderId") String senderId,
                                    @Field("receiverId") String receiverId,
                                    @Field("chatText") String chatText,
                                    @Field("chatImage") String chatImage,
                                    @Field("receiverAnonymous") String receiverAnonymous,
                                    @Field("id_posts") String id_posts,
                                    @Field("token") String token);

    @FormUrlEncoded
    @POST(ApiEndPoint.POST_COMMENT_REPLY)
    Single<List<PostUserCommentModel>> postCommentReply(
                                    @Field("id_post_comments") String id_post_comments,
                                    @Field("id_user_name") String id_user_name,
                                    @Field("id_post_user_name") String id_post_user_name,
                                    @Field("id_posts") String id_posts,
                                    @Field("message") String message,
                                    @Field("token") String token,
                                    @Field("message_image") String message_image,
                                    @Field("type") String type);
    @FormUrlEncoded
    @POST(ApiEndPoint.POST_COMMENT_REPLY_REPLY)
    Single<List<PostUserCommentModel>> postCommentReplyReply(
                                    @Field("id_post_comments") String id_post_comments,
                                    @Field("id_post_comments_reply") String id_post_comments_reply,
                                    @Field("id_user_name") String id_user_name,
                                    @Field("id_post_user_name") String id_post_user_name,
                                    @Field("id_posts") String id_posts,
                                    @Field("message") String message,
                                    @Field("token") String token,
                                    @Field("message_image") String message_image,
                                    @Field("type") String type);



    @GET(ApiEndPoint.GET_AGE_UPDATE)
    Single<SuccessResponse> sendAgeOnlineApi(@Query("age") String age,
                                               @Query("id_user_name") String id_user_name);


    @GET(ApiEndPoint.GET_FACEBOOK_FRIENDS)
    Single<MainResponse> getFacebookFriends(@Query("access_token") String access_token,
                                            @Query("limit") String limit);

    @GET(ApiEndPoint.GET_FACEBOOK_FRIENDS)
    Single<MainResponse> getFacebookFriends02(@Query("access_token") String access_token,
                                        @Query("limit")String limit,
                                        @Query("after")String after);

    @GET(ApiEndPoint.GET_GENDER_UPDATE)
    Single<SuccessResponse> sendGenderOnlineApi(@Query("userId") String userId,
                                        @Query("gender")String gender);


    @GET(ApiEndPoint.POST_SAVE_TOKEN)
    Single<SuccessResponse> postSaveToken(@Query("id_user_name") String id_user_name,
                                        @Query("pushnotificationToken")String pushnotificationToken);

    @GET(ApiEndPoint.GET_ALL_CATEGORIES)
    Single<List<CategoryJoined>> getAllCategory(@Query("user_id") String user_id);


     @GET(ApiEndPoint.GET_GROUPS_SPECIFIC)
     Single<List<CommunityGroupPojo>> getGroupSpecific(@Query("group_id") String group_id,
                                                       @Query("user_id")String user_id,
                                                       @Query("page") String page);


     @GET(ApiEndPoint.GET_ALL_GROUPS)
    Single<List<CommunityGroupPojoNew>> getAllCommunityGroup(@Query("user_id") String user_id,
                                                       @Query("below18")String below18,
                                                       @Query("joined") String joined,
                                                             @Query("page") String page);

     @GET(ApiEndPoint.GET_ALL_GROUPS_LATEST)
     Single<List<CommunityGroupPojoNew>> getAllLatestCommunityGroup(@Query("user_id") String user_id,
                                                       @Query("below18")String below18,
                                                       @Query("joined") String joined,
                                                             @Query("page") String page);

     @GET(ApiEndPoint.GET_CHECK_GROUP_NAME)
     Single<SuccessResponse> get_group_name_check(@Query("group_name") String group_name);

     @GET(ApiEndPoint.SAMPLE)
     Single<SuccessResponse> samplenetwork(@Query("one") String one,
                                           @Query("two") String two);

     @GET(ApiEndPoint.TEST)
     Single<SuccessResponse> getAuthorisedAccess(@Query("access") String access,
                                           @Query("user_id") String user_id);

     @GET(ApiEndPoint.GET_CHECK_USER_NAME)
     Single<SuccessResponse> get_username_check(@Query("group_name") String group_name,
                                           @Query("id_user_name") String id_user_name);


     @GET(ApiEndPoint.GET_SEARCH_GROUP)
     Single<List<CommunityGroupPojo>> getSearchGroup(@Query("search_word") String search_word,
                                           @Query("user_id") String user_id);


     @GET(ApiEndPoint.GET_POST_DATA)
     Single<List<PostsModel>> getLatestPosts(@Query("user_id") String user_id,
                                           @Query("page") String page);


     @GET(ApiEndPoint.GET_NOTIFICATION_DATA)
     Single<List<NotificationPojo>> getNotificationData(@Query("user_id") String user_id,
                                                       @Query("page") String page);


     @GET(ApiEndPoint.GET_ALL_CHAT_NEW)
     Single<List<Dialog>> getAllChats(@Query("user_one") String user_one,
                                      @Query("token") String token);


     @GET(ApiEndPoint.GET_SINGLE_POST)
     Single<List<PostsModel>> getSinglePosts(@Query("id_posts") String id_posts,
                                      @Query("user_id") String user_id);


     @GET(ApiEndPoint.POST_BLOCK_USER)
     Single<UserResponse> blockUserInsert(@Query("currentUserId") String currentUserId,
                                                       @Query("userToBeBlockedId") String userToBeBlockedId);

     @GET(ApiEndPoint.GET_LIST_ALL_GROUPS)
     Single<List<GroupUser>> getListAllGroups(@Query("user_id") String user_id);


     @GET(ApiEndPoint.DELETE_PENDING_POST)
     Single<SuccessResponse> deletePendingPosts(@Query("id_posts") String id_posts);


     @GET(ApiEndPoint.GET_GROUPS_JOINED)
     Single<List<GroupUser>> getJoinedGroups(@Query("user_id") String user_id);


     @GET(ApiEndPoint.GET_USER_PROFILE)
     Single<ProfileMain> getUserProfile(@Query("user_id") String user_id);


     @GET(ApiEndPoint.DELETE_CHAT)
     Single<SuccessResponse> deleteChat(@Query("id_conversation_reply") String id_conversation_reply);


     @GET(ApiEndPoint.POST_DELETE_ALL_CHAT)
     Single<SuccessResponse> deleteEntireChat(@Query("id_conversation") String id_conversation);


     @GET(ApiEndPoint.GET_POST_DATA)
     Single<List<PostsModel>> getHistoryPosts(@Field("id_user_name") String id_user_name,
                                             @Field("user_id") String user_id,
                                             @Field("group_post") String group_post,
                                             @Field("page") String page);


     @GET(ApiEndPoint.SEND_LIKE)
     Single<PostLikesResponse> sendLike(@Field("user_id") String user_id,
                                        @Field("post_id") String post_id,
                                        @Field("like") int like,
                                        @Field("token") String token);



     @GET(ApiEndPoint.SEND_LIKE)
     Single<PostLikesResponse> sendSad(@Field("user_id") String user_id,
                                        @Field("post_id") String post_id,
                                        @Field("hug") int hug,
                                        @Field("token") String token);


     @GET(ApiEndPoint.POST_NEW_CUSTOM_NAME)
     Single<SuccessResponse> sendCustomNameInsert(@Field("id_conversation") String id_conversation,
                                             @Field("user_id") String user_id,
                                             @Field("username") String username,
                                             @Field("avatar_url") String avatar_url);


     @GET(ApiEndPoint.GET_POST_DATA)
     Single<List<PostsModel>> getFacebookFriendsFeed(@Field("id_user_name") String id_user_name,
                                             @Field("user_id") String user_id,
                                             @Field("facebookId") String facebookId,
                                             @Field("page") String page);

     @GET(ApiEndPoint.GET_POST_DATA)
     Single<List<PostsModel>> getUserFeed(@Field("id_user_name") String id_user_name,
                                             @Field("user_id") String user_id,
                                             @Field("filtered") String filtered,
                                             @Field("page") String page);


     @GET(ApiEndPoint.GET_POST_DATA)
     Single<List<PostsModel>> getSearchPost(@Field("id_user_name") String id_user_name,
                                             @Field("search_word") String search_word,
                                             @Field("premium") String premium,
                                             @Field("page") String page);


     @GET(ApiEndPoint.GET_POST_DATA)
     Single<List<PostsModel>> getAgeGenderPost(@Field("id_user_name") String id_user_name,
                                             @Field("gender") String gender,
                                             @Field("user_date_of_birth") String user_date_of_birth,
                                             @Field("premiumuser") String premiumuser,
                                             @Field("page") String page);


     @GET(ApiEndPoint.GET_CHAT_MESSAGES)
     Single<List<Message>> getChatMessages(@Field("user_one") String user_one,
                                           @Field("user_two") String user_two,
                                           @Field("id_posts") String id_posts,
                                           @Field("token") String token,
                                           @Field("page") String page);


     @GET(ApiEndPoint.GET_POST_DATA)
     Single<List<PostsModel>> getGroupPosts(@Field("group_id") String group_id,
                                             @Field("user_id") String user_id,
                                             @Field("page") String page);

     @GET(ApiEndPoint.GET_POST_DATA)
     Single<List<PostsModel>> getSingleUserPosts(@Field("id_user") String id_user,
                                             @Field("user_id") String user_id,
                                             @Field("page") String page);


     @GET(ApiEndPoint.GET_POST_DATA)
     Single<List<PostsModel>> getPopularPosts(@Field("user_id") String user_id,
                                             @Field("popular") String popular,
                                             @Field("page") String page);


     @GET(ApiEndPoint.GET_POST_DATA_PENDING)
     Single<List<PostsModel>> getPendingPosts(@Field("user_id") String user_id,
                                             @Field("pendingPost") String pendingPost,
                                             @Field("page") String page);


     @GET(ApiEndPoint.GET_POST_DATA)
     Single<List<PostsModel>> getImagePosts(@Field("user_id") String user_id,
                                             @Field("onlyImages") String onlyImages,
                                             @Field("page") String page);


    @GET(ApiEndPoint.GET_HUGS_SINGLE)
    Single<PostLikesResponse> post_sad(@Field("user_id") String user_id,
                                       @Field("post_id") String post_id,
                                       @Field("hug") String hug,
                                       @Field("token") String token);

     //  @GET("search/movie")
    // Observable<MovieResponse> getMoviesBasedOnQuery(@Query("api_key") String api_key, @Query("query") String q);

}
