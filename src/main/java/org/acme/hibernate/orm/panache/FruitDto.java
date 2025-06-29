package org.acme.hibernate.orm.panache;

import java.util.Set;

import io.quarkus.hibernate.reactive.panache.common.NestedProjectedClass;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@RegisterForReflection
public class FruitDto {
    public String name;
    public Set<FruitCategory> categories;

    public FruitDto (String name) {
        this.name = name;
    }
}