package com.devopsbuddy.test.integration;

import java.util.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevopsbuddyApplication.class)
public class RepositoriesIntegrationTest {

	private static final int BASIC_PLAN_ID = 1;
	//private static final int BASIC_ROLE_ID = 1;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Rule public TestName testName = new TestName();
	
	@Before
	public void init() {
		Assert.assertNotNull(planRepository);
		Assert.assertNotNull(roleRepository);
		Assert.assertNotNull(userRepository);
		userRepository.deleteAll();
	}
	
	@Test
	public void testCreateNewPlan() throws Exception {
		//Plan basicPlan = createBasicPlan();
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Plan retrievePlan = planRepository.findOne(BASIC_PLAN_ID);
		Assert.assertNotNull(retrievePlan);
	}
	
	@Test
	public void testCreateNewRole() throws Exception {
		//Role userRole = createBasicRole();
		Role userRole = createRole(RolesEnum.BASIC);
		roleRepository.save(userRole);
		Role retrieveRole = roleRepository.findOne(RolesEnum.BASIC.getId());
		Assert.assertNotNull(retrieveRole);
	}
	
	@Test
	public void testCreateNewUser() throws Exception {
		String userName = testName.getMethodName();
		String email = testName.getMethodName() + "@gmail.com";
		//Plan basicPlan = createBasicPlan();
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		
		User basicUser = createBasicUser(userName, email);
		basicUser.setPlan(basicPlan);
		
		//Role basicRole = createBasicRole();
		Role basicRole = createRole(RolesEnum.BASIC);
		
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole(basicUser, basicRole);
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
	
	@Test
	public void testDeleteUser() {
		User basicUser = createBasicUser("a", "a@gmail.com");
		userRepository.save(basicUser);
		userRepository.delete(basicUser.getId());
	}
	
	private Plan createBasicPlan() {
		Plan plan = new Plan();
		plan.setId(BASIC_PLAN_ID);
		plan.setName("Basic plan");		
		return plan;
	}
	
	private Plan createPlan(PlansEnum plansEnum) {
		return new Plan(plansEnum);
	}
	
	private Role createBasicRole() {
		Role role = new Role();
		role.setId(RolesEnum.BASIC.getId());
		role.setName("ROLE_USER");		
		return role;
	}
	
	private Role createRole(RolesEnum rolesEnum) {
		return new Role(rolesEnum);
	}
	
	private User createBasicUser(String username, String email) {
		User user = new User();
		user.setUsername(username);
		user.setPassword("secret");
		user.setEmail(email);
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
