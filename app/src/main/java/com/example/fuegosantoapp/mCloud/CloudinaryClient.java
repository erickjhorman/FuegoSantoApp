package com.example.fuegosantoapp.mCloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;

public class CloudinaryClient {

    public static String getRoundCornerImage(String ImageName){

        Cloudinary cloud = new Cloudinary(Myconfiguration.getMyconfigs());
        Transformation t = new Transformation();
        t.radius(60);
        t.height(500);
        t.width(800);

        String utl = cloud.url().transformation(t).generate(ImageName);
        return  utl;

    }
}
