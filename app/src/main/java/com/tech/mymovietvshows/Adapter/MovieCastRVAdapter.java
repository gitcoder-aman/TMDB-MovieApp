package com.tech.mymovietvshows.Adapter;

import static com.tech.mymovietvshows.MainActivity.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.MovieCreditsCastModel;
import com.tech.mymovietvshows.Model.PersonDetailModel;
import com.tech.mymovietvshows.PersonDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieCastRVAdapter extends RecyclerView.Adapter<MovieCastRVAdapter.viewHolder> {

    Context context;
    List<MovieCreditsCastModel>movieCreditsCastModelList;

    public MovieCastRVAdapter(Context context, List<MovieCreditsCastModel> movieCreditsCastModelList) {
        this.context = context;
        this.movieCreditsCastModelList = movieCreditsCastModelList;
    }

    @NonNull
    @Override
    public MovieCastRVAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cast_crew_rv_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCastRVAdapter.viewHolder holder, int position) {

        MovieCreditsCastModel movieCreditsCastModel = movieCreditsCastModelList.get(position);

        if(movieCreditsCastModel != null){

            Picasso.get()
                    .load(movieCreditsCastModel.getProfile_path())
                    .placeholder(R.drawable.ic_person)
                    .into(holder.profile_image);

            holder.personName.setText(movieCreditsCastModel.getName());
            holder.personCharacter.setText(movieCreditsCastModel.getCharacter());

            int person_id = movieCreditsCastModel.getId();


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RetrofitInstance.getInstance().apiInterface.getPersonDetailById(person_id,api).enqueue(new Callback<PersonDetailModel>() {
                        @Override
                        public void onResponse(@NonNull Call<PersonDetailModel> call, @NonNull Response<PersonDetailModel> response) {

                            PersonDetailModel personDetailModel = response.body();
                            if(personDetailModel != null && !personDetailModel.getBiography().equals("")){

                                Intent intent = new Intent(context, PersonDetailActivity.class);
                                intent.putExtra("person_id", String.valueOf(person_id));
                                context.startActivity(intent);
                            }else{
                                Toast.makeText(context, "No Any details have this person", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<PersonDetailModel> call, @NonNull Throwable t) {
                            Log.d("person", "On Response fail in adapter");
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return movieCreditsCastModelList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView profile_image;
        private final AppCompatTextView personName;
        private final AppCompatTextView personCharacter;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profile_image  = itemView.findViewById(R.id.profile_image);
            personName = itemView.findViewById(R.id.personName);
            personCharacter = itemView.findViewById(R.id.personCharacter);
        }
    }
}
