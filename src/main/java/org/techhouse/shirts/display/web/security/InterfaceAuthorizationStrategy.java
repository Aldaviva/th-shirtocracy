package org.techhouse.shirts.display.web.security;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.techhouse.shirts.data.enums.Role;
import org.techhouse.shirts.display.web.WicketApplication;
import org.techhouse.shirts.service.DeadlineService;

public class InterfaceAuthorizationStrategy implements IAuthorizationStrategy {
	
	private final DeadlineService deadlineService;

	public InterfaceAuthorizationStrategy(DeadlineService deadlineService) {
		this.deadlineService = deadlineService;
	}

	@Override
	public <T extends Component> boolean isInstantiationAuthorized(final Class<T> componentClass) {
		if (AuthenticatedWebPage.class.isAssignableFrom(componentClass)) {
			if ((WicketSession.get()).isSignedIn()) {
				if (AdminPage.class.isAssignableFrom(componentClass)) {
					if (!WicketSession.get().hasRole(Role.ADMIN)) {
						// needed admin role to see this adminpage, but didn't have it
						return false;
					}
				} else if (DeadlinePage.class.isAssignableFrom(componentClass)){
					if (!WicketSession.get().hasRole(Role.ADMIN) && deadlineService.hasDeadlinePassed()) {
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
	public boolean isActionAuthorized(final Component arg0, final Action arg1) {
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

		pageMap.redirectToInterceptPage(WicketApplication.get().getSignInPageClass());*/
		
		throw new RestartResponseAtInterceptPageException(WicketApplication.get().getSignInPageClass());
	}

}
