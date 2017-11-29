package com.nadav.docit.Activities.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import com.nadav.docit.Class.DataType.Image;
import com.nadav.docit.Class.User;
import com.nadav.docit.Constants;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;

/**
 * Created by Nadav on 8/13/2016.
 */
public class ImageDialog extends DataDialog {
    private ImageView imageView;
    private TextView createdBy;
    public ImageDialog(Context context, Image image) {
        super(context, image, R.layout.dialog_image);
        int size = Constants.IMG_WIDTH;

        imageView = (ImageView) findViewById(R.id.image);
        createdBy = (TextView) findViewById(R.id.created_by);
        Display dis = getWindow().getWindowManager().getDefaultDisplay();
        if (dis.getWidth() < dis.getHeight())
            size = dis.getWidth();
        else
            size = dis.getHeight();

        Model.getInstance().setImage(image.getImgSrc(),size,size, new Model.GetImageListener() {
            @Override
            public void onImageLoaded(String name, Bitmap image) {
                imageView.setImageBitmap(image);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        Model.getInstance().getUserData(image.getCreatedBy(), new Model.GetUserDataListener() {
            @Override
            public void onCompletion(User user) {
                createdBy.setText(user.getFname() + " " + user.getLname());
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
