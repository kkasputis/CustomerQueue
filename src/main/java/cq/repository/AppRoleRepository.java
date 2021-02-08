package cq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cq.security.entity.AppRole;
@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
		
}
