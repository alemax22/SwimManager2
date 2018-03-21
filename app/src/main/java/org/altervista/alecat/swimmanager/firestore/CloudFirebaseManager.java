package org.altervista.alecat.swimmanager.firestore;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;
import com.itextpdf.text.pdf.qrcode.WriterException;

import org.altervista.alecat.swimmanager.models.Group;
import org.altervista.alecat.swimmanager.models.SwimManagerContract;
import org.altervista.alecat.swimmanager.models.Swimmer;

/**
 * Created by Alessandro Cattapan on 19/02/2018.
 */

public class CloudFirebaseManager {

    // General Info
    private FirebaseFirestore mDatabase;
    private DocumentReference mTeamReference;

    // Team's main collections
    private CollectionReference mCollectionSwimmerReference;
    private CollectionReference mCollectionCompetitionResultReference;
    private CollectionReference mCollectionCompetitionResultRelayReference;
    private CollectionReference mCollectionGroupReference;

    public CloudFirebaseManager(String teamId){
        mDatabase = FirebaseFirestore.getInstance();
        mTeamReference = mDatabase.collection(SwimManagerContract.MAIN_COLLECTION_TEAM).document(teamId);
        mCollectionSwimmerReference = mTeamReference.collection(SwimManagerContract.COLLECTION_SWIMMER);
        mCollectionCompetitionResultReference = mTeamReference.collection(SwimManagerContract.COLLECTION_COMPETITION_RESULT);
        mCollectionCompetitionResultRelayReference = mTeamReference.collection(SwimManagerContract.COLLECTION_COMPETITION_RESULT_RELAY);
        mCollectionGroupReference = mTeamReference.collection(SwimManagerContract.COLLECTION_GROUP);
    }

    public WriteBatch addSwimmer(Swimmer swimmer){
        WriteBatch batch = mDatabase.batch();

        batch.set(mCollectionSwimmerReference.document(), swimmer);

        return batch;
    }

    public WriteBatch addGroup(Group group){
        WriteBatch batch = mDatabase.batch();

        batch.set(mCollectionGroupReference.document(), group);

        return batch;
    }

    public WriteBatch addSwimmerToGroup(Swimmer... swimmer){
        return null;
        // TODO: Implement method
    }
}
