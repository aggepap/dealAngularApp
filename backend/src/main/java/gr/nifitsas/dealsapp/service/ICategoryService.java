package gr.nifitsas.dealsapp.service;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.CategoryInsertDTO;
import gr.nifitsas.dealsapp.dto.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.model.static_data.Category;

import java.util.List;

public interface ICategoryService {
  List<Category> findAllCategories();
  CategoryReadOnlyDTO saveCategory(CategoryInsertDTO dto) throws AppObjectAlreadyExists, AppObjectInvalidArgumentException;
  CategoryReadOnlyDTO deleteCategory(Long id) throws AppObjectInvalidArgumentException, AppObjectNotFoundException;
}
