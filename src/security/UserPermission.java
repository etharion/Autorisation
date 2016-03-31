package security;

import organisation.OrganisationUnit;

public class UserPermission {
	
	private String userId;
	private Permission permission;
	private OrganisationUnit organizationUnit;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public OrganisationUnit getOrganizationUnit() {
		return organizationUnit;
	}
	public void setOrganizationUnit(OrganisationUnit organizationUnit) {
		this.organizationUnit = organizationUnit;
	}
	@Override
	public String toString() {
		return "UserPermission [userId=" + userId + ", permission=" + permission + ", organizationUnit=" + organizationUnit
				+ "]";
	}

}