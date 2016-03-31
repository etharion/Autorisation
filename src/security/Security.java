package security;

import java.util.List;

import organisation.OrganisationUnit;
import security.Permission;
import security.User;
import security.UserPermission;

public interface Security {
	
	public boolean login(String userId, String encryptedPassword);
	
	public User getUser(String userId);
	
	public String getIdOfUserLoggedIn();
	
	public Permission getPermission(int permissionId);

	public Permission getPermission(String permissionName);
	
	public List<UserPermission> getAllPermissionsForUser(String userId);

	public OrganisationUnit getOrganizationUnitForUserPermission(String userId, int permissionId);
	
	public boolean hasUserAccessToOrganizationUnit(String userId, int permissionId, long organizationId);
	
}