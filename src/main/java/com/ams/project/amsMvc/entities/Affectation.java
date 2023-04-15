package com.ams.project.amsMvc.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Affectation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "idAdmin")
	private int idAdmin;
	
	@Column(name = "idAgent")
	private int idAgent;
	
	@Column(name = "dateAffectation")
	private Date dateAffectation;
	
	@Column(name = "idSupAdmin")
	private int idSupAdmin;
	
	@Column(name = "status")
	private String status;

	@Override
	public String toString() {
		return "Affectation [id=" + id + ", idAdmin=" + idAdmin + ", idAgent=" + idAgent + ", dateAffectation="
				+ dateAffectation + ", idSupAdmin=" + idSupAdmin + ", status=" + status + "]";
	}

	public Affectation(int id, int idAdmin, int idAgent, Date dateAffectation, int idSupAdmin, String status) {
		super();
		this.id = id;
		this.idAdmin = idAdmin;
		this.idAgent = idAgent;
		this.dateAffectation = dateAffectation;
		this.idSupAdmin = idSupAdmin;
		this.status = status;
	}

	public Affectation() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}

	public int getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(int idAgent) {
		this.idAgent = idAgent;
	}

	public Date getDateAffectation() {
		return dateAffectation;
	}

	public void setDateAffectation(Date dateAffectation) {
		this.dateAffectation = dateAffectation;
	}

	public int getIdSupAdmin() {
		return idSupAdmin;
	}

	public void setIdSupAdmin(int idSupAdmin) {
		this.idSupAdmin = idSupAdmin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	

}
