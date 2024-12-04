package gr.nifitsas.dealsapp.model.static_data;

import gr.nifitsas.dealsapp.model.Deal;
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
    private Set<Deal> deals = new HashSet<>();

    public Set<Deal> getAllStoreDeals(){
        if(deals == null) deals = new HashSet<>();
        return Collections.unmodifiableSet(deals);
    }
    public void addStoreDeal(Deal deal){
        if(deals == null) deals = new HashSet<>();
        deals.add(deal);
        deal.setStore(this);
    }
    public void removeStoreDeal(Deal deal){
        deals.remove(deal);
        deal.setStore(null);
    }

}
