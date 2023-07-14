package com.location.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.location.Entity.User;
import com.location.Exception.UserException;
import com.location.Service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/add_data")
	public ResponseEntity<User> addTheUseHandler(@Valid @RequestBody User user) throws UserException {
		
		return new ResponseEntity<>(userService.addTheUser(user), HttpStatus.CREATED);
		
	}
	
	
	@PatchMapping("/update_data")
	public ResponseEntity<User> updateTheUserHandler(@Valid @RequestBody User user) throws UserException {
		
		return new ResponseEntity<User>(userService.updatedTheUser(user), HttpStatus.OK);
		
	}
	
	@GetMapping("/get_data/{n}")
	public ResponseEntity<List<User>> getNearestUser(@PathVariable("n") Integer num) throws UserException {
		
		return new ResponseEntity<>(userService.getNNearestUser(num), HttpStatus.OK);
		
	}
	
	
}
