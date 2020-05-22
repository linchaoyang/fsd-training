package fsd.msservice.category.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fsd.msservice.category.api.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

	@Query("select c from CategoryEntity c where c.name = :name")
	CategoryEntity findByName(@Param("name") String name);
}
