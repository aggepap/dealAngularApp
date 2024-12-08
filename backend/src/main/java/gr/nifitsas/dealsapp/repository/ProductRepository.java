package gr.nifitsas.dealsapp.repository;


import gr.nifitsas.dealsapp.dto.ProductReadOnlyDTO;
import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  List<Product> findByCategory(Category category);
  Optional<Product> findByName(String name);
  List<ProductReadOnlyDTO> findByNameContaining(String name);
  List<ProductReadOnlyDTO> findByNameLike(String name);

}
