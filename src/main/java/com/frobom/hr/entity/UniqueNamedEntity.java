package com.frobom.hr.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class UniqueNamedEntity extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
