package com.rpl.hotel;

import com.rpl.hotel.domain.*;
import com.rpl.hotel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class HotelApplication {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository) {

		return args -> {

			Role adminRole = roleRepository.findByRole("ADMIN");
			if (adminRole == null) {
				Role newAdminRole = new Role();
				newAdminRole.setRole("ADMIN");
				roleRepository.save(newAdminRole);
			}

			Role hotelManager = roleRepository.findByRole("HMM");
			if (hotelManager == null) {
				Role newhotelManager = new Role();
				newhotelManager.setRole("HMM");
				roleRepository.save(newhotelManager);
			}

//			Role userRole = roleRepository.findByRole("USER");
//			if (userRole == null) {
//				Role newUserRole = new Role();
//				newUserRole.setRole("USER");
//				roleRepository.save(newUserRole);
//			}

			Role frontDeskRole = roleRepository.findByRole("FDR");
			if ( frontDeskRole == null ){
				Role newFrontDeskRole = new Role();
				newFrontDeskRole.setRole("FDR");
				roleRepository.save(newFrontDeskRole);
			}

			Role restaurantFrontDesk = roleRepository.findByRole("RFD");
			if ( restaurantFrontDesk == null ){
				Role newrestaurantFrontDesk = new Role();
				newrestaurantFrontDesk.setRole("RFD");
				roleRepository.save(newrestaurantFrontDesk);
			}

			User newUser = userRepository.findByEmail("admin@admin.com");
			if( newUser == null ){
				User user = new User();
				user.setFullname("admin");
				user.setEmail("admin@admin.com");
				user.setPassword(bCryptPasswordEncoder.encode("123qwe"));
				user.setEnabled(true);
				Role newUserRole = roleRepository.findByRole("ADMIN");
				user.setRoles(new HashSet<>(Arrays.asList(newUserRole)));
				userRepository.save(user);
			}
		};

	}


}
