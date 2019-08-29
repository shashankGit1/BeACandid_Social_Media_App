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


import in.becandid.app.becandid.BuildConfig;

/**
 * Created by amitshekhar on 01/02/17.
 */

public final class ApiEndPoint {

    public static final String TEST = BuildConfig.BASE_URL + "/test02.php";


    public static final String POST_BLOCK_USER = BuildConfig.BASE_URL + "/block_user.php";
    public static final String SAMPLE = BuildConfig.BASE_URL + "/sample.php";

    public static final String GET_CHECK_GROUP_NAME = BuildConfig.BASE_URL + "/check_group_name.php";
    public static final String GET_CHECK_USER_NAME = BuildConfig.BASE_URL + "/check_username.php";
    public static final String DELETE_CHAT = BuildConfig.BASE_URL + "/delete_chat.php";
    public static final String DELETE_COMMENTS = BuildConfig.BASE_URL + "/delete_comment.php";
    public static final String GET_ALL_CATEGORIES = BuildConfig.BASE_URL + "/get_all_categories_joined.php";
    public static final String GET_CHAT_MESSAGES = BuildConfig.BASE_URL + "/get_all_chat_messages.php";
    public static final String POST_DELETE_ALL_CHAT = BuildConfig.BASE_URL + "/get_chats_delete.php";
    public static final String GET_LIST_ALL_GROUPS = BuildConfig.BASE_URL + "/get_all_groups.php";
    public static final String GET_COMMENTS_REPLY = BuildConfig.BASE_URL + "/get_comments_reply.php";
    public static final String GET_GROUPS_JOINED = BuildConfig.BASE_URL + "/get_groups_joined.php";
    public static final String GET_GROUPS_SPECIFIC = BuildConfig.BASE_URL + "/get_groups_specific.php";
    public static final String GET_ALL_GROUPS = BuildConfig.BASE_URL + "/get_groups.php";
    public static final String GET_ALL_GROUPS_LATEST = BuildConfig.BASE_URL + "/get_groups_latest.php";
    public static final String GET_ALL_CHAT_NEW = BuildConfig.BASE_URL + "/get_last_chat.php";
    public static final String GET_NOTIFICATION_DATA = BuildConfig.BASE_URL + "/get_notification.php";
    public static final String GET_SEARCH_GROUP = BuildConfig.BASE_URL + "/get_search_groups.php";
    public static final String GET_USER_PROFILE = BuildConfig.BASE_URL + "/get_user_profile.php";
    public static final String GET_HUGS_SINGLE = BuildConfig.BASE_URL + "/hugs_get_single.php";
    public static final String POST_COMMENT_LIKE = BuildConfig.BASE_URL + "/insert_comment_like.php";
    public static final String POST_COMMENT_REPLY_LIKE = BuildConfig.BASE_URL + "/insert_comment_reply_like.php";
    public static final String POST_INSERT_GROUP = BuildConfig.BASE_URL + "/insert_group.php";
    public static final String INSERT_CHAT = BuildConfig.BASE_URL + "/insert_chat.php";
    public static final String POST_NEW_CUSTOM_NAME = BuildConfig.BASE_URL + "/insert_custom_name.php";
    public static final String POST_NEW_SETTINGS = BuildConfig.BASE_URL + "/insert_settings.php";
    public static final String JOIN_CATEGORIES = BuildConfig.BASE_URL + "/join_category.php";
    public static final String POST_JOIN_GROUPS = BuildConfig.BASE_URL + "/join_group.php";

    public static final String GET_LIKES_SINGLE = BuildConfig.BASE_URL + "/likes_get_single.php"; // test
    public static final String SEND_LIKE = BuildConfig.BASE_URL + "/send_like.php";

    public static final String POST_LOGIN = BuildConfig.BASE_URL + "/login.php";
    public static final String UPDATE_GROUP = BuildConfig.BASE_URL + "/update_group.php";
    public static final String POST_OWNER_NOTIFICATION = BuildConfig.BASE_URL + "/postComments_notification.php"; // test
    public static final String POST_COMMENTS = BuildConfig.BASE_URL + "/postComments.php";
    public static final String POST_COMMENT_OWNER_NOTIFICATION = BuildConfig.BASE_URL + "/postComments_reply_notification.php";
    public static final String POST_COMMENT_REPLY = BuildConfig.BASE_URL + "/postComments_reply.php";
    public static final String POST_COMMENT_OWNER_REPLY_NOTIFICATION = BuildConfig.BASE_URL + "/postComments_reply_owner_notification.php";
    public static final String POST_COMMENT_REPLY_REPLY = BuildConfig.BASE_URL + "/postComments_reply_reply.php";
    public static final String GET_SINGLE_POST = BuildConfig.BASE_URL + "/posts_single.php";
    public static final String GET_POST_DATA = BuildConfig.BASE_URL + "/posts.php";
    public static final String DELETE_PENDING_POST = BuildConfig.BASE_URL + "/delete_pending_post.php";
    public static final String GET_POST_DATA_PENDING = BuildConfig.BASE_URL + "/posts_pending.php";
    public static final String POST_STATUS = BuildConfig.BASE_URL + "/postStatus.php";
    public static final String POST_STATUS_PENDING = BuildConfig.BASE_URL + "/postStatusPending.php";
    public static final String POST_SEND_FRIENDS_LIST = BuildConfig.BASE_URL + "/register_facebook_friends.php";
    public static final String POST_REPORT_ABUSE = BuildConfig.BASE_URL + "/report_abuse.php";
    public static final String POST_SAVE_TOKEN = BuildConfig.BASE_URL + "/save_token.php";
    public static final String POST_UNJOIN_GROUPS = BuildConfig.BASE_URL + "/unjoin_group.php";
    public static final String UPDATE_ADULT = BuildConfig.BASE_URL + "/update_adult.php";
    public static final String GET_AGE_UPDATE = BuildConfig.BASE_URL + "/update_age.php";
    public static final String GET_GENDER_UPDATE = BuildConfig.BASE_URL + "/update_gender.php";
    public static final String POST_UPDATE_POST = BuildConfig.BASE_URL + "/update_post_new.php";
    public static final String UPDATE_USER_TIME = BuildConfig.BASE_URL + "/update_user_token.php";

    public static final String DELETE_COMMENTS_REPLY = BuildConfig.BASE_URL + "/delete_comment_reply.php";

    public static final String GET_FACEBOOK_FRIENDS = "https://graph.facebook.com/v3.3/me/friends";
    public static final String POST_IMAGE_UPLOAD = BuildConfig.BASE_URL + "/php-storage-3-new/uploadImage.php";
    public static final String POST_IMAGE_UPLOAD_WITHOUT_FILTER = BuildConfig.BASE_URL + "/php-storage-3-new/upload02.php";


    public static final String ENDPOINT_GOOGLE_LOGIN = BuildConfig.BASE_URL
            + "/588d14f4100000a9072d2943";

    public static final String ENDPOINT_FACEBOOK_LOGIN = BuildConfig.BASE_URL
            + "/588d15d3100000ae072d2944";

    public static final String ENDPOINT_SERVER_LOGIN = BuildConfig.BASE_URL
            + "/588d15f5100000a8072d2945";

    public static final String ENDPOINT_LOGOUT = BuildConfig.BASE_URL
            + "/588d161c100000a9072d2946";

    public static final String ENDPOINT_BLOG = BuildConfig.BASE_URL
            + "/5926ce9d11000096006ccb30";

    public static final String ENDPOINT_OPEN_SOURCE = BuildConfig.BASE_URL
            + "/5926c34212000035026871cd";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
