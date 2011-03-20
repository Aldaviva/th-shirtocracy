package org.techhouse.shirts.display.web;

import org.apache.wicket.Component;
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
import org.techhouse.shirts.display.web.pages.BallotPage;
import org.techhouse.shirts.display.web.pages.LoginPage;
import org.techhouse.shirts.display.web.pages.StatisticsPage;
import org.techhouse.shirts.service.security.WicketSession;

public class WicketApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return BallotPage.class;
	}

	public void init(){
		 addComponentInstantiationListener(new SpringComponentInjector(this));
		 
		 getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy() {
			
			@Override
			public <T extends Component> boolean isInstantiationAuthorized(Class<T> componentClass) {
				if (AuthenticatedWebPage.class.isAssignableFrom(componentClass))
                {
                    if (((WicketSession)Session.get()).isSignedIn())
                    {
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
	
	private void redirectToLoginPage(){
		final RequestCycle cycle = RequestCycle.get();
		final Page requestPage = cycle.getRequest().getPage();

		final IPageMap pageMap;
		if (requestPage != null)
		{
			pageMap = requestPage.getPageMap();
		}
		else
		{
			RequestParameters parameters = cycle.getRequest().getRequestParameters();
			pageMap = PageMap.forName(parameters.getPageMapName());
		}

		pageMap.redirectToInterceptPage(LoginPage.class);
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new WicketSession(request);
	}
}
