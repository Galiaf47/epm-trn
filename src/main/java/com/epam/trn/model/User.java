package com.epam.trn.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private Integer id;
	private String login;
	private String password;
	private Boolean isActive;
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
		return (List<UserRole>) Collections.unmodifiableCollection(roles);
	}

	public void addRole(UserRole role) {
		this.roles.add(role);
	}

	public void removeRole(UserRole role) {
		this.roles.remove(role);
	}

}
