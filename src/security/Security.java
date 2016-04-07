package security;

import java.util.List;

import exceptions.PersistenceFailureException;
import organisation.OrganisationUnit;
import persistence.DataAccess;
import security.Permission;
import security.User;
import security.UserPermission;

public interface Security {
	
	public boolean login(String userId, String encryptedPassword,DataAccess da) throws PersistenceFailureException;
	
	public User getUser(int userId,DataAccess da) throws PersistenceFailureException;
	
	public int getIdOfUserLoggedIn();
	
	public Permission getPermission(int permissionId,DataAccess da) throws PersistenceFailureException;

	public List<Permission> searchPermission(String searchString,DataAccess da) throws PersistenceFailureException;
	
	public List<UserPermission> getAllPermissionsForUser(String userId,DataAccess da);

	public OrganisationUnit getOrganizationUnitForUserPermission(String userId, int permissionId,DataAccess da);
	
	public boolean hasUserAccessToOrganizationUnit(String userId, int permissionId, long organizationId,DataAccess da);
	
}