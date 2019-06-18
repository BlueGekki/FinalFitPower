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
 * The type Stat.
 */
public class Stat extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Spinner spinner;
    /**
     * The My db.
     */
    static DatabaseHelper myDb;
    private WebView WebViewGraph1;
    private WebView WebViewGraph2;

    private TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();


        myDb = new DatabaseHelper(this);
        spinner = findViewById(R.id.spinner);
        error = findViewById(R.id.textView);

        ArrayList<String> lesActivite = new ArrayList();

        lesActivite = myDb.getLibelleTypeActivite();


        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                lesActivite
        );


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
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
            case R.id.activity_main :
                Intent a = new Intent(this,MainActivity.class);
                startActivity(a);
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
     * Convertir duree en minutes int.
     *
     * @param laDuree the la duree
     * @return the int
     */
    public int convertirDureeEnMinutes(String laDuree){
        int dureeMinutes=0;

        int heure=Integer.parseInt(laDuree.substring(0,2))*60;
        int minute=Integer.parseInt(laDuree.substring(3,5));
        int seconde=Integer.parseInt(laDuree.substring(6,8))/60;

        dureeMinutes=heure+minute+seconde;

        return dureeMinutes;
    }

    /**
     * Click button.
     *
     * @param v the v
     */
    public void clickButton (View v) {
        String leTypeAct = spinner.getSelectedItem().toString();
        Type_activite ta = myDb.getTypeActiviteWithLibelle(leTypeAct);

        ArrayList<Activite> lesActivites = new ArrayList<Activite>();
        lesActivites = myDb.getDataActiviteWithId("" + ta.getId(), "1");

        ArrayList<Douleur_Par_Zone> lesDouleurs = new ArrayList<Douleur_Par_Zone>();
        lesDouleurs = myDb.getDouleursWithId("" + ta.getId());

        if (lesActivites.size() > 0 ) {
            error.setText("");
            WebViewGraph1 = findViewById(R.id.graph1);
            String lienGraph2 = "http://chart.apis.google.com/chart?chxt=x,y&chbh=a,2,10&chco=FF0000,0000FF&cht=bvg&chs=360x150&chd=t:";
            String duree = "";
            String date = "";
            for (int i = 0; i < lesActivites.size(); i++) {
                if (!lesActivites.get(i).getDuree().equals("")) {
                    duree += convertirDureeEnMinutes(lesActivites.get(i).getDuree()) + ",";
                    date += lesActivites.get(i).getDateActivite().substring(4) + " " + lesActivites.get(i).getHeureActivite().substring(0,5) + "|";
                }
            }
            lienGraph2 += duree.substring(0, duree.length() - 1) + "&chl=" + date.substring(0, date.length() - 1);
            Log.i("caca",""+lienGraph2);
            WebViewGraph1.loadUrl(lienGraph2);


            if (ta.isKilometrique()) {
                WebViewGraph2 = findViewById(R.id.graph2);
                String lienGraph3 = "http://chart.apis.google.com/chart?chxt=x,y&chco=FF0000&chbh=a,2,10&cht=bvg&chs=360x150&chd=t:";
                String distance = "";
                String date2 = "";
                for (int i = 0; i < lesActivites.size(); i++) {
                    if (!lesActivites.get(i).getDuree().equals("")) {
                        int km=(int)Math.round(lesActivites.get(i).getNbKilometre());
                        distance +=km + ",";
                        date2 += lesActivites.get(i).getDateActivite().substring(4) + " " + lesActivites.get(i).getHeureActivite().substring(0,5) + "|";
                    }

                }
                lienGraph3 += distance.substring(0, distance.length() - 1) + "&chl=" + date2.substring(0, date2.length() - 1);
                Log.i("caca",""+lienGraph3);
                WebViewGraph2.loadUrl(lienGraph3);
            } else {
                WebViewGraph2 = findViewById(R.id.graph2);
                WebViewGraph2.loadUrl("");
            }

        }
        else{
            error.setText("Vous n'avez pas effectué cette activité");
            WebViewGraph1 = findViewById(R.id.graph1);
            WebViewGraph1.loadUrl("");
            WebViewGraph2 = findViewById(R.id.graph2);
            WebViewGraph2.loadUrl("");
        }

    }
}

