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
import android.widget.AbsListView;

import com.tech.mymovietvshows.Adapter.UpcomingNowRVAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.UpcomingNowMovieModel;
import com.tech.mymovietvshows.Model.UpcomingNowMovieResultModel;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingFragment extends Fragment {

    private RecyclerView nowPlayingRecyclerView;

    public NowPlayingFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_now_playing, container, false);


        nowPlayingRecyclerView = view.findViewById(R.id.nowPlaying_recyclerView);

        nowPlayingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        RetrofitInstance.getInstance().apiInterface.getNowPlayingMovie(api).enqueue(new Callback<UpcomingNowMovieModel>() {
            @Override
            public void onResponse(@NonNull Call<UpcomingNowMovieModel> call, @NonNull Response<UpcomingNowMovieModel> response) {

                UpcomingNowMovieModel upcomingMovieModel = response.body();

                if(upcomingMovieModel != null){

                    List<UpcomingNowMovieResultModel> upcomingMovieResultModelList = upcomingMovieModel.getResults();

                    if(upcomingMovieResultModelList.size() > 0 ){

                        UpcomingNowRVAdapter adapter = new UpcomingNowRVAdapter(getContext(),upcomingMovieResultModelList);
                        nowPlayingRecyclerView.setAdapter(adapter);

                        //Create some animation view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                        nowPlayingRecyclerView.setLayoutAnimation(controller);
                        nowPlayingRecyclerView.scheduleLayoutAnimation();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpcomingNowMovieModel> call, @NonNull Throwable t) {
                Log.d("debug", "On Response Fail");
            }
        });
        return view;
    }
}