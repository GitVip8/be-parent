package com.be.common.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "t_group")
public class Group extends BaseEntity {

    private Long id;

    private String sign;

    private String name;

    private int category;

    private String description;

    private Group parent;


    @JsonIgnore
    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private List<User> users;


}
