package com.nadav.docit.Class.StoryBooks;

import com.google.firebase.database.Exclude;
import com.nadav.docit.Class.StoryData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nadav on 6/12/2016.
 */
public class RelationshipSB extends BaseSB {
    public RelationshipSB(String key, String name, String desc, ArrayList<String> storyData) {
        super(key, name, desc);
        setTypeVal(Types.RELATIONSHIP);
    }

    public RelationshipSB() {
        _type = Types.RELATIONSHIP;
    }

    @Override
    public String getKey() {
        return super.getKey();
    }

    @Override
    public void setKey(String key) {
        super.setKey(key);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getDesc() {
        return super.getDesc();
    }

    @Override
    public void setDesc(String desc) {
        super.setDesc(desc);
    }

    @Override
    public String getType() {
        return super.getType();
    }

    @Override
    public HashMap<String, StoryData> getStoryData() {
        return super.getStoryData();
    }

    @Override
    @Exclude
    public void setStoryData(HashMap<String, StoryData> storyData) {
        super.setStoryData(storyData);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
