package in.becandid.app.becandid.ui.discover;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.becandid.app.becandid.di.component.ActivityComponent;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.profile.SimpleDividerItemDecoration;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseFragment;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.InfiniteScrollProvider;
import in.becandid.app.becandid.ui.OnLoadMoreListener;
import timber.log.Timber;


public class FacebookFragment extends BaseFragment implements OnLoadMoreListener, DiscoverFacebookMvpView {
    public static final String ARG_LATEST_PAGE = "ARG_LATEST_PAGE";

   // private LatestListAdapter latestListAdapter;
  //  private RecyclerView recyclerView;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 500;
    private int currentPage = PAGE_START;

   // progressFrame = view.findViewById(R.id.facebook_framelayout);
   // error_btn_retry = (Button) view.findViewById(R.id.error_btn_retry);
   // progressBar = (ProgressBar) view.findViewById(R.id.main_progress);
    // errorLayout = (LinearLayout) view.findViewById(R.id.error_layout);
  //  no_post_layout = (LinearLayout) view.findViewById(R.id.no_post_layout);
  //  no_post_textview = (TextView) view.findViewById(R.id.no_post_textview);
  //  txtError = (TextView) view.findViewById(R.id.error_txt_cause);

    @BindView(R.id.facebook_recyclerview) RecyclerView recyclerView;
    View progressFrame;

    ProgressBar progressBar;

    @BindView(R.id.error_btn_retry) Button error_btn_retry;
    @BindView(R.id.error_layout) LinearLayout errorLayout;
    @BindView(R.id.no_post_layout) LinearLayout no_post_layout;
    @BindView(R.id.no_post_textview) TextView no_post_textview;
    @BindView(R.id.error_txt_cause) TextView txtError;

   // private View progressFrame;

   // ProgressBar progressBar;
   // Button error_btn_retry;
  //  LinearLayout errorLayout;
  //  LinearLayout no_post_layout;
  //  TextView txtError;
    private List<String> popularDiscoverPage;
  //  TextView no_post_textview;
    private LatestListAdapter latestListAdapter;
    List<PostsModel> response;
    SwipeRefreshLayout layout;
    View view;

    @Inject
    DiscoverFacebookMvpPresenter<DiscoverFacebookMvpView> mPresenter;

    public FacebookFragment() {
        // Required empty public constructor
    }

    public static FacebookFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_LATEST_PAGE, page);
        FacebookFragment fragment = new FacebookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        popularDiscoverPage = new ArrayList<>();
        latestListAdapter = new LatestListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_facebook_friend, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);

        }


        progressFrame = view.findViewById(R.id.facebook_framelayout);
        progressBar = (ProgressBar) view.findViewById(R.id.main_progress_discover_facebook);
        layout = (SwipeRefreshLayout) view.findViewById(R.id.facebook_swipeRefreshLayout);
        layout.setColorSchemeResources(android.R.color.holo_orange_light,
                android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //    progressFrame.setVisibility(View.VISIBLE);
                currentPage = PAGE_START;
                try {
                    mPresenter.getFacebookPostsOnline(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", String.valueOf(currentPage));

                    //loadFirstPage();

                    //   getLatestPosts();
                    // loadFirstPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //after shuffle id done then swife refresh is off
                layout.setRefreshing(false);
                /*
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        currentPage = PAGE_START;
                        try {
                            mPresenter.getFacebookPostsOnline(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", String.valueOf(currentPage));

                            //loadFirstPage();

                            //   getLatestPosts();
                            // loadFirstPage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //after shuffle id done then swife refresh is off
                        layout.setRefreshing(false);
                    }
                },5000);*/


            }
        });


        try {
            initUiView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }

        error_btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressFrame.setVisibility(View.VISIBLE);

                try {
                    mPresenter.getFacebookPostsOnline(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", String.valueOf(currentPage));

                 //   getFacebookPosts(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", currentPage);

                  //  loadFirstPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        //recyclerview
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_discover_recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//        recyclerView.setAdapter(new MyRecyclerAdapter(this.getActivity(), getDiscoverLatest()));
        return view;
    }

    private void initUiView(View view) {
      //  recyclerView = (RecyclerView) view.findViewById(R.id.facebook_recyclerview);
      //  LinearLayoutManager linearLayout = new LinearLayoutManager(this.getActivity());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(recyclerView.getContext());
        latestListAdapter = new LatestListAdapter(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(view.getContext()));
        recyclerView.setAdapter(latestListAdapter);
        InfiniteScrollProvider infiniteScrollProvider=new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView,this);


        try {
            progressFrame.setVisibility(View.VISIBLE);

            mPresenter.getFacebookPostsOnline(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", String.valueOf(currentPage));

          //  getFacebookPosts(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", currentPage);
           // loadFirstPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                int totalItemCount = linearLayoutManager.getItemCount();
                isLoading = true;
                currentPage += 1;

                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        */
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showErrorView(Throwable throwable) {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            txtError.setText(fetchErrorMessage(throwable));
        }
    }

    private void showEmptyView() {
        if (no_post_layout.getVisibility() == View.GONE) {
            no_post_layout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            no_post_textview.setText("There are no Facebook Friends Posts");
        }
    }

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setUp(View view) {

    }

    /**
     * @param throwable to identify the type of error
     * @return appropriate error message
     */
    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }



    @Override
    public String toString() {
        return "documentary";
    }

    private void showRecycleWithDataFilled(final List<PostsModel> myList) {

    }


  /*  @Override
    public void retryPageLoad() {
        try {
            loadNextPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } */

    private void arraylistCurrent(List<PostsModel> response){
        this.response = response;
    }

    @Override
    public void onLoadMore() {
        if (response.size() == 25){
            try {
                currentPage += 1;
                // enter facebook id
                progressBar.setVisibility(View.VISIBLE);

                mPresenter.getFacebookPostsOnline02(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", String.valueOf(currentPage));

             //  getFacebookPosts(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", currentPage);
              //  loadNextPage();
              //  progressBar.setVisibility(View.GONE);
                isLoading = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Timber.d("nothing");
        }
    }

    @Override
    public void getFacebookPosts(List<PostsModel> response) {
        progressBar.setVisibility(View.GONE);

        if (response.size() == 0){
            showEmptyView();
            progressFrame.setVisibility(View.GONE);

        } else {
            latestListAdapter.addAll(response);
            progressFrame.setVisibility(View.GONE);
            arraylistCurrent(response);

        }
    }

    @Override
    public void getFacebookPosts02(List<PostsModel> response) {
        progressBar.setVisibility(View.GONE);

        hideErrorView();
        isLoading = false;
        latestListAdapter.addAll(response);
        arraylistCurrent(response);


    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();

        super.onDestroyView();
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
                .getFacebookPosts(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", currentPage)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<PostsModel>>() {
                    @Override
                    public void onNext(List<PostsModel> response) {
                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        hideErrorView();
                        Log.e("RESPONSE:::", "Size===" + response.size());
                        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

                        //   List<PostsModel> model = fetchResults(response);
                        //   showRecycleWithDataFilled(response);
                        if (response.size() == 0){
                            showEmptyView();
                        } else {
                            latestListAdapter.addAll(response);
                            progressFrame.setVisibility(View.GONE);
                            arraylistCurrent(response);

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

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        hideErrorView();

        application.getWebService()
                .getFacebookPosts(MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserId(preferences), "true", currentPage)
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<List<PostsModel>>() {
                    @Override
                    public void onNext(List<PostsModel> response) {
                        hideErrorView();
                        isLoading = false;
                        latestListAdapter.addAll(response);
                        arraylistCurrent(response);

                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        Timber.e("error is: " + e);
                        latestListAdapter.showRetry(true, fetchErrorMessage(e));
                    }
                });

    }
 */
