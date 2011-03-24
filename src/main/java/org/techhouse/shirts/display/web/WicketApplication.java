package org.techhouse.shirts.display.web;

import java.net.URL;

import org.apache.wicket.Application;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authentication.pages.SignInPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.convert.ConverterLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.techhouse.shirts.display.web.converters.URLConverter;
import org.techhouse.shirts.display.web.pages.BallotPage;
import org.techhouse.shirts.display.web.pages.StatisticsPage;
import org.techhouse.shirts.display.web.security.InterfaceAuthorizationStrategy;
import org.techhouse.shirts.display.web.security.WicketSession;
import org.techhouse.shirts.service.DeadlineService;

public class WicketApplication extends AuthenticatedWebApplication {

	@Autowired
	private DeadlineService deadlineService;
	
	@Override
	public Class<? extends Page> getHomePage() {
		if(deadlineService.hasDeadlinePassed()){
			return StatisticsPage.class;
		} else {
			return BallotPage.class;
		}
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		final ConverterLocator converterLocator = new ConverterLocator();
		
		converterLocator.set(URL.class, new URLConverter());
		
		return converterLocator;
	}

	@Override
	public void init() {
		addComponentInstantiationListener(new SpringComponentInjector(this));

		getApplicationSettings().setPageExpiredErrorPage(getHomePage());

		if (!isDevelopment()) {
			getSecuritySettings().setAuthorizationStrategy(new InterfaceAuthorizationStrategy());
		}
	}

	public static WicketApplication get() {
		return (WicketApplication) AuthenticatedWebApplication.get();
	}


	public boolean isDevelopment() {
		return getConfigurationType().equals(Application.DEVELOPMENT);
	}

	@Override
	protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
		return WicketSession.class;
	}

	@Override
	public Class<? extends WebPage> getSignInPageClass() {
		return SignInPage.class;
	}
}
