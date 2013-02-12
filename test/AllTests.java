import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests{

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(model.MoveStackTest.class);
		suite.addTestSuite(model.ComputerDif1Test.class);
		suite.addTestSuite(model.ComputerDif3Test.class);
		suite.addTestSuite(model.GameModelTest.class);
		//$JUnit-END$
		return suite;
	}

}
