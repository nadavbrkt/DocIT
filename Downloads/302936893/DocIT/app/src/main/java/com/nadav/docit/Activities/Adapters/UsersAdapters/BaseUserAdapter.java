package com.nadav.docit.Activities.Adapters.UsersAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nadav.docit.Class.User;
import com.nadav.docit.R;

import java.util.ArrayList;

/**
 * Created by Nadav on 7/21/2016.
 */
public class BaseUserAdapter extends ArrayAdapter<User> {
    protected ArrayList<User> _users;

    public BaseUserAdapter(Context context, int resource, ArrayList<User> users) {
        super(context,resource, users);
        _users = users;
    }
}

