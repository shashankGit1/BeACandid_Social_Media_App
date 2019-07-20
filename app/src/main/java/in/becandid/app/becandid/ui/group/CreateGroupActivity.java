package in.becandid.app.becandid.ui.group;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.ui.userpost.PostActivity;

import static in.becandid.app.becandid.ui.base.Constants.explicitWords;

public class CreateGroupActivity extends BaseActivity implements CreateGroupMvpView {
    EditText etNameOfGroup;
    ImageView ivProfileImage;
    Button btNext;
    ProgressBar search_group_name;
    private String adultWord;



    @Inject
    CreateGroupMvpPresenter<CreateGroupMvpView> mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(CreateGroupActivity.this);
        search_group_name = (ProgressBar) findViewById(R.id.search_group_name);
        search_group_name.setVisibility(View.GONE);
        etNameOfGroup = (EditText) findViewById(R.id.etNameOfGroup);
        etNameOfGroup.addTextChangedListener(new TextWatcher() {
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

        btNext = (Button) findViewById(R.id.group_name_button);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etNameOfGroup.getText().toString().equals("")) {

                    btNext.setVisibility(View.INVISIBLE);
                    search_group_name.setVisibility(View.VISIBLE);

                    try {
                        if (isAdultContent(etNameOfGroup.getText().toString().toLowerCase())) {
                            new AlertDialog.Builder(CreateGroupActivity.this)
                                    .setTitle("The post contains adult content!")
                                    .setMessage("As per Google Guidelines, No adult content can posted publicly. Adult word found in your status : " + adultWord)
                                    .setCancelable(true)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        } else {
                            mPresenter.getSingleSearchNameOnline(etNameOfGroup.getText().toString());

                        }
                        //   postStatus(locationValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    private boolean isAdultContent(String postDesc) {
        for (String str : explicitWords)
            if (postDesc.contentEquals(str)){
                adultWord = str;
                return true;
            }
        return false;
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void getSingleSearchName(SuccessResponse successResponse) {
        if (!successResponse.getSuccess()){
            Intent intent = new Intent(CreateGroupActivity.this, CreateGroupDescActivity.class);
            intent.putExtra("name", etNameOfGroup.getText().toString());
            startActivity(intent);
            finish();
        } else {
            search_group_name.setVisibility(View.INVISIBLE);
            btNext.setVisibility(View.VISIBLE);

            Toast.makeText(CreateGroupActivity.this, "Already exist", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        super.onDestroy();
    }
}

/*
private void searchGroupName() {
        application.getWebService()
                .checkGroup(etNameOfGroup.getText().toString())
                .retryWhen(new RetryWithDelay(3, 2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<SuccessResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onNext(SuccessResponse s) {
                        search_group_name.setVisibility(View.GONE);
                        if (!s.getSuccess()){
                            Intent intent = new Intent(CreateGroupActivity.this, CreateGroupDescActivity.class);
                            intent.putExtra("name", etNameOfGroup.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(CreateGroupActivity.this, "Already exist", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
 */
