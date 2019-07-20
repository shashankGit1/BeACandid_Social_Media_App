package in.becandid.app.becandid.ui.profile;

import in.becandid.app.becandid.ui.base.MvpPresenter;
import in.becandid.app.becandid.ui.group.CreateGroupMvpView;

public interface CustomUsernameMvpPresenter <V extends CustomUsernameMvpView> extends MvpPresenter<V> {

    void getSingleSearchNameOnline(String group_name, String id_user_name);

}
