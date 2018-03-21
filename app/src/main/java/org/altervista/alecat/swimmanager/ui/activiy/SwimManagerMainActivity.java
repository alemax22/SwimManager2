package org.altervista.alecat.swimmanager.ui.activiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import org.altervista.alecat.swimmanager.BuildConfig;
import org.altervista.alecat.swimmanager.R;
import org.altervista.alecat.swimmanager.models.Group;
import org.altervista.alecat.swimmanager.models.SwimManagerContract;
import org.altervista.alecat.swimmanager.ui.fragment.GroupFragment;
import org.altervista.alecat.swimmanager.ui.fragment.SwimmerFragment;
import org.altervista.alecat.swimmanager.models.Swimmer;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Alessandro Cattapan on 10/02/2018.
 */

public class SwimManagerMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SwimmerFragment.OnListFragmentInteractionListener,
        GroupFragment.OnListFragmentInteractionListener {

    // TAG for log messages
    private static final String TAG = SwimManagerMainActivity.class.getSimpleName();

    // Constants
    private static final String STATE_CURRENT_FRAGMENT_TAG = "CURRENT_FRAGMENT_TAG";
    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;

    // TODO: Debug
    private static final String DOCUMENT_TEAM_NAME = "Mq3Jg1MsFFfMy3mvgMj7";

    // Activity
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private String mCurrentFragmentTag;

    // Firebase
    private String mUsername;
    private String mEmail;

    // Firebase Authentication variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // Navigation Pannel Elements
    private TextView mNavUserNameTextView;
    private TextView mNavUserEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swim_manager_main);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavUserNameTextView = findViewById(R.id.nav_user_name);
        mNavUserEmailTextView = findViewById(R.id.nav_user_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        mNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();

        if (savedInstanceState != null && savedInstanceState.getString(STATE_CURRENT_FRAGMENT_TAG) != null){
            mCurrentFragmentTag = savedInstanceState.getString(STATE_CURRENT_FRAGMENT_TAG);
        }

        if (savedInstanceState == null){
            // TODO: Change first screen
            mNavigationView.getMenu().performIdentifierAction(R.id.nav_swimmer,0);
            mNavigationView.setCheckedItem(R.id.nav_swimmer);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateSwimmer();
                Toast.makeText(SwimManagerMainActivity.this, "Swimmers added to the database", Toast.LENGTH_SHORT).show();
            }
        });

        // Authentication listener
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user signed in
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    // user signed out
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(!BuildConfig.DEBUG,true) /* TODO: Delete this line when the app is ready*/
                                    .setAvailableProviders(
                                            Arrays.asList(
                                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                                    new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    private void onSignedInInitialize(String username){
        mUsername = username;
        // TODO:
        /*mNavUserNameTextView.setText(mUsername);
        mNavUserEmailTextView.setText(mEmail);*/
    }

    private void onSignedOutCleanup(){
        mUsername = ANONYMOUS;
        // TODO:
        /*mNavUserNameTextView.setText(ANONYMOUS);
        mNavUserEmailTextView.setText(ANONYMOUS);*/
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_CURRENT_FRAGMENT_TAG, mCurrentFragmentTag);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentFragmentTag = savedInstanceState.getString(STATE_CURRENT_FRAGMENT_TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, R.string.sign_in_msg_success, Toast.LENGTH_SHORT).show();
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(this, R.string.sign_in_msg_cancelled, Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, R.string.sign_in_msg_network_issue, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, R.string.sign_in_msg_unknown_error, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.swim_manager_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.log_out:
                // sign out
                AuthUI.getInstance().signOut(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        switch (id){
            case R.id.nav_dashboard:
                break;
            case R.id.nav_swimmer:
                fragment = SwimmerFragment.newInstance(DOCUMENT_TEAM_NAME);
                break;
            case R.id.nav_group:
                break;
            case R.id.nav_competition:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
        }

        if (fragment != null){
            showFragment(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Fragment fragment){

        if (fragment == null) {
            // Do nothing
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentByTag(mCurrentFragmentTag);

        if (currentFragment != null && currentFragment.getClass().getName().equals(fragment.getClass().getName())) {
            // Fragment already shown, do nothing
            Log.v(TAG, "Hey");
            return;
        }

        String tag = fragment.getClass().getSimpleName();

        fragmentManager.beginTransaction()
                .replace(R.id.main_content_frame, fragment, tag)
                .commit();

        mCurrentFragmentTag = tag;
    }

    @Override
    public void onListFragmentInteraction(Swimmer item) {

    }

    @Override
    public void onListFragmentInteraction(Group item) {

    }

    // TODO: Remove debug method
    private void generateSwimmer(){
        Calendar cal = Calendar.getInstance();
        cal.set(1996,Calendar.MAY,22, 12, 0, 0);
        Swimmer swimmer = new Swimmer("Alessandro","Cattapan", cal.getTime(), SwimManagerContract.GENDER_MALE, SwimManagerContract.CATEGORY_PROPAGANDA, SwimManagerContract.MEMBERSHIP_PROPAGANDA);
        addSwimmerToFirebase(swimmer);
        /*cal.set(2000, Calendar.JULY, 23,12, 0, 0);
        swimmer = new Swimmer("Elia","Dalla Rizza",cal.getTime(), SwimManagerContract.GENDER_MALE, SwimManagerContract.CATEGORY_PROPAGANDA, SwimManagerContract.MEMBERSHIP_PROPAGANDA);
        addSwimmerToFirebase(swimmer);
        cal.set(2000, Calendar.JULY, 28, 12, 0, 0);
        swimmer = new Swimmer("Ester","Russo",cal.getTime(), SwimManagerContract.GENDER_FEMALE, SwimManagerContract.CATEGORY_PROPAGANDA, SwimManagerContract.MEMBERSHIP_PROPAGANDA);
        addSwimmerToFirebase(swimmer);
        cal.set(2001, Calendar.NOVEMBER, 16, 12, 0, 0);
        swimmer = new Swimmer("Francesco","Salomon",cal.getTime(), SwimManagerContract.GENDER_MALE, SwimManagerContract.CATEGORY_PROPAGANDA, SwimManagerContract.MEMBERSHIP_PROPAGANDA);
        addSwimmerToFirebase(swimmer);
        cal.set(1999, Calendar.OCTOBER, 31, 12, 0, 0);
        swimmer = new Swimmer("Tania","Baldassa",cal.getTime(), SwimManagerContract.GENDER_FEMALE, SwimManagerContract.CATEGORY_PROPAGANDA, SwimManagerContract.MEMBERSHIP_PROPAGANDA);
        addSwimmerToFirebase(swimmer);*/
    }

    // TODO: Remove debug method
    private void addSwimmerToFirebase(Swimmer swimmer){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docTeamRef = db.collection(SwimManagerContract.MAIN_COLLECTION_TEAM).document(DOCUMENT_TEAM_NAME);
        DocumentReference docSwimmerRef = docTeamRef.collection(SwimManagerContract.COLLECTION_SWIMMER).document();

        CollectionReference colCompetitionResultRef = docTeamRef.collection(SwimManagerContract.COLLECTION_COMPETITION_RESULT);
        CollectionReference colGroupRef = docTeamRef.collection(SwimManagerContract.COLLECTION_GROUP);
        CollectionReference colCompetitionRef = docTeamRef.collection(SwimManagerContract.COLLECTION_COMPETITION);

        WriteBatch batch = db.batch();
        batch.set(docSwimmerRef,swimmer);

        batch.commit();
        /*
        // Operazione Modulare per Risultati
        Competition competition = new Competition("1a Giornata SwimTeen",
                "Treviso", Calendar.getInstance().getTime(), SwimManagerContract.POOL_LENGTH_25);
        DocumentReference docCompetitonReference =
        CompetitionResultIndividual result =
                new CompetitionResultIndividual(
                        competition,
                        1,
                        SwimManagerContract.TIMING_MANUAL,
                        swimmer.getGender(),
                        100000000L,
                        docSwimmerRef);
        Map<String, Object> data = new HashMap<>();
        DocumentReference docCompRef = colCompetitionRef.document();
        data.put(SwimManagerContract.DOCUMENT_FIELD_REFERENCE, docCompRef);
        data.put(SwimManagerContract.DOCUMENT_FIELD_RACE, result.getRace());
        batch.set(docSwimmerRef.collection(SwimManagerContract.COLLECTION_COMPETITION_RESULT).document(docCompRef.getId()), data);
        batch.set(docCompRef,result);

        // Operazione Modulare per Risultati
        result =
                new CompetitionResultIndividual("2a Giornata SwimTeen",
                        "Montebelluna",
                        Calendar.getInstance().getTime(),
                        1,
                        SwimManagerContract.POOL_LENGTH_25,
                        SwimManagerContract.TIMING_MANUAL,
                        swimmer.getGender(),
                        10000000L,
                        docSwimmerRef);
        data = new HashMap<>();
        docCompRef = colCompetitionRef.document();
        data.put(SwimManagerContract.DOCUMENT_FIELD_REFERENCE, docCompRef);
        data.put(SwimManagerContract.DOCUMENT_FIELD_RACE, result.getRace());
        batch.set(docSwimmerRef.collection(SwimManagerContract.COLLECTION_COMPETITION_RESULT).document(docCompRef.getId()), data);
        batch.set(docSwimmerRef.collection(SwimManagerContract.COLLECTION_COMPETITION_RESULT_PERSONAL_BEST).document(docCompRef.getId()), data);
        batch.set(docCompRef,result);

        // Operazione Modulare per Risultati
        result =
                new CompetitionResultIndividual("3a Giornata SwimTeen",
                        "Preganziol",
                        Calendar.getInstance().getTime(),
                        1,
                        SwimManagerContract.POOL_LENGTH_25,
                        SwimManagerContract.TIMING_MANUAL,
                        swimmer.getGender(),
                        10000008L,
                        docSwimmerRef);
        data = new HashMap<>();
        docCompRef = colCompetitionRef.document();
        data.put(SwimManagerContract.DOCUMENT_FIELD_REFERENCE, docCompRef);
        data.put(SwimManagerContract.DOCUMENT_FIELD_RACE, result.getRace());
        batch.set(docSwimmerRef.collection(SwimManagerContract.COLLECTION_COMPETITION_RESULT).document(docCompRef.getId()), data);
        batch.set(docCompRef,result);

        // Operazione Modulare per Gruppi
        DocumentReference docGroupRef = colGroupRef.document();
        Group group = new Group("Salvamento",1);
        batch.set(docGroupRef, group);
        batch.commit();*/
    }
}
