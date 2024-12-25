package gr.nifitsas.dealsapp.service.specifications;


import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.model.static_data.Store;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    private ProductSpecification(){

    }
  /**
   * Creates a Specification that filters products based on their category.
   *
   * @param categoryId The ID of the category to filter by.
   *                   A categoryId of -1 indicates no filtering by category.
   * @return A Specification for filtering products by category.
   */
    public static Specification<Product>productCategoryIs(Long categoryId){
        return ((root, query, criteriaBuilder) ->{

          if (categoryId == -1) {
            return criteriaBuilder.conjunction();
          }

            Join<Product, Category> product = root.join("category");
            return criteriaBuilder.equal(product.get("id"), categoryId);

        });
    }
  /**
   * Creates a Specification that filters products based on their store.
   *
   * @param storeId The ID of the store to filter by.
   *                A storeId of -1 indicates no filtering by store.
   * @return A Specification for filtering products by store.
   */
  public static Specification<Product>productStoreIs(Long storeId){
    return ((root, query, criteriaBuilder) ->{

      if (storeId == -1) {
        return criteriaBuilder.conjunction();
      }

      Join<Product, Store> product = root.join("store");
      return criteriaBuilder.equal(product.get("id"), storeId);

    });
  }

  /**
   * Creates a Specification that filters products based on a case-insensitive like search on a specified field.
   *
   * @param field The field name to search on.
   * @param value The search value to match.
   *              An empty or null value will match all products.
   * @return A Specification for filtering products by a like search on a field.
   */
    public static Specification<Product> productTitleIsLike(String field, String value){
        return ((root, query, criteriaBuilder) -> {
            if (value == null || value.trim().isEmpty()){
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" +
                    value.toLowerCase() + "%");
        });
    }
}
