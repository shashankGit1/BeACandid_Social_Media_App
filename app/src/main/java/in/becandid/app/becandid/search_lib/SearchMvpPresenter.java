package in.becandid.app.becandid.search_lib;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface SearchMvpPresenter <V extends SearchMvpView> extends MvpPresenter<V> {

    void getSearchGroup(String search_word, String user_id);

}
