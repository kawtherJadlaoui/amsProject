package com.ams.project.amsMvc.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ams.project.amsMvc.entities.Actualite;

import com.ams.project.amsMvc.repositories.ActualiteRepository;
import com.ams.project.amsMvc.services.ActualiteService;



@Controller
@RequestMapping("/actualite/")
public class ActualiteController {
public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploadsActualite";
	
	
	private final ActualiteRepository actualiteRepository;
	private final ActualiteService actualiteService;
	
	
	@Autowired
    public ActualiteController(ActualiteRepository actualiteRepository, ActualiteService actualiteService ) {
        this.actualiteRepository = actualiteRepository;
        this.actualiteService= actualiteService;

	}
	
	@RequestMapping("list")
    public String listActualite(Model model) {
	List<Actualite> ac = (List<Actualite>)actualiteRepository.findAll();
	if(ac.size()==0)
		ac = null;
    model.addAttribute("actualites", ac);
   
	
        
        return "actualite/listActualites.html";
    } 
	@RequestMapping("lists")
	    public String listActualites(Model model) {
		List<Actualite> ac = (List<Actualite>)actualiteRepository.findAll();
    	if(ac.size()==0)
    		ac = null;
        model.addAttribute("actualites", ac);
       
		
	        
	        return "front/actualites.html";
	    }
	@GetMapping("add")
    public String showAddActualiteForm(Model model) {
    	
        Actualite actualite = new Actualite();// object dont la valeur des attributs par defaut
    	model.addAttribute("actualite", actualite);
    	return "actualite/addActualite";
    }
    
    @PostMapping("add")
    //@ResponseBody
    public String addArticle(@Valid Actualite actualite, BindingResult result,
                             @RequestParam("files") MultipartFile[] files
    ) {
    	
    	

        /// part upload

        // upload du ficher
        MultipartFile file = files[0];
        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());

        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Stockage du name du ficher dans la base
        StringBuilder fileName = new StringBuilder();
        fileName.append(file.getOriginalFilename());
        actualite.setPicture(fileName.toString());

        System.out.println(actualite);

        if (result.hasErrors()) {
            return "actualite/addActualite";
        }
        
    	 
    	 if(actualite.getTitre()=="")
    		 actualite.setTitre(null);
         
    	 actualiteRepository.save(actualite);
     	return "redirect:list";
    	
    	
    }
    @GetMapping("delete/{id}")
    public String deleteActualite(@PathVariable("id") long id, Model model) {
       
        Optional<Actualite>actualite = actualiteRepository.findById(id);
        
        
        	actualiteRepository.delete(actualite.get());
        	 
      return "redirect:../list";
    }
    
    @GetMapping("edit/{id}")
    public String showActualiteFormToUpdate(@PathVariable("id") long id, Model model) {
    	Actualite actualite = actualiteRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("Invalid actualite Id:" + id));
        
        model.addAttribute("actualite", actualite);
        
        return "actualite/updateActualite";
    }
    @PostMapping("update")
    public String updateActualite(@Valid Actualite actualite, BindingResult result, Model model) {
    	
    	if (result.hasErrors()) {
    		/* Provider providerToUpdate = providerRepository.findById(provider.getId())
    		            .orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + provider.getId()));
    		        
    		        model.addAttribute("provider", providerToUpdate);*/
            return "actualite/updateActualite";
        }
    	
    	actualiteRepository.save(actualite);
    	return"redirect:list";
    	
}
    @GetMapping("show/{id}")
	public String showActualite(@PathVariable("id") long id, Model model) {
	Actualite actualite = actualiteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid actualite Id:" + id));
	model.addAttribute("actualite", actualite);
	return "actualite/showActualite.html";
	}
    
}
