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

    @Nullable
    private String name;

    @Nullable
    private Category category;



}
