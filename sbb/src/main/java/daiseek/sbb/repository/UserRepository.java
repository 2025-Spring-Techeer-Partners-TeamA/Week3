package daiseek.sbb.repository;

import daiseek.sbb.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {


}
