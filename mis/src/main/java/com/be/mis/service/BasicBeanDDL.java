package com.be.mis.service;

import com.be.mis.entity.BasicBean;
import com.be.mis.entity.BasicBeanProperties;

import javax.persistence.EntityManager;
import java.util.List;

public class BasicBeanDDL {

    EntityManager _entityManager;

    BasicBean _basicBean;

    public BasicBeanDDL(EntityManager entityManager, BasicBean basicBean) {
        this._entityManager = entityManager;
        this._basicBean = basicBean;
    }

    public void createTable() {
        if (_basicBean == null || _basicBean.getProperties() == null) return;
        String sql = "create or replace table basic_entity_" + _basicBean.getId() + " (\n";
        List<BasicBeanProperties> propertiesList = _basicBean.getProperties();
        for (int i = 0; i < propertiesList.size(); i++) {
            BasicBeanProperties one = propertiesList.get(i);
            // sql += "\t" + one.getId();
        }
    }

    public void alterTable() {

    }

    public void createHistoryTable() {

    }
}
