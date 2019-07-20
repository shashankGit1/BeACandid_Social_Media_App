package in.becandid.app.becandid.ui.profile;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Harish on 7/29/2016.
 */

public class ProfileFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    ArrayList<Fragment> pages = new ArrayList<>();
    private String tabTitles[] = new String[]{"My Posts", "History", "Notification"};

    public ProfileFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show latest discover fragment
                return MyPostsFragment.newInstance(1);

            case 1: // Fragment # 0 - This will popular discover fragment
                return DiscoverHistoryFragment.newInstance(1);
            case 2: // Fragment # 1 - This will show Featured
                return NotificationFragment.newInstance();
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
}
