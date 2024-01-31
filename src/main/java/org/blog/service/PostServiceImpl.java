package org.blog.service;

import java.util.List;
import java.util.Optional;

import org.blog.model.Post;
import org.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	
	@Override
	public Post addPost(Post post) {
		
	 return this.postRepository.save(post);
	}

	@Override
	public List<Post> getAllPosts() {
		
		return this.postRepository.findAll();
	}

	@Override
	public List<Post> getPostsByCategory(long categoryId) {
		
		return this.postRepository.findByCategoryId(categoryId);
	}

	@Override
	public Optional<Post> getPostByPostId(long postId) {
		
		return this.postRepository.findById(postId);
	}

	@Override
	public Optional<Post> getPostByPostTitle(String title) {
	
		return this.postRepository.findPostBypostTitle(title);
	}
	
	@Override
	public List<Post> getPostByKeyword(String keyword) {
	
		return this.postRepository.findPostByKeyword(keyword);
	}

	@Override
	public List<Post> getPostsByUserId(long userId) {
		
		return this.postRepository.findPostsByUserId(userId);
	}
	 
		@Override
	    public List<Post> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

			Pageable pageable = PageRequest.of(pageNo, pageSize, Direction.DESC, sortBy);

	        return postRepository.findAll(pageable).toList();
	    }

		@Override
		public void postStatusChange(String status, Long postId) {
		
			this.postRepository.updateStatusByPostId(status, postId);
			
		}

		
		//modification no.2)
		@Override
		public void deletePostByUserId(long userId) {
			// TODO Auto-generated method stub
			
			System.out.println("User id : "+userId);
			this.postRepository.deletePost(userId);
			
		}
}
