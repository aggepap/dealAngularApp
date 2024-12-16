package gr.nifitsas.dealsapp.model.static_data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import gr.nifitsas.dealsapp.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="stores")
public class Store{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String siteURL;
    private String logoURL;


    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
    @JsonManagedReference
      private Set<Product> products = new HashSet<>();

    public Set<Product> getAllStoreProducts() {
        if(products == null) products = new HashSet<>();
        return Collections.unmodifiableSet(products);
    }
    public void addStoreProduct(Product product){
        if(products == null) products = new HashSet<>();
      products.add(product);
        product.setStore(this);
    }
    public void removeStoreDeal(Product product){
      products.remove(product);
        product.setStore(null);
    }

}
