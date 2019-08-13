package com.be.mis.dao;

import com.be.mis.entity.BasicBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BasicBeanDao extends JpaRepository<BasicBean, Long> {

    @Query(value = "select a from BasicBean a where a.state > 0 and a.id = ?1")
    BasicBean findBasicBean(Long id);
}
