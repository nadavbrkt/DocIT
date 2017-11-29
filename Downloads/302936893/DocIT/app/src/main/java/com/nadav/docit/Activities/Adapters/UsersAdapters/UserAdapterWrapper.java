package com.nadav.docit.Activities.Adapters.UsersAdapters;

import android.content.Context;
import android.widget.AdapterView;

import com.nadav.docit.Activities.Adapters.UsersAdapters.BaseUserAdapter;
import com.nadav.docit.Class.User;
import com.nadav.docit.Models.Model;

import java.util.ArrayList;

/**
 * Created by Nadav on 7/1/2016.
 */
public abstract class UserAdapterWrapper {
    protected static final String TAG = "BASE WRAPPER";
    protected Context _context;
    protected ArrayList<User> _data;
    protected AdapterView _adptView;
    protected BaseUserAdapter _userAdapter = null;

    // Base UserAdapterCtor
    public UserAdapterWrapper(Context context, AdapterView adptView) {
        this._adptView = adptView;
        this._context = context;
        this._data = new ArrayList<>();

        Model.getInstance().getUsers(null, new Model.GetUsersListener() {
            @Override
            public void onUserGer(ArrayList<User> users) {
                _data = users;
                setAdapter();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected void setAdapter() {

    }
}
