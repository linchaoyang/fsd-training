package fsd.msservice.category.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import fsd.msservice.category.api.entity.CategoryEntity;
import fsd.msservice.category.api.entity.SubCategoryEntity;
import fsd.msservice.category.api.exception.DuplicateNameException;
import fsd.msservice.category.api.model.CategoryVO;
import fsd.msservice.category.api.model.SubCategoryVO;
import fsd.msservice.category.api.repository.CategoryRepository;
import fsd.msservice.category.api.repository.SubCategoryRepository;
import fsd.msservice.category.api.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private SubCategoryRepository subRepository;

	@Override
	public List<CategoryVO> findAllCategoriesForBuyer() {

		List<CategoryVO> categories = new ArrayList<>();

		List<CategoryEntity> entities = repository.findAll();

		if (!ObjectUtils.isEmpty(entities)) {
			entities.forEach(entity -> {
				CategoryVO category = new CategoryVO();
				category.setId(entity.getId());
				category.setName(entity.getName());
				// get the first content before space
				String description = entity.getDescription().split(" ", 2)[0];
				category.setDescription(description);

				categories.add(category);
			});
		}

		return categories;
	}

	@Override
	public List<CategoryVO> findAllCategoriesForSeller() {
		List<CategoryVO> categories = new ArrayList<>();

		List<CategoryEntity> entities = repository.findAll();

		if (!ObjectUtils.isEmpty(entities)) {
			entities.forEach(entity -> {
				CategoryVO category = new CategoryVO();
				category.setId(entity.getId());
				category.setName(entity.getName());

				List<SubCategoryVO> subcategories = new ArrayList<>();
				List<SubCategoryEntity> subcategoryEntities = entity.getSubcategories();
				if (!ObjectUtils.isEmpty(subcategoryEntities)) {
					subcategoryEntities.forEach(subcategoryEntity -> {
						SubCategoryVO subcategory = new SubCategoryVO();
						subcategory.setId(subcategoryEntity.getId());
						subcategory.setName(subcategoryEntity.getName());
						subcategory.setGtn(subcategoryEntity.getTax());
						subcategories.add(subcategory);
					});
				}

				category.setSubcategories(subcategories);

				categories.add(category);
			});
		}

		return categories;
	}

	@Transactional
	@Override
	public Integer addCategory(CategoryVO category) {
		return saveCategory(category);
	}

	@Transactional
	@Override
	public Integer updateCategory(CategoryVO category) {
		return saveCategory(category);

	}

	private Integer saveCategory(CategoryVO category) {
		CategoryEntity entity = new CategoryEntity();
		if (category.getId() != null) {
			entity.setId(category.getId());
		}
		entity.setName(category.getName());
		entity.setDescription(category.getDescription());

		return repository.saveAndFlush(entity).getId();

	}

	@Transactional
	@Override
	public void delteCategory(Integer id) {
		repository.deleteById(id);
	}

	@Transactional
	@Override
	public Integer addSubCategory(Integer categoryId, SubCategoryVO subcategory) {
		return saveSubCategory(categoryId, subcategory);

	}

	@Transactional
	@Override
	public Integer updateSubCategory(Integer categoryId, SubCategoryVO subcategory) {

		return saveSubCategory(categoryId, subcategory);
	}

	private Integer saveSubCategory(Integer categoryId, SubCategoryVO subcategory) {

		if (ObjectUtils.isEmpty(categoryId)) {
			throw new IllegalArgumentException("categoryId");
		}

		SubCategoryEntity entity = subRepository.findByName(categoryId, subcategory.getName());
		if (entity != null) {
			throw new DuplicateNameException(entity.getName());
		}

		entity = new SubCategoryEntity();
		if (subcategory.getId() != null) {
			entity.setId(subcategory.getId());
		}
		entity.setName(subcategory.getName());
		entity.setDescription(subcategory.getDescription());
		entity.setTax(subcategory.getGtn());

		CategoryEntity category = new CategoryEntity();
		category.setId(categoryId);
		entity.setCategory(category);

		return subRepository.saveAndFlush(entity).getId();
	}

	@Override
	public void delteSubCategory(Integer id) {
		subRepository.deleteById(id);
	}

}
