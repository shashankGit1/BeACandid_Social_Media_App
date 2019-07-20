package in.becandid.app.becandid.search_lib;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.ui.group.CommunityGroupPojo;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;

public class SearchActivity extends BaseActivity implements SearchMvpView {
    private List<SearchPojo> persons;
    LinearLayout no_post_layout;
    private RecyclerView rv;
    private TextView no_post_textview;
    private ProgressBar search_progressbar;
    private String searchQuery;

    @Inject
    SearchMvpPresenter<SearchMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(SearchActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        no_post_layout = (LinearLayout) findViewById(R.id.no_post_layout);
        no_post_textview = (TextView) findViewById(R.id.no_post_textview);
        search_progressbar = (ProgressBar) findViewById(R.id.search_progressbar);

        Intent intent = getIntent();
        searchQuery = intent.getStringExtra(Constants.SEARCH_QUERY);

        search_progressbar.setVisibility(View.VISIBLE);
        try {
            mPresenter.getSearchGroup(searchQuery, MySharedPreferences.getUserId(preferences));
           // loadFirstPage(searchQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /////

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

      //  initializeData();
       // initializeAdapter();
    }

    @Override
    protected void setUp() {

    }


    private void showEmptyView() {
        if (no_post_layout.getVisibility() == View.GONE) {
            no_post_layout.setVisibility(View.VISIBLE);
        //    progressBar.setVisibility(View.GONE);
            search_progressbar.setVisibility(View.GONE);
            no_post_textview.setText("There are no Groups for this Keyword");
        }
    }



    @Override
    public void getSearchGroup(List<CommunityGroupPojo> response) {
        if (response.size() == 0){
            showEmptyView();
        } else {
            search_progressbar.setVisibility(View.GONE);
            SearchRecyclerviewAdapter adapter = new SearchRecyclerviewAdapter(response);
            rv.setAdapter(adapter);

        }
    }


    /* @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    } */

}

/*
private void loadFirstPage(String query) throws Exception {
        Log.d(TAG, "loadFirstPage: ");

        application.getWebService()
                .getSearchGroups(query, MySharedPreferences.getUserId(preferences))
                //  .getFacebookContactPosts(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", "true", currentPage)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<CommunityGroupPojo>>() {
                    @Override
                    public void onNext(List<CommunityGroupPojo> response) {

                     //   progressBar.setVisibility(View.GONE);
                     //   progressFrame.setVisibility(View.GONE);
                        Log.e("RESPONSE:::", "Size===" + response.size());
                        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

                        //   List<PostsModel> model = fetchResults(response);
                        //   showRecycleWithDataFilled(response);
                        if (response.size() == 0){
                            showEmptyView();
                        } else {
                            search_progressbar.setVisibility(View.GONE);
                            SearchRecyclerviewAdapter adapter = new SearchRecyclerviewAdapter(response);
                            rv.setAdapter(adapter);

                        }
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                    }
                });
    }
 */
