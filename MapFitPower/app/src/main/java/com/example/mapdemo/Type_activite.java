package com.example.mapdemo;

/**
 * The type Type activite.
 */
public class Type_activite {
    private int id;
    private String libelle;
    private boolean kilometrique;

    /**
     * Instantiates a new Type activite.
     */
    public Type_activite(){

    }

    /**
     * Instantiates a new Type activite.
     *
     * @param libelle the libelle
     */
    public Type_activite(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Instantiates a new Type activite.
     *
     * @param libelle      the libelle
     * @param kilometrique the kilometrique
     */
    public Type_activite(String libelle, boolean kilometrique) {
        this.libelle = libelle;
        this.kilometrique = kilometrique;
    }

    /**
     * Instantiates a new Type activite.
     *
     * @param id           the id
     * @param libelle      the libelle
     * @param kilometrique the kilometrique
     */
    public Type_activite(int id, String libelle, boolean kilometrique) {
        this.id = id;
        this.libelle = libelle;
        this.kilometrique = kilometrique;
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

    /**
     * Is kilometrique boolean.
     *
     * @return the boolean
     */
    public boolean isKilometrique() {
        return kilometrique;
    }

    /**
     * Sets kilometrique.
     *
     * @param kilometrique the kilometrique
     */
    public void setKilometrique(boolean kilometrique) {
        this.kilometrique = kilometrique;
    }

    @Override
    public String toString() {
        return "Type_activite{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", kilometrique=" + kilometrique +
                '}';
    }
}
