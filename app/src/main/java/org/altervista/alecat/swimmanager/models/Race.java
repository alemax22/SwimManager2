package org.altervista.alecat.swimmanager.models;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import org.altervista.alecat.swimmanager.interfaces.Selectable;

import java.util.Map;
import java.util.Scanner;

/**
 * Created by Alessandro Cattapan on 19/02/2017.
 */

public class Race{

    private int mId;
    private int mLength;
    private String mStyle;
    private Map<String, Boolean> mName;
    private boolean mRelay;

    // Void constructor for firebase database
    public Race(){}

    public Race(int id, int length, String style, Map<String, Boolean> map, boolean relay){
        mId = id;
        mLength = length;
        mStyle = style;
        mName = map;
        mRelay = relay;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getLength() {
        return mLength;
    }

    public void setLength(int length) {
        mLength = length;
    }

    public String getStyle() {
        return mStyle;
    }

    public void setStyle(String style) {
        mStyle = style;
    }

    public Map<String, Boolean> getName() {
        return mName;
    }

    public void setName(Map<String, Boolean> name) {
        mName = name;
    }

    public boolean getRelay() {
        return mRelay;
    }

    public void setRelay(boolean relay) {
        mRelay = relay;
    }
}
