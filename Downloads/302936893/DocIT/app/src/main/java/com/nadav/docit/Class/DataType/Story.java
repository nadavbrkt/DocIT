package com.nadav.docit.Class.DataType;

import com.google.firebase.database.Exclude;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Nadav on 6/8/2016.
 */
public class Story extends Image {
    protected String _title = "";
    protected String _text;

    public Story(HashMap<String, String> map) {
        super(map);
        _text = map.get("_text");
        _title = map.get("_title");
    }

    public String getText() {
        return _text;
    }

    public void setText(String _text) {
        this._text = _text;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public Story() {
        _type = Types.STORY;
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


    public void setImgSrc(String src) {
        super.setImg(src);
    }

    @Override
    public String getImgSrc() {
        return super.getImgSrc();
    }

    @Override
    public String toString() {
        return "Story { _title = " + _title + ", _text = " + _text + super.toString() + "}";
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
