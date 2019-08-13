package com.be.mis.entity;


import com.be.common.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_basic_bean_properties")
public class BasicBeanProperties extends BaseEntity {

    private Long id;

    @ManyToOne
    private BasicBean basicBean;

    private String name;

    private String description;

    private String maxLength;

    private String dataFormat;

    private int type;

    private boolean required;

    private boolean unique;

    private boolean writeable;

    private int orderNum;

    private int orderType;

    private int easyQuery;

    private Long dicId;

    private String quoteType;

    private BasicBeanProperties quoteProperty;
}
