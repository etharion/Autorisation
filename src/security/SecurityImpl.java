package security;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.PersistenceFailureException;
import organisation.OrganisationUnit;
import persistence.DataAccess;

public class SecurityImpl implements Security {
	private User userLoggedIn = null;
	private final String GET_USER = "SELECT * FROM user";
	private final String GET_USER_FROM_ID="SELECT * FROM user where id = ?";
	private final String GET_PERMISSION_FROM_ID ="SELECT * FROM permission WHERE permission_id = ?";
	private final String SEARCH_PERMISSION ="SELECT * FROM permission WHERE permission_name LIKE '%?%'";

	@Override
	public boolean login(String email, String encryptedPassword, DataAccess da) throws PersistenceFailureException {
		boolean loginSuccess = false;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = da.getConnection().prepareStatement(GET_USER);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {

				if (email == resultSet.getString("EMAIL")
						& encryptedPassword == resultSet.getString("USER_PASSWORD")) {
					loginSuccess = true;
					userLoggedIn = getUser(resultSet.getInt("USER_ID"),da);
				} else {
					loginSuccess = false;
					userLoggedIn = null;
				}

			}
		} catch (SQLException e) {
			da.close();
			throw new PersistenceFailureException("Persistence Failure - didn't get user data");
		}
		da.close();
		return loginSuccess;
	}

	@Override
	public User getUser(int userId, DataAccess da) throws PersistenceFailureException {
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
		if(userLoggedIn!=null){
			Id = userLoggedIn.getId();
		}
		return Id;
	}

	@Override
	public Permission getPermission(int permissionId, DataAccess da) throws PersistenceFailureException {
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
	public List<Permission> searchPermission(String searchString, DataAccess da) throws PersistenceFailureException {
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
	public List<UserPermission> getAllPermissionsForUser(String userId, DataAccess da) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrganisationUnit getOrganizationUnitForUserPermission(String userId, int permissionId, DataAccess da) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasUserAccessToOrganizationUnit(String userId, int permissionId, long organizationId,
			DataAccess da) {
		// TODO Auto-generated method stub
		return false;
	}

}
