package com.example.fuegosantoapp.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.adapter.commentsAdapter;
import com.example.fuegosantoapp.entidades.Comentarios;
import com.example.fuegosantoapp.entidades.Publicacion;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComentariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComentariosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recycleComments;
    ArrayList<Comentarios> listComentarios;

    public ComentariosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComentariosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComentariosFragment newInstance(String param1, String param2) {
        ComentariosFragment fragment = new ComentariosFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comentarios, container, false);


        recycleComments = view.findViewById(R.id.RecyclerCommentsId);
        recycleComments.setLayoutManager((new LinearLayoutManager(getContext())));
        Log.i("Recycle" ," Comentarios Bundle objetoPublicacion = getArguments();"+ recycleComments);

        Bundle objComentarios = getArguments();
        Log.i("lista" ," Comentarios Bundle objetoPublicacion = getArguments();"+ objComentarios);
        Comentarios comentarios = null;

       if(objComentarios != null){


          List comentario =  objComentarios.getStringArrayList("lista");

           commentsAdapter commentsAdapter = new commentsAdapter(comentario);
           recycleComments.setAdapter(commentsAdapter);
       }


        return view;
    }


}
