package in.becandid.app.becandid.ui.discover;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface DiscoverActivityMvpPresenter<V extends DiscoverActivityMvpView> extends MvpPresenter<V> {

    void sendLikeNotification(String user_id, String post_id, String like, String token);
    void postSaveToken(String id_user_name, String pushnotificationToken);
    void sendSadNotification(String senderId, String postId, String sad, String token);
    void postMakeAdult(String id_posts, String adult_filter);
    void updateTimeOnline(String token, String user_id);
}
