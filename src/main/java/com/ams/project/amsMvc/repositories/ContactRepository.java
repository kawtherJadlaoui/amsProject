package com.ams.project.amsMvc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ams.project.amsMvc.entities.Contact;



public interface ContactRepository extends CrudRepository<Contact,Long> {
	
	

}
