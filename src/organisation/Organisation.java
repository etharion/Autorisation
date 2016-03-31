package organisation;

import java.util.List;

public interface Organisation {
	
//	public void createOrganisation();
//	
//	public void editOrganisation();
//	
//	public void deleteOrganisation();
//	
//	public void showOrganisation();
	
	public OrganisationUnit getOrganisationUnit(long id);
	
	public List<OrganisationUnit> getChildren(long id);
	
	public List<Long> getAllChildren(long id);
	
	public List<OrganisationUnit> getAllOrganisationsWithoutParents();
	
	public List<OrganisationUnit> searchOrganisation(String search);
}
