package com.tech.mymovietvshows.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerCastCrewAdapter extends FragmentPagerAdapter {

    private final Fragment f1;
    private final Fragment f2;

    public ViewPagerCastCrewAdapter(@NonNull FragmentManager fm, Fragment fragment1, Fragment fragment2) {
        super(fm);

        f1 = fragment1;
        f2 = fragment2;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 1) {
            return f1;
        }
        return f2;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;

        if(position == 0){
            title = "Cast";
        }else if(position == 1) {
            title = "Crew";
        }
        return title;
    }
}
