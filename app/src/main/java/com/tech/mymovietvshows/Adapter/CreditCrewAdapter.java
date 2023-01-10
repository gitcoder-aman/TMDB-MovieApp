package com.tech.mymovietvshows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Model.MovieCreditsCastModel;
import com.tech.mymovietvshows.Model.MovieCreditsCrewModel;
import com.tech.mymovietvshows.R;

import java.util.List;

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
