package com.example.alice.health;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback {
    double lat = 0;
    double lon = 0;
    public MapView mMapView;
    public Context context;
    public FloatingActionButton mFab;

    public LocationFragment() {
        // Required empty public constructor
    }

    public static LocationFragment newInstance(Location location) {

        Bundle args = new Bundle();

        args.putString("lat", String.valueOf(location.getLatitude()));
        args.putString("lon", String.valueOf(location.getLongitude()));

        LocationFragment fragment = new LocationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            lat = Double.parseDouble(getArguments().getString("lat"));
            lon = Double.parseDouble(getArguments().getString("lon"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        mFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final String text = String.format("Latitude %.6f, Longitude %.6f",
                lat,
                lon);
        Log.d("location", text);

        return rootView;
    }
    @Override
    public void onMapReady(GoogleMap map) {
        LatLng latLng = new LatLng(lat,lon);

        if (map != null) {
            MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.style);
            map.setMapStyle(style);
            map.addMarker(new MarkerOptions()
                    .position(latLng).title("Find Me").snippet("Susan Njoroge: 29,Female, (AB-), Asthmatic. Allergic to Napronex")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    .draggable(false).visible(true));
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mMapView != null) {
            try {
                mMapView.onDestroy();
            } catch (NullPointerException e) {
                Log.e("mapview", "Error while attempting MapView.onDestroy(), ignoring exception", e);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }


}

