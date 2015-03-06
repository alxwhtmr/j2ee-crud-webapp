package com.github.alxwhtmr.session;

import com.github.alxwhtmr.entity.Muscle;
import com.github.alxwhtmr.entity.MuscleGroup;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

/**
 * The {@code MuscleFacade} represents session bean
 * for performing data manipulation with muscle
 * entity in database
 */
@Stateless
public class MuscleFacade extends AbstractFacade<Muscle> {
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
    public MuscleFacade() {
        super(Muscle.class);
    }
    
    /**
     * Finds the entity in the database by its id
     * @param id an integer value of the id
     * @return single {@code Muscle} object
     */
    @PersistenceContext
    public Muscle findById(Integer id) {
        TypedQuery<Muscle> query =
                em.createNamedQuery("Muscle.findByIdMuscle", Muscle.class)
                .setParameter("idMuscle", id);
        Muscle muscle = query.getSingleResult();
        return muscle;        
    }
    
    /**
     * Finds a list of entities in the database
     * based on specified name pattern
     * @param name pattern
     * @return {@code List} of {@code Muscle} objects 
     */
    @PersistenceContext
    public List<Muscle> findByPattern(String name) {
        TypedQuery<Muscle> query =
                em.createNamedQuery("Muscle.findByPattern", Muscle.class)
                .setParameter("pattern", "%"+name+"%");
        List<Muscle> muscles = query.getResultList();
        return muscles;        
    }
    
    /**
     * Finds a record in a table {@code muscle} in database by its name
     * @param name a {@code String} that contains name of the desired record
     * @return {@code Muscle} object which represents a database record
     */
    @PersistenceContext
    public Muscle findByName(String name) {
        System.out.println("find " + name);
        TypedQuery<Muscle> query =
                em.createNamedQuery("Muscle.findByNameLatin", Muscle.class)
                .setParameter("nameLatin", name);
        Muscle muscle = query.getSingleResult();
        return muscle;        
    }
    
    /**
     * Inserts new record of the {@code muscle} entity into database
     * @param request {@code HttpServletRequest} object that contains data of the new record
     * @param mg {@code MuscleGroup} object that represents {@code muscle_group} entity
     * @return new {@code Muscle} if data was successfully inserted into database
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Muscle insertNewRecord(HttpServletRequest request, MuscleGroup mg) {
        try {
            Muscle muscle = new Muscle();
            muscle.setNameRu(request.getParameter("nameRu"));
            muscle.setNameLatin(request.getParameter("nameLatin"));
            muscle.setDescription(request.getParameter("description"));
            muscle.setExample(request.getParameter("example"));
            muscle.setFkMuscleGroup(mg);
            em.persist(muscle);
            return muscle;  
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
    /**
     * Deletes record of the {@code muscle} entity from the database
     * @param muscle the {@code Muscle} object to be deleted, which represents a {@code muscle} entity in database
     * @return deleted {@code Muscle} object, if deletion of the entity {@code muscle} from database was successful.  
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Muscle deleteRecord(Muscle muscle) {
        try {
            Muscle toBeRemoved = em.merge(muscle);
            em.remove(toBeRemoved);
            return muscle;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
    
    /**
     * Edits {@code muscle} record in database
     * @param request {@code HttpServletRequest} object that contains parameters of the editable record
     * @param dataToMerge {@code Muscle} object that will apply new parameters and after that will be merged with an existed record
     * @return changed {@code Muscle} object 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Muscle editRecord(HttpServletRequest request, Muscle dataToMerge) {
        dataToMerge.setNameRu(request.getParameter("nameRu"));
        dataToMerge.setNameLatin(request.getParameter("nameLatin"));
        dataToMerge.setDescription(request.getParameter("description"));
        dataToMerge.setExample(request.getParameter("example"));
        try {
            Muscle changedRecord = em.merge(dataToMerge);
            em.merge(changedRecord);
            return changedRecord;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
