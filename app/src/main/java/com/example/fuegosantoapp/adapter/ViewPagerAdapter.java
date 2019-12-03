package com.example.fuegosantoapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.toolbox.Volley;
import com.example.fuegosantoapp.entidades.Imagenes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    ArrayList<Imagenes> listaImagenes;


    public ViewPagerAdapter(Context context, ArrayList<Imagenes> listaImagenes) {
        this.context = context;
        this.listaImagenes = listaImagenes;

    }


    @Override
    public int getCount() {
        return (listaImagenes == null) ? 0 : listaImagenes.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Imagenes imagenes = null;
        ImageView imageView = new ImageView(context);


        Picasso.get()
                .load(listaImagenes.get(position).getImagen())
                .fit()

                .centerCrop()
                .into(imageView);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
