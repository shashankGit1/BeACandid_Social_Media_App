package in.becandid.app.becandid.ui.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.dto.CategoryJoined;
import in.becandid.app.becandid.dto.ContactAddResponse;
import in.becandid.app.becandid.dto.LoginResponse;
import in.becandid.app.becandid.dto.MainResponse;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.group.CommunityGroupPojoNew;
import in.becandid.app.becandid.ui.group.InsertGroupPOJO;
import timber.log.Timber;

import static in.becandid.app.becandid.ui.base.VoicemeApplication.getContext;


public class StartPage02FacebookLogin extends BaseActivity implements LoginMvpView {
    private CallbackManager callbackManager;
    private List<String> list_of_facebook;
    private LoginButton facebookSignInBtn;
    //   private ProgressBar progressBar;
    private String accessToken;
    private String  uuuid;

    View progressFrame;

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page02);
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(StartPage02FacebookLogin.this);

        progressFrame = findViewById(R.id.register_after_login);
        getToolbar().setTitle("Candid Login");

        list_of_facebook = new ArrayList<>();


        facebookSignInBtn = (LoginButton) this.findViewById(R.id.signin_with_facebook_btn);
        list_of_facebook = new ArrayList<>();

        this.setUpFacebookSignIn();



    }

   /* @OnClick(R.id.signin_with_facebook_btn)
    void onServerLoginClick(View v) {
        mPresenter.onServerLoginClick(mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString());
    }
    */

    @Override
    protected void setUp() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_skip_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.skip_menu:

                progressFrame.setVisibility(View.VISIBLE);

                uuuid = android.provider.Settings.Secure.getString(getContext().getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
                //     String removedall = RegexHandler.removeSpecialCharacters(uuuid);

                try {
                    mPresenter.skipUser(uuuid,"0");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // We are coming back from the Facebook login activity
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * Configures (or hides) the Facebook sign in button
     */
    private void setUpFacebookSignIn() {

        String facebookAppId = getString(R.string.FACEBOOK_APP_ID);

        callbackManager = CallbackManager.Factory.create();

        facebookSignInBtn.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends")); //"user_gender","user_age_range","user_location",
        facebookSignInBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                StartPage02FacebookLogin.this.handleFacebookLogin(loginResult);

            }

            @Override
            public void onCancel() {
                Timber.v("User cancelled log in with Facebook");
            }

            @Override
            public void onError(FacebookException error) {
                StartPage02FacebookLogin.this.handleFacebookError(error);
            }
        });
    }



    private void skipUserData(LoginResponse response){

        try{
            UserData(response);
            application.getAuth().setAuthToken("token");
            application.getAuth().getUser().setLoggedIn(true);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            Intent intent = new Intent(StartPage02FacebookLogin.this, StartPage04Gender.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            progressFrame.setVisibility(View.GONE);
        }
    }

    private void existingUser(LoginResponse response){


        try{
            UserData(response);
            // todo network request
          //  getFriendList(accessToken);
            saveFacebook();
            application.getAuth().setAuthToken("token");
            application.getAuth().getUser().setLoggedIn(true);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            Intent intent = new Intent(StartPage02FacebookLogin.this, StartPage04Gender.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void newUser(LoginResponse response){
        try{
            UserDataFirst(response);
            // todo network request
            mPresenter.faceboookFriends(accessToken, "25");

          //  getFriendList(accessToken);
            saveFacebook();
            application.getAuth().setAuthToken("token");
            application.getAuth().getUser().setLoggedIn(true);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            Intent intent = new Intent(StartPage02FacebookLogin.this, StartPage04Gender.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }



    private void saveFacebook(){
        MySharedPreferences.registerFacebook(preferences, "true");
    }

    private synchronized void UserData(LoginResponse response) {
        MySharedPreferences.registerUserId(preferences, response.info.getId());
    //    MySharedPreferences.registerUserToken(preferences, response.info.getUser_token());

        Timber.d("the user ID is " + response.info.getId());

        Timber.e("Successfully entered the value inside SharedPreferences");
    }


    private synchronized void UserDataFirst(LoginResponse response) {
        MySharedPreferences.registerUserId(preferences, response.info.getId());
      //  MySharedPreferences.registerSocialID(preferences, response.info.getUserId());
//        MySharedPreferences.registerUserToken(preferences, response.info.getUser_token());

        Timber.d("the user ID is " + response.info.getId());

        Timber.e("Successfully entered the value inside SharedPreferences");
    }

    /**
     * Handle a Facebook login error
     *
     * @param error the error that should be handled.
     */
    private void handleFacebookError(FacebookException error) {

        String message = String.format("Failed to authenticate against Facebook %s - \"%s\"",
                error.getClass().getSimpleName(), error.getLocalizedMessage());
    }

    /**
     * Handle a Facebook login success
     *
     * @param loginResult the successful login result
     */
    private void handleFacebookLogin(LoginResult loginResult) {
        if (progressFrame == null){
            progressFrame = findViewById(R.id.register_after_login);
        }
        progressFrame.setVisibility(View.VISIBLE);

        accessToken = loginResult.getAccessToken().getToken();

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
                            //    String age_range = me.optString("age_range");
                            //   String user_location = me.optString("location");
                            //     String gender = me.optString("gender");
                            //   String user_age_range = me.optString("user_age_range");



                            try {

                                //   String accessToken, String userId, String name, String email, String deviceId, String socialNetwork

                                //todo enter network request
                                mPresenter.onFacebookLoginClick(String.valueOf(first_name + " " + last_name), email, id, uuuid, "1");
                              //  sendData(String.valueOf(first_name + " " + last_name), email, id, android_id, "1" );
                                //       getData(String.valueOf(first_name + " " + last_name), email, id, android_id, "1");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            // send email and id to your web server
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void sendData(LoginResponse loginResponse) {
        // data after facebook login.
        // todo network call need to check if user is new or old. new user gets to
        // starting 3 pages. other moves out
        existingUser(loginResponse);
    }

    @Override
    public void skipUser(LoginResponse loginResponse) {
        // data after skipping user

        if (loginResponse.status==2){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartPage02FacebookLogin.this);
            alertDialogBuilder.setTitle("You are blocked from using this app");
            alertDialogBuilder.setMessage("Admin of Candid has blocked you from using this app. You cannot use this app any more. Sorry for inconvenience.");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
// continue with discard
                }
            });
            alertDialogBuilder.show();
        } else {
            skipUserData(loginResponse);
        }
    }

    @Override
    public void facebookFriends(MainResponse response) {
        // existingUser(response, socialNetwork);
        Timber.d(response.getSummary().getTotalCount().toString());

        if (response.getPaging().getNext()!= null){
            addAllFacebookId(response);
            try {
                mPresenter.faceboookFriends02(accessToken, "25", response.getPaging().getCursors().getAfter() );
              //  getFriendListPart2(response, accessToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            addAllFacebookIdLoop(response);
            Timber.d("Finished");
        }
    }

    @Override
    public void facebookFriends02(MainResponse response) {

        if (response.getPaging().getNext()!= null){
            addAllFacebookId(response);
            try {

                repeatNetWork(response);
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
        Toast.makeText(this, "number of friends " + contactAddResponse.getInsertedRows(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendAge(SuccessResponse successResponse) {

    }

    @Override
    public void sendGender(SuccessResponse successResponse) {

    }

    @Override
    public void getUserJoinedGroupsFirstPage(List<CommunityGroupPojoNew> communityGroupPojoNewList) {

    }

    @Override
    public void getUserJoinedGroupsSecondPage(List<CommunityGroupPojoNew> communityGroupPojoNewList) {

    }

    @Override
    public void sendCommunityGroupList(InsertGroupPOJO insertGroupPOJO) {

    }

    @Override
    public void getAllGroups(List<CategoryJoined> categoryJoined) {

    }

    @Override
    public void joinGroups(InsertGroupPOJO insertGroupPOJO) {

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

    private void addAllFacebookId(MainResponse response){

        try{
            for (int i = 0; i < response.getData().size(); i++){
                list_of_facebook.add(response.getData().get(i).getId());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void sendAllFacebookId(List<String> response){
        try {
            mPresenter.sendFacebookFriends(MySharedPreferences.getUserId(preferences), response.toString().replace("[", "").replace("]", "").replace(" ", ""));
          //  sendAllFacebookFriends();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void repeatNetWork(MainResponse response){
        try {
            mPresenter.faceboookFriends02(accessToken, "25", response.getPaging().getCursors().getAfter() );

         //   getFriendListPart2(response, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        super.onDestroy();
    }

}

/*



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

    private void repeatNetWork(MainResponse response, String token){
        try {
            getFriendListPart2(response, token);
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

    private void addAllFacebookId(MainResponse response){

        try{
            for (int i = 0; i < response.getData().size(); i++){
                list_of_facebook.add(response.getData().get(i).getId());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }


 */

/***************

done


private void getData(String name, String email, String userId, String deviceId, String socialNetwork) throws Exception {
        application.getWebService()
                .login(name, email, userId, deviceId, socialNetwork)
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

    private void getDataSkip(String deviceId, String socialNetwork) throws Exception {
        application.getWebService()
                .login(deviceId, socialNetwork)
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse response) {

                        if (socialNetwork.contains("0")){
                            skipUser(response, socialNetwork);
                        } else {
                            existingUser(response, socialNetwork);

                        }

                        //   if (response.info.getPresent().equals("yes")){
                        //  } else {
                        //     newUser(response);
                        //  }
                    }
                });
    }


    private void postToken(String id, String token){
        try {
            application.getWebService()
                    .onlyToken(id, token)
                    .retryWhen(new RetryWithDelay(3,2000))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<SuccessResponse>() {
                        @Override
                        public void onNext(SuccessResponse response) {
                            Timber.d(response.getSuccess().toString());
                            Intent intent = new Intent(StartPage02FacebookLogin.this, StartPage04Gender.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            //    MySharedPreferences.registerUsername(preferences, usernameText);
                            //Todo add network call for uploading profile_image file
                            //    startActivity(new Intent(LoginUserDetails.this, MainActivity.class));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void postTokenNewUser(String id, String token){
        try {
            application.getWebService()
                    .onlyToken(id, token)
                    .retryWhen(new RetryWithDelay(3,2000))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<SuccessResponse>() {
                        @Override
                        public void onNext(SuccessResponse response) {
                            Intent intent = new Intent(StartPage02FacebookLogin.this, StartPage01GetStarted.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            //    MySharedPreferences.registerUsername(preferences, usernameText);
                            //Todo add network call for uploading profile_image file
                            //    startActivity(new Intent(LoginUserDetails.this, MainActivity.class));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

 */