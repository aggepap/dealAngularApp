package gr.nifitsas.dealsapp.core.filters;

import gr.nifitsas.dealsapp.model.static_data.Category;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductFilters extends GenericFilters {


    private String name;
    private Long categoryId;
    private int pageSize;
    private int page;
    private Long storeId;



}
