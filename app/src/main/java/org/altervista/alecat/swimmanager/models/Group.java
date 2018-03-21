package org.altervista.alecat.swimmanager.models;

import com.google.firebase.firestore.Exclude;

import org.altervista.alecat.swimmanager.interfaces.Selectable;

/**
 * Created by Alessandro Cattapan on 19/02/2018.
 */

public class Group implements Selectable{

    private String mName;
    private int mNumSwimmer;

    private boolean mSelected = false;

    // Void constructor for firebase database
    public Group(){}

    public Group(String name, int num){
        mName = name;
        mNumSwimmer = num;
    }

    public Group(String name){
        this(name, 0);
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName = name;
    }

    public int getNumSwimmer(){
        return mNumSwimmer;
    }

    public void setNumSwimmer(int num){
        mNumSwimmer = num;
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
