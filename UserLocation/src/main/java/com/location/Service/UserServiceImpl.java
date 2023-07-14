package com.location.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.util.FastMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.location.Entity.User;
import com.location.Exception.UserException;
import com.location.Repository.UserRepository;

/**
 * 
 * UserServiceImpl class is the implementation of the UserService interface
 * 
 * @Service annotation denotes that this belongs to service layer
 * 
 * @Autowired denotes that Data Access Layer interface is connected to the Service Layer
 * 
 * 
 * @author SHIV KUMAR
 *
 */



@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	/**
	 * 
	 * This method will take User class as an parameter and 
	 * return that user after saving the data in database
	 * 
	 */
	
	@Override
	public User addTheUser(User user) throws UserException {
		
		return userRepository.save(user);
		
	}
	
	/**
	 * This method will ask Number of user(Integer n) we want as an parameter
	 * return the List of n user 
	 * 
	 */

	@Override
	public List<User> getNNearestUser(Integer n) throws UserException {
		
		List<User> users = userRepository.findAll();
		
		if(users.isEmpty()) {
			throw new UserException("User is not present");
		}
		
		List<User> filteredUser = users.stream().sorted((user1, user2) -> {
			Double distance1 = calculateDistance(user1.getLatitude(), user1.getLongitude());
			Double distance2 = calculateDistance(user2.getLatitude(), user2.getLongitude());
			return Double.compare(distance1, distance2);
		})
		.limit(n).collect(Collectors.toList());
		
		return filteredUser;
		
	}
	
	/**
	 * 
	 * This method will take User class as an parameter and 
	 * return updated user after saving the updated user in database
	 * 
	 */
	
	@Override
	public User updatedTheUser(User user) throws UserException {
		
		return userRepository.save(user);
		
	}
	
	/**
	 * 
	 * @param lat User location (latitude) Double
	 * @param lon USer location (longitude) Double
	 * @return distance from latitude as 0 and longitude as 0 Double
	 */
	
	public double calculateDistance(Double lat, Double lon) {
		
		Double earthRadius = 6378.1;
		
		Double deltaLatitude = toRadian(lat);
		Double deltaLongitude = toRadian(lon);
		
		Double a = FastMath.sin(deltaLatitude/2) * FastMath.sin(deltaLatitude/2)+FastMath.cos(toRadian(lat)) * FastMath.cos(toRadian(0.0)) * FastMath.sin(deltaLongitude/2) * FastMath.sin(deltaLongitude/2);
		
		Double c = 2 * FastMath.atan2(FastMath.sqrt(a), FastMath.sqrt(1-a));
		
		return earthRadius * c;
		
	}
	
	public Double toRadian(Double degree) {
		return degree * FastMath.PI / 180;
	}

}
