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
import android.widget.ProgressBar;

import com.tech.mymovietvshows.Adapter.CreditCrewAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.MovieCreditsCrewModel;
import com.tech.mymovietvshows.Model.MovieCreditsModel;
import com.tech.mymovietvshows.MovieCreditActivity;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCrewDetails extends Fragment {

    private RecyclerView crewRecyclerView;
    private ProgressBar progressCrewDetail;

    public FragmentCrewDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MovieCreditActivity activity = (MovieCreditActivity) getActivity();
        int getMovieId = activity.sendMovieId();
        String creditType = activity.sendCreditType();

        Log.d("frag", String.valueOf(getMovieId));

        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_crew_details, container, false);

        crewRecyclerView = view.findViewById(R.id.crewRecyclerView);
        progressCrewDetail = view.findViewById(R.id.progressCrewDetail);

        crewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        if(creditType.equals("movie")){
            RetrofitInstance.getInstance().apiInterface.getMovieCreditsById(getMovieId,api).enqueue(new Callback<MovieCreditsModel>() {
                @Override
                public void onResponse(@NonNull Call<MovieCreditsModel> call, @NonNull Response<MovieCreditsModel> response) {

                    MovieCreditsModel movieCreditModel = response.body();

                    progressCrewDetail.setVisibility(View.GONE);

                    if (movieCreditModel != null) {
                        List<MovieCreditsCrewModel> movieCreditsCrewModelList = movieCreditModel.getCrew();

                        if (movieCreditsCrewModelList != null && !movieCreditsCrewModelList.isEmpty()) {
                            CreditCrewAdapter adapter = new CreditCrewAdapter(getContext(), movieCreditsCrewModelList);
                            crewRecyclerView.setAdapter(adapter);

                            crewRecyclerView.setVisibility(View.VISIBLE);

                            //Create some animation view item loading
                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                            crewRecyclerView.setLayoutAnimation(controller);
                            crewRecyclerView.scheduleLayoutAnimation();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieCreditsModel> call, @NonNull Throwable t) {
                    Log.d("crew", "On Response Fail");
                }
            });

        }else if(creditType.equals("tv")){
            RetrofitInstance.getInstance().apiInterface.getTvShowsCreditsById(getMovieId,api).enqueue(new Callback<MovieCreditsModel>() {
                @Override
                public void onResponse(@NonNull Call<MovieCreditsModel> call, @NonNull Response<MovieCreditsModel> response) {

                    MovieCreditsModel movieCreditModel = response.body();

                    if (movieCreditModel != null) {

                        progressCrewDetail.setVisibility(View.GONE);
                        List<MovieCreditsCrewModel> movieCreditsCrewModelList = movieCreditModel.getCrew();

                        if (movieCreditsCrewModelList != null && !movieCreditsCrewModelList.isEmpty()) {
                            CreditCrewAdapter adapter = new CreditCrewAdapter(getContext(), movieCreditsCrewModelList);
                            crewRecyclerView.setAdapter(adapter);

                            crewRecyclerView.setVisibility(View.VISIBLE);
                            //Create some animation view item loading
                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                            crewRecyclerView.setLayoutAnimation(controller);
                            crewRecyclerView.scheduleLayoutAnimation();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieCreditsModel> call, @NonNull Throwable t) {
                    Log.d("crew", "On Response Fail");
                }
            });

        }
        return view;
    }
}