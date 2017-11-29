package com.nadav.docit.Class;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nadav on 6/7/2016.
 */
public interface StoryBook {
    public enum Types {BASE, BLANK, NEWBORN, RELATIONSHIP};

    public String getKey();
    public void setKey(String key);
    public String getName();
    public void setName(String name);
    public String getDesc();
    public void setDesc(String desc);
    public String getType();
    public void setType(String type);
    public HashMap<String, StoryData> getStoryData();
    public void setStoryData(HashMap<String, StoryData> storyData);
    public Map<String, Object> toMap();
    public void addMembers(ArrayList<String> uids);

    void setStoryDataMap(HashMap<String, HashMap<String, String>> storyData);

    public void addMember(String uid);
}
