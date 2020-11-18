package com.example.appdrisonet.Acitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.appdrisonet.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    double lat, lon;
    double lat_conductir, lon_conductor;
    String key_envio;
    DatabaseReference reference,reference2,reference3;
    Location location;
    LocationManager lm;
    private LocationManager locManager;
    private Location loc;
    Button btn_enviar_posicion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null) {
            Log.v("Localizacion", location.getLatitude() + " y " + location.getLongitude());
            lm.removeUpdates(this);
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // Agregamos un marcador en Lima y movemos la cámara
        //   LatLng ubicacion = new LatLng(-12.0560155, -77.0617879);
        //   mMap.animateCamera(CameraUpdateFactory.newLatLng(ubicacion));
        //  mMap.setMinZoomPreference(6.0f);
        // mMap.setMaxZoomPreference(14.0f);
        //   LatLng cliente = new LatLng(lat, lon);

        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        LatLng aquitoy = new LatLng(latitude, longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(aquitoy)
                .zoom(12)//esto es el zoom
                .bearing(30)//esto es la inclonacion
                .build();
        LatLng cliente = new LatLng(lat, lon);

        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.icotaxi)).anchor(0.1f,0.5f).position(cliente).title("Cliente"));
    }
}