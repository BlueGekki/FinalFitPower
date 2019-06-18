package com.example.mapdemo;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * The type Gestion activities.
 */
public class GestionActivities extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    /**
     * The My db.
     */
    DatabaseHelper myDb;
    private Spinner spinActive;
    private EditText zoneText;
    private CheckBox check;
    private boolean rep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_activities);

        myDb = new DatabaseHelper(this);

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();


        spinActive = findViewById(R.id.spinner);
        zoneText = findViewById(R.id.editText);
        check = findViewById(R.id.checkBox);

        ArrayList<String> listActive = myDb.getLibelleTypeActivite();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listActive);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinActive.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_main:
                Intent a = new Intent(this, MainActivity.class);
                startActivity(a);
                break;

            case R.id.activity_stat:
                Intent s = new Intent(this, Stat.class);
                startActivity(s);
                break;

            case R.id.activity_profil:
                Intent p = new Intent(this, ProfilActivity.class);
                startActivity(p);
                break;

            case R.id.activity_historique:
                Intent h = new Intent(this, HistoriqueActivity.class);
                startActivity(h);
                break;

            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
    private void configureToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView() {
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Click button.
     *
     * @param v the v
     */
    public void clickButton(View v) {
        if (zoneText.getText().toString().equals("")) {
            Toast.makeText(this, "Veuillez remplir le champ Nom", Toast.LENGTH_SHORT).show();
        } else {
            if(verifTypeActivite(textMaj(zoneText.getText().toString())) == true){
                rep = (check.isChecked());
                if(myDb.insertTypeActivite(textMaj(zoneText.getText().toString()),rep)) {
                    remplirSpinner();
                    Toast.makeText(this, "L'activité '" + zoneText.getText().toString() + "' a bien été ajoutée", Toast.LENGTH_SHORT).show();
                    zoneText.setText("");
                    check.setChecked(false);
                }

            }
            else{
                Toast.makeText(this, "Activité déjà présente", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Remplir spinner.
     */
    public void remplirSpinner(){

        ArrayList<String> listActive = myDb.getLibelleTypeActivite();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listActive);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinActive.setAdapter(adapter);
    }

    /**
     * Verif type activite boolean.
     *
     * @param nomActivite the nom activite
     * @return the boolean
     */
// Verification insertion Type Activité
    public boolean verifTypeActivite(String nomActivite){
        ArrayList<String> lesActivite = new ArrayList();
        lesActivite = myDb.getLibelleTypeActivite();

        int i=0;
        boolean verifNom=false;
        while (i<lesActivite.size() && !lesActivite.get(i).equals(nomActivite)){
            i++;
        }
        if(i >= lesActivite.size()){
            verifNom=true;
        }

        return verifNom;
    }

    /**
     * Text maj string.
     *
     * @param leString the le string
     * @return the string
     */
    public String textMaj(String leString){
        return leString.substring(0,1).toUpperCase()+leString.substring(1).toLowerCase();
    }
}