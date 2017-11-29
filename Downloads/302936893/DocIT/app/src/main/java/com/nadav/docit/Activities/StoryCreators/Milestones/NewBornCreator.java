package com.nadav.docit.Activities.StoryCreators.Milestones;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nadav.docit.Activities.StoryCreators.IStoryCreator;
import com.nadav.docit.Class.CurrentUser;
import com.nadav.docit.Class.DataType.Milestones.NewBornMS.NewBorn;
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
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nadav on 8/13/2016.
 */
public class NewBornCreator implements IStoryCreator {
    public static final int REQUEST_BABY_IMAGE_CAPTURE = 11;
    public static final int REQUEST_MND_IMAGE_CAPTURE = 12;
    public static final int REQUEST_ROOM_IMAGE_CAPTURE = 13;
    public static final int BABY_PICK_PHOTO = 21;
    public static final int MND_PICK_PHOTO = 22;
    public static final int ROOM_PICK_PHOTO = 23;

    private Context _context;
    private ViewGroup _container;
    private ImageView _babyImg;
    private ImageView _mndImg;
    private ImageView _roomImg;
    private EditText[] _par;
    private Bitmap[] _bitmaps;
    private File[] _tmpFile;

    public NewBornCreator(Context context, ViewGroup container) {
        super();
        _context = context;
        _container = container;
        _bitmaps = new Bitmap[3];
        _par = new EditText[3];
        _tmpFile = new File[3];
    }

    @Override
    public View createView() {

        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout v= (LinearLayout) inflater.inflate(R.layout.creator_milestone_newborn, null);

        _babyImg = (ImageView) v.findViewById(R.id.baby_pic);
        _mndImg = (ImageView) v.findViewById(R.id.mnd_pic);
        _roomImg = (ImageView) v.findViewById(R.id.room_pic);
        _babyImg.setImageResource(R.mipmap.ic_blank);
        _mndImg.setImageResource(R.mipmap.ic_blank);
        _roomImg.setImageResource(R.mipmap.ic_blank);

        _par[0] = (EditText) v.findViewById(R.id.par_1);
        _par[1] = (EditText) v.findViewById(R.id.par_2);
        _par[2] = (EditText) v.findViewById(R.id.par_3);

        ImageButton camera = (ImageButton) v.findViewById(R.id.camera_btn1);
        ImageButton gallary = (ImageButton) v.findViewById(R.id.gallary_btn1);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(_context.getPackageManager()) != null) {
                    _tmpFile[0] = null;

                    try {
                        _tmpFile[0] = Model.getInstance().createTmpImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (_tmpFile[0] != null) {
                        Uri picUri = Uri.fromFile(_tmpFile[0]);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                        ((Activity) _context).startActivityForResult(takePictureIntent, REQUEST_BABY_IMAGE_CAPTURE);
                    }
                }
            }
        });

        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                ((Activity) _context).startActivityForResult(intent, BABY_PICK_PHOTO);
            }
        });

        camera = (ImageButton) v.findViewById(R.id.camera_btn2);
        gallary = (ImageButton) v.findViewById(R.id.gallary_btn2);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(_context.getPackageManager()) != null) {
                    _tmpFile[1] = null;

                    try {
                        _tmpFile[1] = Model.getInstance().createTmpImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (_tmpFile[1] != null) {
                        Uri picUri = Uri.fromFile(_tmpFile[1]);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                        ((Activity) _context).startActivityForResult(takePictureIntent, REQUEST_MND_IMAGE_CAPTURE);
                    }
                }
            }
        });

        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                ((Activity) _context).startActivityForResult(intent, MND_PICK_PHOTO);
            }
        });

        camera = (ImageButton) v.findViewById(R.id.camera_btn3);
        gallary = (ImageButton) v.findViewById(R.id.gallary_btn3);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(_context.getPackageManager()) != null) {
                    _tmpFile[2] = null;

                    try {
                        _tmpFile[2] = Model.getInstance().createTmpImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (_tmpFile[2] != null) {
                        Uri picUri = Uri.fromFile(_tmpFile[2]);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                        ((Activity) _context).startActivityForResult(takePictureIntent, REQUEST_ROOM_IMAGE_CAPTURE);
                    }
                }
            }
        });

        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                ((Activity) _context).startActivityForResult(intent, ROOM_PICK_PHOTO);
            }
        });

        return v;
    }

    @Override
    public boolean validate() {
        for (int i = 0; i < 3; i++) {
            if (_bitmaps[i] == null)
                return false;
        }

        for (int i = 0; i < 3; i++) {
            if (_par[i].getText().toString().isEmpty())
                return false;
        }

        return true;
    }

    @Override
    public StoryData populateStoryData() {
        NewBorn newBorn = new NewBorn();

        newBorn.setFirstPar(_par[0].getText().toString());
        newBorn.setSecond_Par(_par[1].getText().toString());
        newBorn.setThirdPar(_par[2].getText().toString());

        newBorn.setCreatedBy(CurrentUser.getInstance().getUid());
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);

        String babyImg = "IMG_" + format.format(new Date());
        newBorn.setImgBaby(babyImg);
        Model.getInstance().uploadImage(babyImg, _bitmaps[0]);

        String mndImg = "IMG_" + format.format(new Date());
        newBorn.setImgMomDad(mndImg);
        Model.getInstance().uploadImage(mndImg, _bitmaps[1]);

        String roomImg = "IMG_" + format.format(new Date());
        newBorn.setImgChildRoom(roomImg);
        Model.getInstance().uploadImage(roomImg, _bitmaps[2]);

        return newBorn;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap tmp = null;
        switch (requestCode) {
            case REQUEST_BABY_IMAGE_CAPTURE:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        _bitmaps[0] = Model.getInstance().getImageFromTmp(_tmpFile[0]);
                        _babyImg.setImageBitmap(_bitmaps[0]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case BABY_PICK_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        return;
                    }
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;
                        InputStream inputStream = _context.getContentResolver().openInputStream(data.getData());
                        tmp = BitmapFactory.decodeStream(inputStream, null, options);
                        _bitmaps[0] = Model.getInstance().scaleImage(tmp, Constants.IMG_WIDTH_F, Constants.IMG_HEIGHT_F);
                        _babyImg.setImageBitmap(_bitmaps[0]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case REQUEST_MND_IMAGE_CAPTURE:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        _bitmaps[1] = Model.getInstance().getImageFromTmp(_tmpFile[1]);
                        _mndImg.setImageBitmap(_bitmaps[1]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case MND_PICK_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        return;
                    }
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;
                        InputStream inputStream = _context.getContentResolver().openInputStream(data.getData());
                        tmp = BitmapFactory.decodeStream(inputStream, null, options);
                        _bitmaps[1] = Model.getInstance().scaleImage(tmp, Constants.IMG_WIDTH_F, Constants.IMG_HEIGHT_F);
                        _mndImg.setImageBitmap(_bitmaps[1]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case REQUEST_ROOM_IMAGE_CAPTURE:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        _bitmaps[2] = Model.getInstance().getImageFromTmp(_tmpFile[2]);
                        _roomImg.setImageBitmap(_bitmaps[2]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case ROOM_PICK_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        return;
                    }
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;
                        InputStream inputStream = _context.getContentResolver().openInputStream(data.getData());
                        tmp = BitmapFactory.decodeStream(inputStream, null, options);
                        _bitmaps[2] = Model.getInstance().scaleImage(tmp, Constants.IMG_WIDTH_F, Constants.IMG_HEIGHT_F);
                        _roomImg.setImageBitmap(_bitmaps[2]);

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
