package com.ams.project.amsMvc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ams.project.amsMvc.entities.Article;
import com.ams.project.amsMvc.entities.Fournisseur;




public interface FournisseurRepository extends CrudRepository<Fournisseur,Long> {
	@Query("FROM Article a WHERE a.fournisseur.id = ?1") //?1" c'est le 1er paramtre que je passe entre ()=findArticlesByProvider(long id)
	List<Article> findArticlesByFournisseur(long id); //l'implimentation de methode saret bil @Query
}

