package com.ams.project.amsMvc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ams.project.amsMvc.entities.Contact;
import com.ams.project.amsMvc.repositories.ContactRepository;


@Service
public class ContactService {
	private final ContactRepository contactRepository;
	@Autowired
   public ContactService(ContactRepository contactRepository)
   {
	this.contactRepository= contactRepository;	
   }
	
	public List<Contact>getAllContact()
	{
		
		return (List<Contact>)contactRepository.findAll();
		
	}
	public Contact persistProvider(Contact contact)
	{
	 return this.contactRepository.save(contact)	;
	}

}
