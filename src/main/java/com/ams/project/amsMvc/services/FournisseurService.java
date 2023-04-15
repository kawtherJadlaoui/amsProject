package com.ams.project.amsMvc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ams.project.amsMvc.entities.Fournisseur;
import com.ams.project.amsMvc.repositories.FournisseurRepository;




@Service
public class FournisseurService {
	 private final FournisseurRepository fournisseurRepository;

	    @Autowired
	    public FournisseurService(FournisseurRepository fournisseurRepository) {
	        this.fournisseurRepository = fournisseurRepository;
	    }
	    public List<Fournisseur> getAllFournisseur(){
	        return (List<Fournisseur>)fournisseurRepository.findAll();

	    }

	    public Fournisseur persistFournisseur(Fournisseur Fournisseur)
	    {
	        /// autres traiteement
	        return  this.fournisseurRepository.save(Fournisseur);
	    }
	}