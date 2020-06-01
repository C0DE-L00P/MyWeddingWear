package com.example.tabsold;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.CustomViewHolder> {

    private List<ImageModel> imageModelsList;
    private Context mContext;
    private Activity act;

    public ImagesAdapter(Context context, ArrayList<ImageModel> imageList, Activity activity) {
        this.imageModelsList = imageList;
        this.mContext = context;
        this.act = activity;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.goos, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        final ImageModel get = imageModelsList.get(position);

        holder.photographer.setText(get.getPhotographer());
        final String image = get.getUrl();
        Glide
                .with(this.mContext)
                .load(image)
                .into(holder.image);

    }


    @Override
    public int getItemCount() {
        return (null != imageModelsList ? imageModelsList.size() : 0);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView photographer;

        public CustomViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            photographer = (TextView) itemView.findViewById(R.id.photographer);

        }
    }
}