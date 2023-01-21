package com.tech.mymovietvshows.Fragment;

import static com.tech.mymovietvshows.MainActivity.api;
import static com.tech.mymovietvshows.MainActivity.bottomNavigationView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.tech.mymovietvshows.Adapter.PopularRatedTVShowRVAdapter;
import com.tech.mymovietvshows.Adapter.ViewPagerAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieModel;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieResultModel;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private EditText queryEditText;

    //on TV
    private AppCompatButton popularBtn;
    private AppCompatButton airingBtn;
    private AppCompatButton topRatedBtn;
    private RecyclerView tvRecyclerView;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //disable the keyword on start

        drawerLayout = view.findViewById(R.id.drawerLayout);
        navigationView = view.findViewById(R.id.navigation_view);
        toolbar = view.findViewById(R.id.toolbarId);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        queryEditText = view.findViewById(R.id.queryEditText);

        //on TV Shows
        popularBtn = view.findViewById(R.id.popularBtn);
        airingBtn = view.findViewById(R.id.airingBtn);
        topRatedBtn = view.findViewById(R.id.topRatedBtn);
        tvRecyclerView = view.findViewById(R.id.tvRecyclerView);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        queryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action_id, KeyEvent keyEvent) {
                if (action_id == EditorInfo.IME_ACTION_SEARCH) {

                    if (!queryEditText.getText().toString().equals("")) {

                        String query = queryEditText.getText().toString();

                        if (query.equals(" ")) {
                            Toast.makeText(getContext(), "Please enter any text..", Toast.LENGTH_SHORT).show();
                        } else {
                            queryEditText.setText("");

                            //get the category to search the query . movie or Person
                            String finalQuery = query.replace(" ", "+");   //all space character convert into concat the string.
                            Log.d("debug", finalQuery);

                            SearchFragment fragment = new SearchFragment ();
                            Bundle args = new Bundle();
                            args.putString("finalQuery",finalQuery);
                            fragment.setArguments(args);

                            bottomNavigationView.setSelectedItemId(R.id.nav_search);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frameContainer, fragment ); // give your fragment container id in first parameter
                            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                            transaction.commit();

                        }
                        Log.d("HomeQuery",query);
                    }
                    return true;
                }
                return false;
            }
        });


        //for on TV Shows
        tvRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        popularBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
        RetrofitInstance.getInstance().apiInterface.getPopularTv(api).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
            @Override
            public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                TrendingPopularTopRatedMovieModel TPTVModel = response.body();

                if (TPTVModel != null) {

                    List<TrendingPopularTopRatedMovieResultModel> TPMovieResultModelList = TPTVModel.getResults();

                    if (TPMovieResultModelList != null && !TPMovieResultModelList.isEmpty()) {

                        PopularRatedTVShowRVAdapter TPRVAdapter = new PopularRatedTVShowRVAdapter(getContext(),TPMovieResultModelList);
                        tvRecyclerView.setAdapter(TPRVAdapter);

                        //Create some animation view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                        tvRecyclerView.setLayoutAnimation(controller);
                        tvRecyclerView.scheduleLayoutAnimation();

                    } else {
                        Log.d("debug", "Null List");
                    }
                }else{
                    Log.d("debug", "Model NULL");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                Log.d("homeTv","On Response fail");
            }
        });

        popularBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popularBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                airingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                topRatedBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));

                RetrofitInstance.getInstance().apiInterface.getPopularTv(api).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel TPTVModel = response.body();

                        if (TPTVModel != null) {

                            List<TrendingPopularTopRatedMovieResultModel> TPMovieResultModelList = TPTVModel.getResults();

                            if (TPMovieResultModelList != null && !TPMovieResultModelList.isEmpty()) {

                                PopularRatedTVShowRVAdapter TPRVAdapter = new PopularRatedTVShowRVAdapter(getContext(),TPMovieResultModelList);

                                tvRecyclerView.setAdapter(TPRVAdapter);

                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                                tvRecyclerView.setLayoutAnimation(controller);
                                tvRecyclerView.scheduleLayoutAnimation();

                            } else {
                                Log.d("debug", "Null List");
                            }
                        }else{
                            Log.d("debug", "Model NULL");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("homeTv","On Response fail");
                    }
                });

            }
        });
        airingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                airingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                popularBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                topRatedBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));

                RetrofitInstance.getInstance().apiInterface.getAiringTodayTv(api).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel TPTVModel = response.body();

                        if (TPTVModel != null) {

                            List<TrendingPopularTopRatedMovieResultModel> TPMovieResultModelList = TPTVModel.getResults();

                            if (TPMovieResultModelList != null && !TPMovieResultModelList.isEmpty()) {

                                PopularRatedTVShowRVAdapter TPRVAdapter = new PopularRatedTVShowRVAdapter(getContext(),TPMovieResultModelList);
                                tvRecyclerView.setAdapter(TPRVAdapter);

                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                                tvRecyclerView.setLayoutAnimation(controller);
                                tvRecyclerView.scheduleLayoutAnimation();

                            } else {
                                Log.d("debug", "Null List");
                            }
                        }else{
                            Log.d("debug", "Model NULL");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("homeTv","On Response fail");
                    }
                });

            }
        });
        topRatedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topRatedBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_background, getContext().getTheme()));
                airingBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));
                popularBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_background,getContext().getTheme()));

                RetrofitInstance.getInstance().apiInterface.getTopRatedTv(api).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel TPTVModel = response.body();

                        if (TPTVModel != null) {

                            List<TrendingPopularTopRatedMovieResultModel> TPMovieResultModelList = TPTVModel.getResults();

                            if (TPMovieResultModelList != null && !TPMovieResultModelList.isEmpty()) {

                                PopularRatedTVShowRVAdapter TPRVAdapter = new PopularRatedTVShowRVAdapter(getContext(),TPMovieResultModelList);
                                tvRecyclerView.setAdapter(TPRVAdapter);

                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                                tvRecyclerView.setLayoutAnimation(controller);
                                tvRecyclerView.scheduleLayoutAnimation();

                            } else {
                                Log.d("debug", "Null List");
                            }
                        }else{
                            Log.d("debug", "Model NULL");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("homeTv","On Response fail");
                    }
                });

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
        return view;
    }
}