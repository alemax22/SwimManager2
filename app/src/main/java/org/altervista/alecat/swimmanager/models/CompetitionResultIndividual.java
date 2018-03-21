package org.altervista.alecat.swimmanager.models;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

/**
 * Created by Alessandro Cattapan on 19/02/2018.
 */

public class CompetitionResultIndividual extends CompetitionResult {

    private DocumentReference mSwimmerReference;

    // Void constructor for firebase database
    public CompetitionResultIndividual(){}

    public CompetitionResultIndividual(String name, String place, Date date, int poolLength,
                                       int race, int gender, int timingType,
                                       long time, DocumentReference competitionReference,
                                       DocumentReference swimmerReference){
        super(name, place, date, poolLength, race, gender, timingType, time, competitionReference);
        mSwimmerReference = swimmerReference;
    }

    public CompetitionResultIndividual(Competition competition,
                                       int race, int gender, int timingType,
                                       long time, DocumentReference competitionReference,
                                       DocumentReference swimmerReference){
        this(competition.getName(), competition.getPlace(), competition.getDate(), competition.getPoolLength(),
                race, gender, timingType, time, competitionReference,
                swimmerReference);
    }

    public CompetitionResultIndividual(CompetitionResult competitionResult,
                                 DocumentReference swimmerReference){
        this(competitionResult.getName(), competitionResult.getPlace(), competitionResult.getDate(), competitionResult.getPoolLength(),
                competitionResult.getRace(), competitionResult.getGender(), competitionResult.getTimingType(),
                competitionResult.getTime(), competitionResult.getCompetitionReference(),
                swimmerReference);
    }

    public DocumentReference getSwimmerReference(){
        return mSwimmerReference;
    }

    public void setSwimmerReference(DocumentReference reference){
        mSwimmerReference = reference;
    }
}
