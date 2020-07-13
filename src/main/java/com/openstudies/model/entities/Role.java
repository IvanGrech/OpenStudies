package com.openstudies.model.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@Entity(name = "ROLE")
@Table(name = "ROLE")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    public Role() {

    }

    public Role(Long id) {
        this.id = id;

    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long newId) throws NullPointerException {
        if (newId == null) {
            throw new NullPointerException();
        }
        this.id = newId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) throws NullPointerException {
        if (newName == null) {
            throw new NullPointerException();
        }
        this.name = newName;
    }

}
