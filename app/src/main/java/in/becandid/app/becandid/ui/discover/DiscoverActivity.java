package in.becandid.app.becandid.ui.discover;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.FragmentTransaction;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.MenuItem;

import com.google.firebase.iid.FirebaseInstanceId;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.di.component.ActivityComponent;
import in.becandid.app.becandid.infrastructure.Account;
import in.becandid.app.becandid.ui.group.GroupsFragment;
import in.becandid.app.becandid.R;

import in.becandid.app.becandid.ui.profile.ContactService;
import in.becandid.app.becandid.ui.profile.ProfilePageFragment;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.login.StartPage01GetStarted;
import in.becandid.app.becandid.ui.chat.ChatMainPageFragment;
import in.becandid.app.becandid.ui.userpost.PostActivity;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class DiscoverActivity extends BaseActivity implements Constants, DiscoverActivityMvpView {

    @Inject
    DataManager mDataManager;

    protected CompositeDisposable disposables;


    private static Context applicationContext;

    private SharedPreferences prefs;
    BottomNavigationView navigation;
    /**
     * Is true if  the user is  in demo mode.
     * Otherwise is  false
     */

    @Inject
    DiscoverActivityMvpPresenter<DiscoverActivityMvpView> mPresenter;

    private boolean isDemoMode;

    private ActivityComponent activityComponent;


    public static Context getStaticApplicationContext() {
        return applicationContext;
    }

    private FloatingActionButton fab;


    @Override
    protected void setUp() {

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        navigation.getMenu().getItem(0).setCheckable(false);
        navigation.getMenu().getItem(1).setCheckable(false);
        navigation.getMenu().getItem(2).setCheckable(false);
        navigation.getMenu().getItem(3).setCheckable(false);
        navigation.getMenu().getItem(4).setCheckable(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        navigation.getMenu().getItem(0).setCheckable(true);
        navigation.getMenu().getItem(1).setCheckable(true);
        navigation.getMenu().getItem(2).setCheckable(true);
        navigation.getMenu().getItem(3).setCheckable(true);
        navigation.getMenu().getItem(4).setCheckable(true);
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_discover);

        getActivityComponent().inject(this);

       // getActivityComponent().inject(this);


        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(DiscoverActivity.this);

       // String deviceToken = FirebaseInstanceId.getInstance().getToken();

        disposables = new CompositeDisposable();

       // startService(new Intent(this, SyncService.class));

        //  sendOnlineTime();
        prefs = getSharedPreferences("Logged in or not", MODE_PRIVATE);
        isDemoMode = prefs.getBoolean("is this demo mode", false);
        if (!isDemoMode)
            checkAuthStatus();

        if (MySharedPreferences.getFireBaseToken(preferences) == null){
            if (FirebaseInstanceId.getInstance().getToken() != null){
                MySharedPreferences.registerFirebaseToken(preferences, FirebaseInstanceId.getInstance().getToken());
            }
        }

     /*   if (MySharedPreferences.getUserId(preferences) != null) {
            if (MySharedPreferences.checkFirstTimeToken(preferences) == null) {// call below method only once.

                if (MySharedPreferences.getFireBaseToken(preferences) != null) {
                    mPresenter.postSaveToken( MySharedPreferences.getUserId(preferences), MySharedPreferences.getFireBaseToken(preferences));

                  //  postFirebaseToken(MySharedPreferences.getFireBaseToken(preferences));
                } else {
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( DiscoverActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            String newToken = instanceIdResult.getToken();
                            Log.e("newToken",newToken);
                            mPresenter.postSaveToken( MySharedPreferences.getUserId(preferences), newToken);
                          //  postFirebaseToken(newToken);
                            MySharedPreferences.registerFirebaseToken(preferences, newToken);

                        }
                    });

                }

            }
        } */


     //   MySharedPreferences.AlreadyCheckFirstTime(preferences, "true");

        applicationContext = this.getApplicationContext();
        fab = (FloatingActionButton) findViewById(R.id.fab);


        // ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        // viewPager.setAdapter(new DiscoverActivityFragmentPagerAdapter(getSupportFragmentManager()));

 navigation = (BottomNavigationView) findViewById(R.id.navigation);
     //   BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_feed:
                        selectedFragment = FeedFragment.newInstance();
                        FragmentTransaction transaction01 = getSupportFragmentManager().beginTransaction();
                        transaction01.replace(R.id.frame_layout, selectedFragment);
                        transaction01.commit();
                        return true;
                    case R.id.navigation_groups:
                        selectedFragment = GroupsFragment.newInstance();
                        FragmentTransaction transaction02 = getSupportFragmentManager().beginTransaction();
                        transaction02.replace(R.id.frame_layout, selectedFragment);
                        transaction02.commit();
                        return true;
                    case R.id.navigation_post:
                        Intent intent = new Intent(DiscoverActivity.this, PostActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_me:
                        selectedFragment = ProfilePageFragment.newInstance();
                        FragmentTransaction transaction04 = getSupportFragmentManager().beginTransaction();
                        transaction04.replace(R.id.frame_layout, selectedFragment, "FRAGMENTTAG");
                        transaction04.commit();
                        return true;
                    case R.id.navigation_activity:
                        selectedFragment = ChatMainPageFragment.newInstance();
                        FragmentTransaction transaction05 = getSupportFragmentManager().beginTransaction();
                        transaction05.replace(R.id.frame_layout, ChatMainPageFragment.newInstance());
                        transaction05.commit();
                        return true;
                }
                return true;

            }


        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, FeedFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();

        disposables.add(bus.asFlowable().subscribe(
                event -> {
                    if (event instanceof Account.sendLikeNotify) {
                         mPresenter.sendLikeNotification(MySharedPreferences.getUserId(preferences), ((Account.sendLikeNotify) event).postId , ((Account.sendLikeNotify) event).likeUnlike, MySharedPreferences.getFireBaseToken(preferences));
                    //    Toast.makeText(this, "Received" + ((Account.sendLikeNotify) event).otherUserId, Toast.LENGTH_SHORT).show();
                    } else  if (event instanceof Account.sendSadEntry){
                          mPresenter.sendSadNotification(MySharedPreferences.getUserId(preferences),((Account.sendSadEntry) event).postId, ((Account.sendSadEntry) event).sadUnSad, MySharedPreferences.getFireBaseToken(preferences));
                    } else  if (event instanceof Account.makePostAdult){
                        mPresenter.postMakeAdult(((Account.makePostAdult) event).id_posts, ((Account.makePostAdult) event).adult_filter);

                    } else if (event instanceof Account.saveFirebaseToken){
                        mPresenter.postSaveToken(MySharedPreferences.getUserId(preferences), FirebaseInstanceId.getInstance().getToken());
                    }
                }));

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                    //    Log.d(TAG, "Received token broadcast");

                        String tokenVal = intent.getStringExtra(Constants.tokenVal);

                        MySharedPreferences.registerFirebaseToken(preferences, tokenVal);

                        if (MySharedPreferences.getUserId(preferences) == null) {
                            Timber.d("Do nothing");
                        } else {
                            mPresenter.postSaveToken(MySharedPreferences.getUserId(preferences), tokenVal);
                            //  postFirebaseToken(token);
                        }

                    //    Log.d(TAG, "Token Val: " + tokenVal);

                    }
                }, new IntentFilter(Constants.ACTION_TOKEN_BROADCAST)
        );




    }

    private void sendOnlineTime(){
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        mPresenter.updateTimeOnline(MySharedPreferences.getFireBaseToken(preferences), MySharedPreferences.getUserId(preferences));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    handler.postDelayed(this, 60000);
                   // handler.postDelayed(this, 6000);

                }
            }, 1000);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    //quits demo mode
  /*  public void onBackPressed() {

        if (isDemoMode)
            prefs.edit().putBoolean("is this demo mode", false).apply();

        super.onBackPressed();
    } */

    private void callFirebaseNetworkRequest() {
        if (MySharedPreferences.getFireBaseToken(preferences) == null) {
            // Delete firebase token and get new token

            final Handler handler4 = new Handler();
       /*     scheduler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 20 seconds

                    try {
                        mPresenter.pushToken(MySharedPreferences.getUserId(preferences));

                      //  checkPushToken();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 2000); */
        }
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }

    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra("token");
        }
    };

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();


        if (bus.hasObservers()){
            disposables.dispose();
        }

        super.onDestroy();
    }


    private void checkAuthStatus() {
        if (!application.getAuth().getUser().isLoggedIn()) {
            if (application.getAuth().hasAuthToken()) {
           //     callFirebaseNetworkRequest();
                // callFirebaseNetworkRequeddst();
                return;
            } else {
                startActivity(new Intent(this, StartPage01GetStarted.class));
                finish();
            }
        }
    }

    public static void makePrefered(Context c) {
        PackageManager p = c.getPackageManager();
        ComponentName cN = new ComponentName(c, DiscoverActivity.class);
        p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        Intent selector = new Intent(Intent.ACTION_MAIN);
        selector.addCategory(Intent.CATEGORY_HOME);
        c.startActivity(selector);
        p.setComponentEnabledSetting(cN, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }



    public void contactMethod() {

        startService(new Intent(DiscoverActivity.this, ContactService.class));
        //  dialogBox();
        application.getAuth().getUser().setAllContacts(true);
        // method to go to next page
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == getResources().getInteger(R.integer.contacts_request)) {
                contactMethod();
            } else if (requestCode == 34) {
                if (grantResults.length <= 0) {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    //  Log.i(TAG, "User interaction was cancelled.");
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted.
                    //    getLastLocation();
                  // postLocation();
                 //   MySharedPreferences.registerLocation(preferences, "true");

                } else {
                    // Permission denied.

                    // Notify the user via a SnackBar that they have rejected a core permission for the
                    // app, which makes the Activity useless. In a real app, core permissions would
                    // typically be best requested during a welcome-screen flow.

                    // Additionally, it is important to remember that a permission might have been
                    // rejected without asking the user for permission (device policy or "Never ask
                    // again" prompts). Therefore, a user interface affordance is typically implemented
                    // when permissions are denied. Otherwise, your app could appear unresponsive to
                    // touches or interactions which have required permissions.

                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("FRAGMENTTAG");
        fragment.onActivityResult(requestCode, resultCode, data);

    }




}

/*
private void checkPushToken() throws Exception {

        if (MySharedPreferences.getUserId(preferences) != null) {
            application.getWebService()
                    .pushToken(MySharedPreferences.getUserId(preferences))
                    .retryWhen(new RetryWithDelay(3, 2000))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<List<PushTokenCheck>>() {
                        @Override
                        public void onNext(List<PushTokenCheck> response) {

                            MySharedPreferences.AlreadyCheckFirstTime(preferences, "true");

                            if (response.get(0).getToken() == null || response.get(0).getToken().equals("")) {
                                startService(new Intent(DiscoverActivity.this, FirebaseNotificationService.class));
                            } else {
                                // enter that user has token
                                MySharedPreferences.registerFirebaseToken(preferences, "true");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }
                    });
        }


    }

     private void postToken(String id, String token) {
        try {
            application.getWebService()
                    .onlyToken(id, token)
                    .retryWhen(new RetryWithDelay(3, 2000))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<SuccessResponse>() {
                        @Override
                        public void onNext(SuccessResponse response) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postLocation() {
        try {
            application.getWebService()
                    .updateLocation(MySharedPreferences.getUserId(preferences), "true")
                    .retryWhen(new RetryWithDelay(3, 2000))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<SuccessResponse>() {
                        @Override
                        public void onNext(SuccessResponse response) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void postFirebaseToken(String token) {
        try {
            application.getWebService()
                    .onlyToken(MySharedPreferences.getUserId(preferences), token)
                    .retryWhen(new RetryWithDelay(3, 2000))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new BaseSubscriber<SuccessResponse>() {
                        @Override
                        public void onNext(SuccessResponse response) {
                            Timber.d("got result:" + response);
                            MySharedPreferences.AlreadyCheckFirstTime(preferences, "true");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
 */
