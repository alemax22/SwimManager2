package org.altervista.alecat.swimmanager.models;

import com.google.firebase.firestore.Exclude;

import org.altervista.alecat.swimmanager.interfaces.Selectable;

import java.util.Date;

/**
 * Created by Alessandro Cattapan on 20/02/2018.
 */

public class Competition implements Selectable{

    private String mName;
    private String mPlace;
    private Date mDate;
    private int mPoolLength;

    // Excluded from Firebase
    private boolean mSelected = false;

    // Void constructor for Firebase database
    public Competition() {}

    public Competition(String name, String place, Date date, int length) {
        mName = name;
        mPlace = place;
        mDate = date;
        mPoolLength = length;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getPoolLength() {
        return mPoolLength;
    }

    public void setPoolLength(int poolLength) {
        mPoolLength = poolLength;
    }

    @Exclude
    @Override
    public boolean isSelected() {
        return mSelected;
    }

    @Exclude
    @Override
    public void select() {
        mSelected = true;
    }

    @Exclude
    @Override
    public void unSelect() {
        mSelected = false;
    }
}
