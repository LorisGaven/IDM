/*
 * generated by Xtext 2.30.0
 */
package fr.n7.simplepdl.txt;


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class PDLStandaloneSetup extends PDLStandaloneSetupGenerated {

	public static void doSetup() {
		new PDLStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}
