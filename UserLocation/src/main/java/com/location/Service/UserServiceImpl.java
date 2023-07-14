package com.location.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.math3.util.FastMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.location.Entity.User;
import com.location.Entity.UserType;
import com.location.Exception.UserException;
import com.location.Repository.UserRepository;

/**
 * 
 * UserServiceImpl class is the implementation of the UserService interface
 * 
 * @Service annotation denotes that this belongs to service layer
 * 
 * @Autowired denotes that Data Access Layer interface is connected to the
 *            Service Layer
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
	 * This method will take User class as an parameter and return that user after
	 * saving the data in database
	 * 
	 * If Email is already present in our database that means that user is already
	 * present It will throw an Exception
	 * 
	 * 
	 */

	@Override
	public User addTheUser(User user) throws UserException {
		
		if(user.getUserType().equals(UserType.READER)) {
			throw new UserException("You can't create the account");
		}
		
		Optional<User> opt = userRepository.findByEmail(user.getEmail());

		if (opt.isEmpty()) {
			return userRepository.save(user);
		} else {
			throw new UserException("User already present");
		}

	}

	/**
	 * This method will ask Number of user(Integer n) we want as an parameter return
	 * the List of n user
	 * 
	 */

	@Override
	public List<User> getNNearestUser(Integer n) throws UserException {

		List<User> users = userRepository.findAll();

		if (users.isEmpty()) {
			throw new UserException("User is not present");
		}

		List<User> filteredUser = users.stream().sorted((user1, user2) -> {
			Double distance1 = calculateDistance(user1.getLatitude(), user1.getLongitude());
			Double distance2 = calculateDistance(user2.getLatitude(), user2.getLongitude());
			return Double.compare(distance1, distance2);
		}).limit(n).collect(Collectors.toList());

		return filteredUser;

	}

	/**
	 * 
	 * This method will take User class as an parameter and return updated user
	 * after saving the updated user in database
	 * 
	 */

	@Override
	public User updatedTheUser(User user) throws UserException {
		
		if(user.getUserType().equals(UserType.READER)) {
			throw new UserException("You can't update the account");
		}
		
		Optional<User> opt = userRepository.findById(user.getUserId());

		if (opt.isEmpty()) {
			throw new UserException("User is not present");
		}

		User existingUser = opt.get();

		if (user.getLatitude() != null) {
			existingUser.setLatitude(user.getLatitude());
		}

		if (user.getLongitude() != null) {
			existingUser.setLongitude(user.getLongitude());
		}

		if (user.getName() != null) {
			existingUser.setName(user.getName());
		}
		
		if(user.getEmail() != null) {
			existingUser.setEmail(user.getEmail());
		}

		return userRepository.save(existingUser);

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

		Double a = FastMath.sin(deltaLatitude / 2) * FastMath.sin(deltaLatitude / 2) + FastMath.cos(toRadian(lat))
				* FastMath.cos(toRadian(0.0)) * FastMath.sin(deltaLongitude / 2) * FastMath.sin(deltaLongitude / 2);

		Double c = 2 * FastMath.atan2(FastMath.sqrt(a), FastMath.sqrt(1 - a));

		return earthRadius * c;

	}

	public Double toRadian(Double degree) {
		return degree * FastMath.PI / 180;
	}

	/**
	 * 
	 * @param userid admin's User id we have to give
	 * @param User Reader's Object we have to provide
	 * @return registered User will return
	 * 
	 */
	
	
	@Override
	public User addTheUserForReader(Integer userid, User user) throws UserException {

		Optional<User> opt = userRepository.findById(userid);

		if (opt.isEmpty()) {
			throw new UserException("You can't create the account");
		}

		if (opt.get().getUserType().equals(UserType.ADMIN)) {
			Optional<User> opt1 = userRepository.findByEmail(user.getEmail());

			if (opt1.isEmpty()) {
				return userRepository.save(user);
			} else {
				throw new UserException("User already present");
			}
		} else {
			throw new UserException("You can't create the account");
		}

	}

	/**
	 * 
	 * Method is for updating the user for reader
	 * 
	 * @param userid admin's User id we have to give
	 * @param User Reader's Object we have to provide what we want to update
	 * @return updated User will return
	 * 
	 * 
	 */
	
	@Override
	public User updateTheUser(Integer userid, User user) throws UserException {

		Optional<User> opt = userRepository.findById(userid);

		if (opt.isEmpty()) {
			throw new UserException("You can't update the account");
		}

		if (opt.get().getUserType() == UserType.ADMIN) {
			Optional<User> opt1 = userRepository.findById(user.getUserId());

			if (opt1.isEmpty()) {
				throw new UserException("User is not present");
			}

			User existingUser = opt.get();

			if (user.getLatitude() != null) {
				existingUser.setLatitude(user.getLatitude());
			}

			if (user.getLongitude() != null) {
				existingUser.setLongitude(user.getLongitude());
			}

			if (user.getName() != null) {
				existingUser.setName(user.getName());
			}
			
			if(user.getEmail() != null) {
				existingUser.setEmail(user.getEmail());
			}

			return userRepository.save(existingUser);
		} else {
			throw new UserException("You can't update the account");
		}

	}

}
