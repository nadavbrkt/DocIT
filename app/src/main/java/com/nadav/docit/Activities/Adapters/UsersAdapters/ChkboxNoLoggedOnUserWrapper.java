package com.nadav.docit.Activities.Adapters.UsersAdapters;

import android.content.Context;
import android.widget.AdapterView;

import com.nadav.docit.Class.CurrentUser;

/**
 * Created by Nadav on 7/22/2016.
 */
public class ChkboxNoLoggedOnUserWrapper extends ChkboxUserWrapper{
    public ChkboxNoLoggedOnUserWrapper(Context context, AdapterView adptView) {
        super(context, adptView);
    }

    @Override
    protected void setAdapter() {
        super.setAdapter();
        _userAdapter.remove(CurrentUser.getInstance().getUser());
    }
}
