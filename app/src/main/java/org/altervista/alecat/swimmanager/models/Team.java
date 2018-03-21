package org.altervista.alecat.swimmanager.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Created by Alessandro Cattapan on 19/02/2018.
 */

public class Team {

    private String mName;
    private Date mCreationDate;

    // Void constructor for firebase database
    public Team(){}

    public Team(String name){
        mName = name;
    }

    @ServerTimestamp
    public Date getTimestamp() { return mCreationDate; }

    public void setTimestamp(Date timestamp) { mCreationDate = timestamp; }
}
