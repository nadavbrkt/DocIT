package com.nadav.docit.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nadav.docit.Class.StoryBook;
import com.nadav.docit.R;

import java.util.ArrayList;

/**
 * Created by Nadav on 6/12/2016.
 */
public class StoryBookListAdapter extends ArrayAdapter<StoryBook> {
    private final Context _context;
    private final ArrayList<StoryBook> _storyBooks;

    public StoryBookListAdapter(Context context, ArrayList<StoryBook> storyBooks) {
        super(context, 0, storyBooks);
        this._context = context;
        this._storyBooks = storyBooks;
    }

    static class StoryBookHolder {
        public TextView _name;
        public TextView _creator;
        public ImageView _img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoryBook sb = getItem(position);
        StoryBookHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.story_book_line, parent, false);

            viewHolder = new StoryBookHolder();
            viewHolder._creator = (TextView) convertView.findViewById(R.id.story_creator);
            viewHolder._name = (TextView) convertView.findViewById(R.id.story_name);
            viewHolder._img = (ImageView) convertView.findViewById(R.id.img);

            convertView.setTag(viewHolder);
        }

        viewHolder = (StoryBookHolder) convertView.getTag();

        viewHolder._name.setText(sb.getName());
        viewHolder._creator.setText(sb.getDesc());
        switch (StoryBook.Types.valueOf(sb.getType())) {
            case BASE:
                viewHolder._img.setImageResource(R.mipmap.ic_blank);
                break;
            case BLANK:
                viewHolder._img.setImageResource(R.mipmap.ic_blank);
                break;
            case NEWBORN:
                viewHolder._img.setImageResource(R.mipmap.ic_newborn);
                break;
            case RELATIONSHIP:
                viewHolder._img.setImageResource(R.mipmap.ic_realationship);
                break;
        }

        return convertView;
    }

    @Override
    public void add(StoryBook storyBook) {

        for (int i = 0; i < this.getCount() ; i++) {
            if (storyBook.getKey().equals(getItem(i).getKey())) {
                super.remove(getItem(i));
                super.add(storyBook);
                return;
            }
        }

        super.add(storyBook);
    }

    public void removeMemberByID(String storyBook) {
        for (int i = 0; i < this.getCount() ; i++) {
            if (storyBook.equals(getItem(i).getKey())) {
                remove(getItem(i));
                return;
            }
        }
    }


}
