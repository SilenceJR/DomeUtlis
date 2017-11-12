package com.silence.androidmvprxjavadome.image_loader;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.silence.androidmvprxjavadome.image_loader.inter.ImageCache;

/**
 * Created by Silence
 *
 * @time 2017/11/12 11:35
 * @des ${TODO}
 */

public class MemoryCache implements ImageCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache() {
//        mMemoryCache = new LruCache<>();
    }

    @Override
    public Bitmap get(String url) {
        return mMemoryCache.get(url);
    }

    @Override
    public void put(String url, Bitmap bmp) {
        mMemoryCache.put(url, bmp);
    }
}
