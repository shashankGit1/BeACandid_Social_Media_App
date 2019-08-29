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
import in.becandid.app.becandid.ui.base.BaseFragment;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseFragment implements NotificationMvpView {
    private static final int REQUEST_VIEW_MESSAGE = 1;
    private RecyclerView rv;
    private View progressFrame;
    private View view;

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

        mPresenter.getNotificationOnline(MySharedPreferences.getUserId(preferences), "1");

     //   getNotification(MySharedPreferences.getUserId(preferences), "1");
      //  initializeData();



        return view;

    }



    private void showRecycleWithDataFilled(final List<NotificationPojo> myList) {
        NotificationAdapter adapter = new NotificationAdapter(myList);
        rv.setAdapter(adapter);
    }


    @Override
    protected void setUp(View view) {

    }

    @Override
    public void getNotification(List<NotificationPojo> response) {

        showRecycleWithDataFilled(response);

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();

        super.onDestroyView();
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
