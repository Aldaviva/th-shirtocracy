package org.techhouse.shirts.display.web;

import java.net.URL;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.IPageMap;
import org.apache.wicket.Page;
import org.apache.wicket.PageMap;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.convert.ConverterLocator;
import org.techhouse.shirts.data.enums.Role;
import org.techhouse.shirts.display.web.components.converters.URLConverter;
import org.techhouse.shirts.display.web.pages.BallotPage;
import org.techhouse.shirts.display.web.security.AdminPage;
import org.techhouse.shirts.display.web.security.AuthenticatedWebPage;
import org.techhouse.shirts.display.web.security.LoginPage;
import org.techhouse.shirts.display.web.security.SuperPage;
import org.techhouse.shirts.display.web.security.WicketSession;

public class WicketApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return BallotPage.class;
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converterLocator = new ConverterLocator();
		
		converterLocator.set(URL.class, new URLConverter());
		
		return converterLocator;
	}

	public void init() {
		addComponentInstantiationListener(new SpringComponentInjector(this));

		if (!isDevelopment()) {
			getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy() {

				@Override
				public <T extends Component> boolean isInstantiationAuthorized(Class<T> componentClass) {
					if (AuthenticatedWebPage.class.isAssignableFrom(componentClass)) {
						if ((WicketSession.get()).isSignedIn()) {
							if (AdminPage.class.isAssignableFrom(componentClass)) {
								if (!WicketSession.get().hasRole(Role.ADMIN)) {
									// needed admin role to see this adminpage, but didn't ahve it
									return false;
								}
							}
							
							if (SuperPage.class.isAssignableFrom(componentClass)) {
								if (!WicketSession.get().hasRole(Role.ADMIN)) {
									// needed admin role to see this adminpage, but didn't ahve it
									return false;
								}
							}

							// okay to proceed
							return true;
						}

						// Force sign in
						redirectToLoginPage();
					}
					return true;
				}

				@Override
				public boolean isActionAuthorized(Component arg0, Action arg1) {
					return true;
				}
			});
		}
	}

	public static WicketApplication get() {
		return (WicketApplication) WebApplication.get();
	}

	private void redirectToLoginPage() {
		final RequestCycle cycle = RequestCycle.get();
		final Page requestPage = cycle.getRequest().getPage();

		final IPageMap pageMap;
		if (requestPage != null) {
			pageMap = requestPage.getPageMap();
		} else {
			RequestParameters parameters = cycle.getRequest().getRequestParameters();
			pageMap = PageMap.forName(parameters.getPageMapName());
		}

		pageMap.redirectToInterceptPage(LoginPage.class);
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new WicketSession(request);
	}

	public boolean isDevelopment() {
		return getConfigurationType().equals(Application.DEVELOPMENT);
	}
}
