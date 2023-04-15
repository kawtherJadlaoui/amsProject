package com.ams.project.amsMvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ams.project.amsMvc.repositories.RoleRepository;

@Service
public class RoleService {
	private final RoleRepository roleRepository;
	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository= roleRepository;
	}
	}

