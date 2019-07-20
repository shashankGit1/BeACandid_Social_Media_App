package in.becandid.app.becandid.ui.group;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface CommunityGroupMvpPresenter <V extends CommunityGroupMvpView> extends MvpPresenter<V> {

    void getUserJoinedGroupsOnline(String user_id, String below18, String joined, int page);
    void getUserJoinedGroupsOnline02(String user_id, String below18, String joined, int page);

    void sendJoinedGroupsOnline(String id_categories, String user_id);

}
