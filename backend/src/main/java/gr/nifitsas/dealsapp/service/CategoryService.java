package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;

import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.CategoryInsertDTO;
import gr.nifitsas.dealsapp.dto.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
  private final CategoryRepository categoryRepository;
  private final Mapper mapper;

  @Override
  public List<Category> findAllCategories() {
    return categoryRepository.findAll();
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
