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
import com.tech.mymovietvshows.R;

import java.util.List;

public class CreditCastAdapter extends RecyclerView.Adapter<CreditCastAdapter.viewHolder> {

    Context context;
    List<MovieCreditsCastModel>movieCreditsCastModelList;

    public CreditCastAdapter(Context context, List<MovieCreditsCastModel> movieCreditsCastModelList) {
        this.context = context;
        this.movieCreditsCastModelList = movieCreditsCastModelList;
    }

    @NonNull
    @Override
    public CreditCastAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.credit_cast_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditCastAdapter.viewHolder holder, int position) {

        MovieCreditsCastModel movieCreditsCastModel = movieCreditsCastModelList.get(position);

        if(movieCreditsCastModel != null){

            Picasso.get()
                    .load(movieCreditsCastModel.getProfile_path())
                    .placeholder(R.drawable.ic_person)
                    .into(holder.cast_posterImage);

            holder.credit_cast_name.setText(movieCreditsCastModel.getName());
            holder.credit_cast_character.setText(movieCreditsCastModel.getCharacter());
        }
    }

    @Override
    public int getItemCount() {
        return movieCreditsCastModelList.size();
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
