package in.becandid.app.becandid.search_lib;

import java.util.List;

import in.becandid.app.becandid.ui.base.MvpView;
import in.becandid.app.becandid.ui.group.CommunityGroupPojo;

public interface SearchMvpView extends MvpView {

    void getSearchGroup(List<CommunityGroupPojo> communityGroupPojoList);

}
