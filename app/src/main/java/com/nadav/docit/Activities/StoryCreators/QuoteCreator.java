package com.nadav.docit.Activities.StoryCreators;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.nadav.docit.Activities.Adapters.UsersAdapters.SpinnerUserWrapper;
import com.nadav.docit.Class.CurrentUser;
import com.nadav.docit.Class.DataType.Quote;
import com.nadav.docit.Class.StoryData;
import com.nadav.docit.R;

/**
 * Created by Nadav on 7/2/2016.
 */
public class QuoteCreator implements  IStoryCreator {
    private Context _context;
    private ViewGroup _container;
    private EditText _quote;
    private Spinner _sayer;
    private SpinnerUserWrapper _spinnerUserWrapper;

    public QuoteCreator(Context context, ViewGroup container) {
        _context = context;
        _container = container;
    }

    @Override
    public View createView() {

        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout v = (LinearLayout) inflater.inflate(R.layout.quote_creator, null);
        _quote = (EditText) v.findViewById(R.id.quote_txt);
        _sayer = (Spinner) v.findViewById(R.id.sayer_spnr);

        _spinnerUserWrapper = new SpinnerUserWrapper(_context, _sayer);

        return v;

    }

    @Override
    public boolean validate() {

        if (_quote.getText().toString().isEmpty()) {
            Toast.makeText(_context, "Bad arguments, please fill in a Quote", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public StoryData populateStoryData() {
        Quote story = new Quote();
        story.setCreatedBy(CurrentUser.getInstance().getUid());
        story.setSaidBy(_spinnerUserWrapper.getSelectedUser());
        story.setQtext(_quote.getText().toString());

        return story;
    }
}
