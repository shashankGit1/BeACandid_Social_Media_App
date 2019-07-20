package in.becandid.app.becandid.ui.group;

import java.util.List;

import in.becandid.app.becandid.ui.base.MvpView;

public interface NewCommunityGroupMvpView extends MvpView {

    void sendJoinedGroups(InsertGroupPOJO insertGroupPOJO);
    void getLatestUserJoinedGroups(List<CommunityGroupPojoNew> communityGroupPojoNews);
    void getLatestUserJoinedGroups02(List<CommunityGroupPojoNew> communityGroupPojoNews);

}
