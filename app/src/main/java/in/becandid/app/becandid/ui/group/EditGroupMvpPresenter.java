package in.becandid.app.becandid.ui.group;

import java.io.File;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface EditGroupMvpPresenter<V extends EditGroupMvpView> extends MvpPresenter<V> {

    void getGroupSpecific(String group_id, String user_id, String page);
    void editGroup(String id_categories, String name, String group_description, String group_id);
    void editGroupWithImage(String id_categories, String name, String group_image_url, String group_description, String group_id);

    void postImageUpload(File file);

}
