package org.altervista.alecat.swimmanager.ui.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.altervista.alecat.swimmanager.R;
import org.altervista.alecat.swimmanager.interfaces.ItemClickListener;
import org.altervista.alecat.swimmanager.models.Swimmer;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alessandro Cattapan on 16/02/2018.
 */

public class SwimmerFirestoreRecyclerAdapter extends SelectableFirestoreAdapter<Swimmer, SwimmerFirestoreRecyclerAdapter.SwimmerHolder> {

    private Context mContext;
    private ItemClickListener mItemClickListener;


    public SwimmerFirestoreRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Swimmer> options, Context context, ItemClickListener itemClickListener) {
        super(options);
        mContext = context;
        mItemClickListener = itemClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull SwimmerHolder holder, int position, @NonNull Swimmer model) {
        // Bind Swimmer to the holder
        // Surname Name
        String name = model.getSurname() + " " + model.getName();
        holder.mNameTextView.setText(name);

        // Surname Name (SN)
        String surnameFirstLetter = model.getSurname().substring(0,1);
        String nameFistLetter = model.getName().substring(0,1);
        String initialLetter = surnameFirstLetter.toUpperCase() + nameFistLetter.toUpperCase();
        holder.mInitialLetterTextView.setText(initialLetter);

        // Set the proper background color on the circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable swimmerCircle = (GradientDrawable) holder.mInitialLetterTextView.getBackground();
        // Get the appropriate background color based on the current swimmer initial name letter
        int swimmerCircleColor = getSwimmerCircleColor(surnameFirstLetter);
        // Set the color on the circle
        swimmerCircle.setColor(swimmerCircleColor);

        // Age
        int age = calculateAge(model.getBirthday());
        Integer ageInteger = new Integer(age);
        holder.mAgeTextView.setText(ageInteger.toString());

        // Show or Hide Image
        if (isBirthday(model.getBirthday())){
            holder.mBirthdayImageView.setVisibility(View.VISIBLE);
        } else {
            holder.mBirthdayImageView.setVisibility(View.GONE);
        }

        // Highlight the item if it's selected
        if (isSelected(position)){
            holder.mLinearLayout.setSelected(true);
        } else {
            holder.mLinearLayout.setSelected(false);
        }

        // OnClickItemListener
        holder.setItemClickListener(mItemClickListener);
    }

    @Override
    public SwimmerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new instance of the ViewHolder, in this case we are using a custom
        // layout called R.layout.fragment_swimmer_item for each item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_swimmer_item, parent, false);

        return new SwimmerHolder(view);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
    }

    @Override
    public void onError(@NonNull FirebaseFirestoreException e) {
        super.onError(e);
    }

    private int calculateAge(Date birthdayDate){

        long timeInMillis = birthdayDate.getTime();

        // Joda Library: Birthday
        LocalDate birthday = new LocalDate(timeInMillis);

        // Joda Library: Today
        LocalDate today = new LocalDate();

        // Calculate age
        Period period = new Period(birthday, today, PeriodType.yearMonthDay());
        return period.getYears();
    }

    // Check if today is the swimmer's birthday
    private boolean isBirthday(Date birthdayDate){
        // Get a Date object from a string
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(birthdayDate);
        int birthdayDay = birthday.get(Calendar.DAY_OF_MONTH);
        int birthdayMonth = birthday.get(Calendar.MONTH);

        Calendar today = Calendar.getInstance();
        int todayDay = today.get(Calendar.DAY_OF_MONTH);
        int todayMonth = today.get(Calendar.MONTH);

        return (birthdayDay == todayDay && birthdayMonth == todayMonth);
    }

    // Choose the correct color to display
    private int getSwimmerCircleColor(String initialLetter){
        int swimmerCircleColor;

        if (initialLetter.compareToIgnoreCase("E") < 1){
            swimmerCircleColor = R.color.colorSwimmerA;
        } else if (initialLetter.compareToIgnoreCase("K") < 1){
            swimmerCircleColor = R.color.colorSwimmerF;
        } else if (initialLetter.compareToIgnoreCase("P") < 1) {
            swimmerCircleColor = R.color.colorSwimmerL;
        } else if (initialLetter.compareToIgnoreCase("U") < 1) {
            swimmerCircleColor = R.color.colorSwimmerQ;
        } else {
            swimmerCircleColor = R.color.colorSwimmerV;
        }

        return ContextCompat.getColor(mContext, swimmerCircleColor);
    }

    /**
     * Inner class
     */
    public class SwimmerHolder extends FirebaseViewHolder{

        private TextView mInitialLetterTextView;
        private TextView mNameTextView;
        private TextView mAgeTextView;
        private ImageView mBirthdayImageView;
        private LinearLayout mLinearLayout;

        public SwimmerHolder(View itemView) {
            super(itemView);

            mInitialLetterTextView = itemView.findViewById(R.id.initial_letter);
            mNameTextView = itemView.findViewById(R.id.text_swimmer_name);
            mAgeTextView = itemView.findViewById(R.id.text_swimmer_age);
            mBirthdayImageView = itemView.findViewById(R.id.birthday_image);
            mLinearLayout = itemView.findViewById(R.id.linear_layout_item_recycler_view);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
    }
}
