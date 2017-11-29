package com.nadav.docit.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nadav.docit.Activities.Adapters.StoryBookAdapter;
import com.nadav.docit.Activities.Fragments.StoryBookFragment;
import com.nadav.docit.Activities.Fragments.StoryBookListFragment;
import com.nadav.docit.Class.StoryBook;
import com.nadav.docit.Constants;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;



public class MainActivity extends AppCompatActivity implements StoryBookListFragment.OnItemClick {
    private StoryBookListFragment sbFragment;
    private StoryBookFragment sdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            sbFragment = new StoryBookListFragment();
            sdFragment = new StoryBookFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            sbFragment.setArguments(getIntent().getExtras());

            // Add the fragment
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, sbFragment).commit();
        }
    }

    @Override
    public void onBookClicked(StoryBook book) {
        // Book clicked change fragment
        Bundle bun = new Bundle();
        bun.putString(StoryBookAdapter.BOOKID, book.getKey());
        setTitle(book.getName());
        sdFragment.setArguments(bun);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, sdFragment).addToBackStack(null).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Adds menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Action on options menu
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.create_new_sb :
                // Creates a new storybook
                intent = new Intent(MainActivity.this, CreateStoryBookActivity.class);
                startActivity(intent);
                return true;
            case R.id.sign_out:
                // Logout
                Model.getInstance().logOut();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
