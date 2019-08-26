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

package in.becandid.app.becandid.di.module;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import in.becandid.app.becandid.di.ActivityContext;
import in.becandid.app.becandid.search_lib.SearchMvpPresenter;
import in.becandid.app.becandid.search_lib.SearchMvpView;
import in.becandid.app.becandid.search_lib.SearchPresenter;
import in.becandid.app.becandid.ui.chat.ChatMvpPresenter;
import in.becandid.app.becandid.ui.chat.ChatMvpView;
import in.becandid.app.becandid.ui.chat.ChatPresenter;
import in.becandid.app.becandid.ui.discover.DiscoverActivityMvpPresenter;
import in.becandid.app.becandid.ui.discover.DiscoverActivityMvpView;
import in.becandid.app.becandid.ui.discover.DiscoverActivityPresenter;
import in.becandid.app.becandid.ui.discover.DiscoverFacebookMvpPresenter;
import in.becandid.app.becandid.ui.discover.DiscoverFacebookMvpView;
import in.becandid.app.becandid.ui.discover.DiscoverFacebookPresenter;
import in.becandid.app.becandid.ui.discover.DiscoverLatestMvpPresenter;
import in.becandid.app.becandid.ui.discover.DiscoverLatestMvpView;
import in.becandid.app.becandid.ui.discover.DiscoverLatestPresenter;
import in.becandid.app.becandid.ui.discover.DiscoverTrendingMvpPresenter;
import in.becandid.app.becandid.ui.discover.DiscoverTrendingMvpView;
import in.becandid.app.becandid.ui.discover.DiscoverTrendingPresenter;
import in.becandid.app.becandid.ui.filter.PremiumSearchResultMvpPresenter;
import in.becandid.app.becandid.ui.filter.PremiumSearchResultMvpView;
import in.becandid.app.becandid.ui.filter.PremiumSearchResultPresenter;
import in.becandid.app.becandid.ui.group.CommunityGroupMvpPresenter;
import in.becandid.app.becandid.ui.group.CommunityGroupMvpView;
import in.becandid.app.becandid.ui.group.CommunityGroupPresenter;
import in.becandid.app.becandid.ui.group.CreateGroupMvpPresenter;
import in.becandid.app.becandid.ui.group.CreateGroupMvpView;
import in.becandid.app.becandid.ui.group.CreateGroupPresenter;
import in.becandid.app.becandid.ui.group.CreateGroupTagMvpPresenter;
import in.becandid.app.becandid.ui.group.CreateGroupTagMvpView;
import in.becandid.app.becandid.ui.group.CreateGroupTagPresenter;
import in.becandid.app.becandid.ui.group.EditGroupMvpPresenter;
import in.becandid.app.becandid.ui.group.EditGroupMvpView;
import in.becandid.app.becandid.ui.group.EditGroupPresenter;
import in.becandid.app.becandid.ui.group.GroupSearchMvpPresenter;
import in.becandid.app.becandid.ui.group.GroupSearchMvpView;
import in.becandid.app.becandid.ui.group.GroupSearchPresenter;
import in.becandid.app.becandid.ui.group.MyGroupsMvpPresenter;
import in.becandid.app.becandid.ui.group.MyGroupsMvpView;
import in.becandid.app.becandid.ui.group.MyGroupsPresenter;
import in.becandid.app.becandid.ui.group.NewCommunityGroupMvpPresenter;
import in.becandid.app.becandid.ui.group.NewCommunityGroupMvpView;
import in.becandid.app.becandid.ui.group.NewCommunityGroupPresenter;
import in.becandid.app.becandid.ui.group.UserGroupMvpPresenter;
import in.becandid.app.becandid.ui.group.UserGroupMvpView;
import in.becandid.app.becandid.ui.group.UserGroupPresenter;
import in.becandid.app.becandid.ui.login.LoginMvpPresenter;
import in.becandid.app.becandid.ui.login.LoginMvpView;
import in.becandid.app.becandid.ui.login.LoginPresenter;
import in.becandid.app.becandid.ui.postDetails.PostsDetailsMvpPresenter;
import in.becandid.app.becandid.ui.postDetails.PostsDetailsMvpView;
import in.becandid.app.becandid.ui.postDetails.PostsDetailsPresenter;
import in.becandid.app.becandid.ui.profile.CustomUsernameMvpPresenter;
import in.becandid.app.becandid.ui.profile.CustomUsernameMvpView;
import in.becandid.app.becandid.ui.profile.CustomUsernamePresenter;
import in.becandid.app.becandid.ui.profile.DiscoverHistoryMvpPresenter;
import in.becandid.app.becandid.ui.profile.DiscoverHistoryMvpView;
import in.becandid.app.becandid.ui.profile.DiscoverHistoryPresenter;
import in.becandid.app.becandid.ui.profile.MyPostsMvpPresenter;
import in.becandid.app.becandid.ui.profile.MyPostsMvpView;
import in.becandid.app.becandid.ui.profile.MyPostsPresenter;
import in.becandid.app.becandid.ui.profile.NotificationMvpPresenter;
import in.becandid.app.becandid.ui.profile.NotificationMvpView;
import in.becandid.app.becandid.ui.profile.NotificationPresenter;
import in.becandid.app.becandid.ui.profile.ProfilePageMvpPresenter;
import in.becandid.app.becandid.ui.profile.ProfilePageMvpView;
import in.becandid.app.becandid.ui.profile.ProfilePagePresenter;
import in.becandid.app.becandid.ui.userpost.PostMvpPresenter;
import in.becandid.app.becandid.ui.userpost.PostMvpView;
import in.becandid.app.becandid.ui.userpost.PostPresenter;
import in.becandid.app.becandid.utils.rx.AppSchedulerProvider;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by janisharali on 27/01/17.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }


    @Provides
    ChatMvpPresenter<ChatMvpView> provideChatMvpPresenter(
            ChatPresenter<ChatMvpView> presenter) {
        return presenter;
    }

    @Provides
    DiscoverLatestMvpPresenter<DiscoverLatestMvpView> provideDiscoverLatestMvpPresenter(
            DiscoverLatestPresenter<DiscoverLatestMvpView> presenter) {
        return presenter;
    }

    @Provides
    CustomUsernameMvpPresenter<CustomUsernameMvpView> provideCustomUsernameMvpPresenter(
            CustomUsernamePresenter<CustomUsernameMvpView> presenter) {
        return presenter;
    }

    @Provides
    GroupSearchMvpPresenter<GroupSearchMvpView> provideGroupSearchMvpPresenter(
            GroupSearchPresenter<GroupSearchMvpView> presenter) {
        return presenter;
    }

    @Provides
    DiscoverFacebookMvpPresenter<DiscoverFacebookMvpView> provideDiscoverFacebookMvpPresenter(
            DiscoverFacebookPresenter<DiscoverFacebookMvpView> presenter) {
        return presenter;
    }

    @Provides
    DiscoverTrendingMvpPresenter<DiscoverTrendingMvpView> provideDiscoverTrendingMvpPresenter(
            DiscoverTrendingPresenter<DiscoverTrendingMvpView> presenter) {
        return presenter;
    }

    // Group folder
    // Create Group Presenters
    @Provides
    CreateGroupMvpPresenter<CreateGroupMvpView> provideCreateGroupMvpPresenter(
            CreateGroupPresenter<CreateGroupMvpView> presenter) {
        return presenter;
    }

    @Provides
    CreateGroupTagMvpPresenter<CreateGroupTagMvpView> provideCreateGroupTagMvpPresenter(
            CreateGroupTagPresenter<CreateGroupTagMvpView> presenter) {
        return presenter;
    }

    @Provides
    MyGroupsMvpPresenter<MyGroupsMvpView> provideMyGroupsMvpPresenter(
            MyGroupsPresenter<MyGroupsMvpView> presenter) {
        return presenter;
    }

    @Provides
    NewCommunityGroupMvpPresenter<NewCommunityGroupMvpView> provideNewCommunityGroupMvpPresenter(
            NewCommunityGroupPresenter<NewCommunityGroupMvpView> presenter) {
        return presenter;
    }

    @Provides
    EditGroupMvpPresenter<EditGroupMvpView> provideEditGroupMvpPresenter(
            EditGroupPresenter<EditGroupMvpView> presenter) {
        return presenter;
    }

    @Provides
    SearchMvpPresenter<SearchMvpView> provideSearchMvpPresenter(
            SearchPresenter<SearchMvpView> presenter) {
        return presenter;
    }

    // Profile page Component




    @Provides
    DiscoverHistoryMvpPresenter<DiscoverHistoryMvpView> provideDiscoverHistoryMvpPresenter(
            DiscoverHistoryPresenter<DiscoverHistoryMvpView> presenter) {
        return presenter;
    }

    @Provides
    DiscoverActivityMvpPresenter<DiscoverActivityMvpView> provideDiscoverActivityMvpPresenter(
            DiscoverActivityPresenter<DiscoverActivityMvpView> presenter) {
        return presenter;
    }

    @Provides
    NotificationMvpPresenter<NotificationMvpView> provideNotificationMvpPresenter(
            NotificationPresenter<NotificationMvpView> presenter) {
        return presenter;
    }

    @Provides
    MyPostsMvpPresenter<MyPostsMvpView> provideMyPostsMvpPresenter(
            MyPostsPresenter<MyPostsMvpView> presenter) {
        return presenter;
    }


    @Provides
    PremiumSearchResultMvpPresenter<PremiumSearchResultMvpView> providePremiumSearchResultMvpPresenter(
            PremiumSearchResultPresenter<PremiumSearchResultMvpView> presenter) {
        return presenter;
    }

    @Provides
    CommunityGroupMvpPresenter<CommunityGroupMvpView> provideCommunityGroupMvpPresenter(
            CommunityGroupPresenter<CommunityGroupMvpView> presenter) {
        return presenter;
    }


    @Provides
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }


    @Provides
    PostsDetailsMvpPresenter<PostsDetailsMvpView> providePostsDetailsMvpPresenter(
            PostsDetailsPresenter<PostsDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    ProfilePageMvpPresenter<ProfilePageMvpView> provideProfilePageMvpPresenter(
            ProfilePagePresenter<ProfilePageMvpView> presenter) {
        return presenter;
    }

    @Provides
    PostMvpPresenter<PostMvpView> providePostMvpPresenter(
            PostPresenter<PostMvpView> presenter) {
        return presenter;
    }



    @Provides
    UserGroupMvpPresenter<UserGroupMvpView> provideUserGroupMvpPresenter
            (
                    UserGroupPresenter<UserGroupMvpView> presenter) {
        return presenter;
    }

    /*@Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    AboutMvpPresenter<AboutMvpView> provideAboutPresenter(
            AboutPresenter<AboutMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    RatingDialogMvpPresenter<RatingDialogMvpView> provideRateUsPresenter(
            RatingDialogPresenter<RatingDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    FeedMvpPresenter<FeedMvpView> provideFeedPresenter(
            FeedPresenter<FeedMvpView> presenter) {
        return presenter;
    }

    @Provides
    OpenSourceMvpPresenter<OpenSourceMvpView> provideOpenSourcePresenter(
            OpenSourcePresenter<OpenSourceMvpView> presenter) {
        return presenter;
    }

    @Provides
    BlogMvpPresenter<BlogMvpView> provideBlogMvpPresenter(
            BlogPresenter<BlogMvpView> presenter) {
        return presenter;
    }

    @Provides
    FeedPagerAdapter provideFeedPagerAdapter(AppCompatActivity activity) {
        return new FeedPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    OpenSourceAdapter provideOpenSourceAdapter() {
        return new OpenSourceAdapter(new ArrayList<OpenSourceResponse.Repo>());
    }

    @Provides
    BlogAdapter provideBlogAdapter() {
        return new BlogAdapter(new ArrayList<BlogResponse.Blog>());
    }



    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    } */
}
