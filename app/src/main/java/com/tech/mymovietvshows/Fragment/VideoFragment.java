package com.tech.mymovietvshows.Fragment;

import static com.tech.mymovietvshows.MainActivity.api;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tech.mymovietvshows.Adapter.VideoFragment.MovieFragTrendingPopularRatedAdapter;
import com.tech.mymovietvshows.Adapter.VideoFragment.MovieFragUpcomingNowAdapter;
import com.tech.mymovietvshows.Adapter.VideoFragment.TvFragTrendingAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieModel;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieResultModel;
import com.tech.mymovietvshows.Model.UpcomingNowMovieModel;
import com.tech.mymovietvshows.Model.UpcomingNowMovieResultModel;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoFragment extends Fragment {

    private RecyclerView recyclerViewVideo;
    private RecyclerView recyclerMovieVideo;
    private RecyclerView recyclerTvVideo;
    private LinearLayoutCompat trendingMovieLayout;
    private LinearLayoutCompat movieLayout;
    private LinearLayoutCompat tvLayout;
    private AppCompatButton movieBtn;
    private AppCompatButton tvBtn;
    private AppCompatButton popularBtn;
    private AppCompatButton nowPlayingBtn;
    private AppCompatButton topRatedBtn;
    private AppCompatButton upcomingBtn;

    private AppCompatButton airingBtn;
    private AppCompatButton onTvBtn;
    private AppCompatButton popularTvBtn;
    private AppCompatButton topRatedTvBtn;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private LinearLayoutCompat videoLayout;

    private static int pageVideo = 1;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_video, container, false);

        videoLayout = view.findViewById(R.id.videoLayout);
        progressBar = view.findViewById(R.id.progressVideo);

        recyclerViewVideo = view.findViewById(R.id.recyclerViewVideo);
        recyclerMovieVideo = view.findViewById(R.id.recyclerMovieVideo);
        recyclerTvVideo = view.findViewById(R.id.recyclerTvVideo);

        trendingMovieLayout = view.findViewById(R.id.trendingMovieLayout);
        movieLayout = view.findViewById(R.id.movieLayout);
        tvLayout = view.findViewById(R.id.tvLayout);
        movieBtn = view.findViewById(R.id.movieBtn);
        tvBtn = view.findViewById(R.id.tvBtn);

        popularBtn = view.findViewById(R.id.popularBtn);
        nowPlayingBtn = view.findViewById(R.id.nowPlayingBtn);
        topRatedBtn = view.findViewById(R.id.topRatedBtn);
        upcomingBtn = view.findViewById(R.id.upcomingBtn);

        airingBtn = view.findViewById(R.id.airingBtn);
        onTvBtn = view.findViewById(R.id.onTvBtn);
        popularTvBtn = view.findViewById(R.id.popularTvBtn);
        topRatedTvBtn = view.findViewById(R.id.topRatedTvBtn);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        recyclerViewVideo.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerMovieVideo.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerTvVideo.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        callFunctionAfterRefresh();
        movieBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
        popularBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
        airingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pageVideo++;
                callFunctionAfterRefresh();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        movieBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                movieBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                tvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                RetrofitInstance.getInstance().apiInterface.getTrendingMovieDay(api,pageVideo).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel trendingMovieVideoResponse = response.body();
                        if(trendingMovieVideoResponse != null){
                            List<TrendingPopularTopRatedMovieResultModel> trendingMovieVideoResultList = trendingMovieVideoResponse.getResults();

                            if(trendingMovieVideoResultList != null && !trendingMovieVideoResultList.isEmpty()){

                                MovieFragTrendingPopularRatedAdapter adapter = new MovieFragTrendingPopularRatedAdapter(getContext(),trendingMovieVideoResultList);
                                recyclerViewVideo.setAdapter(adapter);
                            }else{
                                trendingMovieLayout.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            trendingMovieLayout.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("VideoFrag","On Response fail");
                    }
                });
            }
        });
        tvBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                tvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                movieBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                RetrofitInstance.getInstance().apiInterface.getTrendingTvDay(api,pageVideo).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel trendingMovieVideoResponse = response.body();
                        if(trendingMovieVideoResponse != null){
                            List<TrendingPopularTopRatedMovieResultModel> trendingMovieVideoResultList = trendingMovieVideoResponse.getResults();

                            if(trendingMovieVideoResultList != null && !trendingMovieVideoResultList.isEmpty()){

                                TvFragTrendingAdapter adapter = new TvFragTrendingAdapter(getContext(),trendingMovieVideoResultList);
                                recyclerViewVideo.setAdapter(adapter);
                            }else{
                                trendingMovieLayout.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            trendingMovieLayout.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("VideoFrag","On Response fail");
                    }
                });

            }
        });
        popularBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popularBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                nowPlayingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                topRatedBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                upcomingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                RetrofitInstance.getInstance().apiInterface.getPopularMovie(api,pageVideo).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel trendingMovieVideoResponse = response.body();
                        if(trendingMovieVideoResponse != null){
                            List<TrendingPopularTopRatedMovieResultModel> trendingMovieVideoResultList = trendingMovieVideoResponse.getResults();

                            if(trendingMovieVideoResultList != null && !trendingMovieVideoResultList.isEmpty()){

                                MovieFragTrendingPopularRatedAdapter adapter = new MovieFragTrendingPopularRatedAdapter(getContext(),trendingMovieVideoResultList);
                                recyclerMovieVideo.setAdapter(adapter);

                                movieLayout.setVisibility(View.VISIBLE);
                            }else{
                                movieLayout.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            movieLayout.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("VideoFrag","On Response fail");
                    }
                });
            }
        });
        nowPlayingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPlayingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                topRatedBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                popularBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                upcomingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));

                RetrofitInstance.getInstance().apiInterface.getNowPlayingMovie(api,pageVideo).enqueue(new Callback<UpcomingNowMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<UpcomingNowMovieModel> call, @NonNull Response<UpcomingNowMovieModel> response) {

                        UpcomingNowMovieModel trendingMovieVideoResponse = response.body();
                        if(trendingMovieVideoResponse != null){
                            List<UpcomingNowMovieResultModel> upcomingNowMovieResultModelList = trendingMovieVideoResponse.getResults();

                            if(upcomingNowMovieResultModelList != null && !upcomingNowMovieResultModelList.isEmpty()){

                                MovieFragUpcomingNowAdapter adapter = new MovieFragUpcomingNowAdapter(getContext(),upcomingNowMovieResultModelList);
                                recyclerMovieVideo.setAdapter(adapter);

                                movieLayout.setVisibility(View.VISIBLE);
                            }else{
                                movieLayout.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            movieLayout.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UpcomingNowMovieModel> call, @NonNull Throwable t) {
                        Log.d("VideoFrag","On Response fail");
                    }
                });
            }
        });
        topRatedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topRatedBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                nowPlayingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                popularBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                upcomingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                RetrofitInstance.getInstance().apiInterface.getTopRatedMovie(api,pageVideo).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel trendingMovieVideoResponse = response.body();
                        if(trendingMovieVideoResponse != null){
                            List<TrendingPopularTopRatedMovieResultModel> trendingMovieVideoResultList = trendingMovieVideoResponse.getResults();

                            if(trendingMovieVideoResultList != null && !trendingMovieVideoResultList.isEmpty()){

                                MovieFragTrendingPopularRatedAdapter adapter = new MovieFragTrendingPopularRatedAdapter(getContext(),trendingMovieVideoResultList);
                                recyclerMovieVideo.setAdapter(adapter);

                                movieLayout.setVisibility(View.VISIBLE);
                            }else{
                                movieLayout.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            movieLayout.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("VideoFrag","On Response fail");
                    }
                });
            }
        });

        upcomingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upcomingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                nowPlayingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                popularBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                topRatedBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                RetrofitInstance.getInstance().apiInterface.getUpcomingMovie(api,pageVideo).enqueue(new Callback<UpcomingNowMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<UpcomingNowMovieModel> call, @NonNull Response<UpcomingNowMovieModel> response) {

                        UpcomingNowMovieModel trendingMovieVideoResponse = response.body();
                        if(trendingMovieVideoResponse != null){
                            List<UpcomingNowMovieResultModel> upcomingNowMovieResultModelList = trendingMovieVideoResponse.getResults();

                            if(upcomingNowMovieResultModelList != null && !upcomingNowMovieResultModelList.isEmpty()){

                                MovieFragUpcomingNowAdapter adapter = new MovieFragUpcomingNowAdapter(getContext(),upcomingNowMovieResultModelList);
                                recyclerMovieVideo.setAdapter(adapter);

                                movieLayout.setVisibility(View.VISIBLE);
                            }else{
                                movieLayout.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            movieLayout.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UpcomingNowMovieModel> call, @NonNull Throwable t) {
                        Log.d("VideoFrag","On Response fail");
                    }
                });
            }
        });
        airingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                airingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                onTvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                popularTvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                topRatedTvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));

                RetrofitInstance.getInstance().apiInterface.getAiringTodayTv(api,pageVideo).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel trendingMovieVideoResponse = response.body();
                        if(trendingMovieVideoResponse != null){
                            List<TrendingPopularTopRatedMovieResultModel> trendingMovieVideoResultList = trendingMovieVideoResponse.getResults();

                            if(trendingMovieVideoResultList != null && !trendingMovieVideoResultList.isEmpty()){

                                TvFragTrendingAdapter adapter = new TvFragTrendingAdapter(getContext(),trendingMovieVideoResultList);
                                recyclerTvVideo.setAdapter(adapter);

                                tvLayout.setVisibility(View.VISIBLE);
                            }else{
                                tvLayout.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            movieLayout.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("VideoFrag","On Response fail");
                    }
                });
            }
        });
        onTvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                airingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                popularTvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                topRatedTvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                RetrofitInstance.getInstance().apiInterface.getOnTheAirTv(api,pageVideo).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel trendingMovieVideoResponse = response.body();
                        if(trendingMovieVideoResponse != null){
                            List<TrendingPopularTopRatedMovieResultModel> trendingMovieVideoResultList = trendingMovieVideoResponse.getResults();

                            if(trendingMovieVideoResultList != null && !trendingMovieVideoResultList.isEmpty()){

                                TvFragTrendingAdapter adapter = new TvFragTrendingAdapter(getContext(),trendingMovieVideoResultList);
                                recyclerTvVideo.setAdapter(adapter);

                                tvLayout.setVisibility(View.VISIBLE);
                            }else{
                                tvLayout.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            movieLayout.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("VideoFrag","On Response fail");
                    }
                });
            }
        });

        popularTvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popularTvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                airingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                onTvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                topRatedTvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                RetrofitInstance.getInstance().apiInterface.getPopularTv(api,pageVideo).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel trendingMovieVideoResponse = response.body();
                        if(trendingMovieVideoResponse != null){
                            List<TrendingPopularTopRatedMovieResultModel> trendingMovieVideoResultList = trendingMovieVideoResponse.getResults();

                            if(trendingMovieVideoResultList != null && !trendingMovieVideoResultList.isEmpty()){

                                TvFragTrendingAdapter adapter = new TvFragTrendingAdapter(getContext(),trendingMovieVideoResultList);
                                recyclerTvVideo.setAdapter(adapter);

                                tvLayout.setVisibility(View.VISIBLE);
                            }else{
                                tvLayout.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            movieLayout.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("VideoFrag","On Response fail");
                    }
                });
            }
        });

        topRatedTvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topRatedTvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                airingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                onTvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                popularTvBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                RetrofitInstance.getInstance().apiInterface.getTopRatedTv(api,pageVideo).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel trendingMovieVideoResponse = response.body();
                        if(trendingMovieVideoResponse != null){
                            List<TrendingPopularTopRatedMovieResultModel> trendingMovieVideoResultList = trendingMovieVideoResponse.getResults();

                            if(trendingMovieVideoResultList != null && !trendingMovieVideoResultList.isEmpty()){

                                TvFragTrendingAdapter adapter = new TvFragTrendingAdapter(getContext(),trendingMovieVideoResultList);
                                recyclerTvVideo.setAdapter(adapter);

                                tvLayout.setVisibility(View.VISIBLE);
                            }else{
                                tvLayout.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            movieLayout.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("VideoFrag","On Response fail");
                    }
                });
            }
        });

        return view;
    }

    private void callFunctionAfterRefresh() {

        //trending videos
        RetrofitInstance.getInstance().apiInterface.getTrendingMovieDay(api,pageVideo).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
            @Override
            public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                TrendingPopularTopRatedMovieModel trendingMovieVideoResponse = response.body();
                if(trendingMovieVideoResponse != null){
                    List<TrendingPopularTopRatedMovieResultModel> trendingMovieVideoResultList = trendingMovieVideoResponse.getResults();

                    if(trendingMovieVideoResultList != null && !trendingMovieVideoResultList.isEmpty()){

                        progressBar.setVisibility(View.GONE);
                        videoLayout.setVisibility(View.VISIBLE);

                        MovieFragTrendingPopularRatedAdapter adapter = new MovieFragTrendingPopularRatedAdapter(getContext(),trendingMovieVideoResultList);
                        recyclerViewVideo.setAdapter(adapter);
                        trendingMovieLayout.setVisibility(View.VISIBLE);
                    }else{
                        trendingMovieLayout.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    trendingMovieLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                Log.d("VideoFrag","On Response fail");
            }
        });

        //popular videos
        RetrofitInstance.getInstance().apiInterface.getPopularMovie(api,pageVideo).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
            @Override
            public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                TrendingPopularTopRatedMovieModel trendingMovieVideoResponse = response.body();
                if(trendingMovieVideoResponse != null){
                    List<TrendingPopularTopRatedMovieResultModel> trendingMovieVideoResultList = trendingMovieVideoResponse.getResults();

                    if(trendingMovieVideoResultList != null && !trendingMovieVideoResultList.isEmpty()){

                        MovieFragTrendingPopularRatedAdapter adapter = new MovieFragTrendingPopularRatedAdapter(getContext(),trendingMovieVideoResultList);
                        recyclerMovieVideo.setAdapter(adapter);

                        movieLayout.setVisibility(View.VISIBLE);
                    }else{
                        movieLayout.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    movieLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                Log.d("VideoFrag","On Response fail");
            }
        });

        //airing today video
        RetrofitInstance.getInstance().apiInterface.getAiringTodayTv(api,pageVideo).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
            @Override
            public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                TrendingPopularTopRatedMovieModel trendingMovieVideoResponse = response.body();
                if(trendingMovieVideoResponse != null){
                    List<TrendingPopularTopRatedMovieResultModel> trendingMovieVideoResultList = trendingMovieVideoResponse.getResults();

                    if(trendingMovieVideoResultList != null && !trendingMovieVideoResultList.isEmpty()){

                        MovieFragTrendingPopularRatedAdapter adapter = new MovieFragTrendingPopularRatedAdapter(getContext(),trendingMovieVideoResultList);
                        recyclerTvVideo.setAdapter(adapter);

                        tvLayout.setVisibility(View.VISIBLE);
                    }else{
                        tvLayout.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Movie not available", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    movieLayout.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Movie is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                Log.d("VideoFrag","On Response fail");
            }
        });

    }
}