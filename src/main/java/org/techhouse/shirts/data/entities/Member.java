package org.techhouse.shirts.data.entities;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.techhouse.shirts.data.enums.Role;

@RooJavaBean
@RooToString
@RooEntity(identifierField = "name")
public class Member {

	@Id
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
}
