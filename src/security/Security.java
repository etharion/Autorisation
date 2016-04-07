package security;

import java.util.List;

import exceptions.PersistenceFailureException;
import organisation.OrganisationUnit;
import persistence.DataAccess;
import security.Permission;
import security.User;
import security.UserPermission;

public interface Security {
	
	public boolean login(String userId, String encryptedPassword) throws PersistenceFailureException;
	
	public User getUser(int userId) throws PersistenceFailureException;
	
	public int getIdOfUserLoggedIn();
	
	public Permission getPermission(int permissionId) throws PersistenceFailureException;

	public List<Permission> searchPermission(String searchString) throws PersistenceFailureException;
	
	public List<UserPermission> getAllPermissionsForUser(String userId);

	public OrganisationUnit getOrganizationUnitForUserPermission(String userId, int permissionId);
	
	public boolean hasUserAccessToOrganizationUnit(String userId, int permissionId, long organizationId);
	
}