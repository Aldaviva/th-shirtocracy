package org.techhouse.shirts.display.web.pages;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.techhouse.shirts.data.enums.Role;
import org.techhouse.shirts.display.web.WicketApplication;
import org.techhouse.shirts.display.web.WicketSession;


public abstract class BasePage extends HtmlPage {
	
	public BasePage() {
		super();
		
		add(new WebMarkupContainer("main"){

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isTransparentResolver() {
				return true;
			}
			
		}.add(new SimpleAttributeModifier("id", this.getClass().getSimpleName())));
		
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
	
		Component adminNavContainer = new WebMarkupContainer("admin");
		adminNavContainer.setVisible(WicketSession.get().hasRole(Role.ADMIN) || WicketApplication.get().isDevelopment());
		add(adminNavContainer);

		adminNavContainer.add(new Link("designAdd"){

			@Override
			public void onClick() {
			}
			
		});
		
		add(new WebMarkupContainer("results").setVisible(WicketSession.get().hasRole(Role.ADMIN) || WicketApplication.get().isDevelopment()));
	}
	
}
