package organisation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import exceptions.PersistenceFailureException;
import persistence.DataAccess;

public class OrganisationImpl implements Organisation {
	
	private final String GET_ORGANISATIONUNIT = "SELECT * FROM organisation WHERE id = ?";
	private final String GET_CHILDREN = "SELECT * FROM organisation WHERE parent_id = ?";
	private final String GET_ALL_CHILDREN = "SELECT * FROM organisation WHERE id = ?"
			+ "WITH RECURSIVE tree (level, parent, child) AS"
			+ "(SELECT 1, parent_id AS parent, id as child FROM organisation where id = ?"
			+ "UNION"
			+ "SELECT level + 1, parent_id, id FROM organisation, tree WHERE parent_id = child)"
			+ "SELECT * FROM tree";
	private final String GET_ALL_ORGANISATION_WITHOUT_PARENTS = "SELECT * FROM organisation";
	private final String SEARCH_ORGANISATION = "SELECT * FROM employees WHERE LOWER(name) LIKE ?";

	@Override
	public OrganisationUnit getOrganisationUnit(long id, DataAccess da) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		OrganisationUnit orgUnit = new OrganisationUnit();
		
		try {
			statement = da.getConnection().prepareStatement(GET_ORGANISATIONUNIT);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				
				orgUnit.setId(resultSet.getInt("id"));
				orgUnit.setName(resultSet.getString("name"));
				orgUnit.setParentId(resultSet.getInt("parent_id"));
			}
		} catch (SQLException e) {
			throw new PersistenceFailureException("Persistence Failure - didn't get organisation unit");
		}
		
		da.close();
		return orgUnit;
	}

	@Override
	public List<OrganisationUnit> getChildren(long id, DataAccess da) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		OrganisationUnit orgUnit = new OrganisationUnit();
		
		try {
			statement = da.getConnection().prepareStatement(GET_CHILDREN);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				
			}
		} catch (SQLException e) {
			throw new PersistenceFailureException("Persistence Failure - didn't get organisation unit");
		}
		
		da.close();
		return null;
	}

	@Override
	public List<Long> getAllChildren(long id, DataAccess da) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrganisationUnit> getAllOrganisationsWithoutParents(DataAccess da) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrganisationUnit> searchOrganisation(String search, DataAccess da) {
		// TODO Auto-generated method stub
		return null;
	}

}
