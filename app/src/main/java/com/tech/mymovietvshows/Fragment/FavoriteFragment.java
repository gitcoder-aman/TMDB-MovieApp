package com.tech.mymovietvshows.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import com.tech.mymovietvshows.Adapter.ViewPagerMovieTVAdapter;

import com.tech.mymovietvshows.R;

public class FavoriteFragment extends Fragment {


    private LinearLayoutCompat favListLayout;
    public static AppCompatTextView emptyFavList;
    private ViewPager fav_viewPager;
    private TabLayout tabLayout;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        favListLayout = view.findViewById(R.id.favListLayout);
        emptyFavList = view.findViewById(R.id.emptyFavList);
        fav_viewPager = view.findViewById(R.id.fav_viewPager);
        tabLayout = view.findViewById(R.id.fav_tabLayout);


        fav_viewPager.setAdapter(new ViewPagerMovieTVAdapter(getChildFragmentManager(), new Fav_frag_movie(),new Fav_frag_tv()));

        tabLayout.setupWithViewPager(fav_viewPager);

        return view;
    }
}