package in.becandid.app.becandid.ui.profile;

import java.util.List;

import in.becandid.app.becandid.ui.base.MvpView;

public interface NotificationMvpView extends MvpView {

    void getNotification(List<NotificationPojo> notificationPojos);

}
