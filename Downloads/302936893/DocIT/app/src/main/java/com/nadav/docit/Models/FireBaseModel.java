package com.nadav.docit.Models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.Toast;

import com.google.android.gms.dynamite.descriptors.com.google.android.gms.firebase_database.ModuleDescriptor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nadav.docit.Class.CurrentUser;
import com.nadav.docit.Class.DataType.Image;
import com.nadav.docit.Class.DataType.Milestone;
import com.nadav.docit.Class.DataType.Milestones.NewBornMS.NewBorn;
import com.nadav.docit.Class.DataType.Quote;
import com.nadav.docit.Class.DataType.Story;
import com.nadav.docit.Class.StoryBook;
import com.nadav.docit.Class.StoryBooks.BaseSB;
import com.nadav.docit.Class.StoryBooks.BlankSB;
import com.nadav.docit.Class.StoryBooks.NewBornSB;
import com.nadav.docit.Class.StoryBooks.RelationshipSB;
import com.nadav.docit.Class.StoryData;
import com.nadav.docit.Class.User;
import com.nadav.docit.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;

/**
 * Created by Nadav on 7/23/2016.
 */
public class FireBaseModel {
    private static final String TAG = "FIREBASEMODEL";
    private Context _context;
    private DatabaseReference _dbRef;
    private String _lastUpdateTime;

    FireBaseModel (Context context) {
        _context = context;
        _dbRef = FirebaseDatabase.getInstance().getReference();

        // Constantly checks for updates
        lastUsersUpdate(new Model.LastUsersUpdateListener() {
            @Override
            public void onComplete(String time) {
                _lastUpdateTime = time;
            }

            @Override
            public void onError(Exception e) {
                _lastUpdateTime = null;
            }
        });
    }

    // Return last update
    public String getLastUpdateTime() {return _lastUpdateTime;};

    // Login to server
    public void login(final String userName, String password, final Model.AuthListener listener) {
        // Tries to authenticate
        FirebaseAuth.getInstance().signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Checks if authentication succeed
                if (task.isSuccessful()){
                    if (userName.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        getUserData(FirebaseAuth.getInstance().getCurrentUser().getUid(), new Model.GetUserDataListener() {
                            @Override
                            public void onCompletion(User user) {
                                CurrentUser.getInstance().setUser(user);
                                listener.onDone(user);
                            }

                            @Override
                            public void onError(Exception e) {
                                onError(e);
                            }
                        });
                    } else {
                        listener.onError(task.getException());
                    }
                } else {
                    listener.onError(task.getException());
                }
            }
        });
    }

    // Register a new user
    public void signUp(final String email, String password, final String fname, final String lname, final Model.SignUpListener listener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    String uid = task.getResult().getUser().getUid();
                    final User user = new User(uid, fname, lname, email, null);

                    // Adds user data
                    _dbRef.child(Constants.FB.UserScheme.Name).child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
                                _dbRef.child(Constants.FB.LastUpdate).setValue(format.format(new Date()));
                                listener.onDone(user);
                            } else {
                                listener.onError(task.getException());
                            }
                        }
                    });
                } else {
                    Toast.makeText(_context.getApplicationContext(), "Unable to create user", Toast.LENGTH_SHORT).show();
                    listener.onError(task.getException());
                }
                }
            });
    }

    // Get all users
    public void getAllUsers(final Model.ListenerForAllUsers listenerForAllUsers) {
        _dbRef.child(Constants.FB.UserScheme.Name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new LinkedList<User>();
                for (DataSnapshot u: dataSnapshot.getChildren()) {
                    users.add(u.getValue(User.class));
                }

                listenerForAllUsers.onComplition(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listenerForAllUsers.onError(databaseError.toException());
            }
        });
    }

    // Last users update
    public void lastUsersUpdate(final Model.LastUsersUpdateListener listener) {
        _dbRef.child(Constants.FB.LastUpdate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    String time = (new SimpleDateFormat(Constants.DATE_FORMAT)).format(new Date());
                    _dbRef.child(Constants.FB.LastUpdate).setValue(time);
                    listener.onComplete(time);
                } else {
                    listener.onComplete(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError.toException());
            }
        });
    }

    // Logout
    public void logOut() {
        FirebaseAuth.getInstance().signOut();
        CurrentUser.getInstance().clear();
    }

    // Get storybook data
    public void getStoryBook(String storyBookId, final Model.StoryBookListener listener) {
        _dbRef.child(Constants.FB.StoryBooksScheme.Name).child(storyBookId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StoryBook sb = Model.getInstance().getSBookFromDataSnapshot(dataSnapshot);
                listener.onStoryBookLoaded(sb);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError.toException());
            }
        });
    }

    // Load story book data (StoryDatas)
    public void loadStoryBooks(final Model.StoryBookLoaderListener listener) {
        _dbRef.child(Constants.FB.UserScheme.Name).child(CurrentUser.getInstance().getUid())
                .child(Constants.FB.UserScheme.StoryBooksID).addChildEventListener(new ChildEventListener() {
            HashMap<String, ValueEventListener> _valueEventListeners = new HashMap<>();

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Adds to user books
                String storyBookID = (String) dataSnapshot.getValue();
                CurrentUser.getInstance().addStoryBook(storyBookID);

                // Creates listener
                _valueEventListeners.put(storyBookID, new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot == null) {
                            return;
                        }

                        listener.addStoryBook(Model.getInstance().getSBookFromDataSnapshot(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, " Error getting story");
                    }
                });

                // Adds listener to data
                _dbRef.child(Constants.FB.StoryBooksScheme.Name).child(storyBookID)
                        .addValueEventListener(_valueEventListeners.get(storyBookID));
            }

            // Should not happend
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prvID) {
                Log.e(TAG,"Error, changed ID of story");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String storyBookID = (String) dataSnapshot.getValue();
                CurrentUser.getInstance().removeStoryBook(storyBookID);

                // Removes listener
                _dbRef.child(Constants.FB.StoryBooksScheme.Name).child(storyBookID)
                        .removeEventListener(_valueEventListeners.get(storyBookID));

                // Removes from adapter
                listener.removeStoryBook(storyBookID);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                onChildRemoved(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError.toException());
                Log.d(TAG, "Error getting data");
            }
        });
    }

    // Creates a new storybook
    public void createStoryBook(String name, String desc, StoryBook.Types type,
                                final ArrayList<String> members, final Model.StoryBookCreatorListener listener) {
        StoryBook storyBook = null;
        DatabaseReference storyBookPush = _dbRef.child(Constants.FB.StoryBooksScheme.Name).push();
        switch (type) {
            case BASE:
                break;
            case BLANK:
                storyBook = new BlankSB(storyBookPush.getKey(),name, desc,null);
                break;
            case NEWBORN:
                storyBook = new NewBornSB(storyBookPush.getKey(),name, desc,null);
                break;
            case RELATIONSHIP:
                storyBook = new RelationshipSB(storyBookPush.getKey(),name, desc,null);
                break;
        }

        final StoryBook sb = storyBook;
        storyBookPush.setValue(sb).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onStoryBookCreated(sb, members);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onError(e);
            }
        });
    }

    // Adds a member to storybook
    public void addMemberToStoryBook(final String storyBookID, final String member, final Model.AddMembersToStoryBookListener listener) {
        DatabaseReference userStoryBooks = FirebaseDatabase.getInstance().getReference().child(Constants.FB.UserScheme.Name)
                .child(member).child(Constants.FB.UserScheme.StoryBooksID);

        userStoryBooks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // Checks if allready inside
                ArrayList<String> storyBooks = new ArrayList<String>();
                for (DataSnapshot storyBook:dataSnapshot.getChildren()) {
                    storyBooks.add(storyBook.getValue().toString());

                    if (storyBook.getValue().toString().equals(storyBookID)) {
                        listener.onMemberAdded(member);
                        return;
                    }
                }

                // Validate update
                storyBooks.add(storyBookID);
                dataSnapshot.getRef().setValue(storyBooks).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            listener.onMemberAdded(member);
                        } else {
                            listener.onError(task.getException());
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError.toException());
            }

        });
    }

    // Add multiple members to storybook
    public void addMembersToStoryBook(final String storyBookID, ArrayList<String> members, final Model.AddMembersToStoryBookListener listener) {
        final HashMap<String, Boolean> chkTable = new HashMap<>();

        for (String member : members) {
            chkTable.put(member, false);
            addMemberToStoryBook(storyBookID, member ,new Model.AddMembersToStoryBookListener() {
                @Override
                public void onMembersAdded() {
                    return;
                }

                @Override
                public void onMemberAdded(String member) {
                    chkTable.put(member, true);
                }

                @Override
                public void onError(Exception e) {
                    listener.onError(e);
                }
            });
        }

        // Validate all entered
        for (String member : members) {
            if (!(chkTable.get(member))) {
                listener.onError(new Exception("Not all members added"));
            }
        }

        listener.onMembersAdded();
    }

    // Writes a story to db
    public void writeStory(final StoryData storyData, final String storyBookID, final Model.WriteStoryListener listener) {
        DatabaseReference fb = _dbRef.child(Constants.FB.StoryBooksScheme.Name)
                .child(storyBookID).child(Constants.FB.StoryBooksScheme.data).push();

        storyData.setKey(fb.getKey());
        fb.setValue(storyData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (storyData instanceof Image)
                        Model.getInstance().uploadImgFromDisk(((Image) storyData).getImgSrc());

                    listener.onCompletion();
                } else {
                    listener.onError(task.getException());
                }
            }
        });
    }

    // Get users data
    public void getUserData(final String uid, final Model.GetUserDataListener listener ) {
        _dbRef.child(Constants.FB.UserScheme.Name).child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    User u = dataSnapshot.getValue(User.class);
                    u.setKey(dataSnapshot.getKey());

                    // Sends data to listener
                    listener.onCompletion(u);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError.toException());
            }
        });
    }

    // Gets users names
    public void getUsersNames(final ArrayList<String> uids, final Model.GetUsersNames listener) {
        Model.GetUserDataListener privateListener = new Model.GetUserDataListener() {
            ArrayList<String> names = new ArrayList<>();
            int size = 0;
            @Override
            public void onCompletion(User user) {
                names.add(user.getFname() + " " + user.getLname());
                size++;
                if (size == uids.size()) {
                    listener.onComplition(names);
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);
            }
        };

        for (String uid : uids) {
            getUserData(uid, privateListener);
        }
    }

    // Get storybook data
    public void getStoryBookData(final String storyBookID, final Model.GetStoryBookDataListener listener) {
        _dbRef.child(Constants.FB.StoryBooksScheme.Name).child(storyBookID)
                .child(Constants.FB.StoryBooksScheme.data).orderByChild(Constants.FB.StoryBooksScheme.date)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Add to adapter
                listener.addStoryData(Model.getInstance().getSDataFromDataSnapshot(dataSnapshot));
            }

            // Should not happend
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prvID) {
                Log.e(TAG,"Error, changed ID of story");
                listener.addStoryData(Model.getInstance().getSDataFromDataSnapshot(dataSnapshot));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String storyBookID = (String) dataSnapshot.getValue();
                Log.d(TAG," remove sd=" + storyBookID);

                // Removes listener
                listener.removeStoryData(storyBookID);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG," move");
                onChildRemoved(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError.toException());
                Log.d(TAG, " Error getting data");
            }
        });
    }

    // Get story data
    public void getStoryData(final String storyBookID, final String storyDataID, final Model.GetStoryDataListener listener) {
        _dbRef.child(Constants.FB.StoryBooksScheme.Name).child(storyBookID)
                .child(Constants.FB.StoryBooksScheme.data).child(storyDataID).addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.changeStoryDataListener(Model.getInstance().getSDataFromDataSnapshot(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError.toException());
            }
        });
    }

    // Get users in storybook
    public void getUsers(final String storyBookID, final Model.GetUsersListener listener) {
        if (storyBookID == null) {
            _dbRef.child(Constants.FB.UserScheme.Name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<User> users = new ArrayList<User>();
                    for (DataSnapshot userData: dataSnapshot.getChildren()) {
                        users.add(userData.getValue(User.class));
                    }

                    listener.onUserGer(users);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listener.onError(databaseError.toException());
                }
            });
        } else {
            _dbRef.child(Constants.FB.UserScheme.Name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<User> users = new ArrayList<User>();
                    for (DataSnapshot userData : dataSnapshot.getChildren()) {
                        if (userData.child(Constants.FB.UserScheme.StoryBooksID).exists()) {
                            if (userData.child(Constants.FB.UserScheme.StoryBooksID).child(storyBookID).exists()) {
                                users.add(userData.getValue(User.class));
                            }
                        }
                    }

                    listener.onUserGer(users);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listener.onError(databaseError.toException());
                }
            });
        }
    }
}