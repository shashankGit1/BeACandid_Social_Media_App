package in.becandid.app.becandid;

import in.becandid.app.becandid.di.rxbus.RxBus;
import in.becandid.app.becandid.ui.base.VoicemeApplication;

/**
 * Created by harish on 12/16/2016.
 */

public abstract class BaseLiveService {

    protected final RxBus bus;
    protected final VoicemeApplication application;

    protected BaseLiveService(VoicemeApplication application) {
        this.application = application;
        bus = application.getBus();
        //getBus.register(this);
    }
}
