package com.tech.mymovietvshows.Fragment;

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

import com.tech.mymovietvshows.Adapter.CreditCastAdapter;
import com.tech.mymovietvshows.Adapter.MovieCastRVAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.MovieCreditsCastModel;
import com.tech.mymovietvshows.Model.MovieCreditsModel;
import com.tech.mymovietvshows.MovieCreditActivity;
import com.tech.mymovietvshows.MovieDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentCastDetails extends Fragment {

    private RecyclerView castRecyclerView;

    public FragmentCastDetails() {
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

        MovieCreditActivity activity = (MovieCreditActivity) getActivity();
        int getMovieId = activity.sendMovieId();
        Log.d("frag", String.valueOf(getMovieId));

        View view = inflater.inflate(R.layout.fragment_cast_details, container, false);

        castRecyclerView = view.findViewById(R.id.castRecyclerView);

        castRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        //Cast and crew of movie set
        RetrofitInstance.getInstance().apiInterface.getMovieCreditsById(getMovieId, api).enqueue(new Callback<MovieCreditsModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieCreditsModel> call, @NonNull Response<MovieCreditsModel> response) {

                MovieCreditsModel movieCreditModel = response.body();

                if (movieCreditModel != null) {
                    List<MovieCreditsCastModel> movieCreditsCastModelList = movieCreditModel.getCast();

                    if (movieCreditsCastModelList != null && !movieCreditsCastModelList.isEmpty()) {
                        CreditCastAdapter adapter = new CreditCastAdapter(getContext(), movieCreditsCastModelList);
                        castRecyclerView.setAdapter(adapter);

                        //Create some animation view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                        castRecyclerView.setLayoutAnimation(controller);
                        castRecyclerView.scheduleLayoutAnimation();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieCreditsModel> call, @NonNull Throwable t) {
                Log.d("movie_detail", "On Response Fail");
            }
        });

        return view;
    }
}