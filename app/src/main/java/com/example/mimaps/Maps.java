package com.example.mimaps;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mimaps.databinding.ActivityMapsBinding;

public class Maps extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, View.OnClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    double latitud, longitud;
    SharedPreferences preferences;
    private Button LimpiarMarcas, Favorito;
    float valorLat, valorLon;
    LatLng miUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LimpiarMarcas = (Button) findViewById(R.id.btnLimpiar);
        Favorito = (Button) findViewById(R.id.btnFavorito);

    }

    @SuppressLint ("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        latitud = Double.parseDouble(getIntent().getStringExtra("latitud"));
        longitud = Double.parseDouble(getIntent().getStringExtra("longitud"));
        // Add a marker in Sydney and move the camera
        miUbicacion = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions()
                .position(miUbicacion)
                .title("Mi ubicacion")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion, 16));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapLongClickListener(this);
        mMap.setMyLocationEnabled(true);
        LimpiarMarcas.setOnClickListener(this);
        Favorito.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if (v == LimpiarMarcas) {
            mMap.clear ();
            Toast.makeText (this, "Marcas Eliminadas", Toast.LENGTH_LONG).show ();
        }
        if (v == Favorito) {
            Toast.makeText(Maps.this, "Posición Favorita" + " Latitud: " + valorLat + " Longitud; "+ valorLon, Toast.LENGTH_SHORT).show();
            miUbicacion = new LatLng(valorLat, valorLon);
            mMap.addMarker(new MarkerOptions()
                    .position(miUbicacion)
                    .title("Posición Favorita")
                    .icon(BitmapDescriptorFactory.fromResource (R.drawable.favoritos)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion, 15));
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(Maps.this, "Click position" + latLng.latitude+latLng.longitude, Toast.LENGTH_SHORT).show();
        mMap.addMarker(new MarkerOptions().position(latLng).title("MiUbicacion"));
        GuardarPreferences(latLng);

        System.out.println (latitud + longitud);

    }
    public void GuardarPreferences(LatLng latLng){

        preferences = getSharedPreferences("Favorito", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat("latitud", (float) latLng.latitude);
        editor.putFloat("longitud", (float) latLng.longitude);
        System.out.println (latitud + longitud);
        editor.commit();
        leerPreferences(latLng);

    }
    public LatLng leerPreferences(LatLng latLng){
        preferences = getSharedPreferences("Favorito", Context.MODE_PRIVATE);
        valorLat = preferences.getFloat("latitud",(float) latLng.latitude);
        valorLon = preferences.getFloat("longitud", (float) latLng.longitude);
        return latLng;
    }

}