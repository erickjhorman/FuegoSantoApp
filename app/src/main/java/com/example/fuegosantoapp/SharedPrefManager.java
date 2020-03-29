package com.example.fuegosantoapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context ctx;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_SUBSCRIPTOR_ID = "subscriptorid";
    private static final String KEY_SUBSCRIPTOR_EMAIL = "subscriptoremail";
    private static final String KEY_SUBSCRIPTOR_NAME = "subscriptorname";
    private static final String KEY_SUBSCRIPTOR_AVATAR = "subscriptoravatar";

    private SharedPrefManager(Context context) {
        ctx = context;


    }


    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean subsciptorLogin(int id_correos, String email, String nombre, String avatar) {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_SUBSCRIPTOR_ID, id_correos);
        editor.putString(KEY_SUBSCRIPTOR_EMAIL, email);
        editor.putString(KEY_SUBSCRIPTOR_NAME, nombre);
        editor.putString(KEY_SUBSCRIPTOR_AVATAR, avatar);

        editor.apply();

        return true;
    }

    public boolean getUserUpdated(int id_correos, String email, String nombre, String avatar) {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_SUBSCRIPTOR_ID, id_correos);
        editor.putString(KEY_SUBSCRIPTOR_EMAIL, email);
        editor.putString(KEY_SUBSCRIPTOR_NAME, nombre);
        editor.putString(KEY_SUBSCRIPTOR_AVATAR, avatar);

        editor.apply();

        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_SUBSCRIPTOR_EMAIL, null) != null) {
            return true;
        }
        return false;
    }

    public boolean isUpdated() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_SUBSCRIPTOR_EMAIL, "Invitado") != null && sharedPreferences.getString(KEY_SUBSCRIPTOR_AVATAR, "Invitado") != null) {
            return true;
        }
        return false;
    }

    /*
     public boolean logout(){
         SharedPreferences sharedPreferences =  ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
         editor.clear();
         editor.apply();
         return true;
    }
    */


    public boolean logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public int getUserId() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_SUBSCRIPTOR_ID, 0);
    }

    public String getUserEmail() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SUBSCRIPTOR_EMAIL, null);
    }

    public String getUserName() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SUBSCRIPTOR_NAME, null);
    }


    public String getUseAvatar() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SUBSCRIPTOR_AVATAR, null);
    }

}

