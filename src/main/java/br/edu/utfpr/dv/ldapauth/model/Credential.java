package br.edu.utfpr.dv.ldapauth.model;

public class Credential {

	private String login;
	private String password;
	
	public Credential() {
		this.setLogin("");
		this.setPassword("");
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
