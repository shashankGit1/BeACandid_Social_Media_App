package in.becandid.app.becandid.ui.group;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.di.component.ActivityComponent;
import in.becandid.app.becandid.ui.base.BaseFragment;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import link.fls.swipestack.SwipeStack;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityGroupFragment extends BaseFragment implements SwipeStack.SwipeStackListener, CommunityGroupMvpView {


    private Button mButtonLeft, mButtonRight;
    private int currentPage = PAGE_START;
    private static final int PAGE_START = 1;
    private View view;
    private ArrayList<String> groupId;
    private ProgressBar progressBar;
    private List<CommunityGroupPojoNew> response;
    private View progressFrame;
    private ArrayList<CommunityGroupPojo> mData;
    private SwipeStack mSwipeStack;
    private SwipeStackAdapterFinal mAdapter;
    private LinearLayout errorLayout;
    private TextView txtError;
    private ImageView edit_group_fab;
    private Button error_btn_retry;
    private List<CommunityGroupPojoNew> dataSet;
    private List<String> joinedGroupsList;
    SharedPreferences pref;
    String key_user_age;

    public CommunityGroupFragment() {
        // Required empty public constructor
    }

    @Inject
    CommunityGroupMvpPresenter<CommunityGroupMvpView> mPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_community_group_main, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);

        }



        mSwipeStack = (SwipeStack) view.findViewById(R.id.swipeStack);
        mButtonLeft = (Button) view.findViewById(R.id.buttonSwipeLeft);
        mButtonRight = (Button) view.findViewById(R.id.buttonSwipeRight);
        edit_group_fab = (ImageView) view.findViewById(R.id.edit_group_fab);

        joinedGroupsList = new ArrayList<>();
        progressBar = (ProgressBar) view.findViewById(R.id.main_progress);
        progressFrame = view.findViewById(R.id.community_Fragment_frame);
        txtError = (TextView) view.findViewById(R.id.error_txt_cause);
        error_btn_retry = (Button) view.findViewById(R.id.error_btn_retry);

        errorLayout = (LinearLayout) view.findViewById(R.id.error_layout);

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        key_user_age = pref.getString("key_user_age", "2");


        if (key_user_age.equals("1")){
            key_user_age = "1";
        } else {
            key_user_age = "0";
        }

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
        edit_group_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginGroupTagActivity.class);
                v.getContext().startActivity(intent);
                /*
                Intent intent = new Intent(getActivity(), LoginGroupTagActivity.class);
                v.getContext().startActivity(intent);
                */
            }
        });

        groupId = new ArrayList<>();

        try {
            progressFrame.setVisibility(View.VISIBLE);

            mPresenter.getUserJoinedGroupsOnline(MySharedPreferences.getUserId(preferences), key_user_age , "0",currentPage);
            //     loadFirstPage();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // fillWithTestData();
        return view;

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


    @Override
    protected void setUp(View view) {

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
    public void onPause() {
        try {
            String groupList = joinedGroupsList.toString().replace("[", "").replace("]", "");
            if (joinedGroupsList.size() != 0){
                mPresenter.sendJoinedGroupsOnline(groupList, MySharedPreferences.getUserId(preferences));
            }

            //   sendJoinedGroups();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }



    @Override
    public void onStackEmpty() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            currentPage += 1;

            mPresenter.getUserJoinedGroupsOnline02(MySharedPreferences.getUserId(preferences), key_user_age , "0",currentPage);


            // getUserJoinedGroups();

            //loadMorePage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUserJoinedGroups(List<CommunityGroupPojoNew> response) {
        //   progressBar.setVisibility(View.GONE);
        hideErrorView();
       // Log.e("RESPONSE:::", "Size===" + response.size());
        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

        //   List<PostsModel> model = fetchResults(response);
        //   showRecycleWithDataFilled(response);
        mAdapter = new SwipeStackAdapterFinal(response, getContext());
        mSwipeStack.setAdapter(mAdapter);
        mSwipeStack.setListener(CommunityGroupFragment.this);

        // mAdapter.addAll(response);
        arraylistCurrent(response);
        progressFrame.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void getUserJoinedGroups02(List<CommunityGroupPojoNew> response) {

        //   progressBar.setVisibility(View.GONE);
        hideErrorView();
     //   Log.e("RESPONSE:::", "Size===" + response.size());
        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

        //   List<PostsModel> model = fetchResults(response);
        //   showRecycleWithDataFilled(response);
       // mAdapter.clear();
        mSwipeStack.resetStack();
        mAdapter = new SwipeStackAdapterFinal(response, getContext());
        mSwipeStack.setAdapter(mAdapter);
        mSwipeStack.setListener(CommunityGroupFragment.this);

      //  mAdapter.addAll(response);
        arraylistCurrent(response);
        progressFrame.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void sendJoinedGroups(InsertGroupPOJO insertGroupPOJO) {
        progressFrame.setVisibility(View.GONE);

    }



    @Override
    public void onDestroyView() {


        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mPresenter.onDetach();
        super.onDetach();
    }


}

/*
 private void loadFirstPage() throws Exception {
        Log.d(TAG, "loadFirstPage: ");
        hideErrorView();

        if(currentPage > PAGE_START){
            currentPage = PAGE_START;
        }

        if (key_user_age.equals("1")){
            key_user_age = "1";
        } else {
            key_user_age = "0";
        }

*/
/*
        application.getWebService()
                .getUserJoinedGroupsFirstPage(MySharedPreferences.getUserId(preferences), key_user_age , "0",currentPage)
        .retryWhen(new RetryWithDelay(3,2000))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new BaseSubscriber<List<CommunityGroupPojoNew>>() {
@Override
public void onNext(List<CommunityGroupPojoNew> response) {

        progressBar.setVisibility(View.GONE);
        progressFrame.setVisibility(View.GONE);
        hideErrorView();
        Log.e("RESPONSE:::", "Size===" + response.size());
        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

        //   List<PostsModel> model = fetchResults(response);
        //   showRecycleWithDataFilled(response);
        mAdapter = new SwipeStackAdapter(response);
        mSwipeStack.setAdapter(mAdapter);
        mSwipeStack.setListener(CommunityGroupFragment.this);

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

        if (key_user_age.equals("1")){
        key_user_age = "1";
        } else {
        key_user_age = "0";
        }
*/
/*

        application.getWebService()
        .getUserJoinedGroupsFirstPage(MySharedPreferences.getUserId(preferences), key_user_age , "0",currentPage)
        .retryWhen(new RetryWithDelay(3,2000))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new BaseSubscriber<List<CommunityGroupPojoNew>>() {
@Override
public void onNext(List<CommunityGroupPojoNew> response) {


        hideErrorView();
        Log.e("RESPONSE:::", "Size===" + response.size());
        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

        //   List<PostsModel> model = fetchResults(response);
        //   showRecycleWithDataFilled(response);
        mSwipeStack.resetStack();
        mAdapter = new SwipeStackAdapter(response);
        mSwipeStack.setAdapter(mAdapter);
        mSwipeStack.setListener(CommunityGroupFragment.this);

        progressBar.setVisibility(View.GONE);
        progressFrame.setVisibility(View.GONE);

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