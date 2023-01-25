package com.tech.mymovietvshows.Fragment;

import static com.tech.mymovietvshows.Fragment.FavoriteFragment.emptyFavList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tech.mymovietvshows.Adapter.FavoriteMovieTVAdapter;
import com.tech.mymovietvshows.Database.DatabaseHelper;
import com.tech.mymovietvshows.Database.MovieTV;
import com.tech.mymovietvshows.R;

import java.util.ArrayList;

public class Fav_frag_tv extends Fragment {

    private RecyclerView favTVRecyclerView;
    private int index = 0;
    public Fav_frag_tv() {
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
        View view =  inflater.inflate(R.layout.fragment_fav_frag_tv, container, false);

        favTVRecyclerView = view.findViewById(R.id.favTVRecyclerView);

        favTVRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(getContext());

        ArrayList<MovieTV> arrayList = (ArrayList<MovieTV>) databaseHelper.movieTVDAO().getAllMovieTV();
        ArrayList<MovieTV>arrayOfTV = new ArrayList<>();

        for(int i = 0; i < arrayList.size(); i++){
            if(arrayList.get(i).getType().equals("tv")){
                arrayOfTV.add(index,arrayList.get(i));
                index++;
            }
        }


        if(!arrayOfTV.isEmpty()){
            FavoriteMovieTVAdapter adapter = new FavoriteMovieTVAdapter(getContext(),arrayOfTV);
            favTVRecyclerView.setAdapter(adapter);

            favTVRecyclerView.setVisibility(View.VISIBLE);
            //        Create some animation view item loading
            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
            favTVRecyclerView.setLayoutAnimation(controller);
            favTVRecyclerView.scheduleLayoutAnimation();
        }else{
            emptyFavList.setVisibility(View.VISIBLE);
        }



        return view;
    }
}