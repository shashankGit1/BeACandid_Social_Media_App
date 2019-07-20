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

package in.becandid.app.becandid.ui.login;


import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

/**
 * Created by janisharali on 27/01/17.
 */

// we add click handlers in this usnig presenter

@PerActivity
public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

  //  void onServerLoginClick(String email, String password);

 //   void onGoogleLoginClick();

    void skipUser(String deviceId, String socialNetwork);

    void onFacebookLoginClick(String name, String email, String userId, String deviceId, String socialNetwork);

    void faceboookFriends(String accessToken, String limit);

    void faceboookFriends02(String access_token, String limit, String after);

    void sendFacebookFriends(String id_user_name, String facebook_id);

    void sendGenderOnline(String userId, String gender);

    void sendAgeOnline(String userId, String age);

    void getUserJoinedGroupsFirstPage(String user_id, String below18, String joined, int page);
    void getUserJoinedGroupsSecondPage(String user_id, String below18, String joined, int page);
    void sendCommunityGroupListOnline(String groupList, String userId);

    void getAllGroupsOnline(String userId);
    void joinGroupsOnline(String userId, String listOfGroup);
}
