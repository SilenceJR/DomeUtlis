package com.silence.androidmvprxjavadome.image_loader.inter;

import android.graphics.Bitmap;

/**
 * Created by Silence
 *
 * @time 2017/11/12 11:17
 * @des ${TODO}
 */

public interface ImageCache {
    Bitmap get(String url);
    void put(String url, Bitmap bmp);

}
