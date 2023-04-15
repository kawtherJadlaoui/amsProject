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
public class Contact {

	@Id  //clé primaire
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", nullable=false)
   // @Column(name = "name")
    private String name;
    
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email", nullable=false)
    private String email;
    
    
    @Column(name = "subject")
    private String subject;
    
    @NotBlank(message = "Message is mandatory")
    @Column(name = "message", nullable=false)
    private String message;
	
	@Basic()
    @CreationTimestamp
    @Column(name = "dateHeureMessage")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateHeureMessage = new Date();

	public Contact(long id, @NotBlank(message = "Name is mandatory") String name,
			@NotBlank(message = "Email is mandatory") String email, String subject,
			@NotBlank(message = "Message is mandatory") String message, Date dateHeureMessage) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.subject = subject;
		this.message = message;
		this.dateHeureMessage = dateHeureMessage;
	}

	public Contact() {
		System.out.println("Hello From contact constructor");	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateHeureMessage() {
		return dateHeureMessage;
	}

	public void setDateHeureMessage(Date dateHeureMessage) {
		this.dateHeureMessage = dateHeureMessage;
	}
	
	
	
}
