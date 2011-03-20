package org.techhouse.shirts.data.entities;

import java.io.Serializable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.techhouse.shirts.data.enums.Role;
import java.util.Set;
import org.techhouse.shirts.data.entities.Design;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

@RooJavaBean
@RooToString
@RooEntity(identifierField = "name")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Design> designs = new HashSet<Design>();
}
