package com.ams.project.amsMvc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ams.project.amsMvc.entities.Role;
import com.ams.project.amsMvc.entities.User;
import com.ams.project.amsMvc.repositories.UserRepository;
import com.ams.project.amsMvc.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.MessagingException;
import java.io.IOException;

@Controller
@RequestMapping("/admin/")

public class AdminController {
	
	@Autowired
    private JavaMailSender javaMailSender;

	
	@Autowired
    private UserService userService;
	
	@Autowired
    private UserRepository userRepository;
    
    @GetMapping("list")
    //@ResponseBody
    public String listAdmins(Model model) {
    	
    	List<User> lu = (List<User>)userRepository.findAll(); // list de tout les Users
    	List<User>la = new ArrayList<>();
    	
    	for(User user : lu)
    	{
    		 Set<Role>userRoles = user.getRoles();
    		 Object roles[] = userRoles.toArray();
  	         Role role = (Role)roles[0];
  	         String userRole = role.getRole();
  	         if(userRole.equals("ADMIN"))
  	        	 la.add(user);
    		
    	}
   
    	
    	if(la.size()==0)
    		la=null;
    	model.addAttribute("nbr", la.size());
        model.addAttribute("admins", la);
    	
        return "admin/listAdmins";
        
        //List<Provider> lp = (List<Provider>)providerRepository.findAll();
       // System.out.println(lp);
        
       // return "Nombre de fournisseurs = " + lp.size();
    }
    
    @GetMapping("add")
    public String AdminForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "admin/addAdmin";
    }
    
    @PostMapping("add")
    
    public String addAdmin(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/addAdmin";
        }
        userService.saveUser(user,"ADMIN",1);
        sendEmail("jadlaouikawther13@gmail.com", true,user.getName()+" "+user.getLastName());
        
        // envoyer un email à l'admin
        return "redirect:list";
    }
    
    void sendEmail(String email, boolean state, String nomComplet) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        if(state == true)
        {
        msg.setSubject("Account Has Been Activated");
        msg.setText("Hello, "+ nomComplet +" Your account has been activated. "
        		+ 
        		"You can log in : http://127.0.0.1:80/login"
        		+ " \n Best Regards!");
        }
        else
        {
        	msg.setSubject("Account Has Been disactivated");
            msg.setText("Hello, Your account has been disactivated.");
        }
        javaMailSender.send(msg);

    }
	
    



    /*
    @GetMapping("delete/{id}")
    public String deleteProvider(@PathVariable("id") long id, Model model) {
    	
    	
    	//long id2 = 100L;
    	
        Provider provider = providerRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + id));
        
        System.out.println("suite du programme...");
        
        providerRepository.delete(provider);
        
  
        return "redirect:../list";
    }
    
    
    @GetMapping("edit/{id}")
    public String showProviderFormToUpdate(@PathVariable("id") long id, Model model) {
        Provider provider = providerRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + id));
        
        model.addAttribute("provider", provider);
        
        return "provider/updateProvider";
    }


    
    @PostMapping("update")
    public String updateProvider(@Valid Provider provider, BindingResult result, Model model) {
    	
    	
    	providerRepository.save(provider);
    	return"redirect:list";
    	
    }*/
}
