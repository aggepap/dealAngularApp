package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;

import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreInsertDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryInsertDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryUpdateDTO;
import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.model.static_data.Store;
import gr.nifitsas.dealsapp.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
  private final CategoryRepository categoryRepository;
  private final Mapper mapper;

  /**
   * Finds a category by its ID.
   *
   * @param id The ID of the category to find.
   * @return An Optional containing the category if found, or empty if not found.
   */
  @Override
  public Optional<Category> findCategoryById(Long id) {
    return categoryRepository.findById(id);
  }

  /**
   * Retrieves all categories.
   *
   * @return A list of all categories as CategoryReadOnlyDTO objects.
   */
  @Override
  public List<CategoryReadOnlyDTO> findAllCategories() {
    return categoryRepository.findAll().stream().map(mapper::mapToCategoryReadOnlyDTO).collect(Collectors.toList());
  }

  /**
   * Saves a new category.
   *
   * @param dto A CategoryInsertDTO object containing the category information.
   * @return The saved category as a CategoryReadOnlyDTO object.
   * @throws AppObjectAlreadyExistsException If a category with the same name already exists.
   * @throws AppObjectInvalidArgumentException If the provided DTO is invalid.
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public CategoryReadOnlyDTO saveCategory(CategoryInsertDTO dto) throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException {
    if (categoryRepository.findByName(dto.getName()).isPresent()) {
      throw new AppObjectAlreadyExistsException("Category", "Category with name " + dto.getName() + " already exists");
    }
    Category category = mapper.mapToCategoryEntity(dto);
    Category savedCategory = categoryRepository.save(category);
    return mapper.mapToCategoryReadOnlyDTO(savedCategory);
  }

  /**
   * Updates an existing category.
   *
   * @param categoryUpdateDTO A CategoryUpdateDTO object containing the updated category information.
   * @return The updated category as a CategoryReadOnlyDTO object.
   * @throws AppObjectNotFoundException If the category to update is not found.
   * @throws AppObjectAlreadyExistsException If a category with the same name already exists (excluding the category being updated).
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public CategoryReadOnlyDTO updateCategory(CategoryUpdateDTO categoryUpdateDTO) throws AppObjectNotFoundException, AppObjectAlreadyExistsException {
    Optional<Category> existingCategory = categoryRepository.findByName(categoryUpdateDTO.getName());
    if (existingCategory.isPresent() && !existingCategory.get().getId().equals(categoryUpdateDTO.getId())) {
      throw new AppObjectAlreadyExistsException("Category", "Category with name '" + categoryUpdateDTO.getName() + "' already exists.");
    }
    Category selectedCategory = categoryRepository.findById(categoryUpdateDTO.getId())
      .orElseThrow(() -> new AppObjectNotFoundException("Category", "Category with id " + categoryUpdateDTO.getId() + " not found."));
    selectedCategory.setIcon(categoryUpdateDTO.getIcon());
    selectedCategory.setName(categoryUpdateDTO.getName());
    Category updatedCategory = categoryRepository.save(selectedCategory);
    return mapper.mapToCategoryReadOnlyDTO(updatedCategory);
  }

  /**
   * Deletes a category. If the category to be deleted has products, all it's products
   * are moved to "Other" cateogory(created automatically if not already present)
   *
   * @param id The ID of the category to delete.
   * @return The deleted category as a CategoryReadOnlyDTO object.
   * @throws AppObjectInvalidArgumentException If the category 'Other' is attempted to be deleted.
   * @throws AppObjectNotFoundException If the category to delete is not found.
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public CategoryReadOnlyDTO deleteCategory(Long id) throws AppObjectInvalidArgumentException, AppObjectNotFoundException {
    Optional<Category> optionalCategory = categoryRepository.findById(id);

    if(optionalCategory.orElseThrow().getName().equals("Other")){
      throw new AppObjectInvalidArgumentException("Category", "Category 'Other' cannot be deleted");
    }

    if (optionalCategory.isPresent()) {
      Optional<Category> defaultCategory = categoryRepository.findByName("Other");

      Category resolvedDefaultCategory;
      if (defaultCategory.isEmpty()) {
        var defaultCategoryDTO = new CategoryInsertDTO("Other", "fa-circle-info");
       Category defaultCategoryToSave = mapper.mapToCategoryEntity(defaultCategoryDTO);
        categoryRepository.save(defaultCategoryToSave);
        resolvedDefaultCategory = defaultCategoryToSave;
      } else{
        resolvedDefaultCategory = defaultCategory.get();
      }

      Set<Product> optionalProducts = optionalCategory.get().getAllCategoryProducts();
      for (Product product : optionalProducts) {
        product.setCategory(resolvedDefaultCategory);
      }
      Category category = optionalCategory.get();
      categoryRepository.delete(category);
      return mapper.mapToCategoryReadOnlyDTO(category);
    } else {
      throw new AppObjectNotFoundException("Category", "Category with id: " + id + " not found");
    }
    }

}
