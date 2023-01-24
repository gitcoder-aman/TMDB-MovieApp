package com.tech.mymovietvshows.Fragment.Section;

import static com.tech.mymovietvshows.Fragment.HomeFragment.pageHome;
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

import com.tech.mymovietvshows.Adapter.UpcomingNowRVAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.UpcomingNowMovieModel;
import com.tech.mymovietvshows.Model.UpcomingNowMovieResultModel;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingFragment extends Fragment {

    private RecyclerView upcomingRecyclerView;

    public UpcomingFragment() {
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
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        upcomingRecyclerView = view.findViewById(R.id.upcoming_recyclerView);

        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        RetrofitInstance.getInstance().apiInterface.getUpcomingMovie(api,pageHome).enqueue(new Callback<UpcomingNowMovieModel>() {
            @Override
            public void onResponse(@NonNull Call<UpcomingNowMovieModel> call, @NonNull Response<UpcomingNowMovieModel> response) {

                UpcomingNowMovieModel upcomingMovieModel = response.body();

                if(upcomingMovieModel != null){

                    List<UpcomingNowMovieResultModel> upcomingMovieResultModelList = upcomingMovieModel.getResults();

                    if(upcomingMovieResultModelList.size() > 0 ){

                        UpcomingNowRVAdapter adapter = new UpcomingNowRVAdapter(getContext(),upcomingMovieResultModelList);
                        upcomingRecyclerView.setAdapter(adapter);

                        //Create some animation view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                        upcomingRecyclerView.setLayoutAnimation(controller);
                        upcomingRecyclerView.scheduleLayoutAnimation();
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