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

import com.example.alice.health.models.EmergencyContacts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */

public class EmergencyContactsFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.emergencyContact) EditText mEmergencyContactName;
    @BindView(R.id.emergencyNumber) EditText mEmergencyNumber;
    @BindView(R.id.emergencyContact1) EditText mEmergencyContactName1;
    @BindView(R.id.emergencyNumber1) EditText mEmergencyNumber1;
    @BindView(R.id.emergencyContact2) EditText mEmergencyContactName2;
    @BindView(R.id.emergencyNumber2) EditText mEmergencyNumber2;
    @BindView(R.id.emergencyContactBtn) Button mEmergencyContactBtn;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_emergency_contacts, container, false);

        ButterKnife.bind(this,view);
        mEmergencyContactBtn.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View v){

        if(v==mEmergencyContactBtn){

            // save emergency contact to firebase
            saveToFirebase();

            // clear input fields after saving to firebase
            mEmergencyContactName.setText("");
            mEmergencyNumber.setText("");
            mEmergencyContactName1.setText("");
            mEmergencyNumber1.setText("");
            mEmergencyContactName2.setText("");
            mEmergencyNumber2.setText("");

            Fragment InsuranceDetail = new InsuranceDetailFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.mainFrame,InsuranceDetail);
            transaction.commit();
        }
    }

    public void saveToFirebase(){

        String emergencyContactName = mEmergencyContactName.getText().toString();
        String emergencyContactNumber = mEmergencyNumber.getText().toString();
        String emergencyContactName1 = mEmergencyContactName1.getText().toString();
        String emergencyContactNumber1 = mEmergencyNumber1.getText().toString();
        String emergencyContactName2 = mEmergencyContactName2.getText().toString();
        String emergencyContactNumber2 = mEmergencyNumber2.getText().toString();

        FirebaseUser users= FirebaseAuth.getInstance().getCurrentUser();
        String uid=users.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user").child(uid);

        EmergencyContacts emergencyContact = new EmergencyContacts (emergencyContactName,emergencyContactNumber,emergencyContactName1,emergencyContactNumber1,emergencyContactName2,emergencyContactNumber2);

        reference.child("emergencyContacts").setValue(emergencyContact);

        Toast.makeText(getActivity(), " Details Saved", Toast.LENGTH_SHORT).show();
    }

}
