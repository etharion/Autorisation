package security;

import java.util.HashMap;
import java.util.List;

import exceptions.PersistenceFailureException;
import organisation.OrganisationUnit;

public interface Security {
	
	public boolean login(String email, String encryptedPassword) throws PersistenceFailureException;
	
	public User getUser(int userId) throws PersistenceFailureException;
	
	public int getIdOfUserLoggedIn();
	
	public Permission getPermission(int permissionId) throws PersistenceFailureException;
	
	public HashMap<Integer, Permission> getAllPermissions() throws PersistenceFailureException;

	public List<Permission> searchPermission(String searchString) throws PersistenceFailureException;
	
	public List<UserPermission> getAllPermissionsForUser(int userId) throws PersistenceFailureException;

	public OrganisationUnit getOrganizationUnitForUser(int userId) throws PersistenceFailureException;
	
	public OrganisationUnit getOrganisationUnit(int id) throws PersistenceFailureException;
	
	public boolean hasUserAccessToOrganizationUnit(int userId, int permissionId, long organizationId) throws PersistenceFailureException;
	
	public boolean hasUserPermission(int userId, int permissionId) throws PersistenceFailureException;
	
}