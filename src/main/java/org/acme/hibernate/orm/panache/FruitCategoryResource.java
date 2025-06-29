package org.acme.hibernate.orm.panache;

import java.util.List;

import org.jboss.resteasy.reactive.RestResponse;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/categories")
@ApplicationScoped
public class FruitCategoryResource {

    @GET
    public Uni<List<FruitCategory>> get() {
        return FruitCategory.listAll(Sort.by("name"));
    }

    @GET
    @Path("/{id}")
    public Uni<FruitCategory> getSingle(Long id) {
        return FruitCategory.findById(id);
    }

    @POST
    public Uni<RestResponse<FruitCategory>> create(FruitCategory fruitCategory) {
        return Panache.withTransaction(fruitCategory::persist).replaceWith(RestResponse.status(RestResponse.Status.CREATED, fruitCategory));
    }

    @GET
    @Path("/{id}/fruits")
    public Uni<List<Fruit>> getFruitsByCategory(Long id) {
        return FruitCategory.<FruitCategory>find("SELECT c FROM FruitCategory c JOIN FETCH c.fruits where c.id = ?1", id)
        .firstResult()
        .onItem().ifNull().failWith(() -> new NotFoundException("Category not found"))
        .map(category -> List.copyOf(category.fruits));
    }
}