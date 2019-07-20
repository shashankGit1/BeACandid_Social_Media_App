package in.becandid.app.becandid.ui.userpost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.dto.ImageUrl;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;

public class EditPost extends BaseActivity implements View.OnClickListener, PostMvpView {
    EditText edit_for_status;
    Button update_status;
    Button delete_status;
    private String postID;
    private String postText;
    private String postOwnerId;
    private String adminId;

    @Inject
    PostMvpPresenter<PostMvpView> mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Edit Post");
        toolbar.setNavigationIcon(R.drawable.ic_clear_black_24dp);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(EditPost.this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        postID = getIntent().getStringExtra(Constants.IDPOST);
        postText = getIntent().getStringExtra(Constants.STATUS_POST);
        postOwnerId = getIntent().getStringExtra(Constants.IDUSERNAME);
        adminId = getIntent().getStringExtra(Constants.ADMIN_ID);

        edit_for_status = (EditText) findViewById(R.id.edit_for_status);
        update_status = (Button) findViewById(R.id.update_status);
        delete_status = (Button) findViewById(R.id.delete_status);


        if (MySharedPreferences.getUserId(preferences).equalsIgnoreCase(adminId)){
            update_status.setVisibility(View.GONE);
        }

        update_status.setOnClickListener(this);
        delete_status.setOnClickListener(this);

        edit_for_status.setText(postText);

    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.update_status){
            mPresenter.updatePost(postID, "text_status", edit_for_status.getText().toString());

            Toast.makeText(this, "Status has been updated successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DiscoverActivity.class));
        } else if (view.getId() == R.id.delete_status){
            mPresenter.updatePost(postID, "delete", edit_for_status.getText().toString());

            Toast.makeText(this, "Status has been deleted successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DiscoverActivity.class));
        }
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
    public void reportAbuse(SuccessResponse successResponse) {

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        super.onDestroy();
    }
}

/*
protected void updateStatus(String postID, String action, String text_status) {
        application.getWebService()
                .updatePost(postID, action, text_status)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<SuccessResponse>() {
                    @Override
                    public void onNext(SuccessResponse response) {
                        Timber.d("Got user details");
                        //     followers.setText(String.valueOf(response.size()));
                        // Toast.makeText(ChangeProfileActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
                        Timber.d("Message from server" + response);
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Toast.makeText(EditPost.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
    }
 */
