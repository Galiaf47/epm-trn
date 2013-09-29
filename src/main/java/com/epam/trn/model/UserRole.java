package com.epam.trn.model;

public class UserRole {
	private Integer id;
	private Integer userId;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object other) {
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof UserRole))return false;
	    UserRole otherUserRole = (UserRole)other;
	    
	    return this.name.equals(otherUserRole.getName());
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
