package in.becandid.app.becandid.ui.discover;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface DiscoverFacebookMvpPresenter <V extends DiscoverFacebookMvpView> extends MvpPresenter<V> {

    void getFacebookPostsOnline(String id_user_name, String user_id, String facebookId, String page);
    void getFacebookPostsOnline02(String id_user_name, String user_id, String facebookId, String page);

}


