package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.controller.CategoryController;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryInsertDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryUpdateDTO;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerJunitTest {

  @Mock
  private CategoryService categoryService;

  private CategoryController categoryController;

  @BeforeEach
  public void setUp() {
    categoryController = new CategoryController(categoryService);
  }

  // Test get all categories - Success
  @Test
  public void testGetAllCategories_success() throws Exception {
    List<CategoryReadOnlyDTO> categoryList = mock(List.class);
    when(categoryService.findAllCategories()).thenReturn(categoryList);

    ResponseEntity<List<CategoryReadOnlyDTO>> response = categoryController.getallCategories();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(categoryService, times(1)).findAllCategories();
  }

  // Test get all categories - Exception
  @Test
  public void testGetAllCategories_throwsException() throws Exception {
    when(categoryService.findAllCategories()).thenThrow(new RuntimeException("Test Exception"));

    assertThrows(Exception.class, () -> categoryController.getallCategories());
    verify(categoryService, times(1)).findAllCategories();
  }

  // Test get category by id - Success (Category Found)
  @Test
  public void testGetCategoryById_success() throws AppObjectNotFoundException {
    Long id = 1L;
    Optional<Category> category = Optional.of(new Category());
    when(categoryService.findCategoryById(id)).thenReturn(category);

    ResponseEntity<Optional<Category>> response = categoryController.getCategoryById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(categoryService, times(1)).findCategoryById(id);
  }

  // Test get category by id - Not Found
  @Test
  public void testGetCategoryById_notFound() throws AppObjectNotFoundException {
    Long id = 1L;
    when(categoryService.findCategoryById(id)).thenReturn(Optional.empty());

    assertThrows(AppObjectNotFoundException.class, () -> categoryController.getCategoryById(id));
    verify(categoryService, times(1)).findCategoryById(id);
  }

  // Test get category by id - Service throws exception
  @Test
  public void testGetCategoryById_serviceThrowsException() throws AppObjectNotFoundException {
    Long id = 1L;
    when(categoryService.findCategoryById(id)).thenThrow(new RuntimeException("Test Exception"));

    assertThrows(RuntimeException.class, () -> categoryController.getCategoryById(id));
    verify(categoryService, times(1)).findCategoryById(id);
  }

  // Test update category - Success
  @Test
  public void testUpdateCategory_success() throws AppObjectAlreadyExistsException, AppObjectNotFoundException, AppObjectInvalidArgumentException {
    CategoryUpdateDTO updateDTO = new CategoryUpdateDTO();
    CategoryReadOnlyDTO updatedCategory = new CategoryReadOnlyDTO();
    when(categoryService.updateCategory(updateDTO)).thenReturn(updatedCategory);

    ResponseEntity<CategoryReadOnlyDTO> response = categoryController.updateCategory(updateDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(updatedCategory, response.getBody());
    verify(categoryService, times(1)).updateCategory(updateDTO);
  }
  // Test update category - Service throws AppObjectAlreadyExistsException
  @Test
  public void testUpdateCategory_throwsAppObjectAlreadyExistsException() throws AppObjectAlreadyExistsException, AppObjectNotFoundException, AppObjectInvalidArgumentException {
    CategoryUpdateDTO updateDTO = new CategoryUpdateDTO();
    when(categoryService.updateCategory(updateDTO)).thenThrow(new AppObjectAlreadyExistsException("Category", "Category already exists"));

    assertThrows(AppObjectAlreadyExistsException.class, () -> categoryController.updateCategory(updateDTO));
    verify(categoryService, times(1)).updateCategory(updateDTO);
  }

  // Test update category - Service throws AppObjectNotFoundException
  @Test
  public void testUpdateCategory_throwsAppObjectNotFoundException() throws AppObjectAlreadyExistsException, AppObjectNotFoundException, AppObjectInvalidArgumentException {
    CategoryUpdateDTO updateDTO = new CategoryUpdateDTO();
    when(categoryService.updateCategory(updateDTO)).thenThrow(new AppObjectNotFoundException("Category", "Category not found"));

    assertThrows(AppObjectNotFoundException.class, () -> categoryController.updateCategory(updateDTO));
    verify(categoryService, times(1)).updateCategory(updateDTO);
  }

  // Test update category - Service throws AppObjectInvalidArgumentException
  @Test
  public void testUpdateCategory_throwsAppObjectInvalidArgumentException() throws AppObjectAlreadyExistsException, AppObjectNotFoundException, AppObjectInvalidArgumentException {
    CategoryUpdateDTO updateDTO = new CategoryUpdateDTO();
    when(categoryService.updateCategory(updateDTO)).thenThrow(new AppObjectInvalidArgumentException("Category", "Invalid category data"));

    assertThrows(AppObjectInvalidArgumentException.class, () -> categoryController.updateCategory(updateDTO));
    verify(categoryService, times(1)).updateCategory(updateDTO);
  }

  // Test add category - Success
  @Test
  public void testAddCategory_success() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException {
    CategoryInsertDTO insertDTO = new CategoryInsertDTO();
    CategoryReadOnlyDTO createdCategory = new CategoryReadOnlyDTO();
    when(categoryService.saveCategory(insertDTO)).thenReturn(createdCategory);

    ResponseEntity<CategoryReadOnlyDTO> response = categoryController.addCategory(insertDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(createdCategory, response.getBody());
    verify(categoryService, times(1)).saveCategory(insertDTO);
  }

  // Test add category - Service throws AppObjectAlreadyExistsException
  @Test
  public void testAddCategory_throwsAppObjectAlreadyExistsException() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException {
    CategoryInsertDTO insertDTO = new CategoryInsertDTO();
    when(categoryService.saveCategory(insertDTO)).thenThrow(new AppObjectAlreadyExistsException("Category", "Category already exists"));

    assertThrows(AppObjectAlreadyExistsException.class, () -> categoryController.addCategory(insertDTO));
    verify(categoryService, times(1)).saveCategory(insertDTO);
  }

  // Test add category - Service throws AppObjectInvalidArgumentException
  @Test
  public void testAddCategory_throwsAppObjectInvalidArgumentException() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException {
    CategoryInsertDTO insertDTO = new CategoryInsertDTO();
    when(categoryService.saveCategory(insertDTO)).thenThrow(new AppObjectInvalidArgumentException("Category", "Invalid category data"));

    assertThrows(AppObjectInvalidArgumentException.class, () -> categoryController.addCategory(insertDTO));
    verify(categoryService, times(1)).saveCategory(insertDTO);
  }

  // Test delete category - Success
  @Test
  public void testDeleteCategory_success() throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
    Long id = 1L;
    CategoryReadOnlyDTO deletedCategory = new CategoryReadOnlyDTO();
    when(categoryService.deleteCategory(id)).thenReturn(deletedCategory);

    ResponseEntity<CategoryReadOnlyDTO> response = categoryController.deleteCategory(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(deletedCategory, response.getBody());
    verify(categoryService, times(1)).deleteCategory(id);
  }

  // Test delete category - Service throws AppObjectNotFoundException
  @Test
  public void testDeleteCategory_throwsAppObjectNotFoundException() throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
    Long id = 1L;
    when(categoryService.deleteCategory(id)).thenThrow(new AppObjectNotFoundException("Category", "Category not found"));

    assertThrows(AppObjectNotFoundException.class, () -> categoryController.deleteCategory(id));
    verify(categoryService, times(1)).deleteCategory(id);
  }

  // Test delete category - Service throws AppObjectInvalidArgumentException
  @Test
  public void testDeleteCategory_throwsAppObjectInvalidArgumentException() throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
    Long id = 1L;
    when(categoryService.deleteCategory(id)).thenThrow(new AppObjectInvalidArgumentException("Category", "Cannot delete 'Other' category"));

    assertThrows(AppObjectInvalidArgumentException.class, () -> categoryController.deleteCategory(id));
    verify(categoryService, times(1)).deleteCategory(id);
  }
}
