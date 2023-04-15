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

import com.ams.project.amsMvc.entities.Article;
import com.ams.project.amsMvc.entities.Fournisseur;
import com.ams.project.amsMvc.repositories.FournisseurRepository;
import com.ams.project.amsMvc.services.FournisseurService;










@Controller
@RequestMapping("/fournisseur/")
public class FournisseurController {
	  public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploadsProvider";
	
	static String trouve=null;
	private final FournisseurRepository fournisseurRepository;
	private final FournisseurService fournisseurService;
	
	
	@Autowired
    public FournisseurController(FournisseurRepository fournisseurRepository, FournisseurService fournisseurService ) {
        this.fournisseurRepository = fournisseurRepository;
        this.fournisseurService= fournisseurService;

	}
	
	 @GetMapping("list")
	    public String listFournisseur(Model model) {
	    	
	    	List<Fournisseur> lf = (List<Fournisseur>)fournisseurRepository.findAll();
	    	if(lf.size()==0)
	    		lf = null;
	        model.addAttribute("fournisseurs", lf);
	        model.addAttribute("trouve", trouve); 
	        return "fournisseur/listFournisseurs";
	    }
	 
	 @GetMapping("lists")
	    public String listFournisseurs(Model model) {
	    	
	    	List<Fournisseur> lf = (List<Fournisseur>)fournisseurRepository.findAll();
	    	if(lf.size()==0)
	    		lf = null;
	        model.addAttribute("fournisseurs", lf);
	        model.addAttribute("trouve", trouve); 
	        return "front/partenaires.html";
	    }
	   @GetMapping("add")
	    public String showAddFournisseurForm(Model model) {
	    	
	        Fournisseur fournisseur = new Fournisseur();// object dont la valeur des attributs par defaut
	    	model.addAttribute("fournisseur", fournisseur);
	    	return "fournisseur/addFournisseur";
	    }
	    
	    @PostMapping("add")
	    //@ResponseBody
	    public String addArticle(@Valid Fournisseur fournisseur, BindingResult result,
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
	        fournisseur.setLogo(fileName.toString());

	        System.out.println(fournisseur);

	        if (result.hasErrors()) {
	            return "fournisseur/addFournisseur";
	        }
	        
	    	 
	    	 if(fournisseur.getName()=="")
	    		 fournisseur.setName(null);
	         
	         this.fournisseurService.persistFournisseur(fournisseur);
	         return "redirect:list";
	    	
	    	
	    }
	    @GetMapping("delete/{id}")
	    public String deleteFournisseur(@PathVariable("id") long id, Model model) {
	       
	        Optional<Fournisseur>fournisseur = fournisseurRepository.findById(id);
	        
	        if(fournisseur.isPresent())
	        {
	        	fournisseurRepository.delete(fournisseur.get());
	        	 trouve="existe";
	        }
	        else {  // le problème
	        	trouve="inexiste";
	        	
	        }
	    
	    
	    
	      return "redirect:../list";
	    }
	    
	    @GetMapping("edit/{id}")
	    public String showFournisseurFormToUpdate(@PathVariable("id") long id, Model model) {
	    	Fournisseur fournisseur = fournisseurRepository.findById(id)
	            .orElseThrow(()->new IllegalArgumentException("Invalid fournisseur Id:" + id));
	        
	        model.addAttribute("fournisseur", fournisseur);
	        
	        return "fournisseur/updateFournisseur";
	    }
	    @PostMapping("update")
	    public String updateFournisseur(@Valid Fournisseur fournisseur, BindingResult result, Model model) {
	    	
	    	if (result.hasErrors()) {
	    		/* Provider providerToUpdate = providerRepository.findById(provider.getId())
	    		            .orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + provider.getId()));
	    		        
	    		        model.addAttribute("provider", providerToUpdate);*/
	            return "fournisseur/updateFournisseur";
	        }
	    	
	    	fournisseurRepository.save(fournisseur);
	    	return"redirect:list";
	    	
}
	    @GetMapping("show/{id}")
		public String showFournisseur(@PathVariable("id") long id, Model model) {
		Fournisseur fournisseur = fournisseurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid fournisseur Id:" + id));
		List<Article> articles = fournisseurRepository.findArticlesByFournisseur(id);
		for (Article a : articles)
			System.out.println("Article = " + a.getLabel());
		model.addAttribute("articles", articles);
		model.addAttribute("fournisseur", fournisseur);
		return "fournisseur/showFournisseur";
		}
	    
}