package in.becandid.app.becandid.ui.discover;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.userpost.OnlyImageFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    View view;

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }


    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_feed, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.activity_discover_viewpager);
        this.addPages(viewPager);

        // Give the PagerSlidingTabStrip the ViewPager
        TabLayout tabsStrip = (TabLayout) view.findViewById(R.id.activity_discover_tab_layout);
        // Attach the view pager to the tab strip
        tabsStrip.setupWithViewPager(viewPager);

       // tabsStrip.setViewPager(viewPager);

        return view;

    }

    //add all pages
    private void addPages(ViewPager pager) {
        DiscoverActivityFragmentPagerAdapter adapter =
                new DiscoverActivityFragmentPagerAdapter(getChildFragmentManager());
        adapter.addPage(DiscoverLatestFragment.newInstance(1));
        adapter.addPage(DiscoverTrendingFragment.newInstance(1));
        adapter.addPage(OnlyImageFragment.newInstance(1));

        //set adapter to pager
        pager.setAdapter(adapter);
    }

}
