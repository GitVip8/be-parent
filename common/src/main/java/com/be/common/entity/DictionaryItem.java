package com.be.common.entity;


import javax.persistence.*;

@Entity
@Table(name = "t_dictionary_items")
public class DictionaryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Dictionary dictionary;

    private String name;

    private String sign;

    private String value;


    @ManyToOne
    private DictionaryItem parent;
}
