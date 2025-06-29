package org.acme.hibernate.orm.panache;

import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

@Entity
@Cacheable
public class FruitCategory extends PanacheEntity {

	@Column(length = 40, unique = true)
	public String name;

	@Column(length = 512)
	public String description;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "categories")
	@JsonIgnore
	public Set<Fruit> fruits = new HashSet<>();
}