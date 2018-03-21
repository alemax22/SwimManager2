package org.altervista.alecat.swimmanager.utils;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;

import org.altervista.alecat.swimmanager.interfaces.FinPdfReader;
import org.altervista.alecat.swimmanager.models.Competition;
import org.altervista.alecat.swimmanager.models.CompetitionResult;
import org.altervista.alecat.swimmanager.models.CompetitionResultIndividual;
import org.altervista.alecat.swimmanager.models.CompetitionResultRelay;
import org.altervista.alecat.swimmanager.models.Rank;
import org.altervista.alecat.swimmanager.models.SwimManagerContract;
import org.altervista.alecat.swimmanager.models.Swimmer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Alessandro Cattapan on 26/12/2017.
 */

public class RankManager {

    private String TAG = RankManager.class.getSimpleName();

    private FinPdfReader mFinPdfReader;
    private ArrayList<Rank> mRankList = null;
    private String mTeamName =  null;
    private ArrayList<CompetitionResultIndividual> mCompetitionResultIndividualList = null;
    private ArrayList<CompetitionResultRelay> mCompetitionResultRelayList = null;
    private String mCompetitionName = null;
    private Date mDate = null;
    private String mPlace = null;
    private int mPoolMeters = -1;
    private int mTimingType = -1;
    private Competition mCompetition;

    public RankManager(FinPdfReader finPdfReader, String teamName){
        mFinPdfReader = finPdfReader;
        mTeamName = teamName.toUpperCase();
    }

    public ArrayList<CompetitionResultIndividual> getIndividualResult(){
        if (mCompetitionResultIndividualList == null){
            // Create the list
            mCompetitionResultIndividualList = new ArrayList<CompetitionResultIndividual>();

            ArrayList<Rank> individualRankList = getIndividualRaceRank();

            Log.v(TAG,"Empty List created!");

            // Get Individual race result
            for (int i = 0; i < individualRankList.size(); i++){
                Log.v(TAG, "Search inside rank number: " + i + " of total " + individualRankList.size());
                searchIndividualResult(individualRankList.get(i));
            }
        }
        return mCompetitionResultIndividualList;
    }

    public ArrayList<CompetitionResultRelay> getRelayResult(){
        if (mCompetitionResultRelayList == null){
            // Create the list
            mCompetitionResultRelayList = new ArrayList<CompetitionResultRelay>();

            ArrayList<Rank> relayRankList = getRelayRaceRank();

            Log.v(TAG,"Empty List created!");

            // Get relay race result
            for (int i = 0; i < relayRankList.size(); i++){
                Log.v(TAG, "Search inside relay rank number: " + i + " of total " + relayRankList.size());
                searchRelayResult(relayRankList.get(i));
            }

        }
        return mCompetitionResultRelayList;
    }

    public ArrayList<Rank> getRelayRaceRank(){
        ArrayList<Rank> relayRankList = new ArrayList<Rank>();
        if (mRankList == null){
            getAllRank();
        }
        if (mRankList.size() == 0){
            Log.e(TAG, "Error while searching ranks inside pdf!");
        }
        for (int i = 0; i < mRankList.size(); i++){
            Rank rank = mRankList.get(i);
            if (rank.getRace().getRelay()){
                relayRankList.add(rank);
            }
        }
       return relayRankList;
    }

    public ArrayList<Rank> getIndividualRaceRank(){
        ArrayList<Rank> individualRankList = new ArrayList<Rank>();
        if (mRankList == null){
            getAllRank();
        }
        if (mRankList.size() == 0){
            Log.e(TAG, "Error while searching ranks inside pdf!");
        }
        for (int i = 0; i < mRankList.size(); i++){
            Rank rank = mRankList.get(i);
            if (!rank.getRace().getRelay()){
                individualRankList.add(rank);
            }
        }
        return individualRankList;
    }

    public void setTeamName(String name){
        mTeamName = name.toUpperCase();
    }

    public String getTeamName(){
        return mTeamName;
    }

    public ArrayList<Rank> getAllRank(){
        if (mRankList == null){
            try {
                mRankList = mFinPdfReader.getAllRank();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Error while searching ranks inside pdf!");
            }
        }
        return mRankList;
    }

    public Competition getCompetition(){
        if (mCompetition == null){
            mCompetition = new Competition(getCompetitionName(), getPlace(), getDate(), getPoolMeters());
        }
        return mCompetition;
    }

    public String getCompetitionName() {
        if (mRankList == null){
            getAllRank();
        }
        if (mCompetitionName == null){
            if (mRankList.size() == 0){
                Log.e(TAG,"There are no ranks inside PDF!");
            } else {
                mCompetitionName = mRankList.get(0).getCompetitionName();
            }
        }
        return mCompetitionName;
    }

    public Date getDate() {
        if (mRankList == null){
            getAllRank();
        }
        if (mDate == null){
            if (mRankList.size() == 0){
                Log.e(TAG,"There are no ranks inside PDF!");
            } else {
                mDate = mRankList.get(0).getDate();
            }
        }
        return mDate;
    }

    public String getPlace() {
        if (mRankList == null){
            getAllRank();
        }
        if (mPlace == null){
            if (mRankList.size() == 0){
                Log.e(TAG,"There are no ranks inside PDF!");
            } else {
               mPlace = mRankList.get(0).getPlace();
            }
        }
        return mPlace;
    }

    public int getPoolMeters() {
        if (mRankList == null){
            getAllRank();
        }
        if (mPoolMeters < 0 ){
            if (mRankList.size() == 0){
                Log.e(TAG,"There are no ranks inside PDF!");
            } else {
                mPoolMeters = mRankList.get(0).getPoolMeters();
            }
        }
        return mPoolMeters;
    }

    public int getTimingType() {
        if (mRankList == null){
            getAllRank();
        }
        if (mTimingType < 0){
            if (mRankList.size() == 0){
                Log.e(TAG,"There are no ranks inside PDF!");
            } else {
                mTimingType = mRankList.get(0).getTimingType();
            }
        }
        return mTimingType;
    }

    private void searchIndividualResult(Rank rank){
        Scanner scanner = new Scanner(rank.getRank());
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (line.contains(mTeamName)) {
                if (!line.substring(0,1).equals("-")) {
                    Scanner console = new Scanner(line);
                    Log.v(TAG, "Line: " + line);
                    console.useDelimiter(mTeamName);
                    String string = console.next(); // Ex: "1 1 1 SURNAME NAME 2000 "
                    String timeString = console.next(); // Ex: " 00:34.1 POINTS"
                    Scanner timeScanner = new Scanner(timeString);
                    Timing time = new Timing(timeScanner.next());
                    // String points = timeScanner.next(); // It can contain "FG", it can also be empty!!
                    timeScanner.close();
                    // TODO: Extract Swimmer
                    DocumentReference swimmerReference = getSwimmer(string);
                    DocumentReference competitionReference = getCompetitionReference();
                    mCompetitionResultIndividualList.add(
                            new CompetitionResultIndividual(
                                    getCompetition(),
                                    rank.getRace().getId(),
                                    rank.getGender(),
                                    getTimingType(),
                                    time.getTimeMillis(),
                                    competitionReference,
                                    swimmerReference));
                    Log.v(TAG, "Added new Result");
                    console.close();
                }
            }
        }
        scanner.close();
    }

    private DocumentReference getSwimmer(String line){
        // TODO: Extract Swimmer
        Scanner scanner = new Scanner(line);
        scanner.close();
        return null;
    }

    private DocumentReference getCompetitionReference(){
        // TODO: Return reference for the race
        return null;
    }

    private void searchRelayResult(Rank rank){
        Scanner scanner = new Scanner(rank.getRank());
        while (scanner.hasNextLine()){
            LinkedList<String> lines = new LinkedList<String>();
            // Create a group of five lines
            for (int i = 0; i <= 4 && scanner.hasNextLine(); i++){
                lines.addLast(scanner.nextLine());
            }
            if (lines.getLast().contains(mTeamName)){
                if (!lines.getLast().substring(0,1).equals("-")) {
                    // TODO: Extract Swimmer DocumentReference
                    // Swimmers
                    Swimmer swimmer1 = new Swimmer();
                    Swimmer swimmer2 = new Swimmer();
                    Swimmer swimmer3 = new Swimmer();
                    Swimmer swimmer4 = new Swimmer();
                    ArrayList<DocumentReference> swimmerReference = new ArrayList<DocumentReference>();

                    Scanner timeScanner = new Scanner(lines.getLast());
                    timeScanner.useDelimiter(mTeamName);
                    timeScanner.next(); // Ex: "1 1 1 "
                    String line = timeScanner.next(); // Ex: " 00:00.000 FG"
                    timeScanner.close();
                    Scanner s = new Scanner(line);
                    String timeString = s.next();
                    Timing time = new Timing(timeString);
                    s.close();
                    mCompetitionResultRelayList.add(
                            new CompetitionResultRelay(
                                    getCompetition(),
                                    rank.getRace().getId(),
                                    rank.getGender(),
                                    getTimingType(),
                                    time.getTimeMillis(),
                                    getCompetitionReference(),
                                    swimmerReference));
                }
            }
        }
        scanner.close();
    }

    // TODO: Implement this method inside: "searchRelayResult" and inside "searchIndividualResult"
    private Swimmer matchSwimmer(String name, ArrayList<Swimmer> swimmers){
        name.trim();
        for (int i = 0; i < swimmers.size(); i++){
            Swimmer swimmer = swimmers.get(i);
            String swimmerName = swimmer.getSurname() + " " + swimmer.getName();
            if (name.equalsIgnoreCase(swimmerName)){
                return swimmer;
            }
        }
        Log.v(TAG, "No match found!");
        return null;
    }

    // TODO: Remove this DEBUG METHOD
    private void buildAtleti(){
        ArrayList<Swimmer> list;
        list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.JULY, 23,0, 0, 0);
        Swimmer swimmer = new Swimmer("Elia","Dalla Rizza",cal.getTime(), SwimManagerContract.GENDER_MALE, SwimManagerContract.CATEGORY_PROPAGANDA, SwimManagerContract.MEMBERSHIP_PROPAGANDA);
        list.add(swimmer);
        cal.set(2000, Calendar.JULY, 28, 0, 0, 0);
        Swimmer swimmer1 = new Swimmer("Ester","Russo",cal.getTime(),SwimManagerContract.GENDER_FEMALE, SwimManagerContract.CATEGORY_PROPAGANDA, SwimManagerContract.MEMBERSHIP_PROPAGANDA);
        list.add(swimmer1);
        cal.set(2001, Calendar.NOVEMBER, 16, 0, 0, 0);
        Swimmer swimmer2 = new Swimmer("Francesco","Salomon",cal.getTime(),SwimManagerContract.GENDER_MALE, SwimManagerContract.CATEGORY_PROPAGANDA, SwimManagerContract.MEMBERSHIP_PROPAGANDA);
        list.add(swimmer2);
        cal.set(1999, Calendar.OCTOBER, 31, 0, 0, 0);
        Swimmer swimmer3 = new Swimmer("Tania","Baldassa",cal.getTime(),SwimManagerContract.GENDER_FEMALE, SwimManagerContract.CATEGORY_PROPAGANDA, SwimManagerContract.MEMBERSHIP_PROPAGANDA);
        list.add(swimmer3);
    }
}
