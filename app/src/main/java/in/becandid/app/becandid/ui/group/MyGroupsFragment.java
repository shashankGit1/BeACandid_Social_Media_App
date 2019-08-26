/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package in.becandid.app.becandid.ui.group;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.core.content.res.ResourcesCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.di.component.ActivityComponent;
import in.becandid.app.becandid.search_lib.SearchActivity;
import in.becandid.app.becandid.autocomplete.Autocomplete;
import in.becandid.app.becandid.autocomplete.AutocompleteCallback;
import in.becandid.app.becandid.autocomplete.AutocompletePresenter;
import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.autocomplete.UserPresenter;
import in.becandid.app.becandid.ui.base.BaseFragment;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import timber.log.Timber;

public class MyGroupsFragment extends BaseFragment implements MyGroupsMvpView {
    private View view;
    private GridAdapter gridAdapter;
    private static final int PAGE_START = 1;
    private int TOTAL_PAGES = 500;
    private int currentPage = PAGE_START;
    private LinearLayout errorLayout;
    private TextView txtError;
    private Button error_btn_retry;
    private SwipeRefreshLayout layout;
    private ProgressBar progressBar;
    private List<CommunityGroupPojoNew> response;
    private View progressFrame;
    private GridView gridView;
   // private EditText search_groups;
   // private TextView create_group;
    private Autocomplete userAutocomplete;
    private SharedPreferences pref;
    List<GroupUser> joinedGroup;
    private String key_user_age;
    private String group_id;
    private int totalItems;
    Drawable backgroundDrawable;

    @Inject
    MyGroupsMvpPresenter<MyGroupsMvpView> mPresenter;


    public static MyGroupsFragment newInstance() {
        MyGroupsFragment fragment = new MyGroupsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_three, container, false);

        setHasOptionsMenu(true);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);

        }



        gridView = (GridView) view.findViewById(R.id.grid);


       // search_groups.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0, 0);

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        key_user_age = pref.getString("key_user_age", "2");


        progressBar = (ProgressBar) view.findViewById(R.id.main_progress);
      //  create_group = (TextView) view.findViewById(R.id.create_group);
        progressFrame = view.findViewById(R.id.groups_Fragment_frame);

        layout = (SwipeRefreshLayout) view.findViewById(R.id.my_group_swipeRefreshLayout);
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
                            mPresenter.getListUserJoinedGroups01(MySharedPreferences.getUserId(preferences), key_user_age, "1",String.valueOf(currentPage));

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

        txtError = (TextView) view.findViewById(R.id.error_txt_cause);
        error_btn_retry = (Button) view.findViewById(R.id.error_btn_retry);

        errorLayout = (LinearLayout) view.findViewById(R.id.error_layout);
        gridAdapter = new GridAdapter(getActivity());


        error_btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage = PAGE_START;
                if (key_user_age.equals("1")){
                    key_user_age = "1";
                } else {
                    key_user_age = "0";
                }
                try {
                    mPresenter.getListUserJoinedGroups01(MySharedPreferences.getUserId(preferences), key_user_age, "1",String.valueOf(currentPage));

                //    getUserJoinedGroups(MySharedPreferences.getUserId(preferences), key_user_age, "1",currentPage);
                   // loadFirstPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

//        create_group.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
//                startActivity(intent);
//            }
//        });

        try {
            if (key_user_age.equals("1")){
                key_user_age = "1";
            } else {
                key_user_age = "0";
            }
            currentPage = PAGE_START;
            progressFrame.setVisibility(View.VISIBLE);

            // todo add zip operator to get result from 2 api together
            mPresenter.getListAllGroupsOnline(MySharedPreferences.getUserId(preferences));
            mPresenter.getListUserJoinedGroups01(MySharedPreferences.getUserId(preferences), key_user_age, "1",String.valueOf(currentPage));

         //   getAllGroups(MySharedPreferences.getUserId(preferences));
        //    getUserJoinedGroups(MySharedPreferences.getUserId(preferences), key_user_age, "1",currentPage);
           // loadFirstPage();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }





    private void setupUserAutocomplete(List<GroupUser> joinedGroup, View view) {
       // search_groups = (EditText) view.findViewById(R.id.search_groups_get_all);
        float elevation = 6f;
        backgroundDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.shadow, null);;
        AutocompletePresenter<GroupUser> presenter = new UserPresenter(getActivity(), joinedGroup);
        AutocompleteCallback<GroupUser> callback = new AutocompleteCallback<GroupUser>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, GroupUser item) {

                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(Constants.SEARCH_QUERY, item.getName());
                view.getContext().startActivity(intent);

                editable.clear();
             //   editable.append(item.getName());
                return true;
            }

            public void onPopupVisibilityChanged(boolean shown) {}
        };

//        userAutocomplete = Autocomplete.<GroupUser>on(search_groups)
//                .with(elevation)
//                .with(backgroundDrawable)
//                .with(presenter)
//                .with(callback)
//                .build();
//
//        search_groups.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                userAutocomplete.showPopup(" ");
//            }
//        });
    }

    private void showErrorView(Throwable throwable) {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            txtError.setText(fetchErrorMessage(throwable));
        }
    }



    private void setGridViewListender(){

        gridView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if(firstVisibleItem + visibleItemCount >= totalItems){

                    if (response.size() == 25){
                        try {
                            currentPage += 1;
                            progressBar.setVisibility(View.VISIBLE);
                            mPresenter.getListUserJoinedGroups02(MySharedPreferences.getUserId(preferences), key_user_age, "1",String.valueOf(currentPage));
                        //    getUserJoinedGroups(MySharedPreferences.getUserId(preferences), key_user_age, "1",currentPage);
                          //  loadNextPage();
                            totalItems += 25;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Timber.d("nothing");
                    }

                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){

            }
        });
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


    @Override
    public void getListAllGroups(List<GroupUser> groupUsers){
        //Creating the ArrmpreayAdapter instance having the country list


        //  response = new ArrayList<>();
        setupUserAutocomplete(groupUsers, view);
    }

    @Override
    public void getListUserJoinedGroups01(List<CommunityGroupPojoNew> response) {
   //     progressBar.setVisibility(View.GONE);
        hideErrorView();
        Log.e("RESPONSE:::", "Size===" + response.size());
        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

        //   List<PostsModel> model = fetchResults(response);
        //   showRecycleWithDataFilled(response);
        gridAdapter.clear();


        gridAdapter.addAll(response);
        gridView.setAdapter(gridAdapter);
        arraylistCurrent(response);

        totalItems = response.size();

        setGridViewListender();
        progressFrame.setVisibility(View.GONE);

    }


    @Override
    public void getListUserJoinedGroups02(List<CommunityGroupPojoNew> response) {
        hideErrorView();
        Log.e("RESPONSE:::", "Size===" + response.size());
        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

        //   List<PostsModel> model = fetchResults(response);
        //   showRecycleWithDataFilled(response);
        gridAdapter.addAll(response);
        //    gridView.setAdapter(gridAdapter);
        arraylistCurrent(response);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();

        super.onDestroyView();
    }


}

/*
private void getAllGroups(View view) throws Exception {
        Log.d(TAG, "loadFirstPage: ");

        application.getWebService()
                .getAllGroups(MySharedPreferences.getUserId(preferences))
                .retryWhen(new RetryWithDelay(3,2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<GroupUser>>() {
                    @Override
                    public void onNext(List<GroupUser> response) {

                        //Creating the ArrayAdapter instance having the country list

                        //  response = new ArrayList<>();
                        Timber.d(response.get(0).getName());
                        progressFrame.setVisibility(View.GONE);
                        setupUserAutocomplete(response, view);

                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                    }
                });
    }
    */
/*

 private void loadFirstPage() throws Exception {
        Log.d(TAG, "loadFirstPage: ");
     //   hideErrorView();

        if(currentPage > PAGE_START){
            currentPage = PAGE_START;
        }

        if (key_user_age.equals("1")){
            key_user_age = "1";
        } else {
            key_user_age = "0";
        }

        String userid = MySharedPreferences.getUserId(preferences);

*/
/*
        application.getWebService()
                .getUserJoinedGroupsFirstPage(MySharedPreferences.getUserId(preferences), key_user_age  group_id, "1",currentPage)
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
        gridAdapter.addAll(response);
        gridView.setAdapter(gridAdapter);
        arraylistCurrent(response);

        totalItems = response.size();

        setGridViewListender();

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

        if (key_user_age.equals("1")){
            key_user_age = "1";
        } else {
            key_user_age = "0";
        }

        application.getWebService()
                .getUserJoinedGroupsFirstPage(MySharedPreferences.getUserId(preferences), key_user_age  group_id, "1",currentPage)
        .retryWhen(new RetryWithDelay(3,2000))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new BaseSubscriber<List<CommunityGroupPojoNew>>() {
@Override
public void onNext(List<CommunityGroupPojoNew> response) {
        progressBar.setVisibility(View.GONE);
        hideErrorView();
        Log.e("RESPONSE:::", "Size===" + response.size());
        //         List<PostsModel> body = (List<PostsModel>) response.get(0).body();

        //   List<PostsModel> model = fetchResults(response);
        //   showRecycleWithDataFilled(response);
        gridAdapter.addAll(response);
        //    gridView.setAdapter(gridAdapter);
        arraylistCurrent(response);

        }
@Override
public void onError(Throwable e){
        e.printStackTrace();
        progressBar.setVisibility(View.GONE);
        showErrorView(e);
        }
        });

        }
 */
