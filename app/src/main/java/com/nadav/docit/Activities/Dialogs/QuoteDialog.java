package com.nadav.docit.Activities.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nadav.docit.Class.DataType.Quote;
import com.nadav.docit.Class.User;
import com.nadav.docit.Constants;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;

import java.util.ArrayList;

/**
 * Created by Nadav on 8/13/2016.
 */
public class QuoteDialog extends DataDialog {

    public QuoteDialog(Context context, Quote quote) {
        super(context, quote, R.layout.dialog_quote);
        TextView data = (TextView) findViewById(R.id.quote_txt);
        final TextView sayer = (TextView) findViewById(R.id.quote_sayer);

        Model.getInstance().getUserData(quote.getSaidBy(), new Model.GetUserDataListener() {
            @Override
            public void onCompletion(User user) {
                sayer.setText(user.getFname() + " " + user.getLname());
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        data.setText(quote.getQtext());
    }
}
