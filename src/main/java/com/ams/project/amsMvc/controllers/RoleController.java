package com.ams.project.amsMvc.controllers;
import java.util.List;

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


import com.ams.project.amsMvc.entities.Role;
import com.ams.project.amsMvc.repositories.RoleRepository;
import com.ams.project.amsMvc.services.RoleService;




@Controller
@RequestMapping("/role/")

public class RoleController {

    private final RoleRepository roleRepository;
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleRepository roleRepository, RoleService roleService) {
        this.roleRepository = roleRepository;
        this.roleService= roleService;
    }

    @GetMapping("list")
    public String listRoles(Model model) {

        List<Role> roles = (List<Role>) roleRepository.findAll();
        long nbr =  roleRepository.count();
        if(roles.size()==0)
            roles = null;
        model.addAttribute("roles", roles);
        model.addAttribute("nbr", nbr);
        return "role/listRoles";
    }

    @GetMapping("add")
    public String showAddRoleForm() {

        //m.addAttribute("Role",new Role("Admin"));
        return "role/addRole";
    }

    @PostMapping("add")
    public String addRole(@RequestParam("role") String role) {

        System.out.println(role);
        Role r = new Role(role);
        Role rSaved = roleRepository.save(r);
        System.out.println("role = "+ rSaved);
        return "redirect:list";
    }

	@GetMapping("delete/{id}")
	public String deleteRole(@PathVariable("id") int id, Model model) {
		// long id2 = 100L;
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
		System.out.println("suite du programme...");
		roleRepository.delete(role);
		/*
		 * model.addAttribute("providers", providerRepository.findAll()); return
		 * "provider/listProviders";
		 */
		return "redirect:../list";
		
		
}

}
