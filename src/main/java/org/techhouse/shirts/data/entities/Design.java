package org.techhouse.shirts.data.entities;

import java.io.Serializable;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.techhouse.shirts.data.query.QueryExtras;
import org.techhouse.shirts.data.query.SortParam;

@RooJavaBean
@RooToString
@RooEntity
public class Design implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	@NotNull
	private URL thumbnail;

	private URL photograph;

	private String artist;

	@Min(1985L)
	@Max(2021L)
	private Integer year;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "designs")
	// TODO probably don't want to cascade all
	private Set<Member> members = new HashSet<Member>();

	@Transient
	public Long countVotes() {
		return (long) members.size();
	}

	public static List<Design> findAllDesigns(final SortParam... sortParams) {
		return QueryExtras.list(Design.class, sortParams, entityManager());
	}
	
	public static int countAllVotes(){
		int total = 0;
		final List<Design> findAllDesigns = findAllDesigns();
		for (Design design : findAllDesigns){
			total += design.countVotes();
		}
		return total;
	}
}
