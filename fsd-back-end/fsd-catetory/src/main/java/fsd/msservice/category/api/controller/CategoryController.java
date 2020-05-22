package fsd.msservice.category.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fsd.msservice.category.api.model.CategoryVO;
import fsd.msservice.category.api.model.SubCategoryVO;
import fsd.msservice.category.api.service.CategoryService;

@RestController
@RequestMapping(value = "/api/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
	@Autowired
	private CategoryService service;

	@GetMapping("/buyer")
	public List<CategoryVO> findAllCategoriesForBuyer() {
		return service.findAllCategoriesForBuyer();
	}

	@GetMapping("/seller")
	public List<CategoryVO> findAllCategoriesForSeller() {
		return service.findAllCategoriesForSeller();
	}

	@PutMapping("")
	public Integer saveCategory(@RequestBody CategoryVO category) {
		if (category.getId() != null) {
			return service.updateCategory(category);
		} else {
			return service.addCategory(category);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteCategory(@PathVariable("id") Integer id) {
		service.delteCategory(id);
	}

	@PutMapping("/{id}/subcategory")
	public Integer saveSubCategory(@PathVariable("id") Integer categoryId, @RequestBody SubCategoryVO subcategory) {
		if (subcategory.getId() != null) {
			return service.updateSubCategory(categoryId, subcategory);
		} else {
			return service.addSubCategory(categoryId, subcategory);
		}
	}

	@DeleteMapping("/subcategory/{id}")
	public void deleteSubCategory(@PathVariable("id") Integer id) {
		service.delteSubCategory(id);
	}
}
