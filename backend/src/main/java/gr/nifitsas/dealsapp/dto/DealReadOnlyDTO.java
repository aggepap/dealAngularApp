package gr.nifitsas.dealsapp.dto;

import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealReadOnlyDTO {
  private Long id;
  private Product product;
  private Store store;
  private String coupon;
  private double price;
  private String URL;
}
