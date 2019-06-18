package com.example.mapdemo;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /**
     * The My db.
     */
    DatabaseHelper myDb;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Spinner spinner;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;
    private Spinner spinner5;
    private Spinner spinner6;

    private Spinner spinActive;

    private String nomSelectionner;
    private int douleurTete;
    private int douleurBrasGauche;
    private int douleurBrasDroit;
    private int douleurHaut;
    private int douleurJambeGauche;
    private int getDouleurJambeDroite;

    private Type_activite typeActiviteEnCours;
    private Utilisateur utilisateurEnCours;
    private Activite activeEnCours;


    private String dateActivite;
    private String heureActivite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        if(myDb.getDataZoneDouleur().size() < 1){
            myDb.insertZoneDouleur("tete");
            myDb.insertZoneDouleur("bras_gauche");
            myDb.insertZoneDouleur("bras_droit");
            myDb.insertZoneDouleur("haut");
            myDb.insertZoneDouleur("jambe_gauche");
            myDb.insertZoneDouleur("jambe_droite");
        }
        Log.i("nbZoneDouleur", ""+myDb.getDataZoneDouleur().size());

        for(Zone_Douleur zd : myDb.getDataZoneDouleur()) {
            Log.i("detailZoneDouleur", "" + zd.getId() + " " + zd.getLibelle());
        }

        if(myDb.getDataUtilisateur().size() < 1){
            myDb.insertUtilisateur("defaut","defaut","defaut@defaut.fr","defaut","defaut",1,1,1,"Masculin");
            Log.i("idUtilisateur",""+myDb.getDataUtilisateur().get(0).getId());
        }
        Log.i("nbUtilisateur",""+myDb.getDataUtilisateur().size());

        if(myDb.getDataTypeActivite().size() < 1){
            myDb.insertTypeActivite("Course",true);
        }
        Log.i("nbTypeActivite",""+myDb.getDataTypeActivite().size());



        for(Type_activite ta : myDb.getDataTypeActivite()){
            Log.i("typeActivite", ""+ta.getLibelle() + " "+ta.getId());
        }

        for(Activite a : myDb.getDataActivite()){
            if(a.getId() == myDb.getIdDernierActiviteCree()){
                Log.i("destiny",a.toString());
                Log.i("destiny2",myDb.getDouleurZoneWithId(Integer.toString(a.getId()),"1").get(0).toString());

            }
        }



//        Log.i("maxid",""+myDb.getIdDernierActiviteCree());

        utilisateurEnCours = myDb.getUtilisateurWithId(1);
        Log.i("utilisateurTest", ""+utilisateurEnCours.toString());


        // 6 - Configure all views

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        spinner5 = findViewById(R.id.spinner5);
        spinner6 = findViewById(R.id.spinner6);
        spinActive = findViewById(R.id.spinner7);

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

        ArrayList<String> listActive = myDb.getLibelleTypeActivite();


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, contenuSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listActive);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);
        spinner6.setAdapter(adapter);
        spinActive.setAdapter(adapter2);


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

        switch (id){

            case R.id.activity_gestion_activites:
                Intent ga = new Intent(this, GestionActivities.class);
                startActivity(ga);
                break;
            case R.id.activity_stat:
                Intent s = new Intent(this,Stat.class);
                startActivity(s);
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
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    /**
     * Click button.
     *
     * @param v the v
     */
//bouton go maps
    public void clickButton (View v){
        //Toast.makeText(this, ""+spinActive.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

        //recup toutes les infos de la page
        douleurTete =Integer.parseInt(spinner4.getSelectedItem().toString());
        douleurBrasGauche =Integer.parseInt(spinner3.getSelectedItem().toString());
        douleurBrasDroit =Integer.parseInt(spinner2.getSelectedItem().toString());
        douleurHaut =Integer.parseInt(spinner.getSelectedItem().toString());
        douleurJambeGauche =Integer.parseInt(spinner5.getSelectedItem().toString());
        getDouleurJambeDroite =Integer.parseInt(spinner6.getSelectedItem().toString());
        nomSelectionner = spinActive.getSelectedItem().toString();

        typeActiviteEnCours = myDb.getTypeActiviteWithLibelle(nomSelectionner);
        Log.i("typeActiviteEncours", ""+typeActiviteEnCours.getLibelle() + " "+typeActiviteEnCours.getId());
        utilisateurEnCours = myDb.getUtilisateurWithId(1);
        Log.i("utilisateurTest", ""+utilisateurEnCours.getPrenom());
        activeEnCours = null;

//création de l'activité en base et après recup objet avec id
        dateActivite = new Date().toString().substring(0,10);
        heureActivite = new Date().toString().substring(11,19); // faire plus 2 au heure
        Log.i("gwendoulilou",dateActivite + "------" + heureActivite);
        if(myDb.insertActivite(0,"",dateActivite,heureActivite,utilisateurEnCours.getId(),typeActiviteEnCours.getId())){
            activeEnCours = new Activite(myDb.getIdDernierActiviteCree(),dateActivite,heureActivite,typeActiviteEnCours,utilisateurEnCours);
        }

//création de la douleur par zone
        myDb.insertDouleur(activeEnCours.getId(),1, douleurTete);
        myDb.insertDouleur(activeEnCours.getId(),2, douleurBrasGauche);
        myDb.insertDouleur(activeEnCours.getId(),3, douleurBrasDroit);
        myDb.insertDouleur(activeEnCours.getId(),4, douleurHaut);
        myDb.insertDouleur(activeEnCours.getId(),5, douleurJambeGauche);
        myDb.insertDouleur(activeEnCours.getId(),6, getDouleurJambeDroite);


      //  activeEnCours = new Activite(18,dateActivite,heureActivite,new Type_activite(1, "course",true),utilisateurEnCours);
        Intent a;
        if(activeEnCours.getType_activite().isKilometrique()){
            a = new Intent(this,MapDemoActivity.class);
        }
        else{
            a = new Intent(this,Chrono.class);
        }
        a.putExtra("idActivite", activeEnCours.getId());
        startActivity(a);
    }


}
