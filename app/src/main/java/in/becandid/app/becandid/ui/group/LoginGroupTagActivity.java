package in.becandid.app.becandid.ui.group;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.dto.ContactAddResponse;
import in.becandid.app.becandid.dto.LoginResponse;
import in.becandid.app.becandid.dto.MainResponse;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.dto.CategoryJoined;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.login.LoginMvpPresenter;
import in.becandid.app.becandid.ui.login.LoginMvpView;

import static in.becandid.app.becandid.ui.base.Constants.IDUSERNAME;

public class LoginGroupTagActivity extends BaseActivity implements View.OnClickListener, LoginMvpView {
    Button btnSubmit;
    String image_url;
    boolean select_category = false;
    String id_user_name;
    ProgressBar create_new_group;

    boolean boolean_group_interest_politics;
    boolean boolean_group_interest_celebrities;
    boolean boolean_group_interest_music;
    boolean boolean_group_interest_technology;
    boolean boolean_group_interest_fashion;
    boolean boolean_group_interest_business;
    boolean boolean_group_interest_school;
    boolean boolean_group_interest_art;
    boolean boolean_group_interest_photography;
    boolean boolean_group_interest_lgbt;
    boolean boolean_group_interest_relationship;
    boolean boolean_group_interest_sports;
    boolean boolean_group_interest_funny;
    boolean boolean_group_interest_confessions;
    boolean boolean_group_interest_family;
    boolean boolean_group_interest_work;
    boolean boolean_group_interest_faith;
    boolean boolean_group_interest_food;
    boolean boolean_group_interest_entertainment;
    boolean boolean_group_interest_personal;
    boolean boolean_group_interest_women_issues;
    boolean boolean_group_interest_sex;
    boolean boolean_group_interest_science;
    boolean boolean_group_interest_health;
    boolean boolean_group_interest_men_issues;
    boolean boolean_group_interest_teen;

    TextView group_interest_science;
    TextView group_interest_health;
    TextView group_interest_men_issues;
    TextView group_interest_teen;

    TextView group_interest_politics;
    TextView group_interest_celebrities;
    TextView group_interest_music;
    TextView group_interest_technology;
    TextView group_interest_fashion;
    TextView group_interest_business;
    TextView group_interest_school;
    TextView group_interest_art;
    TextView group_interest_photography;
    TextView group_interest_lgbt;
    TextView group_interest_relationship;
    TextView group_interest_sports;
    TextView group_interest_funny;
    TextView group_interest_confessions;
    TextView group_interest_family;
    TextView group_interest_work;
    TextView group_interest_faith;
    TextView group_interest_food;
    TextView group_interest_entertainment;
    TextView group_interest_personal;
    TextView group_interest_women_issues;
    TextView group_interest_sex;
    private List<String> joinedGroupsList;

    String selectedGroup = null;

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_group_tag);
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(LoginGroupTagActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        joinedGroupsList = new ArrayList<>();
        btnSubmit = (Button) findViewById(R.id.login_btnSubmit);

        // if user came from login then they provide id_user_name and it passes to second login activity.
        if (getIntent() != null){
            Intent intent = getIntent();
            id_user_name = intent.getStringExtra(IDUSERNAME); // seconnd person username random
        }


        create_new_group = (ProgressBar) findViewById(R.id.login_create_new_group);

        create_new_group.setVisibility(View.VISIBLE);

        group_interest_science = (TextView) findViewById(R.id.login_group_interest_science);
        group_interest_health = (TextView) findViewById(R.id.login_group_interest_health);
        group_interest_men_issues = (TextView) findViewById(R.id.login_group_interest_men_issues);
        group_interest_teen = (TextView) findViewById(R.id.login_group_interest_teen);
        group_interest_politics = (TextView) findViewById(R.id.login_group_interest_politics);
        group_interest_personal = (TextView) findViewById(R.id.login_group_interest_personal);
        group_interest_celebrities = (TextView) findViewById(R.id.login_group_interest_celebrities);
        group_interest_music = (TextView) findViewById(R.id.login_group_interest_music);
        group_interest_technology = (TextView) findViewById(R.id.login_group_interest_technology);
        group_interest_fashion = (TextView) findViewById(R.id.login_group_interest_fashion);
        group_interest_business = (TextView) findViewById(R.id.login_group_interest_business);
        group_interest_school = (TextView) findViewById(R.id.login_group_interest_school);
        group_interest_art = (TextView) findViewById(R.id.login_group_interest_art);
        group_interest_photography = (TextView) findViewById(R.id.login_group_interest_photography);
        group_interest_lgbt = (TextView) findViewById(R.id.login_group_interest_lgbt);
        group_interest_relationship = (TextView) findViewById(R.id.login_group_interest_relationship);
        group_interest_sports = (TextView) findViewById(R.id.login_group_interest_sports);
        group_interest_funny = (TextView) findViewById(R.id.login_group_interest_funny);
        group_interest_confessions = (TextView) findViewById(R.id.login_group_interest_confessions);
        group_interest_family = (TextView) findViewById(R.id.login_group_interest_family);
        group_interest_work = (TextView) findViewById(R.id.login_group_interest_work);
        group_interest_faith = (TextView) findViewById(R.id.login_group_interest_faith);
        group_interest_food = (TextView) findViewById(R.id.login_group_interest_food);
        group_interest_women_issues = (TextView) findViewById(R.id.login_group_interest_women_issues);
        group_interest_entertainment = (TextView) findViewById(R.id.login_group_interest_entertainment);
        group_interest_sex = (TextView) findViewById(R.id.login_group_interest_sex);

        if (MySharedPreferences.getBelow18(preferences) != null){
            group_interest_sex.setVisibility(View.INVISIBLE);
        }


        group_interest_science.setOnClickListener(this);
        group_interest_health.setOnClickListener(this);
        group_interest_men_issues.setOnClickListener(this);
        group_interest_teen.setOnClickListener(this);
        group_interest_personal.setOnClickListener(this);
        group_interest_politics.setOnClickListener(this);
        group_interest_celebrities.setOnClickListener(this);
        group_interest_music.setOnClickListener(this);
        group_interest_technology.setOnClickListener(this);
        group_interest_fashion.setOnClickListener(this);
        group_interest_business.setOnClickListener(this);
        group_interest_school.setOnClickListener(this);
        group_interest_art.setOnClickListener(this);
        group_interest_photography.setOnClickListener(this);
        group_interest_lgbt.setOnClickListener(this);
        group_interest_relationship.setOnClickListener(this);
        group_interest_sports.setOnClickListener(this);
        group_interest_funny.setOnClickListener(this);
        group_interest_confessions.setOnClickListener(this);
        group_interest_family.setOnClickListener(this);
        group_interest_work.setOnClickListener(this);
        group_interest_faith.setOnClickListener(this);
        group_interest_food.setOnClickListener(this);
        group_interest_entertainment.setOnClickListener(this);
        group_interest_women_issues.setOnClickListener(this);
        group_interest_sex.setOnClickListener(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String listOfCategories = String.valueOf(joinedGroupsList.toString().replace("[", "").replace("]", ""));


                try {
                    create_new_group.setVisibility(View.VISIBLE);
                    mPresenter.joinGroupsOnline(MySharedPreferences.getUserId(preferences),listOfCategories);
                    //joinCategory();
                  //  joinCategory();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {
           // getAllCategories(MySharedPreferences.getUserId(preferences));
            mPresenter.getAllGroupsOnline(MySharedPreferences.getUserId(preferences));

     //       getAllCategories();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void setUp() {

    }





    private void setJoinedCategories(List<CategoryJoined> response){
        for (int i = 0; i < response.size(); i++){
            switch (response.get(i).getId_categories()){
                case 1:
                    boolean_group_interest_politics = true;
                    //setGroupId
                    addGroupTag("1");
                    group_interest_politics.setTextColor(getResources().getColor(R.color.white));
                    group_interest_politics.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_politics.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_politics_selected, 0, 0, 0);

                    break;
                case 2:
                    boolean_group_interest_celebrities = true;
                    addGroupTag("2");
                    //setGroupId
                    group_interest_celebrities.setTextColor(getResources().getColor(R.color.white));
                    group_interest_celebrities.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_celebrities.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_celebrities_selected, 0, 0, 0);


                    break;
                case 3:
                    boolean_group_interest_music = true;
                    addGroupTag("3");
                    //setGroupId
                    group_interest_music.setTextColor(getResources().getColor(R.color.white));
                    group_interest_music.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_music.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_music_selected, 0, 0, 0);


                    break;
                case 4:
                    boolean_group_interest_technology = true;
                    addGroupTag("4");
                    //setGroupId
                    group_interest_technology.setTextColor(getResources().getColor(R.color.white));
                    group_interest_technology.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_technology.setCompoundDrawablePadding(10);
                    group_interest_technology.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_technology_selected, 0, 0, 0);


                    break;
                case 5:
                    boolean_group_interest_fashion = true;
                    addGroupTag("5");
                    //setGroupId
                    group_interest_fashion.setTextColor(getResources().getColor(R.color.white));
                    group_interest_fashion.setCompoundDrawablePadding(10);
                    group_interest_fashion.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_fashion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_fashion_selected, 0, 0, 0);


                    break;
                case 6:
                    boolean_group_interest_business = true;
                    addGroupTag("6");
                    //setGroupId
                    group_interest_business.setTextColor(getResources().getColor(R.color.white));
                    group_interest_business.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_business.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_business_selected, 0, 0, 0);


                    break;
                case 7:
                    boolean_group_interest_school = true;
                    addGroupTag("7");
                    //setGroupId
                    group_interest_school.setTextColor(getResources().getColor(R.color.white));
                    group_interest_school.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_school.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_school_selected, 0, 0, 0);


                    break;
                case 8:
                    boolean_group_interest_art = true;
                    addGroupTag("8");
                    //setGroupId
                    group_interest_art.setTextColor(getResources().getColor(R.color.white));
                    group_interest_art.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_art.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_art_selected, 0, 0, 0);


                    break;
                case 9:
                    boolean_group_interest_photography = true;
                    addGroupTag("9");
                    //setGroupId
                    group_interest_photography.setTextColor(getResources().getColor(R.color.white));
                    group_interest_photography.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_photography.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_photography_selected, 0, 0, 0);


                    break;
                case 10:
                    boolean_group_interest_lgbt = true;
                    addGroupTag("10");
                    //setGroupId
                    group_interest_lgbt.setTextColor(getResources().getColor(R.color.white));
                    group_interest_lgbt.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_lgbt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_lgbt_selected, 0, 0, 0);


                    break;
                case 11:
                    boolean_group_interest_relationship = true;
                    addGroupTag("11");
                    group_interest_relationship.setTextColor(getResources().getColor(R.color.white));
                    group_interest_relationship.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_relationship.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_relationships_selected, 0, 0, 0);


                    break;
                case 12:
                    boolean_group_interest_sports = true;
                    addGroupTag("12");
                    group_interest_sports.setTextColor(getResources().getColor(R.color.white));
                    group_interest_sports.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_sports.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_sports_selected, 0, 0, 0);


                    break;
                case 13:
                    boolean_group_interest_funny = true;
                    addGroupTag("13");
                    //setGroupId
                    group_interest_funny.setTextColor(getResources().getColor(R.color.white));
                    group_interest_funny.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_funny.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_funny_selected, 0, 0, 0);


                    break;
                case 14:
                    boolean_group_interest_confessions = true;
                    addGroupTag("14");
                    //setGroupId
                    group_interest_confessions.setTextColor(getResources().getColor(R.color.white));
                    group_interest_confessions.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_confessions.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_confessions_selected, 0, 0, 0);


                    break;
                case 15:
                    boolean_group_interest_personal = true;
                    addGroupTag("15");
                    //setGroupId
                    group_interest_personal.setTextColor(getResources().getColor(R.color.white));
                    group_interest_personal.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_personal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_personal_selected, 0, 0, 0);


                    break;
                case 16:
                    boolean_group_interest_sex = true;
                    addGroupTag("16");
                    //setGroupId
                    group_interest_sex.setTextColor(getResources().getColor(R.color.white));
                    group_interest_sex.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_sex.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_sex_selected, 0, 0, 0);


                    break;
                case 17:
                    boolean_group_interest_family= true;
                    addGroupTag("17");
                    //setGroupId
                    group_interest_family.setTextColor(getResources().getColor(R.color.white));
                    group_interest_family.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_family.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_family_selected, 0, 0, 0);


                    break;
                case 18:
                    boolean_group_interest_work = true;
                    addGroupTag("18");
                    //setGroupId
                    group_interest_work.setTextColor(getResources().getColor(R.color.white));
                    group_interest_work.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_work.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_work_selected, 0, 0, 0);


                    break;
                case 19:
                    boolean_group_interest_faith = true;
                    addGroupTag("19");
                    //setGroupId
                    group_interest_faith.setTextColor(getResources().getColor(R.color.white));
                    group_interest_faith.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_faith.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_faith_selected, 0, 0, 0);



                    break;
                case 20:
                    boolean_group_interest_food = true;
                    addGroupTag("20");
                    //setGroupId
                    group_interest_food.setTextColor(getResources().getColor(R.color.white));
                    group_interest_food.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_food.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_food_selected, 0, 0, 0);


                    break;
                case 21:
                    boolean_group_interest_entertainment = true;
                    addGroupTag("21");
                    //setGroupId
                    group_interest_entertainment.setTextColor(getResources().getColor(R.color.white));
                    group_interest_entertainment.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_entertainment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_entertainment_selected, 0, 0, 0);


                    break;
                case 22:
                    boolean_group_interest_women_issues = true;
                    addGroupTag("22");
                    //setGroupId
                    group_interest_women_issues.setTextColor(getResources().getColor(R.color.white));
                    group_interest_women_issues.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_women_issues.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_womens_issues_selected, 0, 0, 0);


                    break;

                case 23:

                    boolean_group_interest_science = true;
                    addGroupTag("23");
                    //setGroupId
                    group_interest_science.setTextColor(getResources().getColor(R.color.white));
                    group_interest_science.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_science.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_school_selected, 0, 0, 0);


                    break;
                case 24:
                    boolean_group_interest_men_issues = true;
                    addGroupTag("24");
                    //setGroupId
                    group_interest_men_issues.setTextColor(getResources().getColor(R.color.white));
                    group_interest_men_issues.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_men_issues.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_mens_issues_selected, 0, 0, 0);


                    break;
                case 25:
                    boolean_group_interest_science = true;
                    addGroupTag("25");
                    //setGroupId
                    group_interest_science.setTextColor(getResources().getColor(R.color.white));
                    group_interest_science.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_science.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_science_selected, 0, 0, 0);


                    break;
                case 26:
                    boolean_group_interest_teen = true;
                    addGroupTag("25");
                    //setGroupId
                    group_interest_teen.setTextColor(getResources().getColor(R.color.white));
                    group_interest_teen.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_teen.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_teens_selected, 0, 0, 0);


                    break;


            }
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_group_interest_politics:

                if (boolean_group_interest_politics){
                    removeGroupTag("1");
                    boolean_group_interest_politics = false;
                    group_interest_politics.setTextColor(getResources().getColor(R.color.black));
                    group_interest_politics.setBackground(getResources().getDrawable(R.drawable.button_tag_big));
                    group_interest_politics.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_politics, 0, 0, 0);
                } else {
                    boolean_group_interest_politics = true;
                    //setGroupId
                    addGroupTag("1");
                    group_interest_politics.setTextColor(getResources().getColor(R.color.white));
                    group_interest_politics.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_politics.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_politics_selected, 0, 0, 0);

                }


                break;
            case R.id.login_group_interest_personal:

                if (boolean_group_interest_personal){
                    removeGroupTag("15");
                    boolean_group_interest_personal = false;
                    group_interest_personal.setTextColor(getResources().getColor(R.color.black));
                    group_interest_personal.setBackground(getResources().getDrawable(R.drawable.button_tag_big));
                    group_interest_personal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_personal, 0, 0, 0);
                } else {
                    boolean_group_interest_personal = true;
                    addGroupTag("15");
                    //setGroupId
                    group_interest_personal.setTextColor(getResources().getColor(R.color.white));
                    group_interest_personal.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_personal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_personal_selected, 0, 0, 0);


                }

                break;
            case R.id.login_group_interest_celebrities:

                if (boolean_group_interest_celebrities){
                    removeGroupTag("2");
                    boolean_group_interest_celebrities = false;
                    group_interest_celebrities.setTextColor(getResources().getColor(R.color.black));
                    group_interest_celebrities.setBackground(getResources().getDrawable(R.drawable.button_tag_big));
                    group_interest_celebrities.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_celebrities, 0, 0, 0);
                } else {
                    boolean_group_interest_celebrities = true;
                    addGroupTag("2");
                    //setGroupId
                    group_interest_celebrities.setTextColor(getResources().getColor(R.color.white));
                    group_interest_celebrities.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_celebrities.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_celebrities_selected, 0, 0, 0);

                }


                break;
            case R.id.login_group_interest_music:

                if (boolean_group_interest_music){
                    removeGroupTag("3");
                    boolean_group_interest_music = false;
                    group_interest_music.setTextColor(getResources().getColor(R.color.black));
                    group_interest_music.setBackground(getResources().getDrawable(R.drawable.button_tag_big));
                    group_interest_music.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_music, 0, 0, 0);
                } else {
                    boolean_group_interest_music = true;
                    addGroupTag("3");
                    //setGroupId
                    group_interest_music.setTextColor(getResources().getColor(R.color.white));
                    group_interest_music.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_music.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_music_selected, 0, 0, 0);

                }

                break;
            case R.id.login_group_interest_technology:

                if (boolean_group_interest_technology){
                    removeGroupTag("4");
                    boolean_group_interest_technology = false;
                    group_interest_technology.setTextColor(getResources().getColor(R.color.black));
                    group_interest_technology.setBackground(getResources().getDrawable(R.drawable.button_tag_big));
                    group_interest_technology.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_technology, 0, 0, 0);
                } else {
                    boolean_group_interest_technology = true;
                    addGroupTag("4");
                    //setGroupId
                    group_interest_technology.setTextColor(getResources().getColor(R.color.white));
                    group_interest_technology.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_technology.setCompoundDrawablePadding(10);
                    group_interest_technology.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_technology_selected, 0, 0, 0);


                }

                break;
            case R.id.login_group_interest_fashion:

                if (boolean_group_interest_fashion){
                    removeGroupTag("5");
                    boolean_group_interest_fashion = false;
                    group_interest_fashion.setTextColor(getResources().getColor(R.color.black));
                    group_interest_fashion.setBackground(getResources().getDrawable(R.drawable.button_tag_big));
                    group_interest_fashion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_fashion, 0, 0, 0);
                } else {
                    boolean_group_interest_fashion = true;
                    addGroupTag("5");
                    //setGroupId
                    group_interest_fashion.setTextColor(getResources().getColor(R.color.white));
                    group_interest_fashion.setCompoundDrawablePadding(10);
                    group_interest_fashion.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_fashion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_fashion_selected, 0, 0, 0);

                }


                break;
            case R.id.login_group_interest_business:

                if (boolean_group_interest_business){
                    removeGroupTag("6");
                    boolean_group_interest_business = false;
                    group_interest_business.setTextColor(getResources().getColor(R.color.black));
                    group_interest_business.setBackground(getResources().getDrawable(R.drawable.button_tag_big));
                    group_interest_business.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_business, 0, 0, 0);
                } else {
                    boolean_group_interest_business = true;
                    addGroupTag("6");
                    //setGroupId
                    group_interest_business.setTextColor(getResources().getColor(R.color.white));
                    group_interest_business.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_business.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_business_selected, 0, 0, 0);

                }


                break;
            case R.id.login_group_interest_school:

                if (boolean_group_interest_school){
                    removeGroupTag("7");
                    boolean_group_interest_school = false;
                    group_interest_school.setTextColor(getResources().getColor(R.color.black));
                    group_interest_school.setBackground(getResources().getDrawable(R.drawable.button_tag_big));
                    group_interest_school.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_school, 0, 0, 0);
                } else {
                    boolean_group_interest_school = true;
                    addGroupTag("7");
                    //setGroupId
                    group_interest_school.setTextColor(getResources().getColor(R.color.white));
                    group_interest_school.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_school.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_school_selected, 0, 0, 0);

                }


                break;
            case R.id.login_group_interest_art:

                if (boolean_group_interest_art){
                    removeGroupTag("8");
                    boolean_group_interest_art = false;
                    group_interest_art.setTextColor(getResources().getColor(R.color.black));
                    group_interest_art.setBackground(getResources().getDrawable(R.drawable.button_tag_big));
                    group_interest_art.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_art, 0, 0, 0);
                } else {
                    boolean_group_interest_art = true;
                    addGroupTag("8");
                    //setGroupId
                    group_interest_art.setTextColor(getResources().getColor(R.color.white));
                    group_interest_art.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_art.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_art_selected, 0, 0, 0);


                }

                break;
            case R.id.login_group_interest_photography:

                if (boolean_group_interest_photography){
                    removeGroupTag("9");
                    boolean_group_interest_photography = false;
                    group_interest_photography.setTextColor(getResources().getColor(R.color.black));
                    group_interest_photography.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_photography.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_photography, 0, 0, 0);
                } else {
                    boolean_group_interest_photography = true;
                    addGroupTag("9");
                    //setGroupId
                    group_interest_photography.setTextColor(getResources().getColor(R.color.white));
                    group_interest_photography.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_photography.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_photography_selected, 0, 0, 0);


                }

                break;
            case R.id.login_group_interest_lgbt:

                if (boolean_group_interest_lgbt){
                    removeGroupTag("10");
                    boolean_group_interest_lgbt = false;
                    group_interest_lgbt.setTextColor(getResources().getColor(R.color.black));
                    group_interest_lgbt.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_lgbt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_lgbt, 0, 0, 0);
                } else {
                    boolean_group_interest_lgbt = true;
                    addGroupTag("10");
                    //setGroupId
                    group_interest_lgbt.setTextColor(getResources().getColor(R.color.white));
                    group_interest_lgbt.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_lgbt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_lgbt_selected, 0, 0, 0);

                }


                break;
            case R.id.login_group_interest_relationship:

                //setGroupId
                if (boolean_group_interest_relationship){
                    removeGroupTag("11");
                    boolean_group_interest_relationship = false;
                    group_interest_relationship.setTextColor(getResources().getColor(R.color.black));
                    group_interest_relationship.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_relationship.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_relationships, 0, 0, 0);
                } else {
                    boolean_group_interest_relationship = true;
                    addGroupTag("11");
                    group_interest_relationship.setTextColor(getResources().getColor(R.color.white));
                    group_interest_relationship.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_relationship.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_relationships_selected, 0, 0, 0);

                }


                break;
            case R.id.login_group_interest_sports:

                if (boolean_group_interest_sports){
                    removeGroupTag("12");
                    boolean_group_interest_sports = false;
                    group_interest_sports.setTextColor(getResources().getColor(R.color.black));
                    group_interest_sports.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_sports.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_sports, 0, 0, 0);

                } else {
                    boolean_group_interest_sports = true;
                    addGroupTag("12");
                    group_interest_sports.setTextColor(getResources().getColor(R.color.white));
                    group_interest_sports.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_sports.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_sports_selected, 0, 0, 0);

                }

                break;
            case R.id.login_group_interest_funny:

                if (boolean_group_interest_funny){
                    removeGroupTag("13");
                    boolean_group_interest_funny = false;
                    group_interest_funny.setTextColor(getResources().getColor(R.color.black));
                    group_interest_funny.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_funny.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_funny, 0, 0, 0);
                } else {
                    boolean_group_interest_funny = true;
                    addGroupTag("13");
                    //setGroupId
                    group_interest_funny.setTextColor(getResources().getColor(R.color.white));
                    group_interest_funny.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_funny.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_funny_selected, 0, 0, 0);

                }


                break;
            case R.id.login_group_interest_confessions:

                if (boolean_group_interest_confessions){
                    removeGroupTag("14");
                    boolean_group_interest_confessions = false;
                    group_interest_confessions.setTextColor(getResources().getColor(R.color.black));
                    group_interest_confessions.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_confessions.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_confessions, 0, 0, 0);
                } else {
                    boolean_group_interest_confessions = true;
                    addGroupTag("14");
                    //setGroupId
                    group_interest_confessions.setTextColor(getResources().getColor(R.color.white));
                    group_interest_confessions.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_confessions.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_confessions_selected, 0, 0, 0);


                }

                break;
            case R.id.login_group_interest_family:

                if (boolean_group_interest_family){
                    removeGroupTag("17");
                    boolean_group_interest_family = false;
                    group_interest_family.setTextColor(getResources().getColor(R.color.black));
                    group_interest_family.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_family.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_family, 0, 0, 0);
                } else {
                    boolean_group_interest_family= true;
                    addGroupTag("17");
                    //setGroupId
                    group_interest_family.setTextColor(getResources().getColor(R.color.white));
                    group_interest_family.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_family.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_family_selected, 0, 0, 0);


                }

                break;
            case R.id.login_group_interest_work:

                if (boolean_group_interest_work){
                    removeGroupTag("18");
                    boolean_group_interest_work = false;
                    group_interest_work.setTextColor(getResources().getColor(R.color.black));
                    group_interest_work.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_work.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_work, 0, 0, 0);

                } else {
                    boolean_group_interest_work = true;
                    addGroupTag("18");
                    //setGroupId
                    group_interest_work.setTextColor(getResources().getColor(R.color.white));
                    group_interest_work.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_work.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_work_selected, 0, 0, 0);


                }

                break;
            case R.id.login_group_interest_faith:

                if (boolean_group_interest_faith){
                    removeGroupTag("19");
                    boolean_group_interest_faith = false;
                    group_interest_faith.setTextColor(getResources().getColor(R.color.black));
                    group_interest_faith.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_faith.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_faith, 0, 0, 0);

                } else {
                    boolean_group_interest_faith = true;
                    addGroupTag("19");
                    //setGroupId
                    group_interest_faith.setTextColor(getResources().getColor(R.color.white));
                    group_interest_faith.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_faith.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_faith_selected, 0, 0, 0);


                }

                break;
            case R.id.login_group_interest_food:

                if (boolean_group_interest_food){
                    removeGroupTag("20");
                    boolean_group_interest_food=  false;
                    group_interest_food.setTextColor(getResources().getColor(R.color.black));
                    group_interest_food.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_food.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_food, 0, 0, 0);
                } else {
                    boolean_group_interest_food = true;
                    addGroupTag("20");
                    //setGroupId
                    group_interest_food.setTextColor(getResources().getColor(R.color.white));
                    group_interest_food.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_food.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_food_selected, 0, 0, 0);


                }

                break;
            case R.id.login_group_interest_entertainment:

                if (boolean_group_interest_entertainment){
                    removeGroupTag("21");
                    boolean_group_interest_entertainment = false;
                    group_interest_entertainment.setTextColor(getResources().getColor(R.color.black));
                    group_interest_entertainment.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_entertainment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_entertainment, 0, 0, 0);
                } else {
                    boolean_group_interest_entertainment = true;
                    addGroupTag("21");
                    //setGroupId
                    group_interest_entertainment.setTextColor(getResources().getColor(R.color.white));
                    group_interest_entertainment.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_entertainment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_entertainment_selected, 0, 0, 0);


                }

                break;
            case R.id.login_group_interest_sex:


                if (boolean_group_interest_sex){
                    removeGroupTag("16");
                    boolean_group_interest_sex= false;
                    group_interest_sex.setTextColor(getResources().getColor(R.color.black));
                    group_interest_sex.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_sex.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_sex, 0, 0, 0);

                } else {
                    boolean_group_interest_sex = true;
                    addGroupTag("16");
                    //setGroupId
                    group_interest_sex.setTextColor(getResources().getColor(R.color.white));
                    group_interest_sex.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_sex.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_sex_selected, 0, 0, 0);

                }

                break;
            case R.id.login_group_interest_science:

                if (boolean_group_interest_science){
                    removeGroupTag("25");
                    boolean_group_interest_science = false;
                    group_interest_science.setTextColor(getResources().getColor(R.color.black));
                    group_interest_science.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_science.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_science, 0, 0, 0);
                } else {
                    boolean_group_interest_women_issues = true;
                    addGroupTag("25");
                    //setGroupId
                    group_interest_science.setTextColor(getResources().getColor(R.color.white));
                    group_interest_science.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_science.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_science_selected, 0, 0, 0);

                }

                break;
            case R.id.login_group_interest_health:

                if (boolean_group_interest_health){
                    removeGroupTag("23");
                    boolean_group_interest_health = false;
                    group_interest_health.setTextColor(getResources().getColor(R.color.black));
                    group_interest_health.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_health.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_health, 0, 0, 0);
                } else {
                    boolean_group_interest_health = true;
                    addGroupTag("23");
                    //setGroupId
                    group_interest_health.setTextColor(getResources().getColor(R.color.white));
                    group_interest_health.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_health.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_health_selected, 0, 0, 0);

                }

                break;
            case R.id.login_group_interest_men_issues:

                if (boolean_group_interest_men_issues){
                    removeGroupTag("24");
                    boolean_group_interest_men_issues = false;
                    group_interest_men_issues.setTextColor(getResources().getColor(R.color.black));
                    group_interest_men_issues.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_men_issues.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_mens_issues, 0, 0, 0);
                } else {
                    boolean_group_interest_men_issues = true;
                    addGroupTag("24");
                    //setGroupId
                    group_interest_men_issues.setTextColor(getResources().getColor(R.color.white));
                    group_interest_men_issues.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_men_issues.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_mens_issues_selected, 0, 0, 0);

                }

                break;
            case R.id.login_group_interest_teen:

                if (boolean_group_interest_teen){
                    removeGroupTag("26");
                    boolean_group_interest_teen = false;
                    group_interest_teen.setTextColor(getResources().getColor(R.color.black));
                    group_interest_teen.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_teen.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_teens, 0, 0, 0);
                } else {
                    boolean_group_interest_teen = true;
                    addGroupTag("26");
                    //setGroupId
                    group_interest_teen.setTextColor(getResources().getColor(R.color.white));
                    group_interest_teen.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_teen.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_teens_selected, 0, 0, 0);

                }

                break;
            case R.id.login_group_interest_women_issues:

                if (boolean_group_interest_women_issues){
                    removeGroupTag("22");
                    boolean_group_interest_women_issues = false;
                    group_interest_women_issues.setTextColor(getResources().getColor(R.color.black));
                    group_interest_women_issues.setBackground(getResources().getDrawable(R.drawable.button_tag));
                    group_interest_women_issues.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_womens_issues, 0, 0, 0);
                } else {
                    boolean_group_interest_women_issues = true;
                    addGroupTag("22");
                    //setGroupId
                    group_interest_women_issues.setTextColor(getResources().getColor(R.color.white));
                    group_interest_women_issues.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_women_issues.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_womens_issues_selected, 0, 0, 0);

                }

                break;




        }
    }

    private void addGroupTag(String selectedGroup){
        joinedGroupsList.add(selectedGroup);
    }

    private void removeGroupTag(String selectedGroup){
        joinedGroupsList.remove(selectedGroup);
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
    public void getAllGroups(List<CategoryJoined> response) {
        setJoinedCategories(response);
        create_new_group.setVisibility(View.GONE);

        //  fillAutoSpacingLayout(response);
    }

    @Override
    public void joinGroups(InsertGroupPOJO response) {
        if (id_user_name != null){
            Toast.makeText(LoginGroupTagActivity.this, "Joined " + response.getCountGroupsJoined() + " categories", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginGroupTagActivity.this, LoginCommunityGroupActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginGroupTagActivity.this, "Joined " + response.getCountGroupsJoined() + " categories", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginGroupTagActivity.this, DiscoverActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        super.onDestroy();
    }

}

/*
private void getAllCategories() throws Exception {

        application.getWebService()
                .getAllCategoryJoined(MySharedPreferences.getUserId(preferences))
                //  .getFacebookContactPosts(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", "true", currentPage)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<CategoryJoined>>() {
                    @Override
                    public void onNext(List<CategoryJoined> response) {

                        setJoinedCategories(response);
                        create_new_group.setVisibility(View.GONE);
                        //  fillAutoSpacingLayout(response);
                    }
                    @Override
                    public void onError(Throwable e){
                        create_new_group.setVisibility(View.GONE);
                        e.printStackTrace();

                    }
                });
    }

    private void joinCategory() throws Exception {
        String listOfCategories = String.valueOf(joinedGroupsList.toString().replace("[", "").replace("]", ""));

        application.getWebService()
                .joinCategory(MySharedPreferences.getUserId(preferences),listOfCategories)
                //  .getFacebookContactPosts(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", "true", currentPage)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<InsertGroupPOJO>() {
                    @Override
                    public void onNext(InsertGroupPOJO response) {

                        if (id_user_name != null){
                            Toast.makeText(LoginGroupTagActivity.this, "Joined " + response.getCountGroupsJoined() + " categories", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginGroupTagActivity.this, LoginCommunityGroupActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginGroupTagActivity.this, "Joined " + response.getCountGroupsJoined() + " categories", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginGroupTagActivity.this, DiscoverActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        create_new_group.setVisibility(View.GONE);
                        //  fillAutoSpacingLayout(response);
                    }
                    @Override
                    public void onError(Throwable e){
                        create_new_group.setVisibility(View.GONE);
                        e.printStackTrace();

                    }
                });
    }

 */
