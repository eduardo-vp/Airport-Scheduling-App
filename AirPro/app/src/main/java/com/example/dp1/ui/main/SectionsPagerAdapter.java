package com.example.dp1.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dp1.Frag1;
import com.example.dp1.Frag2;
import com.example.dp1.Frag3;
import com.example.dp1.FragError;
import com.example.dp1.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private boolean valid;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3,R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm, boolean _valid) {
        super(fm);
        valid = _valid;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment x = null;
        if(!valid){
            x = new FragError();
        }else {
            switch (position) {
                case 0:
                    x = new Frag1();
                    break;
                case 1:
                    x = new Frag2();
                    break;
                case 2:
                    x = new Frag3();
                    break;
            }
        }
        return x;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(!valid)
            return mContext.getResources().getString(TAB_TITLES[3]);
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        if(!valid) return 1;
        return 3;
    }
}