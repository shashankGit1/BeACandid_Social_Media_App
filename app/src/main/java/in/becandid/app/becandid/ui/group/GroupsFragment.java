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

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import in.becandid.app.becandid.R;

public class GroupsFragment extends Fragment {
    View view;

    public static GroupsFragment newInstance() {
        GroupsFragment fragment = new GroupsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_two, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.activity_groups_viewpager);
        this.addPages(viewPager);

        // Give the PagerSlidingTabStrip the ViewPager
        TabLayout tabsStrip = (TabLayout) view.findViewById(R.id.activity_groups_tab_layout);
        // Attach the view pager to the tab strip
        tabsStrip.setupWithViewPager(viewPager);

        return view;
    }

    //add all pages
    private void addPages(ViewPager pager) {
        GroupFragmentPagerAdapter adapter =
                new GroupFragmentPagerAdapter(getChildFragmentManager());

        adapter.addPage(new CommunityGroupFragment());
        adapter.addPage(MyGroupsFragment.newInstance());
        adapter.addPage(new NewCommunityGroupFragment());

        //set adapter to pager
        pager.setAdapter(adapter);
    }
}
