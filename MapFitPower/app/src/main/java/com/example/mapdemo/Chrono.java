package com.example.mapdemo;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Chrono.
 */
public class Chrono extends AppCompatActivity {

    private String etatCourse = "enAttente"; //enCour, enAttente, Fini, enPause
    private double tempActivite = 0;
    private Date dateDebut;
    private Chronometer chronometer;
    private long pauseOffset;

    private int idActivite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrono);

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

        if (this.etatCourse == "enCour" || this.etatCourse == "enPause") {
            this.chronometer.stop();
            // pas oublier de prendre le screen
            this.chronometer.setBase(SystemClock.elapsedRealtime() - this.pauseOffset);
            //calcule la difference de temps entre la date de début et maintenant
            this.tempActivite += getDuree();
            String tempsActiviteRetour = new SimpleDateFormat("HH:mm:ss").format(this.tempActivite);


            Log.i("Durée", tempsActiviteRetour + "");
            Log.i("Date début", this.dateDebut + "");
           // Toast.makeText(this, "Durée : " + tempsActiviteRetour + "", Toast.LENGTH_SHORT).show();

            this.etatCourse = "Fini";
            this.chronometer.setBase(SystemClock.elapsedRealtime());
            this.pauseOffset = 0;

            Intent a = new Intent(this, AfterActivity.class);
            a.putExtra("idActivite",idActivite);
            a.putExtra("duree", tempsActiviteRetour);
            startActivity(a);

            this.tempActivite = 0;
        }

    }
    //retourne la difference de temps entre la date de début et maintenant

    /**
     * Get duree long.
     *
     * @return the long
     */
    public long getDuree(){

        Date date = new Date();
        Log.i("date début", this.dateDebut.getTime() + "");
        Log.i("date MTN", date.getTime() + "");

        return date.getTime() - this.dateDebut.getTime();
    }
}
