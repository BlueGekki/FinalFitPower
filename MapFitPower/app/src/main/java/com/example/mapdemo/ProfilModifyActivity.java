package com.example.mapdemo;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Profil modify activity.
 */
public class ProfilModifyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The My db.
     */
    DatabaseHelper myDb;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    /**
     * The User.
     */
    Utilisateur user;

    private EditText editTextMail;
    private EditText editTextNom;
    private EditText editTextPrenom;
    private EditText editTextAge;
    private EditText editTextTaille;
    private EditText editTextPoid;
    private Spinner spinnerGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_modify);

        myDb = new DatabaseHelper(this);

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        user = myDb.getUtilisateurWithId(1);

        editTextMail = findViewById(R.id.editTextMail);
        editTextMail.setText(user.getMail());

        editTextNom = findViewById(R.id.editTextNom);
        editTextNom.setText(user.getNom());

        editTextPrenom = findViewById(R.id.editTextPrenom);
        editTextPrenom.setText(user.getPrenom());

        editTextAge = findViewById(R.id.editTextAge);
        editTextAge.setText(Integer.toString(user.getAge()));

        editTextTaille = findViewById(R.id.editTextTaille);
        editTextTaille.setText(Long.toString(user.getTaille()));

        editTextPoid = findViewById(R.id.editTextPoid);
        editTextPoid.setText(Long.toString(user.getTaille()));

        spinnerGenre = findViewById(R.id.spinnerGenre);

        List<String> contenuSpinner = new ArrayList<>();
        if(user.getGenre().equals("Masculin")){
            contenuSpinner.add("Masculin");
            contenuSpinner.add("Feminin");
        }
        else if(user.getGenre().equals("Feminin")){
            contenuSpinner.add("Feminin");
            contenuSpinner.add("Masculin");
        }
        else{
            contenuSpinner.add(user.getGenre());
            contenuSpinner.add("Masculin");
            contenuSpinner.add("Feminin");
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, contenuSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGenre.setAdapter(adapter);


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

            case R.id.activity_gestion_activites:
                Intent ga = new Intent(this, GestionActivities.class);
                startActivity(ga);
                break;

            case R.id.activity_profil:
                Intent p = new Intent(this,ProfilActivity.class);
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
    public void clickButton (View v){
        Log.i("returninfo",editTextMail.getText().toString());
        myDb.updateUtilisateur(editTextMail.getText().toString(), editTextNom.getText().toString(), editTextPrenom.getText().toString(), Integer.parseInt(editTextAge.getText().toString()),Long.parseLong(editTextTaille.getText().toString()),Long.parseLong(editTextPoid.getText().toString()),spinnerGenre.getSelectedItem().toString());

        Intent p = new Intent(this, ProfilActivity.class);
        startActivity(p);
    }
}