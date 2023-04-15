package com.ams.project.amsMvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ams.project.amsMvc.entities.Article;
import com.ams.project.amsMvc.entities.Fournisseur;
import com.ams.project.amsMvc.entities.Role;
import com.ams.project.amsMvc.repositories.ArticleRepository;
import com.ams.project.amsMvc.repositories.FournisseurRepository;


@Controller
public class HomeController {
    private final FournisseurRepository fournisseurRepository;
    private final ArticleRepository articleRepository;
    

    @Autowired
    public HomeController(FournisseurRepository fournisseurRepository, ArticleRepository articleRepository) {
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository= articleRepository;
    
    }
	
	@RequestMapping(value={"/home","/h**"})
	//@ResponseBody
	public String home()
	{
	return "back/index.html";
		//return "welcome";
	}
	
	
	@RequestMapping(value={"/index","/i**"})
	//@ResponseBody
	public String indexFront(Model model)
	{
        List<Fournisseur> frs = (List<Fournisseur>) fournisseurRepository.findAll();
        long nbr =  fournisseurRepository.count();
        model.addAttribute("nbr", nbr);
        List<Article> roles = (List<Article>) articleRepository.findAll();
        long nb =  articleRepository.count();
        model.addAttribute("nb", nb);
	return "front/indexFront.html";
		//return "koukou";
	}
	@RequestMapping("/contact")
	public String contact()
	{
	return "front/contact.html";
	}
	
	@RequestMapping(value={"/produits","/pro**"})
	//@ResponseBody
	public String produit()
	{
	return "front/produits.html";
		//return "welcome";
	}
	@RequestMapping(value={"/partenaires","/par**"})
	//@ResponseBody
	public String partenaire()
	{
	return "front/partenaires.html";
		//return "welcome";
	}
}
