package in.becandid.app.becandid.ui.userpost;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.dto.ImageUrl;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;

public class ReportAbuseActivity extends BaseActivity implements View.OnClickListener, PostMvpView {
    private EditText report_abuse_reason;
    private Button submitProblem;
    private TextView abuse_previous_status;
    private String id_username;
    private String post_text;
    private String current_problem = null;
    private String id_posts;
    private RadioGroup radio_group_report;
    private RadioButton report_other;

    @Inject
    PostMvpPresenter<PostMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_abuse);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Report Abuse Page");
        toolbar.setNavigationIcon(R.drawable.ic_clear_black_24dp);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ReportAbuseActivity.this);

        Intent intent = getIntent();
        id_username = intent.getStringExtra(Constants.IDUSERNAME);
        id_posts = intent.getStringExtra(Constants.IDPOST);
        post_text = intent.getStringExtra(Constants.STATUS_POST);

        report_abuse_reason = (EditText) findViewById(R.id.report_abuse_reason);
        abuse_previous_status = (TextView) findViewById(R.id.abuse_previous_status);
        submitProblem = (Button) findViewById(R.id.submit_report);
        if (report_abuse_reason.getVisibility() == View.VISIBLE){
            report_abuse_reason.setVisibility(View.GONE);
        }

        radio_group_report = (RadioGroup) findViewById(R.id.radio_group_report);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        report_other = (RadioButton) findViewById(R.id.report_other);
        radio_group_report.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id=group.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton) findViewById(id);

                String radioText=rb.getText().toString();
                current_problem = radioText;
                if (rb == report_other){
                    report_abuse_reason.setVisibility(View.VISIBLE);
                }

            }
        });

        report_abuse_reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                current_problem = report_abuse_reason.getText().toString();
            }
        });
        abuse_previous_status.setText(String.valueOf("**" + " " + post_text + " " + "**"));
        submitProblem.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit_report){

            mPresenter.reportAbuse(id_username, MySharedPreferences.getUserId(preferences), id_posts, MySharedPreferences.getFireBaseToken(preferences), current_problem);


        }
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void getJoinedGroups(List<GroupUser> groupUsers) {

    }

    @Override
    public void postStatus(UserResponse groupUsers) {

    }

    @Override
    public void getImageUrl(ImageUrl image) {

    }

    @Override
    public void updatePost(SuccessResponse successResponse) {

    }

    @Override
    public void reportAbuse(SuccessResponse userResponse) {
        if (userResponse.getSuccess()) {
            Toast.makeText(ReportAbuseActivity.this, "Successfully posted message to our team", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ReportAbuseActivity.this, DiscoverActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        super.onDestroy();
    }
}

/*
try {
                    application.getWebService()
                            .reportAbuse(id_username, MySharedPreferences.getUserId(preferences), id_posts, MySharedPreferences.getUserToken(preferences), current_problem)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .retryWhen(new RetryWithDelay(3,2000))
                            .subscribe(new BaseSubscriber<SuccessResponse>() {
                                @Override
                                public void onNext(SuccessResponse userResponse) {
                                    Timber.e("UserResponse " + userResponse.getSuccess());
                                    if (userResponse.getSuccess()) {
                                        Toast.makeText(ReportAbuseActivity.this, "Successfully posted message to our team", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ReportAbuseActivity.this, DiscoverActivity.class));
                                    }
                                }
                                @Override
                                public void onError(Throwable e) {
                                    try {

                                        Toast.makeText(ReportAbuseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }catch (Exception ex){
                                        ex.printStackTrace();
                                    }
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
 */
