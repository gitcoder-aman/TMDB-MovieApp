package com.tech.mymovietvshows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Model.MovieCreditsCastModel;
import com.tech.mymovietvshows.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
