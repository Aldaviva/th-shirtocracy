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
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
	public Authentication authenticate(Authentication token) throws AuthenticationException {
		LOGGER.info("Trying to remotely authenticate user {} to {}://{}:{}{}.", new String[]{ token.getPrincipal().toString(), protocol, host, String.valueOf(port), path});
		
		HttpHost targetHost = new HttpHost(host, port, protocol); 
		HttpHost proxy = new HttpHost("localhost", 9999);
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

		httpClient.getCredentialsProvider().setCredentials(
		        new AuthScope(targetHost.getHostName(), targetHost.getPort()), 
		        new UsernamePasswordCredentials(token.getPrincipal().toString(), (String) token.getCredentials()));

		// Create AuthCache instance
		//AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		//BasicScheme basicAuth = new BasicScheme();
		//authCache.put(targetHost, basicAuth);

		// Add AuthCache to the execution context
//		BasicHttpContext localcontext = new BasicHttpContext();
//		localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);        

		HttpGet httpGet = new HttpGet(path);
		try {
			HttpResponse response;
			response = httpClient.execute(targetHost, httpGet);

		    HttpEntity entity = response.getEntity();
		    
		    ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		    entity.writeTo(resultStream);
		    String result = resultStream.toString();
		    
		    EntityUtils.consume(entity);
		    
//		    String result = (new BufferedReader(new InputStreamReader(entity.getContent()))).readLine();
		    LOGGER.info("Response from REST auth service: "+result);
//		    token.setAuthenticated(token.getName().equals(result));
		    
		    if(token.getName().equals(result)){
		    	return createAuthenticatedToken(token);
		    }
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return token;
	}

	private Authentication createAuthenticatedToken(Authentication token) {
		Member member = Member.findMember(token.getName());
		if(member == null){
			member = memberService.createMember(token.getName());
		}
		Collection<RoleGrantedAuthority> authorities = new ArrayList<RoleGrantedAuthority>();
		Role nominalRole = member.getRole();
		for(Role possibleRole : Role.values()){
			if(possibleRole.compareTo(nominalRole) <= 0){
				authorities.add(new RoleGrantedAuthority(possibleRole));
			}
		}
		Authentication newToken = new UsernamePasswordAuthenticationToken(token.getPrincipal(), token.getCredentials(), authorities);
		return newToken;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
