package com.devopsbuddy.tests.integration;

import java.util.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amazonaws.services.ec2.model.CreatePlacementGroupRequest;
import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevopsbuddyApplication.class)
public class RepositoriesIntegrationTest {

	private static final int BASIC_PLAN_ID = 1;
	private static final int BASIC_ROLE_ID = 1;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void init() {
		Assert.assertNotNull(planRepository);
		Assert.assertNotNull(roleRepository);
		Assert.assertNotNull(userRepository);
	}
	
	@Test
	public void testCreateNewPlan() throws Exception {
		Plan basicPlan = createBasicPlan();
		planRepository.save(basicPlan);
		Plan retrievePlan = planRepository.findOne(BASIC_PLAN_ID);
		Assert.assertNotNull(retrievePlan);
	}
	
	@Test
	public void testCreateNewRole() throws Exception {
		Role userRole = createBasicRole();
		roleRepository.save(userRole);
		Role retrieveRole = roleRepository.findOne(BASIC_ROLE_ID);
		Assert.assertNotNull(retrieveRole);
	}
	
	@Test
	public void testCreateNewUser() throws Exception {
		Plan basicPlan = createBasicPlan();
		planRepository.save(basicPlan);
		
		User basicUser = createBasicUser();
		basicUser.setPlan(basicPlan);
		
		Role basicRole = createBasicRole();		
		
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole();
		userRole.setUser(basicUser);
		userRole.setRole(basicRole);
		userRoles.add(userRole);
		
		basicUser.getUserRoles().add(userRole);
		
		for(UserRole ur : userRoles) {
			roleRepository.save(ur.getRole());
		}
		
		basicUser = userRepository.save(basicUser);
		User newlyCreatedUser = userRepository.findOne(basicUser.getId());
		Assert.assertNotNull(newlyCreatedUser);
		Assert.assertTrue(newlyCreatedUser.getId() != 0);
		Assert.assertNotNull(newlyCreatedUser.getPlan());
		Assert.assertNotNull(newlyCreatedUser.getPlan().getId());
		Set<UserRole> newlyCreatedUserUserRoles = new HashSet<>();
		for(UserRole ur: newlyCreatedUserUserRoles) {
			Assert.assertNotNull(ur.getRole());
			Assert.assertNotNull(ur.getRole().getId());
		}
	}
	
	private Plan createBasicPlan() {
		Plan plan = new Plan();
		plan.setId(BASIC_PLAN_ID);
		plan.setName("Basic plan");		
		return plan;
	}
	
	private Role createBasicRole() {
		Role role = new Role();
		role.setId(BASIC_ROLE_ID);
		role.setName("ROLE_USER");		
		return role;
	}
	
	private User createBasicUser() {
		User user = new User();
		user.setUsername("basicUser");
		user.setPassword("secret");
		user.setEmail("azaveri@gmail.com");
		user.setFirstName("anand");
		user.setLastName("zaveri");
		user.setPhoneNumber("1234345454");
		user.setCountry("IN");
		user.setEnabled(true);
		user.setDescription("basic user");
		user.setProfileImageUrl("https://blable.images.com/basicUser");
		return user;
	}
	
}
