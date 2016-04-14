import exceptions.PersistenceFailureException;
import organisation.OrganisationImpl;
import security.SecurityImpl;

public class Main {

	public static void main(String args[]) {
		OrganisationImpl org = new OrganisationImpl();
		SecurityImpl security = new SecurityImpl();
		
		try {
			System.out.println("Test af alle orgImpl's metoder:" + "alle inputs er hardcoded i hver syso.");
			System.out.println("get organisation unit: id = 1");
			System.out.println(org.getOrganisationUnit(1));
			System.out.println("get children: id = 1");
			System.out.println(org.getChildren(1));
			System.out.println("get all children: id = 1");
			System.out.println(org.getAllChildren(1));
			System.out.println("get all organisations without parents:");
			System.out.println(org.getAllOrganisationsWithoutParents());
			System.out.println("search organisation: kommune");
			System.out.println(org.searchOrganisation("kommune"));
			System.out.println();
			System.out.println("Test af alle securityImpl's metoder:" + "alle inputs er hardcoded i hver syso");
			System.out.println("login: warlordcp@gmail.com, 1234");
			System.out.println(security.login("warlordcp@gmail.com", "1234"));
			System.out.println("get user: id = 1");
			System.out.println(security.getUser(1));
			System.out.println("get id of user logged in");
			System.out.println(security.getIdOfUserLoggedIn());
			System.out.println("get permission");
			System.out.println(security.getPermission(1));
			System.out.println("get all permissions");
			System.out.println(security.getAllPermissions());
			System.out.println("search permission: l");
			System.out.println(security.searchPermission("l"));
			System.out.println("get all permissions for user");
			System.out.println(security.getAllPermissionsForUser(1));
			System.out.println("get organiastion unit for suer permission");
			System.out.println(security.getOrganizationUnitForUserPermission(1, 1));
			System.out.println("get organisation unit");
			System.out.println(security.getOrganisationUnit(1));
			System.out.println("has access");
			System.out.println(security.hasUserAccessToOrganizationUnit(1, 1, 1));
			
			
			
			
		} catch (PersistenceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
