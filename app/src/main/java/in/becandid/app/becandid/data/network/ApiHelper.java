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

import java.io.File;
import java.util.List;

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

/**
 * Created by janisharali on 27/01/17.
 */

public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<List<PostsModel>> getImagePosts(String user_id, String onlyImages, String page);
    Single<SuccessResponse> getAuthorisedAccess(String access, String user_id);

    Single<SuccessResponse> deletePendingPosts(String id_posts);
    Single<List<PostsModel>> getPendingPosts(String user_id, String pendingPost, String page);
    Single<List<PostUserCommentModel>> post_comments(String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type);
    Single<List<PostUserCommentModel>> postCommentReply(String id_post_comments, String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type);
    Single<List<PostUserCommentModel>> postCommentReplyReply(String id_post_comments, String id_post_comments_reply, String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type);

    Single<SuccessResponse> get_username_check(String group_name, String id_user_name);
    Single<UserResponse> postStatusPending(String user_id, String post_text, String image_url, String group_id, String cat_id, String feeling_id, String type, String adult_filter);

    // login API
    Single<SuccessResponse> post_owner_notification(String id_user_name, String id_posts, String token);
    Single<SuccessResponse> post_comment_owner_notification(String id_user_name, String id_post_comments, String id_posts, String token);
    Single<SuccessResponse> post_comment_owner_reply_notification(String id_user_name, String id_post_comments_reply, String id_posts, String token);

    Single<SuccessResponse> editGroup(String id_categories, String name, String group_description, String group_id);
    Single<SuccessResponse> editGroup(String id_categories, String name, String group_image_url, String group_description, String group_id);
    Single<List<PostsModel>> getSearchPost(String id_user_name, String search_word, String premium, String page);
    Single<List<PostsModel>> getAgeGenderPost(String id_user_name, String gender, String user_date_of_birth, String premiumuser, String page);
    Single<PostLikesResponse> post_likes(String user_id, String post_id, String like, String token);
    Single<LoginResponse> doLoginResponse(String name, String email, String userId, String deviceId, String socialNetwork);
    Single<SuccessResponse> updatePost(String id_posts, String action, String text_status);
    Single<SuccessResponse> reportAbuse(String id_user_name, String sender_user_id,String id_posts, String token, String message);
    Single<InsertGroupPOJO> joinCategories(String user_id, String group_ids);
    Single<LoginResponse> skipUser(String deviceId, String socialNetwork);
    Single<List<GroupUser>> getJoinedGroups(String user_id);
    Single<List<Message>> getChatMessages(String from_user_id, String user_id, String to_user_id, String token, String page);
    Single<SuccessResponse> deleteChat(String id_conversation_reply);
    Single<LoginResponse> doSkipLoginResponse(String deviceId, String socialNetwork);
    Single<MainResponse> getFacebookFriends(String access_token, String limit);
    Single<MainResponse> getFacebookFriends02(String access_token, String limit, String after);
   // Single<List<Message>> getChatMessages02(String user_one, String user_two, String id_posts, String token, String page);
    // send gender API
    Single<SuccessResponse> sendGenderOnlineApi(String userId, String gender);
    Single<List<CommunityGroupPojo>> getSearchGroup(String search_word, String user_id);
    Single<SuccessResponse> postNewSettings(String age_range, String gender, String adult_filter, String block_premium_search, String id_user_name);
    Single<UserResponse> postStatus(String user_id, String post_text, String image_url, String group_id, String cat_id, String feeling_id, String type, String adult_filter);
    // send joined groups
    Single<LoginResponse> sendJoinedGroupsOnlineApi(String id_user_name, String facebook_id);
    Single<List<PostsModel>> getGroupPosts(String group_id, String user_id, String page);
    Single<InsertGroupPOJO> sendUnJoinGroup(String group_ids, String user_id);

    Single<SuccessResponse> postCommentReplyLike(String id_post_comment_reply, String id_user_name, String likes, String id_posts, String token);
    Single<SuccessResponse> postCommentLike(String id_post_comment, String id_user_name, String id_posts, String comment_likes, String token);
    // send age
    Single<SuccessResponse> sendAgeOnlineApi(String age, String id_user_name);
    Single<List<CommunityGroupPojo>> getGroupSpecific(String group_id, String user_id, String page);
   // Single<SuccessResponse> postCommentReplyLike(String id_post_comment_reply, String id_user_name, String likes, String post_comment_reply_id, String user_id, String token);
    // post join group
    Single<InsertGroupPOJO> sendJoinGroup(String group_ids, String user_id);
    Single<SuccessResponse> postSaveToken(String id_user_name, String pushnotificationToken);
    // get Get All category joined
    Single<List<CategoryJoined>> getAllCategory(String user_id);

    // get all groups
    Single<List<GroupUser>> getListAllGroups(String user_id);
    Single<List<CommunityGroupPojoNew>> getAllCommunityGroup(String user_id, String below18, String joined, String page);

    // get all groups below 18
    // get history posts
    Single<List<PostsModel>> getHistoryPosts(String id_user_name, String user_id, String group_post, String page);

    Single<SuccessResponse> get_group_name_check(String group_name);
    // get latest posts
    Single<List<PostsModel>> getLatestPosts(String user_id, String page);


    // get single user posts
    Single<List<PostsModel>> getSingleUserPosts(String id_user, String user_id, String page);

    // get popular posts
    public Single<List<PostsModel>> getPopularPosts(String user_id, String popular, String page);

    // get your feed posts
    Single<List<PostsModel>> getUserFeed(String id_user_name, String user_id, String filtered, String page);

    Single<ContactAddResponse> sendFacebookFriends(String id_user_name, String facebook_id);

    // get facebook friends posts
    Single<List<PostsModel>> getFacebookFriendsFeed(String id_user_name, String user_id, String facebookId, String page);

    // get notification data
    Single<List<NotificationPojo>> getNotificationData(String id_user_name, String page);


    Single<ImageUrl> postImageUpload(File file);
    Single<SuccessResponse> deleteEntireChat(String id_conversation);

    // send custom insert name
    // delete entire chat

    Single<UserResponse> deleteCommentReply(String id_post_comment_reply, String user_id, String token);


    Single<List<Dialog>> getAllChats(String user_one, String token);
  //  Single<SuccessResponse> sendTextNotification(String senderId, String receiverId, String chatText, String chatImage, String receiverAnonymous, String id_posts, String token);

    Single<SuccessResponseChat> sendTextNotification(String senderId, String receiverId, String chatText, String chatImage, String receiverAnonymous, String id_posts, String token);
  //  Single<PostCommentReplyPojo> postCommentReply(String id_post_comments, String id_user_name, String id_post_user_name, String id_posts, String message, String token);
    Single<ProfileMain> getUserProfile(String user_id);
    Single<PostLikesResponse> post_sad(String user_id, String post_id, String hug, String token);
    // block user insert
    Single<UserResponse> deleteComment(String id_post_comments, String user_id, String token);
    Single<UserResponse> blockUserInsert(String currentUserId, String userToBeBlockedId);
    Single<SuccessResponse> postMakeAdult(String id_posts, String adult_filter);
    Single<SuccessResponse> updateTimeOnline(String token, String user_id);

    Single<SuccessResponse> sendCustomNameInsert(String id_conversation, String user_id, String username, String avatar_url);
    // get all chat new
  //  Single<List<Dialog>> getAllChats(String userID, String token);

    // GET SINGLE POST DETAILS
    Single<List<PostsModel>> getSinglePosts(String id_posts, String user_id);

    Single<List<CommunityGroupPojoNew>> getAllLatestCommunityGroup(String user_id, String below18, String joined, String page);
    // GET RANDOM NAME
    // send like on server
    Single<PostLikesResponse> sendLike(String user_id, String post_id, int like, String token);

    Single<GroupsCreatePOJO> postInsertGroup(String id_categories, String user_id, String group_name, String group_image, String group_description);
    // send sad to server
    Single<PostLikesResponse> sendSad(String user_id, String post_id, int hug, String token);

   // Single<PostCommentReplyPojo> post_comments(String id_user_name, String id_post_user_name, String id_posts, String message, String token);
    // send comments reply
    Single<List<PostUserCommentModel>> getCommentsReply(String id_posts, String user_id, String token);
}
