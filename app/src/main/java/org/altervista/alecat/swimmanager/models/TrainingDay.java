package org.altervista.alecat.swimmanager.models;

import com.google.firebase.firestore.Exclude;

import org.altervista.alecat.swimmanager.interfaces.Selectable;

import java.util.Date;

/**
 * Created by Alessandro Cattapan on 19/02/2018.
 */

public class TrainingDay implements Selectable{

    private Date mDay;
    private int mIdTrainingInDay;
    private boolean mDone;

    private boolean mSelected = false;

    // Void constructor for firebase database
    public TrainingDay(){}

    public TrainingDay(Date day, int id){
        mDay = day;
        mIdTrainingInDay = id;
        mDone = false;
    }

    public TrainingDay(Date day, int id, boolean done){
        mDay = day;
        mIdTrainingInDay = id;
        mDone = done;
    }

    public Date getDay(){
        return mDay;
    }

    public void setDay(Date day){
        mDay = day;
    }

    public int getIdTrainingInDay(){
        return  mIdTrainingInDay;
    }

    public void setIdTrainingInDay(int id){
        mIdTrainingInDay = id;
    }

    public boolean getDone(){
        return mDone;
    }

    public void setDone(boolean done){
        mDone = done;
    }

    /**
     * This method marks a TrainingDay done
     */
    public void done(){
        mDone = true;
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
