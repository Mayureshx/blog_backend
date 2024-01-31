package org.blog.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.blog.model.Post;
import org.blog.repository.CategoryRepository;
import org.blog.repository.PostRepository;
import org.blog.repository.UserRepository;
import org.blog.service.CommentService;
import org.blog.service.PostService;
import org.blog.service.UserService;
import org.blog.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/blog/")
public class PostController {

	@Autowired
	private PostService service;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post")  //localhost:8080/api/blog
	public ResponseEntity<Map<String,String>> addPost(
			@RequestParam("postId")long postId,
			@RequestParam("postTitle")String postTitle,
			@RequestParam("postContent")String postContent,
			@RequestParam(name = "postImage",required = false)MultipartFile postImage,
			@RequestParam("categoryId")String categoryId,
			@RequestParam("userId")String userId) throws IOException
	{
		Post post=new Post();
		post.setPostId(postId);
		post.setPostTitle(postTitle);
		post.setPostContent(postContent);
		post.setPostImage(postImage.getBytes());
		post.setStatus("inactive");
		post.setCategory(categoryRepo.findById(Long.parseLong(categoryId)).get());
		post.setUser(userRepository.findById(Long.parseLong(userId)).get());
		this.service.addPost(post);
		
		Map<String,String> response=new HashMap<String,String>();
		response.put("status", "success");
		response.put("message", "Post added!!");
		return new ResponseEntity<Map<String,String>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/post")  //localhost:8080/api/blog
	public ResponseEntity<Map<String,String>> updatePost(
			@RequestParam("postId")long postId,
			@RequestParam("postTitle")String postTitle,
			@RequestParam("postContent")String postContent,
			@RequestParam("status")String  status,
			@RequestParam("postImage")MultipartFile postImage,
			@RequestParam("categoryId")String categoryId) throws IOException
	{
		if(this.service.getPostByPostId(postId).isPresent())
		{
			
		Post post=this.service.getPostByPostId(postId).get();
		post.setPostTitle(postTitle);
		post.setPostContent(postContent);
		post.setPostImage(postImage.getBytes());
		post.setStatus(status);
		post.setCategory(categoryRepo.findById(Long.parseLong(categoryId)).get());
		
		this.service.addPost(post);
		
		Map<String,String> response=new HashMap<String,String>();
		response.put("status", "success");
		response.put("message", "Post updated!!");
		return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);
	}
		else
		{
			Map<String,String> response=new HashMap<String,String>();
			response.put("status", "failed");
			response.put("message", "Post not found!!");
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/posts")
	public ResponseEntity<List<Post>> getAllPosts()
	{
		return new ResponseEntity<List<Post>>(this.service.getAllPosts(), HttpStatus.OK);
	}
	
	@GetMapping("/posts/{categoryId}")
	public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable("categoryId")long categoryId)
	{
		return new ResponseEntity<List<Post>>(this.service.getPostsByCategory(categoryId),HttpStatus.OK);
	}
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<?> getPostsById(@PathVariable long postId)
	{
		if(this.service.getPostByPostId(postId).isPresent())
		{
			return new ResponseEntity<Post>(this.service.getPostByPostId(postId).get(),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Post not found!",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/post/user/{userId}")
	public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable long userId)
	{
		
			return new ResponseEntity<List<Post>>(this.service.getPostsByUserId(userId),HttpStatus.OK);
		
	}
	
	@GetMapping("/post/search/")
	public ResponseEntity<?> getPostsByTitle(@RequestParam("keyword") String keyword)
	{
		keyword=keyword.toLowerCase();
		if(this.service.getPostByKeyword(keyword).size()>0)
		{
			return new ResponseEntity<List<Post>>(this.service.getPostByKeyword(keyword),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("No post found!",HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<Map<String,String>> deteleByPostId(@PathVariable long postId)
	{
		Map<String,String> response=new HashMap<String,String>();
		try
		{
			
			this.commentService.deleteCommentByPostId(postId);//modification no.3)
			
				this.postRepository.deleteById(postId);
				
				response.put("status", "success");
				response.put("message", "Post deleted!!");
				return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);	
			}
		catch(Exception e)
		{
			response.put("status", "failed");
			response.put("message", "Post id not found!!");
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.NOT_FOUND);	
		}
	}
	  private Sort.Direction getSortDirection(String direction) {
		    if (direction.equals("asc")) {
		      return Sort.Direction.ASC;
		    } else if (direction.equals("desc")) {
		      return Sort.Direction.DESC;
		    }

		    return Sort.Direction.ASC;
		  }
	@GetMapping("/pageposts")
	  public ResponseEntity<Map<String, Object>> getAllTPostsPage(
	      @RequestParam(required = false) String title,
	      @RequestParam(defaultValue = "0") int page,
	      @RequestParam(defaultValue = "5") int size,
	      @RequestParam(defaultValue = "postId,desc") String[] sort) {

	    try {
	      List<Order> orders = new ArrayList<Order>();

	      if (sort[0].contains(",")) {
	        // will sort more than 2 fields
	        // sortOrder="field, direction"
	        for (String sortOrder : sort) {
	          String[] _sort = sortOrder.split(",");
	          orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
	        }
	      } else {
	        // sort=[field, direction]
	        orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	      }

	      List<Post> posts = new ArrayList<Post>();
	      Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

	      Page<Post> pageTuts = null;
	      if (title == null)
	        pageTuts = postRepository.findAll(pagingSort);
	     
	      posts = pageTuts.getContent();

	      Map<String, Object> response = new HashMap<>();
	      response.put("posts", posts);
	      response.put("currentPage", pageTuts.getNumber());
	      response.put("totalItems", pageTuts.getTotalElements());
	      response.put("totalPages", pageTuts.getTotalPages());

	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	    	System.out.println(e);
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	
//	@PutMapping("/post-status-update")  //localhost:8080/api/blog
//	public ResponseEntity<Map<String,String>> updatePostStatus(
//			@RequestParam("postId")long postId,
//			@RequestParam("status")String status)
//				{
//		if(this.service.getPostByPostId(postId).isPresent())
//		{
//			
//		Post post=this.service.getPostByPostId(postId).get();
//		post.setStatus(status);
//	
//		
//		this.service.addPost(post);
//		
//		Map<String,String> response=new HashMap<String,String>();
//		response.put("status", "success");
//		response.put("message", "Post status updated!!");
//		return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);
//	}
//		else
//		{
//			Map<String,String> response=new HashMap<String,String>();
//			response.put("status", "failed");
//			response.put("message", "Post not found!!");
//			return new ResponseEntity<Map<String,String>>(response, HttpStatus.NOT_FOUND);
//		}
//	}	
	
}
