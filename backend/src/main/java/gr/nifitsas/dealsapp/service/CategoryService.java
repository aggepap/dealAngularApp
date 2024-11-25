package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;

import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryInsertDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryUpdateDTO;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
  private final CategoryRepository categoryRepository;
  private final Mapper mapper;

  @Override
  public Optional<Category> findCategoryById(Long id) {
    return categoryRepository.findById(id);
  }

  @Override
  public List<CategoryReadOnlyDTO> findAllCategories() {
    return categoryRepository.findAll().stream().map(mapper::mapToCategoryReadOnlyDTO).collect(Collectors.toList());
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public CategoryReadOnlyDTO saveCategory(CategoryInsertDTO dto) throws AppObjectAlreadyExists, AppObjectInvalidArgumentException {
    if (categoryRepository.findByName(dto.getName()).isPresent()) {
      throw new AppObjectAlreadyExists("Category", "Category with name " + dto.getName() + " already exists");
    }
    Category category = mapper.mapToCategoryEntity(dto);
    Category savedCategory = categoryRepository.save(category);
    return mapper.mapToCategoryReadOnlyDTO(savedCategory);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public CategoryReadOnlyDTO updateCategory(CategoryUpdateDTO categoryUpdateDTO) throws AppObjectNotFoundException, AppObjectAlreadyExists {
    Optional<Category> existingCategory = categoryRepository.findByName(categoryUpdateDTO.getName());
    if (existingCategory.isPresent() && !existingCategory.get().getId().equals(categoryUpdateDTO.getId())) {
      throw new AppObjectAlreadyExists("Category", "Category with name '" + categoryUpdateDTO.getName() + "' already exists.");
    }

    Category selectedCategory = categoryRepository.findById(categoryUpdateDTO.getId())
      .orElseThrow(() -> new AppObjectNotFoundException("Category", "Category with id " + categoryUpdateDTO.getId() + " not found."));
    selectedCategory.setIcon(categoryUpdateDTO.getIcon());
    selectedCategory.setName(categoryUpdateDTO.getName());
    Category updatedCategory = categoryRepository.save(selectedCategory);
    return mapper.mapToCategoryReadOnlyDTO(updatedCategory);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public CategoryReadOnlyDTO deleteCategory(Long id) throws AppObjectInvalidArgumentException, AppObjectNotFoundException {
    Optional<Category> optionalCategory = categoryRepository.findById(id);

    if (optionalCategory.isPresent()) {
      Category category = optionalCategory.get();
      categoryRepository.delete(category);
      return mapper.mapToCategoryReadOnlyDTO(category);
    } else {
      throw new AppObjectNotFoundException("Category", "Category not found with id: " + id);
    }
  }
}
