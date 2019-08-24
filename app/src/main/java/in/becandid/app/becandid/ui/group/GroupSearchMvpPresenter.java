package in.becandid.app.becandid.ui.group;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface GroupSearchMvpPresenter <V extends GroupSearchMvpView> extends MvpPresenter<V> {

    void samplenetwork(String userID);

}
