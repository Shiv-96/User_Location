package com.location.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.location.Entity.User;
import com.location.Exception.UserException;
import com.location.Service.UserService;

import jakarta.validation.Valid;

/**
 * 
 * UserConntroller class for creating a REST API
 * 
 * @author SHIV KUMAR
 *
 */


@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * REST API endpoint to create new User
	 * 
	 * @param user User class that we need to  create
	 * @return registered User class
	 * @throws UserException if User is already present then in that case it will throw an exception called UserException
	 */
	
	@PostMapping("/add_data")
	public ResponseEntity<User> addTheUseHandler(@Valid @RequestBody User user) throws UserException {
		
		return new ResponseEntity<>(userService.addTheUser(user), HttpStatus.CREATED);
		
	}
	
	/**
	 * 
	 * REST API endpoint to create the user data for READER
	 * 
	 * @param id Admin's userId
	 * @param user Reader's data who want to join as a READER
	 * @return Registered user class
	 * @throws UserException if id is READER's userId or Invalid id or if email is present then throw UserException
	 * 
	 * 
	 */
	
	
	@PostMapping("/add_data/{id}")
	public ResponseEntity<User> addTheUserForReaderHandler(@PathVariable("id") Integer id, @Valid @RequestBody User user) throws UserException {
		
		return new ResponseEntity<>(userService.addTheUserForReader(id, user), HttpStatus.CREATED);
		
	}
	
	
	/**
	 * 
	 * REST API endpoint to update the existing User
	 * 
	 * @param user User class that we want to update
	 * @return User class after updating the data
	 * @throws UserException If userId is not present then it will throw an exception
	 */
	
	@PutMapping("/update_data")
	public ResponseEntity<User> updateTheUserHandler(@RequestBody User user) throws UserException {
		
		return new ResponseEntity<User>(userService.updatedTheUser(user), HttpStatus.OK);
		
	}
	
	/**
	 * 
	 * REST API endpoint to update the user data for READER
	 * 
	 * @param id Admin's userId
	 * @param user Reader's data we want to update
	 * @return Updated user class
	 * @throws UserException if id is READER's userId or Invalid id or if email is present then throw UserException
	 */
	
	@PutMapping("/update_data/{id}")
	public ResponseEntity<User> updateTheUserForReaderHandler(@PathVariable("id") Integer id, @RequestBody User user) throws UserException {
		
		return new ResponseEntity<User>(userService.updateTheUser(id, user), HttpStatus.OK);
		
	}
	
	
	
	
	
	
	/**
	 * 
	 * REST API endpoint to get nearest num user from latitude 0 and longitude 0
	 * 
	 * @param num Number of user I want
	 * @return List of User and size of the list is num
	 * @throws UserException if list is empty or in database num data is not present then in that case throw UserException
	 */
	
	@GetMapping("/get_data/{n}")
	public ResponseEntity<List<User>> getNearestUser(@PathVariable("n") Integer num) throws UserException {
		
		return new ResponseEntity<>(userService.getNNearestUser(num), HttpStatus.OK);
		
	}
	
	
	
	
}
