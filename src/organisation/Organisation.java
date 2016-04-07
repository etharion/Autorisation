package organisation;

import java.util.List;

import exceptions.PersistenceFailureException;
import persistence.DataAccess;

public interface Organisation {
	
//	public void createOrganisation();
//	
//	public void editOrganisation();
//	
//	public void deleteOrganisation();
//	
//	public void showOrganisation();
	
	public OrganisationUnit getOrganisationUnit(long id, DataAccess da) throws PersistenceFailureException;
	
	public List<OrganisationUnit> getChildren(long id, DataAccess da) throws PersistenceFailureException;
	
	public List<OrganisationUnit> getAllChildren(long id, DataAccess da) throws PersistenceFailureException;
	
	public List<OrganisationUnit> getAllOrganisationsWithoutParents(DataAccess da) throws PersistenceFailureException;
	
	public List<OrganisationUnit> searchOrganisation(String search, DataAccess da) throws PersistenceFailureException;
}
