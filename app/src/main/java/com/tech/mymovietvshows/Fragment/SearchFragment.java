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
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.tech.mymovietvshows.Adapter.ViewPagerMovieTVAdapter;
import com.tech.mymovietvshows.R;


public class SearchFragment extends Fragment {


    public static String PASS_QUERY = null;
    private LinearLayoutCompat toolbarLayout;
    private LinearLayoutCompat searchEditLayout;
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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){

            PASS_QUERY = getArguments().getString("finalQuery");

            viewPagerOfSearch.setAdapter(new ViewPagerMovieTVAdapter(getChildFragmentManager(), new MoviesSearchFragment(), new TvShowSearchFragment()));
            tabLayoutSearch.setupWithViewPager(viewPagerOfSearch);
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
                            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                            viewPagerOfSearch.setAdapter(new ViewPagerMovieTVAdapter(getChildFragmentManager(), new MoviesSearchFragment(), new TvShowSearchFragment()));
                            tabLayoutSearch.setupWithViewPager(viewPagerOfSearch);
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