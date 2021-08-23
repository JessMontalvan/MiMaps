package com.example.mimaps.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mimaps.Maps;
import com.example.mimaps.R;
import com.example.mimaps.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Button btnUb, btnMap;
    private EditText txtLat;
    private EditText txtLong;
    private EditText txtAlt;
    private LocationManager locManager;
    private Location loc;
    private double lat, lon, alt;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnUb=root.findViewById(R.id.buttonUb);
        btnMap=root.findViewById(R.id.buttonMap);
        txtLat=root.findViewById(R.id.editLat);
        txtLong=root.findViewById(R.id.editLong);
        txtAlt=root.findViewById(R.id.editAlt);

        btnUb.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onClick(View v) {
        if(v==btnUb){
            miposion();

        }
        if(v==btnMap){
            verificar();
        }
    }


    public void miposion() {
        if (ContextCompat.checkSelfPermission ((Activity) getContext (), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions ((Activity) getContext (),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }
        locManager = (LocationManager) getActivity ().getSystemService (Context.LOCATION_SERVICE);
        loc = locManager.getLastKnownLocation (LocationManager.PASSIVE_PROVIDER);

        lat = loc.getLatitude ();
        lon = loc.getLongitude ();
        alt = loc.getAltitude ();

        txtLat.setText (lat + "");
        txtLong.setText (lon + "");
        txtAlt.setText (alt + "");

    }/*else{

            Toast.makeText(getContext()," Gps desabilitado",Toast.LENGTH_LONG).show();

        }

    }*/
    public void verificar(){
        if ( !txtLong.getText().toString().equals("") || !txtLat.getText().toString().equals("")){
            Intent intent = new Intent(getContext(), Maps.class);
            intent.putExtra ("latitud", lat + "");
            intent.putExtra ("longitud", lon + "");
            startActivity(intent);
        }else{
            Toast.makeText(getContext(),"Los campos no estan llenos", Toast.LENGTH_LONG).show();
        }
    }
}