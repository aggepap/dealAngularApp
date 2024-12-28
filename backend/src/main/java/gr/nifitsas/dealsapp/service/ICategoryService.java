package gr.nifitsas.dealsapp.service;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryInsertDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryUpdateDTO;
import gr.nifitsas.dealsapp.model.static_data.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
  List<CategoryReadOnlyDTO> findAllCategories();
  Optional<Category> findCategoryById(Long id);
  CategoryReadOnlyDTO saveCategory(CategoryInsertDTO dto) throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException;
  CategoryReadOnlyDTO deleteCategory(Long id) throws AppObjectInvalidArgumentException, AppObjectNotFoundException;
  CategoryReadOnlyDTO updateCategory(CategoryUpdateDTO categoryUpdateDTO) throws AppObjectInvalidArgumentException, AppObjectNotFoundException, AppObjectAlreadyExistsException;
}
