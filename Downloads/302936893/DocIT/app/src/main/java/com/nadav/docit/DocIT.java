package com.nadav.docit;

import android.content.Context;
import android.util.Log;

import com.firebase.client.Firebase;

/**
 * Created by Nadav on 6/12/2016.
 */
public class DocIT extends android.app.Application {
    private static Context _context;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        DocIT._context = getApplicationContext();
    }

    public static Context getAppContext() {
        return DocIT._context;
    }
}
