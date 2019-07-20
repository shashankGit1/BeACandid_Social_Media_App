package in.becandid.app.becandid.ui.group;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface NewCommunityGroupMvpPresenter<V extends NewCommunityGroupMvpView> extends MvpPresenter<V> {

    void sendJoinedGroupsOnline(String id_categories, String user_id);

    void getLatestUserJoinedGroupsOnline02(String user_id, String below18, String joined, int page);
    void getLatestUserJoinedGroupsOnline(String user_id, String below18, String joined, int page);
}
