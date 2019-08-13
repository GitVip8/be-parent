package com.be.mis.service;

import com.be.mis.entity.BasicBean;
import com.be.mis.entity.BasicBeanProperties;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface BasicBeanService {


    BasicBean createBasicBean(BasicBean basicBean);

    BasicBean renameBasicBean(Long basicBeanId, String rename);

    BasicBean changeModule(Long basicBeanId, Long moduleId);

    BasicBean change2writeHistory(Long basicBeanId);

    BasicBean saveBasicBeanProperties(Long basicBeanId, List<BasicBeanProperties> propertiesList);

    boolean dropBasicBean(Long id);

    BasicBean find(Long id);

    List<BasicBeanProperties> findBasicBeanProperties(Long basicBeanId);

    List<BasicBean> findModuleBeans(Long moduleId);


}
