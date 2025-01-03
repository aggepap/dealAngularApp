package gr.nifitsas.dealsapp.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.model.static_data.Store;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product extends AbstractEntity implements ImageAttachable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)

    private String name;
    @Column(length = 2000)
    private String description;
    private String coupon;
    private String URL;

    private double price;
    private Double lowestPrice;

  @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "image_id")
    private Attachment image;

  @Override
  public void setImage(Attachment attachment) {
    this.image = attachment;
  }

  @ManyToOne
  @JoinColumn(name="category_id")
  @JsonBackReference
  private Category category;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name="store_id")
  private Store store;




}
