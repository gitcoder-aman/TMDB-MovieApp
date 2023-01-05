package com.tech.mymovietvshows.Fragment.Section;

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

import com.tech.mymovietvshows.Adapter.TrendingPopularTopRatedRVAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieModel;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieResultModel;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopRatedFragment extends Fragment {

    private RecyclerView topRatedRecyclerView;

    public TopRatedFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_top_rated, container, false);

        topRatedRecyclerView = view.findViewById(R.id.topRated_recyclerView);

        topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        RetrofitInstance.getInstance().apiInterface.getTopRatedMovie(api)
                .enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {
                        Log.d("debug", "OnResponse");

                        TrendingPopularTopRatedMovieModel TPMovieModel = response.body();

                        if (TPMovieModel != null) {

                            List<TrendingPopularTopRatedMovieResultModel> TPMovieResultModelList = TPMovieModel.getResults();

                            if (TPMovieResultModelList != null && !TPMovieResultModelList.isEmpty()) {

                                TrendingPopularTopRatedRVAdapter TPRVAdapter = new TrendingPopularTopRatedRVAdapter(getContext(),TPMovieResultModelList);
                                topRatedRecyclerView.setAdapter(TPRVAdapter);

                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                                topRatedRecyclerView.setLayoutAnimation(controller);
                                topRatedRecyclerView.scheduleLayoutAnimation();

                            } else {
                                Log.d("debug", "Null List");
                            }
                        }else{
                            Log.d("debug", "Model NULL");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("debug", "Response Fail");
                    }
                });

        return view;
    }
}