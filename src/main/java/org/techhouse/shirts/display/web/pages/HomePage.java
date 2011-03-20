package org.techhouse.shirts.display.web.pages;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.techhouse.shirts.data.entities.Design;
import org.techhouse.shirts.data.entities.Member;
import org.techhouse.shirts.display.web.AuthenticatedWebPage;
import org.techhouse.shirts.service.security.WicketSession;

public class HomePage extends BasePage implements AuthenticatedWebPage {

	public HomePage() {
		super();
		
		IModel<Long> memberCount = new AbstractReadOnlyModel<Long>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Long getObject() {
				return Member.countMembers();
			}
		};
		
		IModel<Long> designCount = new AbstractReadOnlyModel<Long>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Long getObject() {
				return Design.countDesigns();
			}
		};
		
		add(new Label("memberCount", memberCount));
		add(new Label("designCount", designCount));
		
		add(new Label("roles", new AbstractReadOnlyModel<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject() {
				Roles roles = WicketSession.get().getRoles();
				return StringUtils.join(roles, ", ");
			}
		}));
		
		add(new Link<Void>("logout"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				WicketSession.get().signOut();
				setResponsePage(getApplication().getHomePage());
			}
			
		});
	}

}
