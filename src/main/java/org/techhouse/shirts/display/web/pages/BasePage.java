package org.techhouse.shirts.display.web.pages;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.techhouse.shirts.data.enums.Role;
import org.techhouse.shirts.display.web.WicketApplication;
import org.techhouse.shirts.display.web.security.WicketSession;


public abstract class BasePage extends HtmlPage {
	
	public BasePage() {
		super();
		
		add(new BookmarkablePageLink<BallotPage>("logoLink", WicketApplication.get().getHomePage()));
		
		add(new FeedbackPanel("feedback"){

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return anyMessage();
			}
		});
		
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
		
		add(new Link<Void>("logout"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				WicketSession.get().signOut();
				setResponsePage(WicketApplication.get().getHomePage());
			}
		});
	
		WebMarkupContainer adminNavContainer = new WebMarkupContainer("admin");
		adminNavContainer.setVisible(WicketSession.get().hasRole(Role.ADMIN) || WicketApplication.get().isDevelopment());
		add(adminNavContainer);

		adminNavContainer.add(new Link<Void>("designAdd"){
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onClick() {
				setResponsePage(DesignEditPage.class);
			}
		});
		
		adminNavContainer.add(new Link<Void>("designEdit"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(DesignListPage.class);
			}
		});
		
		add(new WebMarkupContainer("results").setVisible(WicketSession.get().hasRole(Role.ADMIN) || WicketApplication.get().isDevelopment()));
	}
	
}
