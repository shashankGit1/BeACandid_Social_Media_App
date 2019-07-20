package in.becandid.app.becandid.ui.filter;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface PremiumSearchResultMvpPresenter  <V extends PremiumSearchResultMvpView> extends MvpPresenter<V> {
    void getAgeGenderPost01(String id_user_name, String gender, String user_date_of_birth, String premiumuser, int page);
    void getAgeGenderPost02(String id_user_name, String gender, String user_date_of_birth, String premiumuser, int page);

    void getSearchPosts(String id_user_name, String search_word, String premium, int page);
    void getSearchPosts02(String id_user_name, String search_word, String premium, int page);
}
