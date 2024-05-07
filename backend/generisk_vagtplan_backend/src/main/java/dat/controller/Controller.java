package dat.controller;

import dat.dao.DAO;
import dat.dto.DTO;
import dat.exception.ApiException;
import dat.model.Entity;
import io.javalin.http.Context;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This Controller class is a generic class that can be used to create a controller for any entity,
 * including the basic CRUD operations
 *
 * @param <EntityType> The type of the entity
 * @param <DTOType>    The type of the DTO
 */
public abstract class Controller<EntityType extends Entity<DTOType>, DTOType extends DTO<EntityType>> {

    protected final DAO<EntityType> dao;

    public Controller(final DAO<EntityType> dao) {
        this.dao = dao;
    }

    /**
     * Get all entities
     */
    public void getAll(Context ctx) {
        ctx.status(200);
        ctx.json(createFromEntities(this.dao.readAll()));
    }

    /**
     * Get an entity by its id
     *
     * @throws ApiException if the id is invalid
     */
    public void getById(Context ctx) throws ApiException {
        final EntityType entity = this.validateId(ctx);
        ctx.status(200);
        ctx.json(entity.toDTO());
    }

    /**
     * Create an entity
     */
    public void post(Context ctx) {
        final EntityType jsonRequest = ctx.bodyAsClass(this.dao.getClazz());
        final EntityType entity = this.dao.create(jsonRequest);
        ctx.status(201);
        ctx.json(entity.toDTO());
    }

    /**
     * Update an entity
     *
     * @throws ApiException if the id is invalid
     */
    public void put(Context ctx) throws ApiException {
        this.validateId(ctx); // Will throw ApiException if id is invalid
        final String id = ctx.pathParam("id");
        final EntityType jsonRequest = ctx.bodyAsClass(this.dao.getClazz());
        jsonRequest.setId(id);
        final EntityType entity = this.dao.update(jsonRequest);
        ctx.status(200);
        ctx.json(entity.toDTO());
    }

    /**
     * Delete an entity
     *
     * @throws ApiException if the id is invalid
     */
    public void delete(Context ctx) throws ApiException {
        final EntityType entity = this.validateId(ctx);
        this.dao.delete(entity);
        ctx.status(204);
    }

    /**
     * Validate the id in the path param and return the entity if it exists
     *
     * @return The entity
     * @throws ApiException if the id is invalid
     */
    protected EntityType validateId(final Context ctx) throws ApiException {
        final String id = ctx.pathParam("id");
        return this.dao.readById(id)
                .orElseThrow(() -> new ApiException(404, "No entity found with id: " + id));
    }

    /**
     * Create a list of DTOs from a list of entities
     *
     * @param entities The entities
     * @return The DTOs
     */
    protected List<DTOType> createFromEntities(final List<EntityType> entities) {
        return entities.stream()
                .map(EntityType::toDTO)
                .collect(Collectors.toList());
    }
}