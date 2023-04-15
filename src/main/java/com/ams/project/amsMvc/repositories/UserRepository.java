package com.ams.project.amsMvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ams.project.amsMvc.entities.User;



@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    
   // @Query("FROM User u WHERE u.provider.id = ?1")
	//List<User> findClientfromUser(String role);

}
