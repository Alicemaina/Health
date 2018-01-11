package com.example.alice.health;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment  implements View.OnClickListener {

    protected static final String TAG = ProfileFragment.class.getSimpleName();
    public static final int RESULT_GALLERY = 0;
    public StorageReference mStorage;
    protected String mCurrentPhotoPath;

    @BindView(R.id.header_cover_image) ImageView mLogoImage;
    @BindView(R.id.user_profile_photo) ImageButton mUserProfilePhoto;
    @BindView(R.id.editProfile) ImageView mEditProfile;
    @BindView(R.id.user_profile_name) TextView mUserName;
    @BindView(R.id.bloodType) TextView mBloodType;
    @BindView(R.id.gender) TextView mGender;
    @BindView(R.id.emergencyContact1) TextView mEmergencyContactOne;
    @BindView(R.id.emergencyContact1phone) TextView mEmergencyContactOnePhone;
    @BindView(R.id.emergencyContact2) TextView mEmergencyContactTwo;
    @BindView(R.id.emergencyContact2phone) TextView mEmergencyContactTwoPhone;
    @BindView(R.id.emergencyContact3) TextView mEmergencyContactThree;
    @BindView(R.id.emergencyContact3phone) TextView mEmergencyContactThreePhone;
    @BindView(R.id.listConditions) TextView mConditionsList;
    @BindView(R.id.listMedicalAllergies) TextView mListMedicalAllergies;
    @BindView(R.id.listotherAllergies) TextView mlistOtherAllergies;
    @BindView(R.id.userAge) TextView mUserAge;
    @BindView(R.id.userNationalId) TextView mNationalId;
    @BindView(R.id.insurancePolicyNo) TextView mPolicyNo;
    @BindView(R.id.userMedCover) TextView mMedCover;
    @BindView(R.id.userPrefHospital) TextView mPrefHospital;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStorage = FirebaseStorage.getInstance().getReference();
    }


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);

        mUserProfilePhoto.setOnClickListener(this);
        mEditProfile.setOnClickListener(this);

        getProfileData();


        return view;

    }

    @Override
    public void onClick(View v) {

        if(v==mUserProfilePhoto){
            Intent galleryIntent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent , RESULT_GALLERY );


        }
        if(v==mEditProfile){
            Fragment EditProfFrag = new EditProfileFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.mainFrame,EditProfFrag);
            transaction.commit();

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_GALLERY && resultCode == RESULT_OK){
            final Uri selectedImage = data.getData();

            Toast.makeText(getActivity(), "here!!", Toast.LENGTH_SHORT)
                    .show();

            StorageReference profileImagePath = mStorage.child("ProfileImages").child(selectedImage.getLastPathSegment());
            profileImagePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // if the upload is successful
                    @SuppressWarnings("VisibleForTests") Uri imageUri = taskSnapshot.getDownloadUrl();


                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // handel uploads fails

                }
            });
        }

        if (data != null) {
            final Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            mUserProfilePhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            mUserProfilePhoto.setEnabled(true);
            cursor.close();

            StorageReference profileImagePath = mStorage.child("ProfileImages").child(selectedImage.getLastPathSegment());
            profileImagePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(), "saved!!", Toast.LENGTH_SHORT)
                            .show();
                }
            });

        } else {
            Toast.makeText(getActivity(), "Try Again!!", Toast.LENGTH_SHORT)
                    .show();
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
                String person1= dataSnapshot.child("emergencyContacts").child("emergencyContactNameOne").getValue().toString();
                String  person1Cont = dataSnapshot.child("emergencyContacts").child("emergencyContactNumberOne").getValue().toString();
                String person2= dataSnapshot.child("emergencyContacts").child("emergencyContactNameTwo").getValue().toString();
                String  person2Cont = dataSnapshot.child("emergencyContacts").child("emergencyContactNumberTwo").getValue().toString();
                String person3= dataSnapshot.child("emergencyContacts").child("emergencyContactNameThree").getValue().toString();
                String  person3Cont = dataSnapshot.child("emergencyContacts").child("emergencyContactNumberThree").getValue().toString();
                mEmergencyContactOne.setText(person1);
                mEmergencyContactOnePhone.setText(person1Cont);
                mEmergencyContactTwo.setText(person2);
                mEmergencyContactTwoPhone.setText(person2Cont);
                mEmergencyContactThree.setText(person3);
                mEmergencyContactThreePhone.setText(person3Cont);

                // medical detail model

                String age= dataSnapshot.child("medicalDetails").child("age").getValue().toString();
                String  condition = dataSnapshot.child("medicalDetails").child("condition").getValue().toString();
                String medAllergies= dataSnapshot.child("medicalDetails").child("medAllergies").getValue().toString();
                String  name = dataSnapshot.child("medicalDetails").child("name").getValue().toString();
                String phoneNumber= dataSnapshot.child("medicalDetails").child("phoneNumber").getValue().toString();
                String  otherAllegies = dataSnapshot.child("medicalDetails").child("userAllergies").getValue().toString();
                String  gender = dataSnapshot.child("medicalDetails").child("gender").getValue().toString();
                String  bloodType = dataSnapshot.child("medicalDetails").child("bloodGroup").getValue().toString();

                mUserAge.setText(age);
                mConditionsList.setText(condition);
                mListMedicalAllergies.setText(medAllergies);
                mUserName.setText(name);
                mlistOtherAllergies.setText(otherAllegies);
                mGender.setText(gender);
                mBloodType.setText(bloodType);

                //insurance model

                String medCover= dataSnapshot.child("insuranceDetails").child("medCover").getValue().toString();
                String  natId = dataSnapshot.child("insuranceDetails").child("natId").getValue().toString();
                String policyNumber= dataSnapshot.child("insuranceDetails").child("policyNumber").getValue().toString();
                String  prefHospital = dataSnapshot.child("insuranceDetails").child("prefHospital").getValue().toString();

                mNationalId.setText(natId);
                mPolicyNo.setText(policyNumber);
                mMedCover.setText(medCover);
                mPrefHospital.setText(prefHospital);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//
    }



}
