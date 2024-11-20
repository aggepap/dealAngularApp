package gr.nifitsas.dealsapp.repository;

import gr.nifitsas.dealsapp.model.static_data.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

  Optional<Category> findByName(String name);
}
