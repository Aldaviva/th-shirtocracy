package org.techhouse.shirts.data.entities;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(identifierField = "name")
public class Design implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    private String name;

    @NotNull
    private URL thumbnail;

    private URL photo;

    private String artist;

    @Min(1985L)
    @Max(2021L)
    private Integer year;

    @Transient
    public Long countVotes() {
        return entityManager().createQuery("select count(v) from Vote v where v.design = :design", Long.class).setParameter("design", this).getSingleResult();
    }

	public static List<Design> findAllDesigns() {
        return entityManager().createQuery("select d from Design d order by d.name asc", Design.class).getResultList();
    }
}
