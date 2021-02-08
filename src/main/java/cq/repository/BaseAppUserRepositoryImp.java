package cq.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import cq.security.entity.AppUser;


@NoRepositoryBean
public interface BaseAppUserRepositoryImp<T extends AppUser> extends JpaRepository<T, Long>{
	public Optional<T> findByUserName(String userName);
	boolean existsByUserName(String userName);

}
