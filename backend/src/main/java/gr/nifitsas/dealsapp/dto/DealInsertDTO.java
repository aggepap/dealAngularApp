package gr.nifitsas.dealsapp.dto;

import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Store;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class DealInsertDTO {

  @NotNull(message = "You have to select a products")
  private Product product;
  @NotNull(message = "You have to select a Store")
  private Store store;

  private String coupon;
  @NotNull(message = "Price cannot be empty")
  private double price;
  @NotNull(message = "Deal URL cannot be empty")
  private String URL;

}
