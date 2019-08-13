package com.be.mis.entity;


import javax.persistence.*;

@Entity
@Table(name = "t_basic_module")
public class BasicModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "basic_system_id")
    private BasicSystem basicSystem;


}
