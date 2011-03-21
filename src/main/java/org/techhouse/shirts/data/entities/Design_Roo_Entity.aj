// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.techhouse.shirts.data.entities;

import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;
import org.techhouse.shirts.data.entities.Design;

privileged aspect Design_Roo_Entity {
    
    declare @type: Design: @Entity;
    
    @PersistenceContext
    transient EntityManager Design.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Design.id;
    
    @Version
    @Column(name = "version")
    private Integer Design.version;
    
    public Long Design.getId() {
        return this.id;
    }
    
    public void Design.setId(Long id) {
        this.id = id;
    }
    
    public Integer Design.getVersion() {
        return this.version;
    }
    
    public void Design.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Design.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Design.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Design attached = Design.findDesign(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Design.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Design.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Design Design.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Design merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Design.entityManager() {
        EntityManager em = new Design().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Design.countDesigns() {
        return entityManager().createQuery("select count(o) from Design o", Long.class).getSingleResult();
    }
    
    public static List<Design> Design.findAllDesigns() {
        return entityManager().createQuery("select o from Design o", Design.class).getResultList();
    }
    
    public static Design Design.findDesign(Long id) {
        if (id == null) return null;
        return entityManager().find(Design.class, id);
    }
    
    public static List<Design> Design.findDesignEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Design o", Design.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
