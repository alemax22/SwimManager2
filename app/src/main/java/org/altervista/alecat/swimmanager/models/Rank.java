package org.altervista.alecat.swimmanager.models;

import android.util.Log;

import org.altervista.alecat.swimmanager.error.RankInfoException;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static org.altervista.alecat.swimmanager.models.SwimManagerContract.DATE_FORMAT;
import static org.altervista.alecat.swimmanager.models.SwimManagerContract.DATE_PDF_INPUT_FORMAT;
import static org.altervista.alecat.swimmanager.models.SwimManagerContract.TIMING_AUTOMATIC;
import static org.altervista.alecat.swimmanager.models.SwimManagerContract.TIMING_MANUAL;
import static org.altervista.alecat.swimmanager.models.SwimManagerContract.TIMING_UNKNOWN;

/**
 * Created by Alessandro Cattapan on 24/12/2017.
 */

public class Rank {

    private static final String TAG = Rank.class.getSimpleName();

    private static final String SERIE_DELIMITER = "Serie Cat.:";
    private static final String ORE_DELIMITER = "ore";
    private static final String VASCA_DELIMITER = "Base v.:";
    private static final String CRONOMETRAGGIO_DELIMITER = "Cron:";
    private static final String GENDER_MALE_STRING = "Maschi";
    private static final String GENDER_FEMALE_STRING = "Femmine";
    private static final String TIMING_MANUAL_STRING = "M";
    private static final String TIMING_AUTOMATIC_STRING = "A";
    private static final String COMMA_DELIMITER = ",";
    private static final String DASH_DELIMITER = "-";

    private String mInfo;
    private String mRank;
    private String mCompetitionName = null;
    private Date mDate = null;
    private String mPlace = null;
    private Race mRace = null;
    private int mGender = -1;
    private int mPoolMeters = -1;
    private int mTimingType = -1;
    private boolean mHasInfo = false;
    private boolean mHasRank = false;

    public Rank(String competitionName){
        mInfo = null;
        mRank = null;
        mCompetitionName = competitionName;
    }

    public Rank(String info, String rank, String competitionName) {
        mInfo = info;
        mRank = rank;
        mCompetitionName = competitionName;
        if (info != null){
            mHasInfo = true;
        }
        if (rank != null){
            mHasRank = true;
        }
    }

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        if (info != null){
            mHasInfo = true;
        }
        mInfo = info;
    }

    public String getRank() {
        return mRank;
    }

    public void setRank(String rank) {
        if (rank != null){
            mHasRank = true;
        }
       mRank = rank;
    }

    public void setCompetitionName(String name){
        mCompetitionName = name;
    }

    public boolean hasRank(){
        return mHasRank;
    }

    public boolean hasInfo(){
        return mHasInfo;
    }

    /**
     * This method merges two ranks from two different pages into a single rank.
     * It keeps the info String from the first rank, so it is not a good idea to
     * merge different ranks with different info String
     * @param rankToMerge
     * @return rank1 merged
     */
    public void mergeRank(Rank rankToMerge){
        this.setRank(this.getRank() + System.getProperty("line.separator") + rankToMerge.getRank());
        if (this.hasInfo() && rankToMerge.hasInfo()){
            Log.e("Rank", "There might be an error while merging ranks!");
        }
    }

    /**
     * This method returns circuitName
     * @return circuitName
     */
    public String getCompetitionName(){
        return mCompetitionName;
    }

    /**
     * This method searches for the race inside the info String and it retuns an
     * error when you call it in an object with no info String
     * @return race
     * @throws RankInfoException
     */
    public Race getRace(){
        if (!mHasInfo){
            throw new RankInfoException();
        }
        if (mRace == null){
            Scanner scanner = new Scanner(mInfo);
            scanner.useDelimiter(SERIE_DELIMITER);
            mRace = getRaceFromString(scanner.next().trim());
            scanner.close();
        }
        return mRace;
    }

    // TODO: Implement race search
    /**
     *
     * @param string
     * @return
     */
    private Race getRaceFromString(String string){
        return new Race();
    }

    /** This method searches for gender inside the info String and it returns an
     * error when you call it in an object with no info String
     * @return gender
     * @throws RankInfoException
     * */
    public int getGender(){
        if (!mHasInfo){
            throw new RankInfoException();
        }
        if (mGender < 0 ){
            if (mInfo.contains(GENDER_MALE_STRING)){
                mGender =  SwimManagerContract.GENDER_MALE;
            } else if (mInfo.contains(GENDER_FEMALE_STRING)){
                mGender =  SwimManagerContract.GENDER_FEMALE;
            } else {
                mGender = SwimManagerContract.GENDER_UNKNOWN; // Gara mista MASCHI e FEMMINE
            }
        }
        return mGender;
    }

    /** This method searches for the place inside the info String and it returns an
     * error when you call it in an object with no info String
     * @return place
     * @throws RankInfoException
     * */
    public String getPlace(){
        if (!mHasInfo){
            throw new RankInfoException();
        }
        if (mPlace == null){
            Scanner scanner = new Scanner(mInfo);
            scanner.nextLine();
            scanner.useDelimiter(COMMA_DELIMITER);
            mPlace = scanner.next().trim();
            scanner.close();
        }
        return mPlace;
    }

    /** This method searches for the date inside the info String and it returns an
     * error when you call it in an object with no info String
     * @return date
     * @throws RankInfoException
     * */
    public Date getDate(){
        if (!mHasInfo){
            throw new RankInfoException();
        }
        if (mDate == null){
            Scanner scanner = new Scanner(mInfo);
            scanner.useDelimiter(ORE_DELIMITER + "|" + COMMA_DELIMITER);
            scanner.next(); // Delete everything that I don't need!
            String output = scanner.next().trim();
            scanner.close();
            Scanner dateScanner = new Scanner(output);
            dateScanner.next(); // Delete day of week
            output = dateScanner.next();
            // Return the date into the app format
            SimpleDateFormat dateFormatter1 = new SimpleDateFormat(DATE_PDF_INPUT_FORMAT);
            Date circuitDate = dateFormatter1.parse(output, new ParsePosition(0));
            mDate = circuitDate;
        }
        return mDate;
    }

    /** This method searches for pool length inside the info String and it returns an
     * error when you call it in an object with no info String
     * @return poolMeters
     * @throws RankInfoException
     * */
    public int getPoolMeters(){
        if (!mHasInfo){
            throw new RankInfoException();
        }
        if (mPoolMeters < 0){
            Scanner scanner = new Scanner(mInfo);
            scanner.useDelimiter(VASCA_DELIMITER);
            scanner.next(); // Delete everything that I don't need!
            String output = scanner.next().trim();
            scanner.close();
            mPoolMeters = Integer.parseInt(output);
        }
        return mPoolMeters;
    }

    /** This method searches for pool length inside the info String and it returns an
     * error when you call it in an object with no info String
     * @return chrono
     * @throws RankInfoException
     * */
    public int getTimingType(){
        if (!mHasInfo){
            throw new RankInfoException();
        }
        if (mTimingType < 0){
            Scanner scanner = new Scanner(mInfo);
            scanner.useDelimiter(CRONOMETRAGGIO_DELIMITER + "|" + DASH_DELIMITER);
            scanner.next(); // Delete everything that I don't need!
            String output = scanner.next().trim();
            scanner.close();
            if (output.equalsIgnoreCase(TIMING_MANUAL_STRING)){
                mTimingType = TIMING_MANUAL;
            } else if (output.equalsIgnoreCase((TIMING_AUTOMATIC_STRING))){
                mTimingType = TIMING_AUTOMATIC;
            } else {
                mTimingType = TIMING_UNKNOWN;
                Log.e(TAG, "Error! Timing Type not identified, the String is: " + output);
            }
        }
        return mTimingType;
    }

}
