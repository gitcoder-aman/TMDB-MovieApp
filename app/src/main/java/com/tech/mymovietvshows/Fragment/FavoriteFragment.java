package com.tech.mymovietvshows.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.tech.mymovietvshows.Adapter.FavoriteMovieTVAdapter;
import com.tech.mymovietvshows.Adapter.TrendingPopularTopRatedRVAdapter;
import com.tech.mymovietvshows.Database.DatabaseHelper;
import com.tech.mymovietvshows.Database.MovieTV;
import com.tech.mymovietvshows.R;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {


    private RecyclerView watchlistRecycler;
    private LinearLayoutCompat favListLayout;
    private AppCompatTextView emptyFavList;

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
        watchlistRecycler = view.findViewById(R.id.watchlistRecycler);
        favListLayout = view.findViewById(R.id.favListLayout);
        emptyFavList = view.findViewById(R.id.emptyFavList);

        watchlistRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getContext());

        ArrayList<MovieTV> arrayList = (ArrayList<MovieTV>) databaseHelper.movieTVDAO().getAllMovieTV();

        if(arrayList!= null && !arrayList.isEmpty()) {

            for (int i = 0; i < arrayList.size(); i++) {
                Log.d("tmz", "Title : " + arrayList.get(i).getMovieName() + "PosterLink : " + arrayList.get(i).getPosterImage());
            }
            FavoriteMovieTVAdapter adapter = new FavoriteMovieTVAdapter(getContext(),arrayList);
            watchlistRecycler.setAdapter(adapter);

            //Create some animation view item loading
            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
            watchlistRecycler.setLayoutAnimation(controller);
            watchlistRecycler.scheduleLayoutAnimation();
            favListLayout.setVisibility(View.VISIBLE);

        }else{
            favListLayout.setVisibility(View.GONE);
            emptyFavList.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "No data of Favorite", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}