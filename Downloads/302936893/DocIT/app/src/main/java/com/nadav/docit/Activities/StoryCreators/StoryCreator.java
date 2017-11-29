package com.nadav.docit.Activities.StoryCreators;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nadav.docit.Class.CurrentUser;
import com.nadav.docit.Class.DataType.Story;
import com.nadav.docit.Class.StoryData;
import com.nadav.docit.Constants;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nadav on 8/10/2016.
 */
public class StoryCreator implements IStoryCreator {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_PHOTO = 2;
    private Context _context;
    private ViewGroup _container;
    private EditText _title;
    private EditText _text;
    private Bitmap _img;
    private File tmpPic;


    public StoryCreator(Context context, ViewGroup container) {
        _context = context;
        _container = container;
        _img = null;
    }

    @Override
    public View createView() {
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout v = (LinearLayout) inflater.inflate(R.layout.story_creator, null);
        _title = (EditText) v.findViewById(R.id.story_title);
        _text = (EditText) v.findViewById(R.id.story_text);

        ImageButton camera = (ImageButton) v.findViewById(R.id.camera_btn);
        ImageButton gallary = (ImageButton) v.findViewById(R.id.gallary_btn);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(_context.getPackageManager()) != null) {
                    tmpPic = null;
                    try {
                        tmpPic= Model.getInstance().createTmpImageFile();
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

        if (!(_title.getText().toString().isEmpty())) {
            if (_img == null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public StoryData populateStoryData() {
        Story storyData = new Story();

        storyData.setCreatedBy(CurrentUser.getInstance().getUid());
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        String name = "IMG_" + format.format(new Date());
        storyData.setImg(name);
        Model.getInstance().uploadImage(name, _img);

        storyData.setTitle(_title.getText().toString());
        storyData.setText(_text.getText().toString());
        return storyData;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        _img = Model.getInstance().getImageFromTmp(tmpPic);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            case PICK_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        //TODO: Display an error
                        return;
                    }
                    try {
                        InputStream inputStream = _context.getContentResolver().openInputStream(data.getData());
                        _img = BitmapFactory.decodeStream(inputStream);
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
