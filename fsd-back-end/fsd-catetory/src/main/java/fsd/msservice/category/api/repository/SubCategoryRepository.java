package fsd.msservice.category.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fsd.msservice.category.api.entity.SubCategoryEntity;

public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Integer> {

	@Query("select sc from SubCategoryEntity sc where sc.category.id = :id and sc.name = :name")
	SubCategoryEntity findByName(@Param("id") Integer categoryId, @Param("name") String name);
}
