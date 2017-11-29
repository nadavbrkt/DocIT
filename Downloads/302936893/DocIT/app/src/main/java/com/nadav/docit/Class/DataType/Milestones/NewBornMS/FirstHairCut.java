package com.nadav.docit.Class.DataType.Milestones.NewBornMS;

import com.nadav.docit.Class.DataType.Milestone;

/**
 * Created by Nadav on 6/9/2016.
 */
public class FirstHairCut extends Milestone {
    protected String _imgBefore;
    protected String _imgAfter;

    public String getImgBefore() {
        return _imgBefore;
    }

    public void setImgBefore(String _imgBefore) {
        this._imgBefore = _imgBefore;
    }

    public String getImgAfter() {
        return _imgAfter;
    }

    public void setImgAfter(String _imgAfter) {
        this._imgAfter = _imgAfter;
    }

    public String getText() {
        return _text;
    }

    public void setText(String _text) {
        this._text = _text;
    }

    String _text;
}
