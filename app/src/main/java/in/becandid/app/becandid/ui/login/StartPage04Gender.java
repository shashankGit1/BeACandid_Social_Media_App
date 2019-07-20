package in.becandid.app.becandid.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.dto.CategoryJoined;
import in.becandid.app.becandid.dto.ContactAddResponse;
import in.becandid.app.becandid.dto.LoginResponse;
import in.becandid.app.becandid.dto.MainResponse;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.group.CommunityGroupPojoNew;
import in.becandid.app.becandid.ui.group.InsertGroupPOJO;

public class StartPage04Gender extends BaseActivity implements View.OnClickListener, LoginMvpView {
    private TextView select_female_user_icon;
    private TextView select_lgbt_user_icon;
    private ImageView female_user_icon;
    private ImageView transgender_user_icon;
    private ImageView male_user_icon;
    private TextView select_male_user_icon;
    private boolean select_male_user_icon_boolean;
    private boolean select_lgbt_user_icon_boolean;
    private boolean select_female_user_icon_boolean;
    private Button select_gender_button;
    private TextView select_gender_button_skip;
    private View progressFrame;

    String selectGender = null;

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step04);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(StartPage04Gender.this);


        progressFrame = findViewById(R.id.login_age_progressBar);
        select_female_user_icon = (TextView) findViewById(R.id.select_female_user_icon);
        select_gender_button_skip = (TextView) findViewById(R.id.select_gender_button_skip);
        select_male_user_icon = (TextView) findViewById(R.id.select_male_user_icon);
        select_lgbt_user_icon = (TextView) findViewById(R.id.select_lgbt_user_icon);
        female_user_icon = (ImageView) findViewById(R.id.female_user_icon);
        male_user_icon = (ImageView) findViewById(R.id.male_user_icon);
        transgender_user_icon = (ImageView) findViewById(R.id.transgender_user_icon);
        select_gender_button = (Button) findViewById(R.id.select_gender_button);

        select_female_user_icon.setOnClickListener(this);
        select_lgbt_user_icon.setOnClickListener(this);
        female_user_icon.setOnClickListener(this);
        transgender_user_icon.setOnClickListener(this);
        male_user_icon.setOnClickListener(this);
        select_male_user_icon.setOnClickListener(this);
        select_gender_button.setOnClickListener(this);
        select_gender_button_skip.setOnClickListener(this);

        progressFrame.setVisibility(View.GONE);


    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_female_user_icon:


                getSelectedButton();
                setGender("2");

                select_female_user_icon_boolean = true;

                select_female_user_icon.setTextColor(getResources().getColor(R.color.white));
                select_female_user_icon.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));

                break;
            case R.id.select_lgbt_user_icon:

                getSelectedButton();
                setGender("3");

                select_lgbt_user_icon_boolean = true;


                select_lgbt_user_icon.setTextColor(getResources().getColor(R.color.white));
                select_lgbt_user_icon.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));

                break;
            case R.id.female_user_icon:

                getSelectedButton();
                setGender("2");

                select_female_user_icon_boolean = true;


                select_female_user_icon.setTextColor(getResources().getColor(R.color.white));
                select_female_user_icon.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));

                break;
            case R.id.transgender_user_icon:

                getSelectedButton();
                setGender("3");

                select_lgbt_user_icon_boolean = true;


                select_lgbt_user_icon.setTextColor(getResources().getColor(R.color.white));
                select_lgbt_user_icon.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));

                break;
            case R.id.male_user_icon:

                getSelectedButton();
                setGender("1");

                select_male_user_icon_boolean = true;

                select_male_user_icon.setTextColor(getResources().getColor(R.color.white));
                select_male_user_icon.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));

                break;
            case R.id.select_male_user_icon:

                getSelectedButton();
                setGender("1");

                select_male_user_icon_boolean = true;


                select_male_user_icon.setTextColor(getResources().getColor(R.color.white));
                select_male_user_icon.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));

                break;
            case R.id.select_gender_button:

                progressFrame.setVisibility(View.VISIBLE);

                try {

                    mPresenter.sendGenderOnline(MySharedPreferences.getUserId(preferences),selectGender);

                  //
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //   Toast.makeText(this, "Button Selected" + selectGender, Toast.LENGTH_SHORT).show();

                break;
            case R.id.select_gender_button_skip:

                progressFrame.setVisibility(View.VISIBLE);

                getSelectedButton();
                setGender("4");

                try {
                    mPresenter.sendGenderOnline(MySharedPreferences.getUserId(preferences),selectGender);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //   Toast.makeText(this, "Button Selected" + selectGender, Toast.LENGTH_SHORT).show();

                break;

        }
    }





        private void getSelectedButton() {
            if (select_male_user_icon_boolean) {
                select_male_user_icon_boolean = false;
                select_male_user_icon.setTextColor(getResources().getColor(R.color.black));
                select_male_user_icon.setBackground(getResources().getDrawable(R.drawable.button_tag));
            } else if (select_lgbt_user_icon_boolean) {
                select_lgbt_user_icon_boolean = false;
                select_lgbt_user_icon.setTextColor(getResources().getColor(R.color.black));
                select_lgbt_user_icon.setBackground(getResources().getDrawable(R.drawable.button_tag));
            } else if (select_female_user_icon_boolean) {
                select_female_user_icon_boolean = false;
                select_female_user_icon.setTextColor(getResources().getColor(R.color.black));
                select_female_user_icon.setBackground(getResources().getDrawable(R.drawable.button_tag));
            }
        }

    private void setGender(String selectGender){
        this.selectGender = selectGender;
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

    }

    @Override
    public void sendGender(SuccessResponse successResponse) {
        // existingUser(response, socialNetwork);

        Intent intent = new Intent(StartPage04Gender.this, StartPage05Age.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        progressFrame.setVisibility(View.GONE);

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

 private void joinCategory() throws Exception {

        application.getWebService()
                .update_gender(MySharedPreferences.getUserId(preferences),selectGender)
                //  .getFacebookContactPosts(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", "true", currentPage)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<SuccessResponse>() {
                    @Override
                    public void onNext(SuccessResponse response) {


                        Intent intent = new Intent(StartPage04Gender.this, StartPage05Age.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        progressFrame.setVisibility(View.GONE);
                        //  fillAutoSpacingLayout(response);
                    }
                    @Override
                    public void onError(Throwable e){
                        progressFrame.setVisibility(View.GONE);
                        e.printStackTrace();

           }
        });
    }

 */