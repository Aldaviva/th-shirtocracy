package org.techhouse.shirts.data.entities;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.techhouse.shirts.data.entities.Member;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
public class Vote {

    @ManyToOne
    private Member member;

    @ManyToOne
    private Design design;
}
