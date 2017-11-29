package com.nadav.docit.Activities.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nadav.docit.Class.StoryData;
import com.nadav.docit.Constants;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Nadav on 8/13/2016.
 */
public class DataDialog extends Dialog {
    private ListView usersInvolved;
    private Context _context;

    public DataDialog(Context context, StoryData storyData, int res) {
        super(context);
        Log.d("DATADATA", storyData.toString());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(res);

        setCancelable(true);
        _context = context;
        usersInvolved = (ListView) findViewById(R.id.users_involved);
        Button ok = (Button) findViewById(R.id.okbtn);
        TextView date =  (TextView) findViewById(R.id.date);

        SimpleDateFormat format = new SimpleDateFormat(Constants.VIEW_DATE_FORMAT);

        date.setText(format.format(storyData.getCreated()));

        Model.getInstance().getUsersNames(storyData.getInvolved(), new Model.GetUsersNames() {
            @Override
            public void onComplition(ArrayList<String> names) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(_context,
                        R.layout.user_list_item, names);

                usersInvolved.setAdapter(adapter);
                setListViewHeightBasedOnChildren(usersInvolved);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }

    // Display ful list
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
