package in.becandid.app.becandid.ui.group;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;

public class CreateGroupTagActivity extends BaseActivity implements View.OnClickListener, CreateGroupTagMvpView {
    Button btnSubmit;
    String group_name;
    String group_description;
    String image_location;
    String image_url;
    boolean select_category = false;
    ProgressBar create_new_group;

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
    TextView group_interest_science;
    TextView group_interest_health;
    TextView group_interest_men_issues;
    TextView group_interest_teen;

    boolean boolean_group_interest_science;
    boolean boolean_group_interest_health;
    boolean boolean_group_interest_men_issues;
    boolean boolean_group_interest_teen;
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

    String selectedGroup = null;

    @Inject
    CreateGroupTagMvpPresenter<CreateGroupTagMvpView> mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_tag);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(CreateGroupTagActivity.this);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        Intent intent = getIntent();
        group_name = intent.getStringExtra("name");
        group_description = intent.getStringExtra("desc");
        image_location = intent.getStringExtra("locationPOJO");
        create_new_group = (ProgressBar) findViewById(R.id.create_new_group);

        group_interest_science = (TextView) findViewById(R.id.group_interest_science);
        group_interest_health = (TextView) findViewById(R.id.group_interest_health);
        group_interest_men_issues = (TextView) findViewById(R.id.group_interest_men_issues);
        group_interest_teen = (TextView) findViewById(R.id.group_interest_teen);

        group_interest_politics = (TextView) findViewById(R.id.group_interest_politics);
        group_interest_personal = (TextView) findViewById(R.id.group_interest_personal);
        group_interest_celebrities = (TextView) findViewById(R.id.group_interest_celebrities);
        group_interest_music = (TextView) findViewById(R.id.group_interest_music);
        group_interest_technology = (TextView) findViewById(R.id.group_interest_technology);
        group_interest_fashion = (TextView) findViewById(R.id.group_interest_fashion);
        group_interest_business = (TextView) findViewById(R.id.group_interest_business);
        group_interest_school = (TextView) findViewById(R.id.group_interest_school);
        group_interest_art = (TextView) findViewById(R.id.group_interest_art);
        group_interest_photography = (TextView) findViewById(R.id.group_interest_photography);
        group_interest_lgbt = (TextView) findViewById(R.id.group_interest_lgbt);
        group_interest_relationship = (TextView) findViewById(R.id.group_interest_relationship);
        group_interest_sports = (TextView) findViewById(R.id.group_interest_sports);
        group_interest_funny = (TextView) findViewById(R.id.group_interest_funny);
        group_interest_confessions = (TextView) findViewById(R.id.group_interest_confessions);
        group_interest_family = (TextView) findViewById(R.id.group_interest_family);
        group_interest_work = (TextView) findViewById(R.id.group_interest_work);
        group_interest_faith = (TextView) findViewById(R.id.group_interest_faith);
        group_interest_food = (TextView) findViewById(R.id.group_interest_food);
        group_interest_women_issues = (TextView) findViewById(R.id.group_interest_women_issues);
        group_interest_entertainment = (TextView) findViewById(R.id.group_interest_entertainment);
        group_interest_sex = (TextView) findViewById(R.id.group_interest_sex);

        create_new_group.setVisibility(View.INVISIBLE);


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

                try {
                    create_new_group.setVisibility(View.VISIBLE);
                    if (!image_location.equalsIgnoreCase("null")){
                        uploadFile(Uri.parse(image_location));
                    } else {
                        mPresenter.postNewCategoryOnline(selectedGroup, MySharedPreferences.getUserId(preferences), group_name,"","");
                      //  CreatedNewCategory();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        //progressFrame.setVisibility(View.GONE);
    }

    @Override
    protected void setUp() {

    }



    private void setImageFileUrl(String imageUrl) {
        this.image_url = imageUrl;
    }

    private void uploadFile(Uri fileUri) {
        File file = new File(String.valueOf(fileUri));


        mPresenter.postImageUpload(file);
        // create RequestBody instance from file
  /*      RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        // finally, execute the request
        try {
            application.getWebService()
                    .uploadFile(body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<String>() {
                        @Override
                        public void onNext(String response) {
                            Timber.d("file url " + response);
                           // Toast.makeText(CreateGroupTagActivity.this, response, Toast.LENGTH_SHORT).show();
                            setImageFileUrl(response);
                            try{

                                mPresenter.postNewCategoryOnline(selectedGroup, MySharedPreferences.getUserId(preferences), group_name,"","");

                                CreatedNewCategory(response);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.group_interest_politics:

                getSelectedButton();
                setGroup("1");
                boolean_group_interest_politics = true;
                //setGroupId
                    group_interest_politics.setTextColor(getResources().getColor(R.color.white));
                    group_interest_politics.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                    group_interest_politics.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_politics_selected, 0, 0, 0);
                    break;

            case R.id.group_interest_personal:

                getSelectedButton();
                setGroup("15");
                boolean_group_interest_personal = true;
                //setGroupId
                group_interest_personal.setTextColor(getResources().getColor(R.color.white));
                group_interest_personal.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_personal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_personal_selected, 0, 0, 0);


                break;
            case R.id.group_interest_celebrities:

                getSelectedButton();
                setGroup("2");
                boolean_group_interest_celebrities = true;
                //setGroupId
                group_interest_celebrities.setTextColor(getResources().getColor(R.color.white));
                group_interest_celebrities.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_celebrities.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_celebrities_selected, 0, 0, 0);


                break;
            case R.id.group_interest_music:

                getSelectedButton();
                setGroup("3");
                boolean_group_interest_music = true;
                //setGroupId
                group_interest_music.setTextColor(getResources().getColor(R.color.white));
                group_interest_music.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_music.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_music_selected, 0, 0, 0);


                break;
            case R.id.group_interest_technology:

                getSelectedButton();
                boolean_group_interest_technology = true;
                setGroup("4");
                //setGroupId
                group_interest_technology.setTextColor(getResources().getColor(R.color.white));
                group_interest_technology.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_technology.setCompoundDrawablePadding(10);
                group_interest_technology.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_technology_selected, 0, 0, 0);


                break;
            case R.id.group_interest_fashion:

                getSelectedButton();
                boolean_group_interest_fashion = true;
                setGroup("5");
                //setGroupId
                group_interest_fashion.setTextColor(getResources().getColor(R.color.white));
                group_interest_fashion.setCompoundDrawablePadding(10);
                group_interest_fashion.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_fashion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_fashion_selected, 0, 0, 0);


                break;
            case R.id.group_interest_business:

                getSelectedButton();
                setGroup("6");
                boolean_group_interest_business = true;
                //setGroupId
                group_interest_business.setTextColor(getResources().getColor(R.color.white));
                group_interest_business.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_business.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_business_selected, 0, 0, 0);


                break;
            case R.id.group_interest_school:

                getSelectedButton();
                boolean_group_interest_school = true;
                setGroup("7");
                //setGroupId
                group_interest_school.setTextColor(getResources().getColor(R.color.white));
                group_interest_school.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_school.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_school_selected, 0, 0, 0);


                break;
            case R.id.group_interest_art:

                setGroup("8");

                getSelectedButton();
                boolean_group_interest_art = true;
                //setGroupId
                group_interest_art.setTextColor(getResources().getColor(R.color.white));
                group_interest_art.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_art.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_art_selected, 0, 0, 0);


                break;
            case R.id.group_interest_photography:
                getSelectedButton();
                boolean_group_interest_photography = true;
                setGroup("9");
                //setGroupId
                group_interest_photography.setTextColor(getResources().getColor(R.color.white));
                group_interest_photography.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_photography.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_photography_selected, 0, 0, 0);


                break;
            case R.id.group_interest_lgbt:
                getSelectedButton();
                setGroup("10");
                boolean_group_interest_lgbt = true;
                //setGroupId
                group_interest_lgbt.setTextColor(getResources().getColor(R.color.white));
                group_interest_lgbt.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_lgbt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_lgbt_selected, 0, 0, 0);


                break;
            case R.id.group_interest_relationship:
                getSelectedButton();
                setGroup("11");
                //setGroupId
                boolean_group_interest_relationship = true;
                group_interest_relationship.setTextColor(getResources().getColor(R.color.white));
                group_interest_relationship.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_relationship.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_relationships_selected, 0, 0, 0);


                break;
            case R.id.group_interest_sports:
                getSelectedButton();
                //setGroupId
                setGroup("12");
                boolean_group_interest_sports = true;
                group_interest_sports.setTextColor(getResources().getColor(R.color.white));
                group_interest_sports.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_sports.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_sports_selected, 0, 0, 0);

                break;
            case R.id.group_interest_funny:

                getSelectedButton();
                boolean_group_interest_funny = true;
                setGroup("13");
                //setGroupId
                group_interest_funny.setTextColor(getResources().getColor(R.color.white));
                group_interest_funny.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_funny.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_funny_selected, 0, 0, 0);


                break;
            case R.id.group_interest_confessions:

                getSelectedButton();
                boolean_group_interest_confessions = true;
                setGroup("14");
                //setGroupId
                group_interest_confessions.setTextColor(getResources().getColor(R.color.white));
                group_interest_confessions.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_confessions.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_confessions_selected, 0, 0, 0);


                break;
            case R.id.group_interest_family:

                getSelectedButton();
                setGroup("17");
                boolean_group_interest_family= true;
                //setGroupId
                group_interest_family.setTextColor(getResources().getColor(R.color.white));
                group_interest_family.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_family.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_family_selected, 0, 0, 0);


                break;
            case R.id.group_interest_work:

                getSelectedButton();
                boolean_group_interest_work = true;
                setGroup("18");
                //setGroupId
                group_interest_work.setTextColor(getResources().getColor(R.color.white));
                group_interest_work.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_work.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_work_selected, 0, 0, 0);


                break;
            case R.id.group_interest_faith:

                getSelectedButton();
                boolean_group_interest_faith = true;
                setGroup("19");
                //setGroupId
                group_interest_faith.setTextColor(getResources().getColor(R.color.white));
                group_interest_faith.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_faith.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_faith_selected, 0, 0, 0);


                break;
            case R.id.group_interest_food:

                getSelectedButton();
                setGroup("20");
                boolean_group_interest_food = true;
                //setGroupId
                group_interest_food.setTextColor(getResources().getColor(R.color.white));
                group_interest_food.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_food.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_food_selected, 0, 0, 0);


                break;
            case R.id.group_interest_entertainment:

                getSelectedButton();
                boolean_group_interest_entertainment = true;
                setGroup("21");
                //setGroupId
                group_interest_entertainment.setTextColor(getResources().getColor(R.color.white));
                group_interest_entertainment.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_entertainment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_entertainment_selected, 0, 0, 0);


                break;
            case R.id.group_interest_sex:

                getSelectedButton();
                boolean_group_interest_sex = true;
                setGroup("16");
                //setGroupId
                group_interest_sex.setTextColor(getResources().getColor(R.color.white));
                group_interest_sex.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_sex.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_sex_selected, 0, 0, 0);


                break;
            case R.id.group_interest_women_issues:

                getSelectedButton();
                setGroup("22");
                boolean_group_interest_women_issues = true;
                //setGroupId
                group_interest_women_issues.setTextColor(getResources().getColor(R.color.white));
                group_interest_women_issues.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_women_issues.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_womens_issues_selected, 0, 0, 0);

                break;

            case R.id.group_interest_science:

                getSelectedButton();
                setGroup("25");
                boolean_group_interest_science = true;
                //setGroupId
                group_interest_science.setTextColor(getResources().getColor(R.color.white));
                group_interest_science.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_science.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_science_selected, 0, 0, 0);

                break;
            case R.id.group_interest_health:

                getSelectedButton();
                setGroup("23");
                boolean_group_interest_health = true;
                //setGroupId
                group_interest_health.setTextColor(getResources().getColor(R.color.white));
                group_interest_health.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_health.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_health_selected, 0, 0, 0);

                break;
            case R.id.group_interest_men_issues:

                getSelectedButton();
                setGroup("24");
                boolean_group_interest_men_issues = true;
                //setGroupId
                group_interest_men_issues.setTextColor(getResources().getColor(R.color.white));
                group_interest_men_issues.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_men_issues.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_mens_issues_selected, 0, 0, 0);

                break;
            case R.id.group_interest_teen:

                getSelectedButton();
                setGroup("26");
                boolean_group_interest_teen = true;
                //setGroupId
                group_interest_teen.setTextColor(getResources().getColor(R.color.white));
                group_interest_teen.setBackground(getResources().getDrawable(R.drawable.button_tag_selected));
                group_interest_teen.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_teens_selected, 0, 0, 0);

                break;


                }
    }

    private void setGroup(String selectedGroup){
        this.selectedGroup = selectedGroup;
    }



    private void getSelectedButton(){
        if (boolean_group_interest_politics){
        	boolean_group_interest_politics = false;
            group_interest_politics.setTextColor(getResources().getColor(R.color.black));
            group_interest_politics.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_politics.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_politics, 0, 0, 0);
        } else if (boolean_group_interest_celebrities){
        	boolean_group_interest_celebrities = false;
            group_interest_celebrities.setTextColor(getResources().getColor(R.color.black));
            group_interest_celebrities.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_celebrities.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_celebrities, 0, 0, 0);
        } else if (boolean_group_interest_music){
        	boolean_group_interest_music = false;
            group_interest_music.setTextColor(getResources().getColor(R.color.black));
            group_interest_music.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_music.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_music, 0, 0, 0);
        } else if (boolean_group_interest_technology){
        	boolean_group_interest_technology = false;
            group_interest_technology.setTextColor(getResources().getColor(R.color.black));
            group_interest_technology.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_technology.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_technology, 0, 0, 0);
        } else if (boolean_group_interest_fashion){
        	boolean_group_interest_fashion = false;
            group_interest_fashion.setTextColor(getResources().getColor(R.color.black));
            group_interest_fashion.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_fashion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_fashion, 0, 0, 0);
        } else if (boolean_group_interest_business){

        	boolean_group_interest_business = false;
            group_interest_business.setTextColor(getResources().getColor(R.color.black));
            group_interest_business.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_business.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_business, 0, 0, 0);
        } else if (boolean_group_interest_school){
        	boolean_group_interest_school = false;
            group_interest_school.setTextColor(getResources().getColor(R.color.black));
            group_interest_school.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_school.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_school, 0, 0, 0);
        } else if (boolean_group_interest_art){
        	boolean_group_interest_art = false;
            group_interest_art.setTextColor(getResources().getColor(R.color.black));
            group_interest_art.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_art.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_art, 0, 0, 0);
        } else if (boolean_group_interest_photography){
        	boolean_group_interest_photography = false;
            group_interest_photography.setTextColor(getResources().getColor(R.color.black));
            group_interest_photography.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_photography.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_photography, 0, 0, 0);
        } else if (boolean_group_interest_lgbt){
        	boolean_group_interest_lgbt = false;
            group_interest_lgbt.setTextColor(getResources().getColor(R.color.black));
            group_interest_lgbt.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_lgbt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_lgbt, 0, 0, 0);
        } else if (boolean_group_interest_relationship){
        	boolean_group_interest_relationship = false;
            group_interest_relationship.setTextColor(getResources().getColor(R.color.black));
            group_interest_relationship.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_relationship.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_relationships, 0, 0, 0);
        } else if (boolean_group_interest_sports){
        	boolean_group_interest_sports = false;
            group_interest_sports.setTextColor(getResources().getColor(R.color.black));
            group_interest_sports.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_sports.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_sports, 0, 0, 0);
        } else if (boolean_group_interest_funny){
        	boolean_group_interest_funny = false;
            group_interest_funny.setTextColor(getResources().getColor(R.color.black));
            group_interest_funny.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_funny.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_funny, 0, 0, 0);
        } else if (boolean_group_interest_confessions){
        	boolean_group_interest_confessions = false;
            group_interest_confessions.setTextColor(getResources().getColor(R.color.black));
            group_interest_confessions.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_confessions.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_confessions, 0, 0, 0);
        } else if (boolean_group_interest_family){
        	boolean_group_interest_family = false;
            group_interest_family.setTextColor(getResources().getColor(R.color.black));
            group_interest_family.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_family.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_family, 0, 0, 0);
        } else if (boolean_group_interest_work){
        	boolean_group_interest_work = false;
            group_interest_work.setTextColor(getResources().getColor(R.color.black));
            group_interest_work.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_work.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_work, 0, 0, 0);
        } else if (boolean_group_interest_faith){
        	boolean_group_interest_faith = false;
            group_interest_faith.setTextColor(getResources().getColor(R.color.black));
            group_interest_faith.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_faith.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_faith, 0, 0, 0);
        } else if (boolean_group_interest_food){
        	boolean_group_interest_food=  false;
            group_interest_food.setTextColor(getResources().getColor(R.color.black));
            group_interest_food.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_food.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_food, 0, 0, 0);
        } else if (boolean_group_interest_entertainment){
        	boolean_group_interest_entertainment = false;
            group_interest_entertainment.setTextColor(getResources().getColor(R.color.black));
            group_interest_entertainment.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_entertainment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_entertainment, 0, 0, 0);
        } else if (boolean_group_interest_personal){
        	boolean_group_interest_personal = false;
            group_interest_personal.setTextColor(getResources().getColor(R.color.black));
            group_interest_personal.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_personal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_personal, 0, 0, 0);
        } else if (boolean_group_interest_women_issues){
        	boolean_group_interest_women_issues = false;
            group_interest_women_issues.setTextColor(getResources().getColor(R.color.black));
            group_interest_women_issues.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_women_issues.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_womens_issues, 0, 0, 0);
        } else if (boolean_group_interest_sex){
        	boolean_group_interest_sex= false;
            group_interest_sex.setTextColor(getResources().getColor(R.color.black));
            group_interest_sex.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_sex.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_sex, 0, 0, 0);

        }else if (boolean_group_interest_science){
        	boolean_group_interest_science= false;
            group_interest_science.setTextColor(getResources().getColor(R.color.black));
            group_interest_science.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_science.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_science, 0, 0, 0);
        }else if (boolean_group_interest_health){
        	boolean_group_interest_health= false;
            group_interest_health.setTextColor(getResources().getColor(R.color.black));
            group_interest_health.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_health.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_health, 0, 0, 0);
        }else if (boolean_group_interest_men_issues){
        	boolean_group_interest_men_issues= false;
            group_interest_men_issues.setTextColor(getResources().getColor(R.color.black));
            group_interest_men_issues.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_men_issues.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_mens_issues, 0, 0, 0);
        }else if (boolean_group_interest_teen){
        	boolean_group_interest_teen= false;
            group_interest_teen.setTextColor(getResources().getColor(R.color.black));
            group_interest_teen.setBackground(getResources().getDrawable(R.drawable.button_tag));
            group_interest_teen.setCompoundDrawablesWithIntrinsicBounds(R.drawable.onboarding_tags_teens, 0, 0, 0);
        }
    }

    @Override
    public void postNewCategory(GroupsCreatePOJO response) {
        //  Toast.makeText(CreateGroupTagActivity.this, response.getName(), Toast.LENGTH_SHORT).show();
        try{
                     Log.i("postnew", response.getName()+ "  success:"+response.getSuccess());
        }
        catch (Exception e){
            Log.e("postnew", e.getMessage().toString());
        }
        Toast.makeText(CreateGroupTagActivity.this, String.valueOf(response.getName() + " group created successfully"), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CreateGroupTagActivity.this, DiscoverActivity.class);
        intent.putExtra(Constants.CATEGORY, response.getGroupId());
        startActivity(intent);
        create_new_group.setVisibility(View.GONE);
        finish();
    }

    @Override
    public void getImageUrl(String url) {
        // this will send create new category which will be received in above method ad then go to discover activity.
        mPresenter.postNewCategoryOnline(selectedGroup, MySharedPreferences.getUserId(preferences), group_name,url,group_description);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        super.onDestroy();
    }
}

/*
private void CreatedNewCategory(String image_url) throws Exception {

        application.getWebService()
                .addGroup(selectedGroup, MySharedPreferences.getUserId(preferences), group_name, image_url, group_description)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<GroupsCreatePOJO>() {
                    @Override
                    public void onNext(GroupsCreatePOJO response) {
                        Toast.makeText(CreateGroupTagActivity.this, String.valueOf(response.getName() + " group created successfully"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateGroupTagActivity.this, DiscoverActivity.class);
                        intent.putExtra(Constants.CATEGORY, response.getGroupId());
                        startActivity(intent);
                        create_new_group.setVisibility(View.GONE);
                        finish();
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        create_new_group.setVisibility(View.GONE);
                    }
                });
    }

    private void CreatedNewCategory() throws Exception {

        application.getWebService()
                .addGroup(selectedGroup, MySharedPreferences.getUserId(preferences), group_name)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<GroupsCreatePOJO>() {
                    @Override
                    public void onNext(GroupsCreatePOJO response) {
                      //  Toast.makeText(CreateGroupTagActivity.this, response.getName(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(CreateGroupTagActivity.this, String.valueOf(response.getName() + " group created successfully"), Toast.LENGTH_SHORT).show();
                        create_new_group.setVisibility(View.GONE);
                        Intent intent = new Intent(CreateGroupTagActivity.this, DiscoverActivity.class);
                        intent.putExtra(Constants.CATEGORY, response.getGroupId());
                        startActivity(intent);
                        create_new_group.setVisibility(View.GONE);
                        finish();
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        create_new_group.setVisibility(View.GONE);

                    }
                });
    }
 */
