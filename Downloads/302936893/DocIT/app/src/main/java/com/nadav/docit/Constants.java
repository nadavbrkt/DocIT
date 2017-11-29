package com.nadav.docit;

import android.os.Environment;

/**
 * Created by Nadav on 6/10/2016.
 */
public class Constants {
    public static final String MAIN_DIR = Environment.getExternalStorageDirectory().toString() + "/DocIT";
    public static final String IMG_DIR = MAIN_DIR + "/Images";
    public static final String DATE_FORMAT = "ddMMyyyyHHmmssSSS";
    public static final String VIEW_DATE_FORMAT = "dd/MM/yyyy";
    public static final String IMG_TMP = MAIN_DIR + "/TMP" ;
    // Intents names

    public static final int IMG_HEIGHT = 500;
    public static final int IMG_WIDTH = 500;
    public static final int IMG_WIDTH_F = 1000;
    public static final int IMG_HEIGHT_F = 600;
    public static final String QUOTE = "Quote :";
    public static final String STORY = "Story :";
    public static final String IMAGE = "Image :";
    public static final String NEW_BORN = "New Born :";


    // Error handling
    public static final class Errors {
        public static final String NET_ERROR = "Unable to connect to network";
        public static final String DIR_ERROR = "Unable to find path";
        public static final String BAD_IMG = "Unable to retrieve images";
    }

    // Firebase data
    public static final class FB {
        public static final String LastUpdate = "LastUpdate";

        public static final class UserScheme {
            public static final String Name = "Users";
            public static final String StoryBooksID = "storyBookId";
        }

        public static final class StoryBooksScheme {
            public static final String Name = "StoryBooks";
            public static final String Type = "type";
            public static final String data = "storyData";
            public static final String date = "happend";
        }

    }
}
