package com.nadav.docit.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nadav.docit.Class.DataType.Image;
import com.nadav.docit.Class.DataType.Milestone;
import com.nadav.docit.Class.DataType.Milestones.NewBornMS.NewBorn;
import com.nadav.docit.Class.DataType.Quote;
import com.nadav.docit.Class.DataType.Story;
import com.nadav.docit.Class.StoryData;
import com.nadav.docit.Class.User;
import com.nadav.docit.Constants;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Nadav on 6/27/2016.
 */
public class StoryBookAdapter extends BaseAdapter {
    public static final String BOOKID = "com.nadav.docit.Activities.Fragments.StoryBookFragment.BOOK_ID";

    private static final String TAG = "BOOK ADPT";

    private final Activity _context;
    private final ArrayList<StoryData> _data;

    // Holders
    static class ImgViewHolder {
        public ImageView _image;
    }

    static class QouteViewHolder {
        public TextView _qoute;
        public TextView _sayer;
    }

    static class StoryViewHolder {
        public TextView _title;
        public TextView _text;
        public ImageView _image;
    }

    static class MilestoneHolder {
        public TextView _name;
        public TextView _date;
    }

    public StoryBookAdapter(Activity context, ArrayList<StoryData> storyBooks) {
        super();
        this._context = context;
        this._data = storyBooks;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        switch (_data.get(position).getTypeVal()) {
            case IMAGE:
                return 0;
            case QUOTE:
                return 1;
            case STORY:
                return 2;
            case MILESTONE:
                return 3;
            default:
                break;
        }

        return -1;
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int position) {
        return _data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get story data in position
        StoryData storyData = (StoryData) getItem(position);

        View row = convertView;

        Bitmap icon = BitmapFactory.decodeResource(_context.getResources(),
                R.mipmap.ic_blank);
        // Creates view if necessary
        if (row == null) {
            switch (storyData.getTypeVal()) {
            case IMAGE:
                row = _context.getLayoutInflater().inflate(R.layout.img_line, null);

                // Creates holder
                ImgViewHolder viewHolder = new ImgViewHolder();
                viewHolder._image = (ImageView) row.findViewById(R.id.img);
                row.setTag(viewHolder);
                break;
            case QUOTE :
                row = _context.getLayoutInflater().inflate(R.layout.quote_line, null);

                // Creates holder
                QouteViewHolder viewHolder1 = new QouteViewHolder();
                viewHolder1._qoute = (TextView) row.findViewById(R.id.quote_txt);
                viewHolder1._sayer = (TextView) row.findViewById(R.id.quote_sayer);
                row.setTag(viewHolder1);
                break;
            case STORY:
                row = _context.getLayoutInflater().inflate(R.layout.story_line, null);

                // Creates holder
                StoryViewHolder viewHolder2 = new StoryViewHolder();
                viewHolder2._title = (TextView) row.findViewById(R.id.title);
                viewHolder2._text = (TextView) row.findViewById(R.id.text);
                viewHolder2._image = (ImageView) row.findViewById(R.id.image);

                row.setTag(viewHolder2);
                break;
            case MILESTONE:
                row = _context.getLayoutInflater().inflate(R.layout.milestone_line, null);

                // Creates holder
                MilestoneHolder viewHolder3 = new MilestoneHolder();

                viewHolder3._name = (TextView) row.findViewById(R.id.milestone_name);
                viewHolder3._date = (TextView) row.findViewById(R.id.date);

                row.setTag(viewHolder3);
                break;
            }
        }

        // Put data in holder
        switch (storyData.getTypeVal()) {
            case IMAGE:
                int width =  Constants.IMG_WIDTH_F;
                int height =  Constants.IMG_HEIGHT_F;
                final ImgViewHolder imageViewHolder = (ImgViewHolder) row.getTag();

                imageViewHolder._image.setImageBitmap(icon);

                Image img = (Image) getItem(position);

                Model.getInstance().setImage(img.getImgSrc(), Constants.IMG_WIDTH_F, Constants.IMG_HEIGHT_F, new Model.GetImageListener() {
                    @Override
                    public void onImageLoaded(String name, Bitmap image) {

                        imageViewHolder._image.setImageBitmap(image);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
                break;
            case QUOTE :

                Quote quote = (Quote) getItem(position);
                Log.d("TATAG",row.getTag().toString());
                final QouteViewHolder qouteViewHolder = (QouteViewHolder) row.getTag();

                qouteViewHolder._qoute.setText("\"" + quote.getQtext() + "\"");

                Model.getInstance().getUserData(quote.getSaidBy(), new Model.GetUserDataListener() {
                    @Override
                    public void onCompletion(User user) {
                        qouteViewHolder._sayer.setText(user.getFname() + " " + user.getLname());
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });


                break;
            case STORY:
                Story story = (Story) getItem(position);

                final StoryViewHolder storyViewHolder = (StoryViewHolder) row.getTag();
                storyViewHolder._image.setImageBitmap(icon);

                storyViewHolder._text.setText(story.getText());
                storyViewHolder._title.setText(story.getTitle());

                Model.getInstance().setImage(story.getImgSrc(),Constants.IMG_WIDTH, Constants.IMG_HEIGHT, new Model.GetImageListener() {
                    @Override
                    public void onImageLoaded(String name, Bitmap image) {
                        storyViewHolder._image.setImageBitmap(image);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
                break;
            case MILESTONE:
                NewBorn newBorn = (NewBorn) getItem(position);
                final MilestoneHolder newBornHolder = (MilestoneHolder) row.getTag();

                SimpleDateFormat format = new SimpleDateFormat(Constants.VIEW_DATE_FORMAT);
                newBornHolder._name.setText("New baby is born");
                newBornHolder._date.setText(format.format(newBorn.getCreated()));

                break;
        }

        return row;
    }

    public void changeMember(StoryData storyData) {
        for (int i = 0; i < this.getCount() ; i++) {
            if (storyData.getKey().equals(((StoryData) getItem(i)).getKey())) {
                _data.set(i,storyData);
                notifyDataSetChanged();
                return;
            }
        }

        _data.add(storyData);
        notifyDataSetChanged();
    }

    public void removeMemberByID(String key) {
        for (int i = 0; i < this.getCount() ; i++) {
            if (key.equals(((StoryData) getItem(i)).getKey())) {
                _data.remove(getItem(i));
                return;
            }
        }
    }
}
