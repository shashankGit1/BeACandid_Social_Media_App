package in.becandid.app.becandid.ui.userpost;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.di.component.ActivityComponent;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.InfiniteScrollProvider;
import in.becandid.app.becandid.ui.OnLoadMoreListener;
import in.becandid.app.becandid.ui.base.BaseFragment;
import in.becandid.app.becandid.ui.discover.DiscoverTrendingMvpPresenter;
import in.becandid.app.becandid.ui.discover.DiscoverTrendingMvpView;
import in.becandid.app.becandid.ui.discover.LatestListAdapter;
import in.becandid.app.becandid.ui.profile.SimpleDividerItemDecoration;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingPostFragment extends BaseFragment implements OnLoadMoreListener, DiscoverTrendingMvpView {
    public static final String ARG_LATEST_PAGE = "ARG_LATEST_PAGE";

    private int mPage;

    // private LatestListAdapter latestListAdapter;
    // private RecyclerView recyclerView;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 500;
    private int currentPage = PAGE_START;

    // progressFrame = view.findViewById(R.id.activity_discover_popular);
    // error_btn_retry = (Button) view.findViewById(R.id.error_btn_retry);
    // progressBar = (ProgressBar) view.findViewById(R.id.main_progress);
    // errorLayout = (LinearLayout) view.findViewById(R.id.error_layout)
    //         txtError = (TextView) view.findViewById(R.id.error_txt_cause);


    @BindView(R.id.fragment_discover_popular_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.activity_discover_trending) View progressFrame;
    @BindView(R.id.error_btn_retry)
    Button error_btn_retry;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.no_post_textview) TextView no_post_textview;
    @BindView(R.id.error_txt_cause) TextView txtError;

    LinearLayout no_post_layout;


    //private View progressFrame;
    SwipeRefreshLayout layout;

    ProgressBar progressBar;


    //  ProgressBar progressBar;
    //  Button error_btn_retry;
    //  LinearLayout errorLayout;
    // LinearLayout no_post_layout;
    //  TextView txtError;
    private List<String> popularDiscoverPage;
    // TextView no_post_textview;
    private LatestListAdapter latestListAdapter;
    List<PostsModel> response;
    View view;

    @Inject
    DiscoverTrendingMvpPresenter<DiscoverTrendingMvpView> mPresenter;

    public PendingPostFragment() {
        // Required empty public constructor
    }

    public static PendingPostFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_LATEST_PAGE, page);
        PendingPostFragment fragment = new PendingPostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_LATEST_PAGE);
        popularDiscoverPage = new ArrayList<>();
        latestListAdapter = new LatestListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_discover_trending_02, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);

        }


        no_post_layout = (LinearLayout) view.findViewById(R.id.no_post_layout);

        no_post_layout.setVisibility(View.GONE);

        layout = (SwipeRefreshLayout) view.findViewById(R.id.discover_trending_swipeRefreshLayout02);
        progressBar = (ProgressBar) view.findViewById(R.id.main_progress_discover_trending);

        layout.setColorSchemeResources(android.R.color.holo_orange_light,
                android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //    progressFrame.setVisibility(View.VISIBLE);

                currentPage = PAGE_START;
                try {
                    mPresenter.getPendingPostsOnline(MySharedPreferences.getUserId(preferences),"true", String.valueOf(currentPage));

                    //   getLatestPosts();
                    // loadFirstPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //after shuffle id done then swife refresh is off
                layout.setRefreshing(false);

              /*  new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        currentPage = PAGE_START;
                        try {
                            mPresenter.getTrendingPostsOnline(MySharedPreferences.getUserId(preferences),"true", String.valueOf(currentPage));

                            //   getLatestPosts();
                            // loadFirstPage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //after shuffle id done then swife refresh is off
                        layout.setRefreshing(false);
                    }
                },5000);
                */


            }
        });

      /*  layout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setRefreshing(false);
                        currentPage = PAGE_START;
                        progressFrame.setVisibility(View.VISIBLE);
                        try {
                            mPresenter.getTrendingPostsOnline(MySharedPreferences.getUserId(preferences),"true", String.valueOf(currentPage));

                          //  getTrendingPosts(MySharedPreferences.getUserId(preferences),"true", currentPage);

                         //   loadFirstPage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 4000);
            }
        }); */



        try {
            initUiView(view);
            //   loadFirstPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        error_btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressFrame.setVisibility(View.VISIBLE);

                try {
                    mPresenter.getPendingPostsOnline(MySharedPreferences.getUserId(preferences),"true", String.valueOf(currentPage));

                    //    getTrendingPosts(MySharedPreferences.getUserId(preferences),"true", currentPage);

                    //    loadFirstPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        return view;
    }

    private void initUiView(View view) {
        //   recyclerView = (RecyclerView) view.findViewById(R.id.fragment_discover_popular_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(view.getContext()));
        recyclerView.setAdapter(latestListAdapter);
        InfiniteScrollProvider infiniteScrollProvider=new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView,this);

        try {
            progressFrame.setVisibility(View.VISIBLE);

            mPresenter.getPendingPostsOnline(MySharedPreferences.getUserId(preferences),"true", String.valueOf(currentPage));

            //   getTrendingPosts(MySharedPreferences.getUserId(preferences),"true", currentPage);

            //    loadFirstPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            no_post_textview.setText("There are no Latest Posts");
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
                progressBar.setVisibility(View.VISIBLE);

                mPresenter.getPendingPostsOnline02(MySharedPreferences.getUserId(preferences),"true", String.valueOf(currentPage));

                //      getTrendingPosts();
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
    public void getTrendingPosts(List<PostsModel> response) {

    }

    @Override
    public void getImagePosts(List<PostsModel> response) {

    }

    @Override
    public void getPendingPosts(List<PostsModel> response) {

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
    public void getPendingPosts02(List<PostsModel> response) {

        progressBar.setVisibility(View.GONE);

        isLoading = false;
        latestListAdapter.addAll(response);
        arraylistCurrent(response);
        //  progressFrame.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();

        super.onDestroyView();
    }

    @Override
    public void getTrendingPosts02(List<PostsModel> response) {


    }

    @Override
    public void getImagePosts02(List<PostsModel> response) {

    }
}
