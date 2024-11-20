package gr.nifitsas.dealsapp.controller;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.CategoryDeleteDTO;
import gr.nifitsas.dealsapp.dto.CategoryInsertDTO;
import gr.nifitsas.dealsapp.dto.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
  private final CategoryService categoryService;


  @GetMapping("")
     public ResponseEntity<List<Category>> getallCategories(){
     List<Category> categoryList = categoryService.findAllCategories();
     try {
       return new ResponseEntity<>(categoryList, HttpStatus.OK);
     }catch (Exception e){
       LOGGER.error("ERROR: Could not get Categories.", e);
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
   public ResponseEntity<CategoryReadOnlyDTO> deleteCategory (@RequestBody CategoryDeleteDTO categoryDeleteDTO) throws AppObjectNotFoundException, AppObjectInvalidArgumentException {

    try {
      Long categoryId = categoryDeleteDTO.getCategoryId();
      CategoryReadOnlyDTO deletedCategory = categoryService.deleteCategory(categoryId);
      return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    } catch (AppObjectInvalidArgumentException | AppObjectNotFoundException e) {
      LOGGER.error("ERROR: Could not delete category.", e);
      throw e;
    }
   }


}
