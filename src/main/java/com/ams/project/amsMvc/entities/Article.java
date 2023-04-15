package com.ams.project.amsMvc.entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity

public class Article {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Label is mandatory")
    @Column(name = "label")
    private String label;
    
    
 
    @Column(name = "price")
    private float price;

    @Column(name = "photoFace")
    private String photoFace;
    
    @Column(name = "photoProfil")
    private String photoProfil;
    
    @Column(name = "pricePromo")
    private float pricePromo;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "quantiteStock")
    private String quantiteStock;
    
    @Column(name = "dateExpiration")
    private String dateExpiration;

    public Article() {}

    

    public Article(long id, @NotBlank(message = "Label is mandatory") String label, float price, String photoFace,
			String photoProfil, float pricePromo, String description, String quantiteStock, String dateExpiration,
			Fournisseur fournisseur) {
		super();
		this.id = id;
		this.label = label;
		this.price = price;
		this.photoFace = photoFace;
		this.photoProfil = photoProfil;
		this.pricePromo = pricePromo;
		this.description = description;
		this.quantiteStock = quantiteStock;
		this.dateExpiration = dateExpiration;
		this.fournisseur = fournisseur;
	}



	@Override
	public String toString() {
		return "Article [id=" + id + ", label=" + label + ", price=" + price + ", photoFace=" + photoFace
				+ ", photoProfil=" + photoProfil + ", pricePromo=" + pricePromo + ", description=" + description
				+ ", quantiteStock=" + quantiteStock + ", dateExpiration=" + dateExpiration + ", fournisseur="
				+ fournisseur + "]";
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}



	public float getPrice() {
		return price;
	}



	public void setPrice(float price) {
		this.price = price;
	}



	public String getPhotoFace() {
		return photoFace;
	}



	public void setPhotoFace(String photoFace) {
		this.photoFace = photoFace;
	}



	public String getPhotoProfil() {
		return photoProfil;
	}



	public void setPhotoProfil(String photoProfil) {
		this.photoProfil = photoProfil;
	}



	public float getPricePromo() {
		return pricePromo;
	}



	public void setPricePromo(float pricePromo) {
		this.pricePromo = pricePromo;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getQuantiteStock() {
		return quantiteStock;
	}



	public void setQuantiteStock(String quantiteStock) {
		this.quantiteStock = quantiteStock;
	}



	public String getDateExpiration() {
		return dateExpiration;
	}



	public void setDateExpiration(String dateExpiration) {
		this.dateExpiration = dateExpiration;
	}



	



	/**** Many To One ****/
    @JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fournisseur_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Fournisseur fournisseur;
	
    public Fournisseur getFournisseur() {
		return fournisseur;
	}



	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	
	  
    
}
