package in.becandid.app.becandid.ui.filter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.onBoarding.WatchVideoAdActivity;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;

public class NewFilterActivity extends BaseActivity implements PremiumSearchResultMvpView {
    private MaterialSpinner spinner_age;
    private MaterialSpinner spinner_gender;
    private Button search_premium_button;
    private Button search_premium_button_user;
    private EditText premium_post_search_value;
    private String age_selected;
    private String gender_selected;

    @Inject
    PremiumSearchResultMvpPresenter<PremiumSearchResultMvpView> mPresenter;


    private static final String[] age_groups = {
            "Select age",
            "13-17 age",
            "18-24 age",
            "25-34 age",
            "35-44 age",
            "45+ age"
    };

    private static final String[] gender_groups = {
            "Select Gender",
            "Male",
            "Female",
            "LGBT"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_filter);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(NewFilterActivity.this);

        toolbar.setNavigationIcon(R.drawable.ic_clear_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewFilterActivity.this, WatchVideoAdActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        spinner_age = (MaterialSpinner) findViewById(R.id.spinner_age);


        spinner_age.setItems(age_groups);
        search_premium_button = (Button) findViewById(R.id.search_premium_button);
        premium_post_search_value = (EditText) findViewById(R.id.premium_post_search_value);
        search_premium_button_user = (Button) findViewById(R.id.search_premium_button_user);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Premium Search");
        toolbar.setNavigationIcon(R.drawable.ic_clear_black_24dp);

      //  spinner_age.setEnabled(false);
        spinner_age.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
               // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                age_selected = String.valueOf(position);
              //  Toast.makeText(NewFilterActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        spinner_age.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
              //  Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                age_selected = null;
            }
        });

        spinner_gender = (MaterialSpinner) findViewById(R.id.spinner_gender);
        spinner_gender.setItems(gender_groups);
        spinner_gender.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                gender_selected = String.valueOf(position);
               // Toast.makeText(NewFilterActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();

            }
        });
        spinner_gender.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
              //  Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                gender_selected = null;
            }
        });

        search_premium_button_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (age_selected == null || gender_selected == null){
                    Toast.makeText(NewFilterActivity.this, "Please select both drop down", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(NewFilterActivity.this, PremiumSearchResultActivity.class);

                    intent.putExtra(Constants.AGE, age_selected);
                    intent.putExtra(Constants.GENDER, gender_selected);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        search_premium_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (premium_post_search_value.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(NewFilterActivity.this, "Please enter text to search", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(NewFilterActivity.this, PremiumSearchResultActivity.class);
                    intent.putExtra(Constants.SEARCH, premium_post_search_value.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(NewFilterActivity.this, WatchVideoAdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        super.onDestroy();
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void getAgeGenderPosts(List<PostsModel> postsModels) {

    }

    @Override
    public void getAgeGenderPosts02(List<PostsModel> postsModels) {

    }

    @Override
    public void getSearchPost(List<PostsModel> postsModels) {

    }

    @Override
    public void getSearchPost02(List<PostsModel> postsModels) {

    }

   /* public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_group_image:
                Intent intent = new Intent(getBaseContext(), SearchGroupPremiumActivity.class);
                startActivityForResult(intent, Constants.GROUP_ID);
                break;
            case R.id.search_group_text:

                Intent intent02 = new Intent(getBaseContext(), SearchGroupPremiumActivity.class);
                startActivityForResult(intent02, Constants.GROUP_ID);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.GROUP_ID){

            search_group_text.setText(data.getStringExtra(Constants.GROUP_ID_NAME));

            group_id = data.getStringExtra(Constants.GROUP_ID_RETURN);

        }
    }
    */



}
