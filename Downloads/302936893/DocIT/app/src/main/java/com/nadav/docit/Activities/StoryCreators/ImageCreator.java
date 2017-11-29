package com.nadav.docit.Activities.StoryCreators;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nadav.docit.Class.CurrentUser;
import com.nadav.docit.Class.DataType.Image;
import com.nadav.docit.Class.StoryData;
import com.nadav.docit.Constants;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nadav on 7/23/2016.
 */
public class ImageCreator implements IStoryCreator {
    public static final int PICK_PHOTO = 2;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap _imgBmp;
    private Context _context;
    private Boolean _isChanged;
    private ViewGroup _container;
    private ImageView _img;
    private File tmpPic = null;

    public ImageCreator(Context context, ViewGroup container) {
        super();
        _context = context;
        _container = container;
    }

    @Override
    public View createView() {
        _isChanged = false;
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout v = (LinearLayout) inflater.inflate(R.layout.image_creator, null);

        ImageButton camera = (ImageButton) v.findViewById(R.id.camera_btn);
        ImageButton gallary = (ImageButton) v.findViewById(R.id.gallary_btn);
        _img = (ImageView) v.findViewById(R.id.img_chosen);

        _img.setImageResource(R.mipmap.ic_blank);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(_context.getPackageManager()) != null) {
                    tmpPic = null;
                    ExifInterface exifInterface = null;
                    try {
                        tmpPic = Model.getInstance().createTmpImageFile();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (tmpPic != null) {

                        Uri picUri = Uri.fromFile(tmpPic);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                        ((Activity) _context).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });

        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                ((Activity) _context).startActivityForResult(intent, PICK_PHOTO);
            }
        });


        return v;
    }

    @Override
    public boolean validate() {
        return _isChanged;
    }

    @Override
    public StoryData populateStoryData() {
        Image story = new Image();
        story.setCreatedBy(CurrentUser.getInstance().getUid());
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        String name = "IMG_" + format.format(new Date());
        story.setImg(name);
        Model.getInstance().uploadImage(name, _imgBmp);

        return story;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == Activity.RESULT_OK) {
                    FileInputStream imgInput = null;
                    try {
                        _imgBmp = Model.getInstance().getImageFromTmp(tmpPic);
                        _img.setImageBitmap(_imgBmp);
                        _isChanged = true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case PICK_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        //TODO: Display an error
                        return;
                    }
                    try {
                        InputStream inputStream = _context.getContentResolver().openInputStream(data.getData());
                        _imgBmp = BitmapFactory.decodeStream(inputStream);
                        _img.setImageBitmap(_imgBmp);
                        _isChanged = true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }

    }
}
