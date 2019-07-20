package in.becandid.app.becandid.ui.group;

import in.becandid.app.becandid.ui.base.MvpView;

public interface CreateGroupTagMvpView extends MvpView {

    void postNewCategory(GroupsCreatePOJO groupsCreatePOJO);

    void getImageUrl(String url);


}
