package com.example.fuegosantoapp.Slide_images;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuegosantoapp.Constants;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.entidades.Imagenes;
import com.synnapps.carouselview.CarouselView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomSwipeAdapter extends PagerAdapter {

    private int[] image_resources = {R.drawable.sample_1, R.drawable.sample_2, R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_5, R.drawable.sample_6};
    private Context ctx;
    private LayoutInflater layoutInflater;
    ArrayList<Imagenes> listaImagenes;
    Imagenes imagenes = null;
    private ProgressDialog progressDialog;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    public CustomSwipeAdapter(Context ctx, ArrayList<Imagenes> listaImagenes) {
        this.ctx = ctx;
        this.listaImagenes = listaImagenes;
        request = Volley.newRequestQueue(ctx);
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (LinearLayout) o);

    }


    @Override
    public int getCount() {
        return (listaImagenes == null) ? 0 : listaImagenes.size();
    }


/*
    @Override
    public int getCount() {
        return image_resources.length;
    }
*/

/*
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.slide_images_layout, container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
        //TextView textView = (TextView) item_view.findViewById(R.id.image_count);
        imageView.setImageResource(image_resources[position]);


        listaImagenes = new ArrayList<>();




        //textView.setText("Image : "+ position);
        container.addView(item_view);
        return item_view;
    }
*/


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        listaImagenes = new ArrayList<>();
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.slide_images_layout, container, false);
        ImageView imageView = item_view.findViewById(R.id.image_view);
        //TextView textView = (TextView) item_view.findViewById(R.id.image_count);
        //imageView.setImageResource(image_resources[position]);

        getImagenes(imageView);
        //textView.setText("Image : "+ position);
        container.addView(item_view);


        return item_view;
    }


    private void getImagenes(ImageView imageView) {
        request = Volley.newRequestQueue(ctx);
        progressDialog = new ProgressDialog(ctx);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, Constants.URL_IMAGENES, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                //JSONObject jsonObject = new JSONObject(response);

                JSONArray json = response.optJSONArray("imagenes");
                Log.i("Array : ", json.toString());
                //Toast.makeText(ctx,"Mensaje CustomSwipe:" + json, Toast.LENGTH_SHORT).show();
                //To create Carousel
                //carouselView.setPageCount(json.length());
                //Toast.makeText(ctx, "Mensaje CustomSwipe:" + json.length(), Toast.LENGTH_SHORT).show();

                try {

                    for (int i = 0; i < json.length(); i++) {
                        //carouselView.setPageCount(json.length());
                        imagenes = new Imagenes();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);


                        imagenes.setTitulo(jsonObject.optString("Titulo"));
                        imagenes.setDescription(jsonObject.optString("Descripcion"));
                        imagenes.setImagen(jsonObject.optString("Imagen"));

                        String urlImagen = imagenes.getImagen();

                        //cargarImagenUrl(listaImagenes.get(i).getImagen());
                        //Toast.makeText(ctx, "lista desde CustomSwipeAdapter" + urlImagen, Toast.LENGTH_LONG).show();
                        Log.i("Imagees : ", json.toString());
                        //urlImagen(urlImagen);


                        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {

                                //To pit the image avator rounded
                                //imageViewUserAvatar.setImageDrawable(response);

                                imageView.setImageBitmap(response);
                                //imageView.setImageResource(image_resources[position]);


                                //imageViewUserAvatar.setImageBitmap(response);


                            }
                        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Toast.makeText(getApplicationContext(), "Error al cargar la imagen", Toast.LENGTH_LONG).show();
                            }
                        });


                        request.add(imageRequest);

                        return;


                    }

                    listaImagenes.add(imagenes);
                    //Toast.makeText(ctx, "lista desde CustomSwipeAdapter" + listaImagenes.get(i).getTitulo() , Toast.LENGTH_LONG).show();
                    //urlImagen(imagenes.getImagen(), imageView);


                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(ctx, "No se ha podido establecer una relacion con el servidor  " + response.toString(), Toast.LENGTH_LONG).show();
                    //System.out.println();
                    Log.d("error : ", response.toString());
                    progressDialog.hide();

                    //Log.d("error : ", error.toString());
                    progressDialog.hide();
                }
                //Toast.makeText(getApplicationContext(),"Mensaje:" + response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });


        request.add(jsonObjectRequest);


        //String url = "http://localhost/Android/v1/getImagenes.php";

/*
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_IMAGENES, null, this, this);
        request.add(jsonObjectRequest);
*/


    }


    private void urlImagen(String urlImagen, ImageView imageView) {
        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                //To pit the image avator rounded
                //imageViewUserAvatar.setImageDrawable(response);

                imageView.setImageBitmap(response);
                //imageView.setImageResource(image_resources[position]);


                //imageViewUserAvatar.setImageBitmap(response);


            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Error al cargar la imagen", Toast.LENGTH_LONG).show();
            }
        });
        request.add(imageRequest);


        return;

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
