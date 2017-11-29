package com.nadav.docit.Class.DataType;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Nadav on 6/8/2016.
 */
public class Image extends BlankStoryData {
    protected String _imgSrc;

    public Image() {
        _type = Types.IMAGE;
    }

    public Image(HashMap<String, String> map) {
        super(map);
        _imgSrc = map.get("_imgSrc");
    }

    public void setImg(String src) { _imgSrc = src;}
    public String getImgSrc() { return _imgSrc;}

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
    public String toString() {
        return "Image { _imgSrc = " +_imgSrc +"," +super.toString() + "}";
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

    @Override
    public void setInvolved(ArrayList<String> uids) {
        super.setInvolved(uids);
    }
}
