package com.robusta.photoweather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.robusta.photoweather.R;
import com.robusta.photoweather.entities.WeatherPhoto;
import com.robusta.photoweather.helpers.Utils;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<WeatherPhoto> weatherPhotoList;
    private ItemClickListener monClickListener;
    private Context mContext;

    public interface ItemClickListener{
        void onWeatherPhotoClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView weatherImage;
        public MaterialCardView categoryLayout;

        public MyViewHolder(View view) {
            super(view);

            weatherImage = view.findViewById(R.id.weatherImage);
            categoryLayout = view.findViewById(R.id.categoryLayout);

            categoryLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedItemPosition=getAdapterPosition();
            if(view.equals(categoryLayout)){
                monClickListener.onWeatherPhotoClickListener(this, clickedItemPosition);
            }
        }
    }

    public HistoryAdapter(List<WeatherPhoto> weatherPhotoList, ItemClickListener monClickListener, Context mContext) {
        this.weatherPhotoList = weatherPhotoList;
        this.monClickListener = monClickListener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.history_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        WeatherPhoto weatherPhoto = weatherPhotoList.get(i);

        myViewHolder.weatherImage.setImageBitmap(Utils.bytesToBitmaps(weatherPhoto.getImage()));

        /*myViewHolder.categoryTitle.setText(category.getName());
        Picasso.get().load(category.getImage())
                .placeholder( R.drawable.progress_animation ).into(myViewHolder.weatherImage);*/
    }

    @Override
    public int getItemCount() {
        return weatherPhotoList.size();
    }
}
