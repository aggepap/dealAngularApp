package gr.nifitsas.dealsapp.model;

import gr.nifitsas.dealsapp.model.static_data.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="deals")
public class Deal extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String coupon;
    private String URL;
    private Double price;
    private String imageURL;
    private Double lowestPrice;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store;
}
