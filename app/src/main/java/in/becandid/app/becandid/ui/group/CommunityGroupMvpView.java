package in.becandid.app.becandid.ui.group;

import java.util.List;

import in.becandid.app.becandid.ui.base.MvpView;

public interface CommunityGroupMvpView extends MvpView {

    void getUserJoinedGroups(List<CommunityGroupPojoNew> communityGroupPojoNews);
    void getUserJoinedGroups02(List<CommunityGroupPojoNew> communityGroupPojoNews);
    void sendJoinedGroups(InsertGroupPOJO insertGroupPOJO);







}
