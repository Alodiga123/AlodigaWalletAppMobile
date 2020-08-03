package com.alodiga.app.wallet.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BipmapUtils {
    private static Bitmap selectedImage;
    private static Bitmap selectedImageSelfie;


    public static Bitmap getSelectedImage() {
        return selectedImage;
    }

    public static void setSelectedImage(Bitmap selectedImage) {
        BipmapUtils.selectedImage = selectedImage;
    }

    public static Bitmap getSelectedImageSelfie() {
        return selectedImageSelfie;
    }

    public static void setSelectedImageSelfie(Bitmap selectedImageSelfie) {
        BipmapUtils.selectedImageSelfie = selectedImageSelfie;
    }

    //convertir a base 64
    public static String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String imgDecodableString = Base64.encodeToString(b, Base64.DEFAULT);

        return imgDecodableString;
    }

}
