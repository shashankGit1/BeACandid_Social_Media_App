package in.becandid.app.becandid.ui.filter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.concurrent.TimeoutException;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.InfiniteScrollProvider;
import in.becandid.app.becandid.ui.OnLoadMoreListener;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.discover.LatestListAdapter;
import in.becandid.app.becandid.ui.group.CommunityGroupPojo;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.ui.profile.SimpleDividerItemDecoration;
import timber.log.Timber;

public class FilterSearchPostsActivity extends BaseActivity implements OnLoadMoreListener {

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
    SimpleDraweeView category_back_image;
    CommunityGroupPojo responseGroup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);

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

        /* Group Details Views */

        progressFrame = findViewById(R.id.activity_user_category_progress);
        recyclerView = (RecyclerView) findViewById(R.id.user_category_recyclerview_other);

        Intent intent = getIntent();
        groupId = intent.getStringExtra(Constants.GROUPID);
        error_btn_retry = (Button) findViewById(R.id.error_btn_retry);

        activityInteractionAdapter = new LatestListAdapter(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(activityInteractionAdapter);

        progressBar = (ProgressBar) findViewById(R.id.main_progress);
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
                            //  mPresenter.getYourFeedOnline(MySharedPreferences.getUserId(preferences),MySharedPreferences.getUserId(preferences),"true", String.valueOf(currentPage));

                            //loadFirstPage();

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

        error_btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                 //   loadFirstPage();
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
         //   loadFirstPage();
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
                currentPage += 1;
               // loadNextPage();
                progressBar.setVisibility(View.GONE);
                isLoading = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Timber.d("nothing");
        }
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

                        toolbar.setTitle(response.get(0).getName());

                        Log.e("RESPONSE:::", "Size===" + response.size());

                            activityInteractionAdapter.addAll(response);
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
 */
