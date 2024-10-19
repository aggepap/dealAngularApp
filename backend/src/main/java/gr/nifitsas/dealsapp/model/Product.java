package gr.nifitsas.dealsapp.model;

import gr.nifitsas.dealsapp.core.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String imageURL;
    private Double lowestPrice;

    @OneToMany(mappedBy = "product")
    private Set<Deal> deals = new HashSet<>();

    public Set<Deal> getAllProductDeals(){
        if(deals == null) deals = new HashSet<>();
        return Collections.unmodifiableSet(deals);
    }
    public void addProductDeal(Deal deal){
        if(deals == null) deals = new HashSet<>();
        deals.add(deal);
        deal.setProduct(this);
    }
    public void removeProductDeal(Deal deal){
        deals.remove(deal);
        deal.setProduct(null);
    }
}
