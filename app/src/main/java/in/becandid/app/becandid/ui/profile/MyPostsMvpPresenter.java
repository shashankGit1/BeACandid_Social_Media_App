package in.becandid.app.becandid.ui.profile;

import in.becandid.app.becandid.ui.base.MvpPresenter;

public interface MyPostsMvpPresenter <V extends MyPostsMvpView> extends MvpPresenter<V> {

    void getSingleUserPostsOnline(String id_user, String user_id, int page);
    void getSingleUserPostsOnline02(String id_user, String user_id, int page);
}
