package com.be.mis.entity;


import com.be.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Properties;

@Entity
@Table(name = "t_basic_bean")
public class BasicBean extends BaseEntity {

    private Long id;

    private String name;

    private String description;


    @ManyToOne
    @JoinColumn(name = "basic_module_id")
    private BasicModule basicModule;

    private int state;

    private int type;

    private int hasHistory;

    @OneToMany(mappedBy = "basicBean")
    private List<BasicBeanProperties> properties;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BasicModule getBasicModule() {
        return basicModule;
    }

    public void setBasicModule(BasicModule basicModule) {
        this.basicModule = basicModule;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHasHistory() {
        return hasHistory;
    }

    public void setHasHistory(int hasHistory) {
        this.hasHistory = hasHistory;
    }

    public List<BasicBeanProperties> getProperties() {
        return properties;
    }

    public void setProperties(List<BasicBeanProperties> properties) {
        this.properties = properties;
    }
}
