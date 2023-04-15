package com.ams.project.amsMvc.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Actualite {
	
	@Id  //clé primaire
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "titre is mandatory")
    @Column(name = "titre", nullable=false)
   // @Column(name = "name")
    private String titre;
    
   
    
    @Column(name = "description")
    private String description;
    
    
    @Column(name = "picture")
    private String picture;
	
	@Basic()
    @CreationTimestamp
    @Column(name = "datePub")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePub = new Date();

	public Actualite(long id, @NotBlank(message = "titre is mandatory") String titre, String description,
			String picture, Date datePub) {
		super();
		this.id = id;
		this.titre = titre;
		this.description = description;
		this.picture = picture;
		this.datePub = datePub;
	}

	public Actualite() {
		super();
	}

	@Override
	public String toString() {
		return "Actualite [id=" + id + ", titre=" + titre + ", description=" + description + ", picture=" + picture
				+ ", datePub=" + datePub + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getDatePub() {
		return datePub;
	}

	public void setDatePub(Date datePub) {
		this.datePub = datePub;
	}


}
