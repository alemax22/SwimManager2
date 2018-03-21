package org.altervista.alecat.swimmanager.models;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;

import org.altervista.alecat.swimmanager.interfaces.Selectable;

import java.util.Date;

/**
 * Created by Alessandro Cattapan on 19/02/2018.
 */

public class CompetitionResult extends Competition{

    private int mRace;
    private int mTimingType;
    private int mGender;
    private long mTime;
    private DocumentReference mCompetitionReference;

    // Void constructor for firebase database
    public CompetitionResult(){}

    public CompetitionResult(String name, String place, Date date, int poolLength,
                             int race, int gender, int timingType,
                             long time, DocumentReference reference){
        super(name, place, date, poolLength);
        mRace = race;
        mTimingType = timingType;
        mGender = gender;
        mTime = time;
        mCompetitionReference = reference;
    }

    public int getRace(){
        return mRace;
    }

    public void setRace(int race){
        mRace = race;
    }

    public int getTimingType(){
        return mTimingType;
    }

    public void setTimingType(int timingType){
        mTimingType = timingType;
    }

    public int getGender(){
        return mGender;
    }

    public void setGender(int gender){
        mGender = gender;
    }

    public long getTime(){
        return mTime;
    }

    public void setTime(long time){
        mTime = time;
    }

    public DocumentReference getCompetitionReference() {
        return mCompetitionReference;
    }

    public void setCompetitionReference(DocumentReference competitionReference) {
        mCompetitionReference = competitionReference;
    }
}
