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

package in.becandid.app.becandid.data.network;


import com.androidnetworking.common.Priority;
import com.google.android.gms.security.ProviderInstaller;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import in.becandid.app.becandid.autocomplete.GroupUser;
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
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by janisharali on 28/01/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    // LOGIN REQUEST
    @Override
    public Single<SuccessResponse> editGroup(String id_categories, String name, String group_description, String group_id) {
        return Rx2AndroidNetworking.post(ApiEndPoint.UPDATE_GROUP)
               // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_categories", id_categories)
                .addBodyParameter("name", name)
                .addBodyParameter("group_description", group_description)
                .addBodyParameter("group_id", group_id)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    @Override
    public Single<SuccessResponse> editGroup(String id_categories, String name, String group_image_url, String group_description, String group_id) {
        return Rx2AndroidNetworking.post(ApiEndPoint.UPDATE_GROUP)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_categories", id_categories)
                .addBodyParameter("name", name)
                .addBodyParameter("group_image_url", group_image_url)
                .addBodyParameter("group_description", group_description)
                .addBodyParameter("group_id", group_id)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }


    @Override
    public Single<LoginResponse> doLoginResponse(String name, String email, String user_id, String deviceId, String socialNetwork) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_LOGIN)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("name", name)
                .addBodyParameter("email", email)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("deviceId", deviceId)
                .addBodyParameter("socialNetwork", socialNetwork)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    // LOGIN REQUEST
    @Override
    public Single<LoginResponse> skipUser(String deviceId, String socialNetwork) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_LOGIN)
                // .addHeaders(mApiHeader.getPublicApiHeader())

                .addBodyParameter("deviceId", deviceId)
                .addBodyParameter("socialNetwork", socialNetwork)
                .build()
<<<<<<< HEAD
                .getObjectSingle(LoginResponse.class); */

        return  NetworkClient.getRetrofit().create(NetworkInterface.class)
                .skipUser(deviceId, socialNetwork)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
       // RetrofitFactory.getInstance().toSubscribe(observable, subscriber);

=======
                .getObjectSingle(LoginResponse.class);
>>>>>>> 38da18eb4dfb85395eb1d474ba9456ef2bbbff10
    }

    @Override
    public Single<SuccessResponse> reportAbuse(String id_user_name, String sender_user_id,String id_posts, String token, String message) {

        return Rx2AndroidNetworking.post(ApiEndPoint.POST_REPORT_ABUSE)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_user_name", id_user_name)
                .addBodyParameter("sender_user_id", sender_user_id)
                .addBodyParameter("id_posts", id_posts)
                .addBodyParameter("token", token)
                .addBodyParameter("message", message)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }


    //
// get first page facebook friends online
    @Override
    public Single<MainResponse> getFacebookFriends(String access_token, String limit) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_FACEBOOK_FRIENDS)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("access_token", access_token)
                .addQueryParameter("limit", limit)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(MainResponse.class);
    }


    // get first page facebook friends online
    @Override
    public Single<MainResponse> getFacebookFriends02(String access_token, String limit, String after) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_FACEBOOK_FRIENDS)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("access_token", access_token)
                .addQueryParameter("limit", limit)
                .addQueryParameter("after", after)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(MainResponse.class);
    }

// post facebook friends to database

    @Override
    public Single<ContactAddResponse> sendFacebookFriends(String id_user_name, String facebook_id) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_SEND_FRIENDS_LIST)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_user_name", id_user_name)
                .addBodyParameter("facebook_id", facebook_id)
                .build()
                .getObjectSingle(ContactAddResponse.class);
    }


    // send gender online
    @Override
    public Single<SuccessResponse> sendGenderOnlineApi(String userId, String gender) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_GENDER_UPDATE)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("id_user_name", userId)
                .addQueryParameter("gender", gender)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    // send gender online
    @Override
    public Single<SuccessResponse> sendAgeOnlineApi(String age, String id_user_name) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_AGE_UPDATE)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("age", age)
                .addQueryParameter("id_user_name", id_user_name)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    // post join group
    @Override
    public Single<InsertGroupPOJO> sendJoinGroup(String group_ids, String user_id) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_JOIN_GROUPS)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("group_ids", group_ids)
                .addBodyParameter("user_id", user_id)
                .build()
                .getObjectSingle(InsertGroupPOJO.class);
    }

    @Override
    public Single<InsertGroupPOJO> sendUnJoinGroup(String group_ids, String user_id) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_UNJOIN_GROUPS)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("group_ids", group_ids)
                .addBodyParameter("user_id", user_id)
                .build()
                .getObjectSingle(InsertGroupPOJO.class);
    }

    // post join group
    @Override
    public Single<SuccessResponse> postCommentLike(String id_post_comment, String id_user_name, String id_posts, String comment_likes, String token) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_COMMENT_LIKE)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_post_comment", id_post_comment)
                .addBodyParameter("id_user_name", id_user_name)
                .addBodyParameter("id_posts", id_posts)
                .addBodyParameter("comment_likes", comment_likes)
                .addBodyParameter("token", token)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }


    // post join group
    @Override
    public Single<SuccessResponse> postCommentReplyLike(String id_post_comment_reply, String id_user_name, String likes, String id_posts, String token) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_COMMENT_REPLY_LIKE)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_post_comment_reply", id_post_comment_reply)
                .addBodyParameter("id_user_name", id_user_name)
                .addBodyParameter("likes", likes)
                .addBodyParameter("id_posts", id_posts)
                .addBodyParameter("token", token)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    @Override
    public Single<SuccessResponse> postSaveToken(String id_user_name, String pushnotificationToken) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_SAVE_TOKEN)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_user_name", id_user_name)
                .addBodyParameter("pushnotificationToken", pushnotificationToken)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    @Override
    public Single<GroupsCreatePOJO> postInsertGroup(String id_categories, String user_id, String group_name, String group_image, String group_description) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_INSERT_GROUP)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_categories", id_categories)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("group_name", group_name)
                .addBodyParameter("group_image", group_image)
                .addBodyParameter("group_description", group_description)
                .build()
                .getObjectSingle(GroupsCreatePOJO.class);
    }

    @Override
    public Single<SuccessResponse> postNewSettings(String age_range, String gender, String adult_filter, String block_premium_search, String id_user_name) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_NEW_SETTINGS)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("age_range", age_range)
                .addBodyParameter("gender", gender)
                .addBodyParameter("adult_filter", adult_filter)
                .addBodyParameter("block_premium_search", block_premium_search)
                .addBodyParameter("id_user_name", id_user_name)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    @Override
    public Single<ImageUrl> postImageUpload(File file) {
        return Rx2AndroidNetworking.upload(ApiEndPoint.POST_IMAGE_UPLOAD)
                .addMultipartFile("file", file)
                //      .addHeaders("Accept", "application/json")
                .addHeaders("Content-Type", "multipart/form-data")
                .setContentType("form-data")
                .addHeaders("Accept: multipart/form-data")
                //     .setContentType("Multipart")
                //  .addMultipartParameter("key", "file")
                .setPriority(Priority.HIGH)
                .build()
                /*  .setUploadProgressListener(new UploadProgressListener() {
                      @Override
                      public void onProgress(long bytesUploaded, long totalBytes) {}
                  }) */
                .getObjectSingle(ImageUrl.class);
    };

    // get all category group

    @Override
    public Single<List<CategoryJoined>> getAllCategory(String user_id) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_ALL_CATEGORIES)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("user_id", user_id)
                .build()
                .getObjectListSingle(CategoryJoined.class);
    }

    @Override
    public Single<List<CommunityGroupPojo>> getGroupSpecific(String group_id, String user_id, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_GROUPS_SPECIFIC)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("group_id", group_id)
                .addQueryParameter("user_id", user_id)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(CommunityGroupPojo.class);
    }
    @Override
    public Single<SuccessResponse> get_group_name_check(String group_name) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_CHECK_GROUP_NAME)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("group_name", group_name)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    @Override
    public Single<SuccessResponse> samplenetwork(String one, String two) {
        return Rx2AndroidNetworking.get(ApiEndPoint.SAMPLE)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("one", one)
                .addQueryParameter("two", two)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    @Override
    public Single<SuccessResponse> get_username_check(String group_name, String id_user_name) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_CHECK_USER_NAME)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("user_nick_name", group_name)
                .addQueryParameter("id_user_name", id_user_name)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    @Override
    public Single<List<GroupUser>> getListAllGroups(String user_id) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_LIST_ALL_GROUPS)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("user_id", user_id)
                .build()
                .getObjectListSingle(GroupUser.class);
    }

    @Override
    public Single<List<CommunityGroupPojo>> getSearchGroup(String search_word, String user_id) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_SEARCH_GROUP)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("search_word", search_word)
                .addQueryParameter("user_id", user_id)
                .build()
                .getObjectListSingle(CommunityGroupPojo.class);
    }

    // get All community groups
    @Override
    public Single<List<CommunityGroupPojoNew>> getAllCommunityGroup(String user_id, String below18, String joined, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_ALL_GROUPS)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("user_id", user_id)
                .addQueryParameter("below18", below18)
                .addQueryParameter("joined", joined)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(CommunityGroupPojoNew.class);
    }

    @Override
    public Single<List<CommunityGroupPojoNew>> getAllLatestCommunityGroup(String user_id, String below18, String joined, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_ALL_GROUPS_LATEST)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("user_id", user_id)
                .addQueryParameter("below18", below18)
                .addQueryParameter("joined", joined)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(CommunityGroupPojoNew.class);
    }

    @Override
    public Single<SuccessResponse> getAuthorisedAccess(String access, String user_id) {
        return Rx2AndroidNetworking.get(ApiEndPoint.TEST)
                .addHeaders("r", access)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("user_id", user_id)
             /*   .addQueryParameter("below18", below18)
                .addQueryParameter("joined", joined)
                .addQueryParameter("page", page) */
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

// post send join groups

    @Override
    public Single<LoginResponse> sendJoinedGroupsOnlineApi(String id_user_name, String facebook_id) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_JOIN_GROUPS)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_user_name", id_user_name)
                .addBodyParameter("facebook_id", facebook_id)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<SuccessResponse> updatePost(String id_posts, String action, String text_status) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_UPDATE_POST)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_posts", id_posts)
                .addBodyParameter("action", action)
                .addBodyParameter("text_status", text_status)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    @Override
    public Single<InsertGroupPOJO> joinCategories(String user_id, String group_ids) {
        return Rx2AndroidNetworking.post(ApiEndPoint.JOIN_CATEGORIES)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("group_ids", group_ids)
                .build()
                .getObjectSingle(InsertGroupPOJO.class);
    }

// block_user_insert.php


    @Override
    public Single<UserResponse> postStatus(String user_id, String post_text, String image_url, String group_id, String cat_id, String feeling_id, String type, String adult_filter) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_STATUS)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("post_text", post_text)
                .addBodyParameter("image_url", image_url)
                .addBodyParameter("group_id", group_id)
                .addBodyParameter("cat_id", cat_id)
                .addBodyParameter("feeling_id", feeling_id)
                .addBodyParameter("type", type)
                .addBodyParameter("adult_filter", adult_filter)
                .build()
                .getObjectSingle(UserResponse.class);
    }

    @Override
    public Single<UserResponse> postStatusPending(String user_id, String post_text, String image_url, String group_id, String cat_id, String feeling_id, String type, String adult_filter) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_STATUS_PENDING)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("post_text", post_text)
                .addBodyParameter("image_url", image_url)
                .addBodyParameter("group_id", group_id)
                .addBodyParameter("cat_id", cat_id)
                .addBodyParameter("feeling_id", feeling_id)
                .addBodyParameter("type", type)
                .addBodyParameter("adult_filter", adult_filter)
                .build()
                .getObjectSingle(UserResponse.class);
    }

    @Override
    public Single<UserResponse> deleteComment(String id_post_comments, String user_id, String id_posts) {
        return Rx2AndroidNetworking.post(ApiEndPoint.DELETE_COMMENTS)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_post_comments", id_post_comments)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("id_posts", id_posts)
                .build()
                .getObjectSingle(UserResponse.class);
    }


    @Override
    public Single<UserResponse> deleteCommentReply(String id_post_comment_reply, String user_id, String id_posts) {
        return Rx2AndroidNetworking.post(ApiEndPoint.DELETE_COMMENTS_REPLY)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_post_comment_reply", id_post_comment_reply)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("id_posts", id_posts)
                .build()
                .getObjectSingle(UserResponse.class);
    }

    @Override
    public Single<List<PostUserCommentModel>> getCommentsReply(String id_posts, String user_id, String token) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_COMMENTS_REPLY)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_posts", id_posts)
                .addQueryParameter("user_id", user_id)
                .addQueryParameter("token", token)
                .build()
                .getObjectListSingle(PostUserCommentModel.class);
    }

    @Override
    public Single<List<PostUserCommentModel>> post_comments(String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_COMMENTS)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_user_name", id_user_name)
                .addBodyParameter("id_post_user_name", id_post_user_name)
                .addBodyParameter("message_image", message_image)
                .addBodyParameter("id_posts", id_posts)
                .addBodyParameter("message", message)
                .addBodyParameter("type", type)
                .addBodyParameter("token", token)
                .setPriority(Priority.LOW)
                .build()
                .getObjectListSingle(PostUserCommentModel.class);
    }

    @Override
    public Single<List<PostUserCommentModel>> postCommentReply(String id_post_comments, String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_COMMENT_REPLY)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_post_comments", id_post_comments)
                .addBodyParameter("id_user_name", id_user_name)
                .addBodyParameter("id_post_user_name", id_post_user_name)
                .addBodyParameter("id_posts", id_posts)
                .addBodyParameter("message", message)
                .addBodyParameter("message_image", message_image)
                .addBodyParameter("type", type)
                .addBodyParameter("token", token)
                .build()
                .getObjectListSingle(PostUserCommentModel.class);
    }

    @Override
    public Single<List<PostUserCommentModel>> postCommentReplyReply(String id_post_comments, String id_post_comments_reply, String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_COMMENT_REPLY_REPLY)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_post_comments", id_post_comments)
                .addBodyParameter("id_post_comments_reply", id_post_comments_reply)
                .addBodyParameter("id_user_name", id_user_name)
                .addBodyParameter("id_post_user_name", id_post_user_name)
                .addBodyParameter("id_posts", id_posts)
                .addBodyParameter("message", message)
                .addBodyParameter("message_image", message_image)
                .addBodyParameter("type", type)
                .addBodyParameter("token", token)
                .build()
                .getObjectListSingle(PostUserCommentModel.class);
    }

    @Override
    public Single<SuccessResponse> postMakeAdult(String id_posts, String adult_filter) {
        return Rx2AndroidNetworking.post(ApiEndPoint.UPDATE_ADULT)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_posts", id_posts)
                .addBodyParameter("adult_filter", adult_filter)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    @Override
    public Single<SuccessResponse> updateTimeOnline(String token, String user_id) {
        return Rx2AndroidNetworking.post(ApiEndPoint.UPDATE_USER_TIME)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("token", token)
                .addBodyParameter("user_id", user_id)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }


    // GET HISTORY POSTS
    @Override
    public Single<List<PostsModel>> getHistoryPosts(String id_user_name, String user_id, String group_post, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_POST_DATA)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_user_name", id_user_name)
                .addQueryParameter("interaction", "true")
                //  .addQueryParameter("joined", joined)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(PostsModel.class);
    }

    @Override
    public Single<List<PostsModel>> getGroupPosts(String group_id, String user_id, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_POST_DATA)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("group_post", group_id)
                .addQueryParameter("id_user_name", user_id)
                //  .addQueryParameter("joined", joined)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(PostsModel.class);
    }

    // GET SINGLE USER POSTS
    @Override
    public Single<List<PostsModel>> getSingleUserPosts(String id_user, String user_id, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_POST_DATA)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_user_name", id_user)
                .addQueryParameter("singleuser", "true")
                //  .addQueryParameter("joined", joined)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(PostsModel.class);
    }

    // get latest posts
    @Override
    public Single<List<PostsModel>> getLatestPosts(String user_id, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_POST_DATA)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_user_name", user_id)
                .addQueryParameter("latest", "true")
                //  .addQueryParameter("joined", joined)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(PostsModel.class);
    }

    // get latest posts
    @Override
    public Single<List<PostsModel>> getAgeGenderPost(String id_user_name, String gender, String user_date_of_birth, String premiumuser, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_POST_DATA)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_user_name", id_user_name)
                .addQueryParameter("gender", gender)
                .addQueryParameter("user_date_of_birth", user_date_of_birth)
                .addQueryParameter("premiumuser", premiumuser)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(PostsModel.class);
    }

    @Override
    public Single<List<PostsModel>> getSearchPost(String id_user_name, String search_word, String premium, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_POST_DATA)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_user_name", id_user_name)
                .addQueryParameter("search_word", search_word)
                .addQueryParameter("premium", premium)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(PostsModel.class);
    }

    // get popular posts
    @Override
    public Single<List<PostsModel>> getPopularPosts(String user_id, String popular, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_POST_DATA)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_user_name", user_id)
                .addQueryParameter("popular", popular)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(PostsModel.class);
    }

    @Override
    public Single<List<PostsModel>> getPendingPosts(String user_id, String pendingPost, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_POST_DATA_PENDING)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_user_name", user_id)
                .addQueryParameter("pendingPost", pendingPost)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(PostsModel.class);
    }

    @Override
    public Single<List<PostsModel>> getImagePosts(String user_id, String onlyImages, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_POST_DATA)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_user_name", user_id)
                .addQueryParameter("onlyImages", onlyImages)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(PostsModel.class);
    }

    @Override
    public Single<SuccessResponse> deletePendingPosts(String id_posts) {
        return Rx2AndroidNetworking.get(ApiEndPoint.DELETE_PENDING_POST)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_posts", id_posts)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    @Override
    public Single<List<GroupUser>> getJoinedGroups(String user_id) {

        return Rx2AndroidNetworking.get(ApiEndPoint.GET_GROUPS_JOINED)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("user_id", user_id)
                .build()
                .getObjectListSingle(GroupUser.class);
    }

    // get users feed posts
    @Override
    public Single<List<PostsModel>> getUserFeed(String id_user_name, String user_id, String filtered, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_POST_DATA)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_user_name", id_user_name)
                .addQueryParameter("user_id", user_id)
                .addQueryParameter("filtered", filtered)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(PostsModel.class);
    }

    // get facebook friends feed posts
    @Override
    public Single<List<PostsModel>> getFacebookFriendsFeed(String id_user_name, String user_id, String facebookId, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_POST_DATA)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_user_name", id_user_name)
                .addQueryParameter("facebookId", facebookId)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(PostsModel.class);
    }



    // GET NOTIFICATION DATA
    @Override
    public Single<List<NotificationPojo>> getNotificationData(String id_user_name, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_NOTIFICATION_DATA)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_user_name", id_user_name)
                //  .addQueryParameter("joined", joined)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(NotificationPojo.class);
    }


    // send custom name online
    @Override
    public Single<SuccessResponse> sendCustomNameInsert(String id_conversation, String user_id, String username, String avatar_url) {
        return Rx2AndroidNetworking.get(ApiEndPoint.POST_NEW_CUSTOM_NAME)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("id_conversation", id_conversation)
                .addQueryParameter("user_id", user_id)
                .addQueryParameter("username", username)
                .addQueryParameter("avatar_url", avatar_url)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    @Override
    public Single<ProfileMain> getUserProfile(String user_id) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_USER_PROFILE)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("user_id", user_id)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(ProfileMain.class);
    }

    // delete entire chat
    @Override
    public Single<SuccessResponse> deleteEntireChat(String id_conversation) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_DELETE_ALL_CHAT)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_conversation", id_conversation)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }

    // block_user_insert.php
    @Override
    public Single<UserResponse> blockUserInsert(String currentUserId, String userToBeBlockedId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.POST_BLOCK_USER)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("user_id", currentUserId)
                .addQueryParameter("blocked_id", userToBeBlockedId)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(UserResponse.class);
    }

// get_all_chats_new.php

    @Override
    public Single<List<Dialog>> getAllChats(String user_one, String token) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_ALL_CHAT_NEW)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("user_one", user_one)
                .addQueryParameter("token", token)
                .build()
                .getObjectListSingle(Dialog.class);
    }

    // get users feed posts
    @Override
    public Single<List<PostsModel>> getSinglePosts(String id_posts, String user_id) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_SINGLE_POST)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id_posts", id_posts)
                .addQueryParameter("user_id", user_id)
                .build()
                .getObjectListSingle(PostsModel.class);
    }

    // block_user_insert.php
    @Override
    public Single<PostLikesResponse> sendLike(String user_id, String post_id, int like, String token) {
        return Rx2AndroidNetworking.get(ApiEndPoint.SEND_LIKE)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("user_id", user_id)
                .addQueryParameter("post_id", post_id)
                .addQueryParameter("like", String.valueOf(like))
                .addQueryParameter("token", token)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(PostLikesResponse.class);
    }

    // block_user_insert.php
    @Override
    public Single<PostLikesResponse> sendSad(String user_id, String post_id, int hug, String token) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_HUGS_SINGLE)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("user_id", user_id)
                .addQueryParameter("post_id", post_id)
                .addQueryParameter("hug", String.valueOf(hug))
                .addQueryParameter("token", token)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(PostLikesResponse.class);
    }



// block_user_insert.php


// block_user_insert.php sendLikeNotification(String senderId, String receiverId, String postId, String deviceToken)

    @Override
    public Single<PostLikesResponse> post_likes(String user_id, String post_id, String like, String token) {
        return Rx2AndroidNetworking.post(ApiEndPoint.GET_LIKES_SINGLE)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("post_id", post_id)
                .addBodyParameter("like", like)
                .addBodyParameter("token", token)
                .build()
                .getObjectSingle(PostLikesResponse.class);
    }

    @Override
    public Single<SuccessResponse> post_owner_notification(String id_user_name, String id_posts, String token) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_OWNER_NOTIFICATION)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_user_name", id_user_name)
                .addBodyParameter("id_posts", id_posts)
                .addBodyParameter("token", token)
                .build()
                .getObjectSingle(SuccessResponse.class);

    }

    @Override
    public Single<SuccessResponse> post_comment_owner_notification(String id_user_name, String id_post_comments, String id_posts, String token) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_COMMENT_OWNER_NOTIFICATION)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_user_name", id_user_name)
                .addBodyParameter("id_post_comments", id_post_comments)
                .addBodyParameter("id_posts", id_posts)
                .addBodyParameter("token", token)
                .build()
                .getObjectSingle(SuccessResponse.class);

    }

    @Override
    public Single<SuccessResponse> post_comment_owner_reply_notification(String id_user_name, String id_post_comments_reply, String id_posts, String token) {
        return Rx2AndroidNetworking.post(ApiEndPoint.POST_COMMENT_OWNER_REPLY_NOTIFICATION)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("id_user_name", id_user_name)
                .addBodyParameter("id_post_comments_reply", id_post_comments_reply)
                .addBodyParameter("id_posts", id_posts)
                .addBodyParameter("token", token)
                .build()
                .getObjectSingle(SuccessResponse.class);

    }


    @Override
    public Single<SuccessResponseChat> sendTextNotification(String senderId, String receiverId, String chatText, String chatImage, String receiverAnonymous, String id_posts, String token) {
        return Rx2AndroidNetworking.post(ApiEndPoint.INSERT_CHAT)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("user_one", senderId)
                .addBodyParameter("user_two", receiverId)
                .addBodyParameter("chatText", chatText)
                .addBodyParameter("chatImage", chatImage)
                .addBodyParameter("random_user_two", receiverAnonymous)
                .addBodyParameter("id_posts", id_posts)
                .addBodyParameter("token", token)
                .build()
                .getObjectSingle(SuccessResponseChat.class);
    }


    // block_user_insert.php
    @Override
    public Single<PostLikesResponse> post_sad(String user_id, String post_id, String hug, String token) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_HUGS_SINGLE)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("user_id", user_id)
                .addQueryParameter("post_id", post_id)
                .addQueryParameter("hug", hug)
                .addQueryParameter("token", token)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(PostLikesResponse.class);
    }

    @Override
    public Single<SuccessResponse> deleteChat(String id_conversation_reply) {
        return Rx2AndroidNetworking.get(ApiEndPoint.DELETE_CHAT)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addQueryParameter("id_conversation_reply", id_conversation_reply)
                .setPriority(Priority.LOW)
                .build()
                .getObjectSingle(SuccessResponse.class);
    }


// get users feed posts


    @Override
    public Single<List<Message>> getChatMessages(String user_one, String user_two, String id_posts, String token, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_CHAT_MESSAGES)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("user_one", user_one)
                .addQueryParameter("user_two", user_two)
                .addQueryParameter("id_posts", id_posts)
                .addQueryParameter("token", token)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(Message.class)
                .observeOn(AndroidSchedulers.mainThread());
    }




  /*  @Override
    public Single<List<Message>> getChatMessages02(String user_one, String user_two, String id_posts, String token, String page) {
        return Rx2AndroidNetworking.get(ApiEndPoint.GET_CHAT_MESSAGES)
                //  .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("user_one", user_one)
                .addQueryParameter("user_two", user_two)
                .addQueryParameter("id_posts", id_posts)
                .addQueryParameter("token", token)
                .addQueryParameter("page", page)
                .build()
                .getObjectListSingle(Message.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()
                );
    }
    */

    // block_user_insert.php


















    // post community group
/*
    @Override
    public Single<LoginResponse> sendAgeOnlineApi(String name, String email, String userId, String deviceId, String socialNetwork) {
        return Rx2AndroidNetworking.post(ApiEndPoint.JOIN_CATEGORIES)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("firstname", "Amit").addPathParameter("pageNumber", "0")
                .addQueryParameter("name", "10")
                .addQueryParameter("email", "10")
                .addQueryParameter("limit", "10")
                .addQueryParameter("limit", "10")
                .addQueryParameter("limit", "10").addBodyParameter(name,email,userId,deviceId,socialNetwork)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    // get all groups

    @Override
    public Single<OpenSourceResponse> getCommunityGroup() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_OPEN_SOURCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(OpenSourceResponse.class);
    }




    // join all groups

    @Override
    public Single<LoginResponse> sendAgeOnlineApi(String name, String email, String userId, String deviceId, String socialNetwork) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGIN)
                // .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter("firstname", "Amit").addPathParameter("pageNumber", "0")
                .addQueryParameter("name", "10")
                .addQueryParameter("email", "10")
                .addQueryParameter("limit", "10")
                .addQueryParameter("limit", "10")
                .addQueryParameter("limit", "10").addBodyParameter(name,email,userId,deviceId,socialNetwork)
                .build()
                .getObjectSingle(LoginResponse.class);
    }








    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest
                                                              request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GOOGLE_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest
                                                                request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_FACEBOOK_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest
                                                              request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGOUT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(LogoutResponse.class);
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_BLOG)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(BlogResponse.class);
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_OPEN_SOURCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(OpenSourceResponse.class);
    }

    */
}

