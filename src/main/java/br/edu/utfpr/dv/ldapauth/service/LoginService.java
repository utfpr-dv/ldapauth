package br.edu.utfpr.dv.ldapauth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.edu.utfpr.dv.ldapauth.ldap.LdapUtils;
import br.edu.utfpr.dv.ldapauth.model.Credential;
import br.edu.utfpr.dv.ldapauth.model.User;

@Path("/authenticate")
public class LoginService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateLogin(Credential credentials) {
		try {
			if(credentials.getLogin().trim().isEmpty() || credentials.getPassword().trim().isEmpty()) {
	    			throw new Exception("Informe o usuário e a senha.");
	    		}
			
			if(credentials.getLogin().contains("@")){
				credentials.setLogin(credentials.getLogin().substring(0, credentials.getLogin().indexOf("@")));
			}
			
			credentials.setLogin(credentials.getLogin().toLowerCase().trim());
			
			String host = "";
			int port = 0;
			boolean useSSL = true;
			boolean ignoreCertificates = true;
			String basedn = "";
			String varUid = "";
			
			LdapUtils ldapUtils = new LdapUtils(host, port, useSSL, ignoreCertificates, basedn, varUid);
			
			ldapUtils.authenticate(credentials.getLogin(), credentials.getPassword());
			
			Map<String, String> dataLdap = ldapUtils.getLdapProperties(credentials.getLogin());
			
			User user = new User();
			
			if(this.loginIsStudent(credentials.getLogin())) {
				user.setCode("");
				user.setBarcode(dataLdap.get("carLicense"));
				user.setDigit(this.mod11(user.getBarcode()));
			} else {
				if(dataLdap.get("carLicense") == null) {
					user.setCode("");
				} else {
					user.setCode(dataLdap.get("carLicense"));
				}
				user.setBarcode(dataLdap.get("pager"));
				user.setDigit(this.mod11(user.getBarcode()));
			}
			
			user.setName(this.formatName(dataLdap.get("cn")));
			user.setEmail(dataLdap.get("mail"));
			
			return Response.ok(user).build();
		} catch (Exception e) {
			Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);

			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	private String formatName(String name){
		String[] list = name.trim().split(" ");
		List<String> list2 = new ArrayList<String>();
		
		for(String s : list){
			if(s.length() > 2){
				s = s.charAt(0) + s.substring(1).toLowerCase();	
			}else{
				s = s.toLowerCase();
			}
			
			list2.add(s);
		}
		
		return String.join(" ", list2);
	}
	
	private String mod11(String code) {
		int m = 2, sum = 0;
		
		for(int i = code.length() - 1; i >= 0; i--) {
			sum = sum + (Character.getNumericValue(code.charAt(i)) * m);
			m++;
		}
		
		if((sum % 11) < 2) {
			return "0";
		} else {
			return String.valueOf(11 - (sum % 11));
		}
	}
	
	public boolean loginIsStudent(String login){
		if(login.toLowerCase().startsWith("a")){
			login = login.substring(1);
			
			try{
				Integer.parseInt(login);
				
				return true;
			}catch(Exception e){
				return false;
			}
		}
		
		return false;
	}

}
