package com.example.henry.hbm.Adaptador;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Henry on 17/05/2016.
 * Clase que se encarga de Redimensionar las Imagenes BitMapDecode
 */
public abstract class BitmapUtils {
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * Este metodo de la clase es usada en AdaptadorGaleria
     *
     * @param res       Contexto de la clase
     * @param resId     ID de la imagen (int)
     * @param reqWidth  Tamaño de Ancho de la imagen
     * @param reqHeight Tamaño Largo de la imagen
     * @return Retorna un BitMap decodificado y redimensionado para el menor consumo de imagenes grandes
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}

