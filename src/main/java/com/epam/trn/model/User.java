package com.epam.trn.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement
public class User {
	private Integer id;
	private String login;
	@JsonIgnore
	private String password;
	private Boolean isActive;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;

	private List<UserRole> roles = new ArrayList<UserRole>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void addRole(UserRole role) {
		if(!this.roles.contains(role)) {
			this.roles.add(role);
		}
	}

	public void removeRole(UserRole role) {
		this.roles.remove(role);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
