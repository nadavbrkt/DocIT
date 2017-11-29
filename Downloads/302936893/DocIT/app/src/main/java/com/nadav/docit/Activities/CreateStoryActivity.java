package com.nadav.docit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.nadav.docit.Activities.Fragments.CreateBaseStoryFragment;
import com.nadav.docit.Activities.Fragments.CreateExtenedStoryFragment;
import com.nadav.docit.Activities.StoryCreators.ImageCreator;
import com.nadav.docit.Activities.StoryCreators.Milestones.NewBornCreator;
import com.nadav.docit.Class.StoryData;
import com.nadav.docit.Models.Model;
import com.nadav.docit.R;

import java.util.ArrayList;
import java.util.Date;

public class CreateStoryActivity extends AppCompatActivity
        implements CreateExtenedStoryFragment.OnDoneEditData,
                   CreateBaseStoryFragment.OnDoneEditData{
    public static final String STORY_BOOK_ID = "com.nadav.docit.STORY_BOOK_ID";
    public static final String STORY_TYPE = "com.nadav.docit.STORY_TYPE";
    private CreateExtenedStoryFragment extenedStoryFragment;
    private StoryData _storyData;
    private String _storyBookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_story);
        _storyBookID = getIntent().getExtras().getString(STORY_BOOK_ID);

        if (findViewById(R.id.create_story_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            extenedStoryFragment = new CreateExtenedStoryFragment();
            Bundle bun = new Bundle();
            bun.putString(CreateExtenedStoryFragment.TYPE, getIntent().getExtras().get(STORY_TYPE).toString().toUpperCase());
            extenedStoryFragment.setArguments(bun);

            // Add the fragment
            getSupportFragmentManager().beginTransaction().add(R.id.create_story_container, extenedStoryFragment).commit();
        }
    }

    @Override
    public void setExtendedData(StoryData storyData) {
        // Get data from extended data frag
        _storyData = storyData;
        FragmentTransaction t = getSupportFragmentManager().beginTransaction().replace(R.id.create_story_container, new CreateBaseStoryFragment());
        t.addToBackStack(null);
        t.commit();
    }

    @Override
    public void setBaseData(Date happend, ArrayList<String> usersInvolved) {
        // Sets base data
        _storyData.setDateHappend(happend);
        _storyData.setInvolved(usersInvolved);

        // Writes story to DB
        Model.getInstance().writeStory(_storyData, _storyBookID, new Model.WriteStoryListener() {
            @Override
            public void onCompletion() {
                finish();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(), "Unable to write story", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Manages image captures
        if ((requestCode == ImageCreator.REQUEST_IMAGE_CAPTURE ||
                requestCode == ImageCreator.PICK_PHOTO) || ((requestCode >= NewBornCreator.REQUEST_BABY_IMAGE_CAPTURE && requestCode <= NewBornCreator.REQUEST_ROOM_IMAGE_CAPTURE ) ||
                ((requestCode >= NewBornCreator.BABY_PICK_PHOTO && requestCode <= NewBornCreator.ROOM_PICK_PHOTO)))) {
            extenedStoryFragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
