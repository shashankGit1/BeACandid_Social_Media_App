package in.becandid.app.becandid.ui.profile;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface NotificationMvpPresenter <V extends NotificationMvpView> extends MvpPresenter<V> {

    void getNotificationOnline(String id_posts, String page);

}
