package com.example.mapdemo;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * The type Activite.
 */
public class Activite {
    private int id;
    private double nbKilometre;
    private String duree;
    private String dateActivite;
    private String heureActivite;
    private Type_activite type_activite;
    private Utilisateur utilisateur;

    /**
     * Instantiates a new Activite.
     */
    public Activite() {
    }

    /**
     * Instantiates a new Activite.
     *
     * @param id          the id
     * @param nbKilometre the nb kilometre
     * @param duree       the duree
     */
    public Activite(int id, double nbKilometre, String duree) {
        this.id = id;
        this.nbKilometre = nbKilometre;
        this.duree = duree;
    }

    /**
     * Instantiates a new Activite.
     *
     * @param id           the id
     * @param date         the date
     * @param heure        the heure
     * @param typeActivite the type activite
     * @param utilsateur   the utilsateur
     */
    public Activite(int id, String date, String heure, Type_activite typeActivite, Utilisateur utilsateur){
        this.dateActivite = date;
        this.heureActivite = heure;
        this.type_activite = typeActivite;
        this.utilisateur = utilsateur;
        this.id = id;


    }

    /**
     * Instantiates a new Activite.
     *
     * @param id            the id
     * @param nbKilometre   the nb kilometre
     * @param duree         the duree
     * @param dateActivite  the date activite
     * @param heureActivite the heure activite
     */
    public Activite(int id, long nbKilometre, String duree, String dateActivite, String heureActivite) {
        this.id = id;
        this.nbKilometre = nbKilometre;
        this.duree = duree;
        this.dateActivite = dateActivite;
        this.heureActivite = heureActivite;
    }

    /**
     * Instantiates a new Activite.
     *
     * @param id            the id
     * @param nbKilometre   the nb kilometre
     * @param duree         the duree
     * @param dateActivite  the date activite
     * @param heureActivite the heure activite
     * @param type_activite the type activite
     */
    public Activite(int id, long nbKilometre, String duree, String dateActivite, String heureActivite, Type_activite type_activite) {
        this.id = id;
        this.nbKilometre = nbKilometre;
        this.duree = duree;
        this.dateActivite = dateActivite;
        this.heureActivite = heureActivite;
        this.type_activite=type_activite;
    }


    /**
     * Instantiates a new Activite.
     *
     * @param id            the id
     * @param nbKilometre   the nb kilometre
     * @param duree         the duree
     * @param dateActivite  the date activite
     * @param heureActivite the heure activite
     * @param type_activite the type activite
     * @param utilisateur   the utilisateur
     */
    public Activite(int id, double nbKilometre, String duree, String dateActivite, String heureActivite, Type_activite type_activite, Utilisateur utilisateur) {
        this.id = id;
        this.nbKilometre = nbKilometre;
        this.duree = duree;
        this.dateActivite = dateActivite;
        this.heureActivite = heureActivite;
        this.type_activite = type_activite;
        this.utilisateur = utilisateur;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets nb kilometre.
     *
     * @return the nb kilometre
     */
    public double getNbKilometre() {
        return nbKilometre;
    }

    /**
     * Sets nb kilometre.
     *
     * @param nbKilometre the nb kilometre
     */
    public void setNbKilometre(double nbKilometre) {
        this.nbKilometre = nbKilometre;
    }

    /**
     * Gets duree.
     *
     * @return the duree
     */
    public String getDuree() {
        return duree;
    }

    /**
     * Sets duree.
     *
     * @param duree the duree
     */
    public void setDuree(String duree) {
        this.duree = duree;
    }

    /**
     * Gets date activite.
     *
     * @return the date activite
     */
    public String getDateActivite() {
        return dateActivite;
    }

    /**
     * Sets date activite.
     *
     * @param dateActivite the date activite
     */
    public void setDateActivite(String dateActivite) {
        this.dateActivite = dateActivite;
    }

    /**
     * Gets type activite.
     *
     * @return the type activite
     */
    public Type_activite getType_activite() {
        return type_activite;
    }

    /**
     * Sets type activite.
     *
     * @param type_activite the type activite
     */
    public void setType_activite(Type_activite type_activite) {
        this.type_activite = type_activite;
    }

    /**
     * Gets utilisateur.
     *
     * @return the utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Sets utilisateur.
     *
     * @param utilisateur the utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Gets heure activite.
     *
     * @return the heure activite
     */
    public String getHeureActivite() {
        return heureActivite;
    }

    /**
     * Sets heure activite.
     *
     * @param heureActivite the heure activite
     */
    public void setHeureActivite(String heureActivite) {
        this.heureActivite = heureActivite;
    }

    @Override
    public String toString() {
        return "Activite{" +
                "id=" + id +
                ", nbKilometre=" + nbKilometre +
                ", duree='" + duree + '\'' +
                ", dateActivite='" + dateActivite + '\'' +
                ", heureActivite='" + heureActivite + '\'' +
                '}';
    }
}
