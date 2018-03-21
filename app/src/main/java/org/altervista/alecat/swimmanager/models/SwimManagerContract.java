package org.altervista.alecat.swimmanager.models;

/**
 * Created by Alessandro Cattapan on 23/08/2017.
 */

public final class SwimManagerContract {

    // Private constructor
    private SwimManagerContract() {
    }

    /************
     * FIREBASE *
     ************/

    // Cloud Firestore: Collections' name and Documents' fields path
    // Root collections of the Database
    public static final String MAIN_COLLECTION_TEAM = "team";
    public static final String MAIN_COLLECTION_RACE = "race";

    // SubCollections of "team"
    public static final String COLLECTION_SWIMMER = "swimmer";
    public static final String COLLECTION_COMPETITION = "competition";
    public static final String COLLECTION_COMPETITION_RESULT = "competition-result";
    public static final String COLLECTION_COMPETITION_RESULT_RELAY = "competition-result-relay";
    public static final String COLLECTION_GROUP = "group";

    // SubCollection of "swimmer"
    public static final String COLLECTION_COMPETITION_RESULT_PERSONAL_BEST = "competition-result-personal-best";

    // SubCollection of "group"
    public static final String COLLECTION_GROUP_PERIOD = "period";
    public static final String COLLECTION_GROUP_PERIOD_TRAINING_DAY = "training_day";

    // Document field
    public static final String DOCUMENT_FIELD_REFERENCE = "reference";
    public static final String DOCUMENT_FIELD_RACE = "race";
    public static final String DOCUMENT_FIELD_NUM_TRAINING_DONE = "numTrainingDone";


    /*************
     * CONSTANTS *
     *************/

    // Genders
    public static final int GENDER_UNKNOWN = 0;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    // Membership: tipo di Tesseramento
    public static final int MEMBERSHIP_UNKNOWN = 0;
    public static final int MEMBERSHIP_PROPAGANDA = 1;
    public static final int MEMBERSHIP_AGONISTA = 2;
    public static final int MEMBERSHIP_MASTER = 3;

    // Day in the week
    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY = 3;
    public static final int FRIDAY = 4;
    public static final int SATURDAY = 5;
    public static final int SUNDAY = 6;

    // Timing Type
    public static final int TIMING_UNKNOWN = 0;
    public static final int TIMING_MANUAL = 1;
    public static final int TIMING_AUTOMATIC = 2;

    // Pool length
    public static final int POOL_LENGTH_25 = 25;
    public static final int POOL_LENGTH_50 = 50;

    // Category
    public static final int CATEGORY_UNKNOWN = 0;
    public static final int CATEGORY_PROPAGANDA = 1;
    public static final int CATEGORY_ESORDIENTE_B = 2;
    public static final int CATEGORY_ESORDIENTE_A = 3;
    public static final int CATEGORY_CATEGORIA_RAGAZZI = 4;
    public static final int CATEGORY_CATEGORIA_JUNIOR = 5;
    public static final int CATEGORY_CATEGORIA_CADETTI = 6;
    public static final int CATEGORY_CATEGORIA_SENIOR = 7;
    public static final int CATEGORY_MASTER = 8;

    // Date and Time Format
    public static final String DATE_FORMAT = "dd MMM yyyy";
    public static final String DATE_PDF_INPUT_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "mm:ss.SS";

}
