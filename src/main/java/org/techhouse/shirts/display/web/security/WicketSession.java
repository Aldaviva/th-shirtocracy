package org.techhouse.shirts.display.web.security;

import java.util.Collection;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.techhouse.shirts.data.entities.Member;
import org.techhouse.shirts.data.enums.Role;

public class WicketSession extends AuthenticatedWebSession {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private AuthenticationManager authenticationManager;
	
	private Roles roles;
	private Member member;
	private Authentication authentication;
	
	public WicketSession(Request request) {
		super(request);
		injectDependencies();
		ensureDependenciesNotNull();
	}

	@Override
	public boolean authenticate(final String username, final String password) {
		authentication = new UsernamePasswordAuthenticationToken(username, password);
		authentication = authenticationManager.authenticate(authentication);
		setWicketRoles(authentication);
		return authentication.isAuthenticated();
	}
	
	public static WicketSession get(){
		return (WicketSession) Session.get();
	}
	
	public void signOut() {
		super.signOut();
        roles = null;
        authentication = null;
        member = null;
        invalidate();
        detach();
	}

	private void setWicketRoles(Authentication token) {
		Collection<GrantedAuthority> authorities = token.getAuthorities();
		String[] authorityRoles = new String[authorities.size()];
		
		int i=0;
		for(GrantedAuthority authority : authorities){
			authorityRoles[i++] = authority.getAuthority();
		}
		
		roles = new Roles(authorityRoles);
	}
	
	@Override
	public Roles getRoles() {
		return roles;
	}
	
	public Member getMember(){
		return member;
	}
	
	public boolean hasRole(Role role){
		return roles != null && roles.hasRole(role.name());
	}
	
	protected void injectDependencies()
	{
		InjectorHolder.getInjector().inject(this);
	}
	
	private void ensureDependenciesNotNull()
	{
		if (authenticationManager == null)
		{
			throw new IllegalStateException("WicketSession requires an authenticationManager.");
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
    protected void attach()
    {
        super.attach();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

    @Override
    protected void detach()
    {
        SecurityContextHolder.clearContext();
        super.detach();
    }

}
