import org.jbpm.JbpmConfiguration;

public class Test {
	public static void main(String[] args) {
		//JbpmConfiguration.getInstance().createSchema();
		JbpmConfiguration.getInstance().dropSchema();
	}
}
