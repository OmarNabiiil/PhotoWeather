package com.robusta.photoweather.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static byte[] bitmapsToBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        return stream.toByteArray();
    }

    public static Bitmap bytesToBitmaps(byte[] byteArray){
        return BitmapFactory.decodeByteArray(byteArray , 0, byteArray.length);
    }

}
