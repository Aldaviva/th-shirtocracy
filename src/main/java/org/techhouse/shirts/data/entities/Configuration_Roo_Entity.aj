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
import org.techhouse.shirts.data.entities.Configuration;

privileged aspect Configuration_Roo_Entity {
    
    declare @type: Configuration: @Entity;
    
    @PersistenceContext
    transient EntityManager Configuration.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Configuration.id;
    
    @Version
    @Column(name = "version")
    private Integer Configuration.version;
    
    public Long Configuration.getId() {
        return this.id;
    }
    
    public void Configuration.setId(Long id) {
        this.id = id;
    }
    
    public Integer Configuration.getVersion() {
        return this.version;
    }
    
    public void Configuration.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Configuration.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Configuration.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Configuration attached = Configuration.findConfiguration(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Configuration.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Configuration.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Configuration Configuration.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Configuration merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Configuration.entityManager() {
        EntityManager em = new Configuration().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Configuration.countConfigurations() {
        return entityManager().createQuery("select count(o) from Configuration o", Long.class).getSingleResult();
    }
    
    public static List<Configuration> Configuration.findAllConfigurations() {
        return entityManager().createQuery("select o from Configuration o", Configuration.class).getResultList();
    }
    
    public static Configuration Configuration.findConfiguration(Long id) {
        if (id == null) return null;
        return entityManager().find(Configuration.class, id);
    }
    
    public static List<Configuration> Configuration.findConfigurationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Configuration o", Configuration.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}