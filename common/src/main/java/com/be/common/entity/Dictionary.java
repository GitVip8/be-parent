package com.be.common.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_g_dictionary")
public class Dictionary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int category; // 下拉，数据字典，表选字典

    @OneToMany(mappedBy = "dictionary", fetch = FetchType.LAZY)
    private List<DictionaryItem> items;


}
