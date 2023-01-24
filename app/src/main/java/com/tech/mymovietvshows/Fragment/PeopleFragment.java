package com.tech.mymovietvshows.Fragment;

import static com.tech.mymovietvshows.MainActivity.api;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tech.mymovietvshows.Adapter.PopularPeopleFragAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.PopularPersonModel;
import com.tech.mymovietvshows.Model.PopularPersonModelResult;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleFragment extends Fragment {

    private RecyclerView peopleRecycler;
    private int pagePeople = 1;
    private List<PopularPersonModelResult> list;

    private PopularPeopleFragAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    public PeopleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_people, container, false);
        peopleRecycler = view.findViewById(R.id.peopleRecycler);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        progressBar = view.findViewById(R.id.progressPeople);

        peopleRecycler.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        callFunctionAfterRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pagePeople++;
                callFunctionAfterRefresh();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        return view;
    }

    private void callFunctionAfterRefresh() {
        RetrofitInstance.getInstance().apiInterface.getPopularPeople(api, pagePeople).enqueue(new Callback<PopularPersonModel>() {
            @Override
            public void onResponse(@NonNull Call<PopularPersonModel> call, @NonNull Response<PopularPersonModel> response) {

                PopularPersonModel popularPersonModelResponse = response.body();

                if (popularPersonModelResponse != null) {

                    list = popularPersonModelResponse.getResults();

                    if (list != null && !list.isEmpty()) {

                        progressBar.setVisibility(View.GONE);
                        adapter = new PopularPeopleFragAdapter(getContext(), list);
                        peopleRecycler.setAdapter(adapter);

                        //Create some animation view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                        peopleRecycler.setLayoutAnimation(controller);
                        peopleRecycler.scheduleLayoutAnimation();

                    } else {
                        Toast.makeText(getContext(), "List is empty", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "person detail null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PopularPersonModel> call, @NonNull Throwable t) {
                Log.d("peopleDebug", "On Response fail");
            }
        });

    }

}