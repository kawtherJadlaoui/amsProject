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
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.web.multipart.MultipartFile;

import com.ams.project.amsMvc.entities.Article;
import com.ams.project.amsMvc.entities.Fournisseur;
import com.ams.project.amsMvc.repositories.ArticleRepository;
import com.ams.project.amsMvc.repositories.FournisseurRepository;



@Controller
@RequestMapping("/article/")
public class ArticleController {

	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploads";

	static String trouve=null;
	
	private final ArticleRepository articleRepository;
	private final FournisseurRepository fournisseurRepository;
    @Autowired
    public ArticleController(ArticleRepository articleRepository, FournisseurRepository fournisseurRepository) {
        this.articleRepository = articleRepository;
        this.fournisseurRepository = fournisseurRepository;
    }
    
    @GetMapping("list")
    public String listArticle(Model model) {
    	//model.addAttribute("articles", null);
    	List<Article> la = (List<Article>)articleRepository.findAll();
    	if(la.size()==0)
    		la = null;
        model.addAttribute("articles", la);
        model.addAttribute("trouve", trouve); 
        return "article/listArticles";
    }
    @GetMapping("lists")
    public String listArticles(Model model) {
    	
    	List<Article> ar = (List<Article>)articleRepository.findAll();
    	if(ar.size()==0)
    		ar = null;
        model.addAttribute("articles", ar);
       
        return "front/produits";
    }
    
    @GetMapping("add")
    public String showAddArticleForm(Model model) {
    	
    	model.addAttribute("fournisseurs", fournisseurRepository.findAll());
    	model.addAttribute("article", new Article());
        return "article/addArticle";
    }
    
    @PostMapping("add")
    //@ResponseBody
    public String addArticle(@Valid Article article, BindingResult result, @RequestParam(name = "fournisseurId", required = true) Long p,
                             @RequestParam("files") MultipartFile[] files,@RequestParam("files1") MultipartFile[] files1
    ) {
    	
    	Fournisseur fournisseur = fournisseurRepository.findById(p).orElseThrow(()-> new IllegalArgumentException("Invalid fournisseur Id:" + p));
    	article.setFournisseur(fournisseur);

    	/// part upload
    	//upload de fichier 
    	MultipartFile file = files[0]; //recuperer le 1er fichier
    	MultipartFile file1 = files1[0];
    	Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename()); //
    	
    	try {
    	Files.write(fileNameAndPath, file.getBytes()); //getBytes retourne le contenu de fichier et ecrire dans la path "fileNameAndPath"
    	} catch (IOException e) {
    	e.printStackTrace();
    	}

    	//Stockage du name de fichier dans la base 
    	StringBuilder fileName = new StringBuilder();
    	
    	fileName.append(file.getOriginalFilename());
    	
    	article.setPhotoFace(fileName.toString());
    	
    	Path fileNameAndPath1 = Paths.get(uploadDirectory, file1.getOriginalFilename());
    	try {
        	Files.write(fileNameAndPath1, file1.getBytes()); //getBytes retourne le contenu de fichier et ecrire dans la path "fileNameAndPath"
        	} catch (IOException e) {
        	e.printStackTrace();
        	}
    	StringBuilder fileName1 = new StringBuilder();
    	fileName1.append(file1.getOriginalFilename());
    	article.setPhotoProfil(fileName1.toString());
    	articleRepository.save(article);
    	return "redirect:list";
    	//return article.getLabel() + " " +article.getPrice() + " " +p.toString();
    	}
    	
    
    @GetMapping("delete/{id}")
    public String deleteFournisseur(@PathVariable("id") long id, Model model) {
        
        Optional<Article>article = articleRepository.findById(id);
        
        if(article.isPresent())
        {
        	 articleRepository.delete(article.get());
        	 trouve="existe";
        }
        else {  // le problème
        	trouve="inexiste";
        	
        }
   
      return "redirect:../list";
    }

    @GetMapping("edit/{id}")
    public String showArticleFormToUpdate(@PathVariable("id") long id, Model model) {
    	Article article = articleRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("Invalid Fournisseur Id:" + id));
    	
        model.addAttribute("article", article);
        model.addAttribute("fournisseurs", fournisseurRepository.findAll());
        model.addAttribute("idFournisseur", article.getFournisseur().getId());
        
        return "article/updateArticle";
    }
    @PostMapping("update")
    public String updateArticle( @Valid Article article, BindingResult result,
        Model model, @RequestParam(name = "fournisseurId", required = false) Long p , @RequestParam("files") MultipartFile files, @RequestParam("files1") MultipartFile files1) {
        if (result.hasErrors()) {
        	
            return "article/updateArticle";
        }
        
        Fournisseur fournisseur = fournisseurRepository.findById(p).orElseThrow(()-> new IllegalArgumentException("Invalid Fournisseur Id:" + p));
        
    	article.setFournisseur(fournisseur);
    	try {
            // Enregistrer la nouvelle photo de face
            if (!files.isEmpty()) {
                Path fileNameAndPath = Paths.get(uploadDirectory, files.getOriginalFilename());
                Files.write(fileNameAndPath, files.getBytes());
               // files.transferTo(fileNameAndPath);
                // code to update the user's profile with the new face photo
                StringBuilder fileName = new StringBuilder();
            	
            	fileName.append(files.getOriginalFilename());
            	
            	article.setPhotoFace(fileName.toString());

                
            }
            
    	} catch (IOException e) {
            e.printStackTrace();
        }
    	
    	try {
            // Enregistrer la nouvelle photo de face
            if (!files1.isEmpty()) {
                Path fileNameAndPath1 = Paths.get(uploadDirectory, files1.getOriginalFilename());
                Files.write(fileNameAndPath1, files1.getBytes());
               // files.transferTo(fileNameAndPath);
                // code to update the user's profile with the new face photo
                StringBuilder fileName1 = new StringBuilder();
            	
            	fileName1.append(files1.getOriginalFilename());
            	
            	article.setPhotoProfil(fileName1.toString());

                
            }
            
    	} catch (IOException e) {
            e.printStackTrace();
        }
    	
    	articleRepository.save(article);
        return "redirect:list";
    }
    
    

    @GetMapping("show/{id}")
    public String showArticleDetails(@PathVariable("id") long id, Model model) {
        Article article = articleRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid fournisseur Id:" + id));

        model.addAttribute("article", article);

        return "article/showArticle";
    }


}
