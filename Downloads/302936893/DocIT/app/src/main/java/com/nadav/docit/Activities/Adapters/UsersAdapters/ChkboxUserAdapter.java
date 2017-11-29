package com.nadav.docit.Activities.Adapters.UsersAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nadav.docit.Class.User;
import com.nadav.docit.R;

import java.util.ArrayList;

public class ChkboxUserAdapter extends BaseUserAdapter {
    protected ArrayList<String> _checkedUsers;
    public ChkboxUserAdapter(Context context, int resource, ArrayList<User> users) {
        super(context, resource, users);
        _checkedUsers = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_user_checkbox, parent, false);
        }

        CheckBox chkBox = (CheckBox) convertView.findViewById(R.id.usr_chkxb);
        TextView name = (TextView) convertView.findViewById(R.id.usr_name);

        chkBox.setTag(user.getKey());

        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    _checkedUsers.add(buttonView.getTag().toString());
                } else {
                    _checkedUsers.remove(buttonView.getTag().toString());
                }
            }
        });

        name.setText(user.getFname() + " " + user.getLname());
        return convertView;
    }

    public ArrayList<String> getCheckedUsers() { return _checkedUsers;};
}
