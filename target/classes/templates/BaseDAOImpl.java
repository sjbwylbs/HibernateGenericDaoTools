package com.ijqg.pos.dao;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

/**
 * Extension of GenericDAOImpl that is configured for Autowiring with Spring or J2EE.
 */
@Repository
public class BaseDAOImpl<T, ID extends Serializable> extends GenericDAOImpl<T, ID> {

        @Override
        @Resource
        public void setSessionFactory(SessionFactory sessionFactory) {
                super.setSessionFactory(sessionFactory);
        }

}
