package pratik.com.shauryatoday.dewasii_01.UI;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import pratik.com.shauryatoday.dewasii_01.R;
import pratik.com.shauryatoday.dewasii_01.UI.categories.Health.FitnessCenter.healthFitnessCenterDetail;
import pratik.com.shauryatoday.dewasii_01.UI.categories.Health.MedicalStore.healthMedicalStoreDetail;
import pratik.com.shauryatoday.dewasii_01.UI.categories.Health.hospital.healthHospitalDetail;
import pratik.com.shauryatoday.dewasii_01.pojos.health.FitnessCenter;
import pratik.com.shauryatoday.dewasii_01.pojos.health.Hospital;
import pratik.com.shauryatoday.dewasii_01.pojos.health.MedicalStore;
import pratik.com.shauryatoday.dewasii_01.utility.Constant;

public class seeOnMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static double LONG = 22.967028;
    private static double LATI = 76.057092;
    private static int i = 0;
    private static ArrayList coordinateList;
    private static String objectCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_all_hospital_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Intent intent = getIntent();
        objectCategory = intent.getStringExtra(Constant.INTENT_PASSING_MAP_CLASSNAME);
        switch (objectCategory){
//            case "hospital":
//                coordinateList = new ArrayList<Hospital>();
//                break;
//            case "fitnessCenter":
//                coordinateList = new ArrayList<FitnessCenter>();
//                break;
        }

        coordinateList = intent.getParcelableArrayListExtra(Constant.INTENT_PASSING_LIST_0N_MAP);

        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LONG = location.getLongitude();
                LATI = location.getLatitude();
                // Add a marker in Sydney and move the camera
                LatLng toMe = new LatLng(LATI, LONG);
                mMap.addMarker(new MarkerOptions().position(toMe).title("Me").zIndex(100000)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toMe,14.5f));
                onMapReady(mMap);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.INTERNET
                },10);
            }
            return;
        } else {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        LatLng toMe = new LatLng(LATI, LONG);
        switch (objectCategory){
            case Constant.CLASS_NAME_HEALTH_HOSPITAL:
                for(i = 0; i < coordinateList.size(); i++){
                    Hospital fitnessCenter = (Hospital) coordinateList.get(i);
                    double latitude = Double.parseDouble(fitnessCenter.getLatitude());
                    double longitude = Double.parseDouble(fitnessCenter.getLongitude());
                    if(latitude > LATI + 0.090000 && longitude > + 0.090000){
//                Log.e("Map Marker Status", "Don't Show = "+hospital.getName()+" lat :- "+ latitude+" , "+ LATI + 0.000500+ " and  lat :- "+ longitude+" , "+ LONG + 0.000500);
                    } else{
//                Log.e("Map Marker Status", "Show = "+hospital.getName()+" lat :- "+ latitude+" , "+ LATI + 0.000500+ " and  lat :- "+ longitude+" , "+ LONG + 0.000500);
                        LatLng toFitnessCenter = new LatLng(Double.parseDouble(fitnessCenter.getLatitude()), Double.parseDouble(fitnessCenter.getLongitude()));
                        mMap.addMarker(new MarkerOptions().position(toFitnessCenter)
                                .title(fitnessCenter.getName())
                                .zIndex((float) i)
                                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.health_hospital_map_marker)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toFitnessCenter,15.0f));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                int zIndex = (int) marker.getZIndex();
                                if(zIndex == 100000){

                                } else{
                                    Intent intent = new Intent(getApplicationContext(), healthHospitalDetail.class);
                                    intent.putExtra(Constant.INTENT_PASSING_SINGLE_HEALTH_HOSPITAL, (Parcelable) coordinateList.get(zIndex));
                                    startActivity(intent);
                                }
                                return false;
                            }
                        });
                    }
                }
                mMap.addMarker(new MarkerOptions().position(toMe).title("Me").zIndex(100000)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toMe,14.5f));
                break;
            case Constant.CLASS_NAME_HEALTH_FITNESS_CENTER:
                for(i = 0; i < coordinateList.size(); i++){
                    FitnessCenter fitnessCenter = (FitnessCenter) coordinateList.get(i);
                    double latitude = Double.parseDouble(fitnessCenter.getLatitude());
                    double longitude = Double.parseDouble(fitnessCenter.getLongitude());
                    if(latitude > LATI + 0.090000 && longitude > + 0.090000){
//                Log.e("Map Marker Status", "Don't Show = "+hospital.getName()+" lat :- "+ latitude+" , "+ LATI + 0.000500+ " and  lat :- "+ longitude+" , "+ LONG + 0.000500);
                    } else{
//                Log.e("Map Marker Status", "Show = "+hospital.getName()+" lat :- "+ latitude+" , "+ LATI + 0.000500+ " and  lat :- "+ longitude+" , "+ LONG + 0.000500);
                        LatLng toFitnessCenter = new LatLng(Double.parseDouble(fitnessCenter.getLatitude()), Double.parseDouble(fitnessCenter.getLongitude()));
                        mMap.addMarker(new MarkerOptions().position(toFitnessCenter)
                                .title(fitnessCenter.getName())
                                .zIndex((float) i)
                                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.health_hospital_map_marker)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toFitnessCenter,15.0f));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                int zIndex = (int) marker.getZIndex();
                                if(zIndex == 100000){

                                } else{
                                    Intent intent = new Intent(getApplicationContext(), healthFitnessCenterDetail.class);
                                    intent.putExtra(Constant.INTENT_PASSING_SINGLE_HEALTH_FITNESS_CENTER, (Parcelable) coordinateList.get(zIndex));
                                    startActivity(intent);
                                }
                                return false;
                            }
                        });
                    }
                }
                mMap.addMarker(new MarkerOptions().position(toMe).title("Me").zIndex(100000)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toMe,14.5f));
                break;
            case Constant.CLASS_NAME_HEALTH_MEDICAL_STORE:
                for(i = 0; i < coordinateList.size(); i++){
                    MedicalStore fitnessCenter = (MedicalStore) coordinateList.get(i);
                    double latitude = Double.parseDouble(fitnessCenter.getLatitude());
                    double longitude = Double.parseDouble(fitnessCenter.getLongitude());
                    if(latitude > LATI + 0.090000 && longitude > + 0.090000){
//                Log.e("Map Marker Status", "Don't Show = "+hospital.getName()+" lat :- "+ latitude+" , "+ LATI + 0.000500+ " and  lat :- "+ longitude+" , "+ LONG + 0.000500);
                    } else{
//                Log.e("Map Marker Status", "Show = "+hospital.getName()+" lat :- "+ latitude+" , "+ LATI + 0.000500+ " and  lat :- "+ longitude+" , "+ LONG + 0.000500);
                        LatLng toFitnessCenter = new LatLng(Double.parseDouble(fitnessCenter.getLatitude()), Double.parseDouble(fitnessCenter.getLongitude()));
                        mMap.addMarker(new MarkerOptions().position(toFitnessCenter)
                                .title(fitnessCenter.getName())
                                .zIndex((float) i)
                                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.health_hospital_map_marker)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toFitnessCenter,15.0f));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                int zIndex = (int) marker.getZIndex();
                                if(zIndex == 100000){

                                } else{
                                    Intent intent = new Intent(getApplicationContext(), healthMedicalStoreDetail.class);
                                    intent.putExtra(Constant.INTENT_PASSING_SINGLE_HEALTH_MEDICAL_STORE, (Parcelable) coordinateList.get(zIndex));
                                    startActivity(intent);
                                }
                                return false;
                            }
                        });
                    }
                }
                mMap.addMarker(new MarkerOptions().position(toMe).title("Me").zIndex(100000)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toMe,14.5f));
                break;
        }


    }

}
