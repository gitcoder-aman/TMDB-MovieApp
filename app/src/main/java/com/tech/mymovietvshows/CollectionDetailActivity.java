package com.tech.mymovietvshows;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
import static com.tech.mymovietvshows.MainActivity.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Adapter.CollectionIncludeRVAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.MovieCollectionModel;
import com.tech.mymovietvshows.Model.UpcomingNowMovieResultModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayoutCompat collectionBackdropPosterLayout;
    private AppCompatImageView collectionBackdropPoster;
    private AppCompatTextView collectionMovieName;
    private AppCompatTextView collectionOverview;
    private AppCompatImageView collectionPosterImage;

    private LinearLayoutCompat detailCollectionLayout;
    private ProgressBar progressCollection;

    private RecyclerView includeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);

        toolbar = findViewById(R.id.collectionToolbar);
        collectionBackdropPosterLayout = findViewById(R.id.collectionBackdropPosterLayout);
        collectionBackdropPoster = findViewById(R.id.collectionBackdropPoster);
        collectionMovieName = findViewById(R.id.collectionMovieName);
        collectionOverview = findViewById(R.id.collectionOverview);
        collectionPosterImage = findViewById(R.id.collectionPosterImage);
        progressCollection = findViewById(R.id.progressCollection);
        detailCollectionLayout = findViewById(R.id.detailCollectionLayout);

        includeRecyclerView = findViewById(R.id.include_recyclerView);

        includeRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {

            if (intent.getExtras().getString("collection_id") != null) {

                int collection_id = Integer.parseInt(intent.getExtras().getString("collection_id"));
                Log.d("collection_debug", String.valueOf(collection_id));

                //Call Collection details
                RetrofitInstance.getInstance().apiInterface.getMovieCollectionById(collection_id, api).enqueue(new Callback<MovieCollectionModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieCollectionModel> call, @NonNull Response<MovieCollectionModel> response) {

                        MovieCollectionModel movieCollectionModel = response.body();

                        if (movieCollectionModel != null) {

                            progressCollection.setVisibility(View.GONE);
                            detailCollectionLayout.setVisibility(View.VISIBLE);

                            toolbar.setTitle(movieCollectionModel.getName());
                            if (movieCollectionModel.getName() != null) {
                                collectionMovieName.setText(movieCollectionModel.getName());
                            }
                            if(movieCollectionModel.getOverview() != null){
                                collectionOverview.setText(movieCollectionModel.getOverview());
                            }
                            if(movieCollectionModel.getBackdrop_path() != null) {
                                collectionBackdropPosterLayout.setVisibility(View.VISIBLE);
                                Picasso.get()
                                        .load(movieCollectionModel.getBackdrop_path())
                                        .into(collectionBackdropPoster);
                            }else{
                                collectionBackdropPosterLayout.setVisibility(View.GONE);
                            }
                            Picasso.get()
                                    .load(movieCollectionModel.getPoster_path())
                                    .placeholder(R.drawable.image_loading)
                                    .into(collectionPosterImage);

                            List<UpcomingNowMovieResultModel>collectionPartList = movieCollectionModel.getParts();
                            if(collectionPartList != null && !collectionPartList.isEmpty()){

                                CollectionIncludeRVAdapter adapter = new CollectionIncludeRVAdapter(CollectionDetailActivity.this,collectionPartList);
                                includeRecyclerView.setAdapter(adapter);
                            }

                        } else {
                            Toast.makeText(CollectionDetailActivity.this, "Collection is NULL", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<MovieCollectionModel> call, @NonNull Throwable t) {
                        Log.d("collection_debug", "Fail Response");
                    }
                });
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //When execute toolbar back pressed
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.to_bottom);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_top,R.anim.to_bottom);
    }
}