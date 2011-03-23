package org.techhouse.shirts.display.web.security;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.techhouse.shirts.data.enums.Role;
import org.techhouse.shirts.display.web.WicketApplication;

public class InterfaceAuthorizationStrategy implements IAuthorizationStrategy {
	
	@Override
	public <T extends Component> boolean isInstantiationAuthorized(Class<T> componentClass) {
		if (AuthenticatedWebPage.class.isAssignableFrom(componentClass)) {
			if ((WicketSession.get()).isSignedIn()) {
				if (AdminPage.class.isAssignableFrom(componentClass)) {
					if (!WicketSession.get().hasRole(Role.ADMIN)) {
						// needed admin role to see this adminpage, but didn't have it
						return false;
					}
				} else if (DeadlinePage.class.isAssignableFrom(componentClass)){
					if (!WicketSession.get().hasRole(Role.ADMIN)) {
						// needed admin role to see this deadlinepage, but didn't have it
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
	

	private void redirectToLoginPage() {
		/*final RequestCycle cycle = RequestCycle.get();
		final Page requestPage = cycle.getRequest().getPage();

		final IPageMap pageMap;
		if (requestPage != null) {
			pageMap = requestPage.getPageMap();
		} else {
			RequestParameters parameters = cycle.getRequest().getRequestParameters();
			pageMap = PageMap.forName(parameters.getPageMapName());
		}

		pageMap.redirectToInterceptPage(getSignInPageClass());*/
		
		throw new RestartResponseAtInterceptPageException(WicketApplication.get().getSignInPageClass());
	}

}
