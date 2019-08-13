package com.be.mis.entity;

import com.be.common.entity.BaseEntity;

import javax.persistence.*;

@Table(name = "t_basic_system")
@Entity
public class BasicSystem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String sign;

    private String name;

    private String description;

}
