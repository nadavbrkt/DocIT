package com.nadav.docit.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.nadav.docit.Activities.Adapters.UsersAdapters.ChkboxNoLoggedOnUserWrapper;
import com.nadav.docit.Class.CurrentUser;
import com.nadav.docit.Class.StoryBook;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;
import java.util.ArrayList;
import java.util.List;

public class CreateStoryBookActivity extends AppCompatActivity {
    private static final String TAG = "CREATE SB";
    private EditText _name;
    private EditText _desc;
    private Spinner _spnrType;
    private ListView _addMembers;
    private ChkboxNoLoggedOnUserWrapper _userAdapterWrapper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_story_book);

        _name = (EditText) findViewById(R.id.sb_name);
        _desc = (EditText) findViewById(R.id.sb_desc);
        _spnrType = (Spinner) findViewById(R.id.sb_type);

        _addMembers = (ListView) findViewById(R.id.sb_members);

        Button ok = (Button) findViewById(R.id.okbtn);
        Button cancel = (Button) findViewById(R.id.cancelbtn);

        List<String> typesList = new ArrayList<>();

        for (StoryBook.Types t:StoryBook.Types.values()) {
            typesList.add(t.name());
        }

        typesList.remove(StoryBook.Types.BASE.name());
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, typesList);
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        _spnrType.setAdapter(typesAdapter);
        _userAdapterWrapper = new ChkboxNoLoggedOnUserWrapper(getApplicationContext(), _addMembers);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Save storybook data
    public void saveData() {
        if (_name.getText().toString().isEmpty() || _desc.getText().toString().isEmpty()) {
            Toast.makeText(this, "Bad arguments, please fill them all", Toast.LENGTH_SHORT).show();
        } else {
            ArrayList<String> members = _userAdapterWrapper.getSelectedUsers();
            members.add(CurrentUser.getInstance().getUid());

            // Creates a new stoy book and saves it
            Model.getInstance().createStoryBook(
                _name.getText().toString(),
                _desc.getText().toString(),
                StoryBook.Types.valueOf(_spnrType.getSelectedItem().toString()),
                members,
                new Model.StoryBookCreatorListener() {
                    @Override
                    public void onStoryBookCreated(StoryBook sb, ArrayList<String> members) {
                        sb.addMembers(members);
                        finish();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), "Unable to write story book", Toast.LENGTH_SHORT).show();
                    }
                }
            );
        }
    }
}
