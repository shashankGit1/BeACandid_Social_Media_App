package in.becandid.app.becandid.ui.profile;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.di.component.ActivityComponent;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.InfiniteScrollProvider;
import in.becandid.app.becandid.ui.OnLoadMoreListener;
import in.becandid.app.becandid.ui.base.BaseFragment;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseFragment implements NotificationMvpView, OnLoadMoreListener {
    private static final int REQUEST_VIEW_MESSAGE = 1;
    private RecyclerView rv;
    private View progressFrame;
    private List<NotificationPojo>  mylist = new ArrayList<>();
    private View view;
    private static final String PAGE_START = "1";
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private String currentPage = PAGE_START;
    private List<NotificationPojo> response;


    private ArrayList data = new ArrayList<>();

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }


    public NotificationFragment() {
        // Required empty public constructor
    }

    @Inject
    NotificationMvpPresenter<NotificationMvpView> mPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);

        }


        progressFrame = view.findViewById(R.id.activity_notification_progress);

        if (getActivity().getActionBar() != null){
            getActivity().getActionBar().setTitle("Notification");
        }

        rv = (RecyclerView) view.findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        rv.setHasFixedSize(true);
        InfiniteScrollProvider infiniteScrollProvider = new InfiniteScrollProvider();
        infiniteScrollProvider.attach(rv,this);

        mPresenter.getNotificationOnline(MySharedPreferences.getUserId(preferences),currentPage);

     //   getNotification(MySharedPreferences.getUserId(preferences), "1");
      //  initializeData();



        return view;

    }
    private void arraylistCurrent(List<NotificationPojo> response){
        this.response = response;
    }



    private void showRecycleWithDataFilled(final List<NotificationPojo> myList) {
        mylist.addAll(myList);
        NotificationAdapter adapter = new NotificationAdapter(mylist);
        rv.setAdapter(adapter);
    }


    @Override
    protected void setUp(View view) {

    }

    @Override
    public void getNotification(List<NotificationPojo> response) {
        arraylistCurrent(response);
        showRecycleWithDataFilled(response);

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();

        super.onDestroyView();
    }

    @Override
    public void onLoadMore() {
        //mPresenter.getNotificationOnline(MySharedPreferences.getUserId(preferences), "2");
        if (response.size()>0){
            try {
                int next = Integer.parseInt(currentPage);
                next++;
                currentPage =String.valueOf(next);
                //progressBar.setVisibility(View.VISIBLE);

                mPresenter.getNotificationOnline(MySharedPreferences.getUserId(preferences), currentPage);
                //  loadNextPage();
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
private void initializeData() {

        application.getWebService()
                .getNotificationPosts(MySharedPreferences.getUserId(preferences), "1")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<List<NotificationPojo>>() {
                    @Override
                    public void onNext(List<NotificationPojo> response) {
                        showRecycleWithDataFilled(response);
                        progressFrame.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressFrame.setVisibility(View.GONE);
                        try {
                            Timber.e(e.getMessage());
                            //      Toast.makeText(UserHugCounterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
    }
 */
