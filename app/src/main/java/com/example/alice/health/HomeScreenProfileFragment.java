package com.example.alice.health;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenProfileFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.header_cover_image)
    ImageView mUserProfilePhoto;
    @BindView(R.id.user_profile_name)
    TextView mUserName;
    @BindView(R.id.bloodType) TextView mBloodType;
    @BindView(R.id.gender) TextView mGender;
    @BindView(R.id.emergencyContact1) TextView mEmergencyContactOne;
    @BindView(R.id.emergencyContact1phone) TextView mEmergencyContactOnePhone;
    @BindView(R.id.userPhoneIcon) ImageView mUserPhoneIcon;
    @BindView(R.id.userPhoneIcon2) ImageView mUserPhoneICon1;
    @BindView(R.id.userPhoneIcon3) ImageView mUserPhoneICon2;
    @BindView(R.id.emergencyContact2) TextView mEmergencyContactTwo;
    @BindView(R.id.emergencyContact2phone) TextView mEmergencyContactTwoPhone;
    @BindView(R.id.emergencyContact3) TextView mEmergencyContactThree;
    @BindView(R.id.emergencyContact3phone) TextView mEmergencyContactThreePhone;
    @BindView(R.id.listConditions) TextView mConditionsList;
    @BindView(R.id.listMedicalAllergies) TextView mListMedicalAllergies;
    @BindView(R.id.listotherAllergies) TextView mlistOtherAllergies;
    @BindView(R.id.userNationalId) TextView mNationalId;
    @BindView(R.id.insurancePolicyNo) TextView mPolicyNo;
    @BindView(R.id.userMedCover) TextView mMedCover;
    @BindView(R.id.userPrefHospital) TextView mPrefHospital;
    @BindView(R.id.userAge) TextView mUserAge;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public HomeScreenProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_screen_profile, container, false);
        // Inflate the layout for this fragment

        ButterKnife.bind(this,view);

        mEmergencyContactOnePhone.setOnClickListener(this);
        mUserPhoneIcon.setOnClickListener(this);
        mUserPhoneICon1.setOnClickListener(this);
        mUserPhoneICon2.setOnClickListener(this);
        mEmergencyContactTwoPhone.setOnClickListener(this);
        mEmergencyContactThreePhone.setOnClickListener(this);

        getProfileData();

        return view;

    }

    @Override
    public void onClick(View v){
        if(v == mEmergencyContactOnePhone){
            getProfileData();
            String dataPhoneNumberOne =  mEmergencyContactOnePhone.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:="+dataPhoneNumberOne));
            startActivity(intent);

        }
        if(v == mUserPhoneIcon){
            getProfileData();
            String dataPhoneNumberOne =  mEmergencyContactOnePhone.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:="+dataPhoneNumberOne));
            startActivity(intent);
        }

        if(v == mEmergencyContactTwoPhone){
            getProfileData();
            String dataPhoneNumberTwo =  mEmergencyContactTwoPhone.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:="+dataPhoneNumberTwo));
            startActivity(intent);
        }
        if(v == mUserPhoneICon1){
            getProfileData();
            String dataPhoneNumberTwo =  mEmergencyContactTwoPhone.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:="+dataPhoneNumberTwo));
            startActivity(intent);
        }

        if(v == mEmergencyContactThreePhone){
            getProfileData();
            String dataPhoneNumberThree =  mEmergencyContactThreePhone.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:="+dataPhoneNumberThree));
            startActivity(intent);
        }
        if(v == mUserPhoneICon1){
            getProfileData();
            String dataPhoneNumberThree =  mEmergencyContactThreePhone.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:="+dataPhoneNumberThree));
            startActivity(intent);
        }


    }

    public void getProfileData() {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String uid = users.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user").child(uid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //emergency contact model
                String person1 = dataSnapshot.child("emergencyContacts").child("emergencyContactNameOne").getValue().toString();
                String person1Cont = dataSnapshot.child("emergencyContacts").child("emergencyContactNumberOne").getValue().toString();
                String person2 = dataSnapshot.child("emergencyContacts").child("emergencyContactNameTwo").getValue().toString();
                String person2Cont = dataSnapshot.child("emergencyContacts").child("emergencyContactNumberTwo").getValue().toString();
                String person3 = dataSnapshot.child("emergencyContacts").child("emergencyContactNameThree").getValue().toString();
                String person3Cont = dataSnapshot.child("emergencyContacts").child("emergencyContactNumberThree").getValue().toString();
                mEmergencyContactOne.setText(person1);
                mEmergencyContactOnePhone.setText(person1Cont);
                mEmergencyContactTwo.setText(person2);
                mEmergencyContactTwoPhone.setText(person2Cont);
                mEmergencyContactThree.setText(person3);
                mEmergencyContactThreePhone.setText(person3Cont);

                // medical detail model

                String age = dataSnapshot.child("medicalDetails").child("age").getValue().toString();
                String condition = dataSnapshot.child("medicalDetails").child("condition").getValue().toString();
                String medAllergies = dataSnapshot.child("medicalDetails").child("medAllergies").getValue().toString();
                String name = dataSnapshot.child("medicalDetails").child("name").getValue().toString();
                String phoneNumber = dataSnapshot.child("medicalDetails").child("phoneNumber").getValue().toString();
                String otherAllegies = dataSnapshot.child("medicalDetails").child("userAllergies").getValue().toString();
                String gender = dataSnapshot.child("medicalDetails").child("gender").getValue().toString();
                String bloodType = dataSnapshot.child("medicalDetails").child("bloodGroup").getValue().toString();

                mUserAge.setText(age);
                mConditionsList.setText(condition);
                mListMedicalAllergies.setText(medAllergies);
                mUserName.setText(name);
                mlistOtherAllergies.setText(otherAllegies);
                mGender.setText(gender);
                mBloodType.setText(bloodType);

                //insurance model

                String medCover = dataSnapshot.child("insuranceDetails").child("medCover").getValue().toString();
                String natId = dataSnapshot.child("insuranceDetails").child("natId").getValue().toString();
                String policyNumber = dataSnapshot.child("insuranceDetails").child("policyNumber").getValue().toString();
                String prefHospital = dataSnapshot.child("insuranceDetails").child("prefHospital").getValue().toString();

                mNationalId.setText(natId);
                mPolicyNo.setText(policyNumber);
                mMedCover.setText(medCover);
                mPrefHospital.setText(prefHospital);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
