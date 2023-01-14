package com.tech.mymovietvshows.Fragment;

import static com.tech.mymovietvshows.MainActivity.bottomNavigationView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.tech.mymovietvshows.Adapter.ViewPagerAdapter;
import com.tech.mymovietvshows.R;


public class HomeFragment extends Fragment {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private EditText queryEditText;

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
        View view = inflater.inflate(R.layout.fragment_home, container,false);

        //disable the keyword on start

        drawerLayout = view.findViewById(R.id.drawerLayout);
        navigationView = view.findViewById(R.id.navigation_view);
        toolbar = view.findViewById(R.id.toolbarId);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        queryEditText = view.findViewById(R.id.queryEditText);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        queryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action_id, KeyEvent keyEvent) {
                if(action_id == EditorInfo.IME_ACTION_SEARCH){

                    if (!queryEditText.getText().toString().equals("")) {

                        String query = queryEditText.getText().toString();

                        if (query.equals(" ")) {
                            Toast.makeText(getContext(), "Please enter any text..", Toast.LENGTH_SHORT).show();
                        } else {
                            queryEditText.setText("");

                            //get the category to search the query . movie or Person
                            String finalQuery = query.replace(" ", "+");   //all space character convert into concat the string.
                            Log.d("debug", finalQuery);

                        }
                        Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
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