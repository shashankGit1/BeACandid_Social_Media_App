package in.becandid.app.becandid.ui;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;

import in.becandid.app.becandid.infrastructure.MySharedPreferences;

import static in.becandid.app.becandid.ui.base.Constants.CONSTANT_PREF_FILE;

public class FirebaseNotificationService extends IntentService {


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }


    public static final String BROADCAST=
            "in.voiceme.app.voiceme.discover_page.FirebaseNotificationService.BROADCAST";
    private static Intent broadcast=new Intent(BROADCAST);


    SharedPreferences preferences;
    private final IBinder mBinder = new MyNotificationBinder();



    public class MyNotificationBinder extends Binder {
        FirebaseNotificationService getService(){
            return FirebaseNotificationService.this;
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // SystemClock.sleep(5000);

    }

    public FirebaseNotificationService() {
        super("ContactService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Handler mainHandler = new Handler(this.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
               // getContacts();
                if (MySharedPreferences.getFireBaseToken(preferences) == null){
                    try {
                        FirebaseInstanceId.getInstance().deleteInstanceId();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
                            @Override
                            public void onSuccess(InstanceIdResult instanceIdResult) {
                                String deviceToken = instanceIdResult.getToken();
                                postFirebaseToken(deviceToken);
                                // Do whatever you want with your token now
                                // i.e. store it on SharedPreferences or DB
                                // or directly send it to server
                            }
                        });
                    }
                }
            } // This is your code
        };
        mainHandler.post(myRunnable);
        return super.onStartCommand(intent, flags, startId);
    }

    private void postFirebaseToken(String token){
     /*   try {
            ((VoicemeApplication)getApplication()).getWebService()
                    .onlyToken(MySharedPreferences.getUserId(preferences), token)
                    .retryWhen(new RetryWithDelay(3,2000))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new BaseSubscriber<SuccessResponse>() {
                        @Override
                        public void onNext(SuccessResponse response) {
                            Timber.d("got result:" + response);
                            MySharedPreferences.registerFirebaseToken(preferences, "true");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
