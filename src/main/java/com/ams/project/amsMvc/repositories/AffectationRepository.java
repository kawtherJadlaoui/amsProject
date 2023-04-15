package com.ams.project.amsMvc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ams.project.amsMvc.entities.Affectation;



public interface AffectationRepository extends CrudRepository<Affectation, Integer>{

	@Query("FROM Affectation a WHERE a.idAdmin = ?1")
	List<Affectation> findAffectationByAdmin(int id);
	
}
