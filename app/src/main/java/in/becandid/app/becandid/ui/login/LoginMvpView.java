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


import java.util.List;

import in.becandid.app.becandid.dto.CategoryJoined;
import in.becandid.app.becandid.dto.ContactAddResponse;
import in.becandid.app.becandid.dto.LoginResponse;
import in.becandid.app.becandid.dto.MainResponse;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.base.MvpView;
import in.becandid.app.becandid.ui.group.CommunityGroupPojoNew;
import in.becandid.app.becandid.ui.group.InsertGroupPOJO;

/**
 * Created by janisharali on 27/01/17.
 */

// we handle only the method click which take parameters from view

public interface LoginMvpView extends MvpView {

    void openMainActivity();

  //  void sendData(String name, String email, String userId, String deviceId, String socialNetwork);
    void sendData(LoginResponse loginResponse);

    void skipUser(LoginResponse loginResponse);
 //   void skipUser(String deviceId, String socialNetwork);

    void facebookFriends(MainResponse mainResponse);

    void facebookFriends02(MainResponse mainResponse);

    void sendFacebookFriends(ContactAddResponse contactAddResponse);

  void sendAge(SuccessResponse successResponse);

  void sendGender(SuccessResponse successResponse);

  void getUserJoinedGroupsFirstPage(List<CommunityGroupPojoNew> communityGroupPojoNewList);

  void getUserJoinedGroupsSecondPage(List<CommunityGroupPojoNew> communityGroupPojoNewList);

  void sendCommunityGroupList(InsertGroupPOJO insertGroupPOJO);

  void getAllGroups(List<CategoryJoined> categoryJoined);
  void joinGroups(InsertGroupPOJO insertGroupPOJO);



}
