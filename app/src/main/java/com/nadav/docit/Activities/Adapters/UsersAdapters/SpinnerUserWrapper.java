package com.nadav.docit.Activities.Adapters.UsersAdapters;

import android.content.Context;
import android.util.Log;
import android.widget.AdapterView;

import com.nadav.docit.Class.CurrentUser;

/**
 * Created by Nadav on 7/21/2016.
 */
public class SpinnerUserWrapper extends UserAdapterWrapper {
    public SpinnerUserWrapper(Context context, AdapterView adptView) {
        super(context, adptView);
    }

    @Override
    protected void setAdapter() {
        _userAdapter = new SpinnerUserAdapter(_context,0,_data);
        _userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int pos = _userAdapter.getPosition(CurrentUser.getInstance().getUser());
        _adptView.setAdapter(_userAdapter);
        _adptView.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) _userAdapter);
        _adptView.setSelection(pos);
    }

    public String getSelectedUser() {
        return ((SpinnerUserAdapter) _userAdapter).getSelectedUser();
    }
}
