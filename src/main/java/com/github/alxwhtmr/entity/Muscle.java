package com.github.alxwhtmr.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The {@code Muscle} class represents entity object
 * which is an analogue of database table 'muscle'
 * @author alexbel
 */
@Entity
@Table(name = "muscle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Muscle.findAll", query = "SELECT m FROM Muscle m"),
    @NamedQuery(name = "Muscle.findByIdMuscle", query = "SELECT m FROM Muscle m WHERE m.idMuscle = :idMuscle"),
    @NamedQuery(name = "Muscle.findByNameRu", query = "SELECT m FROM Muscle m WHERE m.nameRu = :nameRu"),
    @NamedQuery(name = "Muscle.findByNameLatin", query = "SELECT m FROM Muscle m WHERE m.nameLatin = :nameLatin"),
    @NamedQuery(name = "Muscle.findByDescription", query = "SELECT m FROM Muscle m WHERE m.description = :description"),
    @NamedQuery(name = "Muscle.findByExample", query = "SELECT m FROM Muscle m WHERE m.example = :example"),
    @NamedQuery(name = "Muscle.findByPattern", query = "SELECT m FROM Muscle m WHERE m.nameRu LIKE :pattern OR m.nameLatin LIKE :pattern")
})
public class Muscle implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_muscle")
    private Integer idMuscle;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nameRu")
    private String nameRu;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nameLatin")
    private String nameLatin;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "description")
    private String description;
    
    @Size(max = 500)
    @Column(name = "example")
    private String example;
    
    @JoinColumn(name = "fk_muscle_group", referencedColumnName = "id_muscle_group")
    @ManyToOne(optional = false)
    private MuscleGroup fkMuscleGroup;

    
    /**
     * Returns id of the muscle
     * @return  id
     */
    public Integer getIdMuscle() {
        return idMuscle;
    }
    
    /**
     * Sets record id
     * @param idMuscle id
     */
    public void setIdMuscle(Integer idMuscle) {
        this.idMuscle = idMuscle;
    }

    /**
     * Returns russian name of the muscle
     * @return muscle russian name 
     */
    public String getNameRu() {
        return nameRu;
    }

    /**
     * Sets russian name of the muscle
     * @param nameRu a {@code String} that contains russian name
     */
    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    /**
     * Gets a latin name of the muscle
     * @return latin name of the muscle
     */
    public String getNameLatin() {
        return nameLatin;
    }

    /**
     * Sets a latin name of the muscle
     * @param nameLatin latin name of the muscle
     */
    public void setNameLatin(String nameLatin) {
        this.nameLatin = nameLatin;
    }
    
    /**
     * Gets the description of the muscle
     * @return {@code String} with description of the muscle
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the description of the muscle
     * @param description {@code String} object with description 
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Gets the example of the activities
     * that muscle can perform
     * @return example of the activities
     */
    public String getExample() {
        return example;
    }

    /**
     * Sets the example of the activities
     * that muscle can perform
     * @param example description of the activities
     */
    public void setExample(String example) {
        this.example = example;
    }
    
    /**
     * Gets the {@code MuscleGroup} object,
     * which is a foreign key index for
     * {@code Muscle}
     * @return {@code MuscleGroup} object
     */
    public MuscleGroup getFkMuscleGroup() {
        return fkMuscleGroup;
    }

    /**
     * Sets the foreign key index for the {@code Muscle}
     * @param fkMuscleGroup foreign key
     */
    public void setFkMuscleGroup(MuscleGroup fkMuscleGroup) {
        this.fkMuscleGroup = fkMuscleGroup;
    }


    /**
     * Performs the comparison with another
     * {@code Muscle} object. 
     * Two {@code Muscle} objects are equal 
     * if theirs id are equal
     * @param object another {@code Muscle} object 
     * @return true in case of equality, 
     * false in opposite case
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Muscle)) {
            return false;
        }
        Muscle other = (Muscle) object;
        if ((this.idMuscle == null && other.idMuscle != null) || (this.idMuscle != null && !this.idMuscle.equals(other.idMuscle))) {
            return false;
        }
        return true;
    }

    /**
     * Returns the {@code String} representation
     * of the {@code Muscle} object
     * In particular case is: package.Class[id]
     * @return {@code String} with package, class and id value
     * of the {@code Muscle} object
     */
    @Override
    public String toString() {
        return "com.github.alxwhtmr.entity.Muscle[ idMuscle=" + idMuscle + " ]";
    }
    
}
