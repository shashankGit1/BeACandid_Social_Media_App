//*
// * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     https:mindorks.comlicenseapache-v2
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License
// *

package in.becandid.app.becandid.data.network;


import in.becandid.app.becandid.BuildConfig;

//**
// * Created by amitshekhar on 010217.
// *

public final class ApiEndPoint {

    public static final String TEST =   "test02.php";

    public static final String POST_BLOCK_USER =   "block_user.php";
    public static final String SAMPLE =   "sample.php";

    public static final String GET_CHECK_GROUP_NAME =   "check_group_name.php";
    public static final String GET_CHECK_USER_NAME =   "check_username.php";
    public static final String DELETE_CHAT =   "delete_chat.php";
    public static final String DELETE_COMMENTS =   "delete_comment.php";
    public static final String GET_ALL_CATEGORIES =   "get_all_categories_joined.php";
    public static final String GET_CHAT_MESSAGES =   "get_all_chat_messages.php";
    public static final String POST_DELETE_ALL_CHAT =   "get_chats_delete.php";
    public static final String GET_LIST_ALL_GROUPS =   "get_all_groups.php";
    public static final String GET_COMMENTS_REPLY =   "get_comments_reply.php";
    public static final String GET_GROUPS_JOINED =   "get_groups_joined.php";
    public static final String GET_GROUPS_SPECIFIC =   "get_groups_specific.php";
    public static final String GET_ALL_GROUPS =   "get_groups.php";
    public static final String GET_ALL_GROUPS_LATEST =   "get_groups_latest.php";
    public static final String GET_ALL_CHAT_NEW =   "get_last_chat.php";
    public static final String GET_NOTIFICATION_DATA =   "get_notification.php";
    public static final String GET_SEARCH_GROUP =   "get_search_groups.php";
    public static final String GET_USER_PROFILE =   "get_user_profile.php";
    public static final String GET_HUGS_SINGLE =   "hugs_get_single.php";
    public static final String POST_COMMENT_LIKE =   "insert_comment_like.php";
    public static final String POST_COMMENT_REPLY_LIKE =   "insert_comment_reply_like.php";
    public static final String POST_INSERT_GROUP =   "insert_group.php";
    public static final String INSERT_CHAT =   "insert_chat.php";
    public static final String POST_NEW_CUSTOM_NAME =   "insert_custom_name.php";
    public static final String POST_NEW_SETTINGS =   "insert_settings.php";
    public static final String JOIN_CATEGORIES =   "join_category.php";
    public static final String POST_JOIN_GROUPS = "join_group.php";

    public static final String GET_LIKES_SINGLE =   "likes_get_single.php";  //test
    public static final String SEND_LIKE =   "send_like.php";

    public static final String POST_LOGIN = "login.php";
    public static final String POST_LOGIN_WITHOUT_BASE =  "login.php";
    public static final String UPDATE_GROUP = "update_group.php";
    public static final String POST_OWNER_NOTIFICATION =   "postComments_notification.php"; // test
    public static final String POST_COMMENTS =   "postComments.php";
    public static final String POST_COMMENT_OWNER_NOTIFICATION =   "postComments_reply_notification.php";
    public static final String POST_COMMENT_REPLY =   "postComments_reply.php";
    public static final String POST_COMMENT_OWNER_REPLY_NOTIFICATION =   "postComments_reply_owner_notification.php";
    public static final String POST_COMMENT_REPLY_REPLY =   "postComments_reply_reply.php";
    public static final String GET_SINGLE_POST =   "posts_single.php";
    public static final String GET_POST_DATA =   "posts.php";
    public static final String DELETE_PENDING_POST =   "delete_pending_post.php";
    public static final String GET_POST_DATA_PENDING =   "posts_pending.php";
    public static final String POST_STATUS =   "postStatus.php";
    public static final String POST_STATUS_PENDING =   "postStatusPending.php";
    public static final String POST_SEND_FRIENDS_LIST = "register_facebook_friends.php";
    public static final String POST_REPORT_ABUSE = "report_abuse.php";
    public static final String POST_SAVE_TOKEN =   "save_token.php";
    public static final String POST_UNJOIN_GROUPS =   "unjoin_group.php";
    public static final String UPDATE_ADULT =   "update_adult.php";
    public static final String GET_AGE_UPDATE =   "update_age.php";
    public static final String GET_GENDER_UPDATE =   "update_gender.php";
    public static final String POST_UPDATE_POST =   "update_post_new.php";
    public static final String UPDATE_USER_TIME =   "update_user_token.php";

    public static final String DELETE_COMMENTS_REPLY =   "delete_comment_reply.php";

    public static final String GET_FACEBOOK_FRIENDS = "https:graph.facebook.comv3.3mefriends";
    public static final String POST_IMAGE_UPLOAD =   "php-storage-3-newuploadImage.php";
    public static final String POST_IMAGE_UPLOAD_WITHOUT_FILTER =   "php-storage-3-newupload02.php";


    public static final String ENDPOINT_GOOGLE_LOGIN = BuildConfig.BASE_URL
            + "588d14f4100000a9072d2943";

    public static final String ENDPOINT_FACEBOOK_LOGIN = BuildConfig.BASE_URL
            + "588d15d3100000ae072d2944";

    public static final String ENDPOINT_SERVER_LOGIN = BuildConfig.BASE_URL
            + "588d15f5100000a8072d2945";

    public static final String ENDPOINT_LOGOUT = BuildConfig.BASE_URL
            + "588d161c100000a9072d2946";

    public static final String ENDPOINT_BLOG = BuildConfig.BASE_URL
            + "5926ce9d11000096006ccb30";

    public static final String ENDPOINT_OPEN_SOURCE = BuildConfig.BASE_URL
            + "5926c34212000035026871cd";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
