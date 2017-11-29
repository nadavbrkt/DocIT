package com.nadav.docit.Activities.StoryCreators;

import android.content.Context;
import android.view.View;

import com.nadav.docit.Class.StoryData;

/**
 * Created by Nadav on 7/2/2016.
 */
public interface IStoryCreator {
    public View createView();
    public boolean validate();
    public StoryData populateStoryData();
}
