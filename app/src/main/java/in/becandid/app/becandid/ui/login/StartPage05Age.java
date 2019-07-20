package in.becandid.app.becandid.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.dto.CategoryJoined;
import in.becandid.app.becandid.dto.ContactAddResponse;
import in.becandid.app.becandid.dto.LoginResponse;
import in.becandid.app.becandid.dto.MainResponse;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.group.CommunityGroupPojoNew;
import in.becandid.app.becandid.ui.group.InsertGroupPOJO;
import in.becandid.app.becandid.ui.group.LoginGroupTagActivity;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;

public class StartPage05Age extends BaseActivity implements View.OnClickListener, LoginMvpView {
    private RadioGroup radioGroup;
    private Button select_gender_button;
    private RadioButton rb;
    private boolean radioButtonChecked = false;
    private String user_age="6";
    private View login_new_age_progressBar;

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page05);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(StartPage05Age.this);

        radioGroup = (RadioGroup) findViewById(R.id.ageRadioGroup);
        login_new_age_progressBar = (View) findViewById(R.id.login_new_age_progressBar);
        select_gender_button = (Button) findViewById(R.id.select_gender_button);
        radioGroup.clearCheck();

       // when the radio select is changed then we use this.
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonChecked = true;

            }
        });

        login_new_age_progressBar.setVisibility(View.GONE);
        select_gender_button.setOnClickListener(this);


        }

    @Override
    protected void setUp() {

    }

    private void setAge(String age){
        this.user_age = age;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_gender_button:

                login_new_age_progressBar.setVisibility(View.VISIBLE);

                if (radioButtonChecked){
                    int selectedId=radioGroup.getCheckedRadioButtonId();
                    rb =(RadioButton)findViewById(selectedId);
                    String text = (String) rb.getText();
                    getAgeId(text);

                    try {
                        mPresenter.sendAgeOnline(MySharedPreferences.getUserId(preferences),user_age);
                       // select_age();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //  Toast.makeText(StartPage05Age.this, text, Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(this, "Please select one age group", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }



    private void getAgeId(String age){
        for (int i = 0; i < 5;i++){
            if (age.equals("13 to 17 years")){
                setAge("1");
            } else if (age.equals("18 to 24 years")){
                setAge("2");
            } else if(age.equals("25 to 34 years")){
                setAge("3");
            } else if (age.equals("35 to 44 years")){
                setAge("4");
            } else if (age.equals("45+ years")){
                setAge("5");
            }
        }
    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void sendData(LoginResponse loginResponse) {

    }

    @Override
    public void skipUser(LoginResponse loginResponse) {

    }

    @Override
    public void facebookFriends(MainResponse mainResponse) {

    }

    @Override
    public void facebookFriends02(MainResponse mainResponse) {

    }

    @Override
    public void sendFacebookFriends(ContactAddResponse contactAddResponse) {

    }

    @Override
    public void sendAge(SuccessResponse successResponse) {
        if (user_age.equals("1")){
            MySharedPreferences.RegisterBelow18(preferences,"true");
        }

        Intent intent = new Intent(StartPage05Age.this, LoginGroupTagActivity.class);
        intent.putExtra(Constants.IDUSERNAME, MySharedPreferences.getUserId(preferences));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        login_new_age_progressBar.setVisibility(View.GONE);
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

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        super.onDestroy();
    }
}

/*
private void select_age() throws Exception {

        application.getWebService()
                .update_age(MySharedPreferences.getUserId(preferences),user_age)
                //  .getFacebookContactPosts(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", "true", currentPage)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<SuccessResponse>() {
                    @Override
                    public void onNext(SuccessResponse response) {
                        if (user_age.equals("1")){
                            MySharedPreferences.RegisterBelow18(preferences,"true");
                        }

                        Intent intent = new Intent(StartPage05Age.this, LoginGroupTagActivity.class);
                        intent.putExtra(Constants.IDUSERNAME, MySharedPreferences.getUserId(preferences));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        login_new_age_progressBar.setVisibility(View.GONE);
                        //  fillAutoSpacingLayout(response);
                    }
                    @Override
                    public void onError(Throwable e){
                        login_new_age_progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                });
    }
 */
