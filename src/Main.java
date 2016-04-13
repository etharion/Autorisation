import exceptions.PersistenceFailureException;
import security.SecurityImpl;

public class Main {

	public static void main(String args[]) {
		SecurityImpl security = new SecurityImpl();
		
		try {
			security.login("warlordcp@gmail.com", "1234");
			System.out.println(security.getIdOfUserLoggedIn());
		} catch (PersistenceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
