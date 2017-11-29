package com.nadav.docit.Models;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.AbsListView;

import com.google.firebase.database.DataSnapshot;
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
import com.nadav.docit.DocIT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nadav on 7/23/2016.
 */
public class Model {
    private final static Model instance = new Model();
    Context context;
    FireBaseModel _fireBaseModel;
    CloudinaryModel _cloudinaryModel;
    SqlModel _sqlModel;
    ImgManager _imgManager;

    private Model(){
        context = DocIT.getAppContext();
        _fireBaseModel = new FireBaseModel(DocIT.getAppContext());
        _cloudinaryModel = new CloudinaryModel(DocIT.getAppContext());
        _imgManager = new ImgManager();
        _sqlModel = SqlModel.getInstance();
    }

    public static Model getInstance(){
        return instance;
    }

    public Bitmap scaleImage(Bitmap tmp, int imgWidthF, int imgHeightF) {
        return _imgManager.scaleImage(tmp, imgWidthF, imgHeightF);
    }

    /*
    ==========================================================================
                                    Listeners
    ==========================================================================
    */

    public interface BaseListener {
        void onError(Exception e);
    }

    public interface AuthListener extends Model.BaseListener{
        void onDone(User u);
    }

    public interface SignUpListener extends Model.BaseListener{
        void onDone(User u);
    }

    public interface StoryBookLoaderListener extends Model.BaseListener {
        void addStoryBook(StoryBook sb);
        void removeStoryBook(String sbId);
    }

    public interface StoryBookCreatorListener extends Model.BaseListener {
        void onStoryBookCreated(StoryBook sb, ArrayList<String> members);
    }

    public interface AddMembersToStoryBookListener extends Model.BaseListener {
        void onMembersAdded();
        void onMemberAdded(final String member);
    }

    public interface WriteStoryListener extends Model.BaseListener {
        void onCompletion();
    }

    public interface GetUserDataListener extends Model.BaseListener {
        void onCompletion(User user);
    }

    public interface GetStoryBookDataListener extends Model.BaseListener {
        void addStoryData(StoryData storyData);
        void removeStoryData(String sdId);
    }

    public interface GetStoryDataListener extends Model.BaseListener {
        void changeStoryDataListener(StoryData storyData);
    }

    public interface GetUsersListener extends Model.BaseListener {
        void onUserGer(ArrayList<User> users);
    }

    public interface GetStoredImageListener extends Model.BaseListener {
        void onImageStored(String name, Bitmap image);
    }

    public interface GetImageListener extends Model.BaseListener{
        void onImageLoaded(String name, Bitmap image);
    }

    public interface GetUsersNames extends Model.BaseListener {
        void onComplition(ArrayList<String> names);
    }

    public interface StoryBookListener extends BaseListener {
        void onStoryBookLoaded(StoryBook sb);
    }

    public interface LastUsersUpdateListener extends BaseListener{
        public void onComplete(String time) ;
    }

    public interface ListenerForAllUsers extends BaseListener{
        public void onComplition(List<User> users);
    }
    /*
    ==========================================================================
                                    FireBase
    ==========================================================================
    */

    public void getStoryBook (String storyBookId, final Model.StoryBookListener listener) {
        _fireBaseModel.getStoryBook(storyBookId, listener);
    }

    // Read datasnap to Story data
    public StoryData getSDataFromDataSnapshot(DataSnapshot snapshot) {
        StoryData sd = null;
        // Checks type
        if (snapshot != null) {
            switch (StoryData.Types.valueOf(snapshot.child(Constants.FB.StoryBooksScheme.Type).getValue().toString())) {
                case IMAGE:
                    sd = snapshot.getValue(Image.class);
                    break;
                case QUOTE:
                    sd = snapshot.getValue(Quote.class);
                    break;
                case STORY:
                    sd = snapshot.getValue(Story.class);
                    break;
                case MILESTONE:
                    sd = snapshot.getValue(NewBorn.class);
                    break;
            }
        }

        return sd;
    }

    // Read datasnap to Story book
    public StoryBook getSBookFromDataSnapshot(DataSnapshot snapshot) {
        StoryBook sb = null;
        // Checks type
        switch (StoryBook.Types.valueOf(snapshot.child("type").getValue().toString())) {
            case BASE:
                break;
            case BLANK:
                sb = snapshot.getValue(BlankSB.class);
                break;
            case NEWBORN:
                sb = snapshot.getValue(NewBornSB.class);
                break;
            case RELATIONSHIP:
                sb = snapshot.getValue(RelationshipSB.class);
                break;
        }
        return sb;
    }

    public void login(String userName, String password, AuthListener listener) {
        _fireBaseModel.login(userName, password, listener);
    }

    public void logOut() {
        _fireBaseModel.logOut();
    }

    public void signUp(final String email, String password, final String fname, final String lname ,
                       SignUpListener listener) {
        _fireBaseModel.signUp(email, password, fname, lname, listener);
    }

    public void loadStoryBooks(final Model.StoryBookLoaderListener listener) {
        _fireBaseModel.loadStoryBooks(listener);
    }

    public void createStoryBook(String name, String desc, StoryBook.Types type,
                final ArrayList<String> members, final Model.StoryBookCreatorListener listener) {
        _fireBaseModel.createStoryBook(name, desc, type, members, listener);
    }

    public void addMemberToStoryBook(final String storyBookID, final String member, final Model.AddMembersToStoryBookListener listener) {
        _fireBaseModel.addMemberToStoryBook(storyBookID, member, listener);
    }

    public void addMembersToStoryBook(final String storyBookID, ArrayList<String> members, final Model.AddMembersToStoryBookListener listener) {
        _fireBaseModel.addMembersToStoryBook(storyBookID, members, listener);
    }

    public void writeStory(final StoryData storyData, final String storyBookID, final Model.WriteStoryListener listener) {
        _fireBaseModel.writeStory(storyData, storyBookID, listener);
    }

    // Get user data using cache
    public void getUserData(final String uid, final Model.GetUserDataListener listener ) {
        final usersLoader privateListener = new usersLoader() {
            @Override
            public void onDoneLoadingUsers() {
                listener.onCompletion(UsersSql.getUserById(SqlModel.getInstance().getReadbleDB(), uid));
            }
        };

        final String time = _fireBaseModel.getLastUpdateTime();
        if (time.equals(UsersSql.getLastUpdateDate(SqlModel.getInstance().getReadbleDB()))) {
            privateListener.onDoneLoadingUsers();
        } else {
            _fireBaseModel.getAllUsers(new ListenerForAllUsers() {
                @Override
                public void onComplition(List<User> users) {
                    UsersSql.reWriteDB(users, time);
                    privateListener.onDoneLoadingUsers();
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void getStoryBookData(final String storyBookID, final GetStoryBookDataListener listener) {
        _fireBaseModel.getStoryBookData(storyBookID, listener);
    }

    // Gets users shared this storybook
    public void getUsers(final String storyBookID, final Model.GetUsersListener listener) {
        _fireBaseModel.getUsers(storyBookID, listener);
    }

    public interface usersLoader {
        public void onDoneLoadingUsers();
    }

    // Get users names using cache
    public void getUsersNames(final ArrayList<String> uids,final Model.GetUsersNames listener ) {
        final usersLoader privateListener = new usersLoader() {
            @Override
            public void onDoneLoadingUsers() {
                ArrayList<String> names = new ArrayList<>();

                for (String uid :uids) {
                    User u = UsersSql.getUserById(SqlModel.getInstance().getReadbleDB(), uid);
                    names.add("" + u.getFname() + " " + u.getLname());
                }

                listener.onComplition(names);
            }
        };

        final String time = _fireBaseModel.getLastUpdateTime();
        if (!(time.equals(UsersSql.getLastUpdateDate(SqlModel.getInstance().getReadbleDB())))) {
            _fireBaseModel.getAllUsers(new ListenerForAllUsers() {
                @Override
                public void onComplition(List<User> users) {
                    UsersSql.reWriteDB(users, time);
                    privateListener.onDoneLoadingUsers();
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            privateListener.onDoneLoadingUsers();
        }
    }

     /*
    ==========================================================================
                                    Cloudinary
    ==========================================================================
    */

    public Bitmap downloadImageFromCloud(final String imgName) {
        return _cloudinaryModel.getImage(imgName);
    }

    /*
    ==========================================================================
                                    FileSystem
    ==========================================================================
    */

    public File createTmpImageFile() throws IOException {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        return new File(Constants.IMG_TMP + "/TMP_" + format.format(new Date()) + ".png");
    }

    public void readImageFromFile(final String imgName, final Model.GetStoredImageListener listener) {
        File file = new File(Constants.IMG_DIR, "/" + imgName + ".png");
        FileInputStream imgInput = null;
        try {
            imgInput = new FileInputStream(file);

            listener.onImageStored(imgName, BitmapFactory.decodeStream(imgInput));
        } catch (FileNotFoundException e) {
            listener.onError(e);
        }
    }

    public void writeImageToStorage(final String imgName, final Bitmap bmp, final Model.GetStoredImageListener listener) {
        File imgFile = new File(Constants.IMG_DIR + "/" + imgName + ".png");
        if (!(imgFile.exists())) {
            FileOutputStream imgOut = null;
            try {
                imgOut = new FileOutputStream(imgFile);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, imgOut);
                imgOut.flush();
                imgOut.close();

                listener.onImageStored(imgName, bmp);
            } catch (FileNotFoundException e) {
                listener.onError(e);
            } catch (IOException e) {
                listener.onError(e);
            }

        }
    }

    /*
    ==========================================================================
                                    CACHE
    ==========================================================================
    */

    public void setImage(String image, final Model.GetImageListener listener) {
        _imgManager.setImage(image, listener);
    }

    public void setImage(String image, final int size, final Model.GetImageListener listener) {
        _imgManager.setImage(image, size, listener);
    }

    public void setImage(final String image, final int width, final int height, final Model.GetImageListener listener) {
        _imgManager.setImage(image, width, height, listener);
    }

    // Upload image using cache
    public void uploadImage(String name, Bitmap imgBmp) {
        imgBmp = scaleImage(imgBmp, Constants.IMG_WIDTH);
        _imgManager.addImageToCache(name, imgBmp);

        writeImageToStorage(name, imgBmp, new GetStoredImageListener() {
            @Override
            public void onImageStored(String name, Bitmap image) {
                _cloudinaryModel.uploadImg(name);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public Bitmap scaleImage(Bitmap src, int size) {
        return _imgManager.scaleImage(src, size);
    }

    public void uploadImgFromDisk(String imgSrc) {
        _cloudinaryModel.uploadImageFromDisk(imgSrc);
    }

    public Bitmap getImageFromTmp(File tmp) throws IOException {
        return _imgManager.getImageFromTmp(tmp);
    }

}
