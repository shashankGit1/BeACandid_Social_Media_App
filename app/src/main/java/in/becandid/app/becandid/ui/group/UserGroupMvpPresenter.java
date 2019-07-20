package in.becandid.app.becandid.ui.group;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface UserGroupMvpPresenter <V extends UserGroupMvpView> extends MvpPresenter<V> {
    void getGroupPosts(String group_id, String user_id, String page);
    void getGroupPosts02(String group_id, String user_id, String page);

    void sendJoinGroup(String group_ids, String user_id);
    void sendUnJoinGroup(String group_ids, String user_id);
    void getGroupSpecific(String group_id, String user_id, String page);

}
