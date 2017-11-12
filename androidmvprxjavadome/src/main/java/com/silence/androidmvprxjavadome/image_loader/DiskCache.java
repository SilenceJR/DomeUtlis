package com.silence.androidmvprxjavadome.image_loader;

import android.graphics.Bitmap;

import com.silence.androidmvprxjavadome.image_loader.inter.ImageCache;

/**
 * Created by Silence
 *
 * @time 2017/11/12 11:59
 * @des ${TODO}
 */

public class DiskCache implements ImageCache {

    @Override
    public Bitmap get(String url) {
        return null;
    }

    @Override
    public void put(String url, Bitmap bmp) {

    }
}
