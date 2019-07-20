package in.becandid.app.becandid.ui.group;

import java.io.File;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface CreateGroupTagMvpPresenter<V extends CreateGroupTagMvpView> extends MvpPresenter<V> {

    void postNewCategoryOnline(String id_categories, String user_id, String group_name, String group_image, String group_description);

    void postImageUpload(File file);

}
