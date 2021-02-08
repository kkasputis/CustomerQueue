package cq.security.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cq.repository.UserRoleRepository;
 
@Repository
@Transactional
public class AppRoleDAO extends JdbcDaoSupport {
 
    @Autowired
    public AppRoleDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
    @Autowired
    UserRoleRepository userRoleRepository;
 
    public List<String> getRoleNames(Long userId) {
        try {
        List<String> roles = userRoleRepository.findUserRoles(userId);
        return roles;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
     
}