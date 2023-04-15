package com.ams.project.amsMvc.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ams.project.amsMvc.entities.User;
import com.ams.project.amsMvc.services.UserService;
import com.ams.project.amsMvc.entities.Role;



@Controller
public class LoginController {
	 @Autowired
	    private UserService userService;
	 
	 
	     
	     @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	     public ModelAndView login(){ //ModelAndView bch thezni lil login 
	         ModelAndView modelAndView = new ModelAndView();
	         modelAndView.setViewName("/dashboard/login");
	         return modelAndView;
	     }
	     @RequestMapping(value={"/home"}, method = RequestMethod.GET)
	     public ModelAndView accueil(){
	         ModelAndView modelAndView = new ModelAndView();
	         modelAndView.setViewName("/back/index");
	         Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //bch nrecuperi user connecter - SecurityContextHolder ta3tik context mta3 securité lkol
	         User user = userService.findUserByEmail(auth.getName());
	         System.out.println(user.getEmail()+" " +user.getName()+ " "+user.getLastName());
	         return modelAndView;
	     }

	     @RequestMapping(value="/registration", method = RequestMethod.GET)
	     public ModelAndView registration(){
	         ModelAndView modelAndView = new ModelAndView();
	         User user = new User();
	         modelAndView.addObject("user", user);
	         modelAndView.setViewName("/dashboard/registration");
	         return modelAndView;
	     }
	     @RequestMapping(value = "/registration", method = RequestMethod.POST)
	     public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
	         ModelAndView modelAndView = new ModelAndView();
	         User userExists = userService.findUserByEmail(user.getEmail());
	         if (userExists != null) {
	             bindingResult
	                     .rejectValue("email", "error.user",
	                             "There is already a user registered with the email provided");
	         }
	         if (bindingResult.hasErrors()) {
	             modelAndView.setViewName("/dashboard/registration");
	         } else {
	             userService.saveUser(user,"AGENT",0);
	             modelAndView.addObject("successMessage", "User has been registered successfully");
	             modelAndView.addObject("user", new User());
	             modelAndView.setViewName("/dashboard/registration");
	         }
	         return modelAndView;
	     }
//bch nrecuperi user connecter
	     /* @RequestMapping(value="/admin/home", method = RequestMethod.GET)
	     public ModelAndView home(){
	         ModelAndView modelAndView = new ModelAndView();
	         Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //bch nrecuperi user connecter
	         User user = userService.findUserByEmail(auth.getName());
	         modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
	         modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
	         modelAndView.setViewName("admin/home");
	         return modelAndView;
	     }*/
	     
	     /*@GetMapping("/403") //ki theb tconecti 3la url qui nont pas le droit de consulter
	     public String error403() {
	         return "/error/403"; //redirect:/403
	         
	     }*/

	     @GetMapping("/403")
	     public String reddirectError403() {
	         return "redirect:../axessDenied";
	     }

	     @GetMapping("/axessDenied")
	     public String error403() {
	         return "/error/403";
	     }
	     @GetMapping("/")
		    public String accueil(Model model) {
		        return "dashboard/index";  
		    }
		    
		    @GetMapping("/dashboard")
		    public String dashbaord(Model model) {
		    	
		 
		    	 //1-Récuparation de la session du user Connecté <<Authentication>>
		    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		         //2-Récupéartion du User
		    	 User user = userService.findUserByEmail(auth.getName());
		         
		         //System.out.println(user.toString());
		         model.addAttribute("user", user);
		         //3-Récupération des roles du user
		         Set<Role> userRoles = user.getRoles();
		         //4-Conversion du set vers tableau pour la récupération du premier role
		         Object roles[] = userRoles.toArray();
		         //System.out.println(roles[0].toString()); // On suppose qu'on a un seul role par user
		         //5-Récupéation du rôle : userRole
		         Role role = (Role)roles[0];
		         String userRole = role.getRole();
		         //System.out.println(userRole);
		         
		         
		         switch(userRole) {
		         case "SUPERADMIN" : return "dashboard/superadmin"; 
		         case "AGENT" : return "dashboard/agent";
		         case "ADMIN" : return "dashboard/admin";
		         default : return "dashboard/index";
		         }
		        
		         
		       
		    }
		    
		   

	        
	        
		    @GetMapping("/superadmin")
		    public String dashbaordAdmin(Model model) {
		        return "dashboard/superadmin";  
		    }
		    @GetMapping("/agent")
		    public String dashbaordAgent(Model model) {
		        return "dashboard/agent";  
		    }
		    @GetMapping("/admin")
		    public String dashbaordAClient(Model model) {
		        return "dashboard/admin";  
		    }
		

	}

	 





	 	 

