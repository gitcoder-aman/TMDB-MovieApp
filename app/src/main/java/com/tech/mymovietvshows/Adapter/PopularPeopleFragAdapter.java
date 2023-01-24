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
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.PersonDetailModel;
import com.tech.mymovietvshows.Model.PopularPersonModelResult;
import com.tech.mymovietvshows.MovieDetailActivity;
import com.tech.mymovietvshows.PersonDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularPeopleFragAdapter extends RecyclerView.Adapter<PopularPeopleFragAdapter.viewHolder> {

    Context context;
    List<PopularPersonModelResult> popularPersonModelResultList;
//    ArrayList<List<PopularPersonModelResult>> arrayList;

    public PopularPeopleFragAdapter(Context context, List<PopularPersonModelResult> popularPersonModelResultList) {
        this.context = context;
        this.popularPersonModelResultList = popularPersonModelResultList;
    }

    @NonNull
    @Override
    public PopularPeopleFragAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.people_rv_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularPeopleFragAdapter.viewHolder holder, int position) {


        PopularPersonModelResult modelResult = popularPersonModelResultList.get(position);

        Picasso.get()
                .load(modelResult.getProfile_path())
                .placeholder(R.drawable.image_loading)
                .into(holder.people_imageView);

        if (modelResult.getName() != null) {
            holder.person_name.setText(modelResult.getName());

        } else {
            holder.person_name.setVisibility(View.GONE);
        }

        if (modelResult.getKnown_for_department() != null) {
            holder.department.setText(modelResult.getKnown_for_department());

        } else {
            holder.department.setVisibility(View.GONE);
        }
        int person_id = modelResult.getId();

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

    @Override
    public int getItemCount() {

        return popularPersonModelResultList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView people_imageView;
        private final AppCompatTextView person_name;
        private final AppCompatTextView department;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            people_imageView = itemView.findViewById(R.id.people_imageView);
            person_name = itemView.findViewById(R.id.person_name);
            department = itemView.findViewById(R.id.department);
        }
    }
}
