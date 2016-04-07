package security;

import java.util.HashMap;
import java.util.List;

import exceptions.PersistenceFailureException;
import organisation.OrganisationUnit;

public interface Security {
	
	public boolean login(String userId, String encryptedPassword) throws PersistenceFailureException;
	
	public User getUser(int userId) throws PersistenceFailureException;
	
	public int getIdOfUserLoggedIn();
	
	public Permission getPermission(int permissionId) throws PersistenceFailureException;
	
	public HashMap<Integer, Permission> getAllPermissions() throws PersistenceFailureException;

	public List<Permission> searchPermission(String searchString) throws PersistenceFailureException;
	
	public List<UserPermission> getAllPermissionsForUser(String userId) throws PersistenceFailureException;

	public OrganisationUnit getOrganizationUnitForUserPermission(String userId, int permissionId);
	
	public boolean hasUserAccessToOrganizationUnit(String userId, int permissionId, long organizationId);
	
}