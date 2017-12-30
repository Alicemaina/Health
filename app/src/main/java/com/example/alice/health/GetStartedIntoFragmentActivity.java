package com.example.alice.health;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alice on 12/30/17.
 */

public class GetStartedIntoFragmentActivity extends Fragment implements View.OnClickListener {
    @Override
    public void onClick(View v) {

    }

    @BindView(R.id.btn_get_started)
    TextView mGetStarted;

    public GetStartedIntoFragmentActivity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.get_started_intro, container, false);

        ButterKnife.bind(this, view);

        mGetStarted.setOnClickListener(this);
        return view;
    }
}
