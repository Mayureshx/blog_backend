package org.blog;

import org.blog.model.Post;
import org.blog.model.User;
import org.blog.repository.PostRepository;
import org.blog.repository.UserRepository;
import org.blog.service.PostServiceImpl;
import org.blog.service.UserService;
import org.blog.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	 @Mock
	    private UserRepository userRepository;

	    @InjectMocks
	    private UserServiceImpl userService;

	    private User user=null;

	    @BeforeEach
	    public void setup(){
	      
	        user=new User();
	        user.setUserId(123L);
	        user.setUserName("Raman");
	        user.setUserEmail("raman@gmail.com");
	        user.setRole("USER");
	        user.setUserPassword("12345678");
	                
	    }
	    
	    
	    
	    @DisplayName("JUnit test for getUserByEmail method")
	    @Test
	    public void getUserByEmailTest(){
	        
	    	
	    	// given - precondition or setup

	    	BDDMockito.given(userService.getUserByEmail(user.getUserEmail()))
	    	  .willReturn(Optional.of(user));
	        
	     
	        // when -  action or the behaviour that we are going test
	        Optional<User> existingUserObj = userService.getUserByEmail(user.getUserEmail());

	        // then - verify the output
	        assertThat(existingUserObj.get()).isNotNull();
	        assertThat(existingUserObj.get().getUserEmail()).isEqualTo(user.getUserEmail());
	    }

	    
}
