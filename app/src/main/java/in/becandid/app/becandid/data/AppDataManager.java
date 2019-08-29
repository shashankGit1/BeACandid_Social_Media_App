/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package in.becandid.app.becandid.data;


import android.content.Context;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.data.network.ApiHeader;
import in.becandid.app.becandid.data.network.ApiHelper;
import in.becandid.app.becandid.data.prefs.PreferencesHelper;
import in.becandid.app.becandid.di.ApplicationContext;
import in.becandid.app.becandid.dto.CategoryJoined;
import in.becandid.app.becandid.dto.ContactAddResponse;
import in.becandid.app.becandid.dto.ImageUrl;
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

/**
 * Created by janisharali on 27/01/17.
 */

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;


    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Single<SuccessResponse> samplenetwork(String one, String two) {
        return mApiHelper.samplenetwork(one, two);
    }
        @Override
    public Single<List<PostsModel>> getImagePosts(String user_id, String onlyImages, String page) {
        return mApiHelper.getImagePosts(user_id, onlyImages, page);
    }

    @Override
    public Single<SuccessResponse> getAuthorisedAccess(String access, String user_id) {
        return mApiHelper.getAuthorisedAccess(access, user_id);
    }

    @Override
    public Single<SuccessResponse> deletePendingPosts(String id_posts) {
        return mApiHelper.deletePendingPosts(id_posts);
    }

    @Override
    public Single<List<PostsModel>> getPendingPosts(String user_id, String pendingPost, String page) {
        return mApiHelper.getPendingPosts(user_id, pendingPost, page);
    }

    @Override
    public Single<SuccessResponse> post_owner_notification(String id_user_name, String id_posts, String token) {
        return mApiHelper.post_owner_notification(id_user_name, id_posts, token);
    }

    @Override
    public Single<SuccessResponse> post_comment_owner_notification(String id_user_name, String id_post_comments, String id_posts, String token) {
        return mApiHelper.post_comment_owner_notification(id_user_name, id_post_comments, id_posts, token);
    }

    @Override
    public Single<SuccessResponse> post_comment_owner_reply_notification(String id_user_name, String id_post_comments_reply, String id_posts, String token) {
        return mApiHelper.post_comment_owner_reply_notification(id_user_name, id_post_comments_reply, id_posts, token);
    }

    @Override
    public Single<SuccessResponse> editGroup(String id_categories, String name, String group_description, String group_id) {
        return mApiHelper.editGroup(id_categories, name, group_description, group_id);
    }

    @Override
    public Single<SuccessResponse> editGroup(String id_categories, String name, String group_image_url, String group_description, String group_id) {
        return mApiHelper.editGroup(id_categories, name, group_image_url, group_description, group_id);
    }

    @Override
    public Single<List<PostsModel>> getSearchPost(String id_user_name, String search_word, String premium, String page) {
        return mApiHelper.getSearchPost(id_user_name, search_word, premium, page);
    }

    @Override
    public Single<List<PostsModel>> getAgeGenderPost(String id_user_name, String gender, String user_date_of_birth, String premiumuser, String page) {
        return mApiHelper.getAgeGenderPost(id_user_name, gender, user_date_of_birth, premiumuser, page);
    }

    @Override
    public Single<LoginResponse> doLoginResponse(String name, String email, String userId, String deviceId, String socialNetwork) {
        return mApiHelper.doLoginResponse(name, email, userId, deviceId, socialNetwork);
    }

    @Override
    public Single<SuccessResponse> updatePost(String id_posts, String action, String text_status) {
        return mApiHelper.updatePost(id_posts, action, text_status);
    }

    @Override
    public Single<SuccessResponse> reportAbuse(String id_user_name, String sender_user_id, String id_posts, String token, String message) {
        return mApiHelper.reportAbuse(id_user_name, sender_user_id,id_posts,token,message);
    }

    @Override
    public Single<InsertGroupPOJO> joinCategories(String user_id, String group_ids) {
        return mApiHelper.joinCategories(user_id, group_ids);
    }

    @Override
    public Single<LoginResponse> skipUser(String deviceId, String socialNetwork) {
        return mApiHelper.skipUser(deviceId, socialNetwork);
    }

    @Override
    public Single<List<GroupUser>> getJoinedGroups(String user_id) {
        return mApiHelper.getJoinedGroups(user_id);
    }

    @Override
    public Single<List<Message>> getChatMessages(String from_user_id, String user_id, String to_user_id, String token, String page) {
        return mApiHelper.getChatMessages(from_user_id, user_id, to_user_id, token, page);
    }

   /* @Override
    public Single<List<Message>> getChatMessages02(String from_user_id, String user_id, String to_user_id, String token, String page) {
        return mApiHelper.getChatMessages02(from_user_id, user_id, to_user_id, token, page);
    }
    */

    @Override
    public Single<SuccessResponse> deleteChat(String messageId) {
        return mApiHelper.deleteChat(messageId);
    }

    @Override
    public Single<MainResponse> getFacebookFriends(String access_token, String limit) {
        return mApiHelper.getFacebookFriends(access_token, limit);
    }

    @Override
    public Single<MainResponse> getFacebookFriends02(String access_token, String limit, String after) {
        return mApiHelper.getFacebookFriends02(access_token, limit, after);
    }

    @Override
    public Single<SuccessResponse> sendGenderOnlineApi(String userId, String gender) {
        return mApiHelper.sendGenderOnlineApi(userId, gender);
    }

    @Override
    public Single<List<CommunityGroupPojo>> getSearchGroup(String search_word, String user_id) {
        return mApiHelper.getSearchGroup(search_word, user_id);
    }

    @Override
    public Single<SuccessResponse> postNewSettings(String age_range, String gender, String adult_filter, String block_premium_search, String id_user_name) {
        return mApiHelper.postNewSettings(age_range, gender, adult_filter,block_premium_search,id_user_name);
    }

    @Override
    public Single<UserResponse> postStatus(String user_id, String post_text, String image_url, String group_id, String cat_id, String feeling_id, String type, String adult_filter) {
        return mApiHelper.postStatus(user_id, post_text,image_url,group_id,cat_id,feeling_id,type,adult_filter);
    }

    @Override
    public Single<LoginResponse> sendJoinedGroupsOnlineApi(String id_user_name, String facebook_id) {
        return mApiHelper.sendJoinedGroupsOnlineApi(id_user_name, facebook_id);
    }

    @Override
    public Single<List<PostsModel>> getGroupPosts(String group_id, String user_id, String page) {
        return mApiHelper.getGroupPosts(group_id, user_id, page);
    }

    @Override
    public Single<InsertGroupPOJO> sendUnJoinGroup(String group_ids, String user_id) {
        return mApiHelper.sendUnJoinGroup(group_ids, user_id);
    }

    @Override
    public Single<SuccessResponse> sendAgeOnlineApi(String age, String id_user_name) {
        return mApiHelper.sendAgeOnlineApi(age, id_user_name);
    }

    @Override
    public Single<List<CommunityGroupPojo>> getGroupSpecific(String group_id, String user_id, String page) {
        return mApiHelper.getGroupSpecific(group_id, user_id, page);
    }

    @Override
    public Single<SuccessResponse> postCommentReplyLike(String id_post_comment_reply, String id_user_name, String likes, String id_posts, String token) {
        return mApiHelper.postCommentReplyLike(id_post_comment_reply, id_user_name, likes, id_posts, token);
    }

    @Override
    public Single<SuccessResponse> postCommentLike(String id_post_comment, String id_user_name, String id_posts, String comment_likes, String token) {
        return mApiHelper.postCommentLike(id_post_comment, id_user_name, id_posts, comment_likes, token);
    }

    @Override
    public Single<InsertGroupPOJO> sendJoinGroup(String group_ids, String user_id) {
        return mApiHelper.sendJoinGroup(group_ids, user_id);
    }

    @Override
    public Single<SuccessResponse> postSaveToken(String id_user_name, String pushnotificationToken) {
        return mApiHelper.postSaveToken(id_user_name, pushnotificationToken);
    }

    @Override
    public Single<List<CategoryJoined>> getAllCategory(String user_id) {
        return mApiHelper.getAllCategory(user_id);
    }

    @Override
    public Single<List<GroupUser>> getListAllGroups(String user_id) {
        return mApiHelper.getListAllGroups(user_id);
    }

    @Override
    public Single<List<CommunityGroupPojoNew>> getAllCommunityGroup(String user_id, String below18, String joined, String page) {
        return mApiHelper.getAllCommunityGroup(user_id, below18, joined, page);
    }

    @Override
    public Single<List<PostsModel>> getHistoryPosts(String id_user_name, String user_id, String group_post, String page) {
        return mApiHelper.getHistoryPosts(id_user_name, user_id, group_post, page);
    }

    @Override
    public Single<SuccessResponse> get_group_name_check(String group_name) {
        return mApiHelper.get_group_name_check(group_name);
    }

    @Override
    public Single<List<PostsModel>> getLatestPosts(String user_id, String page) {
        return mApiHelper.getLatestPosts(user_id, page);
    }

    @Override
    public Single<List<PostsModel>> getSingleUserPosts(String id_user, String user_id, String page) {
        return mApiHelper.getSingleUserPosts(id_user, user_id, page);
    }

    @Override
    public Single<List<PostsModel>> getPopularPosts(String user_id, String popular, String page) {
        return mApiHelper.getPopularPosts(user_id, popular, page);
    }

    @Override
    public Single<List<PostsModel>> getUserFeed(String id_user_name, String user_id, String filtered, String page) {
        return mApiHelper.getUserFeed(id_user_name, user_id, filtered, page);
    }

    @Override
    public Single<ContactAddResponse> sendFacebookFriends(String id_user_name, String facebook_id) {
        return mApiHelper.sendFacebookFriends(id_user_name, facebook_id);
    }

    @Override
    public Single<List<PostsModel>> getFacebookFriendsFeed(String id_user_name, String user_id, String facebookId, String page) {
        return mApiHelper.getFacebookFriendsFeed(id_user_name, user_id, facebookId, page);
    }

    @Override
    public Single<List<NotificationPojo>> getNotificationData(String id_user_name, String page) {
        return mApiHelper.getNotificationData(id_user_name, page);
    }

    @Override
    public Single<ImageUrl> postImageUpload(File file) {
        return mApiHelper.postImageUpload(file);
    }

    @Override
    public Single<SuccessResponse> sendCustomNameInsert(String id_conversation, String user_id, String username, String avatar_url) {
        return mApiHelper.sendCustomNameInsert(id_conversation, user_id, username, avatar_url);
    }

    @Override
    public Single<SuccessResponse> deleteEntireChat(String id_conversation) {
        return mApiHelper.deleteEntireChat(id_conversation);
    }

    @Override
    public Single<UserResponse> deleteCommentReply(String id_post_comment_reply, String user_id, String id_posts) {
        return mApiHelper.deleteCommentReply(id_post_comment_reply, user_id, id_posts);
    }

    @Override
    public Single<List<PostUserCommentModel>> postCommentReply(String id_post_comments, String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type) {
        return mApiHelper.postCommentReply(id_post_comments, id_user_name, id_post_user_name,id_posts,message,token, message_image, type);
    }

    @Override
    public Single<SuccessResponseChat> sendTextNotification(String senderId, String receiverId, String chatText, String chatImage, String receiverAnonymous, String id_posts, String token) {
        return mApiHelper.sendTextNotification(senderId, receiverId, chatText,chatImage,receiverAnonymous,id_posts,token);
    }

    @Override
    public Single<ProfileMain> getUserProfile(String user_id) {
        return mApiHelper.getUserProfile(user_id);
    }

    @Override
    public Single<PostLikesResponse> post_likes(String user_id, String post_id, String like, String token) {
        return mApiHelper.post_likes(user_id, post_id, like, token);
    }

    @Override
    public Single<PostLikesResponse> post_sad(String user_id, String post_id, String hug, String token) {
        return mApiHelper.post_sad(user_id, post_id, hug, token);
    }

    @Override
    public Single<UserResponse> deleteComment(String id_post_comments, String user_id, String id_posts) {
        return mApiHelper.deleteComment(id_post_comments, user_id, id_posts);
    }

    @Override
    public Single<UserResponse> blockUserInsert(String currentUserId, String userToBeBlockedId) {
        return mApiHelper.blockUserInsert(currentUserId, userToBeBlockedId);
    }

    @Override
    public Single<SuccessResponse> postMakeAdult(String id_posts, String adult_filter) {
        return mApiHelper.postMakeAdult(id_posts, adult_filter);
    }

    @Override
    public Single<SuccessResponse> updateTimeOnline(String token, String user_id) {
        return mApiHelper.updateTimeOnline(token, user_id);
    }

    @Override
    public Single<List<Dialog>> getAllChats(String user_one, String token) {
        return mApiHelper.getAllChats(user_one, token);
    }

    @Override
    public Single<List<PostsModel>> getSinglePosts(String id_posts, String user_id) {
        return mApiHelper.getSinglePosts(id_posts, user_id);
    }

    @Override
    public Single<List<CommunityGroupPojoNew>> getAllLatestCommunityGroup(String user_id, String below18, String joined, String page) {
        return mApiHelper.getAllLatestCommunityGroup(user_id, below18, joined, page);
    }

    @Override
    public Single<List<PostUserCommentModel>> postCommentReplyReply(String id_post_comments, String id_post_comments_reply, String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type) {
        return mApiHelper.postCommentReplyReply(id_post_comments, id_post_comments_reply, id_user_name, id_post_user_name, id_posts, message, token, message_image, type);
    }

    @Override
    public Single<SuccessResponse> get_username_check(String group_name, String id_user_name) {
        return mApiHelper.get_username_check(group_name, id_user_name);
    }

    @Override
    public Single<UserResponse> postStatusPending(String user_id, String post_text, String image_url, String group_id, String cat_id, String feeling_id, String type, String adult_filter) {
        return mApiHelper.postStatusPending(user_id, post_text, image_url, group_id, cat_id, feeling_id, type, adult_filter);
    }

    @Override
    public Single<PostLikesResponse> sendLike(String user_id, String post_id, int like, String token) {
        return mApiHelper.sendLike(user_id, post_id, like, token);
    }

    @Override
    public Single<GroupsCreatePOJO> postInsertGroup(String id_categories, String user_id, String group_name, String group_image, String group_description) {
        return mApiHelper.postInsertGroup(id_categories, user_id, group_name, group_image, group_description);
    }

    @Override
    public Single<PostLikesResponse> sendSad(String user_id, String post_id, int hug, String token) {
        return mApiHelper.sendSad(user_id, post_id, hug, token);
    }

    @Override
    public Single<List<PostUserCommentModel>> post_comments(String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type) {
        return mApiHelper.post_comments(id_user_name, id_post_user_name, id_posts, message,token, message_image, type);
    }

    @Override
    public Single<List<PostUserCommentModel>> getCommentsReply(String id_posts, String user_id, String token) {
        return mApiHelper.getCommentsReply(id_posts, user_id, token);
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {

    }

    @Override
    public void setUserAsLoggedOut() {

    }

    @Override
    public void updateUserInfo(String userId, String user_token, LoggedInMode loggedInMode) {

    }

    @Override
    public void registerUserId(String userId) {

    }

    @Override
    public String getUserId() {
        return mPreferencesHelper.getUserId();
    }

    @Override
    public void registerFirebaseToken(String token) {

    }

    @Override
    public String getFirebaseToken() {
        return mPreferencesHelper.getFirebaseToken();
    }

    /*
    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }


    @Override
    public Observable<Long> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest
                                                              request) {
        return mApiHelper.doGoogleLoginApiCall(request);
    }

    @Override
    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest
                                                                request) {
        return mApiHelper.doFacebookLoginApiCall(request);
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest
                                                              request) {
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return mApiHelper.doLogoutApiCall();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setUserId(userId);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath) {

        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);

        updateApiHeader(userId, accessToken);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null);
    }

    @Override
    public Observable<Boolean> isQuestionEmpty() {
        return mDbHelper.isQuestionEmpty();
    }

    @Override
    public Observable<Boolean> isOptionEmpty() {
        return mDbHelper.isOptionEmpty();
    }

    @Override
    public Observable<Boolean> saveQuestion(Question question) {
        return mDbHelper.saveQuestion(question);
    }

    @Override
    public Observable<Boolean> saveOption(Option option) {
        return mDbHelper.saveOption(option);
    }

    @Override
    public Observable<Boolean> saveQuestionList(List<Question> questionList) {
        return mDbHelper.saveQuestionList(questionList);
    }

    @Override
    public Observable<Boolean> saveOptionList(List<Option> optionList) {
        return mDbHelper.saveOptionList(optionList);
    }

    @Override
    public Observable<List<Question>> getAllQuestions() {
        return mDbHelper.getAllQuestions();
    }

    @Override
    public Observable<Boolean> seedDatabaseQuestions() {

        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        return mDbHelper.isQuestionEmpty()
                .concatMap(new Function<Boolean, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(Boolean isEmpty)
                            throws Exception {
                        if (isEmpty) {
                            Type type = $Gson$Types
                                    .newParameterizedTypeWithOwner(null, List.class,
                                            Question.class);
                            List<Question> questionList = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DATABASE_QUESTIONS),
                                    type);

                            return saveQuestionList(questionList);
                        }
                        return Observable.just(false);
                    }
                });
    }

    @Override
    public Observable<Boolean> seedDatabaseOptions() {

        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        return mDbHelper.isOptionEmpty()
                .concatMap(new Function<Boolean, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(Boolean isEmpty)
                            throws Exception {
                        if (isEmpty) {
                            Type type = new TypeToken<List<Option>>() {
                            }
                                    .getType();
                            List<Option> optionList = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DATABASE_OPTIONS),
                                    type);

                            return saveOptionList(optionList);
                        }
                        return Observable.just(false);
                    }
                });
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return mApiHelper.getBlogApiCall();
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return mApiHelper.getOpenSourceApiCall();
    }
    */
}
