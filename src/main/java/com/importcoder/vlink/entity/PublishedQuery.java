package com.importcoder.vlink.entity;

import jakarta.persistence.*;
//import lombok.Data;

@Entity
@Table(name="published_query")
public class PublishedQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "custom_query_id")
    private CustomQuery customQuery;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomQuery getCustomQuery() {
        return customQuery;
    }

    public void setCustomQuery(CustomQuery customQuery) {
        this.customQuery = customQuery;
    }
}
