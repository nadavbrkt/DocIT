package com.nadav.docit.Class;

import android.content.Intent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nadav on 6/7/2016.
 */
public class User {
    @JsonIgnore
    private String _key;

    private String _email;
    private String _fname;
    private String _lname;
    private ArrayList<String> _storyBookId;

    public String getKey() {
        return _key;
    }

    public void setKey(String uid) {
        this._key = uid;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String _email) {
        this._email = _email;
    }

    public String getFname() {
        return _fname;
    }

    public void setFname(String _fname) {
        this._fname = _fname;
    }

    public String getLname() {
        return _lname;
    }

    public void setLname(String _lname) {
        this._lname = _lname;
    }

    public ArrayList<String> getStoryBookId() {
        return _storyBookId;
    }

    public void setStoryBookId(ArrayList<String> _storyBookId) {
        this._storyBookId = _storyBookId;
    }

    @Override
    public String toString() {
        return "User{" +
                "_key='" + _key + '\'' +
                ", _email='" + _email + '\'' +
                ", _fname='" + _fname + '\'' +
                ", _lname='" + _lname + '\'' +
                ", _storyBookId=" + _storyBookId +
                '}';
    }

    public User() {
        _storyBookId = new ArrayList<>();
    }

    public User(String uid, String fname, String lname, String email, String[] storyBooks) {
        _key = uid;
        _fname = fname;
        _lname = lname;
        _email = email;
        _storyBookId = new ArrayList<>();

        if (storyBooks != null) {
            for (String bookId : storyBooks) {
                _storyBookId.add(bookId);
            }
        }
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("key", _key);
        result.put("fname", _fname);
        result.put("lname", _lname);
        result.put("email", _email);
        result.put("storybooks", _storyBookId);

        return result;
    }

    public void addStoryBookID(String id) {
        _storyBookId.add(id);
    }

    public void removeStoryBookID(String id) {
        _storyBookId.remove(id);
    }

    @Override
    public boolean equals(Object o) {
        try {
            User user = (User) o;
            return this.getKey().equals(user.getKey());
        } catch (Exception e) {
            return false;
        }

    }
}
