package com.location.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represent User class with table name 'user_location'
 * 
 * Here the data of the user will be stored like Name, Longitude, Latitude
 * All the field is mandatory
 * 
 * @Data annotation will generate all the getter and setter method
 * @AllArgsConstructor will generate the constructor with all the parameter
 * @NoArgsConstructor will generate the constructor without any arguments
 * 
 * 
 * userId will be the primary key with auto generated value
 * 
 * 
 * @author SHIV KUMAR
 *
 */

@Entity
@Table(name = "user_location")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	
	@NotNull(message = "Name is mandatory")
	private String name;
	
	@Email(message = "Invalid email")
	@Column(unique = true)
	private String email;
	
	@NotNull(message = "Longitude is mandatory")
	private Double longitude;
	
	@NotNull(message = "Latitude is mandatory")
	private Double latitude;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
}
