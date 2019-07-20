package in.becandid.app.becandid.ui.profile;

import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.base.MvpView;

public interface CustomUsernameMvpView extends MvpView {

    void getSingleSearchName(SuccessResponse successResponse);

}
