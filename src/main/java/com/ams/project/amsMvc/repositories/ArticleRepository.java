package com.ams.project.amsMvc.repositories;
import org.springframework.data.repository.CrudRepository;

import com.ams.project.amsMvc.entities.Article;


public interface ArticleRepository extends CrudRepository<Article,Long> {

}
