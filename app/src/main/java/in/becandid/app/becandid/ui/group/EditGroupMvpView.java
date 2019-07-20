package in.becandid.app.becandid.ui.group;

import java.util.List;

import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.base.MvpView;

public interface EditGroupMvpView extends MvpView {

    void getGroupSpecific(List<CommunityGroupPojo> response);

    void editGroupSuccess(SuccessResponse response);

    void getImageUrl(String image);


}
