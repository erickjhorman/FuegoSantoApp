package com.example.fuegosantoapp.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.android.volley.toolbox.ImageRequest;
import com.example.fuegosantoapp.MainActivity;
import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.activities.ProfileActivity;
import com.example.fuegosantoapp.fragmentos.Fragmento_publicaciones;
import com.google.firebase.messaging.RemoteMessage;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.Hashtable;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    Bitmap bitmap;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData() != null) {
            enviarNotificacion(remoteMessage);
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Body notification: " + remoteMessage.getNotification().getBody());
            enviarNotificacion(remoteMessage);
        }
    }

    private void enviarNotificacion(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String body = data.get("body");
        String imagen = data.get("imagen");
        convertirImagebBitmap(imagen);

        Log.d(TAG, "Imagen: " + imagen);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "xcheko51x";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Solo para android Oreo o superior
            @SuppressLint("WrongConstant")
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Mi notificacion",
                    NotificationManager.IMPORTANCE_MAX
            );

            // Configuracion del canal de notificacion
            channel.setDescription("xcheko51x channel para app");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            channel.enableVibration(true);

            manager.createNotificationChannel(channel);

        }


        Intent intent = new Intent(this, MainActivity.class);


        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                intent,
                0


        );



        /*
        Intent intent = new Intent(this, MainActivity.class);


        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                intent,
                0


        );
*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setTicker("Hearty365")
                .setContentTitle(title)
                .setContentText(body)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null))

                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setContentIntent(pendingIntent)
                .setContentInfo("info");


        manager.notify(1, builder.build());
    }

    private void convertirImagebBitmap(String imagen) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);


        ImageRequest imageRequest = new ImageRequest(imagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                bitmap = response;

            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Error al cargar la imagen", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(imageRequest);
        return;

    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed Token" + token);

        FirebaseMessaging.getInstance().subscribeToTopic("dispositivos");
        enviarTokenToServer(token);
    }

    private void enviarTokenToServer(final String token) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://192.168.0.74/Android/v1/registerToken.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Se registro exitosamente", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR EN LA CONEXION", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("Token", token);

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
