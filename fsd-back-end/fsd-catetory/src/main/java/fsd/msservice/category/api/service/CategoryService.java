package fsd.msservice.category.api.service;

import java.util.List;

import fsd.msservice.category.api.model.CategoryVO;
import fsd.msservice.category.api.model.SubCategoryVO;

public interface CategoryService {

	List<CategoryVO> findAllCategoriesForBuyer();

	List<CategoryVO> findAllCategoriesForSeller();

	Integer addCategory(CategoryVO category);

	Integer updateCategory(CategoryVO category);

	void delteCategory(Integer id);

	Integer addSubCategory(Integer categoryId, SubCategoryVO subcategory);

	Integer updateSubCategory(Integer categoryId, SubCategoryVO subcategory);

	void delteSubCategory(Integer id);
}
