package org.acme.hibernate.orm.panache;

import java.util.List;
import java.util.Locale.Category;

import org.jboss.resteasy.reactive.RestResponse;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/fruits")
@ApplicationScoped
public class FruitResource {

    @GET
    public Uni<List<Fruit>> get() {
        return Fruit.listAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Fruit> getSingle(Long id) {
        return Fruit.findById(id);
    }

    @POST
    public Uni<RestResponse<Fruit>> create(Fruit fruit) {
        return Panache.withTransaction(fruit::persist).replaceWith(RestResponse.status(RestResponse.Status.CREATED, fruit));
    }

    @GET
    @Path("/{id}/categories")
    public Uni<List<FruitCategory>> getCategories(Long id) {
        return Fruit.<Fruit>find("SELECT f FROM Fruit f JOIN FETCH f.categories WHERE f.id = ?1", id)
        .firstResult()
        .onItem().ifNull().failWith(() -> new NotFoundException("Fruit not found"))
        .map(fruit -> List.copyOf(fruit.categories));
    }
}