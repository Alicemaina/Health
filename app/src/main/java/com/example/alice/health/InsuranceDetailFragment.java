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
import android.widget.Toast;

import com.example.alice.health.models.InsuranceDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alice on 1/1/18.
 */



public class InsuranceDetailFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.userNationalId)
    EditText mNationalId;
    @BindView(R.id.insurancePolicyNo) EditText mPolicyNo;
    @BindView(R.id.userMedCover) EditText mMedCover;
    @BindView(R.id.userPrefHospital) EditText mPrefHospital;
    @BindView(R.id.userBtn)
    Button mUserBtn;

    public InsuranceDetailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_insurance_detail, container, false);

        ButterKnife.bind(this,view);

        mUserBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        if (v == mUserBtn) {
            saveToFirebase();

            mNationalId.setText("");
            mPolicyNo.setText("");
            mMedCover.setText("");
            mPrefHospital.setText("");

            Fragment getStarted = new MainFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.mainFrame,getStarted);
            transaction.commit();
        }
    }

    public void saveToFirebase(){
        String nationalId = mNationalId.getText().toString();
        String policyNumber = mPolicyNo.getText().toString();
        String medicalCover = mMedCover.getText().toString();
        String preferedHospital = mPrefHospital.getText().toString();

        FirebaseUser users= FirebaseAuth.getInstance().getCurrentUser();
        String uid=users.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user").child(uid);

        InsuranceDetails insuranceDetails = new InsuranceDetails(nationalId, policyNumber,medicalCover, preferedHospital);

        reference.child("insuranceDetails").setValue(insuranceDetails);

        Toast.makeText(getActivity(), " Details Saved", Toast.LENGTH_SHORT).show();
    }
}

