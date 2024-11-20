package gr.nifitsas.dealsapp.repository;

import gr.nifitsas.dealsapp.model.Deal;
import gr.nifitsas.dealsapp.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface DealRepository extends JpaRepository<Deal, Long>, JpaSpecificationExecutor<Deal> {
  Optional<Deal> findByid(Long id);
  List<Deal> findByStoreId(Long storeid);
  List<Deal> findByProductId(Long productid);

  List<Deal> getDealsByProduct(Product product);


}
