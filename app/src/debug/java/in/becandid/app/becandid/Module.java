package in.becandid.app.becandid;

import in.becandid.app.becandid.ui.base.VoicemeApplication;

/**
 * Created by harish on 12/16/2016.
 */

public class Module {

    public static void register(VoicemeApplication application) {
        new AccountLiveService(application);
    }

}
