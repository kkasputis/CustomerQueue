package cq.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cq.security.entity.AppUser;
import cq.security.entity.UserRole;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	public Optional<List<UserRole>> findFirstByAppUser(AppUser appUser);
	
	@Query(value = "Select r.Role_Name from User_Role ur, App_Role r where ur.Role_Id = r.Role_Id and ur.User_Id = ?1 ", nativeQuery = true)
	public List<String> findUserRoles(long userId);
}
