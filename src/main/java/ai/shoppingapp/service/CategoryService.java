package ai.shoppingapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.shoppingapp.model.CategoryModel;
import ai.shoppingapp.repository.CategoryRepository;
import ai.shoppingapp.repository.entity.Category;

@Service
public class CategoryService {

	private final CategoryRepository repo;

	public CategoryService(CategoryRepository repo) {
		this.repo = repo;
	}

// LIST
	public List<CategoryModel> findAll() {

		List<Category> entities = this.repo.findAll();

		List<CategoryModel> categories = entities.stream().map(this::toDto).toList();

		return categories;
	}

// DETAIL
	public CategoryModel findById(String id) {

		Category entity = this.repo.findById(id);

		return toDto(entity);
	}

// CREATE
	public int add(CategoryModel dto) {

		Category entity = toEntity(dto);

		return this.repo.save(entity);
	}

// UPDATE
	public int edit(String id, CategoryModel dto) {

		Category entity = toEntity(dto);

		return this.repo.edit(id, entity);
	}

// DELETE
	public int delete(String id) {

		return this.repo.delete(id);
	}

// Entity → DTO
	private CategoryModel toDto(Category entity) {

		CategoryModel dto = new CategoryModel();

		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setImage(entity.getImage());
		dto.setCreatedUserId(entity.getCreatedUserId());
		dto.setUpdatedUserId(entity.getUpdatedUserId());
		dto.setIsActive(entity.getIsActive());

		return dto;
	}

// DTO → Entity
	private Category toEntity(CategoryModel dto) {

		Category entity = new Category();

		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setImage(dto.getImage());
		entity.setCreatedUserId(dto.getCreatedUserId());
		entity.setUpdatedUserId(dto.getUpdatedUserId());
		entity.setIsActive(dto.getIsActive());

		return entity;
	}
}