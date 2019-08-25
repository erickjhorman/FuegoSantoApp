package com.example.fuegosantoapp.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.adapter.publicacionesAdapter;
import com.example.fuegosantoapp.entidades.Publicacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragmento_publicaciones.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragmento_publicaciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragmento_publicaciones extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerPublicaciones;
    ArrayList<Publicacion> listaPublicaciones;

    ProgressDialog progress;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    public Fragmento_publicaciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragmento_publicaciones.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragmento_publicaciones newInstance(String param1, String param2) {
        Fragmento_publicaciones fragment = new Fragmento_publicaciones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_fragmento_publicaciones, container, false);

        listaPublicaciones = new ArrayList<>();
        recyclerPublicaciones = (RecyclerView) vista.findViewById(R.id.idRecycler);
        recyclerPublicaciones.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerPublicaciones.setHasFixedSize(true);

        request = Volley.newRequestQueue(getContext());

        cargarWebServices();

        return vista;
    }

    private void cargarWebServices() {

        progress = new ProgressDialog(getContext());
        progress.setMessage("Consultando");
        progress.show();

        String url = "http://192.168.0.74/Android/v1/publications.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        //Log.d("error : ", error.toString());
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        Publicacion publicacion = null;

        JSONArray json = response.optJSONArray("publicacion");

        try {
            for (int i = 0; i < json.length(); i++) {
                publicacion = new Publicacion();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                publicacion.setId_publicaciones(jsonObject.optInt("id"));
                publicacion.setFtitulo(jsonObject.optString("titulo"));
                publicacion.setDescripcion(jsonObject.optString("descripcion"));
                publicacion.setCover((jsonObject.optString("cover")));
                publicacion.setPublicaciones(jsonObject.optString("publicacion"));
                publicacion.setFecha_publicacion(jsonObject.optString("fecha_publicacion"));
                publicacion.setAutor(jsonObject.optString("autor"));
                listaPublicaciones.add(publicacion);

            }
            progress.hide();

            publicacionesAdapter adapter = new publicacionesAdapter(listaPublicaciones , getContext());
            recyclerPublicaciones.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer una relacion con el servidor  " + response.toString(), Toast.LENGTH_LONG).show();
            //System.out.println();
            Log.d("error : ", response.toString());
            progress.hide();

            //Log.d("error : ", error.toString());
            progress.hide();
        }
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}