package ai.shoppingapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.shoppingapp.model.CategoryDto;
import ai.shoppingapp.repository.CategoryRepository;
import ai.shoppingapp.repository.entity.Category;

@Service
public class CategoryService {

	private final CategoryRepository repo;

	public CategoryService(CategoryRepository repo) {
		this.repo = repo;
	}

// LIST
	public List<CategoryDto> findAll() {

		List<Category> entities = this.repo.findAll();

		List<CategoryDto> categories = entities.stream().map(this::toDto).toList();

		return categories;
	}

// DETAIL
	public CategoryDto findById(String id) {

		Category entity = this.repo.findById(id);

		return toDto(entity);
	}

// CREATE
	public int add(CategoryDto dto) {

		Category entity = toEntity(dto);

		return this.repo.save(entity);
	}

// UPDATE
	public int edit(String id, CategoryDto dto) {

		Category entity = toEntity(dto);

		return this.repo.edit(id, entity);
	}

// DELETE
	public int delete(String id) {

		return this.repo.delete(id);
	}

// Entity → DTO
	private CategoryDto toDto(Category entity) {

		CategoryDto dto = new CategoryDto();

		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setImage(entity.getImage());
		dto.setIsActive(entity.getIsActive());

		return dto;
	}

// DTO → Entity
	private Category toEntity(CategoryDto dto) {

		Category entity = new Category();

		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setImage(dto.getImage());
		entity.setIsActive(dto.getIsActive());

		return entity;
	}
}