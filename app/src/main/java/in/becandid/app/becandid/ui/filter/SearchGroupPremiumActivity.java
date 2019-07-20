package in.becandid.app.becandid.ui.filter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.autocomplete.Autocomplete;
import in.becandid.app.becandid.autocomplete.AutocompleteCallback;
import in.becandid.app.becandid.autocomplete.AutocompletePresenter;
import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.autocomplete.UserPresenter;
import in.becandid.app.becandid.ui.base.BaseActivity;

public class SearchGroupPremiumActivity extends BaseActivity {
    private EditText search_groups;
    private Autocomplete userAutocomplete;
    private View premium_frame;
    private RecyclerView rv_premium_group_search;
    private SearchGroupRecyclerviewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group_premium);

        premium_frame = findViewById(R.id.premium_frame_group_search);
        rv_premium_group_search = (RecyclerView) findViewById(R.id.rv_premium_group_search);
        search_groups = (EditText) findViewById(R.id.search_groups_premium);

        search_groups.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });



        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv_premium_group_search.setLayoutManager(llm);
        rv_premium_group_search.setHasFixedSize(true);


        try {
           // getAllGroups();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setUp() {

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




    private void setupUserAutocomplete(List<GroupUser> joinedGroup) {
        float elevation = 6f;
        Drawable backgroundDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.shadow, null);;
        AutocompletePresenter<GroupUser> presenter = new UserPresenter(SearchGroupPremiumActivity.this, joinedGroup);
        AutocompleteCallback<GroupUser> callback = new AutocompleteCallback<GroupUser>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, GroupUser item) {

                premium_frame.setVisibility(View.VISIBLE);

                try {
                   // loadFirstPage(item.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

              /*  Intent intent = new Intent(SearchGroupPremiumActivity.this, SearchActivity.class);
                intent.putExtra(Constants.SEARCH_QUERY, item.getName());
                startActivity(intent); */

                editable.clear();
                //   editable.append(item.getName());
                return true;
            }

            public void onPopupVisibilityChanged (boolean shown) {

            }
        };

        userAutocomplete = Autocomplete.<GroupUser>on(search_groups)
                .with(elevation)
                .with(backgroundDrawable)
                .with(presenter)
                .with(callback)
                .build();

        search_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAutocomplete.showPopup(" ");
            }
        });
    }



}

/*
private void getAllGroups() throws Exception {
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
                        premium_frame.setVisibility(View.GONE);
                        setupUserAutocomplete(response);

                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                    }
                });
    }
 */
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
                            return;
                        } else {
                            premium_frame.setVisibility(View.GONE);
                            adapter = new SearchGroupRecyclerviewAdapter(response, SearchGroupPremiumActivity.this);
                            rv_premium_group_search.setAdapter(adapter);

                        }
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                    }
                });
    }
 */
