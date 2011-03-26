package org.techhouse.shirts.display.web.security;

import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.techhouse.shirts.display.web.behaviors.PlaceholderBehavior;
import org.techhouse.shirts.display.web.behaviors.SetCssClassToWicketIdBehavior;
import org.techhouse.shirts.display.web.pages.BasePage;

public class SignInPage extends BasePage {
	
	public SignInPage() {
		add(new SignInForm("form"));
	}

	protected static final class SignInForm extends StatelessForm<Void> {
		private static final long serialVersionUID = 1L;

		private final WebMarkupContainer hint;
		private final TextField<String> username;
		private final PasswordTextField password;
		private final ValueMap properties = new ValueMap();

		public SignInForm(final String id) {
			super(id);

			username = new TextField<String>("username", new PropertyModel<String>(properties, "username"));
			username.add(new PlaceholderBehavior());
			username.setType(String.class);
			add(username);

			password = new PasswordTextField("password", new PropertyModel<String>(properties, "password"));
			password.add(new PlaceholderBehavior());
			password.setType(String.class);
			add(password);

			hint = new WebMarkupContainer("hint");
			hint.setVisible(false);
			hint.add(new SetCssClassToWicketIdBehavior());
			add(hint);
		}

		@Override
		public final void onSubmit() {
			if (signIn(getUsername(), getPassword())) {
				onSignInSucceeded();
			} else {
				onSignInFailed();
			}
		}

		private String getUsername() {
			return username.getDefaultModelObjectAsString();
		}

		private String getPassword() {
			return password.getInput();
		}

		private boolean signIn(final String username, final String password) {
			return AuthenticatedWebSession.get().signIn(username, password);
		}

		protected void onSignInFailed() {
//			error(getLocalizer().getString("signInFailed", this, "Sign in failed"));
			hint.setVisible(true);
			add(new AttributeAppender("class", true, Model.of("failed"), " "));
		}

		protected void onSignInSucceeded() {
			if (!continueToOriginalDestination()) {
				setResponsePage(getApplication().getHomePage());
			}
		}
	}
}
