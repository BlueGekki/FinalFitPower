package com.example.mapdemo;

/**
 * The type Douleur par zone.
 */
public class Douleur_Par_Zone {
    private int douleurAvant;
    private int douleurApres;
    private Zone_Douleur zone;
    private Activite activite;

    /**
     * Instantiates a new Douleur par zone.
     */
    public Douleur_Par_Zone() {
    }

    /**
     * Instantiates a new Douleur par zone.
     *
     * @param douleurAvant the douleur avant
     * @param douleurApres the douleur apres
     */
    public Douleur_Par_Zone(int douleurAvant, int douleurApres) {
        this.douleurAvant = douleurAvant;
        this.douleurApres = douleurApres;

    }

    /**
     * Instantiates a new Douleur par zone.
     *
     * @param douleurAvant the douleur avant
     * @param douleurApres the douleur apres
     * @param zone         the zone
     * @param activite     the activite
     */
    public Douleur_Par_Zone(int douleurAvant, int douleurApres, Zone_Douleur zone, Activite activite) {
        this.douleurAvant = douleurAvant;
        this.douleurApres = douleurApres;
        this.zone = zone;
        this.activite = activite;
    }

    /**
     * Gets douleur avant.
     *
     * @return the douleur avant
     */
    public int getDouleurAvant() {
        return douleurAvant;
    }

    /**
     * Sets douleur avant.
     *
     * @param douleurAvant the douleur avant
     */
    public void setDouleurAvant(int douleurAvant) {
        this.douleurAvant = douleurAvant;
    }

    /**
     * Gets douleur apres.
     *
     * @return the douleur apres
     */
    public int getDouleurApres() {
        return douleurApres;
    }

    /**
     * Sets douleur apres.
     *
     * @param douleurApres the douleur apres
     */
    public void setDouleurApres(int douleurApres) {
        this.douleurApres = douleurApres;
    }

    /**
     * Gets zone.
     *
     * @return the zone
     */
    public Zone_Douleur getZone() {
        return zone;
    }

    /**
     * Sets zone.
     *
     * @param zone the zone
     */
    public void setZone(Zone_Douleur zone) {
        this.zone = zone;
    }

    /**
     * Gets activite.
     *
     * @return the activite
     */
    public Activite getActivite() {
        return activite;
    }

    /**
     * Sets activite.
     *
     * @param activite the activite
     */
    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    @Override
    public String toString() {
        return "Douleur_Par_Zone{" +
                "douleurAvant=" + douleurAvant +
                ", douleurApres=" + douleurApres +
                '}';
    }
}
