/*
 * generated by Xtext 2.30.0
 */
package fr.n7.simplepdl.txt.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.n7.simplepdl.txt.PDLRuntimeModule;
import fr.n7.simplepdl.txt.PDLStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class PDLIdeSetup extends PDLStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new PDLRuntimeModule(), new PDLIdeModule()));
	}
	
}
