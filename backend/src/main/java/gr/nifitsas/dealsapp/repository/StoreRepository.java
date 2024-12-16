package gr.nifitsas.dealsapp.repository;

import gr.nifitsas.dealsapp.dto.StoreReadOnlyDTO;
import gr.nifitsas.dealsapp.model.static_data.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {



  Optional<Store> findByName(String storeName);

  Optional<Store> findStoreByIdIs(Long id);
  Optional<StoreReadOnlyDTO> findStoreById(Long id);

}
