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

package in.becandid.app.becandid.di.component;


import dagger.Component;
import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.di.module.ActivityModule;
import in.becandid.app.becandid.search_lib.SearchActivity;
import in.becandid.app.becandid.ui.chat.ChatFragment;
import in.becandid.app.becandid.ui.chat.MessageActivity;
import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.ui.discover.DiscoverLatestFragment;
import in.becandid.app.becandid.ui.discover.DiscoverTrendingFragment;
import in.becandid.app.becandid.ui.discover.FacebookFragment;
import in.becandid.app.becandid.ui.discover.PostsImageCardViewHolder;
import in.becandid.app.becandid.ui.filter.NewFilterActivity;
import in.becandid.app.becandid.ui.filter.PremiumSearchResultActivity;
import in.becandid.app.becandid.ui.group.CommunityGroupFragment;
import in.becandid.app.becandid.ui.group.CreateGroupActivity;
import in.becandid.app.becandid.ui.group.CreateGroupDescActivity;
import in.becandid.app.becandid.ui.group.CreateGroupTagActivity;
import in.becandid.app.becandid.ui.group.EditGroupActivity;
import in.becandid.app.becandid.ui.group.GroupSearchActivity;
import in.becandid.app.becandid.ui.group.LoginCommunityGroupActivity;
import in.becandid.app.becandid.ui.group.LoginGroupTagActivity;
import in.becandid.app.becandid.ui.group.MyGroupsFragment;
import in.becandid.app.becandid.ui.group.NewCommunityGroupFragment;
import in.becandid.app.becandid.ui.group.UserGroupDetails;
import in.becandid.app.becandid.ui.login.StartPage02FacebookLogin;
import in.becandid.app.becandid.ui.login.StartPage04Gender;
import in.becandid.app.becandid.ui.login.StartPage05Age;
import in.becandid.app.becandid.ui.postDetails.PostsDetailsActivity;
import in.becandid.app.becandid.ui.profile.CustomUsernameActivity;
import in.becandid.app.becandid.ui.profile.DiscoverHistoryFragment;
import in.becandid.app.becandid.ui.profile.MyPostsFragment;
import in.becandid.app.becandid.ui.profile.NotificationFragment;
import in.becandid.app.becandid.ui.profile.ProfilePageFragment;
import in.becandid.app.becandid.ui.profile.SettingsActivity;
import in.becandid.app.becandid.ui.userpost.EditPost;
import in.becandid.app.becandid.ui.userpost.OnlyImageFragment;
import in.becandid.app.becandid.ui.userpost.PendingPostFragment;
import in.becandid.app.becandid.ui.userpost.PostActivity;
import in.becandid.app.becandid.ui.userpost.ReportAbuseActivity;

/**
 * Created by janisharali on 27/01/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
//@Component
public interface ActivityComponent {

    void inject(DiscoverActivity activity);
    void inject(GroupSearchActivity activity);
    void inject(DiscoverLatestFragment fragment);

    void inject(DiscoverTrendingFragment fragment);
    void inject(CustomUsernameActivity fragment);

    void inject(FacebookFragment fragment);
    void inject(PendingPostFragment fragment);
    void inject(OnlyImageFragment fragment);

    void inject(StartPage02FacebookLogin activity);
    void inject(StartPage04Gender activity);
    void inject(NewCommunityGroupFragment activity);
    void inject(StartPage05Age activity);
    void inject(PremiumSearchResultActivity activity);
    void inject(NewFilterActivity activity);
    void inject(EditGroupActivity activity);
    void inject(PostsImageCardViewHolder activity);
    void inject(UserGroupDetails activity);
    void inject(SearchActivity activity);
    void inject(ReportAbuseActivity activity);
    void inject(PostActivity activity);
    void inject(SettingsActivity activity);
    void inject(CreateGroupActivity activity);
    void inject(CreateGroupDescActivity activity);
    void inject(CreateGroupTagActivity activity);
    void inject(EditPost activity);


    // login Files
    void inject(MessageActivity activity);


    // starting login also included in Group folder
    void inject(LoginCommunityGroupActivity activity);
    void inject(LoginGroupTagActivity activity);
    void inject(PostsDetailsActivity activity);

    void inject(ChatFragment fragment);



    void inject(CommunityGroupFragment fragment);

    void inject(MyGroupsFragment fragment);

    void inject(DiscoverHistoryFragment fragment);

    void inject(MyPostsFragment fragment);
    void inject(ProfilePageFragment fragment);

    void inject(NotificationFragment fragment);

  //  void inject(RateUsDialog dialog);

}
