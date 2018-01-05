package com.example.alice.health;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alice.health.models.medicalDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicalDetailFragment extends Fragment implements  View.OnClickListener {

    private static final String TAG = MedicalDetailFragment.class.getSimpleName();

    @BindView(R.id.userFullName) EditText mUserFullName;
    @BindView(R.id.userPhoneNumber) EditText mUserPhoneNumber;
    @BindView(R.id.userAge) EditText mUserAge;
    @BindView(R.id.gender_spinner) Spinner mGenderSpinner;
    @BindView(R.id.bloodGroup_Spinner) Spinner mBloodGroupSpinner;
    @BindView(R.id.userMedicalConditions) EditText mUserMedicalConditions;
    @BindView(R.id.userMedAllergies) EditText mUserMedAllergies;
    @BindView(R.id.userAllergies) EditText mUserAllergies;
    @BindView(R.id.userMedicalBtn) Button mUserSaveMedicalButton;

    public MedicalDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_medical_detail, container, false);

        ButterKnife.bind(this,view);
        mUserSaveMedicalButton.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View v){
        if(v ==  mUserSaveMedicalButton){
            // Save values to the database method
            saveToFirebase();

            // when all data is saved clear the fields
            mUserAge.setText("");
            mUserFullName.setText("");
            mUserMedicalConditions.setText("");
            mUserMedAllergies.setText("");
            mUserAllergies.setText("");
            mUserPhoneNumber.setText("");
            mUserPhoneNumber.setText("");

            Fragment emgCont = new EmergencyContactsFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.mainFrame,emgCont);
            transaction.commit();
        }
    }

    public void saveToFirebase(){

        String username = mUserFullName.getText().toString();
        String userAge = mUserAge.getText().toString();
        String medicalConditions = mUserMedicalConditions.getText().toString();
        String medicalAllergies = mUserMedAllergies.getText().toString();
        String otherAllergies = mUserAllergies.getText().toString();
        String phoneNumber = mUserPhoneNumber.getText().toString();
        String gender= mGenderSpinner.getItemAtPosition(mGenderSpinner.getSelectedItemPosition()).toString();
        String bloodGroup = mBloodGroupSpinner.getItemAtPosition(mBloodGroupSpinner.getSelectedItemPosition()).toString();


        FirebaseUser users= FirebaseAuth.getInstance().getCurrentUser();
        String uid=users.getUid();


        DatabaseReference  reference = FirebaseDatabase.getInstance().getReference("user").child(uid);
        medicalDetails medDetails = new medicalDetails (username,userAge,phoneNumber,gender,bloodGroup, medicalConditions, medicalAllergies, otherAllergies);


        reference.child("medicalDetails").setValue(medDetails);

        Toast.makeText(getActivity(), " Details Saved", Toast.LENGTH_SHORT).show();
    }

}
