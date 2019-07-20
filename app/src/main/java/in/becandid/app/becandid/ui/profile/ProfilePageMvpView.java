package in.becandid.app.becandid.ui.profile;

import in.becandid.app.becandid.dto.ContactAddResponse;
import in.becandid.app.becandid.dto.LoginResponse;
import in.becandid.app.becandid.dto.MainResponse;
import in.becandid.app.becandid.dto.ProfileMain;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.base.MvpView;

public interface ProfilePageMvpView extends MvpView {
    void getUserProfile(ProfileMain profileMain);

    void sendData(LoginResponse loginResponse);

    //   void skipUser(String deviceId, String socialNetwork);

    void facebookFriends(MainResponse mainResponse);

    void facebookFriends02(MainResponse mainResponse);

    void sendFacebookFriends(ContactAddResponse contactAddResponse);
    void getAccess(SuccessResponse contactAddResponse);





}
