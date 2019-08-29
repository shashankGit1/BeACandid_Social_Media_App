package in.becandid.app.becandid.ui.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.security.acl.Group;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.autocomplete.Autocomplete;
import in.becandid.app.becandid.autocomplete.AutocompleteCallback;
import in.becandid.app.becandid.autocomplete.AutocompletePresenter;
import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.autocomplete.UserPresenter;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.search_lib.SearchActivity;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;

public class GroupSearchActivity extends BaseActivity implements GroupSearchMvpView {

    @Inject
    GroupSearchMvpPresenter<GroupSearchMvpView> mPresenter;

    @BindView(R.id.search_groups) protected EditText search_groups;

    EditText search_groups02;

    private Autocomplete userAutocomplete;
    Drawable backgroundDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_search);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(GroupSearchActivity.this);

        toolbar.setNavigationIcon(R.drawable.ic_clear_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Search Group");

        mPresenter.samplenetwork(MySharedPreferences.getUserId(preferences));
    }

    private void setupUserAutocomplete(List<GroupUser> joinedGroup) {
        search_groups = (EditText) findViewById(R.id.search_groups_get_all);
        float elevation = 6f;
        backgroundDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.shadow, null);;
        AutocompletePresenter<GroupUser> presenter = new UserPresenter(GroupSearchActivity.this, joinedGroup);
        AutocompleteCallback<GroupUser> callback = new AutocompleteCallback<GroupUser>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, GroupUser item) {

                Intent intent = new Intent(GroupSearchActivity.this, SearchActivity.class);
                intent.putExtra(Constants.SEARCH_QUERY, item.getName());
                startActivity(intent);

                editable.clear();
                //   editable.append(item.getName());
                return true;
            }

            public void onPopupVisibilityChanged(boolean shown) {}
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

    @Override
    protected void setUp() {

    }

    @Override
    public void getResponse(List<GroupUser> response) {

        setupUserAutocomplete(response);
    }
}
