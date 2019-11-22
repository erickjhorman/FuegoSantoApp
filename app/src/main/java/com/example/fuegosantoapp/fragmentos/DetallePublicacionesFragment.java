package com.example.fuegosantoapp.fragmentos;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.adapter.publicacionesAdapter;
import com.example.fuegosantoapp.entidades.Publicacion;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetallePublicacionesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetallePublicacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallePublicacionesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    TextView textPublicacionTexto;
    TextView txtTituloPublicacion;
    TextView txtAutorPublicacion;
    TextView txtFechaPublicacion;
    ImageView imageDetalle;
    RequestQueue request;

    public DetallePublicacionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetallePublicacionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetallePublicacionesFragment newInstance(String param1, String param2) {
        DetallePublicacionesFragment fragment = new DetallePublicacionesFragment();
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

        View vista  = inflater.inflate(R.layout.fragment_detalle_publicacion, container, false);

        request = Volley.newRequestQueue(getContext());
        textPublicacionTexto = (TextView)  vista.findViewById(R.id.descripcionId);
        imageDetalle = (ImageView)  vista.findViewById(R.id.publicacionDetalle);
        txtTituloPublicacion = (TextView) vista.findViewById(R.id.tituloPublicacion);
        txtAutorPublicacion = (TextView) vista.findViewById(R.id.autorPublicacion);
        txtFechaPublicacion = (TextView) vista.findViewById(R.id.fechaPublicacion);

       Bundle objetoPublicacion = getArguments();
        Publicacion publicacion = null;

        if(objetoPublicacion != null){
            publicacion =  (Publicacion)  objetoPublicacion.getSerializable("objeto");

            System.out.println(publicacion);
            textPublicacionTexto.setText(publicacion.getPublicaciones());
            txtTituloPublicacion.setText(publicacion.getFtitulo());
            txtAutorPublicacion.setText(publicacion.getAutor());
            txtFechaPublicacion.setText(publicacion.getFecha_publicacion());
           String imagen = publicacion.getCover();
            //Toast.makeText(getContext(),"Url en DetallePublicacion Activity" + publicacion.getCover(), Toast.LENGTH_LONG).show();
            //Toast.makeText(getContext(),"Url en DetallePublicacion Activity" + imagen , Toast.LENGTH_LONG).show();
            cargarImagenUrl(imagen);
        }


        return  vista;


    }

    private void cargarImagenUrl(String imagen) {

        //String urlImagen = getCover.replace("", "%20");
        //Toast.makeText(getContext(),"Url en cargarImagenUrL Activity" + imagen , Toast.LENGTH_LONG).show();

        ImageRequest imageRequest = new ImageRequest(imagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageDetalle.setImageBitmap(response);
            }
        }, 0,0 , ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Error al cargar la imagen"  , Toast.LENGTH_LONG).show();
            }
        });

        request.add(imageRequest);
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
