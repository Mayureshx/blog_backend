package org.blog.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//JPARepository  - pagination
//CRUDRepository - can't create pagination
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	
	@Query(value = "SELECT * FROM POST p WHERE p.category_id = ?1",nativeQuery = true  )
	public List<Post> findByCategoryId(long categoryId);

	@Query(value = "SELECT * FROM POST p WHERE p.user_id = ?1",nativeQuery = true  )
	public List<Post> findPostsByUserId(long userId);
	
	@Query(value = "SELECT * FROM POST p WHERE p.post_title = ?1",nativeQuery = true  )
	public Optional<Post> findPostBypostTitle(String title);
	
	
	@Query(value = "SELECT * FROM POST p WHERE p.post_title LIKE  %?1%",nativeQuery = true  )
	public List<Post> findPostByKeyword(String keyword);
	
	//@Modifying annotation is used whenever we writing JPQL query for modifying the records (create, update, delete)
	//@Transactional annotation is used whenever we are writing JPQL queries, it is from the org.springframework.transaction.annotation.Transactional package
	
	@Transactional
	@Modifying
	@Query(value="UPDATE POST SET status = :status WHERE postId = :postId",nativeQuery = true )
	void updateStatusByPostId(String status, Long postId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM POST p WHERE p.user_id= ?1",nativeQuery = true )
	void deletePost(long userId);
	
}
