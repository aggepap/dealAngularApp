package gr.nifitsas.dealsapp.model.static_data;

import gr.nifitsas.dealsapp.model.AbstractEntity;
import gr.nifitsas.dealsapp.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="categories")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  private String icon;

  @OneToMany(mappedBy = "category")
  private Set<Product> products = new HashSet<>();

  public Set<Product> getAllCategoryProducts() {
    if (products == null) products = new HashSet<>();
    return Collections.unmodifiableSet(products);
  }
  private void addProductToCategory(Product product){
    if(products == null) products = new HashSet<>();
    products.add(product);
  }
  private void removeProductFromCategory(Product product){
    products.remove(product);
  }
}
