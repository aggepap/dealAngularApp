package gr.nifitsas.dealsapp.core.mapper;

import gr.nifitsas.dealsapp.dto.*;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryInsertDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.model.Deal;
import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.model.static_data.Store;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

//  Deal Mappers
//***************************
  public Deal mapToDealEntity(DealInsertDTO dealInsertDTO){
   return new Deal(null, dealInsertDTO.getCoupon(),dealInsertDTO.getURL(),
     dealInsertDTO.getPrice(),dealInsertDTO.getProduct(),dealInsertDTO.getStore());
  }
  public DealReadOnlyDTO mapToDealReadOnlyDTO(Deal deal){
    return new DealReadOnlyDTO(deal.getId(), deal.getProduct(),deal.getStore(),
      deal.getCoupon(),deal.getPrice(),deal.getURL());
  }
//Product Mappers
//***************************
  public Product mapToProductEntity(ProductInsertDTO productInsertDTO){
    return new Product(null,productInsertDTO.getName(), productInsertDTO.getDescription(),null,null,
       null,null);
  }
  public ProductReadOnlyDTO mapToProductReadOnlyDTO(Product product){
    return new ProductReadOnlyDTO(product.getId(),  product.getName(), product.getDescription(),
      product.getCategory(), product.getImage(), product.getLowestPrice());
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
    return new Store(null, storeInsertDTO.getName(),storeInsertDTO.getSiteURL(), storeInsertDTO.getLogoURL(), null);
  }
  public StoreReadOnlyDTO mapToStoreReadOnlyDTO(Store store){
    return new StoreReadOnlyDTO(store.getId(), store.getName(), store.getSiteURL(), store.getLogoURL(),store.getAllStoreDeals());
  }

}
