package com.ams.project.amsMvc.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.ams.project.amsMvc.entities.Contact;
import com.ams.project.amsMvc.repositories.ContactRepository;
import com.ams.project.amsMvc.services.ContactService;


@Controller
@RequestMapping("/contact/")
public class ContactController { 
	private final ContactRepository contactRepository;
	private final ContactService contactService;

	@Autowired
	public ContactController(ContactRepository contactRepository, ContactService contactService) {
		this.contactRepository= contactRepository;
		this.contactService= contactService;
	}
	
	@RequestMapping("list")
    public String listActualite(Model model) {
	List<Contact> co = (List<Contact>)contactRepository.findAll();
	if(co.size()==0)
		co = null;
    model.addAttribute("contacts", co);
    return "contact/listContacts.html";
    }
	
	 @GetMapping("add")
	    public String showAddContactForm(Model model) {
	    	
	        Contact  contact = new Contact();// object dont la valeur des attributs par defaut
	    	model.addAttribute("contact", contact);
	    	return "front/contact.html";
	    }
	 

		@PostMapping("add")
		public String addContact(@Valid Contact contact, BindingResult result, Model model) {
			if (result.hasErrors()) {
				return "front/contact.html";
			}
			if(contact.getName()== "")
				contact.setName(null);
			//providerRepository.save(provider); pour utlise service on comment
			this.contactService.persistProvider(contact);
			
			return "redirect:/add";
		}
	}

