package org.techhouse.shirts.service.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.techhouse.shirts.data.entities.Member;
import org.techhouse.shirts.data.enums.Role;
import org.techhouse.shirts.service.MemberService;

public class RestAuthenticator implements AuthenticationManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestAuthenticator.class);
	
	@Autowired
	private MemberService memberService;
	
	private String host;
	private int port;
	private String protocol;
	private String path;
	
	@Override
	public Authentication authenticate(final Authentication token) throws AuthenticationException {
		LOGGER.info("Trying to remotely authenticate user {} to {}://{}:{}{}.", new String[]{ token.getPrincipal().toString(), protocol, host, String.valueOf(port), path});
		
		final HttpHost targetHost = new HttpHost(host, port, protocol); 
		
		final DefaultHttpClient httpClient = new DefaultHttpClient();
		
		/** Run authentication requests through a proxy, such as Fiddler.
		 *  If the proxy server is not responding, all authentication requests will fail.
		 */
		/*/httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost("localhost", 9999));/**/

		httpClient.getCredentialsProvider().setCredentials(
		        new AuthScope(targetHost.getHostName(), targetHost.getPort()), 
		        new UsernamePasswordCredentials(token.getPrincipal().toString(), (String) token.getCredentials()));
     

		final HttpGet httpGet = new HttpGet(path);
		try {
			final HttpResponse response = httpClient.execute(targetHost, httpGet);

		    final HttpEntity entity = response.getEntity();
		    
		    final ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		    entity.writeTo(resultStream);
		    final String result = resultStream.toString();
		    
		    EntityUtils.consume(entity);
		    
		    LOGGER.debug("Response from REST auth service: "+result);
		    
		    if(token.getName().equals(result)){
		    	return createAuthenticatedToken(token);
		    } else {
		    	throw new BadCredentialsException("Incorrect username or password.");
		    }
		} catch (final ClientProtocolException e) {
			throw new AuthenticationServiceException("Transport protocol error while authenticating", e);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		
		return token;
	}

	private Authentication createAuthenticatedToken(final Authentication token) {
		Member member = Member.findMember(token.getName());
		if(member == null){
			member = memberService.createMember(token.getName());
		}
		final Collection<RoleGrantedAuthority> authorities = new ArrayList<RoleGrantedAuthority>();
		final Role nominalRole = member.getRole();
		for(final Role possibleRole : Role.values()){
			if(possibleRole.compareTo(nominalRole) <= 0){
				authorities.add(new RoleGrantedAuthority(possibleRole));
			}
		}
		final Authentication newToken = new UsernamePasswordAuthenticationToken(token.getPrincipal(), token.getCredentials(), authorities);
		return newToken;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public void setPort(final int port) {
		this.port = port;
	}

	public void setProtocol(final String protocol) {
		this.protocol = protocol;
	}

	public void setPath(final String path) {
		this.path = path;
	}

}
