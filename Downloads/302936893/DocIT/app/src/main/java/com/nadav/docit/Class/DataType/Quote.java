package com.nadav.docit.Class.DataType;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nadav on 6/8/2016.
 */
@IgnoreExtraProperties
public class Quote extends BlankStoryData {
    protected String _qtext;
    protected String _saidBy;


    public Quote() {
        _type = Types.QUOTE;
    }

    public Quote(HashMap<String, String> map) {
        super(map);
        _qtext = map.get("qtext");
        _saidBy = map.get("saidBy");
    }

    public void setQtext(String text) { _qtext = text; }
    public String getQtext() {return _qtext;}

    public void setSaidBy(String uid) {_saidBy = uid;}
    public String getSaidBy() {return _saidBy;}

    @Override
    public String toString() {
        return "Quote { _qtext = " + _qtext + ", saidBy = " + _saidBy +  super.toString();
                

    }

    @Override
    @Exclude
    public void setTypeVal(Types typeVal) {
        super.setTypeVal(typeVal);
    }

    @Override
    public void setType(String stringType) {
        super.setType(stringType);
    }

    @Override
    @Exclude
    public Types getTypeVal() {
        return super.getTypeVal();
    }

    @Override
    public String getType() {
        return super.getType();
    }

    @Override
    public void setKey(String did) {
        super.setKey(did);
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
