package in.becandid.app.becandid.ui.group;

import java.util.List;

import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.base.MvpView;

public interface UserGroupMvpView extends MvpView {
    void getGroupPosts(List<PostsModel> response);
    void getGroupPosts02(List<PostsModel> response);

    void sendJoinGroup(InsertGroupPOJO insertGroupPOJO);
    void sendUnJoinGroup(InsertGroupPOJO insertGroupPOJO);
    void getGroupSpecific(List<CommunityGroupPojo> response);

}
