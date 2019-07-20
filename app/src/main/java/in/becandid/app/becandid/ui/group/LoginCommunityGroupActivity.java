package in.becandid.app.becandid.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.dto.CategoryJoined;
import in.becandid.app.becandid.dto.ContactAddResponse;
import in.becandid.app.becandid.dto.LoginResponse;
import in.becandid.app.becandid.dto.MainResponse;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.login.LoginMvpPresenter;
import in.becandid.app.becandid.ui.login.LoginMvpView;
import link.fls.swipestack.SwipeStack;

import static in.becandid.app.becandid.ui.base.Constants.IDUSERNAME;

public class LoginCommunityGroupActivity extends BaseActivity implements SwipeStack.SwipeStackListener, LoginMvpView {
    private Button mButtonLeft, mButtonRight;
    private int currentPage = PAGE_START;
    private static final int PAGE_START = 1;
    private View view;
    private ArrayList<String> groupId;
    private ProgressBar progressBar;
    private List<CommunityGroupPojoNew> response;
    private View progressFrame;
    private ArrayList<CommunityGroupPojoNew> mData;
    private SwipeStack mSwipeStack;
    private SwipeStackAdapterFinal mAdapter;
    private LinearLayout errorLayout;
    private TextView txtError;
    private Button error_btn_retry;
    private Button login_swipe_button_next;
    private List<CommunityGroupPojoNew> dataSet;
    private List<String> joinedGroupsList;
    String id_user_name;

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(LoginCommunityGroupActivity.this);
        setContentView(R.layout.activity_login_community_group);

        mSwipeStack = (SwipeStack) findViewById(R.id.swipeStack_login);
        mButtonLeft = (Button) findViewById(R.id.buttonSwipeLeft_login);
        login_swipe_button_next = (Button) findViewById(R.id.login_swipe_button_next);
        mButtonRight = (Button) findViewById(R.id.buttonSwipeRight_login);

        Intent intent = getIntent();
        id_user_name = intent.getStringExtra(IDUSERNAME); // seconnd person username random

        joinedGroupsList = new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.main_progress_login);
        progressFrame = findViewById(R.id.community_Fragment_frame_login);
        txtError = (TextView) findViewById(R.id.error_txt_cause);
        error_btn_retry = (Button) findViewById(R.id.error_btn_retry);

        errorLayout = (LinearLayout) findViewById(R.id.error_layout);

        mButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeStack.swipeTopViewToLeft();
            }
        });
        mButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeStack.swipeTopViewToRight();
            }
        });


        login_swipe_button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (joinedGroupsList.size() > 3){
                    Intent intent = new Intent(LoginCommunityGroupActivity.this, DiscoverActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginCommunityGroupActivity.this, "Please Join atleast 4 groups", Toast.LENGTH_LONG).show();
                }
            }
        });


        groupId = new ArrayList<>();

        if (MySharedPreferences.getBelow18(preferences) == null){


            try {
                //Below18- 1- true
                //joined - 0 -not joined
                // String user_id, String below18, String joined, int page
                progressFrame.setVisibility(View.VISIBLE);


                mPresenter.getUserJoinedGroupsFirstPage(MySharedPreferences.getUserId(preferences), "0", "0", currentPage);

                //  sendCommunity(MySharedPreferences.getUserId(preferences), currentPage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {


            //     mPresenter.getCommunityGroupOnline(userId, currentPage);
            progressFrame.setVisibility(View.VISIBLE);


            try {
                mPresenter.getUserJoinedGroupsFirstPage(MySharedPreferences.getUserId(preferences), "1", "0", currentPage);

                //  sendCommunity(MySharedPreferences.getUserId(preferences), currentPage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }

    private void showErrorView(Throwable throwable) {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            txtError.setText(fetchErrorMessage(throwable));
        }
    }



    private void arraylistCurrent(List<CommunityGroupPojoNew> response){
        this.response = response;
    }


    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setUp() {

    }


    /*
    private void fillWithTestData() {
        for (int x = 0; x < 5; x++) {
            mData.add(new CommunityGroupPojo("1", "Group Name", "1", "Category name",
                    new CreatedBy("1", "username"), false, "timeCreated", "joinedAt"));
        }
    }
    */


    @Override
    public void onViewSwipedToLeft(int position) {
        //   String swipedElement = mAdapter.getmData().get(position).getGroupId();

    }

    @Override
    public void onViewSwipedToRight(int position) {
        if (mAdapter.getmData().get(position) != null){
            joinedGroupsList.add(mAdapter.getmData().get(position).getGroupId());
        }

    }

    @Override
    protected void onStop() {
        try {

            String groupList = joinedGroupsList.toString().replace("[", "").replace("]", "");
            mPresenter.sendCommunityGroupListOnline(groupList, MySharedPreferences.getUserId(preferences));

            //   sendJoinedGroups();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();
    }



    @Override
    public void onStackEmpty() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            currentPage += 1;
            mSwipeStack.resetStack();
            if (MySharedPreferences.getBelow18(preferences) == null){
                progressBar.setVisibility(View.VISIBLE);


                mPresenter.getUserJoinedGroupsSecondPage(MySharedPreferences.getUserId(preferences), "0", "0", currentPage);


            } else {
                progressBar.setVisibility(View.VISIBLE);

                mPresenter.getUserJoinedGroupsSecondPage(MySharedPreferences.getUserId(preferences), "1", "0", currentPage);

            }

        } catch (Exception e) {
            e.printStackTrace();
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

    }

    @Override
    public void sendGender(SuccessResponse successResponse) {

    }

    @Override
    public void getUserJoinedGroupsFirstPage(List<CommunityGroupPojoNew> response) {


        hideErrorView();
        //  Log.e("RESPONSE:::", "Size===" + response.size());
        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

        //   List<PostsModel> model = fetchResults(response);
        //   showRecycleWithDataFilled(response);
        mAdapter = new SwipeStackAdapterFinal(response, getBaseContext());
        mSwipeStack.setAdapter(mAdapter);
        mSwipeStack.setListener(LoginCommunityGroupActivity.this);

        // mAdapter.addAll(response);
        arraylistCurrent(response);
        progressFrame.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);


    }

    @Override
    public void getUserJoinedGroupsSecondPage(List<CommunityGroupPojoNew> response) {
        progressBar.setVisibility(View.GONE);

        hideErrorView();
        //    Log.e("RESPONSE:::", "Size===" + response.size());
        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

        //   List<PostsModel> model = fetchResults(response);
        //   showRecycleWithDataFilled(response);
        mAdapter = new SwipeStackAdapterFinal(response, getBaseContext());
        mSwipeStack.setAdapter(mAdapter);
        mSwipeStack.setListener(LoginCommunityGroupActivity.this);


    }

    @Override
    public void sendCommunityGroupList(InsertGroupPOJO insertGroupPOJO) {
        // send groups list online

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

Below18- 1- true
joined - 0 -not joined

private void loadFirstPage() throws Exception {
        Log.d(TAG, "loadFirstPage: ");
        hideErrorView();

        if(currentPage > PAGE_START){
            currentPage = PAGE_START;
        }
        application.getWebService()
                .getUserJoinedGroupsFirstPage(MySharedPreferences.getUserId(preferences), currentPage)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<CommunityGroupPojo>>() {
                    @Override
                    public void onNext(List<CommunityGroupPojo> response) {

                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        hideErrorView();
                        Log.e("RESPONSE:::", "Size===" + response.size());
                        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

                        //   List<PostsModel> model = fetchResults(response);
                        //   showRecycleWithDataFilled(response);
                        mAdapter = new SwipeStackAdapter(response);
                        mSwipeStack.setAdapter(mAdapter);
                        mSwipeStack.setListener(LoginCommunityGroupActivity.this);

                        // mAdapter.addAll(response);
                        arraylistCurrent(response);

                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        showErrorView(e);
                    }
                });
    }

    private void loadFirstPageForBelow18() throws Exception {
        Log.d(TAG, "loadFirstPage: ");
        hideErrorView();

        if(currentPage > PAGE_START){
            currentPage = PAGE_START;
        }
        application.getWebService()
                .getUserJoinedGroupsBelow18(MySharedPreferences.getUserId(preferences), "true",currentPage)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<CommunityGroupPojo>>() {
                    @Override
                    public void onNext(List<CommunityGroupPojo> response) {

                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        hideErrorView();
                        Log.e("RESPONSE:::", "Size===" + response.size());
                        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

                        //   List<PostsModel> model = fetchResults(response);
                        //   showRecycleWithDataFilled(response);
                        mAdapter = new SwipeStackAdapter(response);
                        mSwipeStack.setAdapter(mAdapter);
                        mSwipeStack.setListener(LoginCommunityGroupActivity.this);

                        // mAdapter.addAll(response);
                        arraylistCurrent(response);

                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        showErrorView(e);
                    }
                });
    }

    private void loadMorePage() throws Exception {

        application.getWebService()
                .getUserJoinedGroupsFirstPage(MySharedPreferences.getUserId(preferences), currentPage)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<CommunityGroupPojo>>() {
                    @Override
                    public void onNext(List<CommunityGroupPojo> response) {

                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        hideErrorView();
                        Log.e("RESPONSE:::", "Size===" + response.size());
                        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

                        //   List<PostsModel> model = fetchResults(response);
                        //   showRecycleWithDataFilled(response);
                        mAdapter = new SwipeStackAdapter(response);
                        mSwipeStack.setAdapter(mAdapter);
                        mSwipeStack.setListener(LoginCommunityGroupActivity.this);

                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        showErrorView(e);
                    }
                });
    }

    private void loadMorePageBelow18() throws Exception {

        application.getWebService()
                .getUserJoinedGroupsBelow18(MySharedPreferences.getUserId(preferences), "true", currentPage)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<CommunityGroupPojo>>() {
                    @Override
                    public void onNext(List<CommunityGroupPojo> response) {

                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        hideErrorView();
                        Log.e("RESPONSE:::", "Size===" + response.size());
                        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

                        //   List<PostsModel> model = fetchResults(response);
                        //   showRecycleWithDataFilled(response);
                        mAdapter = new SwipeStackAdapter(response);
                        mSwipeStack.setAdapter(mAdapter);
                        mSwipeStack.setListener(LoginCommunityGroupActivity.this);

                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        showErrorView(e);
                    }
                });
    }

private void sendJoinedGroups() throws Exception {
        String groupList = joinedGroupsList.toString().replace("[", "").replace("]", "");

        application.getWebService()
                .joinGroup(groupList, MySharedPreferences.getUserId(preferences))
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<InsertGroupPOJO>() {
                    @Override
                    public void onNext(InsertGroupPOJO response) {

                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();

                    }
                });
    }

 */
