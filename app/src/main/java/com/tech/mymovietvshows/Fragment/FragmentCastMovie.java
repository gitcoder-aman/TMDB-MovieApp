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
import android.widget.Toast;

import com.tech.mymovietvshows.Adapter.GetTvShowsCastMovieAdapter;
import com.tech.mymovietvshows.Adapter.getCreditCastMovieAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.GetMovieCastCrewActivity;
import com.tech.mymovietvshows.Model.GetTvShowCastMovieModel;
import com.tech.mymovietvshows.Model.GetTvShowMovieModel;
import com.tech.mymovietvshows.Model.getCastMovieModel;
import com.tech.mymovietvshows.Model.getCreditMovieModel;
import com.tech.mymovietvshows.PersonDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCastMovie extends Fragment {
    
    private RecyclerView castMovieRecyclerView;
    private ProgressBar progressCastMovie;

    public FragmentCastMovie() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GetMovieCastCrewActivity activity = (GetMovieCastCrewActivity) getActivity();
        int person_id = activity.sendPersonId();
        String whichPart = activity.sendWhichPart();

        Log.d("personId1", String.valueOf(person_id));

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cast_movie, container, false);

        castMovieRecyclerView = view.findViewById(R.id.castMovieRecyclerView);
        progressCastMovie = view.findViewById(R.id.progressCastMovie);
        
        castMovieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        if(whichPart.equals("movie")) {

            //get credit movie detail for cast
            RetrofitInstance.getInstance().apiInterface.getMovieCreditsDetailById(person_id, api).enqueue(new Callback<getCreditMovieModel>() {
                @Override
                public void onResponse(@NonNull Call<getCreditMovieModel> call, @NonNull Response<getCreditMovieModel> response) {

                    getCreditMovieModel getCreditMovieModel = response.body();
                    if (getCreditMovieModel != null) {

                        progressCastMovie.setVisibility(View.GONE);

                        List<getCastMovieModel> getCastMovieModelList = getCreditMovieModel.getCast();

                        if (getCastMovieModelList != null && !getCastMovieModelList.isEmpty()) {

                            getCreditCastMovieAdapter adapter = new getCreditCastMovieAdapter(getContext(), getCastMovieModelList);
                            castMovieRecyclerView.setAdapter(adapter);

                            castMovieRecyclerView.setVisibility(View.VISIBLE);
                            //Create some animation view item loading
                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                            castMovieRecyclerView.setLayoutAnimation(controller);
                            castMovieRecyclerView.scheduleLayoutAnimation();

                        } else {
                            Toast.makeText(activity, "cast movie not available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("person_credit", "credit movie null");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<getCreditMovieModel> call, @NonNull Throwable t) {
                    Log.d("person_credit", "On Response fail");
                }
            });

        }else{
            //tvShows movie here
            RetrofitInstance.getInstance().apiInterface.getTvCreditsDetailById(person_id,api).enqueue(new Callback<GetTvShowMovieModel>() {
                @Override
                public void onResponse(@NonNull Call<GetTvShowMovieModel> call, @NonNull Response<GetTvShowMovieModel> response) {

                    GetTvShowMovieModel getTvShowMovieModel = response.body();
                    if(getTvShowMovieModel != null){

                        progressCastMovie.setVisibility(View.GONE);

                        List<GetTvShowCastMovieModel>getTvShowCastMovieModelList = getTvShowMovieModel.getCast();
                        if(getTvShowCastMovieModelList != null && !getTvShowCastMovieModelList.isEmpty()){

                            GetTvShowsCastMovieAdapter adapter = new GetTvShowsCastMovieAdapter(getContext(),getTvShowCastMovieModelList);
                            castMovieRecyclerView.setAdapter(adapter);

                            castMovieRecyclerView.setVisibility(View.VISIBLE);
                            //Create some animation view item loading
                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                            castMovieRecyclerView.setLayoutAnimation(controller);
                            castMovieRecyclerView.scheduleLayoutAnimation();


                        }else{
                            Toast.makeText(activity, "No available of cast movies", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GetTvShowMovieModel> call, @NonNull Throwable t) {
                    Log.d("tv", "On Response fail");
                }
            });

        }



        return view;
    }
}