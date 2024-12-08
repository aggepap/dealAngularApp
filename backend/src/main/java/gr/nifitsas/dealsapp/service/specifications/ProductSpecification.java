package gr.nifitsas.dealsapp.service.specifications;


import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Category;
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


    public static Specification<Product> productTitleIsLike(String field, String value){
        return ((root, query, criteriaBuilder) -> {
            if (value == null || value.trim().isEmpty()){
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
//            return criteriaBuilder.like(root.get(field), "%" + value + "%");
            return criteriaBuilder.like(criteriaBuilder.upper(root.get(field)), "%" +
                    value.toUpperCase() + "%");
        });
    }
}
