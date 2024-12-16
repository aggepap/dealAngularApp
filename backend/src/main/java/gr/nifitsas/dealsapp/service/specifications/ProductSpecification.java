package gr.nifitsas.dealsapp.service.specifications;


import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.model.static_data.Store;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    private ProductSpecification(){

    }

    public static Specification<Product>productCategoryIs(Long categoryId){
        return ((root, query, criteriaBuilder) ->{

          if (categoryId == null) {
            return criteriaBuilder.conjunction();
          }

            Join<Product, Category> product = root.join("category");
            return criteriaBuilder.equal(product.get("id"), categoryId);

        });
    }
  public static Specification<Product>productStoreIs(Long storeId){
    return ((root, query, criteriaBuilder) ->{

      if (storeId == null) {
        return criteriaBuilder.conjunction();
      }

      Join<Product, Store> product = root.join("store");
      return criteriaBuilder.equal(product.get("id"), storeId);

    });
  }


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
