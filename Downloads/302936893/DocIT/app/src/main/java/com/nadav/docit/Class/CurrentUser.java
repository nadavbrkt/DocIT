package com.nadav.docit.Class;

import java.util.ArrayList;

/**
 * Created by Nadav on 6/11/2016.
 */
public class CurrentUser {
    private User _currentUser;

    private static CurrentUser ourInstance = new CurrentUser();

    public static CurrentUser getInstance() {
        return ourInstance;
    }

    private CurrentUser() {
        _currentUser = null;
    }

    public void setUser(User u) {
        _currentUser = u;
    }

    public void clear() {_currentUser = null; }

    public String getUid() {
        return _currentUser.getKey();
    }

    public final User getUser() {return _currentUser;};
    public void setStoryBooksID(ArrayList<String> storyBooks) {_currentUser.setStoryBookId(storyBooks);}
    public void addStoryBook(String storyBook) {_currentUser.addStoryBookID(storyBook);}
    public void removeStoryBook(String storyBook) {_currentUser.removeStoryBookID(storyBook);}
}
