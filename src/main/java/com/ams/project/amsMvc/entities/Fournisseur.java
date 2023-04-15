package com.ams.project.amsMvc.entities;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;



@Entity
public class Fournisseur {
	
	
		@Id  //clé primaire
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;

	    @NotBlank(message = "Name is mandatory")
	    @Column(name = "name", nullable=false)
	   // @Column(name = "name")
	    private String name;
	    
	    @NotBlank(message = "Email is mandatory")
	    @Column(name = "email")
	    private String email;

	    
	 
	    
	    @NotBlank(message = "Tel is mandatory")
	    @Column(name = "tel", nullable=false)
	    private String tel;
	    
	    @NotBlank(message = "Address is mandatory")
	    @Column(name = "address", nullable=false)
	    private String address;
	    
	    @Column(name = "logo")
	    private String logo;
	    
	    public Fournisseur() {
	    	System.out.println("Hello From constructeur");	
	    }

		public Fournisseur(long id, String name, String logo, String tel, String address, String email) {
			this.id = id;
			this.name = name;
			this.logo = logo;
			this.tel = tel;
			this.address = address;
			this.email = email;
		}

		@Override
		public String toString() {
			return "Fournisseur [id=" + id + ", name=" + name + ", logo=" + logo + ", tel=" + tel + ", address="
					+ address + ", email=" + email + "]";
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

		public String getLogo() {
			return logo;
		}

		public void setLogo(String logo) {
			this.logo = logo;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		 @OneToMany(cascade=CascadeType.ALL, mappedBy = "fournisseur")
		    private List<Article> articles;

		    public List<Article> getArticles() {
		        return articles;
		    }

		    public void setArticles(List<Article> articles) {
		        this.articles = articles;
		    }
}
