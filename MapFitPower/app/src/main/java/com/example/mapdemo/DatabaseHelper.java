package com.example.mapdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * The type Database helper.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * The constant DATABASE_NAME.
     */
    public static final String DATABASE_NAME = "Fitpower.db";

    /**
     * The constant TABLE_NAME_1.
     */
    public static final String TABLE_NAME_1 = "utilisateur";
    /**
     * The constant UTIL_COL_1.
     */
    public static final String UTIL_COL_1 = "IdUtilisateur";
    /**
     * The constant UTIL_COL_2.
     */
    public static final String UTIL_COL_2 = "Login";
    /**
     * The constant UTIL_COL_3.
     */
    public static final String UTIL_COL_3 = "Mdp";
    /**
     * The constant UTIL_COL_4.
     */
    public static final String UTIL_COL_4 = "Mail";
    /**
     * The constant UTIL_COL_5.
     */
    public static final String UTIL_COL_5 = "Nom";
    /**
     * The constant UTIL_COL_6.
     */
    public static final String UTIL_COL_6 = "Prenom";
    /**
     * The constant UTIL_COL_7.
     */
    public static final String UTIL_COL_7 = "Age";
    /**
     * The constant UTIL_COL_8.
     */
    public static final String UTIL_COL_8 = "Taille";
    /**
     * The constant UTIL_COL_9.
     */
    public static final String UTIL_COL_9 = "Poids";
    /**
     * The constant UTIL_COL_10.
     */
    public static final String UTIL_COL_10 = "Genre";

    /**
     * The constant TABLE_NAME_2.
     */
    public static final String TABLE_NAME_2 = "Activite";
    /**
     * The constant ACT_COL_1.
     */
    public static final String ACT_COL_1 = "IdActivite";
    /**
     * The constant ACT_COL_2.
     */
    public static final String ACT_COL_2 = "NbKM";
    /**
     * The constant ACT_COL_3.
     */
    public static final String ACT_COL_3 = "Durée";
    /**
     * The constant ACT_COL_4.
     */
    public static final String ACT_COL_4 = "Date";
    /**
     * The constant ACT_COL_5.
     */
    public static final String ACT_COL_5 = "Heure";
    /**
     * The constant ACT_COL_6.
     */
    public static final String ACT_COL_6 = "IdUtilisateur";
    /**
     * The constant ACT_COL_7.
     */
    public static final String ACT_COL_7 = "IdTypeActivite";

    /**
     * The constant TABLE_NAME_3.
     */
    public static final String TABLE_NAME_3 = "Type_Activite";
    /**
     * The constant TYP_ACT_COL_1.
     */
    public static final String TYP_ACT_COL_1 = "IdTypeActivite";
    /**
     * The constant TYP_ACT_COL_2.
     */
    public static final String TYP_ACT_COL_2 = "Libelle";
    /**
     * The constant TYP_ACT_COL_3.
     */
    public static final String TYP_ACT_COL_3 = "SportKilometrique";

    /**
     * The constant TABLE_NAME_4.
     */
    public static final String TABLE_NAME_4 = "Douleur_Par_Zone";
    /**
     * The constant DOULEUR_COL_1.
     */
    public static final String DOULEUR_COL_1 = "IdActivite";
    /**
     * The constant DOULEUR_COL_2.
     */
    public static final String DOULEUR_COL_2 = "IdZone";
    /**
     * The constant DOULEUR_COL_3.
     */
    public static final String DOULEUR_COL_3 = "DouleurAvant";
    /**
     * The constant DOULEUR_COL_4.
     */
    public static final String DOULEUR_COL_4 = "DouleurApres";

    /**
     * The constant TABLE_NAME_5.
     */
    public static final String TABLE_NAME_5 = "Zone_Douleur";
    /**
     * The constant ZONE_DOULEUR_COL_1.
     */
    public static final String ZONE_DOULEUR_COL_1 = "IdZone";
    /**
     * The constant ZONE_DOULEUR_COL_2.
     */
    public static final String ZONE_DOULEUR_COL_2 = "LibelleZone";


    /**
     * Instantiates a new Database helper.
     *
     * @param context the context
     */
    public DatabaseHelper( Context context) {

        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table "+TABLE_NAME_1 + "(IdUtilisateur INTEGER PRIMARY KEY AUTOINCREMENT, Login TEXT, Mdp TEXT, Mail TEXT, Nom TEXT, Prenom TEXT, Age INTEGER, Taille INTEGER, Poids INTEGER, Genre TEXT)");
        db.execSQL("Create table "+TABLE_NAME_3 + "(IdTypeActivite INTEGER PRIMARY KEY AUTOINCREMENT, Libelle TEXT, SportKilometrique TEXT)");
        db.execSQL("Create table "+TABLE_NAME_2 + "(IdActivite INTEGER PRIMARY KEY AUTOINCREMENT, NbKM FLOAT, Durée TEXT, Date TEXT, Heure TEXT, IdUtilisateur INTEGER, IdTypeActivite INTEGER, FOREIGN KEY (IdUtilisateur) REFERENCES "+TABLE_NAME_1+"(IdUtilisateur), FOREIGN KEY (IdTypeActivite) REFERENCES "+TABLE_NAME_3+"(IdTypeActivite))");
        db.execSQL("Create table "+TABLE_NAME_5 + "(IdZone INTEGER PRIMARY KEY AUTOINCREMENT, LibelleZone TEXT)");
        db.execSQL("Create table "+TABLE_NAME_4 + "(IdActivite INTEGER, IdZone INTEGER,DouleurAvant INTEGER, DouleurApres INTEGER, PRIMARY KEY(IdActivite,IdZone))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_4);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_5);

        onCreate(db);
    }

    /**
     * Insert utilisateur boolean.
     *
     * @param login  the login
     * @param mdp    the mdp
     * @param mail   the mail
     * @param nom    the nom
     * @param prenom the prenom
     * @param age    the age
     * @param taille the taille
     * @param poids  the poids
     * @param genre  the genre
     * @return the boolean
     */
    public boolean insertUtilisateur(String login, String mdp, String mail, String nom, String prenom, int age, int taille, int poids, String genre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UTIL_COL_2,login);
        contentValues.put(UTIL_COL_3,mdp);
        contentValues.put(UTIL_COL_4,mail);
        contentValues.put(UTIL_COL_5,nom);
        contentValues.put(UTIL_COL_6,prenom);
        contentValues.put(UTIL_COL_7,age);
        contentValues.put(UTIL_COL_8,taille);
        contentValues.put(UTIL_COL_9,poids);
        contentValues.put(UTIL_COL_10,genre);
        long result = db.insert(TABLE_NAME_1,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     * Insert type activite boolean.
     *
     * @param Libelle           the libelle
     * @param SportKilometrique the sport kilometrique
     * @return the boolean
     */
    public boolean insertTypeActivite(String Libelle, Boolean SportKilometrique){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TYP_ACT_COL_2,Libelle);
        contentValues.put(TYP_ACT_COL_3,SportKilometrique);
        long result = db.insert(TABLE_NAME_3,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     * Insert activite boolean.
     *
     * @param NbKM           the nb km
     * @param Duree          the duree
     * @param Date           the date
     * @param Heure          the heure
     * @param IdUtilisateur  the id utilisateur
     * @param IdTypeActivite the id type activite
     * @return the boolean
     */
    public boolean insertActivite(float NbKM, String Duree, String Date, String Heure, int IdUtilisateur, int IdTypeActivite){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACT_COL_2,NbKM);
        contentValues.put(ACT_COL_3,Duree);
        contentValues.put(ACT_COL_4,Date);
        contentValues.put(ACT_COL_5,Heure);
        contentValues.put(ACT_COL_6,IdUtilisateur);
        contentValues.put(ACT_COL_7,IdTypeActivite);
        long result = db.insert(TABLE_NAME_2,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     * Get id dernier activite cree int.
     *
     * @return the int
     */
    public int getIdDernierActiviteCree(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> array_list = new ArrayList<Integer>();
        Cursor res = db.rawQuery("select IdActivite from "+TABLE_NAME_2,null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(res.getInt(res.getColumnIndex("IdActivite")));
            res.moveToNext();
        }
        return array_list.get(array_list.size()-1);
    }


    /**
     * Insert zone douleur boolean.
     *
     * @param LibelleZone the libelle zone
     * @return the boolean
     */
    public boolean insertZoneDouleur(String LibelleZone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ZONE_DOULEUR_COL_2,LibelleZone);
        long result = db.insert(TABLE_NAME_5,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     * Insert douleur boolean.
     *
     * @param IdActivite   the id activite
     * @param IdZone       the id zone
     * @param DouleurAvant the douleur avant
     * @return the boolean
     */
    public boolean insertDouleur(int IdActivite, int IdZone, int DouleurAvant){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DOULEUR_COL_1,IdActivite);
        contentValues.put(DOULEUR_COL_2,IdZone);
        contentValues.put(DOULEUR_COL_3,DouleurAvant);
        contentValues.put(DOULEUR_COL_4,0);
        long result = db.insert(TABLE_NAME_4,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     * Get libelle type activite array list.
     *
     * @return the array list
     */
    public ArrayList<String>  getLibelleTypeActivite(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery("select Libelle from "+TABLE_NAME_3,null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
           // if(!array_list.contains(res.getString(res.getColumnIndex("Libelle")))){
                array_list.add(res.getString(res.getColumnIndex("Libelle")));
           // }
            res.moveToNext();
        }
        return array_list;
    }

    /**
     * Get data utilisateur array list.
     *
     * @return the array list
     */
    public ArrayList<com.example.mapdemo.Utilisateur> getDataUtilisateur(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<com.example.mapdemo.Utilisateur> array_list = new ArrayList<com.example.mapdemo.Utilisateur>();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME_1,null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(new Utilisateur(res.getInt(res.getColumnIndex("IdUtilisateur")),res.getString(res.getColumnIndex("Login")),res.getString(res.getColumnIndex("Mdp")),res.getString(res.getColumnIndex("Mail")),res.getString(res.getColumnIndex("Nom")),res.getString(res.getColumnIndex("Prenom")),res.getInt(res.getColumnIndex("Age")),res.getLong(res.getColumnIndex("Taille")),res.getLong(res.getColumnIndex("Poids")),res.getString(res.getColumnIndex("Genre"))));
            res.moveToNext();
        }
        return array_list;

    }

    /**
     * Get data activite array list.
     *
     * @return the array list
     */
    public ArrayList<com.example.mapdemo.Activite> getDataActivite(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<com.example.mapdemo.Activite> array_list = new ArrayList<com.example.mapdemo.Activite>();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME_2,null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(new Activite(res.getInt(res.getColumnIndex("IdActivite")),res.getLong(res.getColumnIndex("NbKM")), res.getString(res.getColumnIndex("Durée"))));
            res.moveToNext();
        }
        return array_list;

    }

    /**
     * Get data type activite array list.
     *
     * @return the array list
     */
    public ArrayList<com.example.mapdemo.Type_activite> getDataTypeActivite(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<com.example.mapdemo.Type_activite> array_list = new ArrayList<com.example.mapdemo.Type_activite>();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME_3,null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            boolean value = res.getInt(res.getColumnIndex("SportKilometrique"))>0;
            array_list.add(new Type_activite(res.getInt(res.getColumnIndex("IdTypeActivite")),res.getString(res.getColumnIndex("Libelle")),value));
            res.moveToNext();
        }
        return array_list;

    }

    /**
     * Get data douleur array list.
     *
     * @return the array list
     */
    public ArrayList<Douleur_Par_Zone> getDataDouleur(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Douleur_Par_Zone> array_list = new ArrayList<Douleur_Par_Zone>();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME_4,null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(new Douleur_Par_Zone(res.getInt(res.getColumnIndex("DouleurAvant")),res.getInt(res.getColumnIndex("DouleurApres"))));

            res.moveToNext();
        }
        return array_list;

    }

    /**
     * Get data zone douleur array list.
     *
     * @return the array list
     */
    public ArrayList<Zone_Douleur> getDataZoneDouleur(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Zone_Douleur> array_list = new ArrayList<Zone_Douleur>();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME_5,null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(new Zone_Douleur(res.getInt(res.getColumnIndex("IdZone")),res.getString(res.getColumnIndex("LibelleZone"))));
            res.moveToNext();
        }
        return array_list;
    }

    /**
     * Delete type activite.
     *
     * @param libelleActivite the libelle activite
     */
    public void deleteTypeActivite(String libelleActivite){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Type_Activite","Libelle=?",new String[]{libelleActivite});

    }

    /**
     * Get type activite with libelle type activite.
     *
     * @param nomAct the nom act
     * @return the type activite
     */
    public Type_activite getTypeActiviteWithLibelle(String nomAct){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Type_activite> array_list = new ArrayList<Type_activite>();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME_3+ " Where Libelle=?",new String[]{nomAct});
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            boolean value = res.getInt(res.getColumnIndex("SportKilometrique"))>0;
            array_list.add(new Type_activite(res.getInt(res.getColumnIndex("IdTypeActivite")),res.getString(res.getColumnIndex("Libelle")),value));
            res.moveToNext();
        }
        return array_list.get(0);
    }

    /**
     * Get utilisateur with id com . example . mapdemo . utilisateur.
     *
     * @param idUtil the id util
     * @return the com . example . mapdemo . utilisateur
     */
    public com.example.mapdemo.Utilisateur getUtilisateurWithId(int idUtil){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<com.example.mapdemo.Utilisateur> array_list = new ArrayList<com.example.mapdemo.Utilisateur>();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME_1+ " Where IdUtilisateur="+idUtil,null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(new Utilisateur(res.getInt(res.getColumnIndex("IdUtilisateur")),res.getString(res.getColumnIndex("Login")),res.getString(res.getColumnIndex("Mdp")),res.getString(res.getColumnIndex("Mail")),res.getString(res.getColumnIndex("Nom")),res.getString(res.getColumnIndex("Prenom")),res.getInt(res.getColumnIndex("Age")),res.getLong(res.getColumnIndex("Taille")),res.getLong(res.getColumnIndex("Poids")),res.getString(res.getColumnIndex("Genre"))));
            res.moveToNext();
        }
        return array_list.get(0);

    }

    /**
     * Update douleur boolean.
     *
     * @param douleurApres the douleur apres
     * @param idAct        the id act
     * @param idZone       the id zone
     * @return the boolean
     */
    public boolean UpdateDouleur(int douleurApres, String idAct, String idZone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DouleurApres",douleurApres);

        String[] args = new String[]{idAct,idZone};
        db.update("Douleur_Par_Zone",contentValues, "IdActivite=? And IdZone=?",args);
        return true;
    }

    /**
     * Update activite.
     *
     * @param nbKm  the nb km
     * @param duree the duree
     * @param idAct the id act
     */
    public void UpdateActivite(float nbKm, String duree, String idAct){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NbKM",nbKm);
        contentValues.put("Durée",duree);

        String[] args = new String[]{idAct};
        db.update("Activite",contentValues, "IdActivite=?",args);

    }

    /**
     * Get libelle type activite with id array list.
     *
     * @param idAct the id act
     * @return the array list
     */
    public ArrayList<String> getLibelleTypeActiviteWithId(int idAct){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery("select Libelle from "+TABLE_NAME_3+ " Where IdTypeActivite="+idAct,null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("Libelle")));
            res.moveToNext();
        }
        return array_list;
    }

    /**
     * Get libelle type activite with id act string.
     *
     * @param idActivite the id activite
     * @return the string
     */
    public String getLibelleTypeActiviteWithIdAct(int idActivite){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> array_list = new ArrayList<Integer>();
        Cursor res = db.rawQuery("select IdTypeActivite from "+TABLE_NAME_2+ " Where IdActivite="+idActivite,null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(res.getInt(res.getColumnIndex("IdTypeActivite")));
            res.moveToNext();
        }


        ArrayList<String> array_list_libelle = new ArrayList<String>();
        array_list_libelle.add(getLibelleTypeActiviteWithId(array_list.get(0)).get(0));
        return array_list_libelle.get(0);
    }

    /**
     * Get douleur zone with id array list.
     *
     * @param idAct  the id act
     * @param idZone the id zone
     * @return the array list
     */
    public ArrayList<Douleur_Par_Zone> getDouleurZoneWithId(String idAct, String idZone){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Douleur_Par_Zone> array_list = new ArrayList<Douleur_Par_Zone>();
        Cursor res = db.rawQuery("select * from Douleur_Par_Zone where IdActivite=? And IdZone=?",new String[]{idAct,idZone});
        res.moveToFirst();
        while(res.isAfterLast() == false) {

            array_list.add(new Douleur_Par_Zone(res.getInt(res.getColumnIndex("DouleurAvant")),res.getInt(res.getColumnIndex("DouleurApres"))));

            res.moveToNext();
        }
        return array_list;

    }

    /**
     * Update utilisateur.
     *
     * @param mail   the mail
     * @param nom    the nom
     * @param prenom the prenom
     * @param age    the age
     * @param taille the taille
     * @param poid   the poid
     * @param genre  the genre
     */
    public void updateUtilisateur(String mail, String nom, String prenom, int age, long taille, long poid, String genre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Mail",mail);
        contentValues.put("Nom",nom);
        contentValues.put("Prenom",prenom);
        contentValues.put("Age",age);
        contentValues.put("Taille",taille);
        contentValues.put("Poids",poid);
        contentValues.put("Genre",genre);

        db.update("utilisateur",contentValues, "IdUtilisateur=1",null);

    }

    /**
     * Get data activite with id array list.
     *
     * @param idTypeAct the id type act
     * @param idUtil    the id util
     * @return the array list
     */
    public ArrayList<com.example.mapdemo.Activite> getDataActiviteWithId(String idTypeAct, String idUtil){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<com.example.mapdemo.Activite> array_list = new ArrayList<com.example.mapdemo.Activite>();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME_2+" where IdTypeActivite=? And IdUtilisateur=?",new String[]{idTypeAct,idUtil});
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(new Activite(res.getInt(res.getColumnIndex("IdActivite")),res.getLong(res.getColumnIndex("NbKM")), res.getString(res.getColumnIndex("Durée")),res.getString(res.getColumnIndex("Date")),res.getString(res.getColumnIndex("Heure"))));
            res.moveToNext();
        }
        return array_list;

    }

    /**
     * Get douleurs with id array list.
     *
     * @param idAct the id act
     * @return the array list
     */
    public ArrayList<Douleur_Par_Zone> getDouleursWithId(String idAct){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Douleur_Par_Zone> array_list = new ArrayList<Douleur_Par_Zone>();
        Cursor res = db.rawQuery("select * from Douleur_Par_Zone where IdActivite=?",new String[]{idAct});
        res.moveToFirst();
        while(res.isAfterLast() == false) {

            array_list.add(new Douleur_Par_Zone(res.getInt(res.getColumnIndex("DouleurAvant")),res.getInt(res.getColumnIndex("DouleurApres"))));

            res.moveToNext();
        }
        return array_list;

    }

    /**
     * Get activities array list.
     *
     * @return the array list
     */
    public ArrayList<Activite> getActivities(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Activite> array_list = new ArrayList<Activite>();
        Cursor res = db.rawQuery("select * from Activite where Durée!=''",null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {

            array_list.add(new Activite(res.getInt(res.getColumnIndex("IdActivite")),res.getLong(res.getColumnIndex("NbKM")),res.getString(res.getColumnIndex("Durée")),res.getString(res.getColumnIndex("Date")),res.getString(res.getColumnIndex("Heure")), getTypeActiviteWithId(""+res.getInt(res.getColumnIndex("IdTypeActivite")))));

            res.moveToNext();
        }
        for(int i=0;i<array_list.size();i++){
            if(array_list.get(i).getDuree() == null || array_list.get(i).getDuree().equals("") || array_list.get(i).getDuree().length() == 0){

                array_list.remove(i);
            }
        }

        ArrayList<Activite> ab = new ArrayList<>();
        for(int j = array_list.size()-1;j >= 0;j--){
            ab.add(array_list.get(j));
        }

        return ab;
    }

    /**
     * Get type activite with id type activite.
     *
     * @param idType the id type
     * @return the type activite
     */
    public Type_activite getTypeActiviteWithId(String idType){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Type_activite> array_list = new ArrayList<>();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME_3+ " Where IdTypeActivite=?",new String[]{idType});
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            boolean value = res.getInt(res.getColumnIndex("SportKilometrique"))>0;
            array_list.add(new Type_activite(res.getString(res.getColumnIndex("Libelle")),value));
            res.moveToNext();
        }
        return array_list.get(0);
    }

}