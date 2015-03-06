package com.github.alxwhtmr.session;

import com.github.alxwhtmr.entity.Muscle;
import com.github.alxwhtmr.entity.MuscleGroup;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * The {@code MuscleGroupFacade} represents session bean
 * for performing data manipulation with muscle_group
 * entity in database
 */
@Stateless
public class MuscleGroupFacade extends AbstractFacade<MuscleGroup> {
    @PersistenceContext(unitName = "MusclesWebPU")
    private EntityManager em;

    /**
     * Returns {@code EntityManager} for the class, which is 
     * supposed to interact with persistent context
     * @return {@code EntityManager} instance 
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * A constructor calls its superclass constructor
     */
    public MuscleGroupFacade() {
        super(MuscleGroup.class);
    }
    
    /**
     * Finds a record in a table {@code muscle_group} in database by its name
     * @param name a {@code String} that contains name of the desired record
     * @return {@code Muscle} object which represents a database record
     */
    @PersistenceContext
    public MuscleGroup findByName(String name) {
        TypedQuery<MuscleGroup> query =
                em.createNamedQuery("MuscleGroup.findByNameEn", MuscleGroup.class)
                .setParameter("nameEn", name);
        MuscleGroup mg = query.getSingleResult();
        return mg;        
    }
}
