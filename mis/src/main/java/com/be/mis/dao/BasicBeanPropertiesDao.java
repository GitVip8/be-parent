package com.be.mis.dao;

import com.be.mis.entity.BasicBean;
import com.be.mis.entity.BasicBeanProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BasicBeanPropertiesDao extends JpaRepository<BasicBeanProperties, Long> {


    @Query(value = "select a from BasicBeanProperties a where a.basicBean = ?1")
    List<BasicBeanProperties> findAllByBasicBean(BasicBean basicBean);
}
