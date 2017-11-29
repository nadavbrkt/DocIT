package com.nadav.docit.Class.DataType;

import com.google.firebase.database.Exclude;
import com.nadav.docit.Class.StoryData;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Nadav on 6/8/2016.
 */
public abstract class Milestone extends BlankStoryData {
    protected Boolean _milestone = true;

    public Milestone() {
        _type = Types.MILESTONE;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Exclude
    @Override
    public void setTypeVal(Types typeVal) {
        super.setTypeVal(typeVal);
    }

    @Override
    public void setType(String stringType) {
        super.setType(stringType);
    }

    @Exclude
    @Override
    public Types getTypeVal() {
        return super.getTypeVal();
    }

    @Override
    public String getType() {
        return super.getType();
    }

    public Milestone(HashMap<String, String> map) {
        super(map);
    }

    @Override
    public void setKey(String key) {
        super.setKey(key);
    }

    @Override
    public void setDateHappend(Date happend) {
        super.setDateHappend(happend);
    }

    @Override
    public void setDateCreated(Date created) {
        super.setDateCreated(created);
    }

    @Override
    public void setCreatedBy(String uid) {
        super.setCreatedBy(uid);
    }

    @Override
    public void setInvolved(ArrayList<String> uids) {
        super.setInvolved(uids);
    }

    @Override
    public String getKey() {
        return super.getKey();
    }

    @Override
    public String getCreatedBy() {
        return super.getCreatedBy();
    }

    @Override
    public Date getCreated() {
        return super.getCreated();
    }

    @Override
    public Date getHappend() {
        return super.getHappend();
    }

    @Override
    public ArrayList<String> getInvolved() {
        return super.getInvolved();
    }
}
