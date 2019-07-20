package in.becandid.app.becandid.ui.filter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.onBoarding.WatchVideoAdActivity;
import in.becandid.app.becandid.ui.InfiniteScrollProvider;
import in.becandid.app.becandid.ui.OnLoadMoreListener;
import in.becandid.app.becandid.ui.discover.LatestListAdapter;
import in.becandid.app.becandid.ui.profile.SimpleDividerItemDecoration;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import timber.log.Timber;

public class PremiumSearchResultActivity extends BaseActivity implements OnLoadMoreListener, View.OnClickListener, PremiumSearchResultMvpView {

    private int mPage;
    private RecyclerView recyclerView;
    private LatestListAdapter latestListAdapter;

    @BindView(R.id.no_post_layout) LinearLayout no_post_layout;
    @BindView(R.id.no_post_textview) TextView no_post_textview;

    private static final int PAGE_START = 1;
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

    SwipeRefreshLayout layout;
    String age;
    String gender;
    String textSearch;

    boolean isAgeGender = false;
    boolean isSearchPage = false;

    @Inject
    PremiumSearchResultMvpPresenter<PremiumSearchResultMvpView> mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_search_result);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(PremiumSearchResultActivity.this);

        toolbar.setNavigationIcon(R.drawable.ic_clear_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PremiumSearchResultActivity.this, WatchVideoAdActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        no_post_layout.setVisibility(View.GONE);

        /* Group Details Views */

        /* Group Details Views */

        Intent intent = getIntent();
        age = intent.getStringExtra(Constants.AGE); // seconnd person username random
        gender = intent.getStringExtra(Constants.GENDER); // seconnd person username random
        textSearch = intent.getStringExtra(Constants.SEARCH); // seconnd person username random

        if (age != null){
            isAgeGender = true;
        }

        if (textSearch != null){
            isSearchPage = true;
        }

        progressFrame = findViewById(R.id.activity_user_category_progress);
        recyclerView = (RecyclerView) findViewById(R.id.user_category_recyclerview_other);

        error_btn_retry = (Button) findViewById(R.id.error_btn_retry);

        latestListAdapter = new LatestListAdapter(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(latestListAdapter);

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
                        if (isAgeGender){
                            mPresenter.getAgeGenderPost01(MySharedPreferences.getUserId(preferences), gender, age, "true", currentPage);
                        } else {
                            mPresenter.getSearchPosts(MySharedPreferences.getUserId(preferences), textSearch, "true", currentPage);
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

        error_btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isAgeGender){
                        mPresenter.getAgeGenderPost01(MySharedPreferences.getUserId(preferences), gender, age, "true", currentPage);
                    } else {
                        mPresenter.getSearchPosts(MySharedPreferences.getUserId(preferences), textSearch, "true", currentPage);
                    }
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
            if (isAgeGender){
                mPresenter.getAgeGenderPost01(MySharedPreferences.getUserId(preferences), gender, age, "true", currentPage);
            } else {
                mPresenter.getSearchPosts(MySharedPreferences.getUserId(preferences), textSearch, "true", currentPage);
            }
            //    loadFirstPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private void showEmptyView() {
        if (no_post_layout.getVisibility() == View.GONE) {
            no_post_layout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            no_post_textview.setText("There are no Latest Posts");
        }
    }

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
                if (isAgeGender){
                    mPresenter.getAgeGenderPost02(MySharedPreferences.getUserId(preferences), gender, age, "true", currentPage);
                } else {
                    mPresenter.getSearchPosts02(MySharedPreferences.getUserId(preferences), textSearch, "true", currentPage);
                }
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
    public void onClick(View v) {

    }

    @Override
    public void getAgeGenderPosts(List<PostsModel> response) {

        progressBar.setVisibility(View.GONE);
        //   progressFrame.setVisibility(View.GONE);
        hideErrorView();

//        Log.e("RESPONSE:::", "Size===" + response.size());
        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

        //   List<PostsModel> model = fetchResults(response);
        //   showRecycleWithDataFilled(response);
        if (response.size() == 0){
            showEmptyView();
            progressFrame.setVisibility(View.GONE);

        } else {
            latestListAdapter.addAll(response);
            arraylistCurrent(response);
            progressFrame.setVisibility(View.GONE);


        }
    }

    @Override
    public void getAgeGenderPosts02(List<PostsModel> response) {
        progressBar.setVisibility(View.GONE);

        hideErrorView();
        isLoading = false;
        latestListAdapter.addAll(response);
        arraylistCurrent(response);
    }

    @Override
    public void getSearchPost(List<PostsModel> response) {

        progressBar.setVisibility(View.GONE);
        //   progressFrame.setVisibility(View.GONE);
        hideErrorView();

        Log.e("RESPONSE:::", "Size===" + response.size());
        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

        //   List<PostsModel> model = fetchResults(response);
        //   showRecycleWithDataFilled(response);
        if (response.size() == 0){
            showEmptyView();
            progressFrame.setVisibility(View.GONE);

        } else {
            latestListAdapter.addAll(response);
            arraylistCurrent(response);
            progressFrame.setVisibility(View.GONE);


        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(PremiumSearchResultActivity.this, WatchVideoAdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

        super.onBackPressed();

    }

    @Override
    public void getSearchPost02(List<PostsModel> response) {
        progressBar.setVisibility(View.GONE);

        hideErrorView();
        isLoading = false;
        latestListAdapter.addAll(response);
        arraylistCurrent(response);
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
