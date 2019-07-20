package in.becandid.app.becandid.ui.profile;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface ProfilePageMvpPresenter <V extends ProfilePageMvpView> extends MvpPresenter<V> {
    void getUserProfileOnline(String user_id);

    void onFacebookLoginClick(String name, String email, String userId, String deviceId, String socialNetwork);

    void faceboookFriends(String accessToken, String limit);

    void faceboookFriends02(String access_token, String limit, String after);

    void sendFacebookFriends(String id_user_name, String facebook_id);


    void sendAuthorisation(String secretWord, String user_id);





}
