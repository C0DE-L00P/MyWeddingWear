package com.example.tabsold.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.tabsold.MainActivity;
import com.example.tabsold.R;
import com.example.tabsold.blue_effect.BlurTransformation;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class VitrineFragment extends Fragment implements MainActivity.FragmentLifecycle {

    public static boolean inSlider = false;
//    WeakReference weakReference ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.main, container, false);

        VerticalViewPager verticalViewPager = (VerticalViewPager) v.findViewById(R.id.verticleViewPager);
        ArrayList<DealModel> dealsList;
        dealsList = new ArrayList<DealModel>();
        dealsList.add(new DealModel(R.drawable.w2));
        dealsList.add(new DealModel(R.drawable.w3));
        dealsList.add(new DealModel(R.drawable.w4));
        dealsList.add(new DealModel(R.drawable.w5));

        verticalViewPager.setAdapter(new VerticlePagerAdapter(getContext(), dealsList));
        return v;
    }

    @Override
    public void onPauseFragment() {
        MainActivity.showTabLayout();
        Log.d(TAG, "onPauseFragment: vitrine paused");
    }

    @Override
    public void onResumeFragment() {
        MainActivity.hideTabLayout();
        Log.d(TAG, "onPauseFragment: vitrine resumed");
    }
//
//    @Override
//    public void onBackPressed() {
//        if (inSlider) {
//
//        } else {
//            super.onBackPressed();
//        }
//    }
}


class VerticlePagerAdapter extends PagerAdapter {
    private String mResources[] = {"swedish", "spanish", "german", "fifth", "sixth"};
    private String mPrices[] = {"1000$", "1400$", "1450$", "1550$", "2000$"};
    private int mImages[] = {R.drawable.w1, R.drawable.w2, R.drawable.w3, R.drawable.w4, R.drawable.w5};
    private Context mContext;
    private ArrayList<DealModel> lst;
    private SharedPreferences preferences;

    private LayoutInflater mLayoutInflater;

    public VerticlePagerAdapter(Context context, ArrayList<DealModel> lst) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.content_main, container, false);
        final ImageView imageView = itemView.findViewById(R.id.imageView);
        final ImageView heart_ic = itemView.findViewById(R.id.heart_ic);
        final TapBarMenu tapBarMenu = itemView.findViewById(R.id.tapBarMenu);
        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
                Log.d("DaTuM", "onClick: tabBar is on");
            }
        });

        ImageView share = itemView.findViewById(R.id.item1);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Share");
            }
        });

        final LinearLayout priceLayout = itemView.findViewById(R.id.price_layout);

        ((TextView) itemView.findViewById(R.id.textView)).setText(mResources[position]);
        ((TextView) itemView.findViewById(R.id.price_tag)).setText(mPrices[position]);

        //DotsIndicator dotsIndicator = (DotsIndicator) itemView.findViewById(R.id.dots_indicator);

        if (preferences.getBoolean(((TextView) itemView.findViewById(R.id.textView)).getText().toString() + " isLiked", false)) {
            heart_ic.setImageResource(R.drawable.ic_heart_full);
        } else {
            heart_ic.setImageResource(R.drawable.ic_heart);
        }

        heart_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (heart_ic.getTag() == "off") {
                    heart_ic.setImageResource(R.drawable.ic_heart_full);
                    heart_ic.setTag("on");
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(((TextView) itemView.findViewById(R.id.textView)).getText().toString() + " isLiked", true);
                    editor.apply();
                } else {
                    heart_ic.setImageResource(R.drawable.ic_heart);
                    heart_ic.setTag("off");
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(((TextView) itemView.findViewById(R.id.textView)).getText().toString() + " isLiked", false);
                    editor.apply();
                }
            }
        });
//
//        Glide.with(mContext).asBitmap().load(mImages[position])
//                .into(imageView);

        Glide.with(mContext).load(mImages[position]).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    imageView.setBackground(resource);
                }
            }
        });


        ImageView Gallery = itemView.findViewById(R.id.gallery_ic);
        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager vp = (ViewPager) itemView.findViewById(R.id.view_pager_slider);
//                DotsIndicator dotsIndicator = (DotsIndicator) itemView.findViewById(R.id.dots_indicator);
                if (heart_ic.getVisibility() == View.VISIBLE) {
                    priceLayout.setVisibility(View.GONE);
                    heart_ic.setVisibility(View.GONE);
                    vp.setVisibility(View.VISIBLE);
                    vp.setPageTransformer(true, new ViewPagerStack());
                    vp.setOffscreenPageLimit(4); //mark
                    vp.setAdapter(new DealsAdapter(lst, mContext));
                    imageView.setBackgroundResource(mImages[position]);

                    Glide.with(mContext).asBitmap().load(mImages[position])
                            .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                            .into(imageView);

                    ((ImageView) itemView.findViewById(R.id.gallery_ic)).setImageResource(R.drawable.ic_arrow);
                    ((ImageView) itemView.findViewById(R.id.gallery_ic)).animate().scaleX(0.6f).scaleY(0.6f).rotation(180f);
                    VitrineFragment.inSlider = true;

                } else {
                    priceLayout.setVisibility(View.VISIBLE);
                    heart_ic.setVisibility(View.VISIBLE);
                    vp.setVisibility(View.GONE);

                    ((ImageView) itemView.findViewById(R.id.gallery_ic)).setImageResource(R.drawable.cards);
                    ((ImageView) itemView.findViewById(R.id.gallery_ic)).animate().scaleX(1f).scaleY(1f).rotation(0);

                    imageView.setImageDrawable(null);
                    VitrineFragment.inSlider = false;
                }
            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((androidx.constraintlayout.widget.ConstraintLayout) object);
    }
}

class VerticalViewPager extends ViewPager {
    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() { // The majority of the magic happens here
        setPageTransformer(true, new VerticalPageTransformer()); // The easiest way to get rid of the overscroll drawing that happens on the left and right
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private static class VerticalPageTransformer implements PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View view, float position) {
            if (position < -1) { // [-Infinity,-1) // This page is way off-screen to the left.
                view.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1); // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position); //set Y position to swipe in from top
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);
                view.setScaleX(1);
                view.setScaleY(1);
            } else if (position <= 1) { // [0,1]
                view.setAlpha(1); // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position); // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    /**
     * Swaps the X and Y coordinates of your touch event.
     */

    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();
        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;
        ev.setLocation(newX, newY);
        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev); // return touch coordinates to original reference frame for any child views
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }
}


class ViewPagerStack implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position >= 0) {
            page.setScaleX(0.7f - 0.05f * position);
            page.setScaleY(0.7f);

            page.setTranslationX(-page.getWidth() * position);
            page.setTranslationY(-30 * position);
        }
    }
}


class DealsAdapter extends PagerAdapter {

    private List<DealModel> deals;
    private Context context;

    public DealsAdapter(List<DealModel> deals, Context context) {
        this.deals = deals;
        this.context = context;
    }

    @Override
    public int getCount() {
        return deals.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.deal_model, container, false);
        container.addView(view);

        ImageView dealImageView = view.findViewById(R.id.deal_image);
//        dealImageView.setImageResource(deals.get(position).getDeal_img());
        Glide.with(context).asBitmap().load(deals.get(position).getDeal_img()).into(dealImageView);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}

class DealModel {

    private int deal_img;

    DealModel(int deal_img) {
        this.deal_img = deal_img;
    }

    int getDeal_img() {
        return deal_img;
    }

    public void setDeal_img(int deal_img) {
        this.deal_img = deal_img;
    }
}