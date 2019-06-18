package com.example.mapdemo;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

/**
 * The type Map demo activity.
 */
@RuntimePermissions
public class MapDemoActivity extends AppCompatActivity {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private LocationRequest mLocationRequest;
    /**
     * The M current location.
     */
    Location mCurrentLocation;
    private long UPDATE_INTERVAL = 1;  /* 10 s */
    private long FASTEST_INTERVAL = 1000; /* 1 s */
    private String etatCourse = "enAttente"; //enCour, enAttente, Fini, enPause
    //trace dur la map
    private ArrayList<PolylineOptions> maTrace = new ArrayList<PolylineOptions>();
    private Button btnSuivi; //activer ou désactiver le suivi
    /**
     * The Suivi pos.
     */
    boolean suiviPos = true;
    //temps
    private double tempActivite = 0;
    private Date dateDebut;
    private Chronometer chronometer;
    private long pauseOffset;
    private final static String KEY_LOCATION = "location";
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private int idActivite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_demo_activity);
        if (TextUtils.isEmpty(getResources().getString(R.string.google_maps_api_key))) {
            throw new IllegalStateException("You forgot to supply a Google Maps API key");
        }

        if (savedInstanceState != null && savedInstanceState.keySet().contains(KEY_LOCATION)) {
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
                }
            });
        } else {
            //Toast.makeText(this, "Erreur - Map Fragment est null!!", Toast.LENGTH_SHORT).show();
        }
        //button suivi
        this.btnSuivi = (Button) this.findViewById(R.id.suivi);
        this.btnSuivi.setBackgroundColor(Color.GREEN);
        //Chronomètre
        this.chronometer = findViewById(R.id.chronometer);

        Intent recup = getIntent();
        idActivite = 0;
        if(recup != null){
            if(recup.hasExtra("idActivite")){
                idActivite = recup.getIntExtra("idActivite",0);
                Log.i("verifPasserId",""+idActivite);
            }
        }
    }

    /**
     * Start chronometer.
     *
     * @param v the v
     */
    public void startChronometer(View v) {
        if (this.etatCourse != "enCour") {
            this.maTrace.add(new PolylineOptions());
            // initialise la date
            this.dateDebut = new Date();

            this.chronometer.setBase(SystemClock.elapsedRealtime() - this.pauseOffset);
            this.chronometer.start();
            this.etatCourse = "enCour";
        }
    }

    /**
     * Pause chronometer.
     *
     * @param v the v
     */
    public void pauseChronometer(View v) {
        if (this.etatCourse == "enCour") {
            this.chronometer.stop();

            //mise en pause du temps de l'activité
            this.tempActivite += getDuree();

            this.pauseOffset = SystemClock.elapsedRealtime() - this.chronometer.getBase();
            this.etatCourse = "enPause";

        }
    }

    /**
     * Rest chronometer.
     *
     * @param v the v
     */
    public void restChronometer(View v) {
        if (this.etatCourse == "enCour" || this.etatCourse == "enPause" ) {
            this.chronometer.stop();

            this.chronometer.setBase(SystemClock.elapsedRealtime() - this.pauseOffset);

            //calcule la difference de temps entre la date de début et mtn
            this.tempActivite += getDuree();
            String tempsActiviteRetour = new SimpleDateFormat("HH:mm:ss").format(this.tempActivite);

            Log.i("Durée", tempsActiviteRetour + "");
            Log.i("Date début", this.dateDebut + "");
            //Toast.makeText(this, "Durée : " + tempsActiviteRetour + "", Toast.LENGTH_SHORT).show();

            this.etatCourse = "Fini";
            this.chronometer.setBase(SystemClock.elapsedRealtime());
            this.pauseOffset = 0;

            //calcule la distance parcourue
            float distance = 0;

            for (int i = 0; i < this.maTrace.size(); i++) {
                for (int b = 0; b < this.maTrace.get(i).getPoints().size() - 1; b++) {
                    distance = distance + this.getDistance(new LatLng(this.maTrace.get(i).getPoints().get(b).latitude, this.maTrace.get(i).getPoints().get(b).longitude)
                            , new LatLng(this.maTrace.get(i).getPoints().get(b + 1).latitude, this.maTrace.get(i).getPoints().get(b + 1).longitude));
                }
            }
            Toast.makeText(this, "Distance : " + distance + "", Toast.LENGTH_SHORT).show();
            Log.i("Distance", distance + "");


            Intent a = new Intent(this, AfterActivity.class);
            a.putExtra("idActivite",idActivite);
            a.putExtra("duree", tempsActiviteRetour);
            a.putExtra("distance",distance);
            startActivity(a);

            this.tempActivite = 0;
            this.maTrace.clear();
            this.map.clear();
        }
    }

    // retourne la distance entre deux points
    private float getDistance(LatLng my_latlong, LatLng frnd_latlong) {
        Location l1 = new Location("1");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);

        Location l2 = new Location("2");
        l2.setLatitude(frnd_latlong.latitude);
        l2.setLongitude(frnd_latlong.longitude);

        //distance M
        float distance = l1.distanceTo(l2);
        //distance KM
        distance = distance / 1000.0f;

        return distance;
    }

    /**
     * Gets duree.
     *
     * @return the duree
     */
//retourne la difference de temps entre la date de début et maintenant
    public long getDuree() {
        Date date = new Date();
        Log.i("date début", this.dateDebut.getTime() + "");
        Log.i("date MTN", date.getTime() + "");
        return date.getTime() - this.dateDebut.getTime();
    }

    /**
     * Suivi position.
     *
     * @param v the v
     */
// Permet de suivre la postion
    public void suiviPosition(View v) {
        if (this.suiviPos == true) {
            this.suiviPos = false;
            this.btnSuivi.setBackgroundColor(Color.RED);
        } else {
            this.suiviPos = true;
            this.btnSuivi.setBackgroundColor(Color.GREEN);
        }
    }

    /**
     * Load map.
     *
     * @param googleMap the google map
     */
    protected void loadMap(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            // Map is ready
            //Toast.makeText(this, "Map Fragment was loaded properly!", Toast.LENGTH_SHORT).show();
            MapDemoActivityPermissionsDispatcher.getMyLocationWithPermissionCheck(this);
            MapDemoActivityPermissionsDispatcher.startLocationUpdatesWithPermissionCheck(this);
        } else {
            //Toast.makeText(this, "Erreur - Map est null!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MapDemoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /**
     * Gets my location.
     */
    @SuppressWarnings({"MissingPermission"})
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void getMyLocation() {
        map.setMyLocationEnabled(true);

        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    /*
     * Appelé lorsque l'activité devient visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
       /* if (wl != null) {
            wl.acquire();
        }*/
    }

    /*
     * Appelé lorsque l'activité n'est plus visible.
     */
    @Override
    protected void onStop() {
        super.onStop();
        /*if (wl != null) {
            wl.release();
        }*/
    }


    /**
     * Start location updates.
     */
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());

    }

    /**
     * On location changed.
     *
     * @param location the location
     */
    public void onLocationChanged(Location location) {
        // le GPS peut être désactivé
        if (location == null) {
            return;
        }

        //Signaler à l'utilisateur que l'emplacement a été mis à jour

        mCurrentLocation = location;
        String msg = "Mise à jour localisation: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        //Actualise la map
        if (mCurrentLocation != null) {
            if(this.suiviPos == true){
                //Toast.makeText(this, "La localisation GPS a été trouvée!", Toast.LENGTH_SHORT).show();
                LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                map.animateCamera(cameraUpdate);
            }


            //Tracé du GPS
            if(this.etatCourse == "enCour"){

                this.maTrace.get(this.maTrace.size()-1).add(new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude()));
                //this.maTrace.add(new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude()));
                this.map.clear();

                for(int i = 0 ; i < this.maTrace.size() ; i++){
                   this.map.addPolyline(this.maTrace.get(i));
                }
            }


        }
        else {
            //Toast.makeText(this, "La position actuelle est nulle, activez le GPS !", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * The type Error dialog fragment.
     */
// Définir un DialogFragment qui affiche la boîte de dialogue d'erreur
    public static class ErrorDialogFragment extends android.support.v4.app.DialogFragment {

        // Champ global contenant la boîte de dialogue d'erreur
        private Dialog mDialog;

        /**
         * Instantiates a new Error dialog fragment.
         */
// Constructeur par défaut. Définit le champ de dialogue sur null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        /**
         * Sets dialog.
         *
         * @param dialog the dialog
         */
// modifie la boite de dialogue
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        // retourne la boite de dialogue.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

}
