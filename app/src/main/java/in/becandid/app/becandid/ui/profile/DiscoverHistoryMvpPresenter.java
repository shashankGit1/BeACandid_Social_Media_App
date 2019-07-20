package in.becandid.app.becandid.ui.profile;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;


@PerActivity
public interface DiscoverHistoryMvpPresenter <V extends DiscoverHistoryMvpView> extends MvpPresenter<V>{

    void getHistoryPostsOnline(String id_user_name, String user_id, String group_post, int page);
    void getHistoryPostsOnline02(String id_user_name, String user_id, String group_post, int page);

}
