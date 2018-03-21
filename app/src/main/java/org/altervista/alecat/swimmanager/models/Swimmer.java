package org.altervista.alecat.swimmanager.models;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import org.altervista.alecat.swimmanager.interfaces.Selectable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Alessandro Cattapan on 23/08/2017.
 */
// This class describes a Swimmer
public class Swimmer implements Selectable{
    // General Info
    private String mName;
    private String mSurname;
    private Date mBirthday;
    private int mGender;
    private int mCategory;
    private int mMembership;
    private Date mTimestamp;

    // Excluded from firebase
    private boolean mSelected = false;

    // Void constructor for firebase database
    public Swimmer(){}

    // Real Object's constructor
    public Swimmer (String name, String surname, Date birthday, int gender, int category, int membership) {
        mName = name;
        mSurname = surname;
        mBirthday = birthday;
        mGender = gender;
        mCategory = category;
        mMembership = membership;
    }

    public String getName() { return mName; }

    public void setName(String name) { mName = name; }

    public String getSurname() { return mSurname; }

    public void setSurname(String surname) { mSurname = surname; }

    public Date getBirthday() { return mBirthday; }

    public void setBirthday(Date birthday) { mBirthday = birthday; }

    public int getGender() { return mGender; }

    public void setGender (int gender) { mGender = gender; }

    public int getCategory() { return mCategory; }

    public void setCategory (int category) { mCategory = category; }

    public int getMembership() { return mMembership; }

    public void setMembership (int membership) { mMembership = membership; }

    @ServerTimestamp
    public Date getTimestamp() { return mTimestamp; }

    public void setTimestamp(Date timestamp) { mTimestamp = timestamp; }

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
