package in.becandid.app.becandid.ui.group;

import java.util.List;

import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.ui.base.MvpView;

public interface MyGroupsMvpView extends MvpView {

    void getListAllGroups(List<GroupUser> groupUsers);
    void getListUserJoinedGroups01(List<CommunityGroupPojoNew> communityGroupPojoNew);
    void getListUserJoinedGroups02(List<CommunityGroupPojoNew> communityGroupPojoNew);
}
