package com.ams.project.amsMvc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ams.project.amsMvc.entities.Actualite;
import com.ams.project.amsMvc.repositories.ActualiteRepository;

@Service
public class ActualiteService {
	private final ActualiteRepository actualiteRepository;
	@Autowired
	public ActualiteService(ActualiteRepository actualiteRepository)
	{
		this.actualiteRepository= actualiteRepository;
		
	}
	
	
	
}
