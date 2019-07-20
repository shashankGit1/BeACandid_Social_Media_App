package in.becandid.app.becandid.ui.group;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Harish on 7/29/2016.
 */

public class GroupFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    ArrayList<Fragment> pages = new ArrayList<>();
    private String tabTitles[] = new String[]{"Community", "My Groups", "New Groups"};

    public GroupFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show latest discover fragment
            return new CommunityGroupFragment();
            case 1: // Fragment # 0 - This will popular discover fragment
                return MyGroupsFragment.newInstance();
            case 2: // Fragment # 0 - This will popular discover fragment
                return new NewCommunityGroupFragment();
            
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    //add a page
    public void addPage(Fragment fragment) {
        pages.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        try{
            super.finishUpdate(container);
        } catch (NullPointerException nullPointerException){
            System.out.println("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
        }
    }
}
