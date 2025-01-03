package gr.nifitsas.dealsapp.core.mapper;

import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreInsertDTO;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.UserDTOs.UserInsertDTO;
import gr.nifitsas.dealsapp.dto.UserDTOs.UserReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryInsertDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductInsertDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductReadOnlyDTO;
import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.User;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.model.static_data.Store;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

//User Mappers
//***************************

  public User mapToUserEntity(UserInsertDTO userInsertDTO) {
    return new User(null, null, userInsertDTO.getUsername(), userInsertDTO.getPassword(),true, null);
  }
  public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
    return new UserReadOnlyDTO(user.getUuid(), user.getUsername(), user.getRole());
  }

//Product Mappers
//***************************
  public Product mapToProductEntity(ProductInsertDTO productInsertDTO){
    return new Product(null,productInsertDTO.getName(), productInsertDTO.getDescription(),productInsertDTO.getCoupon(), productInsertDTO.getUrl(), productInsertDTO.getPrice(),
      null,null, null, null);
  }
  public ProductReadOnlyDTO mapToProductReadOnlyDTO(Product product){
    return new ProductReadOnlyDTO(product.getId(),  product.getName(), product.getDescription(),
      product.getCategory(),product.getStore(), product.getCoupon(), product.getURL(), product.getPrice(), product.getImage(), product.getLowestPrice(),product.getUpdatedAt());
  }

//  Category Mappers
//***************************
  public Category mapToCategoryEntity(CategoryInsertDTO categoryInsertDTO){
    Category category = new Category();
    category.setName(categoryInsertDTO.getName());
    category.setIcon(categoryInsertDTO.getIcon());
    return category;
  }
  public CategoryReadOnlyDTO mapToCategoryReadOnlyDTO(Category category){
    return new CategoryReadOnlyDTO(category.getId(), category.getName(), category.getIcon());
  }

  //  Store Mappers
//***************************
  public Store mapToStoreEntity(StoreInsertDTO storeInsertDTO){
    return new Store(null, storeInsertDTO.getName(),storeInsertDTO.getSiteURL(),null, null);
  }
  public StoreReadOnlyDTO mapToStoreReadOnlyDTO(Store store){
    return new StoreReadOnlyDTO(store.getId(), store.getName(), store.getSiteURL(), store.getLogo(), store.getAllStoreProducts());
  }


}
