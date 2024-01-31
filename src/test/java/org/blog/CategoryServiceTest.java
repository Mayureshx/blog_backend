package org.blog;
import org.blog.model.Category;
import org.blog.model.Post;
import org.blog.model.User;
import org.blog.repository.CategoryRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	 	@Mock
	    private CategoryRepository categoryRepository;

	    private Category category=null;

	    @BeforeEach
	    public void setup(){
	      
	    	category=new Category();
	    	category.setCategoryName("Java");
	                
	    }
	    
	    
	    
	    @DisplayName("JUnit test for save category method")
	    @Test
	    public void saveCategoryTest(){
	    	
	    	 // given - precondition or setup
	    	BDDMockito.given(categoryRepository.save(category)).willReturn(category);

	        // when -  action or the behaviour that we are going test
	        Category savedCategory = categoryRepository.save(category);

	     
	        // then - verify the output
	        assertNotNull(savedCategory);
	        assertThat(savedCategory.getCategoryId()>0 );
	        assertEquals(savedCategory.getCategoryName(),category.getCategoryName());
	    }
	    
	
}
