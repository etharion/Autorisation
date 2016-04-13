package security;

import organisation.OrganisationUnit;

public class UserPermission {
	
	private int userId;
	private Permission permission;
	private OrganisationUnit organizationUnit;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
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