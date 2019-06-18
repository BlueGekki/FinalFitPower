package com.example.mapdemo;

/**
 * The type Utilisateur.
 */
public class Utilisateur {
    private int id;
    private String login;
    private String mdp;
    private String mail;
    private String nom;
    private String prenom;
    private int age;
    private long taille;
    private long poid;
    private String genre;

    /**
     * Instantiates a new Utilisateur.
     */
    public Utilisateur(){

    }

    /**
     * Instantiates a new Utilisateur.
     *
     * @param id     the id
     * @param login  the login
     * @param mdp    the mdp
     * @param mail   the mail
     * @param nom    the nom
     * @param prenom the prenom
     * @param age    the age
     * @param taille the taille
     * @param poid   the poid
     * @param genre  the genre
     */
    public Utilisateur(int id, String login, String mdp, String mail, String nom, String prenom, int age, long taille, long poid, String genre) {
        this.id = id;
        this.login = login;
        this.mdp = mdp;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.taille = taille;
        this.poid = poid;
        this.genre = genre;
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
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets mdp.
     *
     * @return the mdp
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Sets mdp.
     *
     * @param mdp the mdp
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * Gets mail.
     *
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets mail.
     *
     * @param mail the mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Gets nom.
     *
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Sets nom.
     *
     * @param nom the nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Gets prenom.
     *
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Sets prenom.
     *
     * @param prenom the prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets taille.
     *
     * @return the taille
     */
    public long getTaille() {
        return taille;
    }

    /**
     * Sets taille.
     *
     * @param taille the taille
     */
    public void setTaille(long taille) {
        this.taille = taille;
    }

    /**
     * Gets poid.
     *
     * @return the poid
     */
    public long getPoid() {
        return poid;
    }

    /**
     * Sets poid.
     *
     * @param poid the poid
     */
    public void setPoid(long poid) {
        this.poid = poid;
    }

    /**
     * Gets genre.
     *
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets genre.
     *
     * @param genre the genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", mdp='" + mdp + '\'' +
                ", mail='" + mail + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", taille=" + taille +
                ", poid=" + poid +
                ", genre='" + genre + '\'' +
                '}';
    }
}
