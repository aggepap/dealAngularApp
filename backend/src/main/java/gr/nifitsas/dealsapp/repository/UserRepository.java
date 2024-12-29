package gr.nifitsas.dealsapp.repository;

import gr.nifitsas.dealsapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface  UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

  Optional<User> findByUuid(String uuid);
  Optional<User> findById(Long id);
  Optional<User> findByUsername(String username);
}
