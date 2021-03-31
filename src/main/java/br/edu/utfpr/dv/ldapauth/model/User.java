package br.edu.utfpr.dv.ldapauth.model;

public class User {
	
	private String name;
	private String email;
	private String code;
	private String barcode;
	private String digit;
	
	public User() {
		this.setName("");
		this.setEmail("");
		this.setCode("");
		this.setBarcode("");
		this.setDigit("");
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getDigit() {
		return digit;
	}
	public void setDigit(String digit) {
		this.digit = digit;
	}

}
