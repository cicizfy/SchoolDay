package com.example.fangyuanzheng.schoolday;

/**
 * Created by Fangyuan Zheng on 4/11/2017.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TableLayout;
public class CustomAdapter  extends FragmentPagerAdapter {
    private Context context;

    final int PAGE_COUNT=5;
    private String tabTiltles[]=new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday"};
    private LayoutInflater layoutInflater;
    public CustomAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {

        //layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return recycleViewClassScheduleFragment.newInstance(tabTiltles[position],-1);
    }
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position){

        return tabTiltles[position];
    }

}
