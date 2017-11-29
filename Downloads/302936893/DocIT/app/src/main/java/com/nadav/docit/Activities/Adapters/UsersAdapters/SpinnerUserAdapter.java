package com.nadav.docit.Activities.Adapters.UsersAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nadav.docit.Class.User;

import java.util.ArrayList;

public class SpinnerUserAdapter extends BaseUserAdapter implements AdapterView.OnItemSelectedListener {
    private String _selectedUser;
    public SpinnerUserAdapter(Context context, int resource, ArrayList<User> users) {
        super(context, resource, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(android.R.id.text1);
        name.setText(user.getFname() + " " + user.getLname());
        return convertView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
       _selectedUser = ((User) parent.getItemAtPosition(position)).getKey();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public String getSelectedUser() {
        return _selectedUser;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(android.R.id.text1);
        name.setText(user.getFname() + " " + user.getLname());
        return convertView;
    }
}
