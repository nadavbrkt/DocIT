package com.nadav.docit.Class;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by Nadav on 6/8/2016.
 */
public interface StoryData {
    public enum Types {IMAGE, QUOTE, STORY, MILESTONE};

    public void setKey(String key);
    public void setDateHappend(Date happend);
    public void setDateCreated(Date created);
    public void setCreatedBy(String uid);

    public void setInvolved(ArrayList<String> uids);
    public void setTypeVal(Types typeVal);
    public void setType(String stringType);

    public Types getTypeVal();
    public String getType();
    public String getKey();
    public String getCreatedBy();
    public Date getCreated();
    public Date getHappend();
    public ArrayList<String> getInvolved();

}
