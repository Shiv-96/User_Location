package com.location.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.location.Entity.User;

/**
 * 
 * UserRepository interface to manage our user entity
 * 
 * @Repository annotation denotes that this interface belongs to Data Access Layer 
 * 
 * @author SHIV KUMAR
 *
 */



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
