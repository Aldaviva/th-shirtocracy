package org.techhouse.shirts.display.web.pages;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.techhouse.shirts.data.enums.Role;
import org.techhouse.shirts.service.security.WicketSession;


public abstract class BasePage extends HtmlPage {

	public BasePage() {
		super();
		
		add(new Label("username", new AbstractReadOnlyModel<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getObject() {
				if(WicketSession.get().isSignedIn()){
					return WicketSession.get().getMember().getName();
				} else {
					return getString("nav.user.anonymous");
				}
			}
		}));
		
		
		add(new WebMarkupContainer("admin").setVisible(WicketSession.get().hasRole(Role.ADMIN)));
		add(new WebMarkupContainer("results").setVisible(WicketSession.get().hasRole(Role.ADMIN)));
	}

	
	
}
