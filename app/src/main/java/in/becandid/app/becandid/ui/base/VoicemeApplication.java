package in.becandid.app.becandid.ui.base;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.androidnetworking.AndroidNetworking;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

import in.becandid.app.becandid.R;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.di.component.ApplicationComponent;
import in.becandid.app.becandid.di.component.DaggerApplicationComponent;
import in.becandid.app.becandid.di.module.ApplicationModule;
import in.becandid.app.becandid.di.rxbus.RxBus;
import in.becandid.app.becandid.infrastructure.Auth;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Harish on 7/20/2016.
 */
public class VoicemeApplication extends MultiDexApplication {

    @Inject
    DataManager mDataManager;

    private static Auth auth;
    private static Context context;
    // private WebService webService;
    public FirebaseAnalytics sAnalytics;

    protected RxBus bus;

    /*@Inject
    CalligraphyConfig mCalligraphyConfig;
    */



  /*  private class MainThreadBus extends RxBus {
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void post(final Object event){
            if (Looper.myLooper() == Looper.getMainLooper()){
                super.post(event);
            } else {
                mHandler.post(() -> {
                    MainThreadBus.super.post(event);
                });
            }
        }
    }
    */

    protected ApplicationComponent applicationComponent;

    @Inject
    DataManager dataManager;

    public static VoicemeApplication get(Context context) {
        return (VoicemeApplication) context.getApplicationContext();
    }

   /* public VoicemeApplication() {
        getBus = new MainThreadBus();
    }
    */

    public static Context getContext() {
        return context;
    }

    public static Auth getAuth() {
        return auth;
    }

   /* public static Bus getBus() {
        return getBus;
    }
    */

    public RxBus getBus() {
        if (bus == null) {
            bus = new RxBus();
        }

        return bus;
    }



    @Override
    public void onCreate() {
        super.onCreate();


        //Fabric.with(this, new Crashlytics());
        auth = new Auth(this);



        sAnalytics = FirebaseAnalytics.getInstance(this);


        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationComponent.inject(this);



      /*  try {
            webService = ServiceFactory.createRetrofitService(WebService.class);
        } catch (NullPointerException ex){
            Timber.e("Null Pointer exception");
        } catch (Exception ex){
            ex.printStackTrace();
        }
        */



        /* **************************************
        Timber.plant(new Timber.DebugTree() {
            // Add the line number to the TAG
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });

*/

      //  FacebookSdk.sdkInitialize(this);

        /* *****************************************/
        //     Fabric.with(this, new Crashlytics());
      //   Timber.plant(new ReleaseTree());


        context = getApplicationContext();
        /*
         *Creates a periodic job to refresh token
         */

        //   LeakCanary.install(this);

      /*  Timber.d("Setting up StrictMode policy checking.");
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
                */

        Fresco.initialize(this);
        bus = new RxBus();



        final Fabric fabric = new Fabric.Builder(this)
                .kits( new Crashlytics())
                .debuggable(true)
                .build();

        Fabric.with(fabric);

        AndroidNetworking.initialize(getApplicationContext());


        // initDatabase();
    }


    public FirebaseAnalytics getFireBase(){
        return sAnalytics;
    }

   /* synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }

    */







//    public WebService getWebService() {
    //    return webService;
  //  }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }

    /*
    private void initDatabase() {
        Realm.init(instance);
        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
    */


}