package com.be.mis.service;

import com.be.mis.dao.BasicBeanDao;
import com.be.mis.dao.BasicBeanPropertiesDao;
import com.be.mis.entity.BasicBean;
import com.be.mis.entity.BasicBeanProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public class IBasicBeanService implements BasicBeanService {

    @Autowired
    BasicBeanDao basicBeanDao;

    @Autowired
    BasicBeanPropertiesDao basicBeanPropertiesDao;

    @Override
    public BasicBean createBasicBean(BasicBean basicBean) {
        if (basicBean == null) return null;
        if (basicBean.getId() != null) basicBean.setId(null);
        return basicBeanDao.save(basicBean);
    }

    @Override
    @CachePut(value = "basic_bean", key = "#basicBeanId")
    public BasicBean renameBasicBean(Long basicBeanId, String rename) {
        BasicBean basicBean = find(basicBeanId);
        basicBean.setName(rename);
        return basicBeanDao.save(basicBean);
    }

    @Override
    public BasicBean changeModule(Long basicBeanId, Long moduleId) {
        return null;
    }

    @Override
    @CachePut(value = "basic_bean", key = "#basicBeanId")
    public BasicBean change2writeHistory(Long basicBeanId) {
        BasicBean basicBean = find(basicBeanId);
        basicBean.setHasHistory(1);
        return basicBeanDao.save(basicBean);
    }


    @Override
    public BasicBean saveBasicBeanProperties(Long basicBeanId, List<BasicBeanProperties> propertiesList) {
        return null;
    }

    @Override
    public boolean dropBasicBean(Long id) {
        return false;
    }

    @Override
    @Cacheable(value = "basic_bean", key = "#id")
    public BasicBean find(Long id) {
        return find(id, true);
    }

    private BasicBean find(Long id, boolean loadProperties) {
        BasicBean basicBean = basicBeanDao.findById(id).orElse(null);
        if (basicBean != null && loadProperties) basicBean.setProperties(findBasicBeanProperties(basicBean.getId()));
        return basicBean;
    }

    @Override
    public List<BasicBeanProperties> findBasicBeanProperties(Long basicBeanId) {
        BasicBean basicBean = new BasicBean();
        basicBean.setId(basicBeanId);
        return basicBeanPropertiesDao.findAllByBasicBean(basicBean);
    }

    @Override
    public List<BasicBean> findModuleBeans(Long moduleId) {
        return null;
    }
}
