package com.voiture.gestion_voitures.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author owani-sana_jency-w
 *
 */
public class Voiture implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @SerializedName("id")
    private Integer id;
    // Attributs
    private String marque;
    private String modele;
    private String matriculation;
    private int annee;
    private double prix;
    private double consommation; // en litres/100km
    private int puissance; // en chevaux


    public Voiture() {

    }
    public Voiture(Integer id, String marque, String modele, String matriculation, int annee, double prix, double consommation, int puissance) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.matriculation = matriculation;
        this.annee = annee;
        this.prix = prix;
        this.consommation = consommation;
        this.puissance = puissance;
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getMarque() {
        return marque;
    }
    public void setMarque(String marque) {
        this.marque = marque;
    }
    public String getModele() {
        return modele;
    }
    public void setModele(String modele) {
        this.modele = modele;
    }
    public String getMatriculation() {
        return matriculation;
    }
    public void setMatriculation(String matriculation) {
        this.matriculation = matriculation;
    }
    public int getAnnee() {
        return annee;
    }
    public void setAnnee(int annee) {
        this.annee = annee;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public double getConsommation() {
        return consommation;
    }
    public void setConsommation(double consommation) {
        this.consommation = consommation;
    }
    public int getPuissance() {
        return puissance;
    }
    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    @Override
    public String toString() {
        return "Voiture{ Id= "+id + '\'' + "marque='" + marque + '\'' + ", modele='" + modele + '\'' +
                ", matriculation='" + matriculation + '\'' +
                ", annee=" + annee +
                ", prix=" + prix +
                ", consommation=" + consommation +
                ", puissance=" + puissance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voiture voiture)) return false;
        return getAnnee() == voiture.getAnnee() && Double.compare(voiture.getPrix(), getPrix()) == 0 && Double.compare(voiture.getConsommation(), getConsommation()) == 0 && getPuissance() == voiture.getPuissance() && getId().equals(voiture.getId()) && getMarque().equals(voiture.getMarque()) && getModele().equals(voiture.getModele()) && getMatriculation().equals(voiture.getMatriculation());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMarque(), getModele(), getMatriculation(), getAnnee(), getPrix(), getConsommation(), getPuissance());

    }
}
