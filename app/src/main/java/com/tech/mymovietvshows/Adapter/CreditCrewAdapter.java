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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.MovieCreditsCastModel;
import com.tech.mymovietvshows.Model.MovieCreditsCrewModel;
import com.tech.mymovietvshows.Model.PersonDetailModel;
import com.tech.mymovietvshows.PersonDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditCrewAdapter extends RecyclerView.Adapter<CreditCrewAdapter.viewHolder> {

    Context context;
    List<MovieCreditsCrewModel>movieCreditsCrewModelList;

    public CreditCrewAdapter(Context context, List<MovieCreditsCrewModel> movieCreditsCrewModelList) {
        this.context = context;
        this.movieCreditsCrewModelList = movieCreditsCrewModelList;
    }

    @NonNull
    @Override
    public CreditCrewAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.credit_cast_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditCrewAdapter.viewHolder holder, int position) {

        MovieCreditsCrewModel movieCreditsCrewModel = movieCreditsCrewModelList.get(position);

        if(movieCreditsCrewModel != null){

            Picasso.get()
                    .load(movieCreditsCrewModel.getProfile_path())
                    .placeholder(R.drawable.ic_person)
                    .into(holder.cast_posterImage);

            holder.credit_cast_name.setText(movieCreditsCrewModel.getName());
            holder.credit_cast_character.setText(movieCreditsCrewModel.getDepartment());

            int person_id = movieCreditsCrewModel.getId();

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
        return movieCreditsCrewModelList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView cast_posterImage;
        private final AppCompatTextView credit_cast_name;
        private final AppCompatTextView credit_cast_character;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            cast_posterImage = itemView.findViewById(R.id.cast_posterImage);
            credit_cast_name = itemView.findViewById(R.id.credit_cast_name);
            credit_cast_character = itemView.findViewById(R.id.credit_cast_character);
        }
    }
}
