package gr.nifitsas.dealsapp.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import gr.nifitsas.dealsapp.model.static_data.Category;
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
public class Product extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;

    private Double lowestPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Attachment image;

    @OneToMany(mappedBy = "product")
    private Set<Deal> deals = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="category_id")
    @JsonBackReference
    private Category category;



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
