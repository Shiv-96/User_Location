package com.location.Service;

import java.util.List;

import com.location.Entity.User;
import com.location.Exception.UserException;

/**
 * 
 * UserService interface represent list of the method
 * 
 * @author SHIV KUMAR
 *
 */

public interface UserService {
	
	public User addTheUser(User user) throws UserException;
	
	public List<User> getNNearestUser(Integer n) throws UserException;
	
	public User updatedTheUser(User user) throws UserException;
	
}
