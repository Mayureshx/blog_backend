package org.blog.repository;



import org.blog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
//	@Query(value="DELETE * FROM category c WHERE c.categoryId=?1",nativeQuery=true)
//	public void deleteById(long categoryId);

}
