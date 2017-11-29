package com.nadav.docit.Models;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.nadav.docit.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CheckedInputStream;

/**
 * Created by Nadav on 6/9/2016.
 */
public class ImgManager {
    private static final String TAG = "IMGMANAGER";
    private static ImgManager _instance = null;
    private static LruCache<String, Bitmap> _imgCache;

    protected ImgManager() {

        String folder_main = "NewFolder";

        File f = new File(Constants.MAIN_DIR);
        if (!f.exists()) {
            f.mkdirs();
        }

        f = new File(Constants.IMG_DIR);
        if (!f.exists()) {
            f.mkdirs();
        }

        f = new File(Constants.IMG_TMP);
        if (!f.exists()) {
            f.mkdirs();
        }

        // Sets LRU Cache
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        _imgCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    // Singletone
    public static ImgManager getInstance() {
        if (_instance == null) {
            _instance = new ImgManager();
        }

        return _instance;
    }

    // Scale squre image
    public Bitmap scaleImage(Bitmap src, int size) {
        Bitmap image = src;
        int newHeight, newWidth;
        float scale;

        if (image.getHeight() >= image.getWidth()) {
            newWidth = size;
            scale = ((float) newWidth) / image.getWidth();
            newHeight = (int) (scale * image.getHeight());

            image = Bitmap.createScaledBitmap(image,
                    newWidth, newHeight, false);
        } else {

            newHeight = size;
            scale = ((float) newHeight) / image.getHeight();
            newWidth = (int) (image.getWidth() * scale);


            image = Bitmap.createScaledBitmap(image,
                    newWidth, newHeight, false);
        }

        return image;
    }

    // Scale rect image
    public Bitmap scaleImage(Bitmap src, int width, int height) {
        Bitmap image = src;
        int newHeight =0, newWidth =0;
        float scale;

        if ((image.getHeight() >= height) && (image.getWidth() >= width)) {
            newHeight = image.getHeight();
            newWidth = image.getWidth();
        } else if((image.getHeight() <= height) || (image.getWidth() <= width)){
            newHeight = height;
            scale = ((float) newHeight) / image.getHeight();
            newWidth = (int) (image.getWidth() * scale);

            if (newWidth < width) {
                newWidth = width;
                scale = ((float) newWidth) / image.getWidth();
                newHeight = (int) (image.getHeight() * scale);
            }

            image = Bitmap.createScaledBitmap(image,
                    newWidth, newHeight, false);
        }
        image = Bitmap.createBitmap(image, ((newWidth - width) / 2),((newHeight - height) / 2),
                width, height);
        return image;
    }

    // Adds image to cache
    public void addImageToCache(String imgName, Bitmap image) {
        _imgCache.put(imgName, image);
    }

    // Sets image rect to listening view
    public void setImage(String imgName, final int size, final Model.GetImageListener listener) {
        final String scaleImgName = imgName + "_" + size + "_" + size;

        // Checks cahce
        if (_imgCache.get(scaleImgName) != null) {
            listener.onImageLoaded(imgName, _imgCache.get(scaleImgName));
        } else if (_imgCache.get(imgName) != null){
            _imgCache.put(scaleImgName, scaleImage(_imgCache.get(imgName), size));
            listener.onImageLoaded(imgName, _imgCache.get(scaleImgName));
        } else {
            setImage(imgName, new Model.GetImageListener() {
                @Override
                public void onImageLoaded(String name, Bitmap image) {
                    // Scale image
                    _imgCache.put(scaleImgName, scaleImage(_imgCache.get(name), size));
                    listener.onImageLoaded(name, _imgCache.get(scaleImgName));
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // Sets image default
    public void setImage(final String imgName, final Model.GetImageListener listener) {
        // Checks cache
        if (_imgCache.get(imgName) != null) {
            listener.onImageLoaded(imgName, _imgCache.get(imgName));
        } else {
            // Checks disk
            Model.getInstance().readImageFromFile(imgName, new Model.GetStoredImageListener() {
                @Override
                public void onImageStored(String name, Bitmap image) {
                    // Puts on cache
                    _imgCache.put(name, image);
                    listener.onImageLoaded(name, image);
                }

                @Override
                public void onError(Exception e) {
                    // Download image
                    AsyncTask<String,String,Bitmap> task = new AsyncTask<String, String, Bitmap >() {
                        @Override
                        protected Bitmap doInBackground(String... params) {
                            Bitmap bmp = Model.getInstance().downloadImageFromCloud(imgName);
                            return bmp;
                        }

                        @Override
                        protected void onPostExecute(Bitmap bitmap) {
                            // Saves to cache and disk
                            if (imgName == null || bitmap == null) {
                                listener.onError(new Exception("problemsaving image"));
                                return;
                            }
                            _imgCache.put(imgName, bitmap);
                            listener.onImageLoaded(imgName, bitmap);
                            Model.getInstance().writeImageToStorage(imgName, bitmap, new Model.GetStoredImageListener() {
                                @Override
                                public void onError(Exception e) {
                                    listener.onError(e);
                                }

                                @Override
                                public void onImageStored(String name, Bitmap image) {
                                    Log.d(TAG, name + " is stored");
                                }
                            });
                        }
                    };
                    task.execute();
                }
            });
        }
    }

    // Set image to view with set w and h
    public void setImage(String image, final int width, final int height, final Model.GetImageListener listener) {
        final String scaleImgName = image + "_" + width + "_" + height;

        // Checks if on cache
        if (_imgCache.get(scaleImgName) != null) {
            listener.onImageLoaded(image, _imgCache.get(scaleImgName));
        } else {
            // Puts image on cache
            setImage(image, new Model.GetImageListener() {
                @Override
                public void onImageLoaded(String name, Bitmap image) {
                    // Scale image as needed
                    _imgCache.put(scaleImgName, scaleImage(_imgCache.get(name), width, height));
                    listener.onImageLoaded(name, _imgCache.get(scaleImgName));
                }

                @Override
                public void onError(Exception e) {
                    listener.onError(e);
                }
            });
        }
    }

    // Get Image with oriantation
    public Bitmap getImageFromTmp(File tmp) throws IOException {
        Bitmap image = null;
        InputStream imgInput = new FileInputStream(tmp.getAbsolutePath());
        ExifInterface exifInterface = new ExifInterface(tmp.getAbsolutePath().toString());
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int rotation = 0;
        switch (orientation){
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotation = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotation = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotation = 270;
                break;
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        image = BitmapFactory.decodeStream(imgInput, null, options);

        image = Bitmap.createBitmap(image, 0,0,image.getWidth(), image.getHeight(), matrix, true);
        scaleImage(image, Constants.IMG_WIDTH_F, Constants.IMG_HEIGHT_F);
        return image;
    }
}
