package com.nadav.docit.Class.StoryBooks;

import android.provider.ContactsContract;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.fasterxml.jackson.databind.type.TypeParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nadav.docit.Class.DataType.Image;
import com.nadav.docit.Class.DataType.Quote;
import com.nadav.docit.Class.DataType.Story;
import com.nadav.docit.Class.StoryBook;
import com.nadav.docit.Class.StoryData;
import com.nadav.docit.Constants;
import com.nadav.docit.Models.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nadav on 6/12/2016.
 */
public abstract class BaseSB implements StoryBook {
    @JsonIgnore
    protected String _key = "";
    protected String _name = "";
    protected String _desc = "";
    protected Types _type = null;

    @JsonIgnore
    protected HashMap<String, StoryData> _storyData = new HashMap<>();

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("key", _key);
        result.put("name", _name);
        result.put("desc", _desc);
        result.put("type", _type.name());
        result.put("storyData", _storyData);

        return result;
    }

    public String getKey() {
        return _key;
    }

    public void setKey(String key) {
        _key = key;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getDesc() {
        return _desc;
    }

    public void setDesc(String desc) {
        _desc = desc;
    }

    @Exclude
    public Types getTypeVal() {
        return _type;
    }

    @Exclude
    public void setTypeVal(Types type) {
        _type = type;
    }

    public String getType() {
        if (_type == null) {
            return null;
        } else {
            return _type.name();
        }
    }

    public void setType(String type) {
        if (type == null) {
            _type = null;
        } else {
            _type = Types.valueOf(type);
        }
    }

    @Override
    public HashMap<String, StoryData> getStoryData() {
        return _storyData;
    }

    @Override
    public void setStoryData(HashMap<String, StoryData> storyData) {
        _storyData = storyData;
    }

    @Override
    public void setStoryDataMap(HashMap<String, HashMap<String, String>> storyData) {
        for (String sd: storyData.keySet()) {
            switch (StoryData.Types.valueOf(storyData.get(sd).get("type"))) {
                case IMAGE:
                    Log.d("GGG", storyData.get(sd).toString());
                    _storyData.put(sd, new Image(storyData.get(sd)));

                    break;
                case QUOTE:
                    Log.d("GGG", storyData.get(sd).toString());
                    _storyData.put(sd, new Quote(storyData.get(sd)));
                    break;
                case STORY:
                    _storyData.put(sd, new Story(storyData.get(sd)));
                    break;
                case MILESTONE:
                    break;
            }
        }
    }

    public BaseSB(String key, String name, String desc) {
        _key = key;
        _name = name;
        _desc = desc;
        _storyData = new HashMap<>();
        _type = Types.BASE;
    }

    @Override
    public String toString() {
        return "BaseSB{" +
                "_key='" + _key + '\'' +
                ", _name='" + _name + '\'' +
                ", _desc='" + _desc + '\'' +
                ", _type='" + _type.name() + '\'' +
                ", _storyData=" + _storyData +
                '}';
    }

    @Override
    public void addMember(String uid) {
        Model.getInstance().addMemberToStoryBook(_key, uid, new Model.AddMembersToStoryBookListener() {
            @Override
            public void onMembersAdded() {
                return;
            }

            @Override
            public void onMemberAdded(String member) {
                return;
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void addMembers(ArrayList<String> uids) {
        Model.getInstance().addMembersToStoryBook(_key, uids, new Model.AddMembersToStoryBookListener() {
            @Override
            public void onMembersAdded() {
                return;
            }

            @Override
            public void onMemberAdded(String member) {
                return;
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }


    BaseSB(){
    }
}
