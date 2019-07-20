package in.becandid.app.becandid.ui.group;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface CreateGroupMvpPresenter <V extends CreateGroupMvpView> extends MvpPresenter<V> {

    void getSingleSearchNameOnline(String group_name);

}
