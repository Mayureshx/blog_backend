
package org.blog;

import org.blog.model.Post;
import org.blog.repository.PostRepository;
import org.blog.service.PostServiceImpl;
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
public class PostServiceTests {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post=null;

    @BeforeEach
    public void setup(){
      
        post=new Post();
        post.setPostId(1L);
        post.setPostTitle("Demo Title");
        post.setPostContent("Demo content");
                
    }

    @DisplayName("JUnit test for savePost method")
    @Test
    public void givenPostObject_whenSavePost_thenReturnPostObject(){
    	
    	 // given - precondition or setup
    	BDDMockito.given(postService.addPost(post)).willReturn(post);

        // when -  action or the behaviour that we are going test
        Post savedPost = postRepository.save(post);

     
        // then - verify the output
        assertThat(savedPost).isNotNull();
    }
    
    
    
    
    // JUnit test for getAllPosts method
    @DisplayName("JUnit test for getAllPosts method")
    @Test
    public void givenPostList_whenGetAllPost_thenReturnPostList(){
        
    	
    	// given - precondition or setup

    	Post post1=new Post();
        post.setPostId(1L);
        post.setPostTitle("Demo Title");
        post.setPostContent("Demo content");
        
        
        Post post2=new Post();
        post.setPostId(1L);
        post.setPostTitle("Demo1 Title");
        post.setPostContent("Demo1 content");
        
       BDDMockito.given(postRepository.findAll())
        
         .willReturn(List.of(post1,post2));

        // when -  action or the behaviour that we are going test
        List<Post> posts = postService.getAllPosts();

        // then - verify the output
        assertThat(posts).isNotNull();
        assertThat(posts.size()).isEqualTo(2);
    }

    
    

	
    }
