package in.becandid.app.becandid.notifications_page;

import android.app.IntentService;
import android.content.Intent;

import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.ui.profile.ChatTextPojo;

/**
 * Created by harishpc on 6/18/2017.
 */
public class ChatNotificationService extends IntentService {
  //  private Bus getBus;
   // private Bus getBus;

    public ChatNotificationService() {
        super("ChatNotificationService");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    //    application = (VoicemeApplication)getApplicationContext();
    //    getBus = application.getBus();
    }

    @Override
    public void onCreate() {
        super.onCreate();

   //     getBus = ((VoicemeApplication)getApplication()).getBus();

   //     getBus.register(this);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */


    public ChatNotificationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ChatTextPojo chatMessage = intent.getExtras().getParcelable(Constants.CHAT_MESSAGE);

    }

    @Override
    public void onDestroy() {
   //     getBus.unregister(this);

        super.onDestroy();
    }

    //callbacks interface for communication with service clients!
    public interface Callbacks{
        public void updateClient(long data);
    }
}
