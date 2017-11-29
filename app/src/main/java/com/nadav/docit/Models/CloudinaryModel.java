package com.nadav.docit.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nadav.docit.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nadav on 7/23/2016.
 */
public class CloudinaryModel {
    private Map _config;
    private Cloudinary _cloudinary;
    private Context _context;

    public CloudinaryModel(Context context){
        _context = context;
        _config = new HashMap();
        _config.put("cloud_name", "docit");
        _config.put("api_key", "191536694636736");
        _config.put("api_secret", "CnZfv_A0PSbxEd8c7zxnrZ8wViA");
        _cloudinary = new Cloudinary(_config);
    }

    // Get image from url
    public Bitmap getImage(final String name) {
        URL url = null;
        try {
            url = new URL(getImgUrl(name));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Upload image to cloud async
    public void uploadImg(final String name) {
        AsyncTask<String,String, Void> task = new AsyncTask<String, String, Void >() {
            @Override
            protected Void doInBackground(String... params) {
            try {
                _cloudinary.uploader().upload(Constants.IMG_DIR + "/" + name + ".png",
                        ObjectUtils.asMap("public_id", name));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
            }
        };

        task.execute();
    }

    // Gets image url
    public String getImgUrl(String src) {
        return _cloudinary.url().transformation(new Transformation().
                width(Constants.IMG_WIDTH).height(Constants.IMG_HEIGHT).crop("fit"))
                .generate(src);
    }

    // Error checking for cloudinary
    protected void errorChecking() throws Exception {
        Boolean isError = false;
        String errorMsg = "";
        ConnectivityManager check = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = check.getAllNetworkInfo();

        // Checks Path exists
        if (!((new File(Constants.IMG_DIR)).exists())) {
            isError = true;
            errorMsg = Constants.Errors.DIR_ERROR;
        } else {
            // Check internet connection
            Boolean connected = false;
            for (int i = 0; i<info.length; i++){
                if (info[i].getState() == NetworkInfo.State.CONNECTED){
                    connected = true;
                }
            }

            if (!(connected)) {
                isError = true;
                errorMsg = Constants.Errors.NET_ERROR;
            }
        }

        // Sends error
        if (isError) {
            throw new Exception(errorMsg);
        }
    }

    // Upload image from disk async
    public void uploadImageFromDisk(final String imgName) {
        AsyncTask<String,String, Void> task = new AsyncTask<String, String, Void >() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    _cloudinary.uploader().upload(Constants.IMG_DIR + "/" + imgName + ".png",
                            ObjectUtils.asMap("public_id", imgName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        task.execute();
    }
}
