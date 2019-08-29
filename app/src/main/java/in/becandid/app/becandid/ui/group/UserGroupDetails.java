package in.becandid.app.becandid.ui.group;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.InfiniteScrollProvider;
import in.becandid.app.becandid.ui.OnLoadMoreListener;
import in.becandid.app.becandid.ui.discover.LatestListAdapter;
import in.becandid.app.becandid.ui.profile.SimpleDividerItemDecoration;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.userpost.PostActivity;
import timber.log.Timber;

public class UserGroupDetails extends BaseActivity implements OnLoadMoreListener, View.OnClickListener, UserGroupMvpView {

    private int mPage;
    private RecyclerView recyclerView;
    private LatestListAdapter activityInteractionAdapter;

    private String groupId;

    private static final int PAGE_START = 1;
    private boolean joinedGroup = false;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 500;
    private int currentPage = PAGE_START;

    ProgressBar progressBar;
    LinearLayout errorLayout;
    TextView txtError;
    List<PostsModel> response;

    Button error_btn_retry;
    private View progressFrame;
    private View groupFrame;

    SwipeRefreshLayout layout;
    TextView user_category_members_counter;
    TextView user_category_edit;
    TextView category_total_post_counter;
    TextView user_category_tag;
    TextView user_category_join;
    SimpleDraweeView category_back_image;
    SimpleDraweeView category_back_image_icon;
    CommunityGroupPojo responseGroup;
    FloatingActionButton create_post_fab;

    private Intent postActivityIntent;

    @Inject
    UserGroupMvpPresenter<UserGroupMvpView> mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(UserGroupDetails.this);

        toolbar.setNavigationIcon(R.drawable.ic_clear_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /* Group Details Views */
        groupFrame = findViewById(R.id.group_detail_progress);
        category_back_image = (SimpleDraweeView)findViewById(R.id.category_back_image);
        category_back_image_icon = (SimpleDraweeView)findViewById(R.id.category_back_image_icon);
        user_category_members_counter = (TextView)findViewById(R.id.user_category_members_counter);
        user_category_edit = (TextView)findViewById(R.id.user_category_edit);
        category_total_post_counter = (TextView)findViewById(R.id.category_total_post_counter);
        user_category_tag = (TextView)findViewById(R.id.user_category_tag);
        user_category_join = (TextView)findViewById(R.id.user_category_join);
        create_post_fab = findViewById(R.id.fab_add_post_to_group);
        category_back_image_icon.setVisibility(View.GONE);
        user_category_edit.setVisibility(View.GONE);

        /* Group Details Views */

        progressFrame = findViewById(R.id.activity_user_category_progress);
        recyclerView = (RecyclerView) findViewById(R.id.user_category_recyclerview_other);
        postActivityIntent = new Intent(this, PostActivity.class);

        Intent intent = getIntent();
        groupId = intent.getStringExtra(Constants.GROUPID);
        error_btn_retry = (Button) findViewById(R.id.error_btn_retry);

        activityInteractionAdapter = new LatestListAdapter(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(activityInteractionAdapter);

        progressBar = (ProgressBar) findViewById(R.id.main_progress_group_details);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        txtError = (TextView) findViewById(R.id.error_txt_cause);

        layout = (SwipeRefreshLayout) findViewById(R.id.category_swipeRefreshLayout);
        layout.setColorSchemeResources(android.R.color.holo_orange_light,
                android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //    progressFrame.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        currentPage = PAGE_START;
                        try {
                            mPresenter.getGroupPosts(groupId,  MySharedPreferences.getUserId(preferences), String.valueOf(currentPage));
                            //   getLatestPosts();
                            // loadFirstPage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //after shuffle id done then swife refresh is off
                        layout.setRefreshing(false);
                    }
                },5000);


            }
        });


        try {
            initUiView();
           // loadFirstPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        user_category_join.setOnClickListener(this);
        create_post_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(postActivityIntent);
            }
        });

        error_btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mPresenter.getGroupPosts(groupId,  MySharedPreferences.getUserId(preferences), String.valueOf(currentPage));
                    mPresenter.getGroupSpecific(groupId, MySharedPreferences.getUserId(preferences), String.valueOf(currentPage));

                    //  loadFirstPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initUiView() {
        recyclerView = (RecyclerView) findViewById(R.id.user_category_recyclerview_other);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setHasFixedSize(true);

        InfiniteScrollProvider infiniteScrollProvider=new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView,this);


        try {
            currentPage = PAGE_START;
            mPresenter.getGroupPosts(groupId,  MySharedPreferences.getUserId(preferences), String.valueOf(currentPage));
            mPresenter.getGroupSpecific(groupId, MySharedPreferences.getUserId(preferences), String.valueOf(currentPage));
            //    loadFirstPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

      //  getMenuInflater().inflate(R.menu.menu_group, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_post_status:
                // do more stuff

                return true;
        }

        return false;
    }

    /*


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (MySharedPreferences.getUserId(preferences).equals(responseGroup.getCreated_by_id_user_name())){
            getMenuInflater().inflate(R.menu.edit_group, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_group) {
            // launch settings activity
            startActivity(new Intent(UserGroupDetails.this, EditGroupActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

      */

    private void showErrorView(Throwable throwable) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            txtError.setText(fetchErrorMessage(throwable));
        }
    }

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }


    /**
     * @param throwable to identify the type of error
     * @return appropriate error message
     */
    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
            //      startActivity(new Intent(this, OfflineActivity.class));
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }





    private void setTextViewParameters(CommunityGroupPojo response){
        responseGroup = response;

        category_back_image.setImageURI(response.getGroupImageUrl());
        user_category_members_counter.setText(response.getUsersInGroup());
        category_total_post_counter.setText(response.getPostsInsideGroups());
        user_category_tag.setText(response.getCategoryName());
        if (response.getJoined()){
            user_category_join.setText("UnJoin");
            joinedGroup = true;
        } else {
            user_category_join.setText("Join");
            joinedGroup = false;
        }

        // static class to get user ID

        if (MySharedPreferences.getUserId(preferences).equalsIgnoreCase(response.getCreatedBy().getIdUserName())){
            category_back_image_icon.setVisibility(View.VISIBLE);
            user_category_edit.setVisibility(View.VISIBLE);
            user_category_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserGroupDetails.this, EditGroupActivity.class);
                intent.putExtra(Constants.GROUP_ID_NEW, response.getGroupId());
                startActivity(intent);
            }
        });

        } else {
           category_back_image_icon.setVisibility(View.GONE);
           user_category_edit.setVisibility(View.GONE);

        }
    }


    @Override
    public String toString() {
        return "documentary";
    }

    @Override
    protected void setUp() {

    }

    private void arraylistCurrent(List<PostsModel> response){
        this.response = response;
    }


    @Override
    public void onLoadMore() {
        if (response.size() == 25){
            try {
                progressBar.setVisibility(View.VISIBLE);

                currentPage += 1;
                mPresenter.getGroupPosts02(groupId,  MySharedPreferences.getUserId(preferences), String.valueOf(currentPage));

              //  loadNextPage();
                isLoading = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Timber.d("nothing");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_category_join:
                if (joinedGroup){
                    try {
                        mPresenter.sendUnJoinGroup(groupId,  MySharedPreferences.getUserId(preferences));
                    //    unjoingroup();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    user_category_join.setText("Join");
                    joinedGroup = false;
                } else {
                    user_category_join.setText("UnJoin");
                    try {
                        mPresenter.sendJoinGroup(groupId,  MySharedPreferences.getUserId(preferences));
                      //  joingroup();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    joinedGroup = true;
                }
        }
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void getGroupPosts(List<PostsModel> response) {
        progressFrame.setVisibility(View.GONE);
        create_post_fab.setVisibility(View.VISIBLE);
     //   user_category_tag.setText(response.get(0).getCategory());

//        toolbar.setTitle(response.get(0).getName());

        Log.e("RESPONSE:::", "Size===" + response.size());

        activityInteractionAdapter.addAll(response);
        arraylistCurrent(response);
    }

    @Override
    public void getGroupPosts02(List<PostsModel> response) {
        progressBar.setVisibility(View.GONE);
        hideErrorView();

        activityInteractionAdapter.addAll(response);
        arraylistCurrent(response);
    }

    @Override
    public void sendJoinGroup(InsertGroupPOJO insertGroupPOJO) {

    }

    @Override
    public void sendUnJoinGroup(InsertGroupPOJO insertGroupPOJO) {

    }

    @Override
    public void getGroupSpecific(List<CommunityGroupPojo> response) {
        try {
            getSupportActionBar().setTitle(response.get(0).getGroupName());
            postActivityIntent.putExtra("POST_GROUP_NAME", response.get(0).getGroupName());
        } catch (Exception ex){
            ex.printStackTrace();
        }

        try {
            setTextViewParameters(response.get(0));
        } catch (Exception ex){

        }
        groupFrame.setVisibility(View.GONE); // group specific loading
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        super.onDestroy();
    }
}

/*
private void loadFirstPage() throws Exception {
        Log.d(TAG, "loadFirstPage: ");
        hideErrorView();

        if(currentPage > PAGE_START){
            currentPage = PAGE_START;
        }

        application.getWebService()
                .getGroupPosts(groupId,  MySharedPreferences.getUserId(preferences), currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<List<PostsModel>>() {
                    @Override
                    public void onNext(List<PostsModel> response) {
                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        hideErrorView();

                        user_category_tag.setText(response.get(0).getCategory());
                        toolbar.setTitle(response.get(0).getName());

                        Log.e("RESPONSE:::", "Size===" + response.size());

                            activityInteractionAdapter.addAll(response);
                            arraylistCurrent(response);

                        try {
                            loadGroupDetails(response.get(0).getGroup_id());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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

    private void unjoingroup() throws Exception {

        application.getWebService()
                .unJoinGroup(groupId,  MySharedPreferences.getUserId(preferences))
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<InsertGroupPOJO>() {
                    @Override
                    public void onNext(InsertGroupPOJO response) {


                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        showErrorView(e);
                    }
                });
    }

    private void joingroup() throws Exception {

        application.getWebService()
                .joinGroup(groupId,  MySharedPreferences.getUserId(preferences))
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<InsertGroupPOJO>() {
                    @Override
                    public void onNext(InsertGroupPOJO response) {


                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        showErrorView(e);
                    }
                });
    }

    private void loadNextPage() {
        hideErrorView();

        application.getWebService()
                .getGroupPosts(groupId, MySharedPreferences.getUserId(preferences), currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<List<PostsModel>>() {
                    @Override
                    public void onNext(List<PostsModel> response) {
                        hideErrorView();

                        activityInteractionAdapter.addAll(response);
                        arraylistCurrent(response);
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        activityInteractionAdapter.showRetry(true, fetchErrorMessage(e));
                    }
                });

    }

    private void loadGroupDetails(String categoryJoin) throws Exception {

        application.getWebService()
                .getGroupSpecific(categoryJoin, MySharedPreferences.getUserId(preferences), currentPage)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<CommunityGroupPojo>>() {
                    @Override
                    public void onNext(List<CommunityGroupPojo> response) {
                        try {
                            getSupportActionBar().setTitle(response.get(0).getGroupName());
                        } catch (Exception ex){
                            ex.printStackTrace();
                        }

                        setTextViewParameters(response.get(0));
                        groupFrame.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        groupFrame.setVisibility(View.GONE);
                    }
                });
    }
 */
