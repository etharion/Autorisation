import exceptions.PersistenceFailureException;
import organisation.OrganisationImpl;
import security.SecurityImpl;

public class Main {

	public static void main(String args[]) {
		OrganisationImpl org = new OrganisationImpl();
		
		try {
			System.out.println(org.searchOrganisation("ik"));
		} catch (PersistenceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
