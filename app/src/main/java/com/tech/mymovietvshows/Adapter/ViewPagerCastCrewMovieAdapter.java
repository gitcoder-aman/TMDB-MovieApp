package com.tech.mymovietvshows.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tech.mymovietvshows.Fragment.FragmentCastDetails;
import com.tech.mymovietvshows.Fragment.FragmentCastMovie;
import com.tech.mymovietvshows.Fragment.FragmentCrewDetails;
import com.tech.mymovietvshows.Fragment.FragmentCrewMovie;

public class ViewPagerCastCrewMovieAdapter extends FragmentPagerAdapter {

    public ViewPagerCastCrewMovieAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 1) {
            return new FragmentCrewMovie();
        }
        return new FragmentCastMovie();
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
