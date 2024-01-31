package org.blog.service;

import java.util.List;
import java.util.Optional;

import org.blog.model.Post;

public interface PostService {

	public Post addPost(Post post);
	public List<Post> getAllPosts();
	public List<Post> getPostsByCategory(long categoryId);
	public Optional<Post> getPostByPostId(long postId);
	public List<Post> getPostsByUserId(long userId);
	public Optional<Post> getPostByPostTitle(String title);
	public List<Post> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	public List<Post> getPostByKeyword(String keyword);
	public void postStatusChange(String status, Long postId);
	public void deletePostByUserId(long userId);//modification no.2)
}
