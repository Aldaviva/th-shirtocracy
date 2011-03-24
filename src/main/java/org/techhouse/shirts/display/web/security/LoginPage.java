package org.techhouse.shirts.display.web.security;

import org.apache.wicket.authentication.pages.SignInPage;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class LoginPage extends SignInPage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);
	
	
	public LoginPage() {
		super();

		setupLoginForm();
	}

	private void setupLoginForm() {
		add(new LoginForm("loginForm"));
		add(new FeedbackPanel("feedback"));
	}

	private final class LoginForm extends StatelessForm<Void> {

		private static final long serialVersionUID = 1L;
		private final ValueMap properties = new ValueMap();
		
		public LoginForm(String id) {
			super(id);
			
			add(new TextField<String>("username", new PropertyModel<String>(properties, "username")).setRequired(true));
			
			add(new PasswordTextField("password", new PropertyModel<String>(properties, "password")));
		}

		@Override
		protected void onSubmit() {
			WicketSession session = (WicketSession) getSession();
			
			session.bind();
			
			String username = properties.getString("username");
			LOGGER.info("Submitting log in form with username = {}.", username);
			
			if(session.signIn(username, properties.getString("password"))){
				LOGGER.info("Log in succeeded.");
				if (!continueToOriginalDestination()) {
                    setResponsePage(getApplication().getHomePage());
                }
			} else {
				LOGGER.info("Login failed.");
				error(getString("login.failed"));
			}
		}
		
	}
}
