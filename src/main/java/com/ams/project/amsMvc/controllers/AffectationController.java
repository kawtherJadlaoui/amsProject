package com.ams.project.amsMvc.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ams.project.amsMvc.entities.Affectation;
import com.ams.project.amsMvc.entities.Role;
import com.ams.project.amsMvc.entities.User;
import com.ams.project.amsMvc.repositories.AffectationRepository;
import com.ams.project.amsMvc.repositories.UserRepository;
import com.ams.project.amsMvc.services.UserService;



@Controller
@RequestMapping("/affectation/")
public class AffectationController {
	
	@Autowired
	UserService userService;
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	AffectationRepository affectationRepository;
	
	
    @GetMapping("encours")
    //@ResponseBody
    public String listProviders(Model model) {
    	
    	
		
    	
    	List<User>lc = getUsersByRole("AGENT");
    	
    	if(lc.size()==0)
    		lc=null;
    	List<User>la = getUsersByRole("ADMIN");
    	
    	
    	model.addAttribute("nbr", lc.size());
        model.addAttribute("agents", lc);
        model.addAttribute("admins", la);
    	
        return "affectation/encours";

    }
    
    @PostMapping("save")
    public String saveAffectation(Model model, @RequestParam("idAgent")int idAgent, @RequestParam("idAdmin")int idAdmin) {
    	
    	Affectation affectation = new Affectation();
    	affectation.setIdAgent(idAgent);
    	affectation.setIdAdmin(idAdmin);
    	affectation.setDateAffectation(new Date());
    	
    	 //1-Récuparation de la session du user Connecté <<Authentication>>
   	 	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //2-Récupéartion du User
   	 	User superadmin = userService.findUserByEmail(auth.getName());
   	 
   	 	affectation.setIdSupAdmin(superadmin.getId());
    	// setters pour affectation
   	    affectation.setStatus("En attente de validation");
       
        affectationRepository.save(affectation);
        
        // envoyer un email vers l'agent correspondant pour lui dire qu'il a une demande à valider
        return "redirect:encours";
    }
    
    @GetMapping("admin")
    //@ResponseBody
    public String listAgentByAdmin(Model model) {
    	
    	//1-Récuparation de la session du user Connecté <<Authentication>>
   	 	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //2-Récupéartion du User
   	 	User adminEnCours = userService.findUserByEmail(auth.getName());
   	 
    	
		List<Affectation> laf = affectationRepository.findAffectationByAdmin(adminEnCours.getId());
		List<User> usersAdmin = new ArrayList<>();
		
		for(Affectation affectation : laf)
		{
			//System.out.println(affectation.getIdClient());
			
			Optional<User> user = userRepository.findById(affectation.getIdAgent());
			if(user.isPresent())
			{
				usersAdmin.add(user.get());
			}
			
		}
    	
		System.out.println(usersAdmin);
    	
    	
    	if(usersAdmin.size()==0)
    		usersAdmin=null;
    	
    	
    	
    	model.addAttribute("nbr", usersAdmin.size());
        model.addAttribute("agents", usersAdmin);
        return "affectation/usersByAdmin";

    }
    
    @GetMapping("activateAccount/{id}")
    //@ResponseBody
    public String activateAccount(@PathVariable("id") int id) {
    	
    	Optional<User> user = userRepository.findById(id);
		if(user.isPresent())
		{
			User exitUser = user.get();
			exitUser.setActive(1);
			userRepository.save(exitUser);
		}
    	
    	return "redirect:../admin";
    }
    
    public List<User> getUsersByRole(String role)
    {
    	List<User> myList=new ArrayList<>();
    	
    	List<User> lu = (List<User>)userRepository.findAll(); // list de tout les Users
    	for(User user : lu)
    	{
    		 Set<Role>userRoles = user.getRoles();
    		 Object roles[] = userRoles.toArray();
  	         Role roleObj = (Role)roles[0];
  	         String userRole = roleObj.getRole();
  	         if(userRole.equals(role))
  	        	myList.add(user);
    		
    	}
    	
    	return myList;
    }

}
