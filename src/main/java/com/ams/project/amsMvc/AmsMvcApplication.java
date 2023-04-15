package com.ams.project.amsMvc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ams.project.amsMvc.controllers.ActualiteController;
import com.ams.project.amsMvc.controllers.ArticleController;
import com.ams.project.amsMvc.controllers.FournisseurController;


@SpringBootApplication
public class AmsMvcApplication {
	public static void main(String[] args) throws IOException {
		new File(FournisseurController.uploadDirectory).mkdir(); 
		new File(ArticleController.uploadDirectory).mkdir();
		
		//new File(ActualiteController.uploadDirectory).mkdir();
		
		
		new File(ActualiteController.uploadDirectory).mkdir();
		Path path= Paths.get(ArticleController.uploadDirectory);
		try
		{
		Files.createDirectory(path);
		
		}
		catch(IOException ex) {}
		SpringApplication.run(AmsMvcApplication.class, args);
		
		
	}

}
