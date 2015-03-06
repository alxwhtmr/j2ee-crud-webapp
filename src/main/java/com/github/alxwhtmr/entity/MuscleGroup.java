/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.alxwhtmr.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The {@code MuscleGroup} class represents the muscle category,
 * which is an analogue of the muscle_group table in database
 * Contains
 */
@Entity
@Table(name = "muscle_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MuscleGroup.findAll", query = "SELECT m FROM MuscleGroup m"),
    @NamedQuery(name = "MuscleGroup.findByIdMuscleGroup", query = "SELECT m FROM MuscleGroup m WHERE m.idMuscleGroup = :idMuscleGroup"),
    @NamedQuery(name = "MuscleGroup.findByNameEn", query = "SELECT m FROM MuscleGroup m WHERE m.nameEn = :nameEn"),
    @NamedQuery(name = "MuscleGroup.findByNameRu", query = "SELECT m FROM MuscleGroup m WHERE m.nameRu = :nameRu")})
public class MuscleGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_muscle_group")
    private Integer idMuscleGroup;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nameEn")
    private String nameEn;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nameRu")
    private String nameRu;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkMuscleGroup")
    private Collection<Muscle> muscleCollection;

    /**
     * Returns muscle group id
     * @return muscle group id
     */
    public Integer getIdMuscleGroup() {
        return idMuscleGroup;
    }

    /**
     * Sets muscle group id
     * @param idMuscleGroup muscle group id 
     */
    public void setIdMuscleGroup(Integer idMuscleGroup) {
        this.idMuscleGroup = idMuscleGroup;
    }

    /**
     * Returns {@code String} with english name of the category
     * @return english name
     */
    public String getNameEn() {
        return nameEn;
    }

    /**
     * Sets english name of the muscle category
     * @param nameEn english name
     */
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    
    /**
     * Returns {@code String} with russian name of the category
     * @return russian name
     */
    public String getNameRu() {
        return nameRu;
    }
    
    /**
     * Sets russian name of the muscle category
     * @param nameRu russian name
     */
    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    /**
     * Returns the {@code Collection} of {@code Muscle} objects
     * which this category contains
     * @return collection of particular muscles
     */
    @XmlTransient
    public Collection<Muscle> getMuscleCollection() {
        return muscleCollection;
    }

    /**
     * Sets the {@code Collection} of {@code Muscle} objects
     * @param muscleCollection collection of muscle objects
     */
    public void setMuscleCollection(Collection<Muscle> muscleCollection) {
        this.muscleCollection = muscleCollection;
    }


    /**
     * Compares one {@code MuscleGroup} object with another
     * Two {@code MuscleGroup} objects are equal 
     * if theirs id are equal
     * @param object another {@code MuscleGroup} object 
     * @return true in case of equality, 
     * false in opposite case
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MuscleGroup)) {
            return false;
        }
        MuscleGroup other = (MuscleGroup) object;
        if ((this.idMuscleGroup == null && other.idMuscleGroup != null) || (this.idMuscleGroup != null && !this.idMuscleGroup.equals(other.idMuscleGroup))) {
            return false;
        }
        return true;
    }

    /**
     * Returns the {@code String} representation
     * of the {@code MuscleGroup} object
     * In particular case is: package.Class[id]
     * @return {@code String} with package, class and id value
     * of the {@code MuscleGroup} object
     */
    @Override
    public String toString() {
        return "com.github.alxwhtmr.entity.MuscleGroup[ idMuscleGroup=" + idMuscleGroup + " ]";
    }
    
}
