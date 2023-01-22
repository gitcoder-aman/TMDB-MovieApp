package com.tech.mymovietvshows.Fragment;

import static com.tech.mymovietvshows.MainActivity.api;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.tech.mymovietvshows.Adapter.SearchMovieAdapter;
import com.tech.mymovietvshows.Adapter.TrendingPopularTopRatedRVAdapter;
import com.tech.mymovietvshows.Adapter.ViewPagerMovieTVAdapter;
import com.tech.mymovietvshows.Model.MovieResponse;
import com.tech.mymovietvshows.Model.MovieResponseResults;
import com.tech.mymovietvshows.R;

import java.util.List;

import io.paperdb.Paper;


public class SearchFragment extends Fragment {


    public static String PASS_QUERY = null;
    private LinearLayoutCompat toolbarLayout;
    private LinearLayoutCompat searchEditLayout;
    private LinearLayoutCompat tabViewPagerLayout;
    private LinearLayoutCompat recentLayout;

    private RecyclerView recentRecycler;
    private AppCompatEditText queryEditText;
    private ViewPager viewPagerOfSearch;

    AppCompatImageView searchBtn;
    TabLayout tabLayoutSearch;


    public SearchFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        toolbarLayout = view.findViewById(R.id.toolbarLayout);
        searchEditLayout = view.findViewById(R.id.searchEditLayout);
        searchBtn = view.findViewById(R.id.searchBtn);
        queryEditText = view.findViewById(R.id.queryEditText);
        viewPagerOfSearch = view.findViewById(R.id.viewPagerOfSearch);
        tabLayoutSearch = view.findViewById(R.id.search_tabLayout);
        tabViewPagerLayout = view.findViewById(R.id.tabViewPagerLayout);
        recentLayout = view.findViewById(R.id.recentLayout);
        recentRecycler = view.findViewById(R.id.recentRecycler);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Paper.init(getContext());

        recentRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        if(getArguments() != null){  //for search from homeFragment

            PASS_QUERY = getArguments().getString("finalQuery");

            viewPagerOfSearch.setAdapter(new ViewPagerMovieTVAdapter(getChildFragmentManager(), new MoviesSearchFragment(), new TvShowSearchFragment()));
            tabLayoutSearch.setupWithViewPager(viewPagerOfSearch);
            tabViewPagerLayout.setVisibility(View.VISIBLE);
        }
        recentLayout.setVisibility(View.VISIBLE);

        //retrieve the results from paper db and start
        if (Paper.book().read("cache") != null) {
            String results = Paper.book().read("cache");

            if (Paper.book().read("source") != null) {
                String source = Paper.book().read("source");
                if (source.equals("movie")) {
                    //convert the string cache to model movie response class using gson
                    MovieResponse movieResponse = new Gson().fromJson(results, MovieResponse.class);
                    if (movieResponse != null) {

                        List<MovieResponseResults> movieResponseResultsList = movieResponse.getResults();

                        SearchMovieAdapter adapter = new SearchMovieAdapter(getContext(), movieResponseResultsList);
                        recentRecycler.setAdapter(adapter);

                        //Create some animation view item loading
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_slide_right);
                        recentRecycler.setLayoutAnimation(controller);
                        recentRecycler.scheduleLayoutAnimation();

                        //now store the results in paper database to access offline

                        Paper.book().write("cache", new Gson().toJson(movieResponse));

                        //store also category to set the spinner at app start
                        Paper.book().write("source", "movie");
                    }

                }
            }
        }


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarLayout.setVisibility(View.GONE);
                searchEditLayout.setVisibility(View.VISIBLE);

            }
        });

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

                            PASS_QUERY = finalQuery;

                            searchEditLayout.setVisibility(View.GONE);  //off search editText
                            toolbarLayout.setVisibility(View.VISIBLE);
                            tabViewPagerLayout.setVisibility(View.VISIBLE);

                            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                            viewPagerOfSearch.setAdapter(new ViewPagerMovieTVAdapter(getChildFragmentManager(), new MoviesSearchFragment(), new TvShowSearchFragment()));
                            tabLayoutSearch.setupWithViewPager(viewPagerOfSearch);

                            recentLayout.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getContext(), "Please enter any movie name", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        //disable editText through drawable end touch listener
        queryEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 0;

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (queryEditText.getRight() - queryEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        toolbarLayout.setVisibility(View.VISIBLE);
                        searchEditLayout.setVisibility(View.GONE);

                        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public String callQueryFromFragment(){
        if(queryEditText.getText().toString() != null) {
            Toast.makeText(getContext(), queryEditText.getText().toString(), Toast.LENGTH_SHORT).show();
            return queryEditText.getText().toString();
        }else{
            Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}