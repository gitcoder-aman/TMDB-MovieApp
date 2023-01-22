package com.tech.mymovietvshows.Fragment;

import static com.tech.mymovietvshows.Fragment.SearchFragment.PASS_QUERY;
import static com.tech.mymovietvshows.MainActivity.api;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tech.mymovietvshows.Adapter.SearchMovieAdapter;
import com.tech.mymovietvshows.Adapter.getCreditCastMovieAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.MovieResponse;
import com.tech.mymovietvshows.Model.MovieResponseResults;
import com.tech.mymovietvshows.PersonDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesSearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private AppCompatTextView noResultFound;

    public MoviesSearchFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_movies_search, container, false);


        recyclerView = view.findViewById(R.id.movie_search_recycler);
        noResultFound = view.findViewById(R.id.noResultFoundMovie);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        RetrofitInstance.getInstance().apiInterface.getMovieByQuery(api,PASS_QUERY).enqueue(new Callback<MovieResponse>() {
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

                        //now store the results in paper database to access offline

                        Paper.book().write("cache", new Gson().toJson(movieResponse));

                        //store also category to set the spinner at app start
                        Paper.book().write("source", "movie");

                    }else{
                        noResultFound.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Not results Found!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    noResultFound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Not available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.d("searchDebug","On Response fail");
            }
        });
        return  view;
    }
}