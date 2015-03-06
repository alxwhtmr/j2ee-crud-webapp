package com.github.alxwhtmr.session;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * The abstract class {@code AbstractFacade} is supposed
 * to provide common methods for concrete classes.
 * These methods manipulates data in database
 * This class is a basement for session beans
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    /**
     * Creates a reference to the specified entity class
     * @param entityClass {@code Muscle}/{@code MuscleGroup}
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Returns entity manager of the object, which performs
     * all the real manipulations with database
     * @return {@code EntityManager} object
     */
    protected abstract EntityManager getEntityManager();
    
    /**
     * Create a new instance of the object and adds
     * the analogous record to the database
     * @param entity new object to be created
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Edit object in the database
     * @param entity object to be edited
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Removes object from the database
     * @param entity object to be removed
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Find the particular entity object based on its id
     * @param id the id of the object
     * @return object instance if it was found in database, null in the opposite case
     */
    public T find(Object id) {
        getEntityManager().flush();
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Find all instances of the particular entity objects
     * Creates the {@code List} of found objects
     * @return {@code List} of a particular entities
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
}
