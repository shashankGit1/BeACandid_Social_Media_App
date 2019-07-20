package in.becandid.app.becandid.ui.group;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface MyGroupsMvpPresenter<V extends MyGroupsMvpView> extends MvpPresenter<V> {

    void getListAllGroupsOnline(String userID);
    void getListUserJoinedGroups01(String user_id, String below18, String joined, String page);
    void getListUserJoinedGroups02(String user_id, String below18, String joined, String page);
}
