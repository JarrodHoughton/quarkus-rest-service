package org.acme.hibernate.orm.panache;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;


@Entity
@Cacheable
public class Fruit extends PanacheEntity {

    @Column(length = 40, unique = true)
    public String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "jt_fruit_fruitcategory",
        joinColumns = @JoinColumn(name = "fruit_id"),
        inverseJoinColumns = @JoinColumn(name = "fruitcategory_id")
    )
    @JsonIgnore
    public Set<FruitCategory> categories = new HashSet<>();
}