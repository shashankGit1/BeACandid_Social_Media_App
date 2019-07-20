package in.becandid.app.becandid.ui.discover;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface DiscoverTrendingMvpPresenter <V extends DiscoverTrendingMvpView> extends MvpPresenter<V> {

    void getTrendingPostsOnline(String userID, String trending, String page);
    void getPendingPostsOnline(String userID, String trending, String page);
    void getImagePostsOnline(String userID, String trending, String page);
    void getTrendingPostsOnline02(String userID, String trending, String page);
    void getPendingPostsOnline02(String userID, String trending, String page);
    void getImagePostsOnline02(String userID, String trending, String page);
}
