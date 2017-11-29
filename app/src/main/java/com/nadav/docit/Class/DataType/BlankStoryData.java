package com.nadav.docit.Class.DataType;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.nadav.docit.Class.StoryData;

import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nadav on 6/12/2016.
 */
@IgnoreExtraProperties
public abstract class BlankStoryData implements StoryData {
    protected String _key = "";
    protected Date _dateCreated = new Date();
    protected Date _dateHappend = new Date();
    protected String _createdBy = "";
    protected ArrayList<String> _usersInvolved = new ArrayList<String>();
    protected StoryData.Types _type;

    @Override
    public String toString() {
        return "BlankStoryData{" +
                "_key='" + _key + '\'' +
                ", _dateCreated=" + _dateCreated +
                ", _dateHappend=" + _dateHappend +
                ", _createdBy='" + _createdBy + '\'' +
                ", _usersInvolved=" + _usersInvolved +
                ", _type=" + _type +
                '}';
    }

    @Override
    @Exclude
    public void setTypeVal(Types typeVal) {
        _type = typeVal;
    }

    @Override
    public void setType(String stringType) {
        if (stringType == null) {
            _type = null;
        } else {
            _type = Types.valueOf(stringType);
        }
    }

    @Override
    @Exclude
    public Types getTypeVal() {
        if (_type == null) {
            return null;
        }

        return _type;
    }

    @Override
    public String getType() {
        if (_type == null) {
            return null;
        } else {
            return _type.name();
        }
    }

    public BlankStoryData() {
    }

    public BlankStoryData(HashMap<String,String> map) {
        _key = map.get("key");
        _dateCreated = java.sql.Date.valueOf("01-01-2016");
        _dateHappend = java.sql.Date.valueOf("01-01-2016");
        _createdBy = map.get("createdBy");
        _usersInvolved = new ArrayList<String>(Arrays.asList(map.get("involved").split(",")));
        _type = Types.valueOf(map.get("type"));
    }

    public void setKey(String key) { _key = key;}

    @Override
    public void setDateHappend(Date happend) { _dateHappend = happend;}

    @Override
    public void setDateCreated(Date created) { _dateCreated = created; }

    @Override
    public void setCreatedBy(String uid) {_createdBy = uid;}

    @Override
    public void setInvolved(ArrayList<String> uids) { _usersInvolved.addAll(uids); }

    public String getKey() {return _key;}

    @Override
    public String getCreatedBy() {return _createdBy;}

    @Override
    public Date getCreated() {return _dateCreated;}

    @Override
    public Date getHappend() {return _dateHappend;}

    @Override
    public ArrayList<String> getInvolved() {return _usersInvolved;}
}
