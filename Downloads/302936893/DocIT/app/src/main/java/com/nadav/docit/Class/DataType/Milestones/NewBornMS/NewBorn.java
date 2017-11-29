package com.nadav.docit.Class.DataType.Milestones.NewBornMS;

import com.google.firebase.database.Exclude;
import com.nadav.docit.Class.DataType.Milestone;
import com.nadav.docit.Class.StoryData;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nadav on 6/8/2016.
 */
public class NewBorn extends Milestone {
    protected String _firstPar;
    protected String _second_Par;
    protected String _thirdPar;
    protected String _imgMomDad;
    protected String _imgBaby;
    protected String _imgChildRoom;

    public String getFirstPar() {
        return _firstPar;
    }

    public void setFirstPar(String firstPar) {
        _firstPar = firstPar;
    }

    public String getSecondPar() {
        return _second_Par;
    }

    public void setSecond_Par(String second_Par) {
        _second_Par = second_Par;
    }

    public String getThirdPar() {
        return _thirdPar;
    }

    public void setThirdPar(String thirdPar) {
        _thirdPar = thirdPar;
    }

    public String getImgMomDad() {
        return _imgMomDad;
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

    public void setImgMomDad(String imgMomDad) {
        _imgMomDad = imgMomDad;
    }

    public String getImgBaby() {
        return _imgBaby;
    }

    public void setImgBaby(String imgBaby) {
        _imgBaby = imgBaby;
    }

    public String getImgChildRoom() {
        return _imgChildRoom;
    }

    public void setImgChildRoom(String imgChildRoom) {
        _imgChildRoom = imgChildRoom;
    }

    public NewBorn() {

    }
}
