package com.wmdigital.barbearia.framework;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractEntityService <Entity> {

    private final Class<Entity> entityClass;
    private final String entityAlias;

    protected AbstractEntityService(Class<Entity> entityClass, String entityAlias) {
        this.entityClass = entityClass;
        this.entityAlias = entityAlias;
    }

    protected abstract JpaRepository<Entity, UUID> getDao();

    public List<Entity> findAll() {
        return getDao().findAll();
    }

    public Entity findOne(UUID id) {
        return getDao().findById(id).orElse(null);
    }

    public Entity save(Entity entity) {
        validateSave(entity);
        return getDao().saveAndFlush(entity);
    }

    public Entity edit(Entity entity) {
        validateEdit(entity);
        return getDao().saveAndFlush(entity);
    }

    public void delete(Entity entity) {
        //validateDelete(entity);
        getDao().delete(entity);
    }

    public void deleteInBatch(List<Entity> entities) {
        getDao().deleteInBatch(entities);
    }

    public long count() {
        return getDao().count();
    }

    protected abstract void validateSave(Entity entity);

    protected abstract void validateEdit(Entity entity);

    protected abstract void validateDelete(UUID id);

    protected abstract String getIdEntity(Entity entity);

    public abstract List<Entity> findAllNew();
} 	
