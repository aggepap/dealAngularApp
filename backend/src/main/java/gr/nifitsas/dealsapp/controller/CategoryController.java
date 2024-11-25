package gr.nifitsas.dealsapp.controller;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryInsertDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryUpdateDTO;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
  private final CategoryService categoryService;


  @GetMapping("")
     public ResponseEntity<List<CategoryReadOnlyDTO>> getallCategories(){
     List<CategoryReadOnlyDTO> categoryList = categoryService.findAllCategories();
     try {
       return new ResponseEntity<>(categoryList, HttpStatus.OK);
     }catch (Exception e){
       LOGGER.error("ERROR: Could not get Categories.", e);
       throw e;
     }

   }
  @GetMapping("/find")
  public ResponseEntity<Optional<Category>> getCategoryById(@RequestParam("id") Long id){
    Optional<Category> category = categoryService.findCategoryById(id);
    try {
      return new ResponseEntity<>(category, HttpStatus.OK);
    }catch (Exception e){
      LOGGER.error("ERROR: Could not get category with id " + id + ".", e);
      throw e;
    }

  }
  @PatchMapping("/update")
  public ResponseEntity<CategoryReadOnlyDTO> updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO) throws AppObjectAlreadyExists, AppObjectNotFoundException {
    try {
      CategoryReadOnlyDTO category = categoryService.updateCategory(categoryUpdateDTO);
      return new ResponseEntity<>(category, HttpStatus.CREATED);
    } catch (AppObjectAlreadyExists | AppObjectNotFoundException e) {
      LOGGER.error("ERROR: Could not update category." + categoryUpdateDTO.getName(), e);
      throw e;
    }

  }
   @PostMapping("/add")
   public ResponseEntity<CategoryReadOnlyDTO> addCategory(@RequestBody CategoryInsertDTO categoryInsertDTO) throws AppObjectInvalidArgumentException, AppObjectAlreadyExists {
     try {
        CategoryReadOnlyDTO category = categoryService.saveCategory(categoryInsertDTO);
       return new ResponseEntity<>(category, HttpStatus.CREATED);
     } catch (AppObjectAlreadyExists | AppObjectInvalidArgumentException e) {
       LOGGER.error("ERROR: Could not add category.", e);
       throw e;
     }

   }

  @DeleteMapping("/remove")
   public ResponseEntity<CategoryReadOnlyDTO> deleteCategory (@RequestParam("id") Long id) throws AppObjectNotFoundException, AppObjectInvalidArgumentException {

    try {
      CategoryReadOnlyDTO deletedCategory = categoryService.deleteCategory(id);
      return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    } catch (AppObjectInvalidArgumentException | AppObjectNotFoundException e) {
      LOGGER.error("ERROR: Could not delete category.", e);
      throw e;
    }
   }


}
