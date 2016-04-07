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
	
	public OrganisationUnit getOrganisationUnit(long id) throws PersistenceFailureException;
	
	public List<OrganisationUnit> getChildren(long id) throws PersistenceFailureException;
	
	public List<OrganisationUnit> getAllChildren(long id) throws PersistenceFailureException;
	
	public List<OrganisationUnit> getAllOrganisationsWithoutParents() throws PersistenceFailureException;
	
	public List<OrganisationUnit> searchOrganisation(String search) throws PersistenceFailureException;
}
