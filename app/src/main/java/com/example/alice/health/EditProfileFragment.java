package com.example.alice.health;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alice.health.models.EmergencyContacts;
import com.example.alice.health.models.InsuranceDetails;
import com.example.alice.health.models.medicalDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EditProfileFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.userFullName)
    EditText mUserFullName;
    @BindView(R.id.userPhoneNumber) EditText mUserPhoneNumber;
    @BindView(R.id.userAge) EditText mUserAge;
    @BindView(R.id.gender_spinner)
    Spinner mGenderSpinner;
    @BindView(R.id.bloodGroup_Spinner) Spinner mBloodGroupSpinner;
    @BindView(R.id.userMedicalConditions) EditText mUserMedicalConditions;
    @BindView(R.id.userMedAllergies) EditText mUserMedAllergies;
    @BindView(R.id.userAllergies) EditText mUserAllergies;
    @BindView(R.id.userMedicalBtn)
    Button mUserSaveMedicalButton;
    @BindView(R.id.emergencyContact) EditText mEmergencyContactName;
    @BindView(R.id.emergencyNumber) EditText mEmergencyNumber;
    @BindView(R.id.emergencyContact1) EditText mEmergencyContactName1;
    @BindView(R.id.emergencyNumber1) EditText mEmergencyNumber1;
    @BindView(R.id.emergencyContact2) EditText mEmergencyContactName2;
    @BindView(R.id.emergencyNumber2) EditText mEmergencyNumber2;
    @BindView(R.id.emergencyContactBtn) Button mEmergencyContactBtn;
    @BindView(R.id.userNationalId) EditText mNationalId;
    @BindView(R.id.insurancePolicyNo) EditText mPolicyNo;
    @BindView(R.id.userMedCover) EditText mMedCover;
    @BindView(R.id.userPrefHospital) EditText mPrefHospital;
    @BindView(R.id.userInsuranceButton) Button mUserInsuranceBtn;

    public EditProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ButterKnife.bind(this,view);

        mUserSaveMedicalButton.setOnClickListener(this);
        mUserInsuranceBtn.setOnClickListener(this);
        mEmergencyContactBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        if(v == mUserSaveMedicalButton){
            UpdateMedicalDataToFirebase();

            mUserAge.setText("");
            mUserFullName.setText("");
            mUserMedicalConditions.setText("");
            mUserMedAllergies.setText("");
            mUserAllergies.setText("");
            mUserPhoneNumber.setText("");
        }
        if(v==mUserInsuranceBtn){
            UpdateInsuranceToFirebase();

            mNationalId.setText("");
            mPolicyNo.setText("");
            mMedCover.setText("");
            mPrefHospital.setText("");

        }
        if(v==mEmergencyContactBtn){
            UpdateEmergencyContactsToFirebase();

            mEmergencyContactName.setText("");
            mEmergencyNumber.setText("");
            mEmergencyContactName1.setText("");
            mEmergencyNumber1.setText("");
            mEmergencyContactName2.setText("");
            mEmergencyNumber2.setText("");
        }

    }

    public void   UpdateMedicalDataToFirebase(){

        String username = mUserFullName.getText().toString();
        String userAge = mUserAge.getText().toString();
        String medicalConditions = mUserMedicalConditions.getText().toString();
        String medicalAllergies = mUserMedAllergies.getText().toString();
        String otherAllergies = mUserAllergies.getText().toString();
        String phoneNumber = mUserPhoneNumber.getText().toString();
        String gender= mGenderSpinner.getItemAtPosition(mGenderSpinner.getSelectedItemPosition()).toString();
        String bloodGroup = mBloodGroupSpinner.getItemAtPosition(mBloodGroupSpinner.getSelectedItemPosition()).toString();


        FirebaseUser users= FirebaseAuth.getInstance().getCurrentUser();
        String uid= null;
        if (users != null) {
            uid = users.getUid();
        }


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user").child(uid);
        medicalDetails medDetails = new medicalDetails(username,userAge,phoneNumber,gender,bloodGroup, medicalConditions, medicalAllergies, otherAllergies);


        reference.child("medicalDetails").setValue(medDetails);

        Toast.makeText(getActivity(), " Details Updated", Toast.LENGTH_SHORT).show();
    }


    public void UpdateInsuranceToFirebase(){
        String nationalId = mNationalId.getText().toString();
        String policyNumber = mPolicyNo.getText().toString();
        String medicalCover = mMedCover.getText().toString();
        String preferredHospital = mPrefHospital.getText().toString();

        FirebaseUser users= FirebaseAuth.getInstance().getCurrentUser();
        String uid= null;
        if (users != null) {
            uid = users.getUid();
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user").child(uid);

        InsuranceDetails insuranceDetails = new InsuranceDetails(nationalId, policyNumber,medicalCover, preferredHospital);

        reference.child("insuranceDetails").setValue(insuranceDetails);

        Toast.makeText(getActivity(), " InsuranceDetails Details Upaded", Toast.LENGTH_SHORT).show();
    }

    public void UpdateEmergencyContactsToFirebase(){

        String emergencyContactName = mEmergencyContactName.getText().toString();
        String emergencyContactNumber = mEmergencyNumber.getText().toString();
        String emergencyContactName1 = mEmergencyContactName1.getText().toString();
        String emergencyContactNumber1 = mEmergencyNumber1.getText().toString();
        String emergencyContactName2 = mEmergencyContactName2.getText().toString();
        String emergencyContactNumber2 = mEmergencyNumber2.getText().toString();

        FirebaseUser users= FirebaseAuth.getInstance().getCurrentUser();
        String uid= null;
        if (users != null) {
            uid = users.getUid();
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user").child(uid);

        EmergencyContacts emergencyContact = new EmergencyContacts (emergencyContactName,emergencyContactNumber,emergencyContactName1,emergencyContactNumber1,emergencyContactName2,emergencyContactNumber2);

        reference.child("emergencyContacts").setValue(emergencyContact);

        Toast.makeText(getActivity(), " Details Saved", Toast.LENGTH_SHORT).show();
    }


}
