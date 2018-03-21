package org.altervista.alecat.swimmanager.models;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alessandro Cattapan on 19/02/2018.
 */

public class CompetitionResultRelay extends CompetitionResult{

    private ArrayList<DocumentReference> mListSwimmerReference;

    // Void constructor for firebase database
    public CompetitionResultRelay(){}

    public CompetitionResultRelay(String name, String place, Date date, int poolLength,
                                  int race, int gender, int timingType,
                                  long time, DocumentReference competitionReference,
                                  ArrayList<DocumentReference> listSwimmerReference){
        super(name, place, date, poolLength, race, gender, timingType, time, competitionReference);
        mListSwimmerReference = listSwimmerReference;
    }

    public CompetitionResultRelay(Competition competition,
                                  int race, int gender, int timingType,
                                  long time, DocumentReference competitionReference,
                                  ArrayList<DocumentReference> listSwimmerReference){
        this(competition.getName(), competition.getPlace(), competition.getDate(), competition.getPoolLength(),
                race, gender, timingType, time, competitionReference,
                listSwimmerReference);
    }

    public CompetitionResultRelay(CompetitionResult competitionResult,
                                  ArrayList<DocumentReference> listSwimmerReference){
        this(competitionResult.getName(), competitionResult.getPlace(), competitionResult.getDate(), competitionResult.getPoolLength(),
                competitionResult.getRace(), competitionResult.getGender(), competitionResult.getTimingType(),
                competitionResult.getTime(), competitionResult.getCompetitionReference(),
                listSwimmerReference);
    }

    public ArrayList<DocumentReference> getSwimmerReference(){
        return mListSwimmerReference;
    }

    public void setSwimmerReference(ArrayList<DocumentReference> reference){
        mListSwimmerReference = reference;
    }

    public int getNumSwimmer(){
        return mListSwimmerReference.size();
    }

    public void addSwimmerReference(DocumentReference reference){
        mListSwimmerReference.add(reference);
    }

    // TODO: Maybe it is useless!
    public void removeSwimmerReference(DocumentReference reference){
        mListSwimmerReference.remove(reference);
    }
}
