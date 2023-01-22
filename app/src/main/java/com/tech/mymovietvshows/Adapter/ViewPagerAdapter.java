package com.tech.mymovietvshows.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tech.mymovietvshows.Fragment.Section.NowPlayingFragment;
import com.tech.mymovietvshows.Fragment.Section.PopularFragment;
import com.tech.mymovietvshows.Fragment.Section.TopRatedFragment;
import com.tech.mymovietvshows.Fragment.Section.TrendingFragment;
import com.tech.mymovietvshows.Fragment.Section.UpcomingFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Log.d("debug", "open trending");
        switch (position) {

            case 1:
                return new PopularFragment();
            case 2:
                return new UpcomingFragment();
            case 3:
                return new NowPlayingFragment();
            case 4:
                return new TopRatedFragment();
            default:
                return new TrendingFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;

        if (position == 0) {
            title = "Trending";
        } else if (position == 1) {
            title = "Popular";
        } else if (position == 2) {
            title = "Upcoming";
        } else if (position == 3) {
            title = "Now Playing";
        } else if (position == 4) {
            title = "Top Rated";
        }
        return title;
    }
}
