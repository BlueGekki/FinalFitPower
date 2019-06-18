package com.example.mapdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * The type After activity.
 */
public class AfterActivity extends AppCompatActivity {

    /**
     * The My db.
     */
    DatabaseHelper myDb;

    private Spinner spinner;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;
    private Spinner spinner5;
    private Spinner spinner6;

    private int douleurTete;
    private int douleurBrasGauche;
    private int douleurBrasDroit;
    private int douleurHaut;
    private int douleurJambeGauche;
    private int getDouleurJambeDroite;

    private int idActivite;
    private String duree;
    private float distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);

        myDb = new DatabaseHelper(this);

        Intent recup = getIntent();
        idActivite = 0;
        duree = "";
        if(recup != null){
            if(recup.hasExtra("idActivite")){
                idActivite = recup.getIntExtra("idActivite",0);
                Log.i("finalidActivite",""+idActivite);
            }
            if(recup.hasExtra("duree")){
                duree = recup.getStringExtra("duree");
                Log.i("finalduree",""+duree);
            }
            if(recup.hasExtra("distance")){
                distance = recup.getFloatExtra("distance",0);
                Log.i("finaldistance",""+distance);
            }
        }

        Log.i("finallibelleviaid",""+myDb.getLibelleTypeActiviteWithIdAct(idActivite));

        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        spinner5 = findViewById(R.id.spinner5);
        spinner6 = findViewById(R.id.spinner6);

        List<String> contenuSpinner = new ArrayList<>();
        contenuSpinner.add("0");
        contenuSpinner.add("1");
        contenuSpinner.add("2");
        contenuSpinner.add("3");
        contenuSpinner.add("4");
        contenuSpinner.add("5");
        contenuSpinner.add("6");
        contenuSpinner.add("7");
        contenuSpinner.add("8");
        contenuSpinner.add("9");
        contenuSpinner.add("10");


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, contenuSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);
        spinner6.setAdapter(adapter);
    }

    /**
     * Click button.
     *
     * @param v the v
     */
    public void clickButton (View v){

        douleurTete =Integer.parseInt(spinner4.getSelectedItem().toString());
        douleurBrasGauche =Integer.parseInt(spinner3.getSelectedItem().toString());
        douleurBrasDroit =Integer.parseInt(spinner2.getSelectedItem().toString());
        douleurHaut =Integer.parseInt(spinner.getSelectedItem().toString());
        douleurJambeGauche =Integer.parseInt(spinner5.getSelectedItem().toString());
        getDouleurJambeDroite =Integer.parseInt(spinner6.getSelectedItem().toString());


        myDb.UpdateDouleur(douleurTete,Integer.toString(idActivite),Integer.toString(1));
        myDb.UpdateDouleur(douleurBrasGauche,Integer.toString(idActivite),Integer.toString(2));
        myDb.UpdateDouleur(douleurBrasDroit,Integer.toString(idActivite),Integer.toString(3));
        myDb.UpdateDouleur(douleurHaut,Integer.toString(idActivite),Integer.toString(4));
        myDb.UpdateDouleur(douleurJambeGauche,Integer.toString(idActivite),Integer.toString(5));
        myDb.UpdateDouleur(getDouleurJambeDroite,Integer.toString(idActivite),Integer.toString(6));


        myDb.UpdateActivite(distance,duree,Integer.toString(idActivite));

        Toast.makeText(this, "Fin de l'activité", Toast.LENGTH_SHORT).show();

        Intent a = new Intent(this,MainActivity.class);
        startActivity(a);
    }
}
