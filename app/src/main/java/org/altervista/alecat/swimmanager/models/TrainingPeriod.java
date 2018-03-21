package org.altervista.alecat.swimmanager.models;

import com.google.firebase.firestore.Exclude;

import org.altervista.alecat.swimmanager.interfaces.Selectable;

import java.util.Date;

/**
 * Created by Alessandro Cattapan on 19/02/2018.
 */

public class TrainingPeriod implements Selectable{

    private int mNumTraining;
    private int mNumTrainingDone;
    private Date mStartingDate;
    private Date mEndingDate;
    private int[] mDays;

    private boolean mSelected;

    // Void constructor for firebase database
    public TrainingPeriod(){}

    public TrainingPeriod(Date start, Date end, int[] days, int numTraining, int numTrainingDone){
        mStartingDate = start;
        mEndingDate = end;
        mDays = days;
        mNumTraining = numTraining;
        mNumTrainingDone = numTrainingDone;
    }

    public Date getStartingDate(){
        return mStartingDate;
    }

    public void setStartingDate(Date start){
        mStartingDate = start;
    }

    public Date getEndingDate(){
        return mEndingDate;
    }

    public void setEndingDate(Date end){
        mEndingDate = end;
    }

    public int getNumTraining(){
        return mNumTraining;
    }

    public void setNumTraining(int num){
        mNumTraining = num;
    }

    public int getNumTrainingDone(){
        return mNumTrainingDone;
    }

    public void setNumTrainingDone(int num){
        mNumTrainingDone = num;
    }

    public int[] getDays(){
        return mDays;
    }

    public void setDays(int[] days){
        mDays = days;
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
