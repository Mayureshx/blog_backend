package org.blog.service;

import java.util.List;

import org.blog.model.Comment;
import org.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository repository;

	@Override
	public void addComment(Comment comment) {
		
		this.repository.save(comment);
		
	}

	@Override
	public void updateComment(Comment comment) {
		this.repository.save(comment);
		
	}

	@Override
	public List<Comment> findCommentsByPostId(long postId) {
		
		return this.repository.findBypost(postId);
	}

	@Override
	public void deleteComment(long commentId) {
		
		this.repository.deleteById(commentId);
		
	}

	@Override
	public List<Comment> findCommentsByUserId(long userId) {
		// TODO Auto-generated method stub
		return this.repository.findCommentsByUserId(userId);
	}

	//modification no.3)
	@Override
	public void deleteCommentByPostId(long postId) {
		// TODO Auto-generated method stub
		
		this.repository.deleteComment(postId);
	}

	
}
