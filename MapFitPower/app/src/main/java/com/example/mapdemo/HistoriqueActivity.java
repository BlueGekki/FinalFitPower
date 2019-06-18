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
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The type Historique activity.
 */
public class HistoriqueActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The My db.
     */
    DatabaseHelper myDb;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    private TextView textViewTemps;
    private TextView textViewDistance;
    private Spinner spinAct;

    private Activite activiteEnCours;

    private WebView WebViewGraph1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        myDb = new DatabaseHelper(this);

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();


        textViewTemps = findViewById(R.id.textView2);
        textViewDistance = findViewById(R.id.textView3);

        spinAct = findViewById(R.id.spinner);


        ArrayList<String> hitler = new ArrayList<>();

        for(Activite a : myDb.getActivities()){
            Log.i("testdure",a.getId() + "   "+a.getDuree().length());
            hitler.add(a.getType_activite().getLibelle()+" - "+a.getDateActivite()+","+a.getHeureActivite());
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, hitler);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinAct.setAdapter(adapter);


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
        activiteEnCours = null;
        for(Activite a : myDb.getActivities()){
            if(spinAct.getSelectedItem().toString().equals(a.getType_activite().getLibelle()+" - "+a.getDateActivite()+","+a.getHeureActivite())){
                activiteEnCours = a;
                Log.i("listedur",a.getDuree());
            }
        }
        if(activiteEnCours.getType_activite().isKilometrique()){
            textViewDistance.setText("La distance parcouru a été de : "+activiteEnCours.getNbKilometre() + " km");
        }
        else{
            textViewTemps.setText("");
        }

        textViewTemps.setText("Le durée de l'activité a été de : "+activiteEnCours.getDuree().substring(3));


        ArrayList<Douleur_Par_Zone> lesDouleurs = new ArrayList<Douleur_Par_Zone>();
        lesDouleurs=myDb.getDouleursWithId(Integer.toString(activiteEnCours.getId()));


        WebViewGraph1 = findViewById(R.id.graph1);
        String lienGraph1="http://chart.apis.google.com/chart?chxt=x,y&chxr=1,0,10&chdl=douleurAvant|douleurApres&chdlp=b&chbh=a,2,10&chco=B6DF83,F09D6F&cht=bvo&chs=365x225&chd=t:";
        String douleurAvant="";
        String douleurApres="";
        for (int i=0;i<lesDouleurs.size();i++){

                douleurAvant+=lesDouleurs.get(i).getDouleurAvant()*10+",";
                douleurApres+=lesDouleurs.get(i).getDouleurApres()*10+",";

        }
        lienGraph1+=douleurAvant.substring(0,douleurAvant.length()-1)+"|"+douleurApres.substring(0,douleurApres.length()-1)+"&chl=Tête|Bras_G|Bras_D|Buste|Jambe_G|Jambe_D";
        WebViewGraph1.loadUrl(lienGraph1);
    }
}
