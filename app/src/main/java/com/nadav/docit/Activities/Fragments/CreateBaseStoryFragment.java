package com.nadav.docit.Activities.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.nadav.docit.Activities.Adapters.UsersAdapters.ChkboxUserWrapper;
import com.nadav.docit.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Nadav on 7/2/2016.
 */
public class CreateBaseStoryFragment extends Fragment {
    private ChkboxUserWrapper _userAdapterWrapper = null;
    private String _dateHappend = null;
    private SimpleDateFormat format;
    private GregorianCalendar cal;
    private Button now;
    private OnDoneEditData _dataListener;

    public interface OnDoneEditData {
        public void setBaseData(Date happend, ArrayList<String> usersInvolved);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_base_story, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDoneEditData) {
            _dataListener = (OnDoneEditData) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement CreateExtenedStoryFragment.OnDoneEditData");
        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            setDate(arg1, arg2+1, arg3);
        }
    };

    private void setDate(int year, int mon, int day) {
        cal.set(year, mon, day);
        _dateHappend = cal.get(Calendar.DAY_OF_MONTH) +"/" + cal.get(Calendar.MONTH)+ "/" + cal.get(Calendar.YEAR);
        now.setText(_dateHappend);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        final ListView _usersInvolved = (ListView) getView().findViewById(R.id.involved_list);
        now = (Button) getView().findViewById(R.id.create_base_story_date);
        cal = new GregorianCalendar();
        _dateHappend = cal.get(Calendar.DAY_OF_MONTH) +"/" + (cal.get(Calendar.MONTH) + 1)+ "/" + cal.get(Calendar.YEAR);
        now.setText(_dateHappend);

        Button ok = (Button) getView().findViewById(R.id.okbtn);
        Button back = (Button) getView().findViewById(R.id.back);

        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), myDateListener, cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(false);
                datePicker.setTitle("Select the date");
                datePicker.show();
            }
        });

        _userAdapterWrapper = new ChkboxUserWrapper(getActivity().getApplicationContext(), _usersInvolved);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dataListener.setBaseData(new Date(), _userAdapterWrapper.getSelectedUsers());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null);
            }
        });
    }
}
