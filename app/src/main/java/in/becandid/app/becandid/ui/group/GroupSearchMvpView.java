package in.becandid.app.becandid.ui.group;

import java.util.List;

import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.base.MvpView;

public interface GroupSearchMvpView extends MvpView {

    void getResponse(List<GroupUser> response);
}
