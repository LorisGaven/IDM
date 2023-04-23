/**
 */
package simplepdl.tests;

import junit.textui.TestRunner;

import simplepdl.ResourceUtilisation;
import simplepdl.SimplepdlFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Resource Utilisation</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ResourceUtilisationTest extends ProcessElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ResourceUtilisationTest.class);
	}

	/**
	 * Constructs a new Resource Utilisation test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceUtilisationTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Resource Utilisation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ResourceUtilisation getFixture() {
		return (ResourceUtilisation)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SimplepdlFactory.eINSTANCE.createResourceUtilisation());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //ResourceUtilisationTest
