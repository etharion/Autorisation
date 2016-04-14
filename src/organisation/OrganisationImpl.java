package organisation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.PersistenceFailureException;
import persistence.DataAccessForSQL;

public class OrganisationImpl implements Organisation {

	private final String GET_ORGANISATIONUNIT = "SELECT * FROM organisation WHERE id = ?";
	private final String GET_CHILDREN = "SELECT * FROM organisation WHERE parent_id = ?";
	private final String GET_ALL_CHILDREN = "WITH RECURSIVE tree (level, parent, child, name) AS "
			+ "(SELECT 1, parent_id AS parent, id as child, name AS name FROM organisation where id = ? " + "UNION "
			+ "SELECT level + 1, parent_id, id, name FROM organisation, tree WHERE parent_id = child) " + "SELECT * FROM tree ";
	private final String GET_ALL_ORGANISATION_WITHOUT_PARENTS = "SELECT * FROM organisation";
	private final String SEARCH_ORGANISATION = "SELECT * FROM organisation WHERE LOWER(name) LIKE ?";
	DataAccessForSQL da;

	@Override
	public OrganisationUnit getOrganisationUnit(long id) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		OrganisationUnit orgUnit = new OrganisationUnit();
		da = new DataAccessForSQL();

		try {
			statement = da.getConnection().prepareStatement(GET_ORGANISATIONUNIT);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {

				orgUnit.setId(resultSet.getInt("id"));
				orgUnit.setName(resultSet.getString("name"));
				orgUnit.setParentId(resultSet.getInt("parent_id"));
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
	public List<OrganisationUnit> getChildren(long id) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<OrganisationUnit> orgList = new ArrayList<>();
		da = new DataAccessForSQL();

		try {
			statement = da.getConnection().prepareStatement(GET_CHILDREN);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				OrganisationUnit orgUnit = new OrganisationUnit();
				orgUnit.setId(resultSet.getInt("id"));
				orgUnit.setName(resultSet.getString("name"));
				orgUnit.setParentId(resultSet.getInt("parent_id"));

				orgList.add(orgUnit);

			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new PersistenceFailureException("Persistence Failure - didn't get organisation unit");
		}

		da.close();
		return orgList;
	}

	@Override
	public List<OrganisationUnit> getAllChildren(long id) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<OrganisationUnit> orgList = new ArrayList<>();
		da = new DataAccessForSQL();

		try {
			statement = da.getConnection().prepareStatement(GET_ALL_CHILDREN);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				OrganisationUnit orgUnit = new OrganisationUnit();
				orgUnit.setId(resultSet.getInt("child"));
				orgUnit.setName(resultSet.getString("name"));
				orgUnit.setParentId(resultSet.getInt("parent"));

				orgList.add(orgUnit);

			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceFailureException("Persistence Failure - didn't get organisation unit");
		}

		da.close();
		return orgList;
	}

	@Override
	public List<OrganisationUnit> getAllOrganisationsWithoutParents() throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<OrganisationUnit> orgList = new ArrayList<>();
		da = new DataAccessForSQL();

		try {
			statement = da.getConnection().prepareStatement(GET_ALL_ORGANISATION_WITHOUT_PARENTS);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				OrganisationUnit orgUnit = new OrganisationUnit();
				orgUnit.setId(resultSet.getInt("id"));
				orgUnit.setName(resultSet.getString("name"));

				orgList.add(orgUnit);

			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new PersistenceFailureException("Persistence Failure - didn't get organisation unit");
		}

		da.close();
		return orgList;
	}

	@Override
	public List<OrganisationUnit> searchOrganisation(String search) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<OrganisationUnit> orgList = new ArrayList<>();
		da = new DataAccessForSQL();

		try {
			statement = da.getConnection().prepareStatement(SEARCH_ORGANISATION);
			statement.setString(1, "%" + search + "%");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				OrganisationUnit orgUnit = new OrganisationUnit();
				orgUnit.setId(resultSet.getInt("id"));
				orgUnit.setName(resultSet.getString("name"));
				orgUnit.setParentId(resultSet.getInt("parent_id"));

				orgList.add(orgUnit);

			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			throw new PersistenceFailureException("Persistence Failure - didn't get organisation unit");
		}

		da.close();
		return orgList;
	}

}
