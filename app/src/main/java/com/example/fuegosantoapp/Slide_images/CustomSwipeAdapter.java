package com.example.fuegosantoapp.Slide_images;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.example.fuegosantoapp.R;

public class CustomSwipeAdapter extends PagerAdapter {

    private  int [] image_resources = {R.drawable.sample_1,R.drawable.sample_2,R.drawable.sample_3,R.drawable.sample_4, R.drawable.sample_5,R.drawable.sample_6,R.drawable.sample_8};
    private Context ctx;
    private LayoutInflater  layoutInflater;

    public CustomSwipeAdapter(Context ctx ){
          this.ctx = ctx;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return ( view == (LinearLayout)o);

    }

    @Override
    public int getCount() {
        return image_resources.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.slide_images_layout, container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
        //TextView textView = (TextView) item_view.findViewById(R.id.image_count);
        imageView.setImageResource(image_resources[position]);
        //textView.setText("Image : "+ position);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
