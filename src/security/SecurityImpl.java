package security;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exceptions.PersistenceFailureException;
import organisation.OrganisationImpl;
import organisation.OrganisationUnit;
import persistence.DataAccess;
import persistence.DataAccessForSQL;

public class SecurityImpl implements Security {
	private User userLoggedIn = null;
	DataAccessForSQL da;
	private final String GET_USER = "SELECT * FROM user";
	private final String GET_USER_FROM_ID = "SELECT * FROM user where user_id = ?";
	private final String GET_PERMISSION_FROM_ID = "SELECT * FROM permission WHERE permission_id = ?";
	private final String GET_ALL_PERMISSIONS = "SELECT * FROM permission";
	private final String SEARCH_PERMISSION = "SELECT * FROM permission WHERE permission_name LIKE '%?%'";
	private final String GET_ALL_PERMISSIONS_FOR_USER = "SELECT * FROM user_permission WHERE user_id = ?";
	private final String GET_USER_ORGANISATION = "SELECT * FROM user_organisation WHERE user_id = ?";
	private final String GET_ORGANISATION = "SELECT * FROM organisation WHERE id = ?";
	private final String CHECK_ORGANISATION = "SELECT * FROM user_organisation WHERE user_id = ?";
	private final String CHECK_PERMISSION = "SELECT * FROM user_permission WHERE user_id = ?";
	
	@Override
	public boolean login(String email, String encryptedPassword) throws PersistenceFailureException {
		boolean loginSuccess = false;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int userId;
		da = new DataAccessForSQL();

		try {
			statement = da.getConnection().prepareStatement(GET_USER);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String tempEmail = resultSet.getString("EMAIL");
				String tempPassword = resultSet.getString("USER_PASSWORD");
				if (encryptedPassword.equalsIgnoreCase(tempPassword) && email.equalsIgnoreCase(tempEmail)) {
					loginSuccess = true;
					userId = resultSet.getInt("USER_ID");
					resultSet.close();
					statement.close();
					da.close();
					userLoggedIn = getUser(userId);
					break;
				} else {
					loginSuccess = false;
					userLoggedIn = null;
				}

			}
		} catch (SQLException e) {
			da.close();
			e.printStackTrace();
			throw new PersistenceFailureException("Persistence Failure - didn't get user data");
		}
		
		return loginSuccess;
	}

	@Override
	public User getUser(int userId) throws PersistenceFailureException {
		PreparedStatement statement;
		ResultSet resultSet;
		User user = new User();
		user.setId(userId);
		da = new DataAccessForSQL();

		try {
			statement = da.getConnection().prepareStatement(GET_USER_FROM_ID);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();
		
			while (resultSet.next()) {
				user.setEmail(resultSet.getString("EMAIL"));

			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceFailureException("Persistence Failure - didn't get user");
		}
		da.close();
		return user;
	}

	@Override
	public int getIdOfUserLoggedIn() {
		int Id = -1;
		if (userLoggedIn != null) {
			Id = userLoggedIn.getId();
		}
		return Id;
	}

	@Override
	public Permission getPermission(int permissionId) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Permission permission = new Permission();
		permission.setId(permissionId);
		da = new DataAccessForSQL();

		try {
			statement = da.getConnection().prepareStatement(GET_PERMISSION_FROM_ID);
			statement.setInt(1, permissionId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				permission.setName(resultSet.getString("PERMISSION_NAME"));

			}
		} catch (SQLException e) {
			da.close();
			throw new PersistenceFailureException("Persistence Failure - didn't get permission data");
		}
		da.close();
		return permission;

	}
	
	@Override
	public HashMap<Integer, Permission> getAllPermissions() throws PersistenceFailureException {
		PreparedStatement statement;
		ResultSet resultSet;
		HashMap<Integer, Permission> permissionList = new HashMap<>();
		da = new DataAccessForSQL();

		try {
			statement = da.getConnection().prepareStatement(GET_ALL_PERMISSIONS);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Permission permission = new Permission();
				permission.setId(resultSet.getInt("permission_id"));
				permission.setName(resultSet.getString("permission_name"));

				permissionList.put(permission.getId(), permission);

			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceFailureException("Persistence Failure - didn't get permission");
		}

		da.close();
		return permissionList;
	}

	@Override
	public List<Permission> searchPermission(String searchString) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Permission> permissionList = new ArrayList();
		da = new DataAccessForSQL();

		try {
			statement = da.getConnection().prepareStatement(SEARCH_PERMISSION);
			statement.setString(1, searchString);
			resultSet = statement.executeQuery();
			

			while (resultSet.next()) {
				Permission permission = new Permission();
				permission.setName(resultSet.getString("PERMISSION_NAME"));
				permission.setId(resultSet.getInt("PERMISSION_ID"));
				permissionList.add(permission);

			}
		} catch (SQLException e) {
			da.close();
			throw new PersistenceFailureException("Persistence Failure - didn't get permission data");
		}
		da.close();
		return permissionList;
	}

	@Override
	public List<UserPermission> getAllPermissionsForUser(int userId) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<UserPermission> permissionList = new ArrayList<>();
		HashMap<Integer, Permission> permissions = getAllPermissions();
		da = new DataAccessForSQL();

		try {
			statement = da.getConnection().prepareStatement(GET_ALL_PERMISSIONS_FOR_USER);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int permissionId = resultSet.getInt("permission_id");
				if (permissions.containsKey(permissionId)) {
					UserPermission userPermission = new UserPermission();
					userPermission.setUserId(userId);
					userPermission.setPermission(permissions.get(permissionId));
					userPermission.setOrganizationUnit(getOrganisationUnit(userId));

					permissionList.add(userPermission);
				}
				

			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceFailureException("Persistence Failure - didn't get permissions for user");
		}

		da.close();
		return permissionList;
	}

	@Override
	public OrganisationUnit getOrganizationUnitForUser(int userId) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		OrganisationUnit orgUnit = new OrganisationUnit();
		da = new DataAccessForSQL();
		
		try {
			statement = da.getConnection().prepareStatement(GET_USER_ORGANISATION);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int orgId = resultSet.getInt("organisation_id");
				orgUnit = getOrganisationUnit(orgId);
				
			}
			resultSet.close();
			statement.close();
			
		} catch (SQLException e) {
			throw new PersistenceFailureException("Persistence Failure - didn't get organisation unit");
		}

		da.close();
		return orgUnit;
	}
	
	@Override
	public OrganisationUnit getOrganisationUnit(int id) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		OrganisationUnit orgUnit = new OrganisationUnit();
		da = new DataAccessForSQL();
		
		try {
			statement = da.getConnection().prepareStatement(GET_ORGANISATION);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				orgUnit.setId(resultSet.getInt("id"));
				orgUnit.setName(resultSet.getString("name"));
			}
			resultSet.close();
			statement.close();
			
		} catch (SQLException e) {
			throw new PersistenceFailureException("Persistence Failure - didn't get organisation unit");
		}

		da.close();
		return orgUnit;
	}

	@Override
	public boolean hasUserAccessToOrganizationUnit(int userId, int permissionId, long organizationId) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		boolean hasAccess = false;
		boolean isChild = false;
		da = new DataAccessForSQL();
		OrganisationImpl org = new OrganisationImpl();
		
		try {
			statement = da.getConnection().prepareStatement(GET_ORGANISATION);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				for(OrganisationUnit orgUnit : org.getAllChildren(resultSet.getLong("id"))) {
					if(orgUnit.getId() == organizationId) {
						isChild = true;
					}
				}
				
				if(isChild) {
					resultSet.close();
					statement.close();
					da.close();
					hasAccess = hasUserPermission(userId, permissionId);
					break;
				} else {
					System.out.println("eg");
					hasAccess = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceFailureException("Persistence Failure - didn't get organisation unit");
		}

		return hasAccess;
	}

	@Override
	public boolean hasUserPermission(int userId, int permissionId) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		boolean hasPermission = false;
		da = new DataAccessForSQL();
		
		try {
			statement = da.getConnection().prepareStatement(CHECK_PERMISSION);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if(permissionId == resultSet.getInt("permission_id")) {
					hasPermission = true;
					break;
				} else {
					hasPermission = false;
				}
			}
			resultSet.close();
			statement.close();
			
		} catch (SQLException e) {
			throw new PersistenceFailureException("Persistence Failure - didn't get organisation unit");
		}

		da.close();
		return hasPermission;
	}
}
