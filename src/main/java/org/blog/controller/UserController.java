package org.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.blog.model.Post;
import org.blog.model.User;
import org.blog.repository.PostRepository;
import org.blog.repository.UserRepository;
import org.blog.service.CommentService;
import org.blog.service.PostService;
import org.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/blog/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository repository; 
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	PostService postService;
	
	@Autowired
	private CommentService commentService;
 	
	@PostMapping("/signup")
	public ResponseEntity<Map<String,String>> singup(@Valid @RequestBody User user)
	{
//		user.setStatus("inactive");
		this.userService.addUser(user);
		Map<String,String> response=new HashMap<String,String>();
		response.put("status", "success");
		response.put("message", "User registered!!");
		
		return new ResponseEntity<Map<String,String>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/login")
	public ResponseEntity<Map<String,String>> login(@RequestParam("email") String email,@RequestParam("password") String password)
	{
		Optional<User> existingUser=this.userService.getUserByEmail(email);
		Map<String,String> response=new HashMap<String,String>();
		if(existingUser.isPresent())
		{
			if(existingUser.get().getUserPassword().equals(password))
			{
				
				response.put("status", "success");
				response.put("message", "User authenticated");
				response.put("userId",String.valueOf( existingUser.get().getUserId()) );
				response.put("userName",String.valueOf( existingUser.get().getUserName()) );
				response.put("userRole", existingUser.get().getRole());
				return new ResponseEntity<Map<String,String>>(response,HttpStatus.OK);
			}
			else
			{
				response.put("status", "Failed");
				response.put("message", "User password inncorrect");
				return new ResponseEntity<Map<String,String>>(response,HttpStatus.NOT_FOUND);
			}
		}		
		else
		{
			response.put("status", "Failed");
			response.put("message", "User email does not exist");
			return new ResponseEntity<Map<String,String>>(response,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUSers()
	{
		return new ResponseEntity<List<User>>(this.repository.findAll(),HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<Map<String,String>> deleteUser(@PathVariable long userId)
	{
		Map<String,String> response=new HashMap<String,String>();
		if(this.repository.findById(userId).isPresent())
		{
		 List<Post>	existingPost =this.postService.getPostsByUserId(userId);
		 
		 for(Post p: existingPost)
		 {
			 this.commentService.deleteCommentByPostId(p.getPostId());
		 }
			
			this.postService.deletePostByUserId(userId);//modification no.2)
			
			this.repository.delete(this.repository.findById(userId).get());
			response.put("status", "Success");
			response.put("message", "User deleted!!");
			return new ResponseEntity<Map<String,String>>(response,HttpStatus.OK);
		}
		else
		{
			response.put("status", "failed");
			response.put("message", "User does not exist!!");
			return new ResponseEntity<Map<String,String>>(response,HttpStatus.NOT_FOUND);
		}
		
	}
	
}
