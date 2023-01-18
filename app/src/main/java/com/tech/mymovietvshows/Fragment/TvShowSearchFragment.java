package com.tech.mymovietvshows.Fragment;

import static com.tech.mymovietvshows.Fragment.SearchFragment.PASS_QUERY;
import static com.tech.mymovietvshows.MainActivity.api;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.tech.mymovietvshows.Adapter.SearchMovieAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.MovieResponse;
import com.tech.mymovietvshows.Model.MovieResponseResults;
import com.tech.mymovietvshows.Model.TvResponseModel.TvResponse;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowSearchFragment extends Fragment {

    private RecyclerView recyclerView;

    public TvShowSearchFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_tv_show_search, container, false);
        recyclerView = view.findViewById(R.id.tv_search_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        RetrofitInstance.getInstance().apiInterface.getTvByQuery(api,PASS_QUERY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {

                MovieResponse movieResponse = response.body();
                if(movieResponse != null){
                    List<MovieResponseResults> movieResponseResultsList = movieResponse.getResults();

                    if(movieResponseResultsList != null && !movieResponseResultsList.isEmpty()){

                        SearchMovieAdapter adapter =  new SearchMovieAdapter(getContext(),movieResponseResultsList);
                        recyclerView.setAdapter(adapter);

                        //Create some animation view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                        recyclerView.setLayoutAnimation(controller);
                        recyclerView.scheduleLayoutAnimation();

                    }else{
                        Toast.makeText(getContext(), "Not results Found!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Not available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.d("searchDebug","On Response fail");
            }
        });


        return view;
    }
}