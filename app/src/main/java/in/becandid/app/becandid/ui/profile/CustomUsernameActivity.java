package in.becandid.app.becandid.ui.profile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.discover.DiscoverActivity;

public class CustomUsernameActivity extends BaseActivity implements CustomUsernameMvpView {

    EditText user_custom_username;
    ImageView ivProfileImage;
    Button btNext;
    ProgressBar search_username;

    @Inject
    CustomUsernameMvpPresenter<CustomUsernameMvpView> mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_username);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(CustomUsernameActivity.this);

        search_username = (ProgressBar) findViewById(R.id.search_username);
        search_username.setVisibility(View.GONE);
        user_custom_username = (EditText) findViewById(R.id.user_custom_username);
        user_custom_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    btNext.setBackgroundColor(Color.parseColor("#51c05c"));
                    btNext.setTextColor(Color.parseColor("#ffffff"));
                    btNext.setEnabled(true);

                } else {
                    btNext.setBackgroundColor(Color.parseColor("#33000000"));
                    btNext.setEnabled(false);
                }
            }
        });

        btNext = (Button) findViewById(R.id.username_button);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user_custom_username.getText().toString().equals("")) {
                    try {
                        search_username.setVisibility(View.VISIBLE);
                        mPresenter.getSingleSearchNameOnline(user_custom_username.getText().toString(), MySharedPreferences.getUserId(preferences));
                        //   searchGroupName();
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void getSingleSearchName(SuccessResponse successResponse) {
        if (!successResponse.getSuccess()){
            search_username.setVisibility(View.GONE);

            Intent intent = new Intent(CustomUsernameActivity.this, DiscoverActivity.class);
            intent.putExtra("name", user_custom_username.getText().toString());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(CustomUsernameActivity.this, "Username Already exist", Toast.LENGTH_LONG).show();
            search_username.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        super.onDestroy();
    }
}
