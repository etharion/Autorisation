package security;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exceptions.PersistenceFailureException;
import organisation.OrganisationUnit;
import persistence.DataAccess;

public class SecurityImpl implements Security {
	private User userLoggedIn = null;
	DataAccess da;
	private final String GET_USER = "SELECT * FROM user";
	private final String GET_USER_FROM_ID = "SELECT * FROM user where id = ?";
	private final String GET_PERMISSION_FROM_ID = "SELECT * FROM permission WHERE permission_id = ?";
	private final String GET_ALL_PERMISSIONS = "SELECT * FROM permission";
	private final String SEARCH_PERMISSION = "SELECT * FROM permission WHERE permission_name LIKE '%?%'";
	private final String GET_ALL_PERMISSIONS_FOR_USER = "SELECT * FROM user_permission WHERE user_id = ?";

	@Override
	public boolean login(String email, String encryptedPassword) throws PersistenceFailureException {
		boolean loginSuccess = false;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int userId;

		try {
			statement = da.getConnection().prepareStatement(GET_USER);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {

				if (email == resultSet.getString("EMAIL") & encryptedPassword == resultSet.getString("USER_PASSWORD")) {
					loginSuccess = true;
					userId = resultSet.getInt("USER_ID");
					da.close();
					userLoggedIn = getUser(userId);
				} else {
					loginSuccess = false;
					userLoggedIn = null;
					da.close();
				}

			}
		} catch (SQLException e) {
			da.close();
			throw new PersistenceFailureException("Persistence Failure - didn't get user data");
		}
		
		return loginSuccess;
	}

	@Override
	public User getUser(int userId) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = new User();
		user.setId(userId);

		try {
			statement = da.getConnection().prepareStatement(GET_USER_FROM_ID);
			resultSet = statement.executeQuery();
			statement.setInt(1, userId);
			while (resultSet.next()) {
				user.setEmail(resultSet.getString("EMAIL"));
				user.setId(userId);

			}
		} catch (SQLException e) {
			da.close();
			throw new PersistenceFailureException("Persistence Failure - didn't get user data");
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

		try {
			statement = da.getConnection().prepareStatement(GET_PERMISSION_FROM_ID);
			resultSet = statement.executeQuery();
			statement.setInt(1, permissionId);
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
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		HashMap<Integer, Permission> permissionList = new HashMap<>();

		try {
			statement = da.getConnection().prepareStatement(GET_ALL_PERMISSIONS);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Permission permission = new Permission();
				permission.setId(resultSet.getInt("id"));
				permission.setName(resultSet.getString("name"));

				permissionList.put(permission.getId(), permission);

			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new PersistenceFailureException("Persistence Failure - didn't get organisation unit");
		}

		da.close();
		return permissionList;
	}

	@Override
	public List<Permission> searchPermission(String searchString) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Permission> permissionList = new ArrayList();

		try {
			statement = da.getConnection().prepareStatement(SEARCH_PERMISSION);
			resultSet = statement.executeQuery();
			statement.setString(1, searchString);

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
	public List<UserPermission> getAllPermissionsForUser(String userId) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<UserPermission> permissionList = new ArrayList<>();
		HashMap<Integer, Permission> permissions = getAllPermissions();

		try {
			statement = da.getConnection().prepareStatement(GET_ALL_PERMISSIONS_FOR_USER);
			statement.setString(1, userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String permissionId = resultSet.getString("permission_id");
				if (permissions.containsKey(permissionId)) {
					UserPermission userPermission = new UserPermission();
					userPermission.setPermission(permissions.get(permissionId));

					permissionList.add(userPermission);
				}
				

			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new PersistenceFailureException("Persistence Failure - didn't get organisation unit");
		}

		da.close();
		return permissionList;
	}

	@Override
	public OrganisationUnit getOrganizationUnitForUserPermission(String userId, int permissionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasUserAccessToOrganizationUnit(String userId, int permissionId, long organizationId) {
		// TODO Auto-generated method stub
		return false;
	}

}
