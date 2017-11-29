package com.nadav.docit.Activities.Dialogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nadav.docit.Class.DataType.Story;
import com.nadav.docit.Class.StoryData;
import com.nadav.docit.Constants;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;

import org.w3c.dom.Text;

/**
 * Created by Nadav on 8/13/2016.
 */
public class StoryDialog extends DataDialog {
    private ImageView _imageView;

    public StoryDialog(Context context, Story story) {
        super(context, story, R.layout.dialog_story);

        _imageView = (ImageView) findViewById(R.id.image);
        TextView title = (TextView) findViewById(R.id.title);
        TextView text = (TextView) findViewById(R.id.text);

        title.setText(story.getTitle());
        text.setText(story.getText());



        Model.getInstance().setImage(story.getImgSrc(), Constants.IMG_WIDTH_F, Constants.IMG_HEIGHT_F, new Model.GetImageListener() {
            @Override
            public void onImageLoaded(String name, Bitmap image) {
                _imageView.setImageBitmap(image);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

    }
}
