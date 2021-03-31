package br.edu.utfpr.dv.ldapauth.service;

import org.glassfish.jersey.server.ResourceConfig;

public class JerseyApplication extends ResourceConfig {
    
	public JerseyApplication() {
        register(CORSFilter.class);
    }
	
}
