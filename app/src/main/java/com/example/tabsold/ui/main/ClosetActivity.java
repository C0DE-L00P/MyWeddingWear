package com.example.tabsold.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tabsold.R;
import com.example.tabsold.blue_effect.BlurTransformation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ClosetActivity extends Fragment implements FavAdapter.OnFavClickListener {

    private RecyclerView rv;
    private FavAdapter favAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private List<FavRow> lst = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.staggered_list, container, false);
        setHasOptionsMenu(true);
        rv = v.findViewById(R.id.fav_recycler_view);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(staggeredGridLayoutManager);

        FloatingActionButton fab = v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


//        lst.add(new FavRow(R.drawable.deals));
        lst.add(new FavRow(R.drawable.w1));
        lst.add(new FavRow(R.drawable.w2));
        lst.add(new FavRow(R.drawable.w3));
        lst.add(new FavRow(R.drawable.w4));
        lst.add(new FavRow(R.drawable.w5));
        lst.add(new FavRow(R.drawable.w1));
        lst.add(new FavRow(R.drawable.w2));
        lst.add(new FavRow(R.drawable.w3));
        lst.add(new FavRow(R.drawable.w4));
        lst.add(new FavRow(R.drawable.w5));
        lst.add(new FavRow(R.drawable.w1));
        lst.add(new FavRow(R.drawable.w2));
        lst.add(new FavRow(R.drawable.w3));
        lst.add(new FavRow(R.drawable.w4));
        lst.add(new FavRow(R.drawable.w5));
        lst.add(new FavRow(R.drawable.w1));
        lst.add(new FavRow(R.drawable.w2));
        lst.add(new FavRow(R.drawable.w3));
        lst.add(new FavRow(R.drawable.w4));
        lst.add(new FavRow(R.drawable.w5));

        favAdapter = new FavAdapter(getContext(), lst, this);
        rv.setAdapter(favAdapter);

        return v;
    }

    @Override
    public void onFavClick(int position, ImageView v, ImageView deleteFav, Context context) {
        if (deleteFav.getVisibility() == View.VISIBLE) {
            Log.d(TAG, "visible");
            v.animate().alpha(0f).setDuration(200);
            deleteFav.animate().alpha(0f).setDuration(200);
            deleteFav.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "invisible");
        }
    }

    @Override
    public void onFavLongClick(int position, ImageView v, ImageView deleteFav, Context context) {
        Glide.with(context).asBitmap().load(lst.get(position).getImg())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(v);
        v.animate().alpha(1f).setDuration(200);
        deleteFav.setVisibility(View.VISIBLE);
        deleteFav.animate().alpha(1f).setDuration(200);

    }

    @Override
    public void onDeleteFavClick(int position, ImageView v, Context context) {
        Glide.with(context).asBitmap().load(lst.get(position).getImg())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(v);
        v.animate().alpha(0f).setDuration(200);
        lst.remove(position);
        favAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calls, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_call) {
//            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
//                    .show();
//        }
        return false;
    }
}


class FavAdapter extends RecyclerView.Adapter<FavAdapter.ImageViewHolder> {

    private Context mContext;
    private List<FavRow> list;
    private OnFavClickListener mOnFavClickListener;

    public FavAdapter(Context mContext, List<FavRow> list, OnFavClickListener onFavClickListener) {
        this.mContext = mContext;
        this.list = list;
        this.mOnFavClickListener = onFavClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fav_item, parent, false);
        return new ImageViewHolder(view, mOnFavClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
//        holder.img.setImageResource(list.get(position).getImg());

        //best performance
        Glide.with(mContext).asBitmap().load(list.get(position).getImg()).into(holder.img);

//        Glide.with(mContext).asBitmap().load(list.get(position).getImg())
//                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
//                .into(holder.img);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView img,imgBlur,deleteFav;
        OnFavClickListener onFavClickListener;

        private ImageViewHolder(@NonNull View itemView, OnFavClickListener onFavClickListener) {
            super(itemView);
            img = itemView.findViewById(R.id.fav_img);
            imgBlur = itemView.findViewById(R.id.fav_img_blur);
            deleteFav = itemView.findViewById(R.id.delete_fav);
            this.onFavClickListener = onFavClickListener;
            deleteFav.setOnClickListener(this);
            img.setOnClickListener(this);
            img.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fav_img:
                    onFavClickListener.onFavClick(getAdapterPosition(), imgBlur, deleteFav, mContext);
                    break;
                case R.id.delete_fav:
                    onFavClickListener.onDeleteFavClick(getAdapterPosition(), imgBlur, mContext);
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            onFavClickListener.onFavLongClick(getAdapterPosition(), imgBlur, deleteFav, mContext);
            return true;
        }


    }

    public interface OnFavClickListener {
        void onFavClick(int position, ImageView v, ImageView deleteFav, Context context);

        void onFavLongClick(int position, ImageView v, ImageView deleteFav, Context context);

        void onDeleteFavClick(int position, ImageView v, Context context);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }
}


class FavRow {

    int img;

    public FavRow(int img) {
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
