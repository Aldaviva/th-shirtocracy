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
import org.springframework.security.core.AuthenticationException;
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
	private String username;
	private Authentication authentication;
	
	public WicketSession(final Request request) {
		super(request);
		injectDependencies();
		ensureDependenciesNotNull();
	}

	@Override
	public boolean authenticate(final String username, final String password) {
		boolean authenticated = false;
		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
			authentication = authenticationManager.authenticate(authentication);
			if(authentication.isAuthenticated()){
				authenticated = true;
				this.authentication = authentication;
				this.username = username;
				setWicketRoles(authentication);
				bindAuthentication();
			}
		} catch (AuthenticationException e) {
			authenticated = false;
		}
		return authenticated;
	}
	
	public static WicketSession get(){
		return (WicketSession) Session.get();
	}
	
	public void signOut() {
		super.signOut();
        roles = null;
        authentication = null;
        username = null;
        invalidate();
        detach();
	}

	private void setWicketRoles(final Authentication token) {
		final Collection<GrantedAuthority> authorities = token.getAuthorities();
		final String[] authorityRoles = new String[authorities.size()];
		
		int i=0;
		for(final GrantedAuthority authority : authorities){
			authorityRoles[i++] = authority.getAuthority();
		}
		
		roles = new Roles(authorityRoles);
	}
	
	@Override
	public Roles getRoles() {
		return roles;
	}
	
	public Member getMember(){
		if(username != null){
			return Member.findMember(username);
		} else {
			return null;
		}
	}
	
	public boolean hasRole(final Role role){
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
        bindAuthentication();
    }

    @Override
    protected void detach()
    {
        SecurityContextHolder.clearContext();
        super.detach();
    }
    
    private void bindAuthentication()
    {    
    	final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

}
