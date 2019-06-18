package com.example.mapdemo;

/**
 * The type Zone douleur.
 */
public class Zone_Douleur {
    private int id;
    private String libelle;

    /**
     * Instantiates a new Zone douleur.
     */
    public Zone_Douleur() {
    }

    /**
     * Instantiates a new Zone douleur.
     *
     * @param id      the id
     * @param libelle the libelle
     */
    public Zone_Douleur(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
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
     * Gets libelle.
     *
     * @return the libelle
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Sets libelle.
     *
     * @param libelle the libelle
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
