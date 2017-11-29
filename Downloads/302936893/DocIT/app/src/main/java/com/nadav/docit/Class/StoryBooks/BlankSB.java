package com.nadav.docit.Class.StoryBooks;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.nadav.docit.Class.StoryBook;
import com.nadav.docit.Class.StoryData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nadav on 6/13/2016.
 */
@IgnoreExtraProperties
public class BlankSB extends BaseSB {
    @Override
    public String getType() {
        if (_type == null) {
            return null;
        } else {
            return _type.name();
        }
    }

    @Override
    public void setType(String type) {
        if (type == null) {
            this._type = null;
        } else {
            this._type = Types.valueOf(type);
        }
    }

    public BlankSB() {
        _type = Types.BLANK;
    }

    public BlankSB(String key, String name, String desc, ArrayList<String> storyData) {
        super(key, name, desc);
        _type = Types.BLANK;
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
    public HashMap<String, StoryData> getStoryData() {
        return super.getStoryData();
    }


    @Override
    public void setStoryDataMap(HashMap<String, HashMap<String, String>> storyData) {
        super.setStoryDataMap(storyData);
    }

    @Exclude
    @Override
    public void setStoryData(HashMap<String, StoryData> storyData) {
        super.setStoryData(storyData);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Exclude
    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("key", _key);
        result.put("name", _name);
        result.put("desc", _desc);
        result.put("storyData", _storyData);
        result.put("type", _type.name());

        return result;
    }
}
