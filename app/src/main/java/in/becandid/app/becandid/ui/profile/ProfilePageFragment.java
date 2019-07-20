/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package in.becandid.app.becandid.ui.profile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.di.component.ActivityComponent;
import in.becandid.app.becandid.dto.ContactAddResponse;
import in.becandid.app.becandid.dto.LoginResponse;
import in.becandid.app.becandid.dto.MainResponse;
import in.becandid.app.becandid.dto.ProfileMain;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseFragment;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.utils.ActivityUtils;
import timber.log.Timber;

public class ProfilePageFragment extends BaseFragment implements View.OnClickListener, ProfilePageMvpView {
    View view;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private double Longitude;
    private double Latitude;
    private AccessTokenTracker mAccessTokenTracker;
    ShareDialog shareDialog;


    /*
     * Provides the entry point to the Fused Location Provider API.
     */
//    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Represents a geographical locationPOJO.
     */
    protected Location mLastLocation;
    private List<String> list_of_facebook;
    ImageView profile_image;
    private TextView user_username;
    ImageView user_settings;
    ProgressBar profile_page_progress;
    private CallbackManager callbackManager;
    private String accessToken;
    Button user_share_button;/* user_location_button*/
    LoginButton user_facebook_button;/* user_location_button*/
    TextView facebook_friends_count, contacts_friends_id, contacts_friends_name, user_post_count, user_post_name, user_group_joined_count, user_group_joined_name;

    public static ProfilePageFragment newInstance() {
        ProfilePageFragment fragment = new ProfilePageFragment();
        return fragment;
    }

    @Inject
    ProfilePageMvpPresenter<ProfilePageMvpView> mPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_item_one, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);

        }

        list_of_facebook = new ArrayList<>();
        profile_page_progress= (ProgressBar) view.findViewById(R.id.profile_page_progress);
        // user_location_button= (Button) view.findViewById(R.id.user_location_button);
        user_share_button = (Button) view.findViewById(R.id.user_share_button);
        user_username = (TextView) view.findViewById(R.id.user_username);
        user_settings= (ImageView) view.findViewById(R.id.user_settings);
        user_facebook_button= (LoginButton) view.findViewById(R.id.user_facebook_button);
        facebook_friends_count = (TextView) view.findViewById(R.id.facebook_friends_count);
        contacts_friends_id = (TextView) view.findViewById(R.id.contacts_friends_id);
        user_post_count = (TextView) view.findViewById(R.id.user_post_count);
        user_group_joined_count = (TextView) view.findViewById(R.id.user_group_joined_count);
        profile_page_progress.setVisibility(View.VISIBLE);



        if (MySharedPreferences.getFacebook(preferences)!= null){
            user_facebook_button.setVisibility(View.GONE);
       }

        user_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomUsernameActivity.class);

                startActivity(intent);

            }
        });

  //      if (MySharedPreferences.getLocation(preferences)!= null){
        //    user_location_button.setVisibility(View.GONE);
  //      }

    //    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            mPresenter.getUserProfileOnline(MySharedPreferences.getUserId(preferences));
           // getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        user_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SettingsActivity.class);
             //   Intent intent = new Intent(getActivity(), StartPage01GetStarted.class);

              //  Intent intent = new Intent(getActivity(), AdPageActivity.class);
              //  Intent intent = new Intent(getActivity(), AdPageActivity.class);
                startActivity(intent);


             /*   if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=in.beacandid.app.beacandid"))
                            .build();
                    shareDialog.show(linkContent);
                } */



            }
        });



        setUpFacebookSignIn();
        addViewPager();
        user_share_button.setOnClickListener(this);
     //   user_location_button.setOnClickListener(this);

        shareDialog = new ShareDialog(this);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        return view;
    }

    private void saveFacebook(){
        MySharedPreferences.registerFacebook(preferences, "true");
    }

    private void requestObjectUser(final AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (response.getError() != null) {
                    // handle error
                } else {
                    Toast.makeText(getActivity(), "Access Token: " + accessToken.getToken(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //add all pages
    private void addPages(ViewPager pager) {
        ProfileFragmentPagerAdapter adapter =
                new ProfileFragmentPagerAdapter(getChildFragmentManager());
        adapter.addPage(MyPostsFragment.newInstance(1));
        adapter.addPage(DiscoverHistoryFragment.newInstance(1));
        adapter.addPage(NotificationFragment.newInstance());

        //set adapter to pager
        pager.setAdapter(adapter);
    }



    private void existingUser(LoginResponse response){

        try{
            UserData(response);
            mPresenter.faceboookFriends(accessToken, "25");
           // getFriendList(accessToken);
            saveFacebook();
            application.getAuth().setAuthToken("token");
            application.getAuth().getUser().setLoggedIn(true);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            Toast.makeText(getActivity(), "Successful Login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), DiscoverActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private synchronized void UserData(LoginResponse response) {
        MySharedPreferences.registerUserId(preferences, response.info.getId());
      //  MySharedPreferences.registerUserToken(preferences, response.info.getUser_token());

        Timber.d("the user ID is " + response.info.getId());

        Timber.e("Successfully entered the value inside SharedPreferences");
    }



    private void addAllFacebookIdLoop(MainResponse response){

        try{
            for (int i = 0; i < response.getData().size(); i++){
                list_of_facebook.add(response.getData().get(i).getId());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            sendAllFacebookId(list_of_facebook);
        }

    }

    private void sendAllFacebookId(List<String> response){
        try {
            mPresenter.sendFacebookFriends(MySharedPreferences.getUserId(preferences), response.toString().replace("[", "").replace("]", "").replace(" ", ""));
       //     sendAllFacebookFriends(response.toString().replace("[", "").replace("]", "").replace(" ", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void addAllFacebookId(MainResponse response){

        try{
            for (int i = 0; i < response.getData().size(); i++){
                list_of_facebook.add(response.getData().get(i).getId());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

  /*  public void getData() throws Exception {
        application.getWebService()
                .getUserProfile(MySharedPreferences.getUserId(preferences))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2000))
                .subscribe(new BaseSubscriber<ProfileMain>() {
                    @Override
                    public void onNext(ProfileMain response) {

                        Timber.e("Got user details");
                        //     followers.setText(String.valueOf(response.size()));
                        profileData(response);
//                        avatarProgressFrame.setVisibility(View.GONE);
//                        progressFrame.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        avatarProgressFrame.setVisibility(View.GONE);
//                        progressFrame.setVisibility(View.GONE);
                        try {
                            Timber.e(e.getMessage());
                            //   Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }
    */

    private void repeatNetWork(MainResponse response, String token){
        try {
            mPresenter.faceboookFriends02(token, "25", response.getPaging().getCursors().getAfter());
        //    getFriendListPart2(response, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void profileData(ProfileMain response) {
        String username = MySharedPreferences.getUserId(preferences);

        if (response.getUser_nick_name()==null){
            user_username.setText("Click here to enter username");
        } else {
            user_username.setText(response.getUser_nick_name());

        }
        user_post_count.setText(""+response.getPostsCount());
        user_group_joined_count.setText(""+response.getGroupsCount());
        profile_page_progress.setVisibility(View.GONE);
    }

    public void addViewPager() {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.activity_profile_viewpager);
        this.addPages(viewPager);

        // Give the PagerSlidingTabStrip the ViewPager


        TabLayout tabsStrip = (TabLayout) view.findViewById(R.id.tabs_main_activity);
        // Attach the view pager to the tab strip
        tabsStrip.setupWithViewPager(viewPager);

    }

    private void setUpFacebookSignIn() {

        String facebookAppId = getString(R.string.FACEBOOK_APP_ID);

        // The Facebook application ID must be defined in res/values/configuration_strings.xml
        if (facebookAppId.isEmpty()) {
            user_facebook_button.setVisibility(View.GONE);
            //     findViewById(R.id.signin_no_facebook_signin_txt).setVisibility(View.VISIBLE);
            return;
        }

        callbackManager = CallbackManager.Factory.create();

        user_facebook_button.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends"));
        user_facebook_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookLogin(loginResult);

            }

            @Override
            public void onCancel() {
                Timber.v("User cancelled log in with Facebook");
            }

            @Override
            public void onError(FacebookException error) {
                handleFacebookError(error);
            }
        });
    }

    private void handleFacebookError(FacebookException error) {

        String message = String.format("Failed to authenticate against Facebook %s - \"%s\"",
                error.getClass().getSimpleName(), error.getLocalizedMessage());

        Timber.e(message);

    }


    private void handleFacebookLogin(LoginResult loginResult) {
        ProgressBar progressFrame = view.findViewById(R.id.profile_page_progress);
        if (progressFrame.getVisibility()==View.INVISIBLE){
            progressFrame.setVisibility(View.VISIBLE);
        }
      //  accessToken = loginResult.getAccessToken().getToken();

        //  final Map<String, String> logins = new HashMap<>();
        //    logins.put(FACEBOOK_LOGIN, AccessToken.getCurrentAccessToken().getToken());
        //    Log.v(TAG, String.format("Facebook token <<<\n%s\n>>>", logins.get(FACEBOOK_LOGIN)));

        GraphRequest graphRequest = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject me, GraphResponse response) {
                        if (response.getError() != null) {
                            Timber.i(response.getError().getErrorMessage());
                        } else {
                            String email = me.optString("email");
                            String id = me.optString("id");
                            //    String gender = me.optString("gender");
                            String first_name = me.optString("first_name");
                            String last_name = me.optString("last_name");
                            String age_range = me.optString("age_range");

                            try {
                                mPresenter.onFacebookLoginClick(String.valueOf(first_name + " " + last_name), email, id, android_id, "1");
                              //  getData(String.valueOf(first_name + " " + last_name), email, id, android_id, "1");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            // send email and id to your web server
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,gender,age_range");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_share_button:

             //   Intent intent = new Intent(getActivity(), SettingsActivity.class);
                //   Intent intent = new Intent(getActivity(), StartPage01GetStarted.class);

              //    Intent intent = new Intent(getActivity(), AdPageActivity.class);
                //  Intent intent = new Intent(getActivity(), AdPageActivity.class);
               // startActivity(intent);
             //   profile_page_progress.setVisibility(View.VISIBLE);
             //   readContacts();



        /*        String deviceId = android.provider.Settings.Secure.getString(getContext().getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
                String secretToken;

                if (MySharedPreferences.getSecretToken(preferences) != null){
                    secretToken = MySharedPreferences.getSecretToken(preferences);

                } else {
                    secretToken = "2e8138fdee95a98";

                }

                String network = Temp.S(secretToken, deviceId);

                String networkclean = network.trim();


                mPresenter.sendAuthorisation(networkclean, "2"); */


                break;
          /*  case R.id.user_location_button:
                if (!checkPermissions()) {
                    requestPermissions();
                }
                break; */

        }
    }



    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
         //   Log.i(TAG, "Displaying permission rationale to provide additional context.");

        } else {
        //    Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    private void readContacts() {
        ActivityUtils.isContactsPermission(getActivity());
    }

    public void contactMethod(){

        getActivity().startService(new Intent(getActivity(), ContactService.class));
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
            } else if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
                if (grantResults.length <= 0) {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    //  Log.i(TAG, "User interaction was cancelled.");
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted.
                    //    getLastLocation();
                 //   postLocation();
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
    public void getUserProfile(ProfileMain response) {
        Timber.e("Got user details");
        //     followers.setText(String.valueOf(response.size()));
        profileData(response);
    }

    @Override
    public void sendData(LoginResponse response) {
        existingUser(response);

    }

    @Override
    public void facebookFriends(MainResponse response) {
        mPresenter.faceboookFriends02(accessToken, "25", response.getPaging().getCursors().getAfter());

    }

    @Override
    public void facebookFriends02(MainResponse response) {
        if (response.getPaging().getNext()!= null){
            addAllFacebookId(response);
            try {
                repeatNetWork(response, accessToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            addAllFacebookIdLoop(response);
            Timber.d("Finished");
        }
    }

    @Override
    public void sendFacebookFriends(ContactAddResponse contactAddResponse) {

    }

    @Override
    public void getAccess(SuccessResponse contactAddResponse) {

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();

        super.onDestroyView();
    }
}

/*
private void getData(String name, String email, String userId, String deviceId, String socialNetwork) throws Exception {
        application.getWebService()
                .profileSkipLogin(name, email, userId,deviceId, socialNetwork)
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse response) {

                        //   if (response.info.getPresent().equals("yes")){
                        existingUser(response, socialNetwork);
                        //  } else {
                        //     newUser(response);
                        //  }
                    }
                });
    }

    private void sendAllFacebookFriends(String contacts) throws Exception {
        application.getWebService()
                .addAllFacebookId(MySharedPreferences.getUserId(preferences), contacts)
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<ContactAddResponse>() {
                    @Override
                    public void onNext(ContactAddResponse response) {
                        Timber.e("Got user details " + response.getInsertedRows().toString());

                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Timber.e(e.getMessage());
                            //   Toast.makeText(ChangeProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
    }

     public void getData() throws Exception {
        application.getWebService()
                .getUserProfile(MySharedPreferences.getUserId(preferences))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2000))
                .subscribe(new BaseSubscriber<ProfileMain>() {
                    @Override
                    public void onNext(ProfileMain response) {

                        Timber.e("Got user details");
                        //     followers.setText(String.valueOf(response.size()));
                        profileData(response);
//                        avatarProgressFrame.setVisibility(View.GONE);
//                        progressFrame.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        avatarProgressFrame.setVisibility(View.GONE);
//                        progressFrame.setVisibility(View.GONE);
                        try {
                            Timber.e(e.getMessage());
                            //   Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }

    private void postLocation(){
        try {
            application.getWebService()
                    .updateLocation(MySharedPreferences.getUserId(preferences), "true")
                    .retryWhen(new RetryWithDelay(3,2000))
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

    private void getFriendListPart2(MainResponse response, String token) throws Exception {
        application.getWebService()
                .getFriends(token, "25", response.getPaging().getCursors().getAfter())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<MainResponse>() {
                    @Override
                    public void onNext(MainResponse response) {

                        if (response.getPaging().getNext()!= null){
                            addAllFacebookId(response);
                            try {
                                repeatNetWork(response, token);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            addAllFacebookIdLoop(response);
                            Timber.d("Finished");
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Timber.e(e.getMessage());
                            //   Toast.makeText(ChangeProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
    }

     // Todo enter number of results that you want in result
    private void getFriendList(String accessToken) throws Exception {
        application.getWebService()
                .getFriendsFirst(accessToken, "25")
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<MainResponse>() {
                    @Override
                    public void onNext(MainResponse response) {
                        Timber.d(response.getSummary().getTotalCount().toString());

                        if (response.getPaging().getNext()!= null){
                            addAllFacebookId(response);
                            try {
                                getFriendListPart2(response, accessToken);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            addAllFacebookIdLoop(response);
                            Timber.d("Finished");
                        }

                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Timber.e(e.getMessage());
                            //   Toast.makeText(ChangeProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
    }
 */
