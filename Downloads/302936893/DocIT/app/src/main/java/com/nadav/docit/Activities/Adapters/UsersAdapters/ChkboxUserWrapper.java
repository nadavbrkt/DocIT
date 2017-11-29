package com.nadav.docit.Activities.Adapters.UsersAdapters;

import android.content.Context;
import android.util.Log;
import android.widget.AdapterView;

import java.util.ArrayList;

/**
 * Created by Nadav on 7/21/2016.
 */
public class ChkboxUserWrapper extends UserAdapterWrapper {
    public ChkboxUserWrapper(Context context, AdapterView adptView) {
        super(context, adptView);
    }

    @Override
    protected void setAdapter() {
        _userAdapter = new ChkboxUserAdapter(_context, 0, _data);
        _adptView.setAdapter(_userAdapter);
    }

    // Returns checked users
    public ArrayList<String> getSelectedUsers() {
        return ((ChkboxUserAdapter) _userAdapter).getCheckedUsers();
    };
}
