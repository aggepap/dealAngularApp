package gr.nifitsas.dealsapp.controller;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryInsertDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryUpdateDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductReadOnlyDTO;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
  private final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
  private final CategoryService categoryService;

  /**
   * Retrieves a list of all categories.
   *
   * @return A ResponseEntity containing a list of CategoryReadOnlyDTO objects and an HTTP status of OK (200).
   * @throws Exception If an unexpected error occurs during retrieval.
   */
  //OpenAPI Annotations
  @Operation(summary = "Retrieves a list of all categories" )
  @ApiResponses(value = {
    @ApiResponse(
        responseCode = "200", description = "categories are found and returned, or not and an empty list is returned",
        content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = CategoryReadOnlyDTO.class)) })})
  //Controller
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

  /**
   * Retrieves a category by its ID.
   *
   * @param id The ID of the category to retrieve.
   * @return A ResponseEntity containing an Optional of Category object, or an empty Optional if no category is found.
   * @throws Exception Any other unexpected error that may occur during retrieval.
   */
  //OpenAPI Annotations
  @Operation(summary = "Retrieves a category by its ID" )
  @ApiResponses(value = {
    @ApiResponse(
        responseCode = "200", description = "Category found and it's info",
        content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = Category.class))}),
    @ApiResponse(
        responseCode = "404",
        description = "Category not found",
        content = @Content) })
  //Controller
  @GetMapping("/find")
  public ResponseEntity<Optional<Category>> getCategoryById(@RequestParam("id") Long id) throws AppObjectNotFoundException {
    Optional<Category> category = categoryService.findCategoryById(id);
    if(category.isPresent()){
    try {
      return new ResponseEntity<>(category, HttpStatus.OK);
    }catch (Exception e){
      LOGGER.error("ERROR: Could not get category with id {}.", id, e);
      throw e;
    }
    }else {
      throw new AppObjectNotFoundException("Category", "Category with id: " + id + " not found");
    }
  }

  /**
   * Updates an existing category.
   *
   * @param categoryUpdateDTO The CategoryUpdateDTO object containing the updated category information.
   * @return A ResponseEntity containing the updated CategoryReadOnlyDTO object and an HTTP status of CREATED (201).
   * @throws AppObjectAlreadyExistsException If a category with the same name already exists.
   * @throws AppObjectNotFoundException If the category to be updated is not found.
   * @throws Exception Any other unexpected error that may occur during update.
   */
  //OpenAPI Annotations
  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = "Updates an existing category" )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "category Updated succesfully",
        content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = ProductReadOnlyDTO.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid Parameters",
        content = @Content),
    @ApiResponse(responseCode = "401", description = "Authentication Error",
        content = @Content),
    @ApiResponse(responseCode = "404", description = "category Not Found",
        content = @Content),
    @ApiResponse(responseCode = "409", description = "Category Already Exists",
        content = @Content)})
  //Controller
  @PatchMapping("/update")
  public ResponseEntity<CategoryReadOnlyDTO> updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO) throws AppObjectAlreadyExistsException, AppObjectNotFoundException, AppObjectInvalidArgumentException {
    try {
      CategoryReadOnlyDTO category = categoryService.updateCategory(categoryUpdateDTO);
      return new ResponseEntity<>(category, HttpStatus.OK);
    } catch (AppObjectAlreadyExistsException | AppObjectNotFoundException | AppObjectInvalidArgumentException e) {
      LOGGER.error("ERROR: Could not update category.{}", categoryUpdateDTO.getName(), e);
      throw e;
    }
  }

  /**
   * Creates a new category.
   *
   * @param categoryInsertDTO The CategoryInsertDTO object containing the new category information.
   * @return A ResponseEntity containing the created CategoryReadOnlyDTO object and an HTTP status of CREATED (201).
   * @throws AppObjectAlreadyExistsException If a category with the same name already exists.
   * @throws AppObjectInvalidArgumentException If the provided category data is invalid.
   * @throws Exception Any other unexpected error that may occur during creation.
   */
  //OpenAPI Annotations
  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = "Creates a new category" )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "category created succesfully",
        content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = ProductReadOnlyDTO.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid Parameters",
        content = @Content),
    @ApiResponse(responseCode = "401", description = "Authentication Error",
        content = @Content),
    @ApiResponse(responseCode = "409", description = "Category Already Exists",
        content = @Content)})
  //Controller
   @PostMapping("/add")
   public ResponseEntity<CategoryReadOnlyDTO> addCategory(@RequestBody CategoryInsertDTO categoryInsertDTO) throws AppObjectInvalidArgumentException,
    AppObjectAlreadyExistsException {
     try {
        CategoryReadOnlyDTO category = categoryService.saveCategory(categoryInsertDTO);
       return new ResponseEntity<>(category, HttpStatus.CREATED);
     } catch (AppObjectAlreadyExistsException | AppObjectInvalidArgumentException e) {
       LOGGER.error("ERROR: Could not add category.", e);
       throw e;
     }
   }


  /**
   * Deletes a category.
   *
   * @param id The ID of the category to delete.
   * @return A ResponseEntity containing the deleted CategoryReadOnlyDTO object and an HTTP status of OK (200).
   * @throws AppObjectNotFoundException If the category to be deleted is not found.
   * @throws AppObjectInvalidArgumentException If the category to be deleted is the "Other" category.
   * @throws Exception Any other unexpected error that may occur during deletion.
   */
  //OpenAPI Annotations

  @Operation(summary = "Deletes a category." )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "category deleted succesfully",
        content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = ProductReadOnlyDTO.class)) }),
    @ApiResponse(responseCode = "404", description = "Category Not found",
        content = @Content),
    @ApiResponse(responseCode = "400", description = "Invalid Parameters",
        content = @Content),
    @ApiResponse(responseCode = "401", description = "Authentication Error",
        content = @Content)})
  //Controller
  @DeleteMapping("/remove")
   public ResponseEntity<CategoryReadOnlyDTO> deleteCategory (@RequestParam("id") Long id) throws AppObjectNotFoundException,
    AppObjectInvalidArgumentException {
    System.out.println(id);

    try {
      CategoryReadOnlyDTO deletedCategory = categoryService.deleteCategory(id);
      return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    } catch (AppObjectInvalidArgumentException | AppObjectNotFoundException e) {
      LOGGER.error("ERROR: Could not delete category.", e);
      throw e;
    }
   }


}
