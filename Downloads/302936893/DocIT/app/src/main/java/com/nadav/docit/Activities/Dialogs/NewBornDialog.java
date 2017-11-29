package com.nadav.docit.Activities.Dialogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.nadav.docit.Class.DataType.Milestones.NewBornMS.NewBorn;
import com.nadav.docit.Class.StoryData;
import com.nadav.docit.Constants;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;

import org.w3c.dom.Text;

/**
 * Created by Nadav on 8/13/2016.
 */
public class NewBornDialog extends DataDialog {
    private ImageView babyImg;
    private ImageView mndImage;
    private ImageView roomImage;

    public NewBornDialog(Context context, NewBorn storyData) {
        super(context, storyData, R.layout.dialog_milestone_newborn);

        TextView par1 = (TextView) findViewById(R.id.par_1);
        TextView par2 = (TextView) findViewById(R.id.par_2);
        TextView par3 = (TextView) findViewById(R.id.par_3);

        babyImg = (ImageView) findViewById(R.id.baby_pic);
        mndImage = (ImageView) findViewById(R.id.mnd_pic);
        roomImage = (ImageView) findViewById(R.id.room_pic);

        par1.setText(storyData.getFirstPar());
        par2.setText(storyData.getSecondPar());
        par3.setText(storyData.getThirdPar());

        Model.getInstance().setImage(storyData.getImgBaby(), Constants.IMG_WIDTH_F, Constants.IMG_HEIGHT_F, new Model.GetImageListener() {
            @Override
            public void onImageLoaded(String name, Bitmap image) {
                babyImg.setImageBitmap(image);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        Model.getInstance().setImage(storyData.getImgMomDad(),Constants.IMG_WIDTH_F, Constants.IMG_HEIGHT_F, new Model.GetImageListener() {
            @Override
            public void onImageLoaded(String name, Bitmap image) {
                mndImage.setImageBitmap(image);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        Model.getInstance().setImage(storyData.getImgChildRoom(),Constants.IMG_WIDTH_F, Constants.IMG_HEIGHT_F, new Model.GetImageListener() {
            @Override
            public void onImageLoaded(String name, Bitmap image) {
                roomImage.setImageBitmap(image);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
