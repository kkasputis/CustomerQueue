package cq.security.dao;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cq.repository.AppUserRepository;
import cq.security.entity.AppUser;
 
@Repository
@Transactional
public class AppUserDAO extends JdbcDaoSupport {
 
    @Autowired
    public AppUserDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
    @Autowired
    AppUserRepository specialistRepository;
 
    public AppUser findUserAccount(String userName) {

        try {
            AppUser userInfo = specialistRepository.findByUserName(userName).orElse(null);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
 
}
