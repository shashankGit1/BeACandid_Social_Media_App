package in.becandid.app.becandid.ui.discover;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface DiscoverLatestMvpPresenter <V extends DiscoverLatestMvpView> extends MvpPresenter<V> {

    void getLatestPostsOnline(String userID, int page);
    void getLatestPostsOnline02(String userID, int page);


}
